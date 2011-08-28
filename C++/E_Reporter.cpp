#include <iostream>

#include "E_Reporter.h"

using std::string;
using std::cerr;
using std::endl;

// reports a method call
void report_method(const string& method_name, const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "/*** P-METH: \"" << method_name << "\" on object \"" << class_name << "\"" << endl;
        cerr << "|" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "|" << endl;
        cerr << "\\*** E-METH: \"" << method_name << "\" on object \"" << class_name << "\"" << endl;
    }
    return;
}

// reports a constructor call
void report_constructor(const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "/*** P-CSTR: \"" << class_name << "\"" << endl;
        cerr << "|" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "|" << endl;
        cerr << "\\*** E-CSTR: \"" << class_name << "\"" << endl;
    }
    return;
}

// reports a destructor call
void report_destructor(const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "/*** P-DSTR: \"" << class_name << "\"" << endl;
        cerr << "|" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "|" << endl;
        cerr << "\\*** E-DSTR: \"" << class_name << "\"" << endl;
    }
    return;
}
