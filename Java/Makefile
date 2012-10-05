# compiler and archiver
JC = javac
JAR = jar

# flags
JFLAGS = 
JARFLAGS = cvfm

# sources and manifest
CLASSES  = EPlayer EBoard ESquare
MAIN     = Encroach.jar
MANIFEST = Manifest.mf

# files
SOURCES = $(CLASSES:=.java)
OBJECTS = $(SOURCES:.java=.class)

build: $(MAIN)

$(MAIN): $(OBJECTS)

%.class: %.java
	$(JC) $(JFLAGS) $<

%.jar: %.class
	$(JAR) $(JARFLAGS) $@ $(MANIFEST) *.class

clean:
	$(RM) *.class $(MAIN) 
