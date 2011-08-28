#include <stdlib.h>
#include <iostream>

using namespace std;

#include "E_Controller.h"
#include "E_Model.h"
#include "E_Reporter.h"

E_Controller::E_Controller(E_Model* model) {
    report_constructor("E_Controller", PROLOGUE);
    
    this.model = model;
    
    report_constructor("E_Controller", EPILOGUE);
}

E_Controller::~E_Controller() {
    report_destructor("E_Controller", PROLOGUE);
    report_destructor("E_Controller", EPILOGUE);
}
