#ifndef _E_COMMAND_
#define _E_COMMAND_

#include <string>

using std::string;

enum CommandType {QUIT, PRINT, EMPTY, DEBUG, MOVE, INVALID};

class E_Command {
    public:
        E_Command();
        virtual ~E_Command();
        void process(const string& command_string);
        CommandType get_type() const;
        const string& str() const;
        
    private:
        CommandType type;
        string lexeme;
        
        bool is_a_digit(const string& command_string) const;
};

#endif
