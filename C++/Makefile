# use -XC to ssh with graphics

# compilation
CXX         = g++
CXXFLAGS    = -Wall -O -g -MMD

# objects
# VIEW_OBJS   =
# MODL_OBJS   =
# CTRL_OBJS   =
FNCN_OBJS   = main.o
OBJECTS     = $(FNCN_OBJS)

# outputs
DEPENDS     = ${OBJECTS:.o=.d}
EXECUTABLE  = encroach

$(EXECUTABLE): $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

-include $(DEPENDS)

clean :
	rm -rf $(DEPENDS) $(OBJECTS)

