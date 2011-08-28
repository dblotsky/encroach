#include <gtkmm.h>
#include <stdlib.h>
#include <iostream>

using namespace std;

#include "E_Controller.h"
#include "E_Model.h"
#include "E_Card.h"
#include "E_Player.h"
#include "E_Reporter.h"

E_Controller::E_Controller(E_Model* m) {
    report_constructor("E_Controller");
    
    model = m;
}

E_Controller::~E_Controller() {
    report_destructor("E_Controller");
}

void E_Controller::event_new_seed(int seed) {
    report_method_call("event_new_seed", "E_Controller");
    
    srand48(seed);
    return;
}

void E_Controller::event_card_selected(int rank, int suit) {
    report_method_call("event_card_selected", "E_Controller");
    
    // TODO: implement
    
    return;
}

void E_Controller::event_new_game() {
    report_method_call("event_new_game", "E_Controller");
    
    // TODO: implement
    
    return;
}

void E_Controller::event_add_player(string player_type) {
    report_method_call("event_add_player", "E_Controller");
    
    // TODO: implement
    
    return;
}

void E_Controller::event_ragequit() {
    report_method_call("event_ragequit", "E_Controller");
    
    // TODO: implement
    
    return;
}

void E_Controller::event_quit() {
    report_method_call("event_quit", "E_Controller");
    
    Gtk::Main::quit();
}

