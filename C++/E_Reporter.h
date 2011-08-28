#ifndef _E_REPORTER_
#define _E_REPORTER_

#include <string>

using std::string;

// TODO: make this a class, and report fancy method call trees

enum ReportStage {PROLOGUE, EPILOGUE};

void report_method(const string& method_name, const string& class_name, const ReportStage stage);
void report_constructor(const string& class_name, const ReportStage stage);
void report_destructor(const string& class_name, const ReportStage stage);

template <typename variable_type> void report_variable (const string& name, variable_type variable) {
    cout << name << " : " << variable << endl;
}

/*
Usage:

    report_method("", "", PROLOGUE or EPILOGUE);
    report_constructor("", PROLOGUE or EPILOGUE);
    report_destructor("", PROLOGUE or EPILOGUE);
    report_variable("", "");
*/

#endif
