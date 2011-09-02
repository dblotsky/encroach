#ifndef _E_REPORTER_
#define _E_REPORTER_

#include <iostream>
#include <string>

using std::string;

// TODO: make this a singleton class, and report fancy method call trees

// enums
enum FunctionType {CONSTRUCTOR, DESTRUCTOR, METHOD, PROCEDURE};
enum ReportStage {PROLOGUE, EPILOGUE};

// public
void prologue(const string& class_name = "", const string& function_name = "");
void epilogue(const string& class_name = "", const string& function_name = "");
template <typename variable_type> void report_variable (const string& name, variable_type variable) {
    std::cerr << "\\- VARIABLE: " << "\"" << name << "\"" << ": " << "\"" << variable << "\"" << std::endl;
    return;
}

// private
// static int depth = 0;
void report(const ReportStage ReportStage, const string& class_name, const string& function_name);
string determine_status(ReportStage ReportStage, const string& prologue_string, const string& epilogue_string);
void report_print(const string& token, const string& label, const string& identifier, const string& status);

#endif
