#ifndef _E_TERMINAL_
#define _E_TERMINAL_

#include "E_View.h"
#include "E_Model.h"
#include "E_Controller.h"

class E_Terminal: public E_View {
    public:
        E_Terminal(E_Controller*, E_Model*);
        virtual ~E_Terminal();
        void run();
        void update();
        
    private:
        
};

#endif
