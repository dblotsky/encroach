# compiler and archiver
JC = javac
JAR = jar

# flags
JFLAGS = 
JARFLAGS = cvfm

# sources and manifest
# TODO: ensure that there is ONLY ONE CLASS PER FILE
CLASSES = Encroach EPlayer EBoard ESquare ETerminal
MANIFEST = Manifest.mf

# intermediate outputs
OBJECTS = $(CLASSES:=.class)
ARCHIVE = Encroach.jar

# final output
EXECUTABLE = encroach.jar

# main rule
$(EXECUTABLE): $(ARCHIVE)
	mv $(ARCHIVE) $(EXECUTABLE)

all build: $(EXECUTABLE)

run: build
	java -jar $(EXECUTABLE)

$(ARCHIVE): $(OBJECTS)

%.class: %.java
	$(JC) $(JFLAGS) $<

%.jar: %.class
	$(JAR) $(JARFLAGS) $@ $(MANIFEST) *.class

clean:
	$(RM) *.class $(EXECUTABLE) 
