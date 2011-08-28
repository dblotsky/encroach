#include <iostream>

using namespace std;

#include "E_Reporter.h"

// reports a method call
void report_method_call(const string& method_name, const string& class_name) {
    cerr << "Method \"" << method_name << "\" called on an object of class \"" << class_name << ".\"" << endl;
    return;
}

// reports a constructor call
void report_constructor(const string& class_name) {
    cerr << "An object of class \"" << class_name << "\" has been created." << endl;
    return;
}

// reports a destructor call
void report_destructor(const string& class_name) {
    cerr << "An object of class \"" << class_name << "\" has been deleted." << endl;
    return;
}
