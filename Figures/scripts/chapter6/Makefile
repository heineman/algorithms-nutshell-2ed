# Chapter 5 Makefile
#

VERSION = 2.0
CODE = ../../../Code
BASE = ../../
RATIO = ../ratio.awk
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

DATA = 2ed-table6-1.dat 2ed-table6-2.dat 2ed-table6-3.dat 2ed-figure6-8.dat 2ed-figure6-10.dat 

all: $(DATA) 

data: $(DATA)

figure:
	@echo "There are no computed figures in chapter 6"

2ed-table6-1.dat:
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; chmod 755 ./2ed-Table6-1.sh)
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; ./2ed-Table6-1.sh)
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; cat 2ed-Table6-1.out) | awk -f 2ed-table6-1.awk | sort -n > 2ed-table6-1.dat
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; rm -f 2ed-Table6-1.out)

2ed-table6-2.dat:
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; chmod 755 ./2ed-Table6-2.sh)
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; ./2ed-Table6-2.sh)
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; cat 2ed-Table6-2.out) | awk -f 2ed-table6-2.awk > 2ed-table6-2.dat
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; rm -f 2ed-Table6-2.out)

2ed-table6-3.dat:
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; chmod 755 ./2ed-Table6-3.sh)
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; ./2ed-Table6-3.sh)
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; cat 2ed-Table6-3.out) | awk -f 2ed-table6-3.awk | sort +2 -2 -r -n > 2ed-table6-3.dat
	(cd $(CODE)/Graph/SingleSourceShortestPath/Tables; rm -f 2ed-Table6-3.out)

2ed-figure6-8.dat:
	@echo "generating data for figure 6-8 (2ed)..."
	(cd $(BASE)/src/algs/chapter6; $(MAKE) figure6-8)
	(cd $(BASE)/src/algs/chapter6; ./figure6-8) > 2ed-figure6-8.dat

2ed-figure6-10.dat:
	@echo "generating data for figure 6-10 (2ed)..."
	(cd $(BASE)/src/algs/chapter6; $(MAKE) figure6-10)
	(cd $(BASE)/src/algs/chapter6; ./figure6-10) > 2ed-figure6-10.dat

2ed-figure6-14.dat:
	@echo "generating data for figure 6-14 (2ed..."
	(cd $(CODE)/Graph/SingleSourceShortestPath; $(MAKE) testGraph; )
	(cd $(CODE)/Graph/SingleSourceShortestPath; ./testGraph -v -f Graphs/2ed-figure6-14.dat) > 2ed-figure6-14.dat


clean:
	rm -f $(DATA) *~

