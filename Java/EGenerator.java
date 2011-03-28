import java.util.*;
import java.math.*;
import java.io.*;

/// Generates random values for different objects.
class EGenerator {
    
    Random  generator;
    int     color_range;
    
    public EGenerator(int POSSIBLE_COLORS) {
        this.generator      = new Random();
        this.color_range    = POSSIBLE_COLORS;
    }
    
    /// Returns a random color value within the color range.
    public int next_color_value() {
        return this.generator.nextInt(this.color_range);
    }
}
