#include <iostream>

using namespace std;

#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"
#include "E_Reporter.h"

E_View::E_View(E_Controller* controller, E_Model* model) {
    report_constructor("E_View", PROLOGUE);
    
    this.controller = controller;
    this.model = model;
    
    // register view as observer of model
    model->subscribe(this);
    
    report_constructor("E_View", EPILOGUE);
} // E_View::E_View

E_View::~E_View() {
    report_destructor("E_View", PROLOGUE);
    
    // un-register view from the model
    model->unsubscribe(this);
    
    report_destructor("E_View", EPILOGUE);
} // E_View::~E_View

void E_View::update() {
    report_method("update", "E_View", PROLOGUE);
    
    // TODO: implement
    
    report_method("update", "E_View", EPILOGUE);
    return;
} // E_View::~E_View
