#ifndef _E_REPORTER_
#define _E_REPORTER_

#include <string>

using std::string;

// TODO: make this a singleton class

// enums
enum FunctionType   {CONSTRUCTOR, DESTRUCTOR, METHOD, PROCEDURE};
enum ReportStage    {START, END};
enum PointerType    {INT, LONG_INT, LONG, LONG_LONG, STRING, CHAR, VOID};

// constants
const string QUOTE = "\"";
const string SPACE = " ";

// public
void prologue(const string& class_name = "", const string& function_name = "");
void epilogue(const string& class_name = "", const string& function_name = "");
void interlude(const string& name, const void* value, PointerType type=VOID);
void toggle_debug();
void on_debug();
void off_debug();

// private
// static int REPORT_DEPTH = 0;
// static bool REPORTING = false;
void report_value(const string& value_name, const string& value);
void report_function(const ReportStage stage, const string& class_name, const string& function_name);
string start_or_end(ReportStage stage, const string& start_string, const string& end_string);
FunctionType get_function_type(const string& class_name, const string& function_name);
void increment_depth();
void decrement_depth();
void stay_at_depth();
bool is_excluded(const string& class_name, const string& method_name);
string build_string(const string& token, const int n);
string indent_buffer();
string alignment_buffer(const int num_already_printed = 0);
void debug_print(const string& message = "", bool indented = false, bool aligned = false, const string start_token = "");
void debug_indent_line();
void debug_empty_line();

#endif
