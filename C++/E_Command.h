#ifndef _E_COMMAND_
#define _E_COMMAND_

#include <string>
#include "E_Color.h"

using std::string;

#define MAX_COMMAND_LENGTH 256

enum CommandType {QUIT, PRINT, EMPTY, DEBUG, MOVE, START_AI_GAME, ADD_PLAYER, INVALID};

class E_Command {
    public:
        E_Command();
        virtual ~E_Command();
        
        void parse(const string& command_string);
        CommandType get_type() const;
        const string& str() const;
        
    private:
        CommandType type;
        string lexeme;
        
        bool is_a_digit(const string& command_string) const;
};

#endif
