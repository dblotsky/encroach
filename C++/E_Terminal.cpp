#include <iostream>
#include <string>
#include <cassert>
#include "E_Reporter.h"
#include "E_Terminal.h"
#include "E_Command.h"
#include "E_Color.h"
#include "E_Controller.h"
#include "E_Model.h"
#include "E_Exception.h"

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
        interlude("command.str()", &(command.str()), STRING);
        
        // act on command
        switch (command.get_type()) {
            
            case QUIT:
                exited = true;
                break;
                
            case PRINT:
                update();
                break;
                
            case EMPTY:
                break;
                
            case DEBUG:
                toggle_debug();
                break;
                
            case START_AI_GAME:
                controller->new_ai_game("Player 1");
                break;
                
            case ADD_PLAYER:
                controller->add_player();
                break;
                
            case MOVE:
                controller->make_move(string_to_color(command.str()));
                break;
                
            case INVALID:
                cout << "Invalid command: \"" << command.str() << "\"." << endl;
                break;
                
            default:
                // throw unhandled_enum_value;
                cerr << endl << "Got an enum value for which a case does not exist." << endl;
                assert(false);
        }
    }
    
    cout << "Goodbye!" << endl;
    epilogue("E_Terminal", "run");
    return;
}

void E_Terminal::update() {
    prologue("E_Terminal", "update");
    
    print_board();
    
    epilogue("E_Terminal", "update");
    return;
}

void E_Terminal::print_board() {
    prologue("E_Terminal", "print_board");
    
    int x_size = model->get_board_x_size();
    int y_size = model->get_board_y_size();
    
    // top border
    cout << "+-";
    for (int i = 0; i < x_size; i++){
        cout << "--";
    }
    cout << "+" << endl;
    
    // colors
    for (int x = 0; x < x_size; x++) {
        cout << "| ";
        for (int y = 0; y < y_size; y++) {
            cout << color_to_int(model->get_color_at(x, y)) << " ";
        }
        cout << "|" << endl;
    }
    
    // bottom border
    cout << "+-";
    for (int i = 0; i < x_size; i++){
        cout << "--";
    }
    cout << "+" << endl;
    
    epilogue("E_Terminal", "print_board");
    return;
}

void E_Terminal::print_prompt() {
    prologue("E_Terminal", "print_prompt");
    
    cout << "--> ";
    
    epilogue("E_Terminal", "print_prompt");
    return;
}
