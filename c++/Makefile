CXX      = g++
DEPFLAGS = -MMD
CXXFLAGS = -Wall -O -g $(DEPFLAGS)

# objects
VIEW_OBJS = Observer E_View E_Terminal E_Command
CTRL_OBJS = E_Controller
MODL_OBJS = Subject E_Node E_Game E_Owner E_Neutral E_Player E_Human E_AI E_Color E_Model
MAIN_OBJS = main E_Reporter E_Exception
ALL_OBJS  = $(MAIN_OBJS) $(VIEW_OBJS) $(MODL_OBJS) $(CTRL_OBJS)

# intermediates
OBJECTS 	 = $(ALL_OBJS:=.o)
DEPENDENCIES = $(ALL_OBJS:=.d)

# output
EXECUTABLE = encroach

# rules
all build: $(EXECUTABLE)

run: $(EXECUTABLE)
	./$(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

clean:
	$(RM) $(DEPENDENCIES) $(OBJECTS) $(EXECUTABLE)

# include generated dependency makefiles
-include $(DEPENDENCIES)

.PHONY: all build clean
