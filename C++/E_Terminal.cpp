#include <iostream>

using namespace std;

#include "E_Terminal.h"
#include "E_Reporter.h"

E_Terminal::E_Terminal(E_Controller* c, E_Model* m): E_View(c, m) {
    report_constructor("E_Terminal");
}

E_Terminal::~E_Terminal() {
    report_destructor("E_Terminal");
}

void E_Terminal::run() {
    report_method_call("run", "E_Terminal");

    cerr << "A E_Terminal was executed." << endl;
    return;
}

void E_Terminal::update() {
    report_method_call("update", "E_Terminal");
    
    return;
}

