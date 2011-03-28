import java.util.*;
import java.math.*;
import java.io.*;

/// The game board.
class EBoard {
    
    // board dimensions
    int x_size;
    int y_size;
    int num_colors;
    
    // random generator of various things
    EGenerator  generator;
    
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
    
    /// Constructs the board.
    public EBoard(String[] args, int NUM_COLORS, int X_DIMENSION, int Y_DIMENSION) {
        
        // board dimensions
        this.x_size         = X_DIMENSION;
        this.y_size         = Y_DIMENSION;
        this.num_colors     = NUM_COLORS;
        
        // initialize the random generator
        this.generator = new EGenerator(this.num_colors);
        
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
        
        // System.err.println("DEBUG: These squares are special: top_left - " + this.top_left.x_coord + ", " + this.top_left.y_coord + "; bottom_right - " +  this.bottom_right.x_coord + ", " + this.bottom_right.y_coord + ".");
        
        // win condition
        this.winning_score = (int) Math.ceil((x_size * y_size) / 2);
    }
    
    /// Initializes the board.
    public void initialize(EPlayer PLAYER_1, EPlayer PLAYER_2) {
        
        // System.err.println("DEBUG: An initialization is attempted with these players: " + PLAYER_1.name + ", " + PLAYER_2.name + ".");
        
        // initialize players
        this.player_1 = PLAYER_1;
        this.player_2 = PLAYER_2;
        
        // System.err.println("DEBUG: The board is initialized with these players: " + this.player_1.name + ", " + this.player_2.name + ".");
        
        // set the players to oppose each other
        this.player_1.set_opponent(this.player_2);
        this.player_2.set_opponent(this.player_1);
        
        // set each player's starting squares
        this.player_1.starting_square = this.top_left;
        this.player_2.starting_square = this.bottom_right;
        // System.err.println("DEBUG: The players' starting squares are as follows: " + this.player_1.name + " - " + this.player_1.starting_square.x_coord + ", " + this.player_1.starting_square.y_coord + "; " + this.player_2.name + " - " +  this.player_2.starting_square.x_coord + ", " + this.player_2.starting_square.y_coord + ".");
        
        reset();
        return;
    }
    
