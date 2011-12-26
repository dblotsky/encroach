#include <string>
#include <cstdlib>
#include <iostream>
#include <cassert>
#include "E_Reporter.h"
#include "E_Color.h"

using std::string;
using std::cerr;
using std::endl;

int color_to_int(E_Color color) {
    prologue("E_Color", "color_to_int");
    
    int color_value = 0;
    
    switch (color) {
        
        case RED:
            color_value = 1;
            break;
            
        case BLUE:
            color_value = 2;
            break;
            
        case GREEN:
            color_value = 3;
            break;
            
        case YELLOW:
            color_value = 4;
            break;
            
        case PURPLE:
            color_value = 5;
            break;
            
        case BLANK:
            color_value = 0;
            break;
            
        default:
            cerr << "Got an unknown color." << endl;
            assert(false);
    }
    
    epilogue("E_Color", "color_to_int");
    return color_value;
}

E_Color string_to_color(const string& color_string) {
    prologue("E_Color", "string_to_color");
    
    E_Color return_value = int_to_color(atoi(color_string.c_str()));
    
    epilogue("E_Color", "string_to_color");
    return return_value;
}

E_Color int_to_color(int color_value) {
    prologue("E_Color", "int_to_color");
    
    E_Color color = BLANK;
    
    switch (color_value) {
        
        case 1:
            color = RED;
            break;
            
        case 2:
            color = BLUE;
            break;
            
        case 3:
            color = GREEN;
            break;
            
        case 4:
            color = YELLOW;
            break;
            
        case 5:
            color = PURPLE;
            break;
            
        case 0:
            color = BLANK;
            break;
            
        default:
            cerr << "Got an unknown color value." << endl;
            assert(false);
    }
    
    epilogue("E_Color", "int_to_color");
    return color;
}
