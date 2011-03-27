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
        
        // 'constants'
        int NUM_COLORS  = 6;
        int X_DIMENSION = 20;
        int Y_DIMENSION = 20;
        String PLAYER_NAME = "Dmitry";
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        // set up and randomize a new board
        EBoard board = new EBoard(args, PLAYER_NAME, NUM_COLORS, X_DIMENSION, Y_DIMENSION);
        board.randomize();
        
        // adjust ownership in the case of starting branches
        board.make_move("player", board.get_current_player_color());
        board.make_move("ai", board.get_current_ai_color());
        
        // welcome the player to the game
        System.out.println("Welcome to Encroach!");
        System.out.println("--------------------");
        
        // print the board, and prompt for input
        board.print_simple();
        System.out.print(board.prompt());
        
        // run the game
        while(in.hasNextLine()) {
            
            // check for a win
            String winner = board.winner();
            if(winner.equals("player")) {
                System.out.println("You win.");
                break;
            } else if(winner.equals("ai")) {
                System.out.println("You lose.");
                break;
            }
            
            // get player's input
            String line_in = in.nextLine();
            
            // making a move
            if(line_in.matches("^[0-9]$")) {
                
                int move = Integer.parseInt(line_in);
                
                // if it's a valid move, make it
                if(board.can_play("player", move)) {
                    board.make_move("player", Integer.parseInt(line_in));
                    board.make_move("ai", board.ai_move_choice());
                } else { // if not, complain
                    System.out.println("Invalid move: " + move + ".");
                }
                
            // resetting the game
            } else if(line_in.equals("reset")) {
                board.randomize();
            
            // quitting the game
            } else if(line_in.equals("q") || line_in.equals("quit") || line_in.equals("exit")) {
                System.out.flush();
                return( "OK" );
            
            // complaining
            } else {
                System.out.println("Unrecognized input: \"" + line_in + "\".");
            }
            
            // print the board, and prompt for input
            board.print_simple();
            System.out.print(board.prompt());
        }
        
        // exit gracefully
        System.out.flush();
        return( "OK" );
    }
}
