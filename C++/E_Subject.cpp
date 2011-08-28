#include <set>

using namespace std;

#include "E_Subject.h"
#include "E_Observer.h"
#include "E_Reporter.h"

void E_Subject::subscribe(E_Observer* new_observer) {
    report_method_call("subscribe", "E_Observer");
    
    observers.insert(new_observer);
    return;
}

void E_Subject::unsubscribe(E_Observer* former_observer) {
    report_method_call("unsubscribe", "E_Observer");
    
    observers.erase(former_observer);
    return;
}

void E_Subject::notify() {
    report_method_call("notify", "E_Subject");
    
    set<E_Observer*>::iterator i;
    for(i = observers.begin(); i != observers.end(); ++i) {
        (*i)->update();
    }
    return;
}

