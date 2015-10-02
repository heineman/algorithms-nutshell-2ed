"""
    Demonstration application for range search using R tree.
    
    Left mouse press starts rectangle add, Left mouse release closes rectangle add 
    Right mouse click begins drag of rectangle.
"""

import tkinter
from adk.R import RTree
from adk.region import Region, minValue, maxValue, X, Y

RectangleSize = 4

class RTreeApp:
    
    def __init__(self):
        """App for creating R tree dynamically and executing range queries"""
        
        self.tree = RTree(m=2, M=4)
        self.ready = False
        
        # for range query
        self.selectedRegion = None
        self.newRegionStart = None
        self.newRegion      = None
        
        # for identifiers
        self.counter = 0
        
        self.master = tkinter.Tk()
        self.master.title('R Tree Range Query Application')
        self.w = tkinter.Frame(self.master, width=512, height=512)
        self.canvas = tkinter.Canvas(self.w, width=512, height=512)        
                        
        self.paint()

        self.canvas.bind("<Button-1>", self.startAdd)
        self.canvas.bind("<B1-Motion>", self.extendAdd)  # only when right mouse dragged
        self.canvas.bind("<ButtonRelease-1>", self.endAdd)
        
        self.canvas.bind("<Button-3>", self.range)   # when right mouse clicked
        self.canvas.bind("<ButtonRelease-3>", self.clear)
        self.canvas.bind("<B3-Motion>", self.range)  # only when right mouse dragged
        self.w.pack()
        
    def startAdd(self, event):
        """End of range search."""
        x = event.x
        y = self.toCartesian(event.y)
        self.newRegionStart = (x,y)
        
    def extendAdd(self, event):
        """End of range search."""
        if self.newRegionStart:
            x = event.x
            y = self.toCartesian(event.y)
            
            self.newRegion = Region (x,y,x,y).unionPoint(self.newRegionStart) 
            
            self.paint()
        
    def endAdd(self, event):
        """End of range search."""
        if self.newRegionStart:
            self.newRegionStart = None
            self.counter += 1
            
            if self.newRegion:
                self.tree.add(self.newRegion, str(self.counter))
                
            self.newRegion = None
            self.paint()

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
        
        # return (node,0,True) where status is True if draining entire tree rooted at node. If False,
        # then (rect,id,False). Draw these
        # as shaded red rectangle to identify whole sub-tree is selected.
        for triple in self.tree.range(self.selectedRegion):
            if triple[2]:
                r = triple[0].region
                self.canvas.create_rectangle(r.x_min, self.toTk(r.y_min), r.x_max, self.toTk(r.y_max),
                                             fill='Red', stipple='gray12')
            else:
                r = triple[0]
                self.canvas.create_rectangle(r.x_min, self.toTk(r.y_min), r.x_max, self.toTk(r.y_max), 
                                             fill='Red')
        
    def click(self, event):
        """Add point to KDtree."""
        p = (event.x, self.toCartesian(event.y))
        
        self.tree.add(p)
             
        self.paint()

    def visit (self, n):
        """ Visit node to paint properly."""
        if n == None: return

        if n.level == 0:
            for idx in range(n.count):
                r = n.children[idx].region
                self.canvas.create_rectangle(r.x_min, self.toTk(r.y_min), r.x_max, self.toTk(r.y_max),
                                     fill='Gray')    
            
        for idx in range(n.count):
            self.visit(n.children[idx])

        # Do after all children so we can see interior nodes, too.
        r = n.region
        self.canvas.create_rectangle(r.x_min, self.toTk(r.y_min), r.x_max, self.toTk(r.y_max),
                                     outline='Black', dash=(2, 4))
        color = 'Gray'
        if n.level == 0:
            color='Black'  
        self.canvas.create_text(r.x_max - 16*len(n.id), self.toTk(r.y_max) + 16, anchor = tkinter.W, 
                                font = "Times 16 bold", fill=color, text=n.id)

    def prepare(self, event):
        """prepare to add points."""
        if self.label:
            self.label.destroy()
            self.label = None
            self.canvas.pack()
        
    def paint(self):
        """Paint R tree by visiting all nodes, or show introductory message."""
        if self.ready:
            self.canvas.delete(tkinter.ALL)
            self.visit(self.tree.root)
            
            if self.newRegion:
                self.canvas.create_rectangle(self.newRegion.x_min, self.toTk(self.newRegion.y_min),
                                         self.newRegion.x_max, self.toTk(self.newRegion.y_max), 
                                         outline='Black', dash=(2, 4))
                
            if self.selectedRegion:
                self.canvas.create_rectangle(self.selectedRegion.x_min, self.toTk(self.selectedRegion.y_min),
                                         self.selectedRegion.x_max, self.toTk(self.selectedRegion.y_max), 
                                         outline='Red', dash=(2, 4))
        else:
            self.label = tkinter.Label(self.w, width=100, height = 40, text="Click To Add Points")
            self.label.bind("<Button-1>", self.prepare)
            self.label.pack()
            self.ready = True

    
if __name__ == "__main__":
    app = RTreeApp()
    app.w.mainloop()
    