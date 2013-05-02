default::
	@echo "Please select an implementation to build:"
	@echo "    make java"
	@echo "    make c++"
	@echo "    make all"

all: java c++

java c++:
	@(cd $@ && $(MAKE))
	@cp $@/encroach* .

clean:
	@(cd java && $(MAKE) clean)
	@(cd c++ && $(MAKE) clean)

.PHONY: java c++ all clean
