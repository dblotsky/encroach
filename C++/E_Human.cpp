#include "E_Reporter.h"
#include "E_Human.h"
#include "E_Color.h"
#include <string>

using std::string;

E_Human::E_Human() {
    prologue("E_Human");
    epilogue("E_Human");
}

E_Human::E_Human(const string& name) {
    prologue("E_Human");
    epilogue("E_Human");
}

E_Human::~E_Human() {
    prologue("E_Human", "~E_Human");
    epilogue("E_Human", "~E_Human");
}

E_Color E_Human::choose_move() {
    prologue("E_Human", "choose_move");
    E_Color return_value = BLANK;
    epilogue("E_Human", "choose_move");
    return return_value;
}

bool E_Human::needs_input() {
    prologue("E_Human", "needs_input");
    epilogue("E_Human", "needs_input");
    return true;
}
