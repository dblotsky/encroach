#include <iostream>
#include <string>

#include "E_Reporter.h"

#include "E_Terminal.h"

using std::string;
using std::cout;
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
    
    // char array and string to store entered command
    char raw_command[256];
    string command = "";
    
    // loop a prompt while cin is good
    while (cin.good()) {
        
        print_prompt();
        
        // process input
        cin.getline(raw_command, 256);
        command = raw_command;
        
        // report the command
        report_variable<string>("command", command);
        
        // process command
        if (command == "q" || command == "quit" || command == "exit") {
            break;
        } else if (command.length() == 0 || command == "p" || command == "print" || command == "d" || command == "display") {
            print_board();
        } else {
            cout << "Invalid command." << endl;
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