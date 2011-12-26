# compilation
CXX       = g++
CXXFLAGS  = -Wall -O -g -MMD

# objects
VIEW_OBJS = E_Observer.o E_View.o E_Terminal.o E_Command.o
CTRL_OBJS = E_Controller.o

PLAY_OBJS = E_Owner.o E_Neutral.o E_Player.o E_Human.o E_AI.o
GAME_OBJS = E_Node.o E_Color.o E_Game.o
MODL_OBJS = E_Subject.o E_Model.o $(GAME_OBJS) $(PLAY_OBJS)

FNCN_OBJS = main.o E_Reporter.o E_Exception.o

OBJECTS   = $(FNCN_OBJS) $(VIEW_OBJS) $(MODL_OBJS) $(CTRL_OBJS)

# outputs
DEPENDS = ${OBJECTS:.o=.d}
EXECUTABLE = encroach.exe

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
