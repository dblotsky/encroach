IMPLEMENTATIONS = java c++

default::
	@echo "Please select an implementation to build:" $(IMPLEMENTATIONS)

java:
	(cd $@ && $(MAKE))

c++:
	(cd $@ && $(MAKE))
	
clean: $(IMPLEMENTATIONS)
	(cd $< && $(MAKE) $@)
	
.PHONY: $(IMPLEMENTATIONS)
