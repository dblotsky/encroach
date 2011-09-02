#ifndef _E_TERMINAL_
#define _E_TERMINAL_

#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"

class E_Terminal: public E_View {
    public:
        E_Terminal(E_Model*, E_Controller*, int argc, char* argv[]);
        virtual ~E_Terminal();
        void run();
        void update();
        
    private:
        void print_board();
        void print_prompt();
        
};

#endif
