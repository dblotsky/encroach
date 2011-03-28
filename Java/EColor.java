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
    public String to_terminal_colored_string() {
        int n = this.value;
        if(n == 0) {
            return "\033[31m0\033[m";
        } else if(n == 1) {
            return "\033[32m1\033[m";
        } else if(n == 2) {
            return "\033[33m2\033[m";
        } else if(n == 3) {
            return "\033[34m3\033[m";
        } else if(n == 4) {
            return "\033[35m4\033[m";
        } else if(n == 5) {
            return "\033[36m5\033[m";
        }
        return "";
    }
    
    /// Returns the color as a basic sting.
    public String to_string() {
        return Integer.toString(this.value);
    }
}