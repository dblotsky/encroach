#ifndef _E_MODEL_
#define _E_MODEL_

#include <vector>
#include <deque>

using namespace std;

#include "E_Subject.h"
#include "E_Card.h"
#include "E_Player.h"
#include "E_Computer.h"
#include "E_Human.h"

#define NUM_PLAYERS 4
#define NUM_CARDS   52

class E_Model : public E_Subject {
    public:
        E_Model();
        ~E_Model();
        
    private:
        
}; // E_Model

#endif
