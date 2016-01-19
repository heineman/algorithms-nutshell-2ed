import unittest

from adk.quad import QuadTree
from adk.region import Region
import random

class TestQuadMethods(unittest.TestCase):

    def setUp(self):
        self.quad = QuadTree(Region(0,0, 256, 256))
        
    def tearDown(self):
        self.quad = None
        
    def test_basic(self):
        self.assertTrue(self.quad.add((10, 20)))
        self.assertFalse(self.quad.add((10, 20)))
        self.assertTrue(self.quad.find((10,20)))
        
        self.assertFalse(self.quad.find((5, 5)))
    
    def expand(self, region):
        """When Full Sub-trees returned as nodes traverse to expand nodes."""
        result = [p for p in self.quad.range(region)]
        expanded = []
        for pair in result:
            if pair[1]:
                for d in pair[0].preorder():
                    if d.points != None:
                        expanded = expanded + d.points
            else:
                expanded.append(pair[0][0])
                
        return expanded
    
    def test_adding(self):
        print ("Starting generation.");
        for _ in range(25):
            print ("Added " + str(_))
            self.quad.add((random.randint(25,225), random.randint(25,225)))
            
            
        print ("Done with generation.");
        
        # make sure not to overlap!
        q_ne = self.expand(Region(128, 128, 256, 256))
        q_nw = self.expand(Region(0, 128, 128, 256))
        q_sw = self.expand(Region(0, 0, 128, 128))
        q_se = self.expand(Region(128, 0, 256, 128))
        
        q_all = self.expand(Region(0,0, 256, 256))
        
        # quick check to see if any were missing
        combined = q_ne + q_nw + q_sw + q_se
        for q in q_all:
            if q not in combined:
                print (q, " missing ")
                
        # check duplicates
        for i in range(len(combined)):
            for j in range(len(combined)):
                if j <= i:
                    pass
                else:
                    if combined[i] == combined[j]:
                        print ("Duplicate:", combined[i])
        
        self.assertEquals(len(q_all), len(combined))
