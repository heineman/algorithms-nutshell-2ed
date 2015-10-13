# Chapter 9 Makefile
#
# Hand-drawn Figures: 
# Computed Figures: 
#    Figures:
#    Tables: 

VERSION = 2.0
PLOT = gnuplot
CODE = ../../../Code
BASE = ../../
RATIO = ../ratio.awk
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

# default rule to build PS files
%.ps: %.plot
	@if [ -f $*.ps ]; then chmod 644 $*.ps ; fi
	$(PLOT) < $*.plot

DATA = 2ed-table9-2.dat 2ed-table9-3.dat 2ed-table9-4.dat 2ed-table9-5.dat 
COMPUTED = 2ed-9-5.dat
FIGS = 2ed-9-5.ps 

all: $(DATA) $(FIGS)

data: $(DATA)

figure: $(FIGS)

2ed-9-5.ps: 2ed-9-5.dat
	$(PLOT) < 2ed-9-5.plot

2ed-table9-2.dat:
	@echo "generating data for table 9-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter9.table2.Main -Xms512m -Xmx512m) > 2ed-table9-2.output
	awk -f 2ed-table9-2.awk  2ed-table9-2.output > 2ed-table9-2.dat
	@rm 2ed-table9-2.output

2ed-table9-3.dat: 2ed-9-5.dat
	@echo "generating data for table 9-3 (2ed)..."
	head -4 2ed-9-5.dat > 2ed-table9-3.dat

2ed-table9-5.dat:
	@echo "generating data for table 9-5 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter9.table5.Main) > 2ed-table9-5.dat

table9-8.dat:
	@echo "generating data for table 9-8..."
	(cd $(BASE); java -cp $(CP) algs.chapter9.table8.Main) > table9-8.dat

2ed-table9-4.dat:
	@echo "generating data for table 9-4 (2ed..."
	(cd $(BASE); java -cp $(CP) algs.chapter9.table4.Main) > 2ed-table9-4.dat

2ed-9-5.dat:
	@echo "generating data for figure 9-5 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter9.figure5.Main) > 9-5.output
	awk -f 2ed-figure9-5.awk 9-5.output > 2ed-9-5.dat
	@rm 9-5.output

clean:
	rm -f $(FIGS) $(DATA) $(COMPUTED) *~
