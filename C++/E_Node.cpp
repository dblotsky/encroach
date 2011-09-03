#include "E_Reporter.h"

#include "E_Node.h"
#include "E_Color.h"

E_Node::E_Node() {
    prologue("E_Node");
    epilogue("E_Node");
}

E_Node::~E_Node(){
    prologue("E_Node", "~E_Node");
    epilogue("E_Node", "~E_Node");
}

E_Color E_Node::get_color() const {
    prologue("E_Node", "get_color");
    epilogue("E_Node", "get_color");
    return color;
}

void E_Node::set_color(E_Color color) {
    prologue("E_Node", "set_color");
    
    this->color = color;
    
    epilogue("E_Node", "set_color");
    return;
}