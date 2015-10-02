"""
    KD Tree Implementation.
    
    If Add modifies the set, then return True, otherwise return False.
    
    Optional data can be associated with the nodes in the KD Tree.
"""

from adk.region import maxRegion, X, Y

HORIZONTAL = 0
VERTICAL   = 1

class KDNode:

    def __init__(self, pt, orient, data = None, region = maxRegion):
        """Create empty KDNode."""
        self.point  = pt
        self.data   = data
        self.orient = orient
        self.above  = None
        self.below  = None
        self.region = region

    def createChild(self, pt, below, data = None):
        """Create child node (either above or below) given node with pt and data."""

        r = self.region.copy()
        if self.orient == VERTICAL:
            if below:
                r.x_max = self.point[X]
            else:
                r.x_min = self.point[X]
        else:
            if below:
                r.y_max = self.point[Y]
            else:
                r.y_min = self.point[Y]

        return KDNode(pt, 1-self.orient, data, r)

    def distance(self, pt):
        """Compute distance from self to pt."""
        if pt:
            return ((self.point[X] - pt[X])**2 + (self.point[Y] - pt[Y])**2) ** 0.5

    def isBelow(self, pt):
        """Is point below current node."""
        if self.orient == VERTICAL:
            return pt[X] < self.point[X]
        return pt[Y] < self.point[Y]

    def isAbove(self, p):
        """Determine if point is below partitioning line."""
        if self.orient == VERTICAL:
            return p[X] >= self.point[X]
        return p[Y] >= self.point[Y]

    def add(self, pt, data):
        """Add point to the KD Tree rooted at this node. Return True if updated structure."""

        if self.point == pt:
            return False

        if self.isBelow(pt):
            if self.below:
                return self.below.add(pt, data)
            else:
                self.below = self.createChild(pt, True, data)
        else:
            if self.above:
                return self.above.add(pt, data)
            else:
                self.above = self.createChild(pt, False, data)

        return True
    
    def range(self, region):
        """
        Yield (node,status) in KD Tree contained within region. When status is True, then all descendant
        nodes are part of the region, otherwise just the selected point.
        """
        
        if region.containsRegion(self.region):
            yield (self, True)
        else:
            if region.containsPoint(self.point):
                yield (self, False)
                
            if self.below:
                if (self.orient == VERTICAL and region.x_min <= self.point[X]) or    \
                   (self.orient == HORIZONTAL and region.y_min <= self.point[Y]):
                    for pair in self.below.range(region):
                        yield pair
            if self.above:
                if (self.orient == VERTICAL and self.point[X] <= region.x_max) or    \
                   (self.orient == HORIZONTAL and self.point[Y] <= region.y_max):
                    for pair in self.above.range(region):
                        yield pair
            

    def nearest(self, mind, p):
        """Return closest node from given subtree to point."""
        d = self.distance(p)
        result = None
        if (d < mind):
            result = self
            mind = d
            
        if self.orient == VERTICAL:
            dp = abs(self.point[X] - p[X])
        else:
            dp = abs(self.point[Y] - p[Y])

        if dp < mind:
            if self.above:
                pt = self.above.nearest(mind, p)
                md = self.above.distance(p)
                if md < mind:
                    result = pt
                    mind = md
            if self.below:
                pt = self.below.nearest(mind,p)
                md = self.below.distance(p)
                if md < mind:
                    result = pt
                    mind = md
        else:
            pt = None
            if self.isAbove(p) and self.above:
                pt = self.above.nearest(mind, p)
            elif self.isBelow(p) and self.below:
                pt = self.below.nearest(mind, p)
            if pt:
                return pt

        return result
    
    def inorder(self):
        """In order traversal of tree rooted at given node that yields KDNode objects."""
        if self.below:
            for n in self.below.inorder():
                yield n

        yield self

        if self.above:
            for n in self.above.inorder():
                yield n

class KDTree:

    def __init__(self):
        """Create empty KD Tree."""
        self.root = None

    def add(self, pt, data = None):
        """Add Point to KD Tree."""

        if self.root:
            return self.root.add(pt, data)
        else:
            self.root = KDNode(pt, VERTICAL, data)
            return True

    def range(self, region):
        """Yield (node,status) in KD Tree contained within region."""
        if self.root is None:
            return None
    
        return self.root.range(region)

    def find(self, p):
        """Find first node in KD Tree within 5 units."""

        n = self.root
        while n:
            if n.distance(p) < 5:
                return n

            if n.isBelow(p):
                n = n.below
            else:
                n = n.above
    
        return n

    def nearest(self, p):
        """Return closest node in KD tree to given point."""

        if self.root is None: return None
        
        # find node which would have been parent of point
        n = self.root
        while n.point != p:
            if n.isBelow(p):
                if n.below:
                    n = n.below
                else:
                    break
            elif n.isAbove(p):
                if n.above:
                    n = n.above
                else:
                    break
        
        mind = n.distance(p)
        better = self.root.nearest(mind, p)
        if better:
            return better
        return n

    def __iter__(self):
        """In order traversal of elements in the KD tree."""
        if self.root:
            for e in self.root.inorder():
                yield e

