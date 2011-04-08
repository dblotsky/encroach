import java.util.*;
import java.math.*;
import java.io.*;

/** The game board. **/
class EBoard {
    
    // board dimensions
    public int x_size;
    public int y_size;
    public int num_colors;
    
    // players
    public EPlayer      player_1;
    public EPlayer      player_2;
    private EOwner      neutral_owner;
    
    // field
    private ESquare[][] field;
    private ESquare     top_left;
    private ESquare     bottom_right;
    
    // winning score
    private int winning_score;
    
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
        this.player_1.set_starting_square(this.top_left);
        this.player_2.set_starting_square(this.bottom_right);
        
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
        player_1.set_color(player_1.get_starting_square().get_color());
        player_2.set_color(player_2.get_starting_square().get_color());
        conquer(player_1.get_starting_square(), player_1);
        conquer(player_2.get_starting_square(), player_2);
        player_1.get_starting_square().border = true;
        player_2.get_starting_square().border = true;
        
        // balance starting positions
        balance_start();
        
        return;
    }
    
    /** Ensures that each player has only one starting square, and can make a legal move. **/
    public void balance_start() {
        
        int p1_x = player_1.get_starting_square().x();
        int p1_y = player_1.get_starting_square().y();
        int p2_x = player_2.get_starting_square().x();
        int p2_y = player_2.get_starting_square().y();
        
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
                this.field[i][j].clear_visited();
            }
        }
    }
    
    /** Resets 'border' flags on squares bordering the given owner's territory. **/
    public void reset_borders(EOwner owner) {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(this.field[i][j].get_owner() == owner) {
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
        
        // mark and conquer squares conquered by the move
        mark_conquered_by_move(player, next_color);
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(this.field[i][j].is_visited()) {
                    conquer(this.field[i][j], player);
                }
            }
        }
        
        // mark all squares that the opponent can reach, and then conquer the unmarked ones
        mark_reachable(player.get_opponent());
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(!this.field[i][j].is_visited()) {
                    conquer(this.field[i][j], player);
                }
            }
        }
        
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
    
    /** Marks down all the squares owned by the player. **/
    public void mark_owned(EPlayer player) {
        reset_borders(player);
        traverse_and_mark(player, player.get_starting_square(), "owned", player.get_color());
    }
    
    /** Marks down all the squares reachable by the player. **/
    public void mark_reachable(EPlayer player) {
        reset_borders(player.get_opponent());
        traverse_and_mark(player, player.get_starting_square(), "reachable", 0);
    }
    
    /** Marks down all the squares that will be conquered if the player playes the given color. **/
    public void mark_conquered_by_move(EPlayer player, int color) {
        reset_borders(player);
        traverse_and_mark(player, player.get_starting_square(), "conquered_by_move", color);
    }
    
    /** Marks down all the squares connected to the given square by color. **/
    public void mark_connected_by_color(ESquare square) {
        reset_borders(neutral_owner);
        traverse_and_mark(null, square, "connected_by_color", square.get_color());
    }
    
    /** Traverses the board based on the passed condition, and marks every traversed square as 'visited'. **/
    private void traverse_and_mark(EPlayer player, ESquare start, String condition, int color) {
        
        // reset all 'visited' flags
        reset_visited();
        
        // set up the queue
        Queue<ESquare> queue = new LinkedList<ESquare>();
        queue.offer(start);
        
        // temporary variables
        /// int current_queue_size = 0;
        ESquare square = null;
        Queue<ESquare> neighbors = null;
        
        // while we have squares on the queue
        while(!queue.isEmpty()) {
            
            /// // remember the queue size
            /// current_queue_size = queue.size();
            
            /// // iterate through as many squares as there are currently on the queue
            /// for(int i = 0; i < current_queue_size; i++) {
                
            // pop the top element, mark it as 'visited', and get its neighbors
            square = queue.poll();
            square.set_visited();
            neighbors = neighbors(square);
            
            // iterate through the square's neighbors
            while(!neighbors.isEmpty()) {
                
                // if the square has not yet been visited, push it onto the queue if it meets the specified condition
                if(!neighbors.peek().is_visited()) {
                    
                    // owned squares
                    if(condition.equals("owned")) {
                        if(neighbors.peek().get_owner() == player) { 
                            queue.offer(neighbors.poll());
                        } else {square.border = true;}
                    
                    // sometime reachable squares
                    } else if(condition.equals("reachable")) {
                        if(neighbors.peek().get_owner() != player.get_opponent()) { 
                            queue.offer(neighbors.poll());
                        } else {square.border = true;}
                    
                    // squares that will be conquered by playing a given color
                    } else if(condition.equals("conquered_by_move")) {
                        if(neighbors.peek().get_owner() == player || neighbors.peek().get_color() == color) { 
                            queue.offer(neighbors.poll());
                        } else {square.border = true;}
                    
                    // squares connected to a given square by color
                    } else if(condition.equals("connected_by_color")) {
                        if(neighbors.peek().get_color() == square.get_color()) { 
                            queue.offer(neighbors.poll());
                        } else {square.border = true;}
                    
                    // error
                    } else {
                        System.err.println("BACKEND ERROR: Attempted to traverse the board using an undefined condition.");
                        break;
                    }
                }
            }
            /// }
        }
        
        return;
    }
    
    /** Returns the neighboring squares of the parent. **/
    /// TODO: someday move this method to ESquare
    private Queue<ESquare> neighbors(ESquare parent) {
        
        // get the parent's coordinates
        int x = parent.x();
        int y = parent.y();
        
        // set up the return list
        Queue<ESquare> neighbors = new LinkedList<ESquare>();
        
        // add every existing neighbor to the list
        if(x != 0) {
            neighbors.add(field[x - 1][y]);
        }
        if(y != 0) {
            neighbors.add(field[x][y - 1]);
        }
        if(x != (x_size - 1)) {
            neighbors.add(field[x + 1][y]);
        }
        if(y != (y_size - 1)) {
            neighbors.add(field[x][y + 1]);
        }
        
        // return the list as an array
        return neighbors;
    }
    
    /** Performs a recursive depth-first search on the board, marking all squares that the player can reach. **/
    /*private void traverse_reachable(EPlayer player, int x, int y) {
        
        // mark self as visited
        this.field[x][y].set_visited();
        
        // recurse through all neighbors, if they exist
        if(x != 0) {
            ESquare top_square = this.field[x - 1][y];
            if(!top_square.is_visited()) {
                if(top_square.conquerable_by_player(player)) {
                    traverse_reachable(player, (x - 1), y);
                } else {
                    top_square.border = true;
                }
            }
        }
        if(y != 0) {
            ESquare left_square = this.field[x][y - 1];
            if(!left_square.is_visited()) {
                if(left_square.conquerable_by_player(player)) {
                    traverse_reachable(player, x, (y - 1));
                } else {
                    left_square.border = true;
                }
            }
        }
        if(x != (x_size - 1)) {
            ESquare bottom_square = this.field[x + 1][y];
            if(!bottom_square.is_visited()) {
                if(bottom_square.conquerable_by_player(player)) {
                    traverse_reachable(player, (x + 1), y);
                } else {
                    bottom_square.border = true;
                }
            }
        }
        if(y != (y_size - 1)) {
            ESquare right_square = this.field[x][y + 1];
            if(!right_square.is_visited()) {
                if(right_square.conquerable_by_player(player)) {
                    traverse_reachable(player, x, (y + 1));
                } else {
                    right_square.border = true;
                }
            }
        }
    }*/
    
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
