# use -XC to ssh with graphics

# compilation
CXX         = g++
CXXFLAGS    = -Wall -O -g -MMD

# objects
VIEW_OBJS   = E_Observer.o E_View.o E_Terminal.o
MODL_OBJS   = E_Subject.o E_Model.o
CTRL_OBJS   = E_Controller.o
FNCN_OBJS   = main.o E_Reporter.o
OBJECTS     = $(FNCN_OBJS) $(VIEW_OBJS) $(MODL_OBJS) $(CTRL_OBJS)

# outputs
DEPENDS     = ${OBJECTS:.o=.d}
EXECUTABLE  = encroach.out

$(EXECUTABLE): $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

-include $(DEPENDS)

clean :
	rm -rf $(DEPENDS) $(OBJECTS)

