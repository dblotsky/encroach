#include "E_Reporter.h"
#include "E_Player.h"

E_Player::E_Player() {
	prologue("E_Player");
	epilogue("E_Player");
}

E_Player::~E_Player() {
	prologue("E_Player", "~E_Player");
	epilogue("E_Player", "~E_Player");
}
