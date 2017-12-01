package truthTableBuilder;

import java.util.Arrays;
import java.util.Set;
import java.util.Stack;

import Constructor.Symbols;

/**
 * @author Merit Victor
 *
 */
public class TTConstructor {

	/**
	 * 
	 */
	private Set<String> mVariables;

	/**
	 * 
	 */
	private String expression;
	
	/**
	 * 
	 */
	private boolean[][] truthTable;
	
	/**
	 * 
	 */
	private String[] orderedVariables;
	
	/**
	 * 
	 */
	private Symbols validSymbol;
	
	/**
	 * The constructor.
	 * @param variables
	 */
	public TTConstructor(Set<String> variables, String exp) {
		this.mVariables = variables;
		this.expression = exp;
		this.validSymbol = new Symbols();
		int secondDimention = 1<<mVariables.size();
		truthTable = new boolean[secondDimention][mVariables.size() + 1];	
		orderedVariables = mVariables.toArray(new String[mVariables.size()]);
		Arrays.sort(orderedVariables);	
	}
	
	/**
	 * @return
	 */
	public boolean[][] constructTT() {
		fillVariablesColumns();
		evaluateResultColumn();
		return this.truthTable;
	}
	
	/**
	 * 
	 */
	private void fillVariablesColumns() {
		for(int i = 0; i < truthTable.length; i++) {
	        for( int j = 0; j < truthTable[0].length - 1; j++) {
	            truthTable[i][truthTable[0].length - j - 2] = ((i >> j) & 1) == 1;
	        }
	    }
	}
	
	/**
	 * 
	 */
	private void evaluateResultColumn() {
		String exp = expression;
		boolean r = false;
		for(int i = 0; i < truthTable.length; i++) {
			exp = expression;
			for(int j = 0; j < orderedVariables.length; j++) {
				exp = exp.replace(orderedVariables[j].toLowerCase(), truthTable[i][j]? "1" : "0");
			}
			r = evaluateRow(exp);
			truthTable[i][truthTable[0].length - 1] = r;
		}
	}
	
