import timeit

def performance():
    """Demonstrate execution performance."""
    n = 8000000
    numTrials = 10
    print ("n", "Add time")
    while n <= 16000000:
        setup = 'total=0'
        code  = 'for i in range(' + str(n) + '): total += i'
        add_total = min(timeit.Timer(code, setup=setup).repeat(5,numTrials))
            
        print ("%d %5.4f " % (n, add_total ))
        n += 2000000
        
if __name__ == '__main__':
    performance()
