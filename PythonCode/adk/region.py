"""
    Defined rectangular region
"""

maxValue = 2147483647
minValue = -2147483648

X = 0
Y = 1

class Region:
    """Represents region in Cartesian space"""

    def __init__(self, xmin,ymin, xmax,ymax):   
        """
        Creates region from two points (xmin,ymin) to (xmax,ymax). If these are not the bottom left
        and top right coordinates for a region, this constructor will properly compute them.
        """
        self.x_min = xmin if xmin < xmax else xmax
        self.y_min = ymin if ymin < ymax else ymax
        self.x_max = xmax if xmax > xmin else xmin
        self.y_max = ymax if ymax > ymin else ymin

    def copy(self):
        """Return copy of region"""
        return Region(self.x_min, self.y_min, self.x_max, self.y_max)

    def area(self):
        """Return area of rectangular region."""
        return (self.x_max - self.x_min)*(self.y_max - self.y_min)

    def unionRect(self, other):
        """Return new region as union of two regions."""
        mx1 = min(self.x_min, other.x_min)
        mx2 = max(self.x_max, other.x_max)
        my1 = min(self.y_min, other.y_min)
        my2 = max(self.y_max, other.y_max)
        
        return Region(mx1, my1, mx2, my2)

    def unionPoint(self, pt):
        """Return new region as union of region and point."""
        mx1 = min(self.x_min, pt[X])
        mx2 = max(self.x_max, pt[X])
        my1 = min(self.y_min, pt[Y])
        my2 = max(self.y_max, pt[Y])
        
        return Region(mx1, my1, mx2, my2)

    def overlap(self, other):
        """Return rectangle of intersection."""
        x1 = self.x_min
        y1 = self.y_min
        
        x2 = self.x_max
        y2 = self.y_max
        
        if x1 < other.x_min: x1 = other.x_min
        if y1 < other.y_min: y1 = other.y_min
        if x2 > other.x_max: x2 = other.x_max
        if y2 > other.y_max: y2 = other.y_max

        # if x1 == x2 or y1 == y2 then line or point returned
        return Region(x1, y1, x2, y2);

    def overlaps(self, other):
        """Return True if rectangles overlap each other in any way."""
        if self.x_max < other.x_min: 
            return False
        if self.x_min > other.x_max:
            return False
        if self.y_max < other.y_min:
            return False
        if self.y_min > other.y_max:
            return False
        
        return True
        

    def containsPoint(self, point):
        """Returns True if point contained in rectangle."""
        if point[X] < self.x_min: return False
        if point[X] > self.x_max: return False
        if point[Y] < self.y_min: return False
        if point[Y] > self.y_max: return False
        
        return True
         
    def containsRegion(self, region):
        """Returns True if region contained in rectangle."""
        if region.x_min < self.x_min: return False
        if region.x_max > self.x_max: return False
        if region.y_min < self.y_min: return False
        if region.y_max > self.y_max: return False
        
        return True
    
    def __str__(self):
        """Return string representation."""
        return "({},{} , {},{})".format(self.x_min, self.y_min, self.x_max, self.y_max)

    def __eq__(self, other):
        """Standard equality check."""
        if isinstance(other, self.__class__):
            return self.__dict__ == other.__dict__
        else:
            return False

    def __ne__(self, other):
        """Standard not-equality check."""
        return not self.__eq__(other)

# default maximum region
maxRegion = Region(minValue, minValue, maxValue, maxValue)
