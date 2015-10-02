from demo.app_kd_range import KDTreeApp
import math

if __name__ == "__main__":
    kdapp = KDTreeApp()
    kdapp.static = True
    
    r = 100
    cx = 200
    cy = 200
    total = 64
    for i in range(total):
        x = r*math.cos((math.pi*2)*i/total)
        y = r*math.sin((math.pi*2)*i/total)
        p = (cx + x, cy + y)
        kdapp.tree.add(p, i)
        
    kdapp.prepare(None) 
    kdapp.paint()
    kdapp.w.mainloop()