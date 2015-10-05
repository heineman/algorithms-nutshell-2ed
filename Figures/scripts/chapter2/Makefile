# Chapter 2 Makefile
#
# Hand-drawn Figures: 
# Computed Figures: 
#    Sidebar Two: Effect of Encoding on Performance (sidebar2-2.dat)
# NEW
#    Figures: 2-1, 2-2, 2-3, 2-4, 2-5, 2-6, 2-7
# OLD
#    Tables: 2-1, 2-4, 2-5, 2-6

VERSION = 2.0
SCHEME = racket
PLOT = gnuplot
CODE = ../../../Code
BASE = ../../
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"
USCONV = ../usconv.awk
MSCONV = ../msconv.awk

# default rule to build PS files
%.ps: %.plot
	@if [ -f $*.ps ]; then chmod 644 $*.ps ; fi
	$(PLOT) < $*.plot

# Figures to be built
FIGS = 2ed-2-1.ps 2ed-2-2.ps 2ed-2-3.ps 2ed-2-4.ps 2ed-2-5.ps 2ed-2-6.ps 2ed-2-7.ps 
DATA = 2ed-table2-2.dat 2ed-table2-3.dat 2ed-table2-4.dat 2ed-table2-5.dat 2ed-figure2-1.dat 2ed-figure2-2.dat 2ed-figure2-3.dat 2ed-table2-4.dat 2ed-table2-5.dat 2ed-figure2-6.dat 2ed-figure2-7.dat

all: $(DATA) $(FIGS)

data: $(DATA)

figure: $(FIGS)

# dependencies between figures and data
2ed-2-1.ps: 2ed-figure2-1.dat
2ed-2-2.ps: 2ed-figure2-2.dat
2ed-2-3.ps: 2ed-figure2-3.dat
2ed-2-4.ps: 2ed-table2-4.dat
2ed-2-5.ps: 2ed-table2-5.dat
2ed-2-6.ps: 2ed-figure2-6.dat
2ed-2-7.ps: 2ed-figure2-7.dat


2ed-table2-2.dat:
	@echo "generating data for Table 2-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter2.table2.Newton) > 2ed-table2-2.dat

2ed-figure2-1.dat:
	@echo "generating data for Figure 2-1 (2ed)..."
	# time in seconds. Must convert to microseconds
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figure2-1.rc) | sed 's/,/	/g' | tail -n +2 | awk -f $(PWD)/$(USCONV) > 2ed-figure2-1.dat

2ed-figure2-2.dat:
	@echo "generating data for Figure 2-2 (2ed)..."	
	# time in seconds. Must convert to microseconds
	@echo "# Top side of figure (sorted -O3 data)" > 2ed-figure2-2.dat
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figure2-2a.rc) | sed 's/,/	/g' | tail -n +2 | awk -f $(PWD)/$(USCONV) >> 2ed-figure2-2.dat
	@echo "" >> 2ed-figure2-2.dat
	@echo "" >> 2ed-figure2-2.dat
	@echo "# Bottom side of figure (nearly sorted, 20 out of order)" >> 2ed-figure2-2.dat
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figure2-2b.rc) | sed 's/,/	/g' | tail -n +2 | awk -f $(PWD)/$(USCONV) >> 2ed-figure2-2.dat

2ed-figure2-3.dat:
	@echo "generating data for Figure 2-3 (2ed)..."
	chmod 755 Figure2-3/buildAll.sh Figure2-3/runAll.sh
	(cd Figure2-3; ./buildAll.sh)
	(cd Figure2-3; ./runAll.sh)
	awk -f $(PWD)/$(MSCONV) < Figure2-3/out.dat > 2ed-figure2-3.dat

2ed-figure2-6.dat:
	@echo "generating data for Figure 2-6 (2ed)..."
	python 2-6.py | sed 's/(//' | sed 's/)//' > 2ed-figure2-6.dat

2ed-figure2-7.dat:
	@echo "generating data for Figure 2-7 (2ed)..."
	python 2-7.py | sed 's/(//' | sed 's/)//' > 2ed-figure2-7.dat


2ed-table2-5.dat:
	@echo "generating data for Table 2-5 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter2.table5.Main) > 2ed-table2-5.dat

2ed-table2-4.dat:
	@echo "generating data for Table 2-4 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter2.table4.Main) > 2ed-table2-4.dat

2ed-table2-3.dat:
	@echo "generating data for Table 2-3 (2ed)..."
	(cd $(BASE)/../Code/Chapter2; make FullReport)
	perl combine.pl $(BASE)../Code/Chapter2/report.g $(BASE)../Code/Chapter2/report.none $(BASE)../Code/Chapter2/report.O3 $(BASE)../Code/Chapter2/report.java > 2ed-table2-3.dat

clean:
	rm -f $(FIGS) $(DATA) *~
	chmod 755 Figure2-3/cleanAll.sh 
	(cd Figure2-3; ./cleanAll.sh)
