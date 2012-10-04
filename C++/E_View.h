#ifndef _E_VIEW_
#define _E_VIEW_

#include "Observer.h"
#include "E_Controller.h"
#include "E_Model.h"

class E_View: public Observer {
    public:
        E_View(E_Model*, E_Controller*);
        virtual ~E_View();
        virtual void update() = 0;
        
    protected:
        E_Model*      model;
        E_Controller* controller;
};

#endif
