usage:
	@echo "usage:"
	@echo "    make [c|java|python]"

java Java:
	(cd Java && $(MAKE))
	cp Java/encroach .
	
c c++ C C++:
	(cd C++ && $(MAKE))
	cp C++/encroach .

run:
	./encroach
	
clean:
	(cd Java && $(MAKE) $@)
	(cd C++ && $(MAKE) $@)

python Python:
	@echo "Python version not implemented yet."

.PHONY: usage Java c C++ C c++ python Python
