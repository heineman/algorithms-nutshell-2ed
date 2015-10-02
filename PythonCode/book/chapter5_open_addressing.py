"""
    Results will be different from one run to the next because the Python hash function for strings
    changes from one run to the next.
    
    MAKE SURE WE EVALUATE SUCCESSFUL vs. UNSUCCESSFUL SEARCHES rather than BUILDING TABLE AS HERE.
"""

from adk.hashtable import Hashtable
import os.path

cur = os.path.realpath(__file__)
wordFile = os.path.normpath(os.path.join(cur, os.pardir, os.pardir, os.pardir, 'Blogs', 'artifacts', 'searching', 'words.english.txt'))
   
def build(wordFile, numberBins, probeFunction):
    """Build Hashtable using given size and probe function ."""
    # table must be at least 213557 
    if numberBins < 213557:
        print ("Must have at least 213557 bins to store the words.")
        return
    
    table = Hashtable(numberBins, probeFunction = probeFunction)
    
    # grab every word and construct hash table using open addressing
    wf = open(wordFile)
    for word in wf:
        numProbes = table.add(word)
        if numProbes < 0:
            print ("unable to add", word, numProbes)
            break
            
    wf.close()
    
    return table

def query(table, wordFile, success):
    """Evaluate probe behavior for given table and whether each query is successful."""
    # grab every word and construct hash table using open addressing
    wf = open(wordFile)
    totalProbes = 0
    numWords = 0
    maxProbes = 0
    for word in wf:
        # modify so it isn't a word in the dictionary
        if not success:
            word = word[:-1] + '*'
        numWords += 1
        numProbes = table.contains(word)
        if numProbes < 0:
            numProbes = -numProbes
        if numProbes > maxProbes:
            maxProbes = numProbes
                
        totalProbes += numProbes
    wf.close()
    
    return ((1.0*totalProbes)/numWords, maxProbes)

def linearProbe(hk, size, i):
    """Default linear probe using c= 101."""
    return (hk+101) % size

def quadraticProbe(hk, size, i):
    """Default quadratic probe using c1=1/2 and c2=1/2."""
    return int(hk + 0.5 + i*i*0.5) % size

print ("size\tAveSQQ\tMaxSQQ\tAveFQQ\tMaxFQQ\tAveSQL\tMaxSQL\tAveFQL\tMaxFQL")
success = [0,0]
failure = [0,0]

sizes = [224234]   # 5% over the lowest limit
factor = 1.1
for i in range(11):
    sizes.append(int(factor*sizes[-1]))

for size in sizes:
    quad = build(wordFile, size, quadraticProbe)
    linear = build(wordFile, size, linearProbe)
    successQ,maxSQ = query(quad, wordFile, success=True)
    failureQ,maxFQ = query(quad, wordFile, success=False)
    successL,maxSL = query(linear, wordFile, success=True)
    failureL,maxFL = query(linear, wordFile, success=False)
    print("%d\t%.3f\t%d\t%.3f\t%d\t%.3f\t%d\t%.3f\t%d" % (size, successQ, maxSQ, failureQ, maxFQ, successL, maxSL, failureL, maxFL))

    
"""
Sample output

size    AveSQQ    MaxSQQ    AveFQQ    MaxFQQ    AveSQL    MaxSQL    AveFQL    MaxFQL
224234    3.409    142    21.935    252    11.199    4448    263.865    4533
246657    2.508    58    8.173    93    4.217    425    27.618  547
271322    2.092    39    5.205    56    2.860    297    11.742  301
298454    1.857    35    3.874    41    2.277    97    6.714    129
328299    1.698    24    3.145    29    1.928    72    4.566    84
361128    1.584    22    2.667    26    1.731    49    3.496    64
397240    1.491    19    2.340    23    1.587    34    2.843    57
436964    1.419    14    2.091    18    1.478    29    2.409    38
480660    1.359    19    1.915    15    1.400    24    2.123    30
528726    1.308    11    1.768    16    1.337    20    1.907    26
581598    1.273    11    1.654    15    1.292    15    1.749    26
639757    1.234    12    1.560    14    1.251    19    1.626    24


"""
