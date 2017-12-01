package truthTableBuilder;

import java.util.HashSet;
import java.util.Set;

import Constructor.Symbols;

/**
 * @author Merit Victor
 * The class that is responsible 
 * to build the truth table.
 */
public class TTBuilder {

	/**
	 * The truth table to be built.  
	 */
	private boolean[][] truthTable;

	/**
	 * The given expression. 
	 */
	private String expres;
	
	/**
	 * 
	 */
	private Set<String> letters;
	
	/**
	 * 
	 */
	private Symbols validSymbols;
	
	/**
	 * 
	 */
	private TTConstructor constructor;
	
	
	/**
	 * The constructor.
	 * @param expression
	 */
	public TTBuilder(String expression) {
		this.expres = expression;
		this.validSymbols = new Symbols();
		this.letters = new HashSet<>();
	}
	
	/**
	 * 
	 */
	public void build() {
		getVariables();
		this.constructor = new TTConstructor(letters, expres);
		this.truthTable = constructor.constructTT();
	}
	
	/**
	 * 
	 */
	private void getVariables() {
		String toBeChecked;
		for(int i = 0; i < expres.length(); i++) {
			toBeChecked = Character.toString(expres.charAt(i));
			if(validSymbols.isLetter(toBeChecked)) {
				letters.add(toBeChecked);
			}
		}
	}
	
	/**
	 * @return
	 */
	public boolean[][] getBooleanTruthTable() {
		return this.truthTable;
	}
	
	/**
	 * @return
	 */
	public String[] getSortedVariables() {
		return this.constructor.getSortedVariables();
	}
	
}
