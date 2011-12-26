#ifndef _E_NODE_
#define _E_NODE_

#include "E_Color.h"
#include "E_Owner.h"

class E_Node {
    public:
        E_Node();
        E_Node(E_Color color);
        ~E_Node();
        
        E_Color get_color() const;
        void set_color(E_Color color);
        E_Owner* get_owner() const;
        void set_owner(E_Owner* owner);
        
    private:
        E_Color color;
        E_Owner* owner;
};

#endif
