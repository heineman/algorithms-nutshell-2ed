# Chapter 5 Makefile
#
# Hand-drawn Figures: 
# Computed Figures: 
#    Figures:
#    Tables: 

VERSION = 2.0
PYCODE = ../../../PythonCode
CODE = ../../../Code
BASE = ../../
PLOT = gnuplot
RATIO = ../ratio.awk
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

DATA = 2ed-table5-1.dat 2ed-table5-2.dat 2ed-table5-3.dat 2ed-table5-4.dat 2ed-table5-5.dat 2ed-table5-6.dat 2ed-table5-7.dat 2ed-figure5-3.dat 2ed-figure5-5.dat

FIGS = 2ed-figure5-3.ps 2ed-figure5-5.ps

all: $(DATA) $(FIGS)

data: $(DATA)

figure: $(FIGS)


2ed-table5-1.dat:
	@echo "generating data for table 5-1 (2ed)..."
	(cd figures; bash 2ed-table5-1.sh) > 2ed-table5-1.dat

2ed-table5-2.dat:
	@echo "generating data for table 5-2 (2ed)..."
	(cd figures; bash 2ed-table5-2.sh ) > 2ed-table5-2.dat

2ed-table5-3.dat:
	@echo "generating data for table 5-3 (2ed)..."
	(cd figures; bash 2ed-table5-3.sh ) > 2ed-table5-3.dat

2ed-table5-4.dat:
	@echo "generating data for table 5-4 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter5.table4.Main) > 2ed-table5-4.dat

2ed-table5-5.dat:
	@echo "generating data for table 5-5 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter5.table5.Extended -Xms512m -Xmx512m) > 2ed-table5-5.output
	awk -f 2ed-table5-5.awk  2ed-table5-5.output > 2ed-table5-5.dat
	@rm 2ed-table5-5.output

2ed-table5-6.dat:
	@echo "generating data for table 5-6 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter5.table5.Main -Xms512m -Xmx512m) | awk -v type=hash -f 2ed-table5-6.awk > 2ed-table5-6.output
	(cd $(BASE); java -cp $(CP) algs.chapter5.table5.Extended -Xms512m -Xmx512m) | awk -v type=util -f 2ed-table5-6.awk >> 2ed-table5-6.output
	awk -f 2ed-table5-6a.awk 2ed-table5-6.output > 2ed-table5-6.dat
	@rm 2ed-table5-6.output

# same numbering in both editions
2ed-table5-7.dat:
	@echo "generating data for table 5-7 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter5.table7.Main -Xms512m -Xmx512m) | sed 's/,/	/g' > table5-7.output 

	# MAIN MODEST=4.0, EXTENDED=.75 NO REHASH
	(cd $(BASE); java -cp $(CP) algs.chapter5.table5.HashTableBuildTimes -Xms512m -Xmx512m) | sed 's/,/	/g' >> table5-7.output
	awk -f 2ed-table5-7.awk table5-7.output > 2ed-table5-7.dat
	@rm table5-7.output

2ed-figure5-3.dat:
	@echo "generating data for figure 5-3 (2ed)..."
	(cd $(PYCODE); python -m book.chapter5_open_addressing) >> 2ed-figure5-3.dat

2ed-figure5-3.ps: 2ed-figure5-3.dat
	@echo "generating figure 5-3 (2ed) ..."
	$(PLOT) < 2ed-5-3.plot

2ed-figure5-5.dat:
	@echo "generating data for figure 5-5 (2ed)..."
	(cd $(PYCODE); python -m book.chapter5_bloom) >> 2ed-figure5-5.dat

2ed-figure5-5.ps: 2ed-figure5-5.dat
	@echo "generating figure 5-5 (2ed) ..."
	$(PLOT) < 2ed-5-5.plot

clean:
	rm -f $(DATA) $(FIGS) *~
	rm -f figures/input.dat
