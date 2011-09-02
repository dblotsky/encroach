#include <iostream>
#include <string>
#include <sstream>

using std::string;
using std::cout;
using std::endl;
using std::stringstream;

int main() {
	stringstream s;
	s << "stuff";
	// clear the stringstream
	cout << s.str() << endl;
	return 0;
}
