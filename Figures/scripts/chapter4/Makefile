# Chapter 4 Makefile
#
# Numbering: 2ed- for all 2nd edition figures

CODE = ../../../Code
BASE = ../../
RATIO = ../ratio.awk

DOTTY = 2ed-4-7.ps 2ed-4-8.ps

DATA = claim_page78.dat 2ed-table4-3.dat 2ed-table4-4.dat 2ed-table4-5.dat 2ed-table4-6.dat 2ed-table4-7.dat 2ed-table4-8.dat 

all: $(DATA) $(DOTTY)

data: $(DATA)

figure: $(DOTTY)

claim_page78.dat:
	@echo "generating data for Claim on Page 78..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/compare-bucket-array-vs-list.rc) | sed 's/,/	/g' | tail -n +2 > claim_page78.dat


# 2ed-table4-1 contains stable sort example
# 2ed-table4-2 contains criteria for choosing sorting algorithm

2ed-table4-3.dat:
	@echo "generating data for Table 4-3 (2ed)..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/2ed-table4-3.rc) | sed 's/,/	/g' | tail -n +2 | awk -f $(RATIO) > 2ed-table4-3.dat

# 2ed-figure4-4 is hand-drawn example
# 2ed-figure4-5 is hand-drawn example
# 2ed-figure4-6 is hand-drawn example

2ed-table4-4.dat:
	@echo "generating data for Table 4-4 (2ed)..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/2ed-table4-4.rc) | sed 's/,/	/g' | tail -n +2 > 2ed-table4-4.dat

2ed-4-7.ps:
	@echo "generating Figure 4-7 (2ed)..."
	(cd $(CODE)/Sorting/Longs; $(MAKE))
	dot -Tps $(CODE)/Sorting/Longs/figure4-7.dotty > 2ed-4-7.ps

2ed-4-8.ps:
	@echo "generating Figure 4-8 (2ed)..."
	(cd $(CODE)/Sorting/Longs; $(MAKE))
	dot -Tps $(CODE)/Sorting/Longs/figure4-8.dotty > 2ed-4-8.ps

# 2ed-figure4-9 is hand-drawn example

2ed-table4-5.dat:
	@echo "generating data for Table 4-5 (2ed)..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/2ed-table4-5.rc) | sed 's/,/	/g' | tail -n +2 > 2ed-table4-5.dat


2ed-table4-6.dat:
	@echo "generating data for Table 4-6 (2ed)..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/2ed-table4-6.rc) | sed 's/,/	/g' | tail -n +2 > 2ed-table4-6.dat

2ed-table4-7.dat:
	@echo "generating data for Table 4-7 (2ed)..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/2ed-table4-7.rc) | sed 's/,/	/g' | tail -n +2 > 2ed-table4-7.dat

2ed-table4-8.dat:
	@echo "generating data for Table 4-8 (2ed)..."
	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/2ed-table4-8.rc) | sed 's/,/	/g' | tail -n +2 > 2ed-table4-8.dat

# ------------------------ older targets ------------------


##2ed-table4-3.dat:
##	@echo "generating data for Table 4-3 (2ed)..."
##	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-4.rc) | s##ed 's/,/	/g' | tail -n +2 > 2ed-table4-3.dat

#2ed-table4-4.dat:
#	@echo "generating data for Table 4-4 (2ed)..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-5.rc) | sed 's/,/	/g' | tail# -n +2 > 2ed-table4-4.dat

#2ed-table4-8.dat:
#	@echo "generating data for Table 4-8 (2ed)..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-10.rc) | sed 's/,/	/g' | tail -n +2 > 2ed-table4-8.dat

