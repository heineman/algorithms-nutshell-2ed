from demo.app_R_range import RTreeApp
from adk.region import Region

if __name__ == "__main__":
    ra = RTreeApp()
    ra.ready = True
    
    r1 = Region(46,290 , 474,390)
    r2 = Region(142,218 , 344,352)
    r3 = Region(106,262 , 302,342)
    r4 = Region(290,406 , 324,426)
    r5 = Region(266,232 , 452,330)
    r6 = Region(162,14 , 404,178)
    r7 = Region(86,18 , 392,298)
    r8 = Region(90,276 , 320,500)
    
    ra.tree.add(r1)
    ra.tree.add(r2)
    ra.tree.add(r3)
    ra.tree.add(r4)
    ra.tree.add(r5)
    ra.tree.add(r6)
    ra.tree.add(r7)
    ra.tree.add(r8)
    ra.prepare(None)
    ra.paint()
    ra.w.mainloop()