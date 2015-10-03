package algs.chapter3.table3;

public class Main {

    /**
     * Note: if you create an Excel spreadsheet with these exact same
     * computations, the proper answer of ZERO is computed.
     */
    public static void main(String[] args) {
	float af = 1.0f/3.0f;
	float bf = 5/3.0f;
	float cf = 33.0f;
	float df = 165.0f;
	float ef = 19;
	float ff = 95;
	
	float fval = (cf-af)*(ff-bf)-(df-bf)*(ef-af);


	double ad = 1.0/3.0;
	double bd = 5/3.0;
	double cd = 33;
	double dd = 165;
	double ed = 19;
	double fd = 95;
	
	double dval = (cd-ad)*(fd-bd)-(dd-bd)*(ed-ad);
	
	System.out.println("\t\t\tfloat\t\tdouble");
	System.out.println("a=1/3\t\t\t" + af + "\t" + ad);
	System.out.println("b=5/3\t\t\t" + bf + "\t" + bd);
	System.out.println("c=33\t\t\t" + cf + "\t\t" + cd);
	System.out.println("d=165\t\t\t" + df + "\t\t" + dd);
	System.out.println("e=19\t\t\t" + ef + "\t\t" + ed);
	System.out.println("f=95\t\t\t" + ff + "\t\t" + fd);
	System.out.println("(c-a)*(f-b)-(d-b)*(e-a)\t" + fval + "\t" + dval);
	System.out.println("\t\t\t" + "(0x" + Integer.toHexString(Float.floatToIntBits(fval)) + 
			   ")\t" +"(0x" + Long.toHexString(Double.doubleToLongBits(dval)) + ")"); 
    }
}
