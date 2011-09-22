#include <vector>
#include <iostream>
#include "E_Reporter.h"
#include "E_Board.h"
#include "E_Node.h"

using std::vector;
using std::cerr;
using std::endl;

E_Board::E_Board(int width, int height) {
    prologue("E_Board");
    
    // check bounds
    if (width < DEFAULT_X_SIZE) {
        cerr << "Cannot have x_size be less than " << DEFAULT_X_SIZE << ". Fixing that for you." << endl;
        width = DEFAULT_X_SIZE;
    }
    if (height < DEFAULT_Y_SIZE) {
        cerr << "Cannot have y_size be less than " << DEFAULT_Y_SIZE << ". Fixing that for you." << endl;
        height = DEFAULT_Y_SIZE;
    }
    
    // assign bounds
    this->x_size = width;
    this->y_size = height;
    
    this->node_list = new E_Node*[this->x_size * this->y_size];
    
    // construct the nodes
    for(int x = 0; x < this->x_size; x++) {
        for(int y = 0; y < this->y_size; y++) {
            this->node_list[(x * (this->y_size)) + y] = new E_Node();
        }
    }
    
    epilogue("E_Board");
}

E_Board::~E_Board() {
    prologue("E_Board", "~E_Board");
    
    // destroy the nodes
    for(int x = 0; x < this->x_size; x++) {
        for(int y = 0; y < this->y_size; y++) {
            delete this->node_list[(x * (this->y_size)) + y];
        }
    }
    
    delete [] node_list;
    
    epilogue("E_Board", "~E_Board");
}

int E_Board::get_x_size() const {
    prologue("E_Board", "get_x_size");
    
    int return_value = this->x_size;
    
    epilogue("E_Board", "get_x_size");
    return return_value;
}

int E_Board::get_y_size() const {
    prologue("E_Board", "get_y_size");
    
    int return_value = this->y_size;
    
    epilogue("E_Board", "get_y_size");
    return return_value;
}

E_Node* E_Board::get_node_at(int x, int y) const {
    prologue("E_Board", "get_node_at");
    
    if (x < 0 || x >= x_size || y < 0 || y >= y_size) {
        // TODO: raise an exception
        cerr << "Accessed a node out of bounds: [" << x << "][" << y << "]." << endl;
        return NULL;
    }
    
    E_Node* return_value = node_list[x * (y_size) + y];
    
    epilogue("E_Board", "get_node_at");
    return return_value;
}
