"""
    Demonstration application for voronoi diagram.
    
    As each point is added, the voronoi diagram is computed
"""

import tkinter

from adk.fortune import Voronoi
from adk.region import minValue, maxValue, X, Y

RectangleSize = 4

class VoronoiApp:
    
    def __init__(self):
        """App for creating KD tree dynamically and executing nearest neighbor queries."""
        
        # Points that we have added.
        self.points = []
        self.voronoi = None
        
        # set to true when you want to view a set of points that
        # were set not by user control.
        self.static = False
        
        self.master = tkinter.Tk()
        self.master.title('Voronoi Diagram Application')
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
            self.process(self.points)
            return
        
        # during movement, add points to track mouse
        p = (event.x, self.toCartesian(event.y))
        
        # Avoid duplicates for cleaner implementation
        if p not in self.points: 
            fullPoints = self.points + [p]
            self.process(fullPoints)         
          
    def click(self, event):
        """Add point to Voronoi collection."""
        if self.static:
            self.process(self.points)
            return
        
        p = (event.x, self.toCartesian(event.y))
        
        # Avoid duplicates for cleaner implementation
        if p not in self.points: 
            self.points.append(p)
            self.process(self.points) 

    def process(self, collection):
        """Now that Voronoi diagram is computed, act on it."""
        self.voronoi = Voronoi(400, 400)
        self.voronoi.process(collection)
        self.canvas.delete(tkinter.ALL)
        
        for fp in collection:
            self.canvas.create_rectangle(fp[X] - RectangleSize, self.toTk(fp[Y]) - RectangleSize, 
                                         fp[X] + RectangleSize, self.toTk(fp[Y]) + RectangleSize, fill='Red')
        
        for edge in self.voronoi.edges:
            start = edge.start
            end = edge.end
            self.canvas.create_line(start.x, self.toTk(start.y), end.x, self.toTk(end.y))

    def prepare(self, event):
        """prepare to add points."""
        if self.label:
            self.label.destroy()
            self.label = None
            self.canvas.pack()
        
    def paint(self):
        """Paint KDTree by visiting all nodes, or show introductory message."""
        if self.voronoi:
            self.canvas.delete(tkinter.ALL)
        else:
            self.label = tkinter.Label(self.w, width=100, height = 40, text="Click To Add Points")
            self.label.bind("<Button-1>", self.prepare)
            self.label.pack()
            

if __name__ == "__main__":
    app = VoronoiApp()
    app.w.mainloop()
    
