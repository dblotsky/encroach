#include <iostream>

using namespace std;

#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"
#include "E_Reporter.h"

E_View::E_View(E_Controller* c, E_Model* m) {
    report_constructor("E_View");
    
    controller = c;
    model = m;
    
    // register view as observer of model
    model->subscribe(this);
} // E_View::E_View

E_View::~E_View() {
    report_destructor("E_View");
    
    // un-register view from the model
    model->unsubscribe(this);
} // E_View::~E_View

void E_View::update() {
    report_method_call("update", "E_View");
    
    // TODO: implement
    
    return;
} // E_View::~E_View
