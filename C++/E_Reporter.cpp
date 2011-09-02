#include <iostream>
#include <sstream>
#include <cassert>

#include "E_Reporter.h"

using std::string;
using std::cerr;
using std::endl;
using std::stringstream;

// constants
const string PROCEDURE_LABEL    = "";
const string PROCEDURE_P_STATUS = "CALL";
const string PROCEDURE_E_STATUS = "RTRN";

const string METHOD_LABEL       = "";
const string METHOD_P_STATUS    = "CALL";
const string METHOD_E_STATUS    = "RTRN";

const string CONSTRUCTOR_LABEL      = "[+]";
const string CONSTRUCTOR_P_STATUS   = "create";
const string CONSTRUCTOR_E_STATUS   = "CREATE";

const string DESTRUCTOR_LABEL       = "[-]";
const string DESTRUCTOR_P_STATUS    = "dstroy";
const string DESTRUCTOR_E_STATUS    = "DSTROY";

const string P_TOKEN = "//";
const string E_TOKEN = "\\\\";

const string SPACE  = " ";
const string QUOTE  = "\"";

void prologue(const string& class_name, const string& function_name) {
    report(PROLOGUE, class_name, function_name);
    return;
}

void epilogue(const string& class_name, const string& function_name) {
    report(EPILOGUE, class_name, function_name);
    return;
}

void report(const ReportStage ReportStage, const string& class_name, const string& function_name) {
    
    // assertions
    assert(!(class_name == "" && function_name == ""));
    
    // arguments for print function
    string token;
    string label;
    string identifier;
    string status;
    
    // derive the constructor's name
    string constructor_name = class_name;

    // derive the destructor's name
    stringstream destructor_stream;
    destructor_stream << "~" << class_name;
    string destructor_name = destructor_stream.str();
    
    // determine the function type
    FunctionType function_type;
    if (class_name == "" && function_name != "") {
        function_type = PROCEDURE;
    } else if (function_name == constructor_name || (class_name != "" && function_name == "")) {
        function_type = CONSTRUCTOR;
    } else if (function_name == destructor_name) {
        function_type = DESTRUCTOR;
    } else {
        function_type = METHOD;
    }
    
    // determine token
    if (ReportStage == EPILOGUE) {
        token = E_TOKEN;
    } else {
        token = P_TOKEN;
    }
    
    // determine values of the other print parameters
    stringstream identifier_stream;
    if (function_type == PROCEDURE) {
        
        identifier_stream << QUOTE << function_name << QUOTE;
        identifier  = identifier_stream.str();
        label       = PROCEDURE_LABEL;
        status      = determine_status(ReportStage, PROCEDURE_P_STATUS, PROCEDURE_E_STATUS);
        
    } else if (function_type == METHOD) {
        
        identifier_stream << QUOTE << function_name << QUOTE << " on " << QUOTE << class_name << QUOTE;
        identifier  = identifier_stream.str();
        label       = METHOD_LABEL;
        status      = determine_status(ReportStage, METHOD_P_STATUS, METHOD_E_STATUS);
        
    } else if (function_type == CONSTRUCTOR) {
        
        identifier_stream << QUOTE << class_name << QUOTE;
        identifier  = identifier_stream.str();
        label       = CONSTRUCTOR_LABEL;
        status      = determine_status(ReportStage, CONSTRUCTOR_P_STATUS, CONSTRUCTOR_E_STATUS);
        
    } else if (function_type == DESTRUCTOR) {
        
        identifier_stream << QUOTE << class_name << QUOTE;
        identifier  = identifier_stream.str();
        label       = DESTRUCTOR_LABEL;
        status      = determine_status(ReportStage, DESTRUCTOR_P_STATUS, DESTRUCTOR_E_STATUS);
        
    } else {
        cerr << "Invalid function type." << endl;
        assert(false);
    }
    
    // print the report
    report_print(token, label, identifier, status);
}

string determine_status(ReportStage ReportStage, const string& prologue_string, const string& epilogue_string) {
    if (ReportStage == PROLOGUE) {
        return prologue_string;
    }
    return epilogue_string;
}

void report_print(const string& token, const string& label, const string& identifier, const string& status) {
    cerr << token << label << status << identifier << endl;
    return;
}