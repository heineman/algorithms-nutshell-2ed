"""
    Binary Search test
"""
import unittest

from adk.binary import bs_insert, bs_contains

class TestKDTree(unittest.TestCase):

    def test_three(self):
        ordered = [ 3, 5, 9 ]
        self.assertEquals(1, bs_contains(ordered, 5))
        self.assertEquals(-2, bs_contains(ordered, 4))       # would go into position 2, so must be -(1+1) or -2
        

    def test_two(self):
        ordered = [ 2, 6 ]
        self.assertEquals(0, bs_contains(ordered, 2))
        self.assertTrue(bs_contains(ordered, 6))
        self.assertEquals(-1, bs_contains(ordered, 1))       # would go into position 0, so must be -(0+1) or -1
        self.assertEquals(-3, bs_contains(ordered, 9))       # would go into position 0, so must be -(2+1) or -3
        bs_insert(ordered, 1);
        self.assertEquals([1, 2, 6], ordered)

if __name__ == '__main__':
    unittest.main()