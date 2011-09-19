#include <string>
#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"
#include "E_Terminal.h"

void process_options(int argc, char* argv[]) {
    std::string option;
    for (int i = 0; i < argc; i++){
        option = argv[i];
        if (option == "-d" ||
            option == "--debug") {
            on_debug();
        }
    }
}

int main(int argc, char* argv[]) {
    
    process_options(argc, argv);

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
