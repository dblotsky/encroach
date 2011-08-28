#ifndef _E_REPORTER_
#define _E_REPORTER_

using std::string;

enum ReportStage {PROLOGUE, EPILOGUE};

void report_method(const string& method_name, const string& class_name, const ReportStage stage);
void report_constructor(const string& class_name, const ReportStage stage);
void report_destructor(const string& class_name, const ReportStage stage);

/*
Usage:

    report_method_call("", "");
    report_constructor("");
    report_destructor("");
*/

#endif
