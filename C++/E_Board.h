#ifndef _E_BOARD_
#define _E_BOARD_

#include "E_Node.h"

// constants
#define DEFAULT_X_SIZE 4
#define DEFAULT_Y_SIZE 4

class E_Board {
    public:
        E_Board(int x_size=DEFAULT_X_SIZE, int y_size=DEFAULT_Y_SIZE);
        ~E_Board();
        int get_x_size() const;
        int get_y_size() const;
        E_Node* get_node_at(int x, int y) const;
        
    private:
        int x_size;
        int y_size;
        E_Node* node_list[];
        
};

#endif
