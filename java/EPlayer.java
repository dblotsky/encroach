import java.util.*;
import java.math.*;
import java.io.*;

/** An entity that can own a square. Stores its name and the number of squares it owns. **/
abstract class EOwner {
    
    protected String    name;
    protected int       default_score;
    protected int       score;
    
    /** Increments the number of squares this owner owns. **/
    public void increment_score() {
        this.score += 1;
        return;
    }
    
    /** Decrements the number of squares this owner owns. **/
    public void decrement_score() {
        this.score -= 1;
        return;
    }
    
    /** Resets the number of owned squares to the default. **/
    public void reset_score() {
        this.score = default_score;
    }
    
    /** Returns the number of squares this owner owns. **/
    public int get_score() {
        return this.score;
    }
    
    /** Sets the name of this owner. **/
    public void set_name(String new_name) {
        this.name = new_name;
        return;
    }
    
    /** Returns the name of this owner. **/
    public String get_name() {
        return this.name;
    }
}

/** A set of methods that a playable entity must implement. **/
interface EPlayable {
    
    // attribute methods
    public void     increment_score();
    public void     decrement_score();
    public void     reset_score();
    public int      get_score();
    public void     set_name(String new_name);
    public String   get_name();
    public void     record_win();
    public void     record_loss();
    public int      num_wins();
    public int      num_losses();
    public ESquare  get_starting_square();
    public void     set_starting_square(ESquare new_square);
    public void     set_color(int new_color);
    public int      get_color();
    public void     set_difficulty(int new_difficulty);
    public int      get_difficulty();
    public void     set_opponent(EPlayer new_opponent);
    public EPlayer  get_opponent();
    
    // AI methods
    public int      ai_next_color_choice(EBoard board);
}

/** A player. **/
class EPlayer extends EOwner implements EPlayable {

    private EPlayer     opponent;
    private ESquare     starting_square;
    private int         color;
    private int         ai_difficulty;
    private int         games_won;
    private int         games_lost;
    
    public EPlayer(String name, int ai_difficulty) {
        this.name               = name;
        this.default_score      = 0;
        this.score              = this.default_score;
        this.color              = 0;
        this.opponent           = null;
        this.starting_square    = null;
        this.ai_difficulty      = ai_difficulty;
        this.games_won          = 0;
        this.games_lost         = 0;
    }
    
    /** Sets this player's ai difficulty level to the given one. **/
    public void set_difficulty(int new_difficulty) {
	this.ai_difficulty = new_difficulty;
	return;
    }
    
    /** Returns this player's ai difficulty level. **/
    public int get_difficulty() {
	return this.ai_difficulty;
    }
    
    /** Records that the player won a game. **/
    public void record_win() {
        this.games_won += 1;
        return;
    }
    
    /** Records that the player lost a game. **/
    public void record_loss() {
        this.games_lost += 1;
        return;
    }
    
    /** Returns the number of wins. **/
    public int num_wins() {
        return this.games_won;
    }
    
    /** Returns the number of losses. **/
    public int num_losses() {
        return this.games_lost;
    }
    
    /** Switches the player's color to the given color. **/
    public void set_color(int new_color) {
        this.color = new_color;
        return;
    }
    
    /** Returns the player's color. **/
    public int get_color() {
        return this.color;
    }
    
    /** Switches the player's opponent to the given opponent. **/
    public void set_opponent(EPlayer new_opponent) {
        this.opponent = new_opponent;
        return;
    }
    
    /** Returns the player's opponent. **/
    public EPlayer get_opponent() {
        return this.opponent;
    }
    
    /** Returns this player's starting square. **/
    public ESquare get_starting_square() {
        return this.starting_square;
    }
    
    /** Switches this player's starting square to the given one. **/
    public void set_starting_square(ESquare new_square) {
        this.starting_square = new_square;
        return;
    }
    
    /** Returns the AI's next choice of color, based on the difficulty setting. **/
    public int ai_next_color_choice(EBoard board) {
        int color_choice = 0;
        
        // 1: plays the color with the most absolute gain - does not count walled-off squares
        if(this.ai_difficulty == 1) {
            
            // get all possible moves
            int moves[] = possible_moves(board);
            
            // return the move with the highest return
            int highest = 0;
            for(int i = 0; i < moves.length; i++) {
                if(moves[i] != -1) {
                    moves[i] = board.mark_conquered_by_move(this, i, true);
                    if(moves[i] > highest) {
                        highest = moves[i];
                        color_choice = i;
                    }
                }
            }
	
	// 0, undefined: plays randomly
        } else {
	    color_choice = (int) Math.floor(Math.random() * board.num_colors);
            while(!board.can_play(this, color_choice)) {
                color_choice = (int) Math.floor(Math.random() * board.num_colors);
            }
	}
	
        return color_choice;
    }
    
    /** Returns an integer array with '-1's at indeces equaling illegal colors, and '0's at all other indeces.**/
    /// TODO: someday make this a method of EBoard
    private int[] possible_moves(EBoard board) {
        
        // make an array as large as the number of possible colors
        int moves[] = new int[board.num_colors];
        
        // set everything to 0
        for(int i = 0; i < moves.length; i++) {
            moves[i] = 0;
        }
        
        // mark the current colors of the players as illegal
        moves[board.player_1.get_color()] = -1;
        moves[board.player_2.get_color()] = -1;
        return moves;
    }
}

/** A neutral owner. **/
class ENeutral extends EOwner {
    
    public ENeutral(String name, int squares_on_board) {
        this.name              = name;
        this.default_score     = squares_on_board;
        this.score             = this.default_score;
    }
}
