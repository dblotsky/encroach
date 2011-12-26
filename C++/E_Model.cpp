#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Game.h"
#include "E_AI.h"
#include <iostream>

using std::vector;
using std::string;
using std::stringstream;

static bool waiting_for_input = false;

// public methods
E_Model::E_Model() {
    prologue("E_Model");
    
    game = NULL;
    ai_player = new E_AI();
    
    epilogue("E_Model");
}

E_Model::~E_Model() {
    prologue("E_Model", "~E_Model");
    
    delete game;
    delete ai_player;
    
    // delete players
    vector<E_Player*>::iterator it;
    for (it = players.begin(); it < players.end(); it++) {
        delete *it;
    }
    
    epilogue("E_Model", "~E_Model");
}

E_Color E_Model::get_color_at(int x, int y) const {
    prologue("E_Model", "get_color_at");
    
    E_Color return_value = game->get_node_at(x, y)->get_color();
    
    epilogue("E_Model", "get_color_at");
    return return_value;
}

int E_Model::get_board_x_size() const {
    prologue("E_Model", "get_board_x_size");
    
    int return_value = game->get_x_size();
    
    epilogue("E_Model", "get_board_x_size");
    return return_value;
}

int E_Model::get_board_y_size() const {
    prologue("E_Model", "get_board_y_size");
    
    int return_value = game->get_y_size();
    
    epilogue("E_Model", "get_board_y_size");
    return return_value;
}

// private methods
void E_Model::add_player(E_Player* player) {
    prologue("E_Model", "add_player");
    
    interlude("player->get_name()", &(player->get_name()), STRING);
    this->players.push_back(player);
    
    epilogue("E_Model", "add_player");
}

E_Player* E_Model::get_player(const string& name) {
    prologue("E_Model", "get_player");
    
    // declare a return pointer
    E_Player* return_value = NULL;
    
    // look though the vector for the player, by name
    vector<E_Player*>::iterator it;
    for (it = players.begin(); it < players.end(); it++) {
        
        // if we find the player, update the return pointer
        if ((*it)->get_name() == name) {
            return_value = *it;
            break;
        }
    }
    
    // if the return value wasn't updated, keep it null
    if (return_value == NULL) {
        return_value = NULL;
    }
    
    epilogue("E_Model", "get_player");
    return return_value;
}

void E_Model::go() {
    prologue("E_Model", "go");
    
    while (!game->over()) {
        
        // get the player that has the turn to move
        E_Player* current_player = game->to_move();
        
        // get the choice of move from the player
        if (current_player->needs_input()) {
            
            if (!waiting_for_input) {
                waiting_for_input = true;
                break;
            } else if (waiting_for_input) {
                waiting_for_input = false;
            }
            
        } else {
            
            // get a move from the player
            game->set_move(current_player->choose_move());
        }
        
        game->make_move();
        notify();
    }
    
    epilogue("E_Model", "go");
}
