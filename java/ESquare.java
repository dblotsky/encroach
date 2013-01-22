import java.util.*;
import java.math.*;
import java.io.*;

/** A square on the board. **/
class ESquare {
    
    // owner and color
    private EOwner  owner;
    private int     color;
    
    // state flags
    private Boolean marked;
    public Boolean  border;
    
    // coordinates: may be deprecated if irregular boards are supported
    private int     x_coord;
    private int     y_coord;
    
    /** Creates a new unowned square with color 0. **/
    public ESquare(int x_coord, int y_coord) {
        this.owner   = null;
        this.color   = 0;
        this.marked  = false;
        this.border  = false;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }
    
    /** Switches the square's owner to the given owner. **/
    public void set_owner(EOwner new_owner) {
        this.owner = new_owner;
        return;
    }
    
    /** Returns true if this square's 'marked' flag is true. False otherwise. **/
    public Boolean is_marked() {
        return this.marked;
    }
    
    /** Returns true if this square's 'marked' flag is true. False otherwise. **/
    public Boolean is_owned() {
        return get_owner().getClass().getName().equals(EPlayer.class.getName());
    }
    
    /** Returns the square's x-coordinate. **/
    public int x() {
        return this.x_coord;
    }
    
    /** Returns the square's y-coordinate. **/
    public int y() {
        return this.y_coord;
    }
    
    /** Returns the square's owner. **/
    public EOwner get_owner() {
        return this.owner;
    }
    
    /** Switches the square's color to the given color. **/
    public void set_color(int new_color) {
        this.color = new_color;
        return;
    }
    
    /** Sets the 'marked' flag to true. **/
    public void set_marked() {
        this.marked = true;
        return;
    }
    
    /** Sets the 'marked' flag to false. **/
    public void clear_marked() {
        this.marked = false;
        return;
    }
    
    /** Returns the square's color. **/
    public int get_color() {
        return this.color;
    }
    
    /** Randomizes the square's color. **/
    public void randomize_color(int num_colors) {
        this.color = (int) Math.floor(Math.random() * num_colors);
        return;
    }
}
