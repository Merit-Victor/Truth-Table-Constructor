package expressionEvaluator;

/**
 * @author Merit Victor
 * The main interface to deal with.
 */
public interface IExpressionEvaluator {

	/**
	 * Evaluates the given expression if correct.
	 */
	public void evaluateExpression();
	
	/**
	 * Shows the truth table of the given expression above.
	 */
	public void showTruthTable();
	
	/**
	 * Test the equivalence of the two given expressions.
	 * @param expression1
	 * @param expression2
	 * @return result
	 */
	public boolean testEquivalence(String expression1, String expression2);
}
