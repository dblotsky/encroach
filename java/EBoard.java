import java.util.*;
import java.math.*;
import java.io.*;

/** The game board. **/
class EBoard {
    
    static Boolean DEBUG = false;
    
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
    
    /** Resets 'marked' flags on all squares. **/
    public void reset_marked() {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                this.field[i][j].clear_marked();
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
        
        // mark squares that will be conquered by the move, and then conquer them
        mark_conquered_by_move(player, next_color, false);
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(this.field[i][j].is_marked()) {
                    conquer(this.field[i][j], player);
                }
            }
        }
        
        // mark all squares that the opponent can reach, and then conquer the unmarked ones
        mark_reachable(player.get_opponent(), false);
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(!this.field[i][j].is_marked()) {
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
    
    /** Marks down all the squares owned by the player. Returns the number of marked squares. **/
    public int mark_owned(EPlayer player, Boolean dry_run) {
        if(DEBUG){System.err.println("DEBUG: Marking squares owned by " + player.get_name() + ".");}
        if(!dry_run) { reset_borders(player); }
        return traverse_and_mark(player, player.get_starting_square(), "owned", player.get_color(), dry_run);
    }
    
    /** Marks down all the squares reachable by the player. Returns the number of marked squares. **/
    public int mark_reachable(EPlayer player, Boolean dry_run) {
        if(DEBUG){System.err.println("DEBUG: Marking squares reachable by " + player.get_name() + ".");}
        if(!dry_run) { reset_borders(player.get_opponent()); }
        return traverse_and_mark(player, player.get_starting_square(), "reachable", 0, dry_run);
    }
    
    /** Marks down all the squares that will be conquered if the player playes the given color. Returns the number of marked squares. **/
    public int mark_conquered_by_move(EPlayer player, int color, Boolean dry_run) {
        if(DEBUG){System.err.println("DEBUG: Marking squares conquered by " + player.get_name() + "'s move to " + color + ".");}
        if(!dry_run) { reset_borders(player); }
        return traverse_and_mark(player, player.get_starting_square(), "conquered_by_move", color, dry_run);
    }
    
    /** Marks down all the squares connected to the given square by color. Returns the number of marked squares. **/
    public int mark_connected_by_color(ESquare square, Boolean dry_run) {
        if(DEBUG){System.err.println("DEBUG: Marking squares connected to x:" + square.x() + ", y:" + square.y() + ".");}
        if(!dry_run) { reset_borders(neutral_owner); }
        return traverse_and_mark(null, square, "connected_by_color", square.get_color(), dry_run);
    }
    
    /** Traverses the board based on the passed condition, and marks every traversed square as 'marked'. Returns the number of marked squares. **/
    private int traverse_and_mark(EPlayer player, ESquare start, String condition, int color, Boolean dry_run) {
        
        // reset all 'marked' flags
        reset_marked();
        
        // set up the queue
        Queue<ESquare> queue = new LinkedList<ESquare>();
        queue.offer(start);
        
        // temporary variables
        ESquare square = null;
        ESquare[] neighbors = null;
        int squares_marked = 0;
        
        // while we have squares on the queue
        while(!queue.isEmpty()) {
                
            // pop the top element, mark it as 'marked', and get its not-yet-marked neighbors
            square = queue.poll();
            square.set_marked();
            squares_marked++;
            neighbors = traversable_neighbors(square);
            if(DEBUG){System.err.println("DEBUG: Found " + neighbors.length + " neighbors for " + square.x() + ", " + square.y() + ", while it was colored " + square.get_color() + "." );}
            
            // iterate through the square's neighbors
            // if a neighbor meets the specified condition, push it onto the queue
            for(int j = 0; j < neighbors.length; j++) {
                
                // owned squares
                if(condition.equals("owned")) {
                    if(neighbors[j].get_owner() == player) { 
                        if(!queue.contains(neighbors[j])) {
                            queue.offer(neighbors[j]);
                        }
                    } else if(!dry_run) { square.border = true; }
                
                // sometime reachable squares
                } else if(condition.equals("reachable")) {
                    if(neighbors[j].get_owner() != player.get_opponent()) { 
                        if(!queue.contains(neighbors[j])) {
                            queue.offer(neighbors[j]);
                        }
                    } else if(!dry_run) { neighbors[j].border = true; }
                
                // squares that will be conquered by playing a given color
                } else if(condition.equals("conquered_by_move")) {
                    if(neighbors[j].get_owner() == player || (neighbors[j].get_color() == color)) { 
                        if(!queue.contains(neighbors[j])) {
                            queue.offer(neighbors[j]);
                        }
                    } else if(!dry_run) { square.border = true; }
                
                // squares connected to a given square by color
                } else if(condition.equals("connected_by_color")) {
                    if(neighbors[j].get_color() == square.get_color()) { 
                        if(!queue.contains(neighbors[j])) {
                            queue.offer(neighbors[j]);
                        }
                    } else if(!dry_run) { square.border = true; }
                
                // error
                } else {
                    System.err.println("BACKEND ERROR: Attempted to traverse the board using an undefined condition.");
                    break;
                }
            }
        }
        if(DEBUG){System.err.println("DEBUG: Marked " + squares_marked + " squares.");}
        return squares_marked;
    }
    
    /** Returns the neighboring squares of the parent. **/
    /// TODO: someday move this method to ESquare
    private ESquare[] traversable_neighbors(ESquare parent) {
        
        // get the parent's coordinates
        int x = parent.x();
        int y = parent.y();
        
        // set up the return list
        ArrayList<ESquare> neighbors = new ArrayList<ESquare>();
        
        // add every existing neighbor to the list
        if(x != 0) {
            if(!field[x - 1][y].is_marked()) {
                neighbors.add(field[x - 1][y]);
            }
        }
        if(y != 0) {
            if(!field[x][y - 1].is_marked()) {
                neighbors.add(field[x][y - 1]);
            }
        }
        if(x != (x_size - 1)) {
            if(!field[x + 1][y].is_marked()) {
                neighbors.add(field[x + 1][y]);
            }
        }
        if(y != (y_size - 1)) {
            if(!field[x][y + 1].is_marked()) {
                neighbors.add(field[x][y + 1]);
            }
        }
        
        // return the list as an array
        ESquare[] return_array = new ESquare[neighbors.size()];
        return neighbors.toArray(return_array);
    }
    
    /** Returns true if either player's score is equal to or greater than the winning score. **/
    public Boolean winner_exists() {
        return ((this.player_1.score >= this.winning_score) || (this.player_2.score >= this.winning_score));
    }
    
    /** Returns true if there are no more neutral squares. **/
    public Boolean filled() {
        return this.neutral_owner.score == 0;
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
    
    // TODO: someday make an iterator method for the board
    
}
