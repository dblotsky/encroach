import java.util.*;
import java.math.*;
import java.io.*;

/** The terminal version of Encroach. **/
public class ETerminal {
    
    // running mode flags
    private Boolean HELP_ONLY;
    private Boolean COLOR;
    
    // "constants"
    // TODO: make them ACTUAL constants
    private int NUM_COLORS;
    private int X_SIZE;
    private int Y_SIZE;
    
    // a game board and players
    private EPlayer player_1;
    private EPlayer player_2;
    private EBoard board;
    
    /** Makes the ETerminal instance, taking in command line arguments. **/
    public ETerminal(String[] args) {
        
        // defaults
        this.HELP_ONLY = false;
        this.COLOR = false;
        this.NUM_COLORS  = 6;
        this.X_SIZE = 20;
        this.Y_SIZE = 20;
        this.player_1 = new EPlayer("Player 1", 0);
        this.player_2 = new EPlayer("Player 2", 1);
        
        // TODO: make a regex or something to accept customizations to the above attributes
        
        this.board = new EBoard(this.NUM_COLORS, this.X_SIZE, this.Y_SIZE);
    }
    
    /** Returns true if the flag is contained in the array of acceptable help flags. **/
    public Boolean is_help_flag(String flag) {
        String[] help_flags = {"help", "-help", "-h", "--help", "-?", "/?", "h", "/h", "/help", "?"};
        for(int i = 0; i < help_flags.length; i++) {
            if(help_flags[i].equals(flag)) {
                return true;
            }
        }
        return false;
    }
    
    /** The main run loop of ETerminal. **/
    public String run(InputStream input) {
        
        // display help and exit if HELP_ONLY was set
        if(HELP_ONLY) {
            print_text("help");
            System.out.flush();
            return( "OK" );
        }
        
        // set up an input scanner
        Scanner in = new Scanner(input);
        
        // initialize the board
        board.initialize(player_1, player_2);
        
        // print a welcome message, the board, and a prompt for input
        System.out.println("");
        print_text("welcome");
        print_text("options");
        System.out.println("");
        print_board();
        System.out.println("");
        print_prompt();
        
        // run the game
        while(in.hasNextLine()) {
            
            // get player's input
            String line_in = in.nextLine();
            System.out.println("");
            
            // act on the input:
            // make a move
            if(line_in.matches("^[0-9]$")) {
                int color = Integer.parseInt(line_in);
                if(board.can_play(player_1, color)) {
                    board.play_color(player_1, color);
                    board.play_color(player_2, player_2.ai_next_color_choice(this.board));
                    print_board();
                } else {
                    System.out.println("Illegal move.");
                }
                
            // reset the board
            } else if(line_in.equals("reset")) {
                board.reset();
                print_board();
                
            // print the score
            } else if(line_in.equals("score")) {
                print_score();
            
            // print the score
            } else if(line_in.equals("winloss")) {
                print_winloss();
            
            // clarify
            } else if(line_in.equals("single digit")) {
                System.out.println("No - an *actual* digit.");
            
            // enable color
            } else if(line_in.equals("color")) {
                if(COLOR) {
                    COLOR = false;
                } else {
                    COLOR = true;
                }
                print_board();
            
            // display helpful text
            } else if(line_in.equals("rules") || line_in.equals("options") || line_in.equals("howto") || line_in.equals("help")) {
                print_text(line_in);
            
            // display the board
            } else if(line_in.equals("display") || line_in.equals("")) {
                print_board();
            
            // quit the game
            } else if(line_in.equals("q") || line_in.equals("quit") || line_in.equals("exit")) {
                System.out.flush();
                return( "OK" );
            
            // complain
            } else {
                System.out.println("Unrecognized input.");
            }
            
            // check for game's end
            if(board.winner_exists()) {
                
                // print score and winner
                System.out.println("");
                System.out.println(board.winner().get_name() + " wins.");
                print_score();
                
                // reset the board for a new game
                board.end_game_and_reset();
                print_winloss();
                System.out.println("");
                print_board();
            }
            
            // prompt for input
            System.out.println("");
            print_prompt();
        }
        
        // exit gracefully
        System.out.flush();
        return( "OK" );
    }
    