    /// Brings the field to a state of randomness and resets player ownership to starting squares.
    public void reset() {
        
        // randomize the colors and reset all ownership to neutral
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                this.field[i][j].set_owner(neutral_owner);
                this.field[i][j].randomize_color(this.generator);
                // System.err.println("DEBUG: Randomizing. color became - " + this.field[i][j].get_color().to_string() + "; owner became - " + this.field[i][j].get_owner().name);
            }
        }
        
        // make sure that top left and bottom right squares are of different color
        while(this.top_left.get_color().equals(this.bottom_right.get_color())) {
            this.bottom_right.randomize_color(this.generator);
        }
        
        // System.err.println("DEBUG: Special square colors re-initialized: top_left.color - " + this.top_left.get_color().to_terminal_colored_string() + ", bottom_right.color - " + this.bottom_right.get_color().to_terminal_colored_string() + ".");
        
        // System.err.println("DEBUG: The players' starting squares are as follows: " + this.player_1.name + " - " + this.player_1.starting_square.x_coord + ", " + this.player_1.starting_square.y_coord + "; " + this.player_2.name + " - " +  this.player_2.starting_square.x_coord + ", " + this.player_2.starting_square.y_coord + ".");
        
        // reset all scores to defaults
        this.player_1.reset_score();
        this.player_2.reset_score();
        this.neutral_owner.reset_score();
        
        // System.err.println("DEBUG: Scores reset: player_1.score - " + this.player_1.score + ", player_2.score - " + this.player_2.score + ", neutral_owner.score - " + this.neutral_owner.score + ".");
        
        // give the players their starting squares and starting colors
        this.player_1.set_color(this.player_1.starting_square.get_color());
        this.player_2.set_color(this.player_2.starting_square.get_color());

        // System.err.println("DEBUG: Player colors changed to starting squares: player_1.color - " + this.player_1.get_color().to_terminal_colored_string() + ", player_2.color - " + this.player_2.get_color().to_terminal_colored_string() + ".");

        conquer(this.player_1.starting_square, this.player_1);
        conquer(this.player_2.starting_square, this.player_2);
        
        // correct any starting ownership of extra squares
        make_move(this.player_1, this.player_1.get_color());
        make_move(this.player_2, this.player_2.get_color());
        
        // System.err.println("DEBUG: The board has been reset.");
        return;
    }
    
    /// Returns false if the given color is equal to the player's opponent's color.
    public Boolean can_play(EPlayer player, EColor next_color) {
        if(next_color.equals(player.get_opponent().get_color())) {
            return false;
        }
        return true;
    }
    
    /// Prints the board to stdout in coloured terminal format.
    public void print_terminal_colored() {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                System.out.printf(this.field[i][j].get_color().to_terminal_colored_string((this.field[i][j].get_owner() == this.neutral_owner)) + " ");
            }
            System.out.printf("\n");
        }
        return;
    }
    
    /// Prints the board to stdout in simplest format.
    public void print_simple() {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                System.out.printf(this.field[i][j].get_color().to_string() + " ");
            }
            System.out.printf("\n");
        }
        return;
    }
    
    /// Prints a prompt for input.
    public String prompt() {
        return "Enter digit within \033[1m0\033[m - \033[1m" + (this.num_colors - 1) + "\033[m (but not " + this.player_2.get_color().to_terminal_colored_string(true) + "): ";
    }
    
    /// Resets 'visited' flags on all squares.
    public void reset_visited() {
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                this.field[i][j].visited = false;
            }
        }
    }
    
    /// Makes a move to the next color for the player.
    public void make_move(EPlayer player, EColor next_color) {
        
        // System.err.println("DEBUG: A move attempted with these parameters: player.name - " + player.name + ", next_color - " + next_color.to_string() + ".");
        
        // bail if the move is illegal
        if(!can_play(player, next_color)) {
            System.err.println("ERROR: Invalid move attempted.");
            return;
        }
        
        // set the player's color
        player.set_color(next_color);
        
        // remember the opponent for convenience
        EPlayer opponent = player.get_opponent();
        
        // conquer connected squares
        traverse_owned(player, next_color, player.starting_square.x_coord, player.starting_square.y_coord);
        reset_visited();
        
        // mark all squares that the opponent can reach
        traverse_reachable(opponent, opponent.starting_square.x_coord, opponent.starting_square.y_coord);
        
        // go over all squares and conquer the ones that were not marked
        for(int i = 0; i < this.field.length; i++) {
            for(int j = 0; j < this.field[i].length; j++) {
                if(!this.field[i][j].visited) {
                    conquer(this.field[i][j], player);
                }
            }
        }
        reset_visited();
        
        return;
    }
    
    /// Adjusts ownership of the given square to the given player and adjusts scores.
    public void conquer(ESquare target, EPlayer conqueror) {
        // System.err.println("DEBUG: A conquer attempted with these paremters: target.x_coord - " + target.x_coord + ", target.y_coord - " + target.y_coord + ", conqueror.name - " + conqueror.name + ".");
        target.get_owner().decrement_score();
        target.set_owner(conqueror);
        target.get_owner().increment_score();
        target.set_color(conqueror.get_color());
        return;
    }
    
    /// Performs a recursive breadth-first search on the board, conquering squares for the player.
    private void traverse_owned(EPlayer player, EColor next_color, int x, int y) {
        
        // mark self as visited
        this.field[x][y].visited = true;
        
        // recurse through all neighbors, if they exist
        if(x != 0) {
            ESquare top_square = this.field[x - 1][y];
            if(!top_square.visited && top_square.conquered_this_turn(next_color, player)) {
                traverse_owned(player, next_color, (x - 1), y);
            }
        }
        if(y != 0) {
            ESquare left_square = this.field[x][y - 1];
            if(!left_square.visited && left_square.conquered_this_turn(next_color, player)) {
                traverse_owned(player, next_color, x, (y - 1));
            }
        }
        if(x != (x_size - 1)) {
            ESquare bottom_square = this.field[x + 1][y];
            if(!bottom_square.visited && bottom_square.conquered_this_turn(next_color, player)) {
                traverse_owned(player, next_color, (x + 1), y);
            }
        }
        if(y != (y_size - 1)) {
            ESquare right_square = this.field[x][y + 1];
            if(!right_square.visited && right_square.conquered_this_turn(next_color, player)) {
                traverse_owned(player, next_color, x, (y + 1));
            }
        }
        
        // conquer this square
        conquer(this.field[x][y], player);
        return;
    }
    
    /// Performs a recursive breadth-first search on the board, marking all squares that the player can reach.
    private void traverse_reachable(EPlayer player, int x, int y) {
        
        // mark self as visited
        this.field[x][y].visited = true;
        
        // recurse through all neighbors, if they exist
        if(x != 0) {
            ESquare top_square = this.field[x - 1][y];
            if(!top_square.visited && top_square.conquerable_by_player(player)) {
                traverse_reachable(player, (x - 1), y);
            }
        }
        if(y != 0) {
            ESquare left_square = this.field[x][y - 1];
            if(!left_square.visited && left_square.conquerable_by_player(player)) {
                traverse_reachable(player, x, (y - 1));
            }
        }
        if(x != (x_size - 1)) {
            ESquare bottom_square = this.field[x + 1][y];
            if(!bottom_square.visited && bottom_square.conquerable_by_player(player)) {
                traverse_reachable(player, (x + 1), y);
            }
        }
        if(y != (y_size - 1)) {
            ESquare right_square = this.field[x][y + 1];
            if(!right_square.visited && right_square.conquerable_by_player(player)) {
                traverse_reachable(player, x, (y + 1));
            }
        }
    }
    
    /// Returns true if either player's score is equal to or greater than the winning score.
    public Boolean has_winner() {
        return this.neutral_owner.score == 0;
        // return ((this.player_1.score >= this.winning_score) || (this.player_2.score >= this.winning_score));
    }
    
    /// Returns the winner of the game.
    public String winner() {
        if(this.player_1.score >= this.player_2.score) {
            return this.player_1.name + " wins.";
        }
        return this.player_2.name + " wins.";
    }
    
    /// Returns the current score.
    public String score() {
        return this.player_1.name + ": " + this.player_1.score + ", " + this.player_2.name + ": " + this.player_2.score + ".";
    }
}
