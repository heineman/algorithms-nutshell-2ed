package algs.example.chapter10;

import algs.model.IPoint;
import algs.model.data.points.UniformGenerator;
import algs.model.twod.TwoDRectangle;

public class RandomQuestion {
	public static void main(String[] args) {
		int T = 1;
		
		// given n random rectangles in the unit square, how many of them intersect a random point?
		UniformGenerator generator = new UniformGenerator();
		for (int n = 4; n <= 1048576; n*= 2) {
			IPoint[] ip = generator.generate(n*2);
			
			// target point to query against all rectangles...
			double numIntersect = 0;
			IPoint tp[] = generator.generate(1);
			
			for (int i = 0; i < n; i++) {
				double left = ip[i*2].getX();
				double bottom = ip[i*2].getY();
				double right = ip[i*2+1].getX();
				double top = ip[i*2+1].getY();
				
				if (left > right) {
					double t = left;
					left = right;
					right = t;
				}
				
				if (top < bottom) {
					double t = top;
					top = bottom;
					bottom = t;
				}
				
				// a rectangle
				TwoDRectangle rect = new TwoDRectangle(left, bottom, right, top);
				
				// generate T random points to see if contained in rectangle
				if (rect.intersects(tp[0])) {
					numIntersect++;
				}
			}
			
			// when we get here, we have results of T random points for each of the n random rectangles
			System.out.println (n + "," + T + "," + numIntersect + "," + (100*(numIntersect/n)) + "%");
		}
	}
}
