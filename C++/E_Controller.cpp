#include "E_Reporter.h"

#include "E_Controller.h"
#include "E_Model.h"

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
    epilogue("E_Controller", "new_ai_game");
}

void E_Controller::new_human_game(const string& player1_name, const string& player2_name) {
    prologue("E_Controller", "new_human_game");
    epilogue("E_Controller", "new_human_game");
}

void E_Controller::register_player(const string& player_name) {
    prologue("E_Controller", "register_player");
    epilogue("E_Controller", "register_player");
}

void E_Controller::event_move(const string& command_string) {
    prologue("E_Controller", "event_move");
    epilogue("E_Controller", "event_move");
}