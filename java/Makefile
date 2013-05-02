JC       = javac
JAR      = jar
JFLAGS   = 
JARFLAGS = cvfm

# sources and manifest
SOURCES  = Encroach EPlayer EBoard ESquare ETerminal
MANIFEST = Manifest.mf

# intermediates
CLASSES = $(SOURCES:=.class)
ARCHIVE = Encroach.jar

# output
EXECUTABLE = encroach.jar

# rules
all build: $(EXECUTABLE)

run: build
	java -jar $(EXECUTABLE)

$(EXECUTABLE): $(ARCHIVE)
	mv $(ARCHIVE) $(EXECUTABLE)

$(ARCHIVE): $(CLASSES) $(MANIFEST)

%.class: %.java
	$(JC) $(JFLAGS) $<

%.jar: %.class
	$(JAR) $(JARFLAGS) $@ $(MANIFEST) *.class

clean:
	$(RM) *.class $(EXECUTABLE)