	/**
	 * @param row
	 */
	private boolean evaluateRow(String row) {
		row = row.replace(" ", "");
		char[] expressionChar = row.toCharArray();
		boolean result  = false;
		String[] r = new String[3];
		for(int i = 0; i < expressionChar.length; i++) {
			if(expressionChar[i] == '(') {
				r = evaluateParenthsis(i, expressionChar, result, row);
				boolean dumbResult = (r[1].equals("1")? true : false);
				String sub = r[2].substring(i, Integer.valueOf(r[0]) + 1);
				row = r[2].replace(sub, dumbResult? "1" : "0");
				expressionChar = row.toCharArray();
				result = dumbResult;
			} else if(validSymbol.isOperation(Character.toString(expressionChar[i])) 
					&& validSymbol.getOperationIndex(Character.toString(expressionChar[i])) == 3) {
					i++;
					//constant
					if(validSymbol.isOperation(Character.toString(expressionChar[i]))) {
						throw new RuntimeException("Can't write two operations.");
					}
					if(expressionChar[i] == '(') {
						r = evaluateParenthsis(i, expressionChar, result, row);
						boolean dumbResult = (r[1].equals("1")? true : false);
						String sub = r[2].substring(i, Integer.valueOf(r[0]) + 1);
						row = r[2].replace(sub, dumbResult? "1" : "0");
						expressionChar = row.toCharArray();
						result = !(Character.toString(expressionChar[i]).equals("1")? true : false);
					} else {
						result =  !(Character.toString(expressionChar[i]).equals("1")? true : false);	
					}
					//AND
			} else if(validSymbol.isOperation(Character.toString(expressionChar[i])) 
						&& validSymbol.getOperationIndex(Character.toString(expressionChar[i])) == 1) {
					i++;
					//constant
					if(validSymbol.isOperation(Character.toString(expressionChar[i]))) {
						throw new RuntimeException("Can't write two operations.");
					}
					if(expressionChar[i] == '(') {
						r = evaluateParenthsis(i, expressionChar, result, row);
						boolean dumbResult = (r[1].equals("1")? true : false);
						String sub = r[2].substring(i, Integer.valueOf(r[0]) + 1);
						row = r[2].replace(sub, dumbResult? "1" : "0");
						expressionChar = row.toCharArray();
						result = result & (Character.toString(expressionChar[i]).equals("1")? true : false);
					} else {
						result = result & (Character.toString(expressionChar[i]).equals("1")? true : false);
					}//OR
			} else if (validSymbol.isOperation(Character.toString(expressionChar[i])) 
						&& validSymbol.getOperationIndex(Character.toString(expressionChar[i])) == 2) {
					i++;
					//constant
					if(validSymbol.isOperation(Character.toString(expressionChar[i]))) {
						throw new RuntimeException("Can't write two operations.");
					}
					if(expressionChar[i] == '(') {
						
						r = evaluateParenthsis(i, expressionChar, result, row);
						boolean dumbResult = (r[1].equals("1")? true : false);
						String sub = r[2].substring(i, Integer.valueOf(r[0]) + 1);
						row = r[2].replace(sub, dumbResult? "1" : "0");
						expressionChar = row.toCharArray();
						result = result | (Character.toString(expressionChar[i]).equals("1")? true : false);
					} else {
						result = result | (Character.toString(expressionChar[i]).equals("1")? true : false);
					}
			} else {
					result = (Character.toString(expressionChar[i]).equals("1")? true : false);
			}
		}
		return result;
	}
	
	
	/**
	 * @param index
	 * @param exp
	 * @param result
	 * @return
	 */
	private String[] evaluateParenthsis(int index, char[] exp, boolean result, String row) {
		Stack<String> operations = new Stack<>();
		Stack<String> dumb = new Stack<>();
		int counter = index + 1;
		String[] r = new String[3];
		while(exp[counter] != ')') {
			if(exp[counter] == '(') {
				r = evaluateParenthsis(counter, exp, result, row);
				boolean dumbResult = (r[1].equals("1")? true : false);
				String sub = r[2].substring(counter, Integer.valueOf(r[0]) + 1);
				row = r[2].replace(sub, dumbResult? "1" : "0");
				exp = row.toCharArray();
				dumb.push(Character.toString(exp[counter]));
			} else {
				dumb.push(Character.toString(exp[counter]));
			}
			counter++;
		}
		while (!dumb.isEmpty()) {
			operations.push(dumb.pop());
		}
		index = counter;
		String c = operations.peek();
		if(validSymbol.isOperation(c) && validSymbol.getOperationIndex(c) != 3) {
				throw new RuntimeException("Operation requires two operands");	
		}
		result = mainEvalutaion(operations, result);
		r[0] = String.valueOf(index);
		r[1] = (result? "1":"0");
		r[2] = row;
		return r;
	}
	
	/**
	 * @return
	 */
	public String[] getSortedVariables() {
		return orderedVariables;
	}
	
	/**
	 * @param operations
	 * @param result
	 * @return
	 */
	public boolean mainEvalutaion(Stack<String> operations, boolean result) {
		String c = operations.peek();
		while(!operations.isEmpty()) {
			c = operations.pop();
			//NOT
			if(validSymbol.isOperation(c) 
				&& validSymbol.getOperationIndex(c) == 3) {
				c = operations.pop();
				//constant
				if(validSymbol.isOperation(c)) {
					throw new RuntimeException("Can't write two operations.");
				}
				result =  !(c.equals("1")? true : false);
				//AND
			} else if(validSymbol.isOperation(c) 
					&& validSymbol.getOperationIndex(c) == 1) {
				c = operations.pop();
				//constant
				if(validSymbol.isOperation(c)) {
					throw new RuntimeException("Can't write two operations.");
				}
				result = result & (c.equals("1")? true : false);
				//OR
			} else if (validSymbol.isOperation(c) 
					&& validSymbol.getOperationIndex(c) == 2) {
				c = operations.pop();
				//constant
				if(validSymbol.isOperation(c)) {
					throw new RuntimeException("Can't write two operations.");
				}
				result = result | (c.equals("1")? true : false);
			} else {
				result = (c.equals("1")? true : false);
			}
		}
		return result;
	}
}
