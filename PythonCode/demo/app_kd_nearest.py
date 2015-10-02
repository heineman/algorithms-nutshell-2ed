"""
    Demonstration application for nearest neighbor using kd tree
"""

import tkinter
from adk.kd import KDTree, X, Y, VERTICAL
from adk.region import minValue, maxValue

RectangleSize = 4
shortline = 'shortline'
closest = 'closest'

class KDTreeApp:
    
    def __init__(self):
        """App for creating KD tree dynamically and executing nearest neighbor queries."""
        
        self.tree = KDTree()
        self.match = None
        self.shortline = None
        
        # set to true when you want to view a set of points that
        # were set not by user control.
        self.static = False
        
        self.master = tkinter.Tk()
        self.master.title('KD Tree Nearest Neighbor Application')
        self.w = tkinter.Frame(self.master, width=410, height=410)
        self.canvas = tkinter.Canvas(self.w, width=400, height=400)        
                        
        self.paint()

        self.canvas.bind("<Button-1>", self.click)
        self.canvas.bind("<Motion>", self.moved)
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

    def moved(self, event):
        """React to mouse move events."""
        if self.static:
            self.paint()
            return
        
        p = (event.x, self.toCartesian(event.y))

        match = self.tree.find(p)
        if match:
            p = match.point
            self.canvas.create_rectangle(p[X] - RectangleSize, self.toTk(p[Y]) - RectangleSize, 
                                         p[X] + RectangleSize, self.toTk(p[Y]) + RectangleSize, fill='Red', 
                                         tags=closest)
            self.canvas.delete(self.shortline)
            self.shortline = None
        else:
            self.canvas.delete(closest)
            n = self.tree.nearest(p)
            if n:
                pn = n.point
                if self.shortline is None:
                    self.shortline = self.canvas.create_line(pn[X], self.toTk(pn[Y]), p[X], self.toTk(p[Y]), 
                                                             fill='Red', dash=(2, 4), tags=shortline)
                else:
                    self.canvas.coords(shortline, pn[X], self.toTk(pn[Y]), p[X], self.toTk(p[Y]))
         
          
    def click(self, event):
        """Add point to KDTree."""
        p = (event.x, self.toCartesian(event.y))
        
        self.tree.add(p)
        if self.shortline:
            self.canvas.delete(self.shortline)
            self.shortline = None
            
        self.paint()

    def drawPartition (self, r, p, orient):
        """Draw proper partition for given node (r,p) and orientation."""
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
        """Draw KDNode properly partitioned and its sub-children."""
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
        """Paint KDTree by visiting all nodes, or show introductory message."""
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
    
