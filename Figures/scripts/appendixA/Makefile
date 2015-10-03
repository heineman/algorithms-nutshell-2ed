# Appendix A Makefile
#

VERSION = 2.0
PLOT = gnuplot
PYCODE = ../../../PythonCode/
CODE = ../../../Code
BASE = ../../
CP = "../dist/ADK-$(VERSION)-ExamplesAndFigures.jar"

DATA = 2ed-tableA-1.dat 2ed-tableA-2.dat 2ed-tableA-3.dat 2ed-tableA-4.dat 2ed-tableA-5.dat 2ed-tableA-6.dat 2ed-tableA-7.dat

JAVA_OUTPUT = tableA-Java.output
PYTHON_OUTPUT = tableA-Python.output
C_OUTPUT = tableA-C.output
NANO_OUTPUT = tableA-nano.output

all: $(DATA) 

data: $(DATA)

figure:
	@echo "There are no computed figures in Appendix A."

$(C_OUTPUT):
	@echo "generating data for Table A-6 (2ed)..."
	(cd $(CODE)/Clock; ./tableA-6) > $(C_OUTPUT)

$(PYTHON_OUTPUT):
	@echo "generating data for Table A-6 (2ed)..."
	(cd $(PYCODE); python -m book.appendixA5) > $(PYTHON_OUTPUT)

2ed-tableA-4.dat: $(C_OUTPUT)
	@echo "generating data for Table A-4 (2ed)..."
	@echo "All time is in microseconds and must be /1000 to ms"
	awk -f A-4.awk < $(C_OUTPUT) > 2ed-tableA-4.dat

2ed-tableA-5.dat: $(PYTHON_OUTPUT)
	@echo "generating data for Table A-5 (2ed) ..."
	cat $(PYTHON_OUTPUT) > 2ed-tableA-5.dat

2ed-tableA-6.dat: $(C_OUTPUT)
	@echo "generating data for Table A-6 (2ed) ..."
	awk -f A-6.awk < $(C_OUTPUT) > 2ed-tableA-6.dat

$(JAVA_OUTPUT):
	@echo "generating data for Tables A-2 and A-3 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.appendixA.example1.Main) > $(JAVA_OUTPUT)

2ed-tableA-3.dat: $(JAVA_OUTPUT)
	@echo "generating data for Table A-3 (2ed)..."
	awk -f A-3.awk < $(JAVA_OUTPUT) > 2ed-tableA-3.dat

2ed-tableA-2.dat: $(JAVA_OUTPUT)
	@echo "generating data for Table A-2 (2ed)..."
	awk -f A-2.awk < $(JAVA_OUTPUT) > 2ed-tableA-2.dat

2ed-tableA-7.dat:
	@echo "generating data for Tables A-7 (2ed)..."
	(cd $(BASE); java -cp $(CP) algs.appendixA.table7.Main) | awk -f A-7.awk > 2ed-tableA-7.dat

2ed-tableA-1.dat:
	@echo "k	Probability" >  2ed-tableA-1.dat
	@echo "1	0.6827"      >> 2ed-tableA-1.dat
	@echo "2	0.9545"      >> 2ed-tableA-1.dat
	@echo "3	0.9973"      >> 2ed-tableA-1.dat
	@echo "4	0.9999"      >> 2ed-tableA-1.dat
	@echo "5	1"           >> 2ed-tableA-1.dat


clean:
	@rm -f *~ $(DATA) $(JAVA_OUTPUT) $(C_OUTPUT) $(NANO_OUTPUT) $(PYTHON_OUTPUT)
