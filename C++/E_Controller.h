#ifndef _E_CONTROLLER_
#define _E_CONTROLLER_

#include "E_Model.h"

class E_Controller {
    public:
        E_Controller(E_Model*);
        ~E_Controller();
        
        // events
        // TODO: make event functions
        
    private:
        E_Model* model;
        
};

#endif