#2ed-table4-9.dat:
#	@echo "generating data for Table 4-9 (2ed)..."
#	@rm -f 2ed-table4-9.dat
#########	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_4096.rc) | sed 's/,/	/g' | tail -n +2 >> 2ed-table4-9.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_8192.rc) | sed 's/,/	/g' | tail -n +3 >> 2ed-table4-9.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_16384.rc) | sed 's/,/	/g' | tail -n +3 >> 2ed-table4-9.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_32768.rc) | sed 's/,/	/g' | tail -n +3 >> 2ed-table4-9.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_65536.rc) | sed 's/,/	/g' | tail -n +3 >> 2ed-table4-9.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_131072.rc) | sed 's/,/	/g' | tail -n +3 >> 2ed-table4-9.dat
#
#
#table4-16.dat:
#	@echo "generating data for Table 4-16..."
#	@rm -f table4-16.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-16_4096.rc) | sed 's/,/	/g' | tail -n +2 >> table4-16.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-16_8192.rc) | sed 's/,/	/g' | tail -n +3 >> table4-16.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-16_16384.rc) | sed 's/,/	/g' | tail -n +3 >> table4-16.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-16_32768.rc) | sed 's/,/	/g' | tail -n +3 >> table4-16.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-16_65536.rc) | sed 's/,/	/g' | tail -n +3 >> table4-16.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-16_131072.rc) | sed 's/,/	/g' | tail -n +3 >> table4-16.dat
#
#table4-15.dat:
#	@echo "generating data for Table 4-15..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-15.rc) | sed 's/,/	/g' | tail -n +2 > table4-15.dat
#
#table4-14.dat:
#	@echo "generating data for Table 4-14..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-14.rc) | sed 's/,/	/g' | tail -n +2 > table4-14.dat
#
#table4-13.dat:
#	@echo "generating data for Table 4-13..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-13.rc) | sed 's/,/	/g' | tail -n +2 > table4-13.dat
#
#table4-12.dat:
#	@echo "generating data for Table 4-12..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-12.rc) | sed 's/,/	/g' | tail -n +2 > table4-12.dat
#
#
#table4-11.dat:
#	@echo "generating data for Table 4-11..."
#	@rm -f table4-11.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_4096.rc) | sed 's/,/	/g' | tail -n +2 >> table4-11.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_8192.rc) | sed 's/,/	/g' | tail -n +3 >> table4-11.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_16384.rc) | sed 's/,/	/g' | tail -n +3 >> table4-11.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_32768.rc) | sed 's/,/	/g' | tail -n +3 >> table4-11.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_65536.rc) | sed 's/,/	/g' | tail -n +3 >> table4-11.dat
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-11_131072.rc) | sed 's/,/	/g' | tail -n +3 >> table4-11.dat
#
#table4-10.dat:
#	@echo "generating data for Table 4-10..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-10.rc) | sed 's/,/	/g' | tail -n +2 > table4-10.dat
#
#
#table4-9.dat:
#	@echo "generating data for Table 4-9..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-9.rc) | sed 's/,/	/g' | tail -n +2 > table4-9.dat
#
#
#table4-8.dat:
#	@echo "generating data for Table 4-8..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-8.rc) | sed 's/,/	/g' | tail -n +2 > table4-8.dat
#
#
#table4-7.dat:
#	@echo "generating data for Table 4-7..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-7.rc) | sed 's/,/	/g' | tail -n +2 > table4-7.dat
#
#
#table4-5.dat:
#	@echo "generating data for Table 4-5..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-5.rc) | sed 's/,/	/g' | tail -n +2 > table4-5.dat
#
#
#
#table4-4.dat:
#	@echo "generating data for Table 4-4..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-4.rc) | sed 's/,/	/g' | tail -n +2 > table4-4.dat
#
#4-13.ps:
#	@echo "generating Figure 4-13..."
#	(cd $(CODE)/Sorting/Longs; $(MAKE))
#	dot -Tps $(CODE)/Sorting/Longs/figure4-13.dotty > 4-13.ps
#
#4-12.ps:
#	@echo "generating Figure 4-12..."
#	(cd $(CODE)/Sorting/Longs; $(MAKE))
#	dot -Tps $(CODE)/Sorting/Longs/figure4-12.dotty > 4-12.ps
#
#table4-3.dat:
#	@echo "generating data for Table 4-3..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-3.rc) | sed 's/,/	/g' | tail -n +2 > table4-3.dat
#
#
#table4-2.dat:
#	@echo "generating data for Table 4-2..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-2.rc) | sed 's/,/	/g' | tail -n +2 > table4-2.dat
#
#
#table4-1.dat:
#	@echo "generating data for Table 4-1..."
#	(cd $(CODE)/Sorting; ../bin/suiteRun.sh $(PWD)/figures/table4-1.rc) | sed 's/,/	/g' | tail -n +2 | awk -f $(RATIO) > table4-1.dat
#
#4-10.txt:
#	@echo "generating data for Figure 4-10..."	
#	(cd $(CODE)/Sorting/Longs; $(MAKE))
#	$(CODE)/Sorting/Longs/Figure4-10 > 4-10.txt
#
#4-9.ps:
#	@echo "generating Figure 4-9..."	
#	(cd $(CODE)/Sorting/Longs; $(MAKE))
#	dot -Tps $(CODE)/Sorting/Longs/figure4-9.dotty > 4-9.ps

clean:
	rm -f $(DATA) *~ $(DOTTY)
