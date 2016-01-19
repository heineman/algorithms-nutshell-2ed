import unittest

from adk.mergesort import sort
import random

class TestMergeSortMethods(unittest.TestCase):

    
    def test_adding(self):
        for size in range(1,500):
            c = [random.randint(1,1000) for _ in range(size)]
        
            copy = c[:]
            
            # sort using mergeSort and using builtin sort
            sort(c)
            copy.sort()
            
            self.assertEquals(c,copy)
