from demo.app_voronoi import VoronoiApp
from adk.fortune import DCEL
import random

if __name__ == "__main__":
    ra = VoronoiApp()
    ra.static = True
    
    p1 = (205,238)
    p2 = (156,157)
    p3 = (255,133)
    
    p0 = (182.48230816520572, 313.5077651402048)
    p3 = (150.37131608978206, 284.59611276529694)
    p2 = (213.54488831679248, 284.76130981212833)
    p1 = (181.24763498252958, 290.8665934576889)
    ra.points = [p0,p1,p2,p3]
    
#     p1 = (240, 240)
#     p2 = (200, 200)
#     p3 = (280, 200)
#     p4 = (240.24, 160.88)
# 
#     ra.points = [p1,p2,p3,p4]
#     
#     ra.points = [(x,y) for x in range(120,190,30) for y in range(120,190,30)]
#     for i in range(len(ra.points)):
#         pt = ra.points[i]
#         ra.points[i] = (pt[0] + random.random(), pt[1] + random.random())  
#         
#     # This detected I was associating circle events with sites rather than
#     # nodes. NICE ONE
#     r1 = (103, 87)
#     r2 = (277, 162)
#     r3 = (173, 248)
#     r4 = (135, 93)
#     
#     r1 = (100, 100)
#     r2 = (120, 100)
#     r3 = (140, 100)
#     r4 = (160, 100)
#     r5 = (160, 98)
#     ra.points = [r1,r2,r3,r4,r5]
     
    ra.prepare(None)
    ra.process(ra.points)
    
    for vp in ra.voronoi.points:
        print (vp.polygon)
    
    ra.paint()
    ra.w.mainloop()