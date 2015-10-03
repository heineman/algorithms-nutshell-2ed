# Chapter 7 Makefile
#

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

FIGS = 2ed-7-30.ps 2ed-7-28.ps 2ed-7-24.ps 2ed-7-23.ps 2ed-7-21.ps 2ed-7-19.ps 2ed-7-15.ps 2ed-7-13.ps 2ed-7-10.ps 2ed-7-8.ps 2ed-7-7.ps 2ed-7-5.ps

DATA = 2ed-table7-1.dat 2ed-table7-2.dat 2ed-table7-3.dat 

all: $(DATA) $(FIGS)

data: $(DATA)

figure: $(FIGS)

2ed-7-30.ps: 2ed-table7-3.dat
	@echo "generating Figure 7-30 (2ed)..."
	$(PLOT) < 2ed-7-30.plot
	rm -f fit.log

2ed-table7-3.dat:
	@echo "generating data for table 7-3 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.table3.Main > $(PWD)/2ed-table7-3.dat)

2ed-7-28.ps:
	@echo "generating Figure 2ed-7-28 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure28.Main > $(PWD)/2ed-7-28.dotty)
	dot -Tps 2ed-7-28.dotty > 2ed-7-28.ps
	rm 2ed-7-28.dotty

2ed-7-24.ps:
	@echo "generating Figure 2ed-7-24 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure24.Main > $(PWD)/2ed-7-24.dotty)
	dot -Tps 2ed-7-24.dotty > 2ed-7-24.ps
	rm 2ed-7-24.dotty

2ed-7-23.ps:
	@echo "generating Figure 7-23 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure23.Main > $(PWD)/2ed-7-23.dotty)
	dot -Tps 2ed-7-23.dotty > 2ed-7-23.ps
	rm 2ed-7-23.dotty

2ed-7-21.ps:
	@echo "generating Figure 7-21 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure21.Main > $(PWD)/2ed-7-21.dotty)
	dot -Tps 2ed-7-21.dotty > 2ed-7-21.ps
	rm 2ed-7-21.dotty

2ed-7-19.ps:
	@echo "generating Figure 7-19 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure19.Main > $(PWD)/2ed-7-19.out)
	#
	# Don't try to graph Unbounded one...
	awk -f 2ed-figure7-19.awk 2ed-7-19.out | head -30 > 2ed-7-19.dat
	rm 2ed-7-19.out 
	$(PLOT) < 2ed-figure7-19.plot
	rm 2ed-7-19.dat


2ed-7-15.ps:
	@echo "generating Figure 7-15 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure15.Main > $(PWD)/2ed-7-15.dotty)
	dot -Tps 2ed-7-15.dotty > 2ed-7-15.ps
	rm 2ed-7-15.dotty

2ed-7-13.ps:
	@echo "generating Figure 7-13 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure13.Main > $(PWD)/2ed-7-13.dotty)
	dot -Tps 2ed-7-13.dotty > 2ed-7-13.ps
	rm 2ed-7-13.dotty

2ed-7-10.ps:
	@echo "generating Figure 7-10 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure10.Main > $(PWD)/2ed-7-10.dotty)
	dot -Tps 2ed-7-10.dotty > 2ed-7-10.ps
	rm 2ed-7-10.dotty

2ed-7-8.ps:
	@echo "generating Figure 7-8 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure8.Main > $(PWD)/2ed-7-8.dotty)
	dot -Tps 2ed-7-8.dotty > 2ed-7-8.ps
	rm 2ed-7-8.dotty

2ed-7-7.ps:
	@echo "generating Figure 7-7 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure7.Main > $(PWD)/2ed-7-7.dotty)
	dot -Tps 2ed-7-7.dotty > 2ed-7-7.ps
	rm 2ed-7-7.dotty

2ed-7-5.ps:
	@echo "generating Figure 7-5 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.figure5.Main > $(PWD)/2ed-7-5.dotty)
	dot -Tps 2ed-7-5.dotty > 2ed-7-5.ps
	rm 2ed-7-5.dotty

2ed-table7-1.dat:
	@echo "generating data for table 7-1 (2ed) ..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.table1.Main > $(PWD)/2ed-table7-1.dat)

2ed-table7-2.dat:
	@echo "generating data for table 7-2 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.chapter7.table2.Main > $(PWD)/2ed-table7-2.dat)


clean:
	rm -f $(FIGS) $(DATA) *~ $(DOTTY) fit.log