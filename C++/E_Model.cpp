#include <vector>
#include <deque>

using namespace std;

#include "E_Model.h"
#include "E_Reporter.h"

E_Model::E_Model() {
    report_constructor("E_Model", PROLOGUE);
    report_constructor("E_Model", EPILOGUE);
}

E_Model::~E_Model(){
    report_destructor("E_Model", PROLOGUE);
    report_destructor("E_Model", EPILOGUE);
}
