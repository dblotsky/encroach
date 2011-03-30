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
	
	/// Prints the current state of the game board.
	public void print_board(EBoard board, Boolean colored_output) {
		if(colored_output) {
			board.print_terminal_colored();
		} else {
			board.print_simple();
		}
	}
	
	/// Prints a prompt for input.
	public void print_prompt(EBoard board, Boolean colored_output) {
		if(colored_output) {
			System.out.print(board.prompt_terminal_colored());
		} else {
			System.out.print(board.prompt());
		}
	}
    
    /// Prints various helful text.
    public void print_text(String text) {
        if(text.equals("options")) {
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("Available options:");
            System.out.println("");
            System.out.println("    reset         - resets the board");
            System.out.println("    q|quit|exit   - quits the game");
            System.out.println("");
            System.out.println("    single digit  - makes a move");
            System.out.println("    score         - shows the score");
            System.out.println("    display       - displays the board");
            System.out.println("    [ENTER]       - also displays the board");
            System.out.println("");
            System.out.println("    rules         - prints the rules");
            System.out.println("    options       - re-prints these options");
            System.out.println("    help          - shows the help text");
            System.out.println("    howto         - explains how to play");
            System.out.println("");
            System.out.println("--------------------------------------------");
        } else if(text.equals("help")) {
            System.out.println("--------------------------------------------");
            System.out.println("                  Encroach                  ");
            System.out.println("");
            System.out.println("              March 15th, 2011              ");
            System.out.println("");
            System.out.println(" Written by Ryan Bateman and Dmitry Blotsky ");
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("Running the game:");
            System.out.println("");
            System.out.println(" Run the game as you already did, and pass");
            System.out.println(" it one of the option combinations below:");
            System.out.println("");
            System.out.println("    color");
            System.out.println("    color [x_value] [y_value]");
            System.out.println("    [x_valye] [y_value]");
            System.out.println("");
            System.out.println(" Replace the [y_value] and [x_value] with a");
            System.out.println(" number between 5 and 50 (inclusive).");
            System.out.println("");
            System.out.println("");
            System.out.println(" NOTE: To run in color, you MUST be using a");
            System.out.println("       terminal that supports ANSI escape");
            System.out.println("       codes for colored text. If they're");
            System.out.println("       not supported, you will see some");
            System.out.println("       funky stuff.");
            System.out.println("");
            System.out.println("--------------------------------------------");
        } else if(text.equals("rules")) {
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("Rules:");
            System.out.println("");
            System.out.println("    - YOU start in TOP LEFT corner");
            System.out.println("    - OPPONENT starts in BOTTOM RIGHT corner");
            System.out.println("    - you CAN'T choose the opponent's color");
            System.out.println("    - game ends when NO SQUARES are NEUTRAL");
            System.out.println("");
            System.out.println("--------------------------------------------");
        } else if(text.equals("howto")) {
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("How to play:");
            System.out.println("");
            System.out.println("    The point of the game is to encroach");
            System.out.println(" upon more squares than your opponent. You");
            System.out.println(" own the square on which you start (top");
            System.out.println(" left), and all the squares that are");
            System.out.println(" connected to it - above, to the left, to");
            System.out.println(" the right, and below.");
            System.out.println("");
            System.out.println(" You may encroach upon more squares by");
            System.out.println(" changing color, thus connecting yourself");
            System.out.println(" to more squares.");
            System.out.println("");
            System.out.println("--------------------------------------------");
        }
        return;
    }
    
    /// The main run loop of EBasicRunner.
    private String run(InputStream input, String[] args) {
        
        try {
            if(
                args[0].equals("help") || 
                args[0].equals("-help") || 
                args[0].equals("-h") || 
                args[0].equals("--help") || 
                args[0].equals("-?") || 
                args[0].equals("/?") || 
                args[0].equals("h") || 
                args[0].equals("/h") || 
                args[0].equals("/help") || 
                args[0].equals("?")
            ) {
                print_text("help");
                System.out.flush();
                return( "OK" );
            }
        } catch(Exception e) {}
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        Boolean COLOR = false;
		try {
			if(args[0].equals("color")) {
				COLOR = true;
			}
		} catch(Exception e) {}
        
        // 'constants'
        int NUM_COLORS  = 6;
        int X_DIMENSION = 0;
        int Y_DIMENSION = 0;
        
        try {
            if(COLOR) {
                X_DIMENSION = Integer.parseInt(args[1]);
                Y_DIMENSION = Integer.parseInt(args[2]);
            } else {
                X_DIMENSION = Integer.parseInt(args[0]);
                Y_DIMENSION = Integer.parseInt(args[1]);
            }
        } catch(Exception e) {
            X_DIMENSION = 20;
            Y_DIMENSION = 20;
        }

        // largest value for both X and Y is 50
        if((X_DIMENSION < 5 || Y_DIMENSION < 5) || (X_DIMENSION > 50 || Y_DIMENSION > 50)) {
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
        System.out.println("--------------------------------------------");
        System.out.println("            Welcome to Encroach!            ");
        print_text("options");
        System.out.println("");
        
        // print the board, and prompt for input
        print_board(board, COLOR);
        System.out.println("");
        print_prompt(board, COLOR);
        
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
                        print_board(board, COLOR);
                    
                    // if not, complain
                    } else {
                        if(COLOR) {
                            System.out.println("------------- Illegal move: " + human_next_color.to_terminal_colored_string(true) + ". -------------");
                        } else {
                            System.out.println("------------- Illegal move: " + human_next_color.to_string() + ". -------------");
                        }
                    }
                }
                
            // resetting the game
            } else if(line_in.equals("reset")) {
                board.reset();
                
                // print the board, and prompt for input
                print_board(board, COLOR);
            
            // printing the score
            } else if(line_in.equals("score")) {
                System.out.println(board.score());
            
            // clarifying
            } else if(line_in.equals("single digit")) {
                System.out.println("No - an *actual* digit.");
            
            // displaying the rules
            } else if(
                line_in.equals("rules") || 
                line_in.equals("options") ||
                line_in.equals("howto") ||
                line_in.equals("help")
            ) {
                print_text(line_in);
            
            // displaying the board
            } else if(line_in.equals("display") || line_in.equals("")) {
                print_board(board, COLOR);
            
            // quitting the game
            } else if(line_in.equals("q") || line_in.equals("quit") || line_in.equals("exit")) {
                System.out.flush();
                return( "OK" );
            
            // complaining
            } else {
                System.out.println("Unrecognized input.");
            }
            
            // prompt for input
            System.out.println("");
            print_prompt(board, COLOR);
            
            if(board.has_winner()) {
                System.out.println("");
                System.out.println("");
                break;
            }
        }
        
        print_board(board, COLOR);
        System.out.println("");
        System.out.println(board.winner());
        System.out.println(board.score());
        System.out.println("");
        
        // exit gracefully
        System.out.flush();
        return( "OK" );
    }
}
