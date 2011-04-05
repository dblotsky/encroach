Some loose coding style guidelines.
Established by Ryan and Dmitry for the Java version of Encroach.

Naming
------

class names:        CamelCase, CapitalizedFirstLetter, PrefixGameClassesWithE: EClass, EOtherClass, ELostTheGameClass
variable names:     lower_case, words_separated_by_underscores
function names:     see_variable_names
constant names:     UPPER_CASE, WORDS_SEPARATED_BY_UNDERSCORES
all names:          the more descriptive, the merrier

Formatting
----------

- declare imports at top of file
- use spaces for tabs (i.e. soft tabs); four spaces per tab
- use the 'this.' prefix when any variables have similar names inside a function
- put no spaces after loop keywords and comparisons, and one space after a condition:
    
    if(condition) {
    while(condition) {

- put the open brace on the same line as the beginning of the block (above example)
- put the closing brace on its own line:
    
    if(condition) {
        do_some_stuff();
    }

- "else"s and "catch"es go on the same line as the closing brace:
    
    if(condition) {
        do_some_stuff();
    } else if(condition) {
        do_some_stuff();
    } else {
        do_some_stuff();
    }
    
    try {
        do_some_stuff();
    } catch(Exception e) {
        do_some_stuff();
    }

- make a slash-star-star comment above every function
- double-slash comments get their own line
- condense boilerplate code ONLY if it makes it more readable and/or less obstructive:
    
    try { 
        do_a_short_thing();
    } catch(Exception e) {
        // do nothing
    }
    
    | | | | | |
    V V V V V V

    try { do_a_single_line_thing(); } catch(Exception e) {}


