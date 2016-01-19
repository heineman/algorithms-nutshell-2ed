import unittest

from adk.fortune import Voronoi, Point, Arc
import random

class TestFortuneMethods(unittest.TestCase):

    def perturb(self, points):
        for i in range(len(points)):
            pt = points[i]
            points[i] = (pt[0] + random.random(), pt[1] + random.random())

    def valid(self, edge, expectedEdges):
        if edge in expectedEdges:
            return
        if [edge[1], edge[0]] in expectedEdges:
            return
        
        self.fail("edge not found in expected:" + str(edge)) 

    def test_singlePoint(self):
        p1 = (8,4)
        
        v = Voronoi(20,20)
        v.process([p1])
        self.assertEquals(0, len(v.edges))
            
    def test_bisectorIntersection(self):
        pt = Point((2,1))
        arc = Arc(point = pt)
        y = arc.pointOnBisectionLine(5,0)
        self.assertEquals (Point((5, 5.0)), y)
        
    def test_twoInitial(self):
        p1 = (8,4)
        p2 = (10,0)
        
        e1 = [Point((0,-2.5)),Point((20,7.5))]
        edges = [e1]
        
        v = Voronoi(20,20)
        v.process([p1,p2])
        for e in v.edges:
            self.valid([e.start, e.end], edges)
        
        
    def test_twoInitialVerticalBisection(self):
        p1 = (3,3)
        p2 = (8,3)
        e1 = [Point((5.5,10)),Point((5.5,0))]
        
        edges = [e1]
        
        v = Voronoi(10, 10)
        v.process([p1,p2])
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_threeBasic(self):
        p1 = (6,7)
        p2 = (2,3)
        p3 = (8,1) 
        
        e1 = [Point((0,9.0)), Point((5.5,3.5))]
        e2 = [Point((5.5,3.5)), Point((0,-13.0))]
        e3 = [Point((5.5,3.5)), Point((10,5.0))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(10,10)
        v.process([p1,p2,p3])
        
        for e in v.edges:
            self.valid([e.start, e.end], edges)
        for p in v.points:
            print (p.polygon)


    def test_threeL(self):
        p1 = (2,5)
        p2 = (2,2)
        p3 = (5,2) 
        
        e1 = [Point((0,3.5)), Point((3.5,3.5))]
        e2 = [Point((3.5,3.5)), Point((3.5,0))]
        e3 = [Point((3.5,3.5)), Point((10,10.0))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(10,10)
        v.process([p1,p2,p3])
        self.assertEquals (3, len(v.edges))
        for e in v.edges:
            self.valid([e.start, e.end], edges)
            
    def test_threeL2(self):
        p1 = (6,5)
        p2 = (6,2)
        p3 = (2,2) 
        
        e1 = [Point((0,8.8334)), Point((4.0,3.5))]
        e2 = [Point((4.0,3.5)), Point((4.0,0))]
        e3 = [Point((4.0,3.5)), Point((10,3.5))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(10,10)
        v.process([p1,p2,p3])
        self.assertEquals (3, len(v.edges))
        for e in v.edges:
            print (e)
            self.valid([e.start, e.end], edges)
    
    def test_threeL3(self):
        p1 = (2,5)
        p2 = (5,5)
        p3 = (2,2) 
        
        e1 = [Point((0,3.5)), Point((3.5,3.5))]
        e2 = [Point((3.5,10)), Point((3.5,3.5))]
        e3 = [Point((3.5,3.5)), Point((7,0))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(10,10)
        v.process([p1,p2,p3])
        self.assertEquals (3, len(v.edges))
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_threeL4(self):
        p1 = (2,5)
        p2 = (5,5)
        p3 = (5,2) 
        
        e1 = [Point((3.5,10)), Point((3.5,3.5))]
        e2 = [Point((3.5,3.5)), Point((0,0))]
        e3 = [Point((3.5,3.5)), Point((10,3.5))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(10,10)
        v.process([p1,p2,p3])
        self.assertEquals (3, len(v.edges))
        for e in v.edges:
            self.valid([e.start, e.end], edges)        
    
    
    def test_threeOneParallelOneIntersect(self):
        p1 = (3,3)
        p2 = (5,3)
        p3 = (8,2)
        
        # outside bounding box area
        e1 = [Point((4,10)),Point((4,-5.0))]
        e2 = [Point((4,-5.0)),Point((9,10))]
        e3 = [Point((4,-5.0)),Point((0,-25))]     ## This one is completely outside and can't be fixed.
        
        edges = [e1,e2,e3]
        
        v = Voronoi(10, 10)
        v.process([p1,p2,p3])
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_threeSameY(self):
        p1 = (50,200)
        p2 = (100,200)
        p3 = (150,200)
        
        e1 = [Point((75,300)),Point((75,0))]
        e2 = [Point((125,300)),Point((125,0))]
        
        edges = [e1,e2]
        v = Voronoi(300,300)
        v.process([p1,p2,p3])
         
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_collinearParallel(self):
        p1 = (8,7)
        p2 = (6,5)
        p3 = (4,3)
        p4 = (2,1)
        
        e1 = [Point((0,13)),Point((13,0))]
        e2 = [Point((0,9)),Point((9,0))]
        e3 = [Point((0,5)),Point((5,0))]
        
        edges = [e1,e2,e3]
        v = Voronoi(width=20, height=20)
        v.process([p1,p2,p3,p4])
        
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_collinearOtherParallel(self):
        p1 = (2,7)
        p2 = (4,5)
        p3 = (6,3)
        p4 = (8,1)
        
        e1 = [Point((0,3)),Point((17,20))]
        e2 = [Point((0,-1)),Point((20,19))]
        e3 = [Point((0,-5)),Point((20,15))]
        
        edges = [e1,e2,e3]
        v = Voronoi(width=20, height=20)
        v.process([p1,p2,p3,p4])
        
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_firstCircleEvent(self):
        p1 = (6,3)
        p2 = (8,4)
        p3 = (5,1)
        
        e1 = [Point((0,4.75)),Point((8.5,0.5))]
        e2 = [Point((0,17.5)),Point((8.5,0.5))]
        e3 = [Point((8.5,0.5)),Point((9,0))]
        e4 = [Point((6,5.5)), Point((8.5,0.5))]
        e5 = [Point((5, 2.25)), Point((8.5, 0.5))]
        
        edges = [e1,e2,e3,e4,e5]
        v = Voronoi(width=10, height=10)
        v.process([p1,p2,p3])
        
        for e in v.edges:
            self.valid([e.start, e.end], edges)
    
    def test_multipleFirstSweepLine(self):
        p1 = (150,200)
        p2 = (250,200)
        p3 = (200,150)
        
        v = Voronoi(width=300, height=300)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)
    
    def test_arc(self):
        pt = Point((2,1))
        root = Arc(point = pt)
        root.left = Arc(point = Point ((1,0)))
        root.right = Arc(point = Point ((3, 0)))
        
        x = root.getLargestLeftDescendant()
        y = root.getSmallestRightDescendant()
        self.assertEquals ((1,0), (x.site.x, x.site.y))
        self.assertEquals ((3,0), (y.site.x, y.site.y))
        
    def test_another(self):
        p1 = (11, 26)
        p2 = (28, 15)
        p3 = (22, 41) 
        v = Voronoi(width=50, height=50)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)
   
    def test_perfectCircle(self):
        #p1 = (83, 181)
        #p2 = (108, 181)
        #p3 = (380, 308)
        p1 = (4,6)
        p2 = (2,4)
        p3 = (6,4) 
           
        e1 = [Point((0,8)),Point((4,4))]
        e2 = [Point((4,4)),Point((4,0))]
        e3 = [Point((4,4)),Point((8,8))]
        
        edges = [e1,e2,e3]
        v = Voronoi(8,8)
        v.process([p1,p2,p3])
        
        for e in v.edges:
            self.valid([e.start, e.end], edges)
   
    def test_edgeExplanation(self):
        #p1 = (83, 181)
        #p2 = (108, 181)
        #p3 = (380, 308)
        p1 = (4,6)
        p2 = (2,4)
        p3 = (6,4) 
        p4 = (3,1)    
        p5 = (5,1)
           
        v = Voronoi(width=8, height=8)
        v.process([p1,p2,p3,p4,p5])
        for e in v.edges:
            print (e)
        
    def test_badCase(self):
        p1 = (14, 20)
        p2 = (19, 15)
        p3 = (5, 12)   
           
        v = Voronoi(width=40, height=40)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)
            
    def test_good_bad(self):
        p1 = (205,238)
        p2 = (156,157 )
        p3 = (255,133)
           
        v = Voronoi(width=400, height=400)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)  
            
        print ("----")
        p1 = (205,238)
        p2 = (156,157 )
        p3 = (254,133)    ## small change causes one more circle event
           
        v = Voronoi(width=400, height=400)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)               
            
    # while lines are computed, the polygons are frustratingly hard to compute
    # for horizontal and vertical lines
    def test_threeVertical(self):
        points = [(10,x) for x in range(2,9,3) ]
        
        v = Voronoi(width=20, height=20)
        v.process(points)
        for e in v.points:
            print (e, e.polygon)
            
    def test_threeHorizontal(self):
        points = [(x,5) for x in range(2,9,3) ]
        
        e1 = [Point((3.5,10)),Point((3.5,0))]
        e2 = [Point((6.5,10)),Point((6.5,0))]
        
        edges = [e1,e2]
        
        v = Voronoi(width=10, height=10)
        v.process(points)
        for e in v.edges:
            self.valid([e.start, e.end], edges)   
            
    def test_threeIsoceles(self):
        points = [(5,2), (11,2), (8,4) ]
        
        e1 = [Point((8.0,0.75)),Point((0,12.75))]
        e2 = [Point((15,11.25)),Point((8.0,0.75))]
        e3 = [Point((8.0,0.75)),Point((8.0,0.0))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(width=15, height=15)
        v.process(points)
        for e in v.edges:
            self.valid([e.start, e.end], edges)    
            
    def test_threeIsocelesTilted(self):
        points = [(5,2), (5,8), (9,5) ]
        
        e1 = [Point((9.625,10)),Point((5.875,5.0))]
        e2 = [Point((5.875,5.0)),Point((0,5.0))]
        e3 = [Point((5.875,5.0)),Point((9.625,0))]
        
        edges = [e1,e2,e3]
        
        v = Voronoi(width=10, height=10)
        v.process(points)
        for e in v.edges:
            self.valid([e.start, e.end], edges)   
    
            
    def test_threeByThree(self):
        points = [(x,y) for x in range(2,9,3) for y in range(2,9,3)]
        v = Voronoi(width=10, height=10)
        self.perturb(points)
        v.process(points)
        for e in v.edges:
            print (e) 
         
    def test_slightlyOff(self):
        p1 = (6, 6)
        p2 = (6, 8)
        p3 = (6.1, 4)
        v = Voronoi(width=10, height=10)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)    
    
    def test_missing(self):
        
        p1 = (110.0, 229.0)
        p2 = (187.0, 231.0)
        p3 = (150.0, 107.0)
        v = Voronoi(width=400, height=400)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)   
    
    def test_crossover(self):
        p1 = (138, 243)
        p2 = (180, 184)
        p3 = (113, 115)
        p4 = (245, 112)
        
        v = Voronoi(width=400, height=400)
        v.process([p1,p2,p3,p4])
        for e in v.edges:
            print (e)       
            
            
    def test_fortune(self):
        p1 = (2,6)
        p2 = (7,5)
        p3 = (4,2)
        v = Voronoi(width=10, height=10)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)
            
    def test_whenPointAtCircumcircle(self):
        p1 = (2,5)
        p2 = (6,5)
        p3 = (4,3)
        
        # This is challenging and not yet solved.
        
        v = Voronoi(10,10)
        v.process([p1,p2,p3])
        for e in v.edges:
            print (e)
    