#include "E_Reporter.h"
#include "E_Node.h"
#include "E_Color.h"

E_Node::E_Node() {
    prologue("E_Node");
    
    this->color = BLANK;
    
    epilogue("E_Node");
}

E_Node::E_Node(E_Color color){
    prologue("E_Node", "E_Node");
    
    this->color = color;
    
    epilogue("E_Node", "E_Node");
}

E_Node::~E_Node(){
    prologue("E_Node", "~E_Node");
    epilogue("E_Node", "~E_Node");
}

E_Color E_Node::get_color() const {
    prologue("E_Node", "get_color");
    
    E_Color return_value = this->color;
    
    epilogue("E_Node", "get_color");
    return return_value;
}

void E_Node::set_color(E_Color color) {
    prologue("E_Node", "set_color");
    
    this->color = color;
    
    epilogue("E_Node", "set_color");
    return;
}
