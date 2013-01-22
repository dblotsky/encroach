#ifndef _E_CONTROLLER_
#define _E_CONTROLLER_

#include <string>
#include "E_Model.h"

using std::string;

class E_Controller {
    public:
        E_Controller(E_Model*);
        ~E_Controller();
        
        void new_ai_game(const string& player_name);
        void new_human_game(const string& player_a_name, const string& player_b_name);
        void make_move(const E_Color color);
        void add_player(const string& player_name);
        void add_player();
        
    private:
        E_Model* model;
        
};

#endif
