# Chapter 11 Makefile
#
# Hand-drawn Figures: 
# Computed Figures: 
#    Tables: 

VERSION = 2.0
PLOT = gnuplot
PYCODE = ../../../PythonCode
CODE = ../../../Code
BASE = ../../
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

# default rule to build PS files
%.ps: %.plot
	@if [ -f $*.ps ]; then chmod 644 $*.ps ; fi
	$(PLOT) < $*.plot

DATA = 2ed-table11-1.dat 2ed-table11-2.dat 2ed-table11-3.dat 2ed-table11-4.dat 
FIGS = 2ed-11-1.ps 2ed-11-2.ps
OTHER_DATA = 2ed-11-1.dat
THREADS = thread.11-1.1.dat thread.11-1.4.dat thread.11-1.8.dat

all: $(DATA) $(FIGS)

data: $(DATA)

figure: $(FIGS)

2ed-table11-1.dat:
	@echo "generating data for table 11-1 (2ed)..."
	(cd $(PYCODE); python -m book.chapter11) > 2ed-table11-1.dat

2ed-table11-2.dat:
	@echo "generating data for table 11-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter11.figure1.ComparisonDriver 1) > thread.11-1.1.dat
	awk -f 11-2.awk thread.11-1.1.dat > 2ed-table11-2.dat

# run this on quad computer and dual computer. Then name them appropriately
# quad_table_nr.dat and dual_table_nr.dat. Then make '2ed-11-2.ps'
# These cannot be removed during 'make clean'
table_nr.dat:
	@echo "generating data for figure 11-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter11.figure1.GenerateTable)>table_nr.dat

thread.11-1.4.dat:
	@echo "generating data for figure 11-1 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter11.figure1.ComparisonDriver 4) > thread.11-1.4.dat

2ed-11-2.ps: dual_table_nr.dat quad_table_nr.dat
	@echo "generating figure 11-2 (2ed)..."
	$(PLOT) < 2ed-11-2.plot

2ed-11-1.ps: thread.11-1.4.dat
	@echo "generating figure 11-1 (2ed)... "
	cat thread.11-1.4.dat | awk -f expand.awk > 2ed-11-1.dat
	$(PLOT) < 2ed-11-1.plot

2ed-table11-3.dat:
	@echo "generating table 11-3 (2ed)..."
	(cd $(PYCODE); python -m adk.counting) > 2ed-table11-3.dat	

2ed-table11-4.dat:
	@echo "generating table 11-4 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter11.table4.Main) > 2ed-table11-4.dat

clean:
	rm -f $(DATA) *~ $(THREADS) $(FIGS) $(OTHER_DATA)
