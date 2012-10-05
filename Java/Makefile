# compiler and archiver
JC = javac
JAR = jar

# flags
JFLAGS = 
JARFLAGS = cvfm

# sources and manifest
# TODO: ensure that there is ONLY ONE CLASS PER FILE
CLASSES = Encroach EPlayer EBoard ESquare ETerminal
EXECUTABLE = Encroach.jar
MANIFEST = Manifest.mf

# files
SOURCES = $(CLASSES:=.java)
OBJECTS = $(SOURCES:.java=.class)

# main rule
run: $(EXECUTABLE)
	java -jar $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)

%.class: %.java
	$(JC) $(JFLAGS) $<

%.jar: %.class
	$(JAR) $(JARFLAGS) $@ $(MANIFEST) *.class

clean:
	$(RM) *.class $(EXECUTABLE) 
