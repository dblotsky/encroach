# compilation
CXX = g++

# dependency flags - generate .d makefiles based on #include directives
DEPFLAGS = -MMD

# other flags
CXXFLAGS  = -Wall -O -g $(DEPFLAGS)

# objects
VIEW_OBJS = Observer E_View E_Terminal E_Command
CTRL_OBJS = E_Controller
MODL_OBJS = Subject E_Node E_Game E_Owner E_Neutral E_Player E_Human E_AI E_Color E_Model
MAIN_OBJS = main E_Reporter E_Exception

# outputs
OBJECTS = $(MAIN_OBJS=.o) $(VIEW_OBJS=.o) $(MODL_OBJS=.o) $(CTRL_OBJS=.o)
DEPENDENCIES = ${OBJECTS:.o=.d}
EXECUTABLE = encroach

run:: $(EXECUTABLE)
	./$(EXECUTABLE)

build:: $(EXECUTABLE)

$(EXECUTABLE):: $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

# include generated dependency makefiles
-include $(DEPENDENCIES)

clean::
	rm -rf $(DEPENDENCIES) $(OBJECTS) $(EXECUTABLE)
