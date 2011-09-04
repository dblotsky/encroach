#ifndef _E_MODEL_
#define _E_MODEL_

#include <vector>
#include "E_Subject.h"
#include "E_Board.h"
#include "E_Player.h"

using std::vector;

class E_Model : public E_Subject {
    public:
        E_Model();
        ~E_Model();
        
    private:
        E_Board* board;
        vector<E_Player*> players;
};

#endif
