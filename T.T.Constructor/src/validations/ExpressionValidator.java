package validations;

/**
 * @author Merit Victor
 * The class that is responsible to 
 * validate everything in the given expression.
 */
public abstract class ExpressionValidator {
	
	/**
	 * The string.
	 */
	private String expressoinToBeValidated;
	
	/**
	 * The constructor.
	 * @param expression
	 */
	public ExpressionValidator(String expression) {
		this.expressoinToBeValidated = expression;
	}
	
	/**
	 * An abstract method to be implemented by any class
	 * (validator) that inherits from this class. 
	 *
	 * @throws RuntimeException if the expression is invalid.
	 */
	public abstract void validate() throws RuntimeException;
	
	/**
	 * Just a getter method.
	 * @return
	 */
	public String getExpression() {
		return this.expressoinToBeValidated;
	}
}
