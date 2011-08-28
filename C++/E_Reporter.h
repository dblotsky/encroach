#ifndef _E_REPORTER_
#define _E_REPORTER_

#include <iostream>

using namespace std;

void report_method_call(const string& method_name, const string& class_name);
void report_constructor(const string& class_name);
void report_destructor(const string& class_name);

/*
report_method_call("", "");
report_constructor("");
report_destructor("");
*/

#endif

