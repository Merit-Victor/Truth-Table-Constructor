import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.InputMismatchException;
import java.util.Scanner;

import expressionEvaluator.ExpressionEvaluatorImp;

/**
 * @author Merit Victor
 * The User Interface.
 */
public class TTConstructorUI {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		ExpressionEvaluatorImp evaluator;
		System.out.println("Please insert number of required process:\n"
				+ "1)Evaluate single expression\n"
				+ "2)Test Equivalence of two expressions.");
		long currentTime1 = 0;
		long currentTime2 = 0;
		try {
			int numberOfProcess = scan.nextInt();
			if(numberOfProcess == 1) {
				System.out.println("Please enter the expression.");
				String expr = readFullLine(scan);
				evaluator = new ExpressionEvaluatorImp(expr);
				currentTime1 = System.currentTimeMillis();
				evaluator.evaluateExpression();
				evaluator.showTruthTable();
				currentTime2 = System.currentTimeMillis();
				System.out.println("Save? Y:N");
				String answer = scan.next();
				if(answer.trim().equalsIgnoreCase("Y")) {
					System.out.println("Please enter file's name.");
					String name = scan.next();
					saveToFile(evaluator.getTruthTable(), name);
					System.out.println("File saved.");
				}
			} else if(numberOfProcess == 2) {
				System.out.println("Please enter first expression.");
				String expr1 = readFullLine(scan);
				System.out.println("Please enter second expression.");
				String expr2 = readFullLine(scan);
				evaluator = new ExpressionEvaluatorImp();
				currentTime1 = System.currentTimeMillis();
				if(evaluator.testEquivalence(expr1, expr2)) {
					System.out.println("Equivalent.");
				} else {
					System.out.println("NOT Equivalent");
				}
				currentTime2 = System.currentTimeMillis();
			} else {
				throw new InputMismatchException();
			}
			
		} catch(InputMismatchException e) {
			System.err.println("Invalid input.");
			main(args);
		}
		
		long elapsedTime = (currentTime2 - currentTime1);
		try {
		    Files.write(Paths.get("TimeAnalys.txt"), (String.valueOf(elapsedTime) + "\r\n").getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    System.err.println(e.getMessage());
		}
		System.out.println("Time taken in the process: " + elapsedTime + " ms.");
		scan.close();
		System.exit(0);
	}

	/**
	 * This method to enable scanning 
	 * the whole sentence.
	 * @param scan scanner
	 * @return line
	 */
	private static String readFullLine(final Scanner scan) {
		String line = "";
		line += scan.next();
		line += scan.nextLine();
		return line;
	}
	
	/**
	 * Saves the truth table to a file.
	 * @param truthTable
	 * @param file name.
	 */
	private static void saveToFile(boolean[][] truthTable, String name) {
		try {
			FileWriter fileW = new FileWriter(name);
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < truthTable.length; i++)
			{
			   for(int j = 0; j < truthTable[0].length; j++)
			   {
			      builder.append((truthTable[i][j]? 1 : 0 )+"");//append to the output string
			      if(j < truthTable[0].length - 1)//if this is not the last row element
			         builder.append(",");//then add comma (for the ability to split the string again)
			   }
			   builder.append("\n");//append new line at the end of the row
			}
			BufferedWriter writer = new BufferedWriter(fileW);
			writer.write(builder.toString());//save the string representation of the board
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
