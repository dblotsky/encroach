#include <vector>
#include <deque>

using namespace std;

#include "E_Model.h"
#include "E_Card.h"
#include "E_Player.h"
#include "E_Computer.h"
#include "E_Human.h"
#include "E_Reporter.h"

#define NUM_PLAYERS 4
#define NUM_CARDS   52

E_Model::E_Model() {
    report_constructor("E_Model");
}

E_Model::~E_Model(){
    report_destructor("E_Model");
}
