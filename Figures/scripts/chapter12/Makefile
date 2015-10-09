# Appendix A Makefile
#

VERSION = 2.0
BASE = ../../
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

DATA = 2ed-table12-1.dat
THREADS = thread1.output thread2.output thread3.output thread4.output

all: $(DATA) 

data: $(DATA)

figure:
	@echo "There are no computed figures in chapter 12."

thread1.output:
	@echo "generating data for multithread convexhull..."
	(cd $(BASE); java -cp $(CP) algs.example.convexhull.parallel.Main 1) > thread1.output

thread2.output:
	@echo "generating data for multithread convexhull..."
	(cd $(BASE); java -cp $(CP) algs.example.convexhull.parallel.Main 2) > thread2.output

thread3.output:
	@echo "generating data for multithread convexhull..."
	(cd $(BASE); java -cp $(CP) algs.example.convexhull.parallel.Main 3) > thread3.output

thread4.output:
	@echo "generating data for multithread convexhull..."
	(cd $(BASE); java -cp $(CP) algs.example.convexhull.parallel.Main 4) > thread4.output

2ed-table12-1.dat: thread1.output thread2.output thread3.output thread4.output
	@echo "generating data for table 12-1 (2ed)..."
	cat thread[1-4].output | awk -f process.awk | sort -n > 2ed-table12-1.dat

clean:
	@rm -f *~ $(DATA) $(THREADS)

