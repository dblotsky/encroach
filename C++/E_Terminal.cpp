#include <iostream>
#include <string>
#include <cassert>

#include "E_Reporter.h"

#include "E_Terminal.h"

using std::string;
using std::cout;
using std::cerr;
using std::cin;
using std::endl;

enum Command {QUIT, PRINT, EMPTY, SWITCH_REPORTING, INVALID};

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
    
    // char array, string, and enum variable to store entered command
    char raw_command[256];
    string command_string = "";
    Command command;
    
    // exit flag
    bool exited = false;
    
    // loop a prompt while cin is good and while the user has not exited
    while (cin.good() && !exited) {
        
        print_prompt();
        
        // process input
        cin.getline(raw_command, 256);
        command_string = raw_command;
        
        // report command string
        report_variable<string>("command_string", command_string);
        
        // determine command
        if (command_string == "q" || command_string == "quit" || command_string == "exit") {
            command = QUIT;
        } else if (command_string == "p" || command_string == "print" || command_string == "d" || command_string == "display") {
            command = PRINT;
        } else if (command_string.length() == 0) {
            command = EMPTY;
        } else if (command_string == "debug" || command_string == "DEBUG" || command_string == "dbg" || command_string == "DBG") {
            command = SWITCH_REPORTING;
        } else {
            command = INVALID;
        }
        
        // act on command
        switch (command) {
            case QUIT:
                exited = true;
                break;
                
            case PRINT:
                print_board();
                break;
                
            case EMPTY:
                break;
                
            case SWITCH_REPORTING:
                switch_reporting();
                break;
                
            case INVALID:
                cout << "Invalid command: " << QUOTE << command_string << QUOTE << "." << endl;
                break;
                
            default:
                cerr << endl << "Caught an enum value for which a case does not exist!" << endl;
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
    cout << "--> ";
    return;
}