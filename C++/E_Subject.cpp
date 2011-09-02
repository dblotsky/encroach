#include <set>

#include "E_Reporter.h"

#include "E_Subject.h"
#include "E_Observer.h"

using std::set;

void E_Subject::subscribe(E_Observer* new_observer) {
    prologue("E_Subject", "subscribe");
    
    observers.insert(new_observer);
    
    epilogue("E_Subject", "subscribe");
    return;
}

void E_Subject::unsubscribe(E_Observer* former_observer) {
    prologue("E_Subject", "unsubscribe");
    
    observers.erase(former_observer);
    
    epilogue("E_Subject", "unsubscribe");
    return;
}

void E_Subject::notify() {
    prologue("E_Subject", "notify");
    
    set<E_Observer*>::iterator i;
    for(i = observers.begin(); i != observers.end(); ++i) {
        (*i)->update();
    }
    
    epilogue("E_Subject", "notify");
    return;
}

