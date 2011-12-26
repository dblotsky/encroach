#include "E_Reporter.h"
#include "E_Node.h"
#include "E_Color.h"

E_Node::E_Node(E_Color color){
    prologue("E_Node", "E_Node");
    
    this->color = color;
    this->owner = NULL;
    
    epilogue("E_Node", "E_Node");
}

E_Node::E_Node() {
    prologue("E_Node");
    
    this->color = BLANK;
    this->owner = NULL;
    
    epilogue("E_Node");
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

E_Owner* E_Node::get_owner() const {
    prologue("E_Node", "get_owner");
    epilogue("E_Node", "get_owner");
    return owner;
}

void E_Node::set_owner(E_Owner* owner) {
    prologue("E_Node", "set_owner");
    this->owner = owner;
    epilogue("E_Node", "set_owner");
}
