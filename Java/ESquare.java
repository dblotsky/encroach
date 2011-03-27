import java.util.*;
import java.math.*;
import java.io.*;

/// a square on the board
class ESquare {
    
    // flag of ownership, flag of being visited, and the block's color
    String owner;
    Boolean visited;
    int color;
    
    public ESquare() {
        owner   = "free";
        visited = false;
        color   = 0;
    }
}
