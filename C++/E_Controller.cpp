#include <cassert>
#include "E_Reporter.h"
#include "E_Controller.h"
#include "E_Model.h"
#include "E_Human.h"
#include "E_AI.h"

E_Controller::E_Controller(E_Model* model) {
    prologue("E_Controller");
    
    this->model = model;
    
    epilogue("E_Controller");
}

E_Controller::~E_Controller() {
    prologue("E_Controller", "~E_Controller");
    epilogue("E_Controller", "~E_Controller");
}

void E_Controller::new_ai_game(const string& player_name) {
    prologue("E_Controller", "new_ai_game");
    
    // get AI player
    E_Player* ai_player = this->model->ai_player;
    interlude("ai_player->get_name()", &(ai_player->get_name()), STRING);
    
    // get human player
    E_Player* human_player = this->model->get_player(player_name);
    assert(human_player != NULL);
    interlude("human_player->get_name()", &(human_player->get_name()), STRING);
    
    if (this->model->game != NULL) {
        delete this->model->game;
    }
    
    // make the game
    this->model->game = new E_Game(human_player, ai_player);
    this->model->go();
    
    epilogue("E_Controller", "new_ai_game");
}

void E_Controller::new_human_game(const string& player_a_name, const string& player_b_name) {
    prologue("E_Controller", "new_human_game");
    epilogue("E_Controller", "new_human_game");
}

void E_Controller::make_move(const E_Color color) {
    prologue("E_Controller", "make_move");
    
    if (model->game == NULL) {
        return;
    }
    
    model->game->set_move(color);
    model->go();
    epilogue("E_Controller", "make_move");
}

void E_Controller::add_player(const string& player_name) {
    prologue("E_Controller", "add_player");
    
    E_Player* new_player = new E_Human(player_name);
    model->add_player(new_player);
    
    epilogue("E_Controller", "add_player");
}

void E_Controller::add_player() {
    prologue("E_Controller", "add_player");
    
    E_Player* new_player = new E_Human();
    model->add_player(new_player);
    
    epilogue("E_Controller", "add_player");
}
