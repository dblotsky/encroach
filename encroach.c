#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define ROWS 20
#define COLUMNS 70
#define MAX_TRIES 200
#define MAX_COLORS 6
#define HUMAN 1

// prints the current state of the board
void print_board(int board[ROWS][COLUMNS]) {
	int i, j;
	for(i = 0; i < ROWS; i++) {
		for(j = 0; j < COLUMNS; j++) {
			printf(" %d", (board[i][j]/* + 97*/));
		}
		printf("\n");
	}
	return;
}

void initialize_board(int board[ROWS][COLUMNS]) {

    // set up random number generator
    int seed = (int)time(NULL);
    srand(seed);

    int i, j;
    for(i = 0; i < ROWS; i++) {
		for(j = 0; j < COLUMNS; j++) {
			board[i][j] = (int)(rand() % MAX_COLORS);
		}
	}
	return;
}

// recurses through all cells (connected to home) of the previous color, and paints them the next color
int move_helper(int prev_color, int next_color, int board[ROWS][COLUMNS], int visited[ROWS][COLUMNS], int r, int c, int* colors) {
    
    // set up return values
    int top = 0;
    int right = 0;
    int bottom = 0;
    int left = 0;

    // make this square visited
    visited[r][c] = 1;
    
    // bail if we didn't own this cell last turn
    if(board[r][c] != prev_color) {
        colors[board[r][c]]++;
        return 0;
    }
    
    // now, safely recurse through all neighbors
    if(r != 0) {
        if(!visited[r - 1][c]) {
            top = move_helper(prev_color, next_color, board, visited, (r - 1), c, colors);
        }
    }
    if(c != 0) {
        if(!visited[r][c - 1]) {
            left = move_helper(prev_color, next_color, board, visited, r, (c - 1), colors);
        }
    }
    if(r != (ROWS - 1)) {
        if(!visited[r + 1][c]) {
            bottom = move_helper(prev_color, next_color, board, visited, (r + 1), c, colors);
        }
    }
    if(c != (COLUMNS - 1)) {
        if(!visited[r][c + 1]) {
            right = move_helper(prev_color, next_color, board, visited, r, (c + 1), colors);
        }
    }
    
    // switch the cell's color
    board[r][c] = next_color;
    
    return top + right + bottom + left + 1;
}

int make_move(int next_color, int board[ROWS][COLUMNS], int* colors) {

    // grid for tracking visited cells
    int visited[ROWS][COLUMNS] = {0};
    
    // recurse through its neighbors
    return move_helper(board[0][0], next_color, board, visited, 0, 0, colors);
}

int won(int board[ROWS][COLUMNS]) {
    int i, j, fill = board[0][0];
	for(i = 0; i < ROWS; i++) {
		for(j = 0; j < COLUMNS; j++) {
			if(board[i][j] != fill) {
                return 0;
            }
		}
	}
	return 1;
}

int print_most_gain(int* colors, int board[ROWS][COLUMNS]) {
    make_move(board[0][0], board, colors);
    int i, j, highest = colors[0];
    int best_choice = 0;
	for(i = 0; i < MAX_COLORS; i++) {
        if(colors[i] >= highest) {
            highest = colors[i];
            best_choice = i;
        }
	}
	printf("The best next move is %d.\n", best_choice);
    return best_choice;
}

int main() {
    
    if(COLUMNS < 3 || ROWS < 3) {
        printf("The game field must have 3 or more rows and 3 or more columns.");
        return 0;
    }

	// define the playing field
	int board[ROWS][COLUMNS] = {0};
	initialize_board(board);
    
    // the color played by the player
    int next_color = 0;
    
    // count how many tries it takes to win
    int tries = 0;

	// game state:
	// 1 - in progress
	// 0 - lost
	// 2 - won
	int game_state = 1;
    
    int most_gain = 0;

	// the game's run loop
	while(game_state == 1) { // while the game is in progress...
        tries++;
        
        // histogram of most profitable move each turn
        int colors[MAX_COLORS] = {0};
        
		// print the game board
		print_board(board);

		if(HUMAN) {
            printf("Enter a number between 0 and %d: ", MAX_COLORS);
            // get and echo the player's input; bail or try again if it is invalid (depending on HOW invalid)
            do {
		        if(!scanf("%d", &next_color)) {
                    return 0;
                }
            } while(!(next_color < MAX_COLORS && next_color >= 0));
        } else {
            next_color = most_gain;
        }
        printf("You played: '%d'.\n", next_color);

		// calculate the board's next state based on the player's move
		make_move(next_color, board, colors);
        
        // print the best next move
        most_gain = print_most_gain(colors, board);
        
        // see if we've won
        if(won(board)) {
            game_state = 2;
            break;
        }
        
        // if it takes more than MAX_TRIES tries, a loss is marked
        if(tries > MAX_TRIES) {
            game_state = 0;
            break;
        }
	}
    
    // display endgame message
	if(game_state == 0) {
        print_board(board);
		printf("\nYou lost...\n");
	} else if(game_state == 2) {
        print_board(board);
		printf("\nYou won in %d tries!\n", tries);
	} else {
        printf("\nGet. Out.\n");
    }

	return 0;
}
