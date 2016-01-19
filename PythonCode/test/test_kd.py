import unittest

from adk.kd import KDTree
from adk.region import Region
import random

class TestKDMethods(unittest.TestCase):

    def setUp(self):
        self.kd = KDTree()
        
    def tearDown(self):
        self.kd = None
        
    def test_basic(self):
        self.assertTrue(self.kd.add((10, 20)))
        self.assertFalse(self.kd.add((10, 20)))
        self.assertTrue(self.kd.find((10,20)))
        
        self.assertFalse(self.kd.find((5, 5)))

    def expand(self, region):
        """When Full Sub-trees returned as nodes traverse to expand nodes."""
        result = [p for p in self.kd.range(region)]
        expanded = []
        for pair in result:
            if pair[1]:
                expanded = expanded + [d.point for d in pair[0].inorder()]
            else:
                expanded.append(pair[0].point)
                
        return expanded
        
    def test_adding(self):
        for _ in range(5000):
            self.kd.add((random.randint(250,750), random.randint(250,750)))
        
        # make sure not to overlap!
        q_ne = self.expand(Region(500, 500, 1000, 1000))
        q_nw = self.expand(Region(0, 500, 499, 1000))
        q_sw = self.expand(Region(0, 0, 499, 499))
        q_se = self.expand(Region(500, 0, 1000, 499))
        
        q_all = self.expand(Region(0,0, 1000, 1000))
        
        # quick check to see if any were missing
        combined = q_ne + q_nw + q_sw + q_se
        for q in q_all:
            if q not in combined:
                print (q, " missing ")
        
        self.assertEquals(len(q_all), len(combined))
        
        # validate searches are true
        for p in combined:
            self.assertTrue(self.kd.find(p))
            
        # validate can't add these points anymore
        for p in combined:
            self.assertFalse(self.kd.add(p))
        