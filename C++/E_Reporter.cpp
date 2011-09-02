#include <iostream>
#include <sstream>
#include <cassert>

#include "E_Reporter.h"

using std::string;
using std::cerr;
using std::endl;
using std::stringstream;

static int  REPORT_DEPTH = 0;
static bool REPORTING_ON = true;

void switch_reporting() {
    if (REPORTING_ON) {
        REPORTING_ON = false;
        cerr << "Debugging OFF." << endl;
        return;
    }
    REPORTING_ON = true;
    cerr << "Debugging ON." << endl;
    return;
}

void prologue(const string& class_name, const string& function_name) {
    
    report_function(PROLOGUE, class_name, function_name);
    REPORT_DEPTH++;
    
    return;
}

void epilogue(const string& class_name, const string& function_name) {
    
    REPORT_DEPTH--;
    report_function(EPILOGUE, class_name, function_name);
    
    return;
}

void report_function(const ReportStage ReportStage, const string& class_name, const string& function_name) {
    
    // assertions
    assert(!(class_name == "" && function_name == ""));
    
    // arguments for print function
    string line_start;
    string label;
    string message;
    
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
    
    // determine line start
    if (ReportStage == PROLOGUE) {
        line_start = P_LINE_START;
    } else {
        line_start = E_LINE_START;
    }
    
    // determine values of the other print parameters
    stringstream message_stream;
    if (function_type == PROCEDURE) {
        
        message_stream << QUOTE << function_name << QUOTE;
        message = message_stream.str();
        label   = determine_label(ReportStage, PROCEDURE_P_LABEL, PROCEDURE_E_LABEL);
        
    } else if (function_type == METHOD) {
        
        message_stream << QUOTE << function_name << QUOTE << " on " << QUOTE << class_name << QUOTE;
        message = message_stream.str();
        label   = determine_label(ReportStage, METHOD_P_LABEL, METHOD_E_LABEL);
        
    } else if (function_type == CONSTRUCTOR) {
        
        message_stream << QUOTE << class_name << QUOTE;
        message = message_stream.str();
        label   = determine_label(ReportStage, CONSTRUCTOR_P_LABEL, CONSTRUCTOR_E_LABEL);
        
    } else if (function_type == DESTRUCTOR) {
        
        message_stream << QUOTE << class_name << QUOTE;
        message = message_stream.str();
        label   = determine_label(ReportStage, DESTRUCTOR_P_LABEL, DESTRUCTOR_E_LABEL);
        
    } else {
        cerr << "Invalid function type." << endl;
        assert(false);
    }
    
    // print the report
    report_print(line_start, label, message);
}

string determine_label(ReportStage stage, const string& prologue_string, const string& epilogue_string) {
    if (stage == PROLOGUE) {
        return prologue_string;
    } else if (stage == EPILOGUE) {
        return epilogue_string;
    } else {
        cerr << "Invalid enum value: " << QUOTE << stage << QUOTE << "." << endl;
        assert(false);
    }
}

void report_print(const string& line_start, const string& label, const string& message) {
    
    if (!REPORTING_ON) {return;}
    
    // print the debug marker
    cerr << DEBUG_MARKER;
    
    // indent
    for (int i = 0; i < REPORT_DEPTH; i++) {
        cerr << INDENT_CHARACTER;
    }
    
    // if there the line start token is not empty, then print it with a buffer
    if (line_start != "") {
        
        // print the token
        cerr << line_start;
        
        // print the character buffer
        for(int i = 0; i < (BUFFER_SIZE - REPORT_DEPTH - line_start.size()); i++) {
            cerr << BUFFER_CHARACTER;
        }
        
        // print a space
        cerr << " ";
    }
    
    // print the label if it's not empty
    if (label != "") {
        cerr << label << " ";
    }
    
    // print the message if it's not empty
    if (label != "") {
        cerr << message;
    }
    
    // end the report
    cerr << endl;
    return;
}