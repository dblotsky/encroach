# compilation
CXX = g++

# dependency flags - generate .d makefiles based on #include directives
DEPFLAGS = -MMD

# other flags
CXXFLAGS  = -Wall -O -g $(DEPFLAGS)

# objects
VIEW_OBJS = Observer.o E_View.o E_Terminal.o E_Command.o

CTRL_OBJS = E_Controller.o

PLAY_OBJS = E_Owner.o E_Neutral.o E_Player.o E_Human.o E_AI.o
GAME_OBJS = E_Color.o E_Node.o E_Game.o
MODL_OBJS = Subject.o E_Model.o $(GAME_OBJS) $(PLAY_OBJS)

MAIN_OBJS = main.o E_Reporter.o E_Exception.o

# outputs
OBJECTS = $(MAIN_OBJS) $(VIEW_OBJS) $(MODL_OBJS) $(CTRL_OBJS)
DEPENDENCIES = ${OBJECTS:.o=.d}
EXECUTABLE = encroach

run:: build
	./$(EXECUTABLE)

build all:: $(EXECUTABLE)

$(EXECUTABLE):: $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(EXECUTABLE)

# include generated dependency makefiles
-include $(DEPENDENCIES)

clean::
	rm -rf $(DEPENDENCIES) $(OBJECTS) $(EXECUTABLE)
