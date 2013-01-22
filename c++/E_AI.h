#ifndef _E_AI_
#define _E_AI_

#include "E_Player.h"
#include "E_Color.h"
#include <string>

using std::string;

class E_AI: public E_Player {
    public:
        E_AI();
        E_AI(const string& name);
        
        virtual E_Color choose_move();
        virtual bool needs_input();
        virtual ~E_AI();
        
    private:
        
};

#endif
