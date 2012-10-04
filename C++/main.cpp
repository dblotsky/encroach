#include <string>
#include <stdlib.h>
#include "E_Reporter.h"
#include "E_Model.h"
#include "E_Controller.h"
#include "E_View.h"
#include "E_Terminal.h"

// processes command line args
void process_args(int argc, char* argv[])
{
    std::string option;
    
    for (int i = 0; i < argc; i++) {
        
        option = argv[i];
        
        if (option == "-d" ||
            option == "--debug"
        ) {
            debug_on();
        }
    }
}

int main(int argc, char* argv[])
{
    // TODO: use a proper command line option parser
    process_args(argc, argv);
    
    // seed random generator
    srand(0);
    
    // create model and controller
    E_Model* model = new E_Model();
    E_Controller* controller = new E_Controller(model);
    
    // create view
    // TODO: provide a way to choose among the different views
    E_Terminal* view = new E_Terminal(model, controller, argc, argv);
    
    // run the game
    view->run();
    
    // free everything
    // NOTE: the order of these matters
    delete view;
    delete controller;
    delete model;

    return 0;
}
