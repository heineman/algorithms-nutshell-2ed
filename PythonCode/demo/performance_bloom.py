from adk.bloom import *

import random
import os.path

cur = os.path.realpath(__file__)
wordFile = os.path.normpath(os.path.join(cur, os.pardir, os.pardir, os.pardir, 'Blogs', 'artifacts', 'searching', 'words.english.txt'))

def hf1(s, size):
    return hash(s) % size

def jenkins_one_at_a_time_hash(s, size):
    """http://en.wikipedia.org/wiki/Jenkins_hash_function."""
    h = 0
    for c in s:
        h += ord(c)
        h += (h << 10)
        h ^= (h >> 6)
    h += (h << 3);
    h ^= (h >> 11)
    h += (h << 15)
    return h % size

def hf3(s, size):
    """Hashcode implementation from Java String class."""
    h = 0
    for c in s:
        h = 31*h + ord(c)
    return h % size

def evalBloom(size, strings, missing):
    """
    Returns the total number of false positives when searching for the elements of missing after 
    creating a Bloom Filter of the given input strings.
    """
    bloom = bloomFilter(size, [hf1, jenkins_one_at_a_time_hash, hf3])
    
    for s in missing:
        if s in strings:
            print ("input contains a missing word:", s, ". Try again with different input.")
            return None
    
    # Create Bloom Filter
    for s in strings:
        bloom.add(s)
        
    # Now that bloom filter is built, try the missing words
    numFalse = 0
    for s in missing:
        if s in bloom:
            numFalse += 1
        
    return numFalse

# grab every word and add to bloom filter. Must strip '\n' from each. Use ascending
# and descending orders
wf = open(wordFile)
raw = wf.readlines()
words = [w[:-1] for w in raw]

wf.close()

letters = 'abcdefghijklmnopqrstuvwxyz'
    
base = 2135
    
for size in range(100000,2000000,10000):
    copy = words[:]
    random.shuffle(copy)
    missing = copy[:base]
    copy = copy[base:]
    
    wordFalsePositive = evalBloom(size, copy, missing)
    
    # generate random strings of between 2 and 10 characters in length
    randomStrings = []
    while len(randomStrings) < base:
        rlen = random.randint(2,10)
        w = ''
        for i in range(rlen):
            w = w + letters[random.randint(0,25)]
        if w not in words:
            randomStrings.append(w)
     
    randomFalsePositive = evalBloom(size, copy, randomStrings) 
    
    n = len(copy)
    k = 3
    
    computed = (1 - (1-1/size) ** (k*n)) ** k
    fp  = '%.4f' % (1.0*wordFalsePositive/base)
    rfp = '%.4f' % (1.0*randomFalsePositive/base)
    cfp = '%.4f' % computed
    
    print (size, fp, rfp, cfp)
