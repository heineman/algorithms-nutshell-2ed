"""
Evaluate time to perform exponentiation. Figure 2-7 in 2nd Edition.
"""

import timeit
import math

for p in range(0,200):
    print (p, timeit.timeit("2**"+str(p), number=100000))
    
def power(n,c):
    """return pi*2**n"""
    return math.pi*c

for p in range(0,140):
    c = str(2**p)
    cmd = 'power(' + str(p) + ',' + c + ')'
    print (p, timeit.timeit(cmd, number=100000, setup="from __main__ import power"))
    
