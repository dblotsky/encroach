# use -XC to ssh with graphics

# compilation
CXX         = g++
CXXFLAGS    = -Wall -O -g -MMD

# objects
VIEW_OBJS   = E_Observer.o E_View.o E_Terminal.o
MODL_OBJS   = E_Subject.o E_Model.o E_Node.o E_Color.o
CTRL_OBJS   = E_Controller.o
FNCN_OBJS   = main.o E_Reporter.o
OBJECTS     = $(FNCN_OBJS) $(VIEW_OBJS) $(MODL_OBJS) $(CTRL_OBJS)

# outputs
DEPENDS     = ${OBJECTS:.o=.d}
EXECUTABLE  = encroach.out

# build rule
$(EXECUTABLE) : $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

# dependencies
-include $(DEPENDS)

# other targets
clean :
	rm -rf $(DEPENDS) $(OBJECTS) $(EXECUTABLE)

neat :
	rm -rf $(DEPENDS) $(OBJECTS)
