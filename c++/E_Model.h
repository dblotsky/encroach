#ifndef _E_MODEL_
#define _E_MODEL_

#include <vector>
#include <string>
#include "Subject.h"
#include "E_Game.h"
#include "E_Player.h"

using std::vector;
using std::string;

class E_Controller;

class E_Model: public Subject {
    public:
        E_Model();
        ~E_Model();
        friend class E_Controller;
        
        E_Color get_color_at(int x, int y) const;
        int get_board_x_size() const;
        int get_board_y_size() const;
        
    private:
        E_Game* game;
        E_Player* ai_player;
        vector<E_Player*> players;
        
        void go();
        E_Player* get_player(const string& name);
        void add_player(E_Player* player);
        
};

#endif
