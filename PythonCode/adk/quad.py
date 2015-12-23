"""
    Quad Tree implementation.
    
    Every Quad Node has four children, partitioning space accordingly based on NE, NW, SW, SE quadrants.
    Each Node evenly divides quadrants. Each node can store 4 points, after which it must be subdivided
    to store additional points.
    
    On all range searches, the x_min and y_min are inclusive whereas the x_max and y_max are exclusive.
    This the origin point for a region would be inserted into quadrant NE (0). This leads to the odd 
    behavior that the point (x_max, y_max) is not available in a search using (x_min,x_max)-(y_min,y_max).
    To avoid this dilemma, be sure to use ranges that extend further than you believe necessary.
"""

import math
from adk.region import X, Y

NE = 0
NW = 1
SW = 2
SE = 3

def distance(p, pt):
    """Compute distance from p to pt."""
    if pt:
        return ((p[X] - pt[X])**2 + (p[Y] - pt[Y])**2) ** 0.5

def smaller2k(n):
    """
    Returns power of 2 which is smaller than n. Handles negative numbers.
    """
    if n == 0: return 0
    if n < 0:
        return -2**math.ceil(math.log2(-n))
    else:
        return 2**math.floor(math.log2(n))
    
def larger2k(n):
    """
    Returns power of 2 which is larger than n. Handles negative numbers.
    """
    if n == 0: return 0
    if n < 0:
        return -2**math.floor(math.log2(-n))
    else:
        return 2**math.ceil(math.log2(n))

def containsPoint(region, point):
    """Returns True if point contained in rectangle, closed on min and open on max."""
    if point[X] < region.x_min: return False
    if point[X] >= region.x_max: return False
    if point[Y] < region.y_min: return False
    if point[Y] >= region.y_max: return False
    
    return True

