#ifndef _E_CONTROLLER_
#define _E_CONTROLLER_

#include "E_Model.h"

class E_Controller {
    public:
        E_Controller(E_Model*);
        ~E_Controller();
        
        // events
        // void event_new_seed(int seed);
        
    private:
        E_Model* model;
        
}; // E_Controller

#endif
