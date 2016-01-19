import unittest


from adk.region import Region
from adk.R import RTree

import random


class TestRMethods(unittest.TestCase):

    def setUp(self):
        self.tree = RTree()
        
    def tearDown(self):
        self.tree = None
        
    def test_basic(self):
        r = Region(10,20,50,60)
        
        self.tree.add(r, 99)
                         
        for node,_,status in self.tree.range(r):
            self.assertTrue(status)
            for rect, ident in node.leafOrder():
                self.assertEquals(99, ident)
                self.assertEquals(r, rect)

    # Expanded region for node with multiple insertions.
    def test_expand(self):
        r = Region(10,20,50,60)
        r2 = Region(30,60,70,80)
                
        self.tree.add(r, 99)
        self.tree.add(r2, 101)
                         
        self.assertEquals(Region(10,20,70,80), self.tree.root.region)
        
    # Add five regions and watch split.
    def test_split(self):
        r1 = Region(10,20,50,60)
        r2 = Region(30,60,70,80)
        r3 = Region(-30,60,70,80)
        r4 = Region(40,70,60,100)
           
        self.tree.add(r1, 99)
        self.tree.add(r2, 101)
        self.tree.add(r3, 103)
        self.tree.add(r4, 105)
        
        self.assertEquals(Region(-30,20,70,100), self.tree.root.region)
        
        r5 = Region(10,60,0,90)
        
        self.tree.add(r5, 107)

        self.assertEquals(Region(-30,20,70,100), self.tree.root.region)
        
        # finds all rectangles.
        r_ids = []
        for triple in self.tree.range(Region(-100, -100, 200, 200)):
            if triple[2]:
                for d in triple[0].leafOrder():
                    r_ids.append(d[1])
            else:
                r_ids.append(triple[1])
        r_ids.sort()
        self.assertEquals ([99,101,103,105,107], r_ids)
     
    def expand(self, region):
        """When Full Sub-trees returned as nodes traverse to expand nodes."""
        result = [p for p in self.tree.range(region)]
        expanded = []
        for triple in result:
            if triple[2]:
                for d in triple[0].leafOrder():
                    expanded.append(d[0])
            else:
                expanded.append(triple[0])
                
        return expanded
       
    def test_iterator(self):
        # rectangles have all even numbered coordinates
        for _ in range(500):
            self.tree.add(Region(2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250)))
        
        count = 0
        for _ in self.tree:
            count = count + 1
        self.assertEquals(500, count)
       
    def test_adding(self):
        # rectangles have all even numbered coordinates. Doesn't really matter since
        # range returns regions that intersect target, rather than being wholly contained
        for _ in range(500):
            self.tree.add(Region(2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250)))
        
        # make sure not to overlap!
        q_ne = self.expand(Region(125, 125, 501, 501))
        q_nw = self.expand(Region(1, 125, 125, 501))
        q_sw = self.expand(Region(1, 1, 125, 125))
        q_se = self.expand(Region(125, 1, 511, 125))
        
        q_all = self.expand(Region(1,1, 501, 501))
        
        # quick check to see if any were missing
        combined = []
        for r in q_ne + q_nw + q_sw + q_se:
            if r not in combined:
                combined.append(r)

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
                
    def ensure_maxBounding(self, node):
        """Ensures R-Node guarantees max bounding rectangle for all interior nodes covers descendants."""
        for i in range(node.count):
            n = node.children[i]
            self.assertTrue(node.region.containsRegion(n.region))
            self.ensure_maxBounding(n)
                
    def test_verticalSlices(self):
        # check case of vertical rectangles all in a row. This forces regular extensions.
        # Make sure bounding rectangles are computed properly.
        for x in range(1,50):
            self.tree.add(Region(x*6,10, x*6+3,100))
            
        # sliding window always grabs eight of them since there is one rect every 6 pixels and query width is 47
        for x in range(1, 40):
            s = Region(x*6, 2, x*6+47, 100)
            match = self.expand(s)
            
            self.assertEquals(8, len(match))
        
        self.ensure_maxBounding(self.tree.root)
        
    def test_tiling(self):
        # create evenly placed rectangles (s=16_ and validate queries
        for x in range(1,8):
            for y in range(1,8):
                self.tree.add(Region(x*64,y*64, x*64+32,y*64+32))
    
        for x in range(1,5):
            for y in range(1,5):
                s = Region(x*64, y*64, x*64+255, y*64+127)   # includes 2x4 selection or 8
                match = self.expand(s)
            
                self.assertEquals(8, len(match))
        
    def test_multipleM(self):
        # check multiple m,M values
        
        for m,M in zip([3, 4, 5, 6], [7, 8, 10, 13]):
            self.tree = RTree(m,M)
            regions = []
            for ctr in range(500):
                r = Region(2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250))
                regions.append((r, ctr))
                self.tree.add(r,ctr)
                
            # make sure all can be individually found
            for ctr in range(len(regions)):
                match = [m for m in self.tree.search(regions[ctr][0])][0]
                self.assertEquals(match, regions[ctr][0])
                
            # make sure that all are contained within the 
            self.ensure_maxBounding(self.tree.root)
                
    # test deletions
    def test_deleteOnlyOne(self):
        r = Region(10,20,50,60)
                
        self.tree.add(r, 99)
                         
        self.assertTrue(self.tree.search(r))
        self.assertEquals(Region(10,20,50,60), self.tree.root.region)
        
        # not in tree.
        self.assertFalse(self.tree.remove(Region(2,4,22,50)))
        
        self.assertTrue(self.tree.remove(r))

        # Not present.
        self.assertTrue(self.tree.search(r) is None)
                
    # test deletions
    def test_delete(self):
        r = Region(10,20,50,60)
        r2 = Region(30,60,70,80)
                
        self.tree.add(r, 99)
        self.tree.add(r2, 101)
                         
        self.assertTrue(self.tree.search(r))
        self.assertTrue(self.tree.search(r2))
        self.assertEquals(Region(10,20,70,80), self.tree.root.region)
        
        # not in tree.
        self.assertFalse(self.tree.remove(Region(2,4,22,50)))
        
        self.assertTrue(self.tree.remove(r2))

        # one is in, other is out
        self.assertTrue(self.tree.search(r))
        self.assertTrue(self.tree.search(r2) is None)
        
    def test_deleteFive(self):
        r1 = Region(38,148  , 300,288)
        r2 = Region(164,384 , 432,428)
        r3 = Region(316,342 , 456,392)
        r4 = Region(12,242  , 172,484)
        r5 = Region(324,200 , 494,276)
        for r in [r1,r2,r3,r4,r5]:
            self.tree.add(r)
            
        for r in [r1,r2,r3,r4,r5]:
            self.assertTrue(self.tree.search(r))
            self.tree.remove(r)
            self.assertFalse(self.tree.search(r))
            
        self.assertTrue (self.tree.root == None)
        
    def test_deleteThirteen(self):
        r1 = Region(410,168 , 482,348)
        r2 = Region(136,236 , 250,266)
        r3 = Region(290,26 , 496,470)   ## FAILS when deleting 10
        r4 = Region(398,24 , 444,100)
        r5 = Region(160,388 , 462,480)
        r6 = Region(50,124 , 120,486)
        r7 = Region(22,258 , 144,322)
        r8 = Region(28,148 , 392,236)
        r9 = Region(78,190 , 174,370)   #  problem on this one
        r10 = Region(38,142 , 232,184)   
        r11 = Region(52,346 , 52,424)
        r12 = Region(70,120 , 248,400)
        r13 = Region(274,368 , 444,370)   
        for r in [r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13]:
            self.tree.add(r)
            
        for r in [r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13]:
            self.assertTrue(self.tree.search(r))
            self.tree.remove(r)
            self.assertFalse(self.tree.search(r))
            
        self.assertTrue (self.tree.root == None)
        
    def test_multipleDeletions(self):
        # rectangles have all even numbered coordinates
        added = []
        for _ in range(4096):
            r = Region(2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250), 2*random.randint(2,250))
            self.tree.add(r)
            added.append(r)
        
        print (self.tree.root.level)
        random.shuffle(added)
        for r in added:
            self.assertTrue(self.tree.search(r))
            self.tree.remove(r)
            self.assertFalse(self.tree.search(r))
       
        # once done, R-Tree is empty once again
        ct = 0
        for _ in self.tree:
            ct += 1
        self.assertEquals (0, ct)
