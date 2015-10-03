#
# Make sure that 'javac' and 'gnuplot' are in your PATH. All compilation
# is done with 'gcc' or 'g++'. Some scripts depend upon bash.
# 

SUBDIRS = Timing \
	bin \
	Chapter1 \
	Chapter2 \
	Chapter3 \
	Chapter4 \
	Clock \
	Search \
	Sorting \
	Graph

all:
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE);  if [ $$? -ne 0 ]; then exit -1; fi;) done

validate:
	@echo -n "Available gcc binary: " 
	@which gcc
	@echo -n "       version: " 
	@gcc --version | head -1
	@echo -n "Available g++ version: " 
	@which g++
	@echo -n "       version: " 
	@g++ --version | head -1
	@echo -n "Available perl : " 
	@which perl
	@echo -n " version: "
	@perl --version | head -2
	@echo -n "Available javac version: " 
	@which javac
	@echo -n "Available javac version: " 
	@which java
	@echo -n "Available gnuplot version: " 
	@which gnuplot
	@echo -n "       version: " 
	@gnuplot --version | head -1
	@echo -n "Available python version: "
	@which python
	@echo -n "       version: "
	@python --version 

figure:
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) figure; if [ $$? -ne 0 ]; then exit -1; fi;) done

test:
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) test; if [ $$? -ne 0 ]; then exit -1; fi;) done

reports:
	(cd Chapter2; $(MAKE) FullReport)

doxygen:
	doxygen

# Clean can be in any order, but we keep same order as above for sanity
clean:
	for i in $(SUBDIRS); do (cd "$${i}"; $(MAKE) clean;) done
	rm -fr docs
	rm -f *~ core a.out

