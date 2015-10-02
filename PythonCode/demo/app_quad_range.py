"""
    Demonstration application for range search using quad tree.
    
    Left mouse adds point. 
    Right mouse click begins drag of rectangle.
"""

import tkinter
from adk.quad import QuadTree
from adk.region import Region, minValue, maxValue, X, Y

RectangleSize = 4
W=256
H=256

class QuadTreeApp:
    
    def __init__(self):
        """App for creating Quad tree dynamically and executing range queries."""
        
        self.tree = QuadTree(Region(0,0,W,H))
        self.match = None
        
        # for range query
        self.selectedRegion = None
        self.queryRect = None
        
        self.master = tkinter.Tk()
        self.master.title('Quad Tree Range Application')
        self.w = tkinter.Frame(self.master, width=W, height=H)
        self.canvas = tkinter.Canvas(self.w, width=W, height=H)        
                        
        self.paint()

        self.canvas.bind("<Button-1>", self.click)
        self.canvas.bind("<Button-3>", self.range)   # when right mouse clicked
        self.canvas.bind("<ButtonRelease-3>", self.clear)
        self.canvas.bind("<B3-Motion>", self.range)  # only when right mouse dragged
        self.w.pack()
        

    def toCartesian(self, y):
        """Convert tkinter point into Cartesian"""
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
        # all in Yellow using another inorder traversal
        for pair in self.tree.range(self.selectedRegion):
            
            if pair[1]:
                self.canvas.create_rectangle(pair[0].region.x_min, self.toTk(pair[0].region.y_min), 
                                             pair[0].region.x_max, self.toTk(pair[0].region.y_max),
                                             fill='Red', stipple='gray12')
            else:
                p = pair[0][0]   # ignore data and grab point
                self.canvas.create_rectangle(p[X] - RectangleSize, self.toTk(p[Y]) - RectangleSize, 
                                             p[X] + RectangleSize, self.toTk(p[Y]) + RectangleSize, fill='Red')
        
        
        self.queryRect = self.canvas.create_rectangle(self.selectedRegion.x_min, self.toTk(self.selectedRegion.y_min),  
                                                     self.selectedRegion.x_max, self.toTk(self.selectedRegion.y_max), 
                                                     outline='Red', dash=(2, 4))
        
    def click(self, event):
        """Add point to QuadTree."""
        p = (event.x, self.toCartesian(event.y))
        
        self.tree.add(p)
             
        self.paint()

         
    def visit (self, node):
        """ Visit node to paint properly."""
        if node == None: return

        if node.points is None: 
            r = node.region
            self.canvas.create_rectangle(r.x_min, self.toTk(r.y_min), r.x_max, self.toTk(r.y_max))
            
            self.canvas.create_line(r.x_min, self.toTk(node.origin[Y]), r.x_max, self.toTk(node.origin[Y]),
                                    fill='black', dash=(2, 4)) 
            self.canvas.create_line(node.origin[X], self.toTk(r.y_min), node.origin[X], self.toTk(r.y_max),
                                    fill='black', dash=(2, 4))
            for n in node.children:
                self.visit(n)
        else:
            for p in node.points:
                self.canvas.create_rectangle(p[X] - RectangleSize, self.toTk(p[Y]) - RectangleSize, 
                                             p[X] + RectangleSize, self.toTk(p[Y]) + RectangleSize, fill='Black')
        
    def prepare(self, event):
        """prepare to add points."""
        if self.label:
            self.label.destroy()
            self.label = None
            self.canvas.pack()
        
    def paint(self):
        """Paint Quad tree by visiting all nodes, or show introductory message."""
        if self.tree.root:
            self.canvas.delete(tkinter.ALL)
            self.visit(self.tree.root)
        else:
            self.label = tkinter.Label(self.w, width=100, height = 40, text="Click To Add Points")
            self.label.bind("<Button-1>", self.prepare)
            self.label.pack()
            
if __name__ == "__main__":
    app = QuadTreeApp()
    app.w.mainloop()
