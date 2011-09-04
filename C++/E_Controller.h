#ifndef _E_CONTROLLER_
#define _E_CONTROLLER_

#include <string>
#include "E_Model.h"

using std::string;

class E_Controller {
    public:
        E_Controller(E_Model*);
        ~E_Controller();
        
        // events
        void new_ai_game(const string& player_name);
        void new_human_game(const string& player1_name, const string& player2_name);
        void event_move(const string& command_string);
        
    private:
        E_Model* model;
        
};

#endif
