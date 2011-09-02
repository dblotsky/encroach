#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"
#include "E_Terminal.h"

int main(int argc, char* argv[]) {

    // create a back-end
    E_Model*        model       = new E_Model();
    E_Controller*   controller  = new E_Controller(model);
    
    // create a front-end
    E_Terminal* view = new E_Terminal(model, controller, argc, argv);
    
    // run the game
    view->run();
    
    // delete the front-end
    delete view;
    
    // delete the back-end
    delete controller;
    delete model;

    return 0;
}
