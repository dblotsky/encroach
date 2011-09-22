#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Board.h"

using std::vector;
using std::string;
using std::stringstream;

E_Model::E_Model() {
    prologue("E_Model");
    
    board = NULL;
    
    epilogue("E_Model");
}

E_Model::~E_Model() {
    prologue("E_Model", "~E_Model");
    
    delete board;
    
    epilogue("E_Model", "~E_Model");
}

void E_Model::start_game() {
    prologue("E_Model", "start_game");
    
    board = new E_Board();
    
    epilogue("E_Model", "start_game");
    return;
}

void E_Model::end_game() {
    prologue("E_Model", "end_game");
    
    delete board;
    
    epilogue("E_Model", "end_game");
    return;
}

E_Color E_Model::get_color_at(int x, int y) const {
    prologue("E_Model", "get_color_at");
    
    E_Color return_value = board->get_node_at(x, y)->get_color();
    
    epilogue("E_Model", "get_color_at");
    return return_value;
}

int E_Model::get_board_x_size() const {
    prologue("E_Model", "get_board_x_size");
    
    int return_value = board->get_x_size();
    
    epilogue("E_Model", "get_board_x_size");
    return return_value;
}

int E_Model::get_board_y_size() const {
    prologue("E_Model", "get_board_y_size");
    
    int return_value = board->get_y_size();
    
    epilogue("E_Model", "get_board_y_size");
    return return_value;
}

bool E_Model::game_exists() const {
    if (board == NULL) {  
        return false; 
    }
    return true;
}
