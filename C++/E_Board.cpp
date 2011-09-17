#include <vector>
#include <iostream>
#include "E_Reporter.h"
#include "E_Board.h"
#include "E_Node.h"

using std::vector;
using std::cerr;
using std::endl;

E_Board::E_Board(int x_size, int y_size) {
    prologue("E_Board");
    
    // check bounds
    if (x_size < DEFAULT_X_SIZE) {
        cerr << "Cannot have x_size be less than " << DEFAULT_X_SIZE << ". Fixing that for you." << endl;
        x_size = DEFAULT_X_SIZE;
    }
    if (y_size < DEFAULT_Y_SIZE) {
        cerr << "Cannot have y_size be less than " << DEFAULT_Y_SIZE << ". Fixing that for you." << endl;
        y_size = DEFAULT_Y_SIZE;
    }
    
    // assign bounds
    this->x_size = x_size;
    this->y_size = y_size;
    
    // construct the nodes
    for(int x = 0; x < this->x_size; x++) {
        for(int y = 0; y < this->y_size; y++) {
            node_list[x * (this->y_size) + y] = new E_Node();
        }
    }
    
    epilogue("E_Board");
}

E_Board::~E_Board() {
    prologue("E_Board", "~E_Board");
    
    for(int x = 0; x < this->x_size; x++) {
        for(int y = 0; y < this->y_size; y++) {
            delete node_list[x * (this->y_size) + y];
        }
    }
    
    epilogue("E_Board", "~E_Board");
}

int E_Board::get_x_size() const {
    prologue("E_Board", "get_x_size");
    epilogue("E_Board", "get_x_size");
    return x_size;
}

int E_Board::get_y_size() const {
    prologue("E_Board", "get_y_size");
    epilogue("E_Board", "get_y_size");
    return y_size;
}

E_Node* E_Board::get_node_at(int x, int y) const {
    prologue("E_Board", "get_node_at");
    
    if (x < 0 || x >= x_size || y < 0 || y >= y_size) {
        return NULL; // TODO: raise an exception
    }
    
    epilogue("E_Board", "get_node_at");
    return node_list[x * (y_size) + y];
}