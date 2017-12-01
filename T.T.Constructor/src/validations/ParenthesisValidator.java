package validations;

import java.util.EmptyStackException;
import java.util.Stack;


/**
 * @author Merit Victor
 * The class that is responsible to check that 
 * every opened parenthesis is closed.
 */
public class ParenthesisValidator extends ExpressionValidator{

	/**
	 * The given expression.
	 */
	private String expr;
	
	/**
	 * The constructor.
	 * @param expression
	 */
	public ParenthesisValidator(String expression) {
		super(expression);
		expr = super.getExpression();
	}
	
	@Override
	public void validate() throws RuntimeException {
		Stack<String> parenthesisStack = new Stack<>();
		for(int i = 0; i < expr.length(); i++) {
			if(expr.charAt(i) == '(') {
				parenthesisStack.push(Character.toString(expr.charAt(i)));
			} else if(expr.charAt(i) == ')') {
				try {
					parenthesisStack.pop();
				} catch (EmptyStackException e) {
					throw new RuntimeException("Wrong number of parenthesis");
				}
			}
		}
		if(!parenthesisStack.isEmpty()) {
			throw new RuntimeException("Wrong number of parenthesis");
		}
	}

}
