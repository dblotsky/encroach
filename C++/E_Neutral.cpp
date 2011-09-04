#include "E_Reporter.h"
#include "E_Neutral.h"

E_Neutral::E_Neutral() {
	prologue("E_Neutral");
	epilogue("E_Neutral");
}

E_Neutral::~E_Neutral() {
	prologue("E_Neutral", "~E_Neutral");
	epilogue("E_Neutral", "~E_Neutral");
}
