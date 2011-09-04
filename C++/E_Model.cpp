#include <vector>
#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Board.h"
#include "E_Player.h"

using std::vector;

E_Model::E_Model() {
    prologue("E_Model");
    
    board = NULL;
    
    epilogue("E_Model");
}

E_Model::~E_Model() {
    prologue("E_Model", "~E_Model");
    epilogue("E_Model", "~E_Model");
}
