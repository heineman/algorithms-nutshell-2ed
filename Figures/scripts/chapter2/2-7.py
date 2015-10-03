import timeit
import math

def power(n,c):
    """return pi*2**n"""
    return math.pi*c

for p in range(0,256):
    c = str(2**p)
    cmd = 'power(' + str(p) + ',' + c + ')'
    print (p, timeit.timeit(cmd, number=10000, setup="from __main__ import power"))
    


