IMPLEMENTATIONS = java c++

default::
	@echo "Please select an implementation to build:" $(IMPLEMENTATIONS)

$(IMPLEMENTATIONS):
	(cd $@ && $(MAKE))
	(cp $@/encroach* .)

all:
	$(foreach implementation, $(IMPLEMENTATIONS), $(MAKE) ${implementation};)

clean:
	$(foreach implementation, $(IMPLEMENTATIONS), $(MAKE) ${clean};)

.PHONY: $(IMPLEMENTATIONS)