    /** Prints the board. **/
    public void print_board() {
        ESquare[][] field = this.board.get_field();
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field[i].length; j++) {
                System.out.printf(square_to_string(field[i][j]) + " ");
            }
            System.out.printf("\n");
        }
        return;
    }
    
    /** Prints the number of wins and losses for both players. **/
    public void print_winloss() {
        System.out.println(player_1.get_name() + " - Won: " + player_1.num_wins() + ", Lost: " + player_1.num_losses() + ".");
        System.out.println(player_2.get_name() + " - Won: " + player_2.num_wins() + ", Lost: " + player_2.num_losses() + ".");
    }
    
    /** Prints a prompt. **/
    public void print_prompt() {
        String forbidden_color_1 = square_to_string(board.player_1.get_starting_square(), true);
        String forbidden_color_2 = square_to_string(board.player_2.get_starting_square(), true);
        System.out.print("Enter digit within 0 - " + (this.board.num_colors - 1) + " (but not " + forbidden_color_1 + " or " + forbidden_color_2 + "): ");
    }
    
    /** Displays the current score. **/
    public void print_score() {
        System.out.println(board.player_1.get_name() + ": " + Integer.toString(board.player_1.get_score()) + ", " + board.player_2.get_name() + ": " + Integer.toString(board.player_2.get_score()) + ".");
    }
    
    /** Calls square_to_string() with force_plain set to false. **/
    public String square_to_string(ESquare square) {
        return square_to_string(square, false);
    }
    
    /** Converts the square to a String to be printed. Uses ANSI text effects if COLOR is set to true. **/
    public String square_to_string(ESquare square, Boolean force_plain) { 
        
        // get the color
        int color = square.get_color();
        
        // return plain output if COLOR is set to false
        if(!COLOR || force_plain) { return Integer.toString(color); }
        
        String display_character = "";
        String extra_effects = "";
        
        // apply extra effects
        if(square.border) {
            extra_effects += "\033[1m";
            display_character = "#";
        }
        if(square.get_owner() != board.player_1 && square.get_owner() != board.player_2) {
            // extra_effects += "\033[2m";
            display_character = Integer.toString(color);
        }
        if((square.get_owner() == board.player_1 || square.get_owner() == board.player_2) && !square.border) {
            extra_effects += "\033[47m";
            extra_effects += "\033[7m";
            display_character = " ";
        }
        
        // return colorful output if COLOR is set to true
        if(color == 0) {
            return extra_effects + "\033[31m" + display_character + "\033[m";
        } else if(color == 1) {
            return extra_effects + "\033[32m" + display_character + "\033[m";
        } else if(color == 2) {
            return extra_effects + "\033[33m" + display_character + "\033[m";
        } else if(color == 3) {
            return extra_effects + "\033[34m" + display_character + "\033[m";
        } else if(color == 4) {
            return extra_effects + "\033[35m" + display_character + "\033[m";
        } else if(color == 5) {
            return extra_effects + "\033[36m" + display_character + "\033[m";
        }
        return display_character;
    }
    
    /** Prints various helful text. **/
    public void print_text(String choice) {
        if(choice.equals("welcome")) {
            System.out.println("--------------------------------------------");
            System.out.println("            Welcome to Encroach!            ");
        } else if(choice.equals("options")) {
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("Available options:");
            System.out.println("");
            System.out.println("    reset         - resets the board");
            System.out.println("    q|quit|exit   - quits the game");
            System.out.println("");
            System.out.println("    single digit  - makes a move");
            System.out.println("    score         - shows the score");
            System.out.println("    winloss       - shows wins/losses");
            System.out.println("    display       - displays the board");
            System.out.println("    [ENTER]       - also displays the board");
            System.out.println("    color         - toggles ANSI effects");
            System.out.println("");
            System.out.println("    rules         - prints the rules");
            System.out.println("    options       - re-prints these options");
            System.out.println("    help          - shows the help text");
            System.out.println("    howto         - explains how to play");
            System.out.println("");
            System.out.println(" NOTE: To run in color, you MUST be using a");
            System.out.println("       terminal that supports ANSI escape");
            System.out.println("       codes for colored text. If they're");
            System.out.println("       not supported, you will see some");
            System.out.println("       funky stuff.");
            System.out.println("");
            System.out.println("--------------------------------------------");
        } else if(choice.equals("help")) {
            System.out.println("--------------------------------------------");
            System.out.println("              Encroach on Java              ");
            System.out.println("");
            System.out.println("              March 15th, 2011              ");
            System.out.println("");
            System.out.println(" Written by Ryan Bateman and Dmitry Blotsky ");
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("Running the game:");
            System.out.println("");
            System.out.println(" The game works in a GUI or a terminal UI.");
            System.out.println(" To run in GUI, simply double-click it, or");
            System.out.println(" run from command line. To run in terminal,");
            System.out.println(" run from command line, and pass it the");
            System.out.println(" 'terminal' flag.");
            /*System.out.println("");
            System.out.println(" Available terminal options are (after the");
            System.out.println(" 'terminal' flag):");
            System.out.println("");
            System.out.println("    --color");
            System.out.println("    --num_colors");
            System.out.println("    --x_size");
            System.out.println("    --y_size");*/
            System.out.println("");
            System.out.println("--------------------------------------------");
        } else if(choice.equals("rules")) {
            System.out.println("--------------------------------------------");
            System.out.println("");
            System.out.println("Rules:");
            System.out.println("");
            System.out.println("    - YOU start in TOP LEFT corner");
            System.out.println("    - OPPONENT starts in BOTTOM RIGHT corner");
            System.out.println("    - you CAN'T choose the opponent's color");
            System.out.println("    - you CAN'T choose your own color");
            System.out.println("    - game ends when NO SQUARES are NEUTRAL");
            System.out.println("");
            System.out.println("--------------------------------------------");
        } else if(choice.equals("howto")) {
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
}
