import java.util.*;
import java.math.*;
import java.io.*;

/// the game board
class EBoard {
    
    // board dimensions
    int x_size;
    int y_size;
    int num_colors;
    
    // playing field
    ESquare[][] field;
    
    // player's name
    String player_name;
    
    // current ai's and player's colors
    int current_player_color;
    int current_ai_color;
    
    // scores
    int player_score;
    int ai_score;
    
    // win condition
    int winning_score;
    
    // random number generator
    Random generator;
    
    public EBoard(String[] args, String PLAYER_NAME, int NUM_COLORS, int X_DIMENSION, int Y_DIMENSION) {
        
        // initialize variables
        this.x_size = X_DIMENSION;
        this.y_size = Y_DIMENSION;
        this.num_colors = NUM_COLORS;
        
        // record the player's name
        this.player_name = PLAYER_NAME;
        
        // initialize each color
        // note: this setup is illegal, and only exists at creation
        this.current_player_color = 0;
        this.current_ai_color = 0;

        // start the random number generator
        this.generator = new Random();
        
        // make a new playing field
        this.field = new ESquare[x_size][y_size];
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                this.field[i][j] = new ESquare();
            }
        }
        
        // set ownership of the top left node and bottom right node
        field[0][0].owner = "player";
        field[x_size - 1][y_size - 1].owner = "ai";
        
        // initial scores
        this.player_score = 1;
        this.ai_score = 1;
        this.winning_score = (int) Math.ceil((this.x_size * this.y_size) / 2);
    }
    
    // accessor methods
    public int get_current_player_color() {
        return current_player_color;
    }
    
    public int get_current_ai_color() {
        return current_ai_color;
    }
    
    /// prints the board to stdout in plain text format
    public void print_simple() {
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                System.out.printf(color_number(field[i][j].color) + " ");
            }
            System.out.printf("\n");
        }
        return;
    }
    
    /// prints a prompt for input
    public String prompt() {
        return "Please enter a number between 0 and " + (num_colors - 1) + ": ";
    }
    
    /// returns a Linux terminal coloured number
    /// ONLY works for up to 6 colors
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
            
    /// Randomizes the board and resets ownership
    public void randomize() {
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                field[i][j].color = generator.nextInt(num_colors);
                field[i][j].owner = "free";
            }
        }
        
        // give the player the top left square, and record its color
        field[0][0].owner = "player";
        current_player_color = field[0][0].color;
        
        // give the ai the bottom right square
        field[x_size - 1][y_size - 1].owner = "ai";
        
        // record its color if it is a legal color
        if(can_play("ai", field[x_size - 1][y_size - 1].color)) {
            current_ai_color = field[x_size - 1][y_size - 1].color;
        
        // if color is illegal, change it to the nearest legal color, and then record
        } else {
            if(field[x_size - 1][y_size - 1].color >= (num_colors - 1)) {
                field[x_size - 1][y_size - 1].color -= 1;
            } else {
                field[x_size - 1][y_size - 1].color += 1;
            }
            current_ai_color = field[x_size - 1][y_size - 1].color;
        }
        return;
    }
    
    /// Makes a move to the next color
    public void make_move(String player, int color) {
        
        // bail if the move is illegal
        if(!can_play(player, color)) {
            return;
        }
        
        // adjust the current color
        if(player == "player") {
            current_player_color = color;
        } else if(player == "ai") {
            current_ai_color = color;
        }
        
        // for the selected player, do a breadth-first search on the board
        // mark all reachable squares as owned, and change color
        if(player == "ai") {
            traverse(player, color, (x_size-1), (y_size-1));
        } else if(player == "player") {
            traverse(player, color, 0, 0);
        } else {
            return;
        }
        
        // resets 'visited' flags on all squares
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                field[i][j].visited = false;
            }
        }
        
        return;
    }
    
    /// returns the ai's move
    public int ai_move_choice() {
        int n;
        while(true) {
            n = (int) Math.floor((Math.random() * num_colors));
            if(can_play("ai", n)) {
                break;
            }
        }
        return n;
    }
    
    /// checks if the given player is allowed to play the given color
    public Boolean can_play(String player, int color) {
        
        // color out of range
        if(color >= num_colors || color < 0) {
            return false;
        }
        
        // ai would take over player
        if(player == "ai" && color == current_player_color) {
            return false;
        }
        
        // player would take over ai
        if(player == "player" && color == current_ai_color) {
            return false;
        }
        
        // all is well
        return true;
    }
    
    /// a recursive breadth-first search
    private void traverse(String player, int next_color, int x, int y) {
        
        // mark self as visited
        field[x][y].visited = true;
        
        // recurse through all neighbors, if needed
        if(x != 0) {
            ESquare top_node = field[x - 1][y];
            if(!top_node.visited && (top_node.color == next_color || top_node.owner.equals(player))) {
                traverse(player, next_color, (x - 1), y);
            }
        }
        if(y != 0) {
            ESquare left_node = field[x][y - 1];
            if(!left_node.visited && (left_node.color == next_color || left_node.owner.equals(player))) {
                traverse(player, next_color, x, (y - 1));
            }
        }
        if(x != (x_size - 1)) {
            ESquare bottom_node = field[x + 1][y];
            if(!bottom_node.visited && (bottom_node.color == next_color || bottom_node.owner.equals(player))) {
                traverse(player, next_color, (x + 1), y);
            }
        }
        if(y != (y_size - 1)) {
            ESquare right_node = field[x][y + 1];
            if(!right_node.visited && (right_node.color == next_color || right_node.owner.equals(player))) {
                traverse(player, next_color, x, (y + 1));
            }
        }
        
        // switch the cell's color if needed
        if(field[x][y].color != next_color) {
            field[x][y].color = next_color;
        }
        
        // switch the cell's ownership if needed; update player's score
        if(field[x][y].owner != player) {
            if(player == "player") {
                player_score += 1;
            } else if(player == "ai") {
                ai_score += 1;
            }
            field[x][y].owner = player;
        }
        
        return;
    }
    
    /// returns a winner, or "none" if there is no winner yet
    public String winner() {
        String winner = "none";
        if(player_score >= winning_score) {
            winner = "player";
        } else if (ai_score >= winning_score) {
            winner = "ai";
        }
        return winner;
    }
}
