#ifndef _E_HUMAN_
#define _E_HUMAN_

#include "E_Player.h"
#include "E_Color.h"
#include <string>

using std::string;

class E_Human: public E_Player {
    public:
        E_Human();
        E_Human(const string& name);
        
        virtual E_Color choose_move();
        virtual bool needs_input();
        virtual ~E_Human();
        
    private:
        
};

#endif
