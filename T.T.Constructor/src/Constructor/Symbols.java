package Constructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Merit Victor
 * The class that contains all 
 * allowed letters in the expression.
 */
public class Symbols {

	/**
	 * The operations map. 
	 */
	private static Map<String, Integer> operations = new HashMap<String, Integer>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("^", 1);
		put("v", 2);
		put("~", 3);
	}};
	
	/**
	 * The constants map.
	 */
	private static Map<String, Integer> constants = new HashMap<String, Integer>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("1",1);
		put("0", 0);
	}};
	
	/**
	 * Checks if the given string is in the operations map.
	 * @param opr string.
	 * @return boolean.
	 */
	public boolean isOperation(String opr) {
		return Symbols.operations.containsKey(opr);
	}
	
	/**
	 * Checks if the given string is in the constant map.
	 * @param cons string.
	 * @return boolean.
	 */
	public boolean isConstant(String cons) {
		return Symbols.constants.containsKey(cons);
	}
	
	/**
	 * @param opr
	 * @return the index of index value of the operation.
 	 */
	public int getOperationIndex(String opr) {
		return Symbols.operations.get(opr);
	}
	
	/**
	 * @param cons
	 * @return the index value of the constant.
	 */
	public int getConstantIndex(String cons) {
		return Symbols.constants.get(cons);
	}
	
	/**
	 * Checks if the given string is a small letter.
	 * @param letter
	 * @return boolean.
	 */
	public boolean isLetter(String letter) {
		char theLetter = letter.charAt(0);
		return ((theLetter >= 'a' && theLetter <= 'z') && theLetter != 'v');	
	}
	
}
