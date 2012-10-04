#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"

E_View::E_View(E_Model* model, E_Controller* controller) {
    prologue("E_View");
    
    // set model and controller
    this->model = model;
    this->controller = controller;
    
    // register view as observer of model
    model->subscribe(this);
    
    epilogue("E_View");
}

E_View::~E_View() {
    prologue("E_View", "~E_View");
    
    // un-register view from the model
    model->unsubscribe(this);
    
    epilogue("E_View", "~E_View");
}
