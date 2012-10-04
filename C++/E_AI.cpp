#include <string>
#include "E_Reporter.h"
#include "E_AI.h"
#include "E_Color.h"

using std::string;

E_AI::E_AI() {
    prologue("E_AI");
    epilogue("E_AI");
}

E_AI::E_AI(const string& name) {
    prologue("E_AI");
    epilogue("E_AI");
}

E_AI::~E_AI() {
    prologue("E_AI", "~E_AI");
    epilogue("E_AI", "~E_AI");
}

E_Color E_AI::choose_move() {
    prologue("E_AI", "choose_move");
    E_Color return_value = BLUE;
    epilogue("E_AI", "choose_move");
    return return_value;
}

bool E_AI::needs_input() {
    prologue("E_AI", "needs_input");
    epilogue("E_AI", "needs_input");
    return false;
}
