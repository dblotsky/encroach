#include <iostream>

#include "E_Reporter.h"

using std::string;
using std::cerr;
using std::endl;

// reports a method call
void report_method(const string& method_name, const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "Entered method \"" << method_name << "\" in an object of class \"" << class_name << ".\"" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "Exited method \"" << method_name << "\" in an object of class \"" << class_name << ".\"" << endl;
    }
    return;
}

// reports a constructor call
void report_constructor(const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "Started creating an object of class \"" << class_name << ".\"" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "Finished creating an object of class \"" << class_name << ".\"" << endl;
    }
    return;
}

// reports a destructor call
void report_destructor(const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "Started deleting an object of class \"" << class_name << ".\"" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "Finished deleting an object of class \"" << class_name << ".\"" << endl;
    }
    return;
}
