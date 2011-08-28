# use -XC to ssh with graphics

# compilation
CXX         = g++
CXXFLAGS    = -Wall -O -g -MMD

objects
VIEW_OBJS   = Observer.o View.o Terminal.o
MODL_OBJS   = Subject.o Model.o
CTRL_OBJS   = Controller.o
FNCN_OBJS   = main.o Reporter.o
OBJECTS 	= $(FNCN_OBJS) $(VIEW_OBJS) $(MODL_OBJS) $(CTRL_OBJS)

# outputs
DEPENDS     = ${OBJECTS:.o=.d}
EXECUTABLE  = encroach

$(EXECUTABLE): $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

-include $(DEPENDS)

clean :
	rm -rf $(DEPENDS) $(OBJECTS)

