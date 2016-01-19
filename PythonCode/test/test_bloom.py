import unittest

from adk.bloom import *

class TestHashtableMethods(unittest.TestCase):

    def test_basic(self):
        bloom = bloomFilter()
    
        self.assertFalse(15 in bloom)
        bloom.add(15)
        self.assertTrue(15 in bloom)
        
        # default size is to have 1000 items. So 1015 also hashes to same spot
        self.assertTrue(1015 in bloom)
        
        # but this won't
        self.assertFalse(1014 in bloom)
        
    def test_multiple(self):
        hf1 = lambda e, size : hash(e) % size
        hf2 = lambda e, size : hash(e*e) % size
        hf3 = lambda e, size : hash(1/e) if e != 0 else 1

        bloom = bloomFilter(10000, [hf1, hf2, hf3])
    
        self.assertFalse(15 in bloom)
        bloom.add(15)
        self.assertTrue(15 in bloom)
        
        # not present.
        self.assertFalse(14 in bloom)
    