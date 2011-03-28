import java.util.*;
import java.math.*;
import java.io.*;

/// a color
class EColor {
    
    int value;
    
    public EColor(int VALUE) {
        this.value = VALUE;
    }
    
    /// Returns true if this color is the same as the other color.
    public Boolean equals(EColor other_color) {
        return this.value == other_color.value;
    }
    
    /// Returns the color as a string formatted to display colored in a Linux terminal.
    public String to_terminal_colored_string(Boolean neutral) {
        
        int n = this.value;
        
        String extra_tags = "";
        if(!neutral) {
            extra_tags = "\033[1m";
        }
        
        String display_character = "#";
        display_character = Integer.toString(n);
        
        if(n == 0) {
            return extra_tags + "\033[31m" + display_character + "\033[m";
        } else if(n == 1) {
            return extra_tags + "\033[32m" + display_character + "\033[m";
        } else if(n == 2) {
            return extra_tags + "\033[33m" + display_character + "\033[m";
        } else if(n == 3) {
            return extra_tags + "\033[34m" + display_character + "\033[m";
        } else if(n == 4) {
            return extra_tags + "\033[35m" + display_character + "\033[m";
        } else if(n == 5) {
            return extra_tags + "\033[36m" + display_character + "\033[m";
        }
        return "";
    }
    
    /// Returns the color as a basic sting.
    public String to_string() {
        return Integer.toString(this.value);
    }
}
