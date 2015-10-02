"""
    Demonstration application for collision detection. Each point has data which is (dx,dy,hit) reflecting
    motion of square and how many cycles to draw as 'hit'
    
    Left mouse adds point.
    
    The quad tree enforces uniqueness of points. In this brief example, don't worry about this situation. 
    This leads to squares occasionally 'disappearing' because they can't be added with new coordinates.
    
"""

import tkinter
import random

from adk.quad import QuadTree
from adk.region import Region, minValue, maxValue, X, Y

RectangleSize = 4
frameDelay = 40
HIT = 2
MaxHit = 3

class QuadTreeApp:
    
    def __init__(self):
        """App for creating Quad tree dynamically with moving squares that detect collisions."""
        
        self.tree = QuadTree(Region(0,0,512,512))
        self.match = None
        
        # for range query
        self.selectedRegion = None
        self.queryRect = None
        
        self.master = tkinter.Tk()
        self.master.title('Quad Tree Collision Detection')
        self.w = tkinter.Frame(self.master, width=512, height=512)
        self.canvas = tkinter.Canvas(self.w, width=512, height=512)        
                        
        self.paint()

        self.master.after(frameDelay, self.updateSquares)
        
        self.canvas.bind("<Button-1>", self.click)
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
         
    def click(self, event):
        """Add point to QuadTree with random motion in place. Let animation paint as appropriate."""
        p = (event.x, self.toCartesian(event.y))
        dx = random.randint(1,4)*(2*random.randint(0,1)-1)
        dy = random.randint(1,4)*(2*random.randint(0,1)-1)
        self.tree.add(p, [dx,dy,0])

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
            for (p,d) in zip(node.points,node.data):
                markColor = 'Black'
                if d[HIT]:
                    markColor = 'Red'
                self.canvas.create_rectangle(p[X] - RectangleSize, self.toTk(p[Y]) - RectangleSize, 
                                             p[X] + RectangleSize, self.toTk(p[Y]) + RectangleSize, 
                                             fill=markColor)
        
    def prepare(self, event):
        """prepare to add points"""
        if self.label:
            self.label.destroy()
            self.label = None
            self.canvas.pack()
        
    def updateSquares(self):
        """Move all shapes and repaint."""
        self.master.after(frameDelay, self.updateSquares)
         
        if self.tree.root:
            for n in self.tree.root.preorder():
                if n.points is None: 
                    continue
                
                pts = list(n.points)
                datas = list(n.data)
                for idx in range(len(pts)):
                    pt = pts[idx]
                    data = datas[idx]
                    
                    n.remove(pt)
                    
                    p = [pt[X], pt[Y]]
                    d = [data[X], data[Y], max(0,data[HIT]-1)]
                    if pt[X] + d[X] <= self.tree.region.x_min:
                        d[X] = -d[X]
                    elif p[X] + d[X] >= self.tree.region.x_max:
                        d[X] = -d[X]
                    else:
                        p[X] = pt[X] + d[X]
                        
                    if p[Y] + d[Y] <= self.tree.region.y_min:
                        d[Y] = -d[Y]
                    elif p[Y] + d[Y] >= self.tree.region.y_max:
                        d[Y] = -d[Y]
                    else:
                        p[Y] = pt[Y] + d[Y]
                        
                    # Update hit status for all colliding points
                    for (_,data) in self.tree.collide((p[X],p[Y]), RectangleSize):
                        data[HIT] = MaxHit
                        d[HIT] = MaxHit
                    self.tree.add((p[X],p[Y]),[d[X],d[Y],d[HIT]])
                    
            self.canvas.delete(tkinter.ALL)
            self.visit(self.tree.root)
        
    def paint(self):
        """Paint Quad tree by visiting all nodes, or show introductory message."""
        if self.tree.root:
            self.canvas.delete(tkinter.ALL)
            self.visit(self.tree.root)
        else:
            self.label = tkinter.Label(self.w, width=100, height = 40, text="Click To Add Moving Squares")
            self.label.bind("<Button-1>", self.prepare)
            self.label.pack()
            
if __name__ == "__main__":
    app =  QuadTreeApp()
    app.w.mainloop()
