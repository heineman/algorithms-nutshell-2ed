"""
  Computes k for a Bloom Filter with n known in advance and p.
  
  This example shows n= 213,557 - 2,135 words
  
  m must be sufficiently large.
"""

p = 0.25
n = 211422

#for m in range(2000000, 500000, -10000):
for m in range(610000,2000000,10000):
    k = 1
    pk = 99
    iterations = 0
    while pk > p and iterations < 1000:
        k = k + 1
        pk = (1 - (1 - 1/m)**(k*n))**k
        iterations += 1
    
    print (m, k, pk)
