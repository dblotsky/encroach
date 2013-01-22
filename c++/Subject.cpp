#include <set>
#include "E_Reporter.h"
#include "Subject.h"
#include "Observer.h"

using std::set;

void Subject::subscribe(Observer* new_observer) {
    prologue("Subject", "subscribe");
    
    observers.insert(new_observer);
    
    epilogue("Subject", "subscribe");
    return;
}

void Subject::unsubscribe(Observer* former_observer) {
    prologue("Subject", "unsubscribe");
    
    observers.erase(former_observer);
    
    epilogue("Subject", "unsubscribe");
    return;
}

void Subject::notify() {
    prologue("Subject", "notify");
    
    set<Observer*>::iterator i;
    for(i = observers.begin(); i != observers.end(); ++i) {
        (*i)->update();
    }
    
    epilogue("Subject", "notify");
    return;
}

