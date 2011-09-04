#include <vector>
#include "E_Reporter.h"
#include "E_Board.h"

using std::vector;

E_Board::E_Board() {
    prologue("E_Board");
    epilogue("E_Board");
}

E_Board::~E_Board() {
    prologue("E_Board", "~E_Board");
    epilogue("E_Board", "~E_Board");
}
