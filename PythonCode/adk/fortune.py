from heapq import heappop, heappush
from adk.region import maxValue, X, Y
    
# Still occasional errors as size of P increases when running live application.
# Naturally these are hard to track down and seem related to floating
# point errors in the computations.
#
# Here are a bunch of resources I found online:
#   http://www.cs.sfu.ca/~binay/813.2011/Fortune.pdf
#   http://cgm.cs.mcgill.ca/~mcleish/644/Projects/DerekJohns/Sweep.htm
#   http://www.ams.org/samplings/feature-column/fcarc-voronoi
#   http://www.ambrsoft.com/TrigoCalc/Circle3D.htm


class VoronoiPolygon:
    """
    Represents a polygon in the Voronoi Diagram around a point pt=(x,y).
    Points are listed counter clockwise. When horizontal or vertical lines
    in the Voronoi Diagram are computed, the polygons are often incomplete.
    The computed Edges in the Diagram are accurate, however.
    """
    def __init__(self, pt):
        self.points = []
        self.pt = pt
        self.first = None
        self.last = None
    
    def isEmpty(self):
        return self.first is None
    
    def addToEnd(self, pt):   
        if len(self.points) == 0:
            self.first = self.last = pt
            self.points.append(pt)
        else:
            self.points.append(pt)
            self.last = pt 

    def addToFront(self, pt):
        if len(self.points) == 0:
            self.first = self.last = pt
            self.points.append(pt)    
        else:
            self.points.insert(0, pt)
            self.first = pt
            
    def __str__(self):
        rep = '['
        for pt in self.points:
            rep = rep + str(pt) + ','
        rep = rep + ']'
        return rep

class Arc:
    """
    Every interior node represents breakpoint between neighboring 
    sites. If an interior node has two leaf nodes for children, 
    then the breakpoint is between these two nodes. Otherwise
    the breakpoint is between the largest descendant in the left-subtree
    and the smallest descendant in the right-subtree (in other words
    neighboring nodes on the beachline).
    
    Construct leaf node with point.
    
    Every interior node contains references to bisection edge which 
    is initially an infinite half edge but then becomes a full finite 
    edge as the algorithm progresses (or indeed, when finishing edges
    at the end).
    
    Discovered potential circle events are stored with the associated
    Arc node.
    """
    def __init__(self, point = None, edge = None):
        self.parent = None
        self.left = None
        self.right = None
        self.edge = edge
        self.site = point
        self.isLeaf = False
        if point:
            self.isLeaf = True
        self.circleEvent = None
            
    def __str__(self):
        leftS = ''
        if self.left:
            leftS = str(self.left)
        rightS =''
        if self.right:
            rightS = str(self.right)
            
        return '(pt=' + str(self.site) + ', left=' + leftS + ", right=" + rightS +')'

    def pointOnBisectionLine(self, x, sweepY):
        """
        Given y-coordinate of sweep line and desired x intersection, 
        return point on the bisection line with given x coordinate
        """
        if self.site.y == sweepY:
            # vertical line halfway between x and site's x
            return Point(((x+self.site.x)/2,sweepY))
        else:
            # slope of bisection line is negative reciprocal
            # of line connecting points (x,sweepY) and self.site
            m = -(x - self.site.x)/(sweepY - self.site.y)
            halfway = ((x+self.site.x)/2, (sweepY + self.site.y)/2)
            b = halfway[1] - m*halfway[0]
            y = m*x +b
        
        return Point((x,y))

    def setLeft(self, n):
        self.left = n
        n.parent = self
        
    def setRight(self, n):
        self.right = n
        n.parent = self

    def getLeftAncestor(self):
        """
        Find first ancestor with right link to a parent of self (if exists).
        """
        parent = self.parent
        n = self
        while parent != None and parent.left == n:
            n = parent
            parent = parent.parent
        
        return parent
    
    def getRightAncestor(self):
        """
        Find first ancestor with right link to a parent of self (if exists).
        """
        parent = self.parent
        n = self
        while parent != None and parent.right == n:
            n = parent
            parent = parent.parent
        
        return parent
        
    def getLargestLeftDescendant(self):
        """Find largest value in left sub-tree."""
        n = self.left
        while not n.isLeaf:
            n = n.right
        
        return n
        
    def getSmallestRightDescendant(self):
        """Find smallest value in right sub-tree."""
        n = self.right
        while not n.isLeaf:
            n = n.left
        
        return n
        
    def remove(self):
        """Remove leaf node from tree. """
        grandParent = self.parent.parent
        if self.parent.left == self:
            if grandParent.left == self.parent:
                grandParent.setLeft(self.parent.right)
            else:
                grandParent.setRight(self.parent.right)
        else:
            if grandParent.left == self.parent:
                grandParent.setLeft(self.parent.left)
            else:
                grandParent.setRight(self.parent.left)
        
