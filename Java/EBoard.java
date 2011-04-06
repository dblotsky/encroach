import java.util.*;
import java.math.*;
import java.io.*;

/** The game board. **/
class EBoard {
    
    // board dimensions
    int x_size;
    int y_size;
    int num_colors;
    
    // players
    EPlayer     player_1;
    EPlayer     player_2;
    EOwner      neutral_owner;
    
    // field
    ESquare[][] field;
    ESquare     top_left;
    ESquare     bottom_right;
    
    // winning score
    int winning_score;
    
    /** Constructs the board with given colors and dimensions. **/
    public EBoard(int num_colors, int x_size, int y_size) {
        
        // board dimensions
        this.x_size         = x_size;
        this.y_size         = y_size;
        this.num_colors     = num_colors;
        
        // initialize the neutral player
        this.player_1      = null;
        this.player_2      = null;
        this.neutral_owner = new ENeutral("Neutral", this.x_size * this.y_size);
        
        // make a new playing field
        this.field = new ESquare[this.x_size][this.y_size];
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                this.field[i][j] = new ESquare(i, j);
            }
        }
        
        this.top_left     = this.field[0][0];
        this.bottom_right = this.field[this.x_size - 1][this.y_size - 1];
        
        // win condition
        this.winning_score = (int) Math.ceil((x_size * y_size) / 2);
    }
    
    /** Initializes the board with the given players. **/
    public void initialize(EPlayer player_1, EPlayer player_2) {
                
        // initialize players
        this.player_1 = player_1;
        this.player_2 = player_2;
                
        // set the players to oppose each other
        this.player_1.set_opponent(this.player_2);
        this.player_2.set_opponent(this.player_1);
        
        // set each player's starting squares
        this.player_1.starting_square = this.top_left;
        this.player_2.starting_square = this.bottom_right;
        
        reset();
        return;
    }
    
    /** Brings the field to a state of randomness and resets player ownership to starting squares. **/
    public void reset() {
        
        // randomize the colors and reset all ownership to neutral
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                field[i][j].set_owner(neutral_owner);
                field[i][j].border = false;
                field[i][j].randomize_color(num_colors);
            }
        }
        
        // make sure that top left and bottom right squares are of different color
        while(top_left.get_color() == bottom_right.get_color()) {
            bottom_right.randomize_color(num_colors);
        }
        
        // reset all scores to defaults
        player_1.reset_score();
        player_2.reset_score();
        neutral_owner.reset_score();
        
        // give the players their starting squares and starting colors
        player_1.set_color(player_1.starting_square.get_color());
        player_2.set_color(player_2.starting_square.get_color());
        conquer(player_1.starting_square, player_1);
        conquer(player_2.starting_square, player_2);
        player_1.starting_square.border = true;
        player_2.starting_square.border = true;
        
        // balance starting positions
        balance_start();
        
        return;
    }
    
    /** Ensures that each player has only one starting square, and can make a legal move. **/
    public void balance_start() {
        
        int p1_x = player_1.starting_square.x_coord;
        int p1_y = player_1.starting_square.y_coord;
        int p2_x = player_2.starting_square.x_coord;
        int p2_y = player_2.starting_square.y_coord;
        
        ESquare p1_neighbor_1 = field[p1_x + 1][p1_y];
        ESquare p1_neighbor_2 = field[p1_x][p1_y + 1];
        ESquare p2_neighbor_1 = field[p2_x - 1][p2_y];
        ESquare p2_neighbor_2 = field[p2_x][p2_y - 1];
        
        while(player_1.get_color() == p1_neighbor_1.get_color() || !can_play(player_1, p1_neighbor_1.get_color())) {
            p1_neighbor_1.randomize_color(num_colors);
        }
        while(player_1.get_color() == p1_neighbor_2.get_color()) {
            p1_neighbor_2.randomize_color(num_colors);
        }
        while(player_2.get_color() == p2_neighbor_1.get_color() || !can_play(player_2, p2_neighbor_1.get_color())) {
            p2_neighbor_1.randomize_color(num_colors);
        }
        while(player_2.get_color() == p2_neighbor_2.get_color()) {
            p2_neighbor_2.randomize_color(num_colors);
        }
        return;
    }
    
    /** Returns false if the player cannot play the color - if it's the same color, the opponent's color, or out of bounds. **/
    public Boolean can_play(EPlayer player, int color) {
        if(color == player.get_opponent().get_color() || (color < 0 || color >= this.num_colors) || color == player.get_color()) {
            return false;
        }
        return true;
    }
    
    /** Resets 'visited' flags on all squares. **/
    public void reset_visited() {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                this.field[i][j].visited = false;
            }
        }
    }
    
    /** Resets 'border' flags on squares bordering the given player's territory. **/
    public void reset_border(EPlayer player) {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(this.field[i][j].get_owner() == player) {
                    this.field[i][j].border = false;
                }
            }
        }
    }
    
    /** Makes a move to the next color for the player. **/
    public void play_color(EPlayer player, int next_color) {
                
        // bail if the move is illegal
        if(!can_play(player, next_color)) {
            System.err.println("ERROR: Invalid move attempted by: " + player.name + ".");
            return;
        }
        
        // set the player's color
        player.set_color(next_color);
        
        // remember the opponent for convenience
        EPlayer opponent = player.get_opponent();
        
        // clear border flags for this player
        reset_border(player);
        
        // conquer newly connected squares
        traverse_owned(player, next_color, player.starting_square.x_coord, player.starting_square.y_coord);
        reset_visited();
        
        // determine all squares that the opponent cannot reach
        traverse_reachable(opponent, opponent.starting_square.x_coord, opponent.starting_square.y_coord);
        
        // now conquer all squares that the opponent cannot reach
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(!this.field[i][j].visited) {
                    conquer(this.field[i][j], player);
                    // also clear borders
                }
            }
        }
        reset_visited();
        
        return;
    }
    
    /** Adjusts ownership of the given square to the given player and adjusts scores. **/
    public void conquer(ESquare square, EPlayer conqueror) {
        square.get_owner().decrement_score();
        square.set_owner(conqueror);
        square.get_owner().increment_score();
        square.set_color(conqueror.get_color());
        return;
    }
    
    /** Performs a recursive depth-first search on the board, conquering squares for the player. **/
    private void traverse_owned(EPlayer player, int next_color, int x, int y) {
        
        // mark self as visited
        this.field[x][y].visited = true;
        
        // recurse through all neighbors, if they exist and fit traversal conditions
        if(x != 0) {
            ESquare top_square = this.field[x - 1][y];
            if(!top_square.visited) {
                if(top_square.conquered_by_move(next_color, player)) {
                    traverse_owned(player, next_color, (x - 1), y);
                }
            }
        }
        if(y != 0) {
            ESquare left_square = this.field[x][y - 1];
            if(!left_square.visited) {
                if(left_square.conquered_by_move(next_color, player)) {
                    traverse_owned(player, next_color, x, (y - 1));
                }
            }
        }
        if(x != (x_size - 1)) {
            ESquare bottom_square = this.field[x + 1][y];
            if(!bottom_square.visited) {
                if(bottom_square.conquered_by_move(next_color, player)) {
                    traverse_owned(player, next_color, (x + 1), y);
                }
            }
        }
        if(y != (y_size - 1)) {
            ESquare right_square = this.field[x][y + 1];
            if(!right_square.visited) {
                if(right_square.conquered_by_move(next_color, player)) {
                    traverse_owned(player, next_color, x, (y + 1));
                }
            }
        }
        
        // conquer this square
        conquer(this.field[x][y], player);
        return;
    }
    
    // private void breadth-first-search() {
        /*
		make a queue
		push the first element on the queue
		while the queue is not empty
			record the current length of the queue
			for the current length
				pop element
                mark element as visited
				perform whatever we need on the element
				push valid children onto the queue
		when the queue is empty, that means that we traveresed everything we can reach
		*/
    // }
    
    /** Performs a recursive depth-first search on the board, marking all squares that the player can reach. **/
    private void traverse_reachable(EPlayer player, int x, int y) {
        
        // mark self as visited
        this.field[x][y].visited = true;
        
        // recurse through all neighbors, if they exist
        if(x != 0) {
            ESquare top_square = this.field[x - 1][y];
            if(!top_square.visited) {
                if(top_square.conquerable_by_player(player)) {
                    traverse_reachable(player, (x - 1), y);
                } else {
                    top_square.border = true;
                }
            }
        }
        if(y != 0) {
            ESquare left_square = this.field[x][y - 1];
            if(!left_square.visited) {
                if(left_square.conquerable_by_player(player)) {
                    traverse_reachable(player, x, (y - 1));
                } else {
                    left_square.border = true;
                }
            }
        }
        if(x != (x_size - 1)) {
            ESquare bottom_square = this.field[x + 1][y];
            if(!bottom_square.visited) {
                if(bottom_square.conquerable_by_player(player)) {
                    traverse_reachable(player, (x + 1), y);
                } else {
                    bottom_square.border = true;
                }
            }
        }
        if(y != (y_size - 1)) {
            ESquare right_square = this.field[x][y + 1];
            if(!right_square.visited) {
                if(right_square.conquerable_by_player(player)) {
                    traverse_reachable(player, x, (y + 1));
                } else {
                    right_square.border = true;
                }
            }
        }
    }
    
    /** Returns true if either player's score is equal to or greater than the winning score. **/
    public Boolean winner_exists() {
        return this.neutral_owner.score == 0;
        // return ((this.player_1.score >= this.winning_score) || (this.player_2.score >= this.winning_score));
    }
    
    /** Returns the game field. **/
    public ESquare[][] get_field() {
        return this.field;
    }
    
    /** Returns the winner of the game. **/
    public EPlayer winner() {
        
        // if there is no winner yet, return nothing and complain
        if(!winner_exists()) {
            System.err.println("ERROR: There is no winner yet.");
            return null;
        }
        
        // return the player with the highest score
        if(player_1.get_score() > player_2.get_score()) {
            return player_1;
        } else if(player_1.get_score() < player_2.get_score()) {
            return player_2;
        }
        
        // if a tie is discovered, Chuck Norris wins
        return new EPlayer("Chuck Norris", 0);
    }
    
    /** Marks the end of the game and resets the board for a new game. **/
    public void end_game_and_reset() {
        end_game();
        reset();
    }
    
    /** Ends the game and updates win/loss count. **/
    public void end_game() {
        
        // refuse to end until there is a winner
        if(!winner_exists()) {
            System.err.println("ERROR: Game is not yet over.");
            return;
        }
    
        // record win and loss
        if(winner() == player_1) {
            player_1.record_win();
            player_2.record_loss();
        } else if(winner() == player_2) {
            player_1.record_loss();
            player_2.record_win();
        }
        return;
    }
    
    // TODO: make an iterator method for the board?
    
}
