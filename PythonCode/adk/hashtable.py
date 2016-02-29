"""
    Hashtable Implementations with open addressing and linear probing.
    
    For set semantics, you have to ensure add checks if value has already been inserted.
"""

def defaultHash(value, size):
    return hash(value) % size

class Hashtable:
    def __init__(self, b=1009, hashFunction=None, probeFunction=None):
        """Initialize a hash table with b bins, given hash function, and probe function."""
        self.b = b
        self.bins = [None] * b
        self.deleted = [False] * b
        
        if hashFunction:
            self.hashFunction = hashFunction
        else:
            self.hashFunction = defaultHash
        
        if probeFunction:
            self.probeFunction = probeFunction
        else:
            self.probeFunction = lambda hk, size, i : (hk + 37) % size 
        
    def add(self, value):
        """
        Add element into hashtable returning -self.b on failure after self.b tries. Returns number of probes
        on success.
        
        Add into bins that have been marked for deletion and properly deal with formerly deleted entries.
        """
        hk = self.hashFunction(value, self.b)
        
        ctr = 1
        while ctr <= self.b:
            if self.bins[hk] is None or self.deleted[hk]:
                self.bins[hk] = value
                self.deleted[hk] = False
                return ctr
            
            # already present? Leave now
            if self.bins[hk] == value and not self.deleted[hk]:
                return ctr
            
            hk = self.probeFunction(hk, self.b, ctr)
            ctr += 1
        
        return -self.b
            
    def contains(self, value):
        """
        Determine whether element is contained, returning -n on failure where n was the number of probes.
        Return positive number of probes to locate value.
        """
        hk = self.hashFunction(value, self.b)
        
        ctr = 1
        while ctr <= self.b:
            if self.bins[hk] is None:
                return -ctr
        
            if self.bins[hk] == value and not self.deleted[hk]:
                return ctr
            
            hk = self.probeFunction(hk, self.b, ctr)
            ctr += 1
        
        return -self.b
    
    def delete(self, value):
        """Delete value from hash table without breaking existing chains."""
        hk = self.hashFunction(value, self.b)
        
        ctr = 1
        while ctr <= self.b:
            if self.bins[hk] is None:
                return -ctr
        
            if self.bins[hk] == value and not self.deleted[hk]:
                self.deleted[hk] = True
                return ctr
            
            hk = self.probeFunction(hk, self.b, ctr)
            ctr += 1
        
        return -self.b
    