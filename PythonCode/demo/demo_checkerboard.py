from demo.app_R_range import RTreeApp
from adk.region import Region

if __name__ == "__main__":
    ra = RTreeApp()
    ra.ready = True
    
    for x in range(1,8):
        for y in range(1,8):
            ra.tree.add(Region(x*64,y*64, x*64+32,y*64+32))

    ra.prepare(None)
    ra.paint()
    ra.w.mainloop()