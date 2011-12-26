#ifndef _E_PLAYER_
#define _E_PLAYER_

#include "E_Owner.h"
#include "E_Color.h"
#include <string>

using std::string;

class E_Player: public E_Owner {
    public:
        E_Player();
        E_Player(const string& name);
        
        virtual E_Color choose_move() = 0;
        virtual bool needs_input() = 0;
        const string& get_name() const;
        virtual ~E_Player();
        
    private:
        string name;
};

#endif
