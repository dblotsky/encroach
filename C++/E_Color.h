#ifndef _E_COLOR_
#define _E_COLOR_

enum E_Color {RED, BLUE, GREEN, YELLOW, PURPLE, BLANK};

int color_to_int(E_Color color);
E_Color int_to_color(int color_value);
E_Color string_to_color(const string& color_string);

#endif
