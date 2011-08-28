#ifndef _E_CONTROLLER_
#define _E_CONTROLLER_

#include "E_Model.h"
#include "E_Card.h"
#include "E_Player.h"

class E_Controller {
    public:
        E_Controller(E_Model*);
        ~E_Controller();
        
        // events
        void event_new_seed(int seed);
        void event_card_selected(int rank = 0, int suit = 0);
        void event_new_game();
        void event_add_player(string player_type = "human");
        void event_ragequit();
        void event_quit();
        
    private:
        E_Model* model;
        
}; // E_Controller

#endif

