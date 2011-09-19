#ifndef _E_MODEL_
#define _E_MODEL_

#include <vector>
#include <string>
#include "E_Subject.h"
#include "E_Board.h"
#include "E_Player.h"

using std::vector;
using std::string;

class E_Controller;

class E_Model : public E_Subject {
    public:
        E_Model();
        ~E_Model();
        friend class E_Controller;
        
        E_Color get_node_color(int x, int y) const;
        int get_board_x_size() const;
        int get_board_y_size() const;
        bool game_exists() const;
        
    private:
        E_Board* board;
        vector<E_Player*> players;
        void start_game();
        void end_game();
};

#endif
