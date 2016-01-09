package algs.model.performance.chapter2.calc;

/** Planned for extension. Aspect will determine later which Value to use... */
public class Value {

	/** Default constructor: To be overridden by aspect. */
	protected Value() {
		
	}

	/** Copy constructor: To be overridden by aspect. If null passed in, set to default zero value. */
	public Value (Value v) {
		
	}

	
	/** Constructor expects string. */
	public Value (String val) throws NumberFormatException {
		parseType (val);
	}	
	
	/** Understands how to parse the String value. */
	public void parseType(String val){ }

	// core operations.
	public Value add(Value c1) { return null; }
	public Value subtract(Value c1)  { return null; }
	public Value multiply(Value c1)  { return null; }
	public Value[] divide(Value c1)  { return null; }
}
