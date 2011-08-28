#ifndef _E_SUBJECT_
#define _E_SUBJECT_

#include <set>

using namespace std;

#include "E_Observer.h"

class E_Subject {
    public:
       void subscribe(E_Observer*);
       void unsubscribe(E_Observer*);
    
    protected:
       void notify();
    
    private:
       set<E_Observer*> observers;
    
}; // E_Subject

#endif

