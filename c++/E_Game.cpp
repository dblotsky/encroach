#include <vector>
#include <iostream>
#include "E_Reporter.h"
#include "E_Game.h"
#include "E_Node.h"
#include "E_Color.h"

using std::vector;
using std::cerr;
using std::endl;

E_Game::E_Game(E_Player* player_a, E_Player* player_b, int width, int height) {
    prologue("E_Game");
    
    // check bounds
    if (width < DEFAULT_X_SIZE) {
        cerr << "Cannot have x_size be less than " << DEFAULT_X_SIZE << ". Fixing that for you." << endl;
        width = DEFAULT_X_SIZE;
    }
    if (height < DEFAULT_Y_SIZE) {
        cerr << "Cannot have y_size be less than " << DEFAULT_Y_SIZE << ". Fixing that for you." << endl;
        height = DEFAULT_Y_SIZE;
    }
    
    // assign board dimensions
    this->x_size = width;
    this->y_size = height;
    
    // allocate space for the board
    this->board = new E_Node*[this->x_size * this->y_size];
    
    // construct the board
    for(int x = 0; x < this->x_size; x++) {
        for(int y = 0; y < this->y_size; y++) {
            this->board[(x * (this->y_size)) + y] = new E_Node();
        }
    }
    
    // assign players
    this->player_a = player_a;
    this->player_b = player_b;
    
    interlude("player_a->get_name()", &(player_a->get_name()), STRING);
    interlude("player_b->get_name()", &(player_b->get_name()), STRING);
    
    this->a_to_move = true;
    this->current_move = BLANK;
    
    first_node()->set_owner(player_a);
    last_node()->set_owner(player_b);
    
    epilogue("E_Game");
}

E_Game::~E_Game() {
    prologue("E_Game", "~E_Game");
    
    // destroy the nodes
    for(int x = 0; x < this->x_size; x++) {
        for(int y = 0; y < this->y_size; y++) {
            delete this->board[(x * (this->y_size)) + y];
        }
    }
    
    // destroy the array
    delete [] this->board;
    
    // release the payers
    this->player_a = NULL;
    this->player_b = NULL;
    
    epilogue("E_Game", "~E_Game");
}

E_Node* const E_Game::first_node() const {
    prologue("E_Game", "first_node");
    
    E_Node* return_value = this->get_node_at(0, 0);
    
    epilogue("E_Game", "first_node");
    return return_value;
}

E_Node* const E_Game::last_node() const {
    prologue("E_Game", "last_node");
    
    int last_x = this->get_x_size() - 1;
    int last_y = this->get_y_size() - 1;
    E_Node* return_value = this->get_node_at(last_x, last_y);
    
    epilogue("E_Game", "last_node");
    return return_value;
}

int E_Game::get_x_size() const {
    prologue("E_Game", "get_x_size");
    
    int return_value = this->x_size;
    
    epilogue("E_Game", "get_x_size");
    return return_value;
}

int E_Game::get_y_size() const {
    prologue("E_Game", "get_y_size");
    
    int return_value = this->y_size;
    
    epilogue("E_Game", "get_y_size");
    return return_value;
}

E_Node* const E_Game::get_node_at(int x, int y) const {
    prologue("E_Game", "get_node_at");
    
    // check bounds
    if (x < 0 || x >= x_size || y < 0 || y >= y_size) {
        // TODO: raise an exception
        cerr << "Accessed a node out of bounds: [" << x << "][" << y << "]." << endl;
        return NULL;
    }
    
    E_Node* return_value = board[x * (y_size) + y];
    
    epilogue("E_Game", "get_node_at");
    return return_value;
}

bool E_Game::over() {
    prologue("E_Game", "over");
    epilogue("E_Game", "over");
    return false;
}

E_Player* E_Game::to_move() {
    prologue("E_Game", "to_move");
    
    E_Player* current_player = NULL;
    
    if (a_to_move) {
        current_player = player_a;
    } else {
        current_player = player_b;
    }
    
    epilogue("E_Game", "to_move");
    return current_player;
}

void E_Game::set_move(E_Color new_move) {
    prologue("E_Game", "set_move");
    current_move = new_move;
    epilogue("E_Game", "set_move");
}

void E_Game::toggle_move() {
    prologue("E_Game", "toggle_move");
    if (a_to_move) {
        a_to_move = false;
    } else {
        a_to_move = true;
    }
    epilogue("E_Game", "toggle_move");
}

void E_Game::make_move() {
    prologue("E_Game", "make_move");
    
    E_Owner* current_player = to_move();
    
    for(int x = 0; x < x_size; x++) {
        for(int y = 0; y < y_size; y++) {
            
            E_Node* current_node = get_node_at(x, y);
            
            if (current_node->get_owner() == current_player) {
                current_node->set_color(current_move);
            }
        }
    }
    
    toggle_move();
    
    epilogue("E_Game", "make_move");
}
