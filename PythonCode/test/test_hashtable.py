import unittest

from adk.hashtable import Hashtable


class TestHashtableMethods(unittest.TestCase):

    def setUp(self):
        self.table = Hashtable(b=11, probeFunction = lambda hk, size, i : (hk + 1) % size )

    def tearDown(self):
        self.table = None
        
    def test_basic(self):
        self.table.add(15)
        self.assertTrue(15 in self.table.bins)
    
    def test_fills(self):
        for i in range(11):
            self.assertTrue(self.table.add("s" + str(i)) > 0)
            
        # print out hashtable for inspection
        for i in range(len(self.table.bins)):
            print (i, self.table.bins[i])
            
        # now the hashtable is completely full. Can't add any more
        self.assertEquals(-11, self.table.add("s" + str(12)))
        
    def test_badlinear(self):
        """linear with 0 must fail."""
        self.table = Hashtable(b=11, probeFunction = lambda hk, size, i : (hk + 0) % size )
        self.table.add(15)
        self.assertTrue(self.table.contains(15))
        
        # probes to same spot...
        self.assertTrue(self.table.add(26) < 0)
        
    def test_deletion(self):
        self.table.add(15)
        self.assertTrue(self.table.contains(15))
        self.table.add(26)
        self.assertTrue(self.table.contains(26))
        
        self.table.delete(15)
        self.assertTrue(self.table.contains(15) < 0)
        self.assertTrue(self.table.contains(26))
        