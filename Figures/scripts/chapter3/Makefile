# Chapter 3 Makefile
#
# Hand-drawn Figures: 3-1, 3-2, table 3-4
# Computed Figures: 
#    Tables: 3-1, 3-2, 3-3

VERSION = 2.0
CODE = ../../../Code
BASE = ../../
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

DATA = 2ed-table3-1.dat 2ed-table3-2.dat 2ed-table3-3.dat

all: $(DATA)

data: $(DATA)

figure:
	@echo "no figures in chapter 3"

2ed-table3-1.dat:
	@echo "generating data for Table 3-1 (2ed)..."
	(cd $(CODE)/Chapter3/table3-1; $(MAKE); $(MAKE) 2ed-table3-1)
	@cp $(CODE)/Chapter3/table3-1/2ed-table3-1.dat .

2ed-table3-2.dat:
	@echo "generating data for Table 3-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter3.table2.Main) > 2ed-table3-2.dat

2ed-table3-3.dat:
	@echo "generating data for Table 3-3 (2ed..."
	(cd $(BASE); java -cp $(CP) algs.chapter3.table3.Main) > 2ed-table3-3.dat

clean:
	rm -f $(DATA) *~

