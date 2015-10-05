# Chapter 10 Makefile [Spatial Tree Structures]
#
# Hand-drawn Figures: 
# Computed Figures: 
#    Figures:
#    Tables: 

VERSION = 2.0
PLOT = gnuplot
PYCODE = ../../../PythonCode
CODE = ../../../Code
BASE = ../../
RATIO = ../ratio.awk
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

# default rule to build PS files
%.ps: %.plot
	@if [ -f $*.ps ]; then chmod 644 $*.ps ; fi
	$(PLOT) < $*.plot


DATA = 2ed-table10-1.dat 2ed-table10-2.dat 2ed-table10-3.dat 2ed-table10-4567.dat 

COMPUTED = 2ed-10-9.dat  2ed-10-12.dat figure10-7.dat
FIGS = 2ed-10-7.ps 2ed-10-9.ps 2ed-10-11.ps 2ed-10-12.ps

all: $(DATA) $(FIGS)

data: $(DATA)

figure: $(FIGS)

# was table9-6
2ed-table10-1.dat:
	@echo "generating data for table 10-1 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter10.table1.Main) > 2ed-table10-1.dat

2ed-table10-2.dat:
	@echo "generating data for table 10-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter10.table2.Main) > 2ed-table10-2.dat


figure10-7.dat:
	@echo "generating data for figure 10-7 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter10.figure7.Main -Xms1024m -Xmx2048m) > figure10-7.dat

2ed-10-7.ps: figure10-7.dat
	$(PLOT) < 2ed-10-7.plot

2ed-10-9.dat:
	@echo "generating data for figure 10-9 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter10.figure9.Main -Xms1024m -Xmx2048m) > 2ed-10-9.dat

2ed-10-9.ps: 2ed-10-9.dat
	$(PLOT) < 2ed-10-9.plot

2ed-10-11.ps: 
	@echo "generating figure 10-11 (2ed)..."
	$(PLOT) < 2ed-10-11.plot

2ed-10-12.ps: 2ed-10-12.dat
	@echo "generating figure 10-12 (2ed)..."
	$(PLOT) < 2ed-10-12.plot

2ed-10-12.dat:
	@echo "generating data for figure 10-12 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter10.figure12.Main -Xms512m -Xms512m) > 2ed-10-12.dat

2ed-table10-3.dat:
	@echo "generating data for table 10-3 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter10.table3.Main) > 2ed-table10-3.dat

2ed-table10-4567.dat:
	@echo "generating data for table 10-4,10-5,10-6,10-7 (2ed)..."
	(cd $(PYCODE); python -m book.chapter10_table4 ) > 2ed-table10-4567.dat

clean:
	rm -f $(FIGS) $(DATA) $(COMPUTED) *~
