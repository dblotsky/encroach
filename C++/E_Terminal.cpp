#include <iostream>

using namespace std;

#include "E_Terminal.h"
#include "E_Reporter.h"

E_Terminal::E_Terminal(E_Controller* controller, E_Model* model): E_View(controller, model) {
    report_constructor("E_Terminal", PROLOGUE);
    report_constructor("E_Terminal", EPILOGUE);
}

E_Terminal::~E_Terminal() {
    report_destructor("E_Terminal", PROLOGUE);
    report_destructor("E_Terminal", EPILOGUE);
}

void E_Terminal::run() {
    report_method("run", "E_Terminal", PROLOGUE);

    cerr << "An E_Terminal was executed." << endl;
    
    report_method("run", "E_Terminal", EPILOGUE);
    return;
}

void E_Terminal::update() {
    report_method("update", "E_Terminal", PROLOGUE);
    report_method("update", "E_Terminal", EPILOGUE);
    return;
}

