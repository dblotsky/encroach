#include "E_Reporter.h"

#include "E_Model.h"

E_Model::E_Model() {
    prologue("E_Model");
    epilogue("E_Model");
}

E_Model::~E_Model() {
    prologue("E_Model", "~E_Model");
    epilogue("E_Model", "~E_Model");
}
