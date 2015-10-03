import timeit
import math

for p in range(0,256):
    print (p, timeit.timeit("2**"+str(p), number=10000))
