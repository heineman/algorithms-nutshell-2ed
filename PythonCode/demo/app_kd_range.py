"""
    Demonstration application for range search using kd tree.
    
    Left mouse adds point. 
    Right mouse click begins drag of rectangle.
"""

import tkinter
from adk.kd import KDTree, X, Y, VERTICAL
from adk.region import Region, minValue, maxValue

RectangleSize = 4

class KDTreeApp:
    
    def __init__(self):
        """App for creating KD tree dynamically and executing range queries."""
        
        self.tree = KDTree()
        self.static = False
        
        # for range query
        self.selectedRegion = None
        self.queryRect = None
        
        self.master = tkinter.Tk()
        self.master.title('KD Tree Range Query Application')
        self.w = tkinter.Frame(self.master, width=410, height=410)
        self.canvas = tkinter.Canvas(self.w, width=400, height=400)        
                        
        self.paint()

        self.canvas.bind("<Button-1>", self.click)
        self.canvas.bind("<Motion>", self.moved)
        self.canvas.bind("<Button-3>", self.range)   # when right mouse clicked
        self.canvas.bind("<ButtonRelease-3>", self.clear)
        self.canvas.bind("<B3-Motion>", self.range)  # only when right mouse dragged
        self.w.pack()
        

    def toCartesian(self, y):
        """Convert tkinter point into Cartesian."""
        return self.w.winfo_height() - y

    def toTk(self,y):
        """Convert Cartesian into tkinter point."""
        if y == maxValue: return 0
        tk_y = self.w.winfo_height()
        if y != minValue:
            tk_y -= y
        return tk_y
         
    def clear(self, event):
        """End of range search."""
        self.selectedRegion = None
        self.paint()
        
    def range(self, event):
        """Initiate a range search using a selected rectangular region."""
        
        p = (event.x, self.toCartesian(event.y))
         
        if self.selectedRegion is None:
            self.selectedStart = Region(p[X],p[Y],  p[X],p[Y])
        self.selectedRegion = self.selectedStart.unionPoint(p)
        
        self.paint()
        
        # return (node,status) where status is True if draining entire tree rooted at node. Draw these
        # as shaded red rectangle to identify whole sub-tree is selected.
        for pair in self.tree.range(self.selectedRegion):
            p = pair[0].point
            
            if pair[1]:
                self.canvas.create_rectangle(pair[0].region.x_min, self.toTk(pair[0].region.y_min), 
                                             pair[0].region.x_max, self.toTk(pair[0].region.y_max),
                                             fill='Red', stipple='gray12')
            else:
                self.canvas.create_rectangle(p[X] - RectangleSize, self.toTk(p[Y]) - RectangleSize, 
                                             p[X] + RectangleSize, self.toTk(p[Y]) + RectangleSize, fill='Red')
        
        self.queryRect = self.canvas.create_rectangle(self.selectedRegion.x_min, self.toTk(self.selectedRegion.y_min),  
                                                     self.selectedRegion.x_max, self.toTk(self.selectedRegion.y_max), 
                                                     outline='Red', dash=(2, 4))
        
        
    def moved(self, event):
        """Only here for static option."""
        if self.static:
            self.paint()
        
    def click(self, event):
        """Add point to KDtree."""
        p = (event.x, self.toCartesian(event.y))
        
        self.tree.add(p)
             
        self.paint()

    def drawPartition (self, r, p, orient):
        """Draw partitioning line and points itself as a small square."""
        if orient == VERTICAL:
            self.canvas.create_line(p[X], self.toTk(r.y_min), p[X], self.toTk(r.y_max))
        else:
            xlow = r.x_min
            if r.x_min <= minValue: xlow = 0
            xhigh = r.x_max
            if r.x_max >= maxValue: xhigh = self.w.winfo_width()

            self.canvas.create_line(xlow, self.toTk(p[Y]), xhigh, self.toTk(p[Y]))

        self.canvas.create_rectangle(p[X] - RectangleSize, self.toTk(p[Y]) - RectangleSize,
                                     p[X] + RectangleSize, self.toTk(p[Y]) + RectangleSize, fill='Black')

    def visit (self, n):
        """ Visit node to paint properly."""
        if n == None: return

        self.drawPartition(n.region, n.point, n.orient)

        self.visit (n.below)
        self.visit (n.above)

    def prepare(self, event):
        """prepare to add points."""
        if self.label:
            self.label.destroy()
            self.label = None
            self.canvas.pack()
        
    def paint(self):
        """Paint quad tree by visiting all nodes, or show introductory message."""
        if self.tree.root:
            self.canvas.delete(tkinter.ALL)
            self.visit(self.tree.root)
        else:
            self.label = tkinter.Label(self.w, width=100, height = 40, text="Click To Add Points")
            self.label.bind("<Button-1>", self.prepare)
            self.label.pack()
            

    
if __name__ == "__main__":
    app = KDTreeApp()
    app.w.mainloop()
