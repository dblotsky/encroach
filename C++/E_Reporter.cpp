#include <iostream>

#include "E_Reporter.h"

using std::string;
using std::cerr;
using std::endl;

// reports a method call
void report_method(const string& method_name, const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "/* P: \"" << method_name << "\" on one of \"" << class_name << "\"" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "\\* E: \"" << method_name << "\" on one of \"" << class_name << "\"" << endl;
    }
    return;
}

// reports a constructor call
void report_constructor(const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "/* P-CSTR: \"" << class_name << "\"" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "\\* E-CSTR: \"" << class_name << "\"" << endl;
    }
    return;
}

// reports a destructor call
void report_destructor(const string& class_name, const ReportStage stage) {
    if (stage == PROLOGUE) {
        cerr << "/* P-DSTR: \"" << class_name << "\"" << endl;
    } else if (stage == EPILOGUE) {
        cerr << "\\* E-DSTR: \"" << class_name << "\"" << endl;
    }
    return;
}
