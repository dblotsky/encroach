#ifndef _E_VIEW_
#define _E_VIEW_

#include "E_Observer.h"
#include "E_Controller.h"
#include "E_Model.h"

class E_View : public E_Observer {
    public:
        E_View(E_Controller*, E_Model*);
        virtual ~E_View();
        virtual void update();
        
    protected:
        E_Controller*   controller;
        E_Model*        model;
        
}; // E_View

#endif
