import java.util.*;
import java.math.*;
import java.io.*;

public class ERunner {

    public static final void main(String[] args) {
        new ERunner().run(System.in, args);
    }
    
    private String run(InputStream input, String[] args) {
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        // set up a board
        EBoard board = new EBoard(args);
        board.randomize();
        
        System.out.println("Please enter a command.");
        
        // run the game
        while(in.hasNextLine()) {
            
            // get the input
            String line = in.nextLine();
            String[] command = line.split(":");
            
            String command_name = command[0];
            if(command_name.equals("display")) {
                board.print();
            } else if(command_name.matches("^[0-9]$")) {
                board.make_move(Integer.parseInt(command[0]));
                board.print();
            } else if(command_name.equals("randomize")) {
                board.randomize();
                board.print();
            } else if(command_name.equals("")) {
                System.out.println("Please enter a command.");
            } else {
                System.out.println("Unrecognized command: " + command_name + ".");
            }
        }

        System.out.flush();
        return( "OK" );
    }
    
    /// a square on the board
    class ENode {
        
        // flag of ownership, flag of being visited, and the block's color
        Boolean owned;
        Boolean visited;
        int color;
        
        public ENode() {
            owned   = false;
            visited = false;
            color   = 0;
        }
    }
    
    /// the game board
    class EBoard {
        
        public static final int NUM_COLORS  = 6;
        public static final int X_DIMENSION = 28;
        public static final int Y_DIMENSION = 28;
        
        // board dimensions
        int x_size;
        int y_size;
        
        // playing field
        ENode[][] field;
        
        // random number generator
        Random generator;

        // recursion depth testing
        int max_depth;
        
        public EBoard(String[] args) {
            
            // initialize variables
            this.x_size = X_DIMENSION;
            this.y_size = Y_DIMENSION;

            // start the random number generator
            this.generator = new Random();
            
            // make a new playing field
            this.field = new ENode[x_size][y_size];
            for(int i = 0; i < this.x_size; i++) {
                for(int j = 0; j < this.y_size; j++) {
                    this.field[i][j] = new ENode();
                }
            }
            
            // set ownership of the top left node
            field[0][0].owned = true;
            
            // recursion depth testing
            max_depth = 0;
        }
        
        /// prints the board
        public void print() {
            for(int i = 0; i < this.x_size; i++) {
                for(int j = 0; j < this.y_size; j++) {
                    System.out.printf(color_number(this.field[i][j].color) + " ");
                }
                System.out.printf("\n");
            }
            return;
        }
        
        // returns a console-coloured number
        // ONLY works for up to 6 colors
        private String color_number(int n) {
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
                
        /// randomizes the board; resets ownership
        public void randomize() {
            for(int i = 0; i < this.x_size; i++) {
                for(int j = 0; j < this.y_size; j++) {
                    field[i][j].color = generator.nextInt(NUM_COLORS);
                    field[i][j].owned = false;
                }
            }
            field[0][0].owned = true;
            return;
        }
        
        /// makes a move to the next color
        public void make_move(int color) {
            
            // bail if the color is out of bounds
            if(color >= NUM_COLORS || color < 0) {
                return;
            }
            
            // do a breadth-first search on the board, marking all reachable squares as owned, and changing color
            move_helper(color, 0, 0, 0);
            
            // resets 'visited' flags on all squares
            for(int i = 0; i < x_size; i++) {
                for(int j = 0; j < y_size; j++) {
                    field[i][j].visited = false;
                }
            }
            
            // print and reset recursion depth
            System.out.println("Went " + Integer.toString(max_depth) + " iterations deep.");
            max_depth = 0;
            
            System.out.println("Changed color to " + Integer.toString(color) + ".");
            
            return;
        }
        
        // recursive function for breadth-first search
        private void move_helper(int next_color, int x, int y, int depth) {
            
            // mark self as visited
            field[x][y].visited = true;
            
            // recursion depth testing
            depth += 1;
            if(depth > max_depth) {
                max_depth = depth;
            }
            
            // recurse through all neighbors, if needed
            if(x != 0) {
                ENode top_node = field[x - 1][y];
                if(!top_node.visited && (top_node.color == next_color || top_node.owned == true)) {
                    move_helper(next_color, (x - 1), y, depth);
                }
            }
            if(y != 0) {
                ENode left_node = field[x][y - 1];
                if(!left_node.visited && (left_node.color == next_color || left_node.owned == true)) {
                    move_helper(next_color, x, (y - 1), depth);
                }
            }
            if(x != (x_size - 1)) {
                ENode bottom_node = field[x + 1][y];
                if(!bottom_node.visited && (bottom_node.color == next_color || bottom_node.owned == true)) {
                    move_helper(next_color, (x + 1), y, depth);
                }
            }
            if(y != (y_size - 1)) {
                ENode right_node = field[x][y + 1];
                if(!right_node.visited && (right_node.color == next_color || right_node.owned == true)) {
                    move_helper(next_color, x, (y + 1), depth);
                }
            }
            
            // switch the cell's color
            field[x][y].color = next_color;
            field[x][y].owned = true;
            
            // this.display();
            
            return;
        }
    }
}
