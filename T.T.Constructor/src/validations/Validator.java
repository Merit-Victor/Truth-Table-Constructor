package validations;

import Constructor.Symbols;

/**
 * @author Merit Victor
 *
 */
public class Validator {

	/**
	 * The string.
	 */
	private String expressoinToBeValidated;
	
	/**
	 * 
	 */
	private ParenthesisValidator parenthV;
	
	/**
	 * 
	 */
	private SymbolsUsedValidator symbolV;
	
	/**
	 * 
	 */
	private Symbols validSymbol;
	
	/**
	 * The constructor.
	 * @param expression
	 */
	public Validator(String expression) {
		this.expressoinToBeValidated = expression;
		this.parenthV = new ParenthesisValidator(expressoinToBeValidated);
		this.symbolV = new SymbolsUsedValidator(expressoinToBeValidated);
		this.validSymbol = new Symbols();
	}
	
	/**
	 * Validates all the expression.
	 * @throws RuntimeException is thrown from some called validator.
	 */
	public void validateExpression() throws RuntimeException {
		parenthV.validate();
		symbolV.validate();
		char firstChar = this.expressoinToBeValidated.charAt(0);
		char lastChar = this.expressoinToBeValidated.charAt(expressoinToBeValidated.length() - 1);
		String toBeChecked1 = Character.toString(firstChar);
		String toBeChecked2 = Character.toString(lastChar);
		if ((validSymbol.isOperation(toBeChecked1) 
				&& validSymbol.getOperationIndex(toBeChecked1) != 3)
				|| toBeChecked1 == ")") {
			throw new RuntimeException("Invalid start of Expression.");
		} else if(validSymbol.isOperation(toBeChecked2) 
				||toBeChecked2 == "("){
			throw new RuntimeException("Invalid end of Expression.");
		}
	}
}
