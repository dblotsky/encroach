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
    
    /// Prints the rules of the game and prints how to play.
    public void print_rules() {
        System.out.println("--------------------------------------------");
        System.out.println("Type these commands to play:");
        System.out.println("");
        System.out.println("    reset         - resets board");
        System.out.println("    q|quit|exit   - quits the game");
        System.out.println("    digit         - makes a move");
        System.out.println("    score         - prints the score");
        System.out.println("    display       - prints the board");
        System.out.println("    rules         - prints these rules");
        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("Rules:");
        System.out.println("");
        System.out.println("    - you start in top left corner");
        System.out.println("    - opponent starts in bottom right corner");
        System.out.println("    - you can't choose the opponent's color");
        System.out.println("    - game ends when no squares are neutral");
        System.out.println("");
        System.out.println("--------------------------------------------");
        return;
    }
    
    /// The main run loop of EBasicRunner.
    private String run(InputStream input, String[] args) {
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        // 'constants'
        int NUM_COLORS  = 6;
        int X_DIMENSION = 0;
        int Y_DIMENSION = 0;
        
        try {
            X_DIMENSION = Integer.parseInt(args[0]);
            Y_DIMENSION = Integer.parseInt(args[1]);
            if((X_DIMENSION < 5 || Y_DIMENSION < 5) || (X_DIMENSION > 50 || Y_DIMENSION > 50)) {
                X_DIMENSION = 20;
                Y_DIMENSION = 20;
            }
        } catch (Exception e) {
            X_DIMENSION = 20;
            Y_DIMENSION = 20;
        }
        
        
        // make a human player and an AI player
        EPlayer human    = new EPlayer("Human player", 0);
        EPlayer computer = new EPlayer("AI player", 0);
        
        // set up and randomize a new board
        EBoard board = new EBoard(args, NUM_COLORS, X_DIMENSION, Y_DIMENSION);
        board.initialize(human, computer);
        
        // welcome the player to the game
        System.out.println("");
        System.out.println("            \033[1mWelcome to Encroach!\033[m            ");
        print_rules();
        System.out.println("");
        
        // print the board, and prompt for input
        board.print_terminal_colored();
        System.out.println("");
        System.out.print(board.prompt());
        
        // run the game
        while(in.hasNextLine()) {
            
            // get player's input
            String line_in = in.nextLine();
            System.out.println("");
            
            // making a move
            if(line_in.matches("^[0-9]$")) {
                
                int color_initializer = Integer.parseInt(line_in);
                
                // check if the number is out of bounds
                if(color_initializer >= NUM_COLORS || color_initializer < 0) {
                    System.out.println("Out of bounds: " + Integer.toString(color_initializer) + ".");
                
                // if it is not, proceed with making a move
                } else {
                    
                    EColor human_next_color     = new EColor(color_initializer);
                    EColor computer_next_color  = computer.ai_next_color_choice(board);
                    
                    while(human_next_color.equals(computer_next_color)) {
                        computer_next_color  = computer.ai_next_color_choice(board);
                    }
                    
                    // if it's a valid move, make it
                    if(board.can_play(human, human_next_color)) {
                        
                        board.make_move(human, human_next_color);
                        
                        // then make the computer's move
                        board.make_move(computer, computer_next_color);
                        
                        // print the board
                        board.print_terminal_colored();
                    
                    // if not, complain
                    } else {
                        System.out.println("----- Illegal move: " + human_next_color.to_terminal_colored_string(true) + ". -----");
                    }
                }
                
            // resetting the game
            } else if(line_in.equals("reset")) {
                board.reset();
                
                // print the board, and prompt for input
                board.print_terminal_colored();
            
            // printing the score
            } else if(line_in.equals("score")) {
                System.out.println(board.score());
            
            // clarifying
            } else if(line_in.equals("digit")) {
                System.out.println("No, an \033[1mactual\033[m digit, like the prompt is asking.");
            
            // displaying the rules
            } else if(line_in.equals("rules")) {
                print_rules();
            
            // displaying the board
            } else if(line_in.equals("display") || line_in.equals("")) {
                board.print_terminal_colored();
            
            // quitting the game
            } else if(line_in.equals("q") || line_in.equals("quit") || line_in.equals("exit")) {
                System.out.flush();
                return( "OK" );
            
            // complaining
            } else {
                System.out.println("Unrecognized input. I won't reprint it. Look at it yourself.");
            }
            
            // prompt for input
            System.out.println("");
            System.out.print(board.prompt());
            
            if(board.has_winner()) {
                System.out.println("");
                System.out.println("");
                break;
            }
        }
        
        board.print_terminal_colored();
        System.out.println("");
        System.out.println(board.winner());
        System.out.println(board.score());
        System.out.println("");
        
        // exit gracefully
        System.out.flush();
        return( "OK" );
    }
}
