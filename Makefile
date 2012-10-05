IMPLEMENTATIONS = java c++

default::
	@echo "Please select an implementation to build:" $(IMPLEMENTATIONS)

$(IMPLEMENTATIONS):
	(cd $@ && $(MAKE))
	(cp $@/encroach* .)

clean:
	(cd java && $(MAKE) clean)
	(cd c++ && $(MAKE) clean)

.PHONY: $(IMPLEMENTATIONS)
