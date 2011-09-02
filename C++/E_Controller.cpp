#include "E_Reporter.h"

#include "E_Controller.h"
#include "E_Model.h"

E_Controller::E_Controller(E_Model* model) {
    prologue("E_Controller");
    
    this->model = model;
    
    epilogue("E_Controller");
}

E_Controller::~E_Controller() {
    prologue("E_Controller", "~E_Controller");
    epilogue("E_Controller", "~E_Controller");
}
