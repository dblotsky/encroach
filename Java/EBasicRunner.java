import java.util.*;
import java.math.*;
import java.io.*;

/// This is a basic runner class for Encroach, providing an interface to an EBoard.
public class EBasicRunner {
    
    /// Makes an instance of itself, and runs it.
    public static final void main(String[] args) {
        new EBasicRunner().run(System.in, args);
        return;
    }
    
    /// The main run loop of EBasicRunner.
    private String run(InputStream input, String[] args) {
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        // 'constants'
        int NUM_COLORS  = 6;
        int X_DIMENSION = 30; // 30
        int Y_DIMENSION = 30; // 30
        
        // make a human player and an AI player
        EPlayer human    = new EPlayer("Dmitry", 0);
        EPlayer computer = new EPlayer("AI_1", 0);
        
        // set up and randomize a new board
        EBoard board = new EBoard(args, NUM_COLORS, X_DIMENSION, Y_DIMENSION);
        board.initialize(human, computer);
        
        // welcome the player to the game
        System.out.println("Welcome to Encroach!");
        System.out.println("--------------------");
        
        // print the board, and prompt for input
        board.print_terminal_colored();
        System.out.print(board.prompt());
        
        // run the game
        while(in.hasNextLine() && !board.has_winner()) {
            
            // get player's input
            String line_in = in.nextLine();
            
            // making a move
            if(line_in.matches("^[0-9]$")) {
                
                int color_initializer = Integer.parseInt(line_in);
                
                // check if the number is within bounds
                if(color_initializer >= NUM_COLORS || color_initializer < 0) {
                    System.out.println("Out of bounds: " + Integer.toString(color_initializer) + ".");
                } else {
                    
                    EColor human_next_color     = new EColor(color_initializer);
                    EColor computer_next_color  = computer.ai_next_color_choice(board.generator);
                    
                    while(human_next_color.equals(computer_next_color)) {
                        computer_next_color  = computer.ai_next_color_choice(board.generator);
                    }
                    
                    // if it's a valid move, make it
                    if(board.can_play(human, human_next_color)) {
                        board.make_move(human, human_next_color);
                        // then make the computer's move
                        board.make_move(computer, computer_next_color);
                    
                    // if not, complain
                    } else {
                        System.out.println("Illegal move: " + human_next_color.to_terminal_colored_string() + ".");
                    }
                }
            // resetting the game
            } else if(line_in.equals("reset")) {
                board.reset();
                System.err.println("DEBUG: The board has been reset.");
            
            // quitting the game
            } else if(line_in.equals("q") || line_in.equals("quit") || line_in.equals("exit")) {
                System.out.flush();
                return( "OK" );
            
            // complaining
            } else {
                System.out.println("Unrecognized input: \"" + line_in + "\".");
            }
            
            // print the board, and prompt for input
            board.print_terminal_colored();
            System.out.print(board.prompt());
        }
        
        board.print_terminal_colored();
        System.out.println(board.winner());
        
        // exit gracefully
        System.out.flush();
        return( "OK" );
    }
}
