#include <iostream>
#include <sstream>
#include <cassert>
#include "E_Reporter.h"

using std::string;
using std::cerr;
using std::endl;
using std::stringstream;

// constants
const string PRCD_START_LABEL   = "   ";
const string PRCD_END_LABEL     = "   ";
const string MTHD_START_LABEL   = "   ";
const string MTHD_END_LABEL     = "   ";
const string CSTR_START_LABEL   = "[ ]";
const string CSTR_END_LABEL     = "[+]";
const string DSTR_START_LABEL   = "[ ]";
const string DSTR_END_LABEL     = "[-]";

const string DEBUG_MARKER       = "DEBUG: ";
const string INDENT_TOKEN       = "| ";

const int    ALIGN_BUFFER_SIZE  = 16;
const string ALIGN_BUFFER_CHAR  = "-";

const string START_CHAR         = "+";
const string END_CHAR           = "\\";

// report exclusions
const int    NUM_EXCLUDED_CLASSES = 1;
const string EXCLUDED_CLASSES[NUM_EXCLUDED_CLASSES] = {"E_Command"};

// statics
static int  stack_depth = 0;
static bool debug = true;

// globals
static bool been_function_at_depth  = false;
static bool been_value_at_depth     = false;

void report_value(const string& value_name, const string& value) {
    string report_string = QUOTE + value_name + QUOTE + ": " + QUOTE + value + QUOTE;
    debug_print(report_string, true);
}

void report_function(const ReportStage stage, const string& class_name, const string& function_name) {

    // determine the label and the message from the class name and function name
    string label;
    string message;
    switch(get_function_type(class_name, function_name)) {
        case PROCEDURE:
            message = QUOTE + function_name + QUOTE;
            label   = start_or_end(stage, PRCD_START_LABEL, PRCD_END_LABEL);
            break;

        case METHOD:
            message = QUOTE + function_name + QUOTE + " on " + QUOTE + class_name + QUOTE;
            label   = start_or_end(stage, MTHD_START_LABEL, MTHD_END_LABEL);
            break;

        case CONSTRUCTOR:
            message = QUOTE + class_name + QUOTE;
            label   = start_or_end(stage, CSTR_START_LABEL, CSTR_END_LABEL);
            break;

        case DESTRUCTOR:
            message = QUOTE + class_name + QUOTE;
            label   = start_or_end(stage, DSTR_START_LABEL, DSTR_END_LABEL);
            break;

        default:
            cerr << "Got function type for which there is no case." << endl;
            assert(false);
    }

    string report_string = label + SPACE + message;
    string start_token   = start_or_end(stage, START_CHAR, END_CHAR);

    debug_print(report_string, true, true, start_token);
    return;
}

// returns first string argument if stage is START; returns second string argument if stage is END
string start_or_end(ReportStage stage, const string& start_string, const string& end_string) {
    if (stage == START) {
        return start_string;
    } else if (stage == END) {
        return end_string;
    } else {
        cerr << "Got an invalid enum value for ReportStage: " << QUOTE << stage << QUOTE << "." << endl;
        assert(false);
    }
}

// returns a type of function based on passed class name and function name
FunctionType get_function_type(const string& class_name, const string& function_name) {
    
    // class name and function name must not both be empty
    assert(!(class_name == "" && function_name == ""));
    
    string constructor_name = class_name;
    string destructor_name  = string("~") + class_name;

    // PROCEDURE: empty class name
    if (class_name == "" && function_name != "") {
        return PROCEDURE;

    // CONSTRUCTOR: function name is class name
    } else if (function_name == constructor_name || (class_name != "" && function_name == "")) {
        return CONSTRUCTOR;

    // DESTRUCTOR: function name is class name preceded by a tilde
    } else if (function_name == destructor_name) {
        return DESTRUCTOR;

    // METHOD: class name is not empty and function is not a constructor or destructor
    } else {
        return METHOD;
    }
}

// print debug info at the beginning of a function
void prologue(const string& class_name, const string& function_name) {
    
    if (!debug) { return; }
    if (is_excluded(class_name)) { return; }
    
    // skip a line if there has been a report at the current depth
    if (been_function_at_depth || been_value_at_depth) { debug_indent_line(); }
    
    report_function(START, class_name, function_name);
    increment_depth();

    return;
}

// print debug info at the end of a function
void epilogue(const string& class_name, const string& function_name) {
    
    if (!debug) { return; }
    if (is_excluded(class_name)) { return; }
    
    decrement_depth();
    report_function(END, class_name, function_name);
    
    return;
}

// debug info in the body of a function
void interlude_int(const string& value_name, const int value) {
    
    if (!debug) { return; }

    // skip a line if needed
    if (been_function_at_depth && !been_value_at_depth) { debug_indent_line(); }

    // make a string out of the value
    stringstream value_stream;
    value_stream << value;
    string value_string = value_stream.str();

    stay_at_depth();
    report_value(value_name, value_string);
    return;
}

// debug info in the body of a function
void interlude_string(const string& value_name, const string& value) {
    
    if (!debug) { return; }

    // skip a line if needed
    if (been_function_at_depth && !been_value_at_depth) { debug_indent_line(); }

    // make a string out of the value
    stringstream value_stream;
    value_stream << value;
    string value_string = value_stream.str();

    stay_at_depth();
    report_value(value_name, value_string);
    return;
}

// increments current depth
void increment_depth() {
    stack_depth++;
    been_function_at_depth = false;
    been_value_at_depth = false;
}

// flags that something was reported at this depth
void stay_at_depth() {
    been_value_at_depth = true;
}

// decrements current depth
void decrement_depth() {
    stack_depth--;
    been_function_at_depth = true;
    been_value_at_depth = false;
}

// returns true if the current class name is excluded from debugging
bool is_excluded(const string& class_name) {
    for(int i = 0; i < NUM_EXCLUDED_CLASSES; i++) {
        if (class_name == EXCLUDED_CLASSES[i]) {
            return true;
        }
    }
    return false;
}

// toggle debugging on/off
void toggle_debug() {
    if (debug) {
        debug = false;
        return;
    }
    debug = true;
    return;
}

// builds a string of n tokens
string build_string(const string& token, const int n) {
    string return_string;
    for (int i = 0; i < n; i++) {
        return_string += token;
    }
    return return_string;
}

// returns the indent buffer
string indent_buffer() {
    return build_string(INDENT_TOKEN, stack_depth);
}

// returns the alignment buffer
string alignment_buffer(const int num_chars_already_printed) {
    int buffer_length = (ALIGN_BUFFER_SIZE - (stack_depth * INDENT_TOKEN.size()) - num_chars_already_printed);
    return build_string(ALIGN_BUFFER_CHAR, buffer_length);
}

// prints debug messages
void debug_print(const string& message, bool indented, bool aligned, const string start_token) {
    
    cerr << DEBUG_MARKER;
    
    if (indented) {
        cerr << indent_buffer();
    }
    
    if (indented && aligned) {
        cerr << start_token;
        cerr << alignment_buffer(start_token.size());
        cerr << SPACE;
    }
    
    cerr << message << endl;
    return;
}

// prints an indented line
void debug_indent_line() {
    debug_print("", true);
    return;
}

// prints an empty line
void debug_empty_line() {
    debug_print();
    return;
}