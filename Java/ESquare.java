import java.util.*;
import java.math.*;
import java.io.*;

/// A square on the board.
class ESquare {
    
    EOwner  owner;
    EColor  color;
    Boolean visited;
    Boolean border;
    int     x_coord;
    int     y_coord;
    
    public ESquare(int ECKS, int WHY) {
        this.owner   = null;
        this.color   = new EColor(0);
        this.visited = false;
        this.border  = false;
        this.x_coord = ECKS;
        this.y_coord = WHY;
    }
    
    /// Switches the square's owner to the given owner.
    public void set_owner(EOwner new_owner) {
        this.owner = new_owner;
        return;
    }
    
    /// Returns the square's owner.
    public EOwner get_owner() {
        return this.owner;
    }
    
    /// Switches the square's color to the given color.
    public void set_color(EColor new_color) {
        this.color = new_color;
        return;
    }
    
    /// Returns the square's color.
    public EColor get_color() {
        return this.color;
    }
    
    /// Randomizes the square's color.
    public void randomize_color(EGenerator generator) {
        EColor new_color = new EColor(generator.next_color_value());
        this.color = new_color;
        return;
    }
    
    /// Returns false if this square is owned by the opponent.
    public Boolean conquerable_by_player(EPlayer player) {
        if(this.get_owner() == player.get_opponent()) {
            return false;
        }
        return true;
    }
    
    /// Returns true if this square is to be conquered this turn.
    public Boolean conquered_this_turn(EColor next_color, EPlayer conqueror) {
        if(this.color.equals(next_color) || this.owner == conqueror) {
            return true;
        }
        return false;
    }
}
