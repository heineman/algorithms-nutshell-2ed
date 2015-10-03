#
# Make sure that 'javac' and 'gnuplot' are in your PATH. All compilation
# is done with 'gcc' or 'g++'. Some scripts depend upon bash.
# 
# To generate the DATA for the figures (independently of the drawing of 
# those figures) use the 'data' target; once this is done, then use 
# the 'figure' target. That is:
#
#    make data
#    make figure

# no figures are automatically generated in chapter 8
SUBDIRS = chapter2 \
	chapter3 \
	chapter4 \
	chapter5 \
	chapter6 \
	chapter7 \
	chapter9 \
	chapter10 \
	chapter11 \
	chapter12 \
	appendixA

all: data figure

data:
	for i in $(SUBDIRS); do (cd "scripts/$${i}"; $(MAKE) data;  if [ $$? -ne 0 ]; then exit -1; fi;) done

figure:
	for i in $(SUBDIRS); do (cd "scripts/$${i}"; $(MAKE) figure; if [ $$? -ne 0 ]; then exit -1; fi;) done

validate:
	@echo -n "Available gcc binary: " 
	@which gcc
	@echo -n "       version: " 
	@gcc --version | head -1
	@echo -n "Available g++ version: " 
	@which g++
	@echo -n "       version: " 
	@g++ --version | head -1
	@echo -n "Avaliable perl : " 
	@which perl
	@perl --version | head -2
	@echo -n "Available javac version: " 
	@which javac
	@echo -n "Available javac version: " 
	@which java
	@echo -n "Available gnuplot version: " 
	@which gnuplot
	@echo -n "       version: " 
	@gnuplot --version | head -1
	@echo -n "Available GraphViz version: "
	@which dot
	@dot -V
	@which python
	@echo -n "       version: "
	@python --version 

clean:
	for i in $(SUBDIRS); do (cd "scripts/$${i}"; $(MAKE) clean;  if [ $$? -ne 0 ]; then exit -1; fi;) done
	(cd src/algs/chapter6; $(MAKE) clean; )
