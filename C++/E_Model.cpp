#include <vector>
#include <sstream>
#include <iostream>
#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Board.h"
#include "E_Player.h"

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

string E_Model::get_board_string() const {
    prologue("E_Model", "get_board_string");
    
    // output accumulator
    stringstream accumulator;
    
    // get bounds
    int x_bound = board->get_x_size();
    int y_bound = board->get_y_size();
    
    // accumulate nodes
    for (int x = 0; x < x_bound; x++) {
        for (int y = 0; y < y_bound; y++) {
            accumulator << board->get_node_at(x, y)->get_color() << " ";
        }
        accumulator << std::endl;
    }
    
    epilogue("E_Model", "get_board_string");
    return accumulator.str();
}
