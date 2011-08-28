#include <iostream>
#include <string>

#include "E_Terminal.h"
#include "E_Reporter.h"

using std::string;
using std::cout;
using std::cin;
using std::endl;

E_Terminal::E_Terminal(E_Controller* controller, E_Model* model, int argc, char* argv[]): E_View(controller, model) {
    report_constructor("E_Terminal", PROLOGUE);
    
    
    
    report_constructor("E_Terminal", EPILOGUE);
}

E_Terminal::~E_Terminal() {
    report_destructor("E_Terminal", PROLOGUE);
    
    
    
    report_destructor("E_Terminal", EPILOGUE);
}

void E_Terminal::run() {
    report_method("run", "E_Terminal", PROLOGUE);
    
    // char array and string to store entered command
    char raw_command[256];
    string command = "";
    
    // loop a prompt while cin is good
    while (cin.good()) {
        
        print_prompt();
        
        // process command
        cin.getline(raw_command, 256);
        command = raw_command;
        
        // report the command
        report_variable<string>("command", command);
        
        if (command == "q" || command == "quit" || command == "exit") {
            break;
        } else if (command.length() == 0 || command == "p" || command == "print" || command == "d" || command == "display") {
            print_board();
        } else {
            cout << "Invalid command." << endl;
        }
    }
    
    report_method("run", "E_Terminal", EPILOGUE);
    return;
}

void E_Terminal::update() {
    report_method("update", "E_Terminal", PROLOGUE);
    
    
    
    report_method("update", "E_Terminal", EPILOGUE);
    return;
}

void E_Terminal::print_board() {
    report_method("print_board", "E_Terminal", PROLOGUE);
    
    cout << "000\n000\n000\n";
    
    report_method("print_board", "E_Terminal", EPILOGUE);
    return;
}

void E_Terminal::print_prompt() {
    report_method("print_prompt", "E_Terminal", PROLOGUE);
    
    cout << "--> ";
    
    report_method("print_prompt", "E_Terminal", EPILOGUE);
    return;
}