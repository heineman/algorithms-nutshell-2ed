from demo.app_R_range import RTreeApp
from adk.region import Region

if __name__ == "__main__":
    ra = RTreeApp()
    ra.ready = True
    
    for x in range(1,50):
        ra.tree.add(Region(x*6,10, x*6+3,100))

    ra.prepare(None)
    ra.paint()
    ra.w.mainloop()