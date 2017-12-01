package expressionEvaluator;

import truthTableBuilder.TTBuilder;
import validations.Validator;

/**
 * @author Merit Victor
 * The class that implements the interface.
 */
public class ExpressionEvaluatorImp implements IExpressionEvaluator {

	/**
	 * The given expression.
	 */
	private String expression;

	/**
	 * The validator.
	 */
	private Validator validator;
	
	/**
	 * The truth table builder.
	 */
	private TTBuilder builder;
	
	/**
	 * The constructor.
	 * @param expr string given.
	 */
	public ExpressionEvaluatorImp(String expr) {
		this.expression = expr.toLowerCase();
		this.validator = new Validator(expression);
		this.builder = new TTBuilder(expression);
	}
	
	/**
	 * Empty constructor if the test equivalence is required. 
	 */
	public ExpressionEvaluatorImp() {
	
	}
	
	@Override
	public void evaluateExpression() {
		try {
			validator.validateExpression();
			builder.build();
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
		
	}

	@Override
	public void showTruthTable() {
		boolean[][] truthTable = getTruthTable();
		String[] variables = builder.getSortedVariables();
		for(int i = 0; i < variables.length; i++) {
			System.out.print(variables[i] + "  ");
		}
		System.out.println("Result");
		boolean testType = truthTable[0][truthTable[0].length - 1];
		boolean result = true;
		for(int i = 0; i < truthTable.length; i++) {
			for(int j = 0; j < truthTable[0].length; j++) {
				System.out.print((truthTable[i][j]? "1" : "0") + "  ");
				if(j == truthTable[0].length - 1) {
					if(truthTable[i][j] != testType) {
						result = false;
					}
				}
			}
			System.out.println();
		}
		if(result) {
			System.out.println(testType?  "Expression is a tautology." : "Expression is a contradiction");
		}
		
	}

	@Override
	public boolean testEquivalence(String expression1, String expression2) {
		boolean[][] tt1;
		boolean[][] tt2;
		ExpressionEvaluatorImp ee1 = new ExpressionEvaluatorImp(expression1);
		ee1.evaluateExpression();
		ee1.showTruthTable();
		System.out.println();
		tt1 = ee1.getTruthTable();
		ExpressionEvaluatorImp ee2 = new ExpressionEvaluatorImp(expression1);
		ee2.evaluateExpression();
		ee2.showTruthTable();
		tt2 = ee1.getTruthTable();
		if(tt2.length != tt1.length) {
			return false;
		}
		for(int i = 0; i < tt2.length; i++) {
			if(tt1[i][tt1[0].length - 1] != tt2[i][tt2[0].length - 1]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return
	 */
	public boolean[][] getTruthTable() {
		return builder.getBooleanTruthTable();
	}
}
