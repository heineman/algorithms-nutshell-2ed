import unittest

from adk.avl import BinaryTree
import random

class TestAVLMethods(unittest.TestCase):

    def setUp(self):
        self.bst = BinaryTree()
        
    def tearDown(self):
        self.bst = None
        
    def test_basic(self):
        self.bst.add(10)
        self.assertTrue(10 in self.bst)
        
    def test_adding_and_removing(self):
        vals = []
        for _ in range(1000):
            n = random.randint(1,100000)
            vals.append(n)
            if not n in self.bst:
                self.bst.add(n)
                self.assertTrue(self.bst.assertAVLProperty())
           
        for r in vals:
            self.bst.remove(r)
            self.assertFalse(r in self.bst)
            self.assertTrue(self.bst.assertAVLProperty())

if __name__ == '__main__':
    unittest.main()    