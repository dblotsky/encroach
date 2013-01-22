import java.util.*;
import java.math.*;
import java.io.*;

/** The terminal version of Encroach. **/
public class ETerminal {
    
    // running mode flags
    private Boolean HELP_ONLY;
    private Boolean COLOR;
    
    // board settings
    private int NUM_COLORS;
    private int X_SIZE;
    private int Y_SIZE;
    
    // a game board and players
    private EPlayer player_1;
    private EPlayer player_2;
    private EBoard board;
    
    // "constants"
    // TODO: make them ACTUAL constants
    private int MIN_COLORS = 4;
    private int MAX_COLORS = 6;
    
    private int MIN_X = 3;
    private int MAX_X = 100;
    private int MIN_Y = 3;
    private int MAX_Y = 100;
    
    private int MIN_NAME_LENGTH = 1;
    private int MAX_NAME_LENGTH = 20;
    
    private int MIN_AI_LEVEL = 0;
    private int MAX_AI_LEVEL = 1;
    
    /** Makes the ETerminal instance, taking in command line arguments. **/
    public ETerminal(String[] args) {
        
        // defaults
        this.HELP_ONLY = false;
        this.COLOR = false;
        
        this.NUM_COLORS = 6;
        this.X_SIZE = 10;
        this.Y_SIZE = 10;
        
        this.player_1 = new EPlayer("Player 1", 0);
        this.player_2 = new EPlayer("Player 2", 0);
        
        // TODO: do this more neatly, parhaps with an open-source library?
        
        // go through each command-line flag
        for(int i = 0; i < args.length; i++) {
            
            // setting flag
            if(args[i].contains("=")) {
                
                // split the flag into a name part and a value part
                String[] flag = args[i].split("=");
                
                // if it split into exactly two parts...
                if(flag.length == 2) {
                
                    // if the value is an integer, store it; store -1 otherwise
                    int parsed_int = -1;
                    try {
                        parsed_int = Integer.parseInt(flag[1]);
                    } catch (NumberFormatException e) {}
                    
                    // number of colors
                    if(flag[0].matches("--num_colors")) {
                        if(!(parsed_int == -1) && parsed_int >= MIN_COLORS && parsed_int <= MAX_COLORS) {
                            this.NUM_COLORS = parsed_int;
                        } else { System.err.println("ERROR: Invalid number of colors: " + flag[1] + "."); }
                    
                    // 'x' dimension of the board
                    } else if(flag[0].matches("--x_size")) {
                        if(!(parsed_int == -1) && parsed_int >= MIN_X && parsed_int <= MAX_X) {
                            this.X_SIZE = parsed_int;
                        } else { System.err.println("ERROR: Invalid X size: " + flag[1] + "."); }
                        
                    
                    // 'y' dimension of the board
                    } else if(flag[0].matches("--y_size")) {
                        if(!(parsed_int == -1) && parsed_int >= MIN_Y && parsed_int <= MAX_Y) {
                            this.Y_SIZE = parsed_int;
                        } else { System.err.println("ERROR: Invalid Y size: " + flag[1] + "."); }
                    
                    // p1's name
                    } else if(flag[0].matches("--p1_name")) {
                        if(flag[1].length() >= MIN_NAME_LENGTH && flag[1].length() <= MAX_NAME_LENGTH) {
                            player_1.set_name(flag[1]);
                        } else { System.err.println("ERROR: Invalid name for player 1: " + flag[1] + "."); }
                    
                    // p2's name
                    } else if(flag[0].matches("--p2_name")) {
                        if(flag[1].length() >= MIN_NAME_LENGTH && flag[1].length() <= MAX_NAME_LENGTH) {
                            player_2.set_name(flag[1]);
                        } else { System.err.println("ERROR: Invalid name for player 2: " + flag[1] + "."); }
                    
                    // ai level
                    } else if(flag[0].matches("--ai_level")) {
                        if(!(parsed_int == -1) && parsed_int >= MIN_AI_LEVEL && parsed_int <= MAX_AI_LEVEL) {
                            player_1.set_difficulty(parsed_int);
                            player_2.set_difficulty(parsed_int);
                        } else { System.err.println("ERROR: Invalid AI level: " + flag[1] + "."); }
                    
                    // p1's ai level
                    } else if(flag[0].matches("--p1_ai_level")) {
                        if(!(parsed_int == -1) && parsed_int >= MIN_AI_LEVEL && parsed_int <= MAX_AI_LEVEL) {
                            player_1.set_difficulty(parsed_int);
                        } else { System.err.println("ERROR: Invalid AI level for player 1: " + flag[1] + "."); }
                    
                    // p2's ai level
                    } else if(flag[0].matches("--p2_ai_level")) {
                        if(!(parsed_int == -1) && parsed_int >= MIN_AI_LEVEL && parsed_int <= MAX_AI_LEVEL) {
                            player_2.set_difficulty(parsed_int);
                        } else { System.err.println("ERROR: Invalid AI level for player 2: " + flag[1] + "."); }
                    
                    // unrecognized setting
                    } else {
                        System.err.println("ERROR: Unrecognized setting encountered: \"" + flag[0] + "\" - skipping.");
                    }
                
                // complain if the setting flag didn't split nicely
                } else {
                    System.err.println("ERROR: Setting incorrectly formatted: \"" + args[i] + "\" - skipping.");
                }
            
            // help flag
            } else if(is_help_flag(args[i])) {
                this.HELP_ONLY = true;
            
            // color flag
            } else if(args[i].equals("-c") || args[i].equals("--color")) {
                this.COLOR = true;
            
            // terminal flag; do nothing
            } else if(args[i].equals("-t") || args[i].equals("--terminal")) {
            
            // unrecognized flag
            } else {
                System.err.println("ERROR: Unrecognized flag encountered: \"" + args[i] + "\" - skipping.");
            }
        }
        
        // finally, make the board
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
            System.out.println("");
            print_text("help");
            System.out.println("");
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
                    board.play_color(player_2, player_2.ai_next_color_choice(board));
                    print_board();
                } else {
                    System.out.println("Illegal move.");
                }
                
            // reset the board
            } else if(line_in.equals("reset") || line_in.equals("r")) {
                board.reset();
                print_text("new_game");
                System.out.println("");
                print_board();
                
            // print the score
            } else if(line_in.equals("score") || line_in.equals("s")) {
                print_score();
            
            // print the score
            } else if(line_in.equals("winloss") || line_in.equals("w")) {
                print_winloss();
            
            // clarify
            } else if(line_in.equals("single digit")) {
                System.out.println("No - an *actual* digit.");
            
            // enable color
            } else if(line_in.equals("color") || line_in.equals("c")) {
                if(COLOR) {
                    COLOR = false;
                } else {
                    COLOR = true;
                }
                print_board();
            
            // display helpful text
            } else if (
                line_in.equals("rules") ||
                line_in.equals("help") || 
                line_in.equals("h")
            ) {
                print_text("rules");
                
            // for help, print options
            } else if (
                line_in.equals("options") || 
                line_in.equals("o")
            ) {
                print_text("options");
            
            // display the board
            } else if(line_in.equals("display") || line_in.equals("") || line_in.equals("d")) {
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
                
                // get the winner
                EPlayer winner = board.winner();
                
                // in a very cruel manner, fill the board
                /*
                while(!board.filled()) {
                    
                    int color = winner.ai_next_color_choice(board);
                    
                    // wait a bit
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    // if we can't proceed, just exit
                    if(board.mark_conquered_by_move(winner, color, true) == board.mark_conquered_by_move(winner, winner.get_color(), true)) {
                        break;
                    }
                    
                    // make a move
                    board.play_color(winner, color);
                    
                    // print the board
                    System.out.println("");
                    print_board();
                }
                */
                
                // print score and winner
                System.out.println("");
                System.out.println(winner.get_name() + " wins.");
                System.out.println("");
                print_score();
                
                // reset the board for a new game
                board.end_game_and_reset();
                System.out.println("");
                print_winloss();
                System.out.println("");
                print_text("new_game");
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
            System.out.printf("  ");
            for(int j = 0; j < field[i].length; j++) {
                System.out.printf(square_to_string(field[i][j]) + " ");
            }
            System.out.printf("\n");
        }
        return;
    }
    
    /** Prints the number of wins and losses for both players. **/
    public void print_winloss() {
        System.out.println(player_1.get_name() + ":");
        System.out.println("");
        System.out.println("    Won: " + player_1.num_wins() + ", Lost: " + player_1.num_losses() + ".");
        System.out.println("");
        System.out.println(player_2.get_name() + ":");
        System.out.println("");
        System.out.println("    Won: " + player_2.num_wins() + ", Lost: " + player_2.num_losses() + ".");
    }
    
    /** Prints a prompt. **/
    public void print_prompt() {
        String forbidden_color_1 = square_to_string(board.player_1.get_starting_square(), true);
        String forbidden_color_2 = square_to_string(board.player_2.get_starting_square(), true);
        System.out.print("Enter digit within 0 - " + (this.board.num_colors - 1) + " (but not " + forbidden_color_1 + " or " + forbidden_color_2 + "): ");
    }
    
    /** Displays the current score. **/
    public void print_score() {
        System.out.println(board.player_1.get_name() + ": " + Integer.toString(board.player_1.get_score()) + ".");
        System.out.println(board.player_2.get_name() + ": " + Integer.toString(board.player_2.get_score()) + ".");
    }
    
    /** Calls square_to_string() with force_plain set to false. **/
    public String square_to_string(ESquare square) {
        return square_to_string(square, false);
    }
    
    /** Converts the square to a String to be printed. Uses ANSI text effects if COLOR is set to true. **/
    public String square_to_string(ESquare square, Boolean force_plain) { 
        
        // get the color
        int color = square.get_color();
        
        // return plain output if COLOR is set to false, or if plain output is forced
        if(!COLOR || force_plain) { return Integer.toString(color); }
        
        String display_character = "";
        String extra_effects = "";
        
        // apply extra effects
        if(square.border) {
            extra_effects += "\033[1m";
            display_character = "#";
        }
        if(!square.is_owned()) {
            // extra_effects += "\033[2m";
            display_character = Integer.toString(color);
        }
        if(square.is_owned() && !square.border) {
            extra_effects += "\033[40m";
            extra_effects += "\033[7m";
            display_character = Integer.toString(color);
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
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|             Welcome to Encroach!                |");
        } else if(choice.equals("options")) {
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|                                                 |");
            System.out.println("| Available commands:                             |");
            System.out.println("|                                                 |");
            System.out.println("|    h | help | rules  - explains how to play     |");
            System.out.println("|    o | options       - displays this            |");
            System.out.println("|                                                 |");
            System.out.println("|    c | color         - toggles ANSI colors      |");
            System.out.println("|    r | reset         - resets the board         |");
            System.out.println("|    s | score         - shows the score          |");
            System.out.println("|    w | winloss       - shows wins/losses        |");
            System.out.println("|    q | quit | exit   - quits the game           |");
            System.out.println("|                                                 |");
            System.out.println("|    d | display       - displays the board       |");
            System.out.println("|    [ENTER]           - (same as above)          |");
            System.out.println("|                                                 |");
            System.out.println("|    single digit      - makes a move             |");
            System.out.println("|                                                 |");
            System.out.println("| NOTE: To run in color, you MUST be using a      |");
            System.out.println("|       terminal that supports ANSI escape codes  |");
            System.out.println("|       for colored text. If they're not          |");
            System.out.println("|       supported, you will see ugly things.      |");
            System.out.println("|                                                 |");
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
        } else if(choice.equals("help")) {
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|                Encroach on Java                 |");
            System.out.println("|                                                 |");
            System.out.println("|                March 15th, 2011                 |");
            System.out.println("|                                                 |");
            System.out.println("|   Written by Ryan Bateman and Dmitry Blotsky    |");
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|                                                 |");
            System.out.println("| Running the game:                               |");
            System.out.println("|                                                 |");
            System.out.println("|    The game works in a GUI or a terminal UI. To |");
            System.out.println("| run in terminal, simply double-click it. To run |");
            System.out.println("| in GUI, pass it the '--gui' or the '-g' flag.   |");
            System.out.println("|                                                 |");
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|                                                 |");
            System.out.println("| Available flags:                                |");
            System.out.println("|                                                 |");
            System.out.println("|    -c | --color                                 |");
            System.out.println("|    -g | --gui                                   |");
            System.out.println("|                                                 |");
            System.out.println("| Available settings \"--[name]=[value]\":          |");
            System.out.println("|                                                 |");
            System.out.println("|    names         values                         |");
            System.out.println("|    -----         ------                         |");
            System.out.println("|                                                 |");
            System.out.println("|    num_colors    integer between " + MIN_COLORS + " and " + MAX_COLORS + "        |");
            System.out.println("|    x_size        integer between " + MIN_X + " and " + MAX_X + "      |");
            System.out.println("|    y_size        integer between " + MIN_Y + " and " + MAX_Y + "      |");
            System.out.println("|                                                 |");
            System.out.println("|    ai_level      integer between " + MIN_AI_LEVEL + " and " + MAX_AI_LEVEL + "        |");
            System.out.println("|    p1_ai_level   (same as above)                |");
            System.out.println("|    p2_ai_level   (same as above)                |");
            System.out.println("|                                                 |");
            System.out.println("|    p1_name       string of " + MIN_NAME_LENGTH + " to " + MAX_NAME_LENGTH + " characters   |");
            System.out.println("|    p2_name       (same as above)                |");
            System.out.println("|                                                 |");
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
        } else if(choice.equals("rules")) {
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|                                                 |");
            System.out.println("| Rules:                                          |");
            System.out.println("|                                                 |");
            System.out.println("|    The point of the game is to encroach upon    |");
            System.out.println("| more squares than your opponent. You own the    |");
            System.out.println("| square on which you start (TOP LEFT), and your  |");
            System.out.println("| opponent owns the square on which they start    |");
            System.out.println("| (BOTTOM RIGHT).                                 |");
            System.out.println("|                                                 |");
            System.out.println("| You may encroach upon more squares by changing  |");
            System.out.println("| color, thus connecting yourself to more         |");
            System.out.println("| squares. To change color, simply enter its      |");
            System.out.println("| number. You may NOT pick YOUR OPPONENT'S color  |");
            System.out.println("| or YOUR CURRENT color.                          |");
            System.out.println("|                                                 |");
            System.out.println("| The game ends when either player owns more than |");
            System.out.println("| HALF of the squares on the board. You may see   |");
            System.out.println("| the current square ownership by typing \"s\" or   |");
            System.out.println("| \"score\".                                        |");
            System.out.println("|                                                 |");
            System.out.println("| To see more options, type \"o\" or \"options\".     |");
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
        } else if(choice.equals("new_game")) {
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
            System.out.println("|                    New Game                     |");
            System.out.println("+ - - - - - - - - - - - - - - - - - - - - - - - - +");
        }
        return;
    }
}