class QuadNode:

    def __init__(self, region, pt = None, data = None):
        """Create empty QuadNode centered on origin of given region."""
        self.region = region
        self.origin = (region.x_min + (region.x_max - region.x_min)//2, region.y_min + (region.y_max - region.y_min)//2) 
        self.children = [None] * 4
        
        if pt:
            self.points = [pt]
            self.data = [data]
        else:
            self.points = []
            self.data = []
    
    def collide (self, pt, r):
        """Yield points in leaf that intersect with point and square side r."""
        node = self
        while node:
            # Point must fit in this region
            if containsPoint (node.region, pt):
                # if we have points, then we are leaf node. Check here
                if node.points != None:
                    for p,d in zip(node.points, node.data):
                        if p[X] - r <= pt[X] <= p[X] + r and p[Y] - r <= pt[Y] <= p[Y] + r:
                            yield (p,d)
                
                # Find quadrant into which to check further
                q = node.quadrant (pt)
                node = node.children[q]
     
    def add (self, pt, data):
        """Add (pt, data) to the QuadNode."""
        node = self
        while node:
            # Not able to fit in this region
            if not containsPoint (node.region, pt):
                return False
        
            # if we have points, then we are leaf node. Check here
            if node.points != None:
                if pt in node.points:
                    return False

                # Add if room                
                if len(node.points) < 4:
                    node.points.append (pt)
                    node.data.append (data)
                    return True
            
            # Find quadrant into which to add
            q = node.quadrant (pt)
            if node.children[q] is None:
                # subdivide and reassign points to each quadrant. Then add point
                node.subdivide()
            node = node.children[q]
            
        return False

    def remove(self, pt):
        """Remove pt (and its data) from QuadNode. Does not adjust structure. Return True if updated information."""
        if self.points != None:
            if pt in self.points:
                idx = self.points.index(pt)
                del self.points[idx]
                del self.data[idx]
                return True
            
        return False

    def subdivide(self):
        """Add four children nodes to node and reassign existing points."""
        
        region = self.region.copy()
        region.x_min = self.origin[X]
        region.y_min = self.origin[Y]
        self.children[NE] = QuadNode(region)
        
        region = self.region.copy()
        region.x_max = self.origin[X]
        region.y_min = self.origin[Y]
        self.children[NW] = QuadNode(region)
        
        region = self.region.copy()
        region.x_max = self.origin[X]
        region.y_max = self.origin[Y]
        self.children[SW] = QuadNode(region)
            
        region = self.region.copy()
        region.x_min = self.origin[X]
        region.y_max = self.origin[Y]
        self.children[SE] = QuadNode(region)
        
        for pair in zip(self.points, self.data):
            q = self.quadrant(pair[0])
            self.children[q].add(pair[0], pair[1])
        self.points = None
        self.data = None
    
    def quadrant(self, pt):
        """Determine quadrant in which point exists. Closed intervals on quadrants I (NE) and III (SW)."""
        if pt[X] >= self.origin[X]:
            if pt[Y] >= self.origin[Y]:
                return NE
            else:
                return SE
        else:
            if pt[Y] >= self.origin[Y]:
                return NW
            else:
                return SW
     
    def range(self, region):
        """
        Yield (node,status) in Quad Tree contained within region. When status is True, then all descendant
        nodes are part of the region, otherwise just the selected point.
        """
        if region.containsRegion (self.region):
            yield (self, True)
        else:
            # if we have points, then we are leaf node. Check here
            if self.points != None:
                for i in range(len(self.points)):
                    if containsPoint (region, self.points[i]):
                        yield ((self.points[i], self.data[i]), False)
            else:
                for child in self.children:
                    if child.region.overlap (region):
                        for pair in child.range (region):
                            yield pair
     
    def preorder(self):
        """In order traversal of tree rooted at given node."""
        yield self

        for node in self.children:
            if node:
                for n in node.preorder():
                    yield n

    def __str__(self):
        """toString representation."""
        return "[{} ({}): {},{},{},{}]".format(self.region, self.points, self.children[NE], self.children[NW], self.children[SW], self.children[SE])

class QuadTree:

    def __init__(self, region):
        """
        Create Quad Tree defined over existing rectangular region. Assume that (0,0) is the center
        and half-length side of any square in quadtree is power of 2. If incoming region is too small, then
        this expands accordingly.    
        """
        self.root = None
        self.region = region.copy()
        
        xmin2k = smaller2k(self.region.x_min)
        ymin2k = smaller2k(self.region.y_min)
        xmax2k = larger2k(self.region.x_max)
        ymax2k = larger2k(self.region.y_max)
        
        self.region.x_min = self.region.y_min = min(xmin2k, ymin2k)
        self.region.x_max = self.region.y_max = max(xmax2k, ymax2k)
        
    def add(self, pt, data = None):
        """Add point to Quad Tree."""
        if self.root is None:
            self.root = QuadNode(self.region, pt, data)
            return True
        
        return self.root.add (pt, data)
    
    def collide(self, pt, r):
        """Return collisions to point within Quad Tree."""
        if self.root is None:
            return None
        
        return self.root.collide (pt, r)
    
    def range(self, region):
        """Yield (node,status) in Quad Tree contained within region."""
        if self.root is None:
            return None
    
        return self.root.range(region)
    
    def remove(self, pt):
        """Remove (pt,data) should it exist in tree."""
        n = self.root
        while n:
            if n.points != None:
                for i in range(len(n.points)):
                    if n.points[i] == pt:
                        del n.points[i]
                        del n.data[i]
                        return True

            q = n.quadrant(pt)
            if n.children[q] is None:
                return False
            else:
                n = n.children[q]
    
        return False
    
    def find(self, pt):
        """Find and return first (point,data) in QuadTree within 5 units of target."""
        n = self.root
        while n:
            for i in range(len(n.points)):
                if distance(n.points[i], pt) < 5:
                    return (n.points[i], n.data[i])

            q = n.quadrant(pt)
            if n.children[q] is None:
                return False
            else:
                n = n.children[q]
    
        return False
    
    def __iter__(self):
        """In order traversal of elements in the tree."""
        if self.root:
            for e in self.root.preorder():
                yield e
        