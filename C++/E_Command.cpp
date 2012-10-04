#include <string>
#include <cctype>
#include <assert>
#include "E_Reporter.h"
#include "E_Command.h"

using std::string;

E_Command::E_Command() {
    prologue("E_Command");
    
    type = EMPTY;
    lexeme = "";
    
    epilogue("E_Command");
}

E_Command::~E_Command() {
    prologue("E_Command", "~E_Command");
    epilogue("E_Command", "~E_Command");
}

CommandType E_Command::get_type() const {
    prologue("E_Command", "get_type");
    epilogue("E_Command", "get_type");
    return type;
}

const string& E_Command::str() const {
    prologue("E_Command", "str");
    epilogue("E_Command", "str");
    return lexeme;
}

void E_Command::parse(const string& command_string)
{
    prologue("E_Command", "parse");
    
    // check for max command length
    assert(command_string.length() <= MAX_COMMAND_LENGTH);
    
    lexeme = command_string;
    
    if (command_string.length() == 0) {
        type = EMPTY;
        
    } else if ( command_string == "p" || 
                command_string == "print" || 
                command_string == "d" || 
                command_string == "display") {
        type = PRINT;
        
    } else if ( command_string == "q" || 
                command_string == "quit" || 
                command_string == "exit") {
        type = QUIT;
        
    } else if ( command_string == "debug" || 
                command_string == "DEBUG" || 
                command_string == "dbg" || 
                command_string == "DBG" ||
                command_string == "b") {
        type = DEBUG;
        
    } else if ( command_string == "ai" ||
                command_string == "n" ||
                command_string == "new") {
        type = START_AI_GAME;
        
    } else if ( command_string == "add_player" ||
                command_string == "ap" ||
                command_string == "player") {
        type = ADD_PLAYER;
        
    } else if (is_a_digit(command_string)) {
        type = MOVE;
        
    } else {
        type = INVALID;
    }
    
    epilogue("E_Command", "parse");
    return;
}

bool E_Command::is_a_digit(const string& command_string) const {
    prologue("E_Command", "is_a_digit");
    
    bool return_value = (command_string.size() == 1 && isdigit(command_string.at(0)));
    
    epilogue("E_Command", "is_a_digit");
    return return_value;
}
