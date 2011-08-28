#include <set>

#include "E_Subject.h"
#include "E_Observer.h"
#include "E_Reporter.h"

using std::set;

void E_Subject::subscribe(E_Observer* new_observer) {
    report_method("subscribe", "E_Subject", PROLOGUE);
    
    observers.insert(new_observer);
    
    report_method("subscribe", "E_Subject", EPILOGUE);
    return;
}

void E_Subject::unsubscribe(E_Observer* former_observer) {
    report_method("unsubscribe", "E_Subject", PROLOGUE);
    
    observers.erase(former_observer);
    
    report_method("unsubscribe", "E_Subject", EPILOGUE);
    return;
}

void E_Subject::notify() {
    report_method("notify", "E_Subject", PROLOGUE);
    
    set<E_Observer*>::iterator i;
    for(i = observers.begin(); i != observers.end(); ++i) {
        (*i)->update();
    }
    
    report_method("notify", "E_Subject", EPILOGUE);
    return;
}

