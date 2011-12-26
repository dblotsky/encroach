#include "E_Reporter.h"
#include "E_Player.h"
#include <string>
#include <sstream>

using std::string;
using std::stringstream;

static int player_count = 0;

E_Player::E_Player() {
    prologue("E_Player");
    
    // make a default name
    stringstream name_stream;
    name_stream << "Player " << player_count;
    this->name = name_stream.str();
    
    // update counter
    player_count += 1;
    
    interlude("this->name", &(this->name), STRING);
    
    epilogue("E_Player");
}

E_Player::E_Player(const string& name) {
    prologue("E_Player");
    
    this->name = name;
    
    epilogue("E_Player");
}

E_Player::~E_Player() {
    prologue("E_Player", "~E_Player");
    interlude("this->name", &(this->name), STRING);
    epilogue("E_Player", "~E_Player");
}

const string& E_Player::get_name() const {
    prologue("E_Player", "get_name");
    
    const string& return_value = name;
    interlude("return_value", &(return_value), STRING);
    
    epilogue("E_Player", "get_name");
    return return_value;
}
