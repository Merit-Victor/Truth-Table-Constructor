package validations;

import Constructor.Symbols;

/**
 * @author Merit Victor The class that is responsible to check that the user
 *         didn't use invalid symbols.
 */
public class SymbolsUsedValidator extends ExpressionValidator {

	/**
	 * The given expression.
	 */
	private String expr;

	/**
	 * The symbols class.
	 */
	private Symbols validSymbols;

	/**
	 * The constructor.
	 * 
	 * @param expression
	 */
	public SymbolsUsedValidator(String expression) {
		super(expression);
		expr = super.getExpression();
		validSymbols = new Symbols();
	}

	@Override
	public void validate() throws RuntimeException {
		int numberOfSymbols = 0;
		for (int i = 0; i < expr.length(); i++) {
			if(expr.charAt(i) == ' ') {
				i++;
			}
			String toBeChecked = Character.toString(expr.charAt(i));
			if (!validSymbols.isConstant(toBeChecked) 
					&& !validSymbols.isOperation(toBeChecked)
					&& !validSymbols.isLetter(toBeChecked) 
					&& !toBeChecked.equals("(") && !toBeChecked.equals(")")) {
				throw new RuntimeException("Invalid symbol: " + toBeChecked);
			} else if (validSymbols.isConstant(toBeChecked)) {
				// Checking that there isn't two TRUEs or FALSEs together.

				toBeChecked = Character.toString(expr.charAt(i + 1));
				if (validSymbols.isConstant(toBeChecked)) {
					throw new RuntimeException("Invalid expression near " + expr.substring(i, i + 2));
				}

			} else if (validSymbols.isLetter(toBeChecked)) {
				numberOfSymbols++;
			}
		}
		if (numberOfSymbols == 0) {
			throw new RuntimeException("Expression must have at least one variable.");
		}
	}

}
