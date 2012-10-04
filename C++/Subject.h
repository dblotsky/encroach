#ifndef _E_SUBJECT_
#define _E_SUBJECT_

#include <set>
#include "Observer.h"

using std::set;

class Subject {
    public:
        void subscribe(Observer*);
        void unsubscribe(Observer*);
        
    protected:
        void notify();
        
    private:
        set<Observer*> observers;
        
};

#endif
