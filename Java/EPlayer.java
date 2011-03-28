import java.util.*;
import java.math.*;
import java.io.*;

/// An entity that can own a square. Stores the number of squares it owns.
abstract class EOwner {
    
    String  name;
    int     default_score;
    int     score;
    
    /// Increments the number of squares this owner owns.
    public void increment_score() {
        this.score += 1;
        return;
    }
    
    /// Decrements the number of squares this owner owns.
    public void decrement_score() {
        this.score -= 1;
        return;
    }
    
    /// Resets the number of owned squares to zero;
    public void reset_score() {
        this.score = default_score;
    }
}

/// A player.
class EPlayer extends EOwner implements EPlayable {

    EPlayer     opponent;
    EColor      color;
    ESquare     starting_square;
    int         ai_difficulty;
    
    public EPlayer(String NAME, int DIFFICULTY) {
        this.name               = NAME;
        this.default_score      = 0;
        this.score              = this.default_score;
        this.color              = null;
        this.opponent           = null;
        this.starting_square    = null;
        this.ai_difficulty      = DIFFICULTY;
    }
    
    /// Switches the player's color to the given color.
    public EColor set_color(EColor new_color) {
        this.color = new_color;
        return this.color;
    }
    
    /// Returns the player's color.
    public EColor get_color() {
        return this.color;
    }
    
    /// Switches the player's opponent to the given opponent.
    public EPlayer set_opponent(EPlayer new_opponent) {
        this.opponent = new_opponent;
        return this.opponent;
    }
    
    /// Returns the player's opponent.
    public EPlayer get_opponent() {
        return this.opponent;
    }
    
    /// Returns the AI's next choice of color.
    public EColor ai_next_color_choice(EGenerator generator) {
        EColor new_color = new EColor(generator.next_color_value());
        return new_color;
    }
}

/// A set of functions that a playable entity must impement.
interface EPlayable {
    
    // score manipulation functions
    public void     increment_score();
    public void     decrement_score();
    public void     reset_score();
    
    // color manipulation functions
    public EColor   set_color(EColor COLOR);
    public EColor   get_color();
    
    // opponent manipulation functions
    public EPlayer  set_opponent(EPlayer new_opponent);
    public EPlayer  get_opponent();
    
    // AI functions
    public EColor   ai_next_color_choice(EGenerator generator);
}

/// A neutral owner.
class ENeutral extends EOwner {
    
    public ENeutral(String NAME, int SQUARES_ON_BOARD) {
        this.name              = NAME;
        this.default_score     = SQUARES_ON_BOARD;
        this.score             = this.default_score;
    }
}
