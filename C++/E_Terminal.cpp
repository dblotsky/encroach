#include <iostream>
#include <string>
#include <cassert>
#include <exception>

#include "E_Reporter.h"

#include "E_Terminal.h"
#include "E_Command.h"
#include "E_Controller.h"
#include "E_Model.h"

using std::string;
using std::cout;
using std::cerr;
using std::cin;
using std::endl;

E_Terminal::E_Terminal(E_Model* model, E_Controller* controller, int argc, char* argv[]): E_View(model, controller) {
    prologue("E_Terminal");
    epilogue("E_Terminal");
}

E_Terminal::~E_Terminal() {
    prologue("E_Terminal", "~E_Terminal");
    epilogue("E_Terminal", "~E_Terminal");
}

void E_Terminal::run() {
    prologue("E_Terminal", "run");
    
    // char array, string, and E_Command to store entered command
    char raw_command[256];
    string command_string = "";
    E_Command command;
    
    // exit flag
    bool exited = false;
    
    // loop a prompt while cin is good and while the user has not exited
    while (cin.good() && !exited) {
        
        print_prompt();
        
        // process input
        cin.getline(raw_command, 256);
        command_string = raw_command;
        command.process(command_string);
        
        // report command string
        interlude_string("command.str()", command.str());
        
        // act on command
        switch (command.get_type()) {
            case QUIT:
                exited = true;
                break;
                
            case PRINT:
                print_board();
                break;
                
            case EMPTY:
                break;
                
            case DEBUG:
                toggle_debug();
                break;
            
            case MOVE:
                controller->event_move(command.str());
                print_board();
                break;
                
            case INVALID:
                cout << "Invalid command: " << QUOTE << command.str() << QUOTE << "." << endl;
                break;
                
            default:
                cerr << endl << "Got an enum value for which a case does not exist." << endl;
                assert(false);
                break;
        }
    }
    
    epilogue("E_Terminal", "run");
    return;
}

void E_Terminal::update() {
    prologue("E_Terminal", "update");
    epilogue("E_Terminal", "update");
    return;
}

void E_Terminal::print_board() {
    prologue("E_Terminal", "print_board");
    
    cout << "0 0 0\n0 0 0\n0 0 0\n";
    
    epilogue("E_Terminal", "print_board");
    return;
}

void E_Terminal::print_prompt() {
    // prologue("E_Terminal", "print_prompt");
    
    cout << "--> ";
    
    // prologue("E_Terminal", "print_prompt");
    return;
}