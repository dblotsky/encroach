#ifndef _E_REPORTER_
#define _E_REPORTER_

#include <sstream>
#include <string>

using std::string;
using std::stringstream;

// TODO: make this a singleton class

// constants
const string PROCEDURE_P_LABEL      = "call";
const string PROCEDURE_E_LABEL      = "rtrn";

const string METHOD_P_LABEL         = "CALL";
const string METHOD_E_LABEL         = "RTRN";

const string CONSTRUCTOR_P_LABEL    = "[+c]";
const string CONSTRUCTOR_E_LABEL    = "[+C]";

const string DESTRUCTOR_P_LABEL     = "[-d]";
const string DESTRUCTOR_E_LABEL     = "[-D]";

const string P_LINE_START           = "/";
const string E_LINE_START           = "\\";

const string SPACE                  = " ";
const string QUOTE                  = "\"";
const string DEBUG_MARKER           = "DBG: ";
const string INDENT_CHARACTER       = "-";
const int    BUFFER_SIZE            = 7;
const string BUFFER_CHARACTER       = " ";

// enums
enum FunctionType {CONSTRUCTOR, DESTRUCTOR, METHOD, PROCEDURE};
enum ReportStage {PROLOGUE, EPILOGUE};

// private
string determine_label(ReportStage stage, const string& prologue_string, const string& epilogue_string);
void report_function(const ReportStage stage, const string& class_name, const string& function_name);
void report_print(const string& line_start, const string& label, const string& message);
// static int REPORT_DEPTH = 0;
// static bool REPORTING_ON = false;

// public
void prologue(const string& class_name = "", const string& function_name = "");
void epilogue(const string& class_name = "", const string& function_name = "");
void switch_reporting();
template <typename variable_type> void report_variable (const string& name, variable_type variable) {

    // build the string
    stringstream report_stream;
    report_stream << QUOTE << name << QUOTE << ": " << QUOTE << variable << QUOTE;
    string report_string = report_stream.str();
    
    report_print("", "[VARIABLE]:", report_string);
    return;
}

#endif
