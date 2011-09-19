#ifndef _E_NODE_
#define _E_NODE_

#include "E_Color.h"

class E_Node {
    public:
        E_Node();
        E_Node(E_Color color);
        ~E_Node();
        E_Color get_color() const;
        void set_color(E_Color color);
        
    private:
        E_Color color;
};

#endif