class Event:
    """Event in the queue. If an event is deleted, it still remains in the queue, but is not processed."""
    def __init__(self, p, site=None): 
        self.p = p
        self.site = site 
        self.y = p.y
        self.deleted = False
        
        # Circle events link back to Arc node
        self.node = None

    # built-in methods to support Event being used in priority queue
    def __lt__(self, other):
        """Higher Y values are "smaller" for events. Tie breaker is on smaller x value."""
        if self.y > other.y: 
            return True
        if self.y < other.y:
            return False
        
        if self.p.x < other.p.x:
            return True
        
        return False
    
    def __eq__(self, other):
        return self.p.x == other.p.x and self.p.y == other.p.y
    def __ne__(self, other):
        return not self.p.x == other.p.x or not self.p.y == other.p.y

    # These can all be defined in terms of < above
    def __gt__(self, other):
        return other<self
    def __ge__(self, other):
        return not self<other
    def __le__(self, other):
        return not other<self
    
class Point:
    """
    Every point defines center of a Voronoi Polygon. Maintains index for post-processing.
    Voronoi Polygon is defined here rather than Arc because those objects come and
    go as the BeachLine is processed.
    
    To deal with floating point issues, all values are rounded to four digits of precision.
    This allows test cases to be accurately defined and helps eliminate special cases.
    """
    def __init__(self, p, idx = None):
        """ p is a tuple (x,y)."""
        self.x = round(p[X],4)
        self.y = round(p[Y],4)
        
        self.polygon = VoronoiPolygon((self.x, self.y))
        self.idx = idx

    def __eq__(self, other):
        if other is None: return False
        return self.x == other.x and self.y == other.y
    
    def __ne__(self, other):
        if other is None: return False
        return self.x != other.x or self.y != other.y

    def __str__(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'

class VoronoiEdge:
    """
    Represents edge not yet placed, initially a true Half Edge formed as infinite ray.
    
    Edge has orientation based on the relative location of left and right points, which
    is used when detecting intersections. This is a subtle but critical part of the 
    algorithm.
    """
    def __init__(self, p, left, right):
                
        self.left = left
        self.right = right
        self.partner = None
        self.end = None
        
        # record orientation from sweep point of view (first means seen first by sweep).
        self.rightYFirst = right.y > left.y
        self.rightXFirst = right.x < left.x
        
        # remember orientation of edge
        if left.y == right.y:
            # vertical line is handled specially by declaring no y-intercept
            self.m = maxValue
            self.b = None
            self.x = (right.x + left.x)/2
            self.start  = Point((p.x, p.y))
        else:
            # Compute line characteristics.
            self.m = (right.x - left.x) / (left.y - right.y)
            self.b = p.y - self.m*p.x
            self.x = None
            self.start  = Point((p.x, p.y))
    
    def finish(self, width, height):
        """
        Close half edge, if self.end does not exist, assuming bounding box.
        Might extend point in both directions. Crop to bounding box as needed.
        """
        if self.rightYFirst:
            y = width*self.m + self.b
            if y < 0:
                p = (-self.b/self.m, 0)
            elif y > height:
                p = ((height - self.b)/self.m, height)
            else:
                p = (width, y)
        elif self.b is None:
            p = (self.x, 0)   
        else:
            p = (0, self.b)

        self.end = Point(p)

    def intersect(self, other):
        """Return point of intersection between two (half-)edges."""
        if self.b == None:
            if other.b == None:
                if self.x == other.x:
                    return self.x
                return None
            
            p = Point((self.x, self.x*other.m + other.b))
        elif other.b == None:
            p = Point((other.x, other.x*self.m + self.b))
        else:
            # parallel lines have no intersection
            if self.m == other.m:
                return None
            else:
                x = (other.b - self.b)/(self.m - other.m)
                y = self.m*x + self.b
                p = Point((x,y))
        
       
        # self and other share a point. Ensure intersection is viable
        # based on orientation. Bisecting lines have -1/m slopes, which
        # is why X is paired with Y in the cX variables below.
        self_Xfirst = self.start.x < p.x
        self_Yfirst = self.start.y < p.y
        
        other_Xfirst = other.start.x < p.x
        other_Yfirst = other.start.y < p.y

        c1 = not (self_Xfirst == self.rightYFirst)
        c2 = not (self_Yfirst == self.rightXFirst)
        c3 = not (other_Xfirst == other.rightYFirst)
        c4 = not (other_Yfirst == other.rightXFirst)
        if c1 or c2 or c3 or c4:
            return None  
        return p
     
    def __str__(self):
        endS = ''
        if self.end:
            endS = str(self.end)
        return '[' + str(self.start) + ',' + endS + ']'
    

class Voronoi:
    """Fortune Line Sweep Implementation."""
    
    def __init__(self, width = 800, height = 400):
        """Construct Voronoi diagram within given bounding box."""
        self.width = width
        self.height = height
        
    def process(self, points):
        """Process given points, represented as tuple (x,y) to return edge collection.""" 
        self.pq = []
        self.edges = []
        self.tree = None
        self.firstPoint = None     # handle tie breakers with first 
        self.stillOnFirstRow = True
        self.points = []
        
        # Each point has unique identifier
        for idx in range(len(points)):
            pt = Point(points[idx], idx)
            self.points.append(pt)
            event = Event(pt, site=pt)
            heappush(self.pq, event)
            
        while self.pq:
            event = heappop(self.pq)
            if event.deleted:
                continue
            
            self.sweepPt = event.p
            
            # Special case if multiple points are all on first row.
            if self.stillOnFirstRow and self.firstPoint:
                if self.sweepPt.y != self.firstPoint.y:
                    self.stillOnFirstRow = False
            
            if event.site:
                self.processSite(event)
            else:
                self.processCircle(event) 
            
        # complete edges that remain and stretch to infinity
        if self.tree and not self.tree.isLeaf:
            self.finishEdges(self.tree)
        
            # Complete Voronoi Edges with partners.
            for e in self.edges:
                if e.partner:
                    if e.b is None: 
                        e.start.y = self.height
                    else:
                        e.start = e.partner.end
        
    def findArc(self, x):
        """
        Find correct arc leaf node in BeachLine for this x coordinate. Don't have to
        check each parabola, only 2*log(n) of them.
        """
        n = self.tree
        while not n.isLeaf:
            lineX = self.computeBreakPoint(n)   # compute breakpoint based on sweep line
        
            # if tie, can choose either one.
            if lineX > x:
                n = n.left
            else:
                n = n.right

        return n
        
    def computeBreakPoint(self, n):
        """
        With sweep line Y coordinate and left/right children of interior node. You want 
        to find the x-coordinate of the breakpoint, which changes based upon the y-value
        of the sweep line. Must compute intersection of two parabolas.
 
        Parabola can be computed as 4p(y-k)=(x-h)^2 where (h,k) is the site point, which
        becomes the focal point for the parabola. p is the distance to the directrix 
        (aka, the sweep line) from the site's point (site.y - sweepPt.y)
         
        y1 = (1/4p1)x^2 + (-h1/2p1)x + (h1^2/4p1+k1) and compute for (h2,k2,p2)
        
        Only subtlety is that to simplify equation, normalize y-coordinates so
        k1 = p1/4 and k2 = p2/4; seems to eliminate most errors.
        
        Now set to each other and subtract to get:
        
        0 = (1/4p1 - 1/4p2)x^2 + (-h1/2p1 + h2/2p2) + (h1^2/4p1+k1) - (h2^2/4p2+k2)
        
        Compute for x using quadratic formula: (-b +/- sqrt(b^2-4ac))/2a
        """
        left = n.getLargestLeftDescendant()
        right = n.getSmallestRightDescendant()
        
        # degenerate case: might be same point, so return it.
        if left.site == right.site:
            return left.site.x
        
        # both on horizontal line? Decide based on relation to sweepPt.x
        p1 = left.site.y - self.sweepPt.y
        p2 = right.site.y - self.sweepPt.y
        if p1 == 0 and p2 == 0:
            if self.sweepPt.x > right.site.x:
                return right.site.x
            elif self.sweepPt.x < left.site.x:
                return left.site.x
            else:
                # between, so can choose either one. Go right
                return right.site.x
                
        # on same horizontal line as sweep. Break arbitrarily
        if p1 == 0:
            return left.site.x
        if p2 == 0:
            return right.site.x
        
        h1 = left.site.x
        h2 = right.site.x
        
        a = 1/(4*p1) - 1/(4*p2)
        b = -h1/(2*p1) + h2/(2*p2)
        c = (p1/4 + h1*h1/(4*p1)) - (p2/4 + h2*h2/(4*p2))
        
        # not quadratic. only one solution. What if b is zero?
        if a == 0:
            x = -c/b
            return x
        
        # two solutions, possibly
        sq = b*b - 4*a*c
        
        x1 = (-b - (sq**0.5))/(2*a)
        x2 = (-b + (sq**0.5))/(2*a)

        # since left.site is to the left of right.site, base decision on respective heights
        if left.site.y < right.site.y:
            return max(x1, x2)
        return min(x1,x2)
  

    def processSite(self, event):
        """Process a site event from the queue."""
        
        if self.tree == None:
            self.tree = Arc(event.p)
            self.firstPoint = event.p
            return

        # must handle special case when two points are at top-most y coordinate, in 
        # which case the root is a leaf node. Note that when sorting events, ties
        # are broken by x coordinate, so the next point must be to the right
        if self.tree.isLeaf and event.y == self.tree.site.y:
            left = self.tree
            right = Arc(event.p)
        
            start = Point(((self.firstPoint.x + event.p.x)/2, self.height))
            edge = VoronoiEdge(start, self.firstPoint, event.p)
            
            self.tree = Arc(edge = edge)
            self.tree.setLeft(left)
            self.tree.setRight(right)

            self.edges.append(edge)
            return
    
        # find point on parabola where event.pt.x bisects with vertical line,
        leaf = self.findArc(event.p.x)
        
        # Special case where there are multiple points, all horizontal with first point
        # so keep expanding to the right
        if self.stillOnFirstRow:
            leaf.setLeft(Arc(leaf.site))
            start = Point(((leaf.site.x + event.p.x)/2, self.height))
            
            leaf.edge = VoronoiEdge(start, leaf.site, event.p)
            leaf.isLeaf = False
            leaf.setRight(Arc(event.p))
            
            self.edges.append(leaf.edge)
            return
        
        # If leaf had a circle event, it is no longer valid
        # since it is being split
        if leaf.circleEvent:
            leaf.circleEvent.deleted = True
        
        # Voronoi edges discovered between two sites. Leaf.site is higher
        # giving orientation to these edges.
        start = leaf.pointOnBisectionLine (event.p.x, self.sweepPt.y)
        negRay = VoronoiEdge(start, leaf.site, event.p)
        posRay = VoronoiEdge(start, event.p, leaf.site)
        negRay.partner = posRay
        self.edges.append (negRay)
        
        # old leaf becomes root of two nodes, and grandparent of two
        leaf.edge = posRay
        leaf.isLeaf = False
        
        left = Arc()      
        left.edge = negRay
        left.setLeft (Arc(leaf.site))
        left.setRight (Arc(event.p))
        
        leaf.setLeft (left)
        leaf.setRight (Arc(leaf.site)) 
         
        self.generateCircleEvent (left.left)
        self.generateCircleEvent (leaf.right)

    def finishEdges(self, n):
        """
        Close all Voronoi edges against maximum bounding box, based on how edge extends. 
        """
        n.edge.finish(self.width, self.height)
        n.edge.left.polygon.addToFront(n.edge.end)
        n.edge.right.polygon.addToEnd(n.edge.end)
        
        if not n.left.isLeaf:
            self.finishEdges(n.left)
        if not n.right.isLeaf:
            self.finishEdges(n.right)
        

    def generateCircleEvent(self, node):
        """
        There is possibility of a circle event with this new node being the
        middle of three consecutive nodes. If so, then add new circle 
        event to the priority queue for further processing.
        """
        # Find neighbor on the left and right, should they exist.
        leftA = node.getLeftAncestor()
        if leftA is None:
            return
        left = leftA.getLargestLeftDescendant()
        
        rightA = node.getRightAncestor()
        if rightA is None:
            return
        right = rightA.getSmallestRightDescendant()
    
        # sanity check. Must be different
        if left.site == right.site:
            return
        
        # If two edges have no intersection, leave now
        p = leftA.edge.intersect (rightA.edge)
        if p is None:
            return
        
        radius = ((p.x-left.site.x)**2 + (p.y-left.site.y)**2)**0.5
        
        # make sure choose point at bottom of circumcircle
        circleEvent = Event(Point((p.x, p.y-radius)))   
        if circleEvent.p.y >= self.sweepPt.y: 
            return
            
        node.circleEvent = circleEvent
        circleEvent.node = node
        heappush (self.pq, circleEvent)

    def processCircle(self, event):
        """Process circle event."""
        node = event.node
        
        # Find neighbor on the left and right.
        leftA  = node.getLeftAncestor()
        left   = leftA.getLargestLeftDescendant()
        rightA = node.getRightAncestor()
        right  = rightA.getSmallestRightDescendant()
        
        # Eliminate old circle events if they exist.
        if left.circleEvent:
            left.circleEvent.deleted = True
        if right.circleEvent:
            right.circleEvent.deleted = True
            
        # Circle defined by left - node - right. Terminate Voronoi rays
        p = node.pointOnBisectionLine(event.p.x, self.sweepPt.y)

        # this is a real Voronoi point! Add to appropriate polygons
        if left.site.polygon.last == node.site.polygon.first:
            node.site.polygon.addToEnd(p)
        else:
            node.site.polygon.addToFront(p)
        
        left.site.polygon.addToFront(p)
        right.site.polygon.addToEnd(p)
        
        # Found Voronoi vertex. Update edges appropriately
        leftA.edge.end = p
        rightA.edge.end = p
       
        # Find where to record new voronoi edge. Place with
        # (left) or (right), depending on which of leftA/rightA is higher
        # in the beachline tree. Without loss of generality, assume leftA is higher. 
        # Reason is because node is being deleted and the highest ancestor (leftA) is 
        # interior node that represents breakpoint involving node, and this interior
        # node must now represent the breakpoint [left|right]. Since leftA is higher,
        # it will remain while rightA is being removed (effectively replaced by right).
        t = node
        ancestor = None
        while t != self.tree:
            t = t.parent
            if t == leftA:
                ancestor = leftA
            elif t == rightA:
                ancestor = rightA
            
        ancestor.edge = VoronoiEdge (p, left.site, right.site)
        self.edges.append (ancestor.edge)
        
        # eliminate middle arc (leaf node) from beach line tree
        node.remove()
        
        # May find new neighbors after deletion so must check
        # for circles as well...
        self.generateCircleEvent(left)
        self.generateCircleEvent(right)
