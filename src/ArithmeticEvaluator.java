import java.util.Stack;

public class ArithmeticEvaluator {
	/* operators without priority ranking */
	static final String operators = "-+/*";
	/* list of possible operands */
	static final String operands = "0123456789";
        
	/* prevent instantiation */
	private ArithmeticEvaluator(){}

	/**
	 * converts infix expression to an postfix
	 * 
	 * @param expr infix expression
	 * @return postfix version of input
	 */
	static String infixToPostfix(String expr) {
		char[] chars = expr.toCharArray();
		Stack<Character> stack = new Stack<Character>();
		StringBuilder out = new StringBuilder(expr.length());
	
		for (char c : chars) {
			if ( isOperator(c) ) {
				while ( !stack.isEmpty() && stack.peek() != '(' ) {
					if ( operatorGreaterOrEqual(stack.peek(), c) )
						out.append(stack.pop());
					else
						break;
				}
				
				stack.push(c);
			} else if ( c == '(' ) {
				stack.push(c);
			} else if ( c == ')' ) {
				while ( !stack.isEmpty() && stack.peek() != '(' )
					out.append(stack.pop());
	
				if ( !stack.isEmpty() )
					stack.pop();
			} else if ( isOperand(c) ){
					out.append(c);
			}
		}
			
		while (!stack.empty())
			out.append(stack.pop());
	
		return out.toString();
	}

	/**
	 * evaluates arithmetic postfix string and returns result
	 * 
	 * @param expr arithmetic postfix string
	 * @return arithmetic evaluation of input
	 */
	static double evaluatePostfix(String expr) {
		char[] chars = expr.toCharArray();
		Stack<Double> stack = new Stack<Double>();
	
		for (char c : chars) {
			if (ArithmeticEvaluator.isOperand(c)) {
				
				/* convert ASCII to double */
				stack.push((double) c - '0');
			} else if (ArithmeticEvaluator.isOperator(c)) {
				double op1 = stack.pop();
				double op2 = stack.pop();
				double result;
	
				switch (c) {
					case '*':
						result = op1 * op2;
						stack.push(result);
						break;
					case '/':
						result = op2 / op1;
						stack.push(result);
						break;
					case '+':
						result = op1 + op2;
						stack.push(result);
						break;
					case '-':
						result = op2 - op1;
						stack.push(result);
						break;
				}
			}
		}
		
		return stack.pop();
	}
	
	/**
	 * evaluates arithmetic infix string
	 * 
	 * @param expr arithmetic infix string
	 * @return arithmetic result
	 */
	static double evaluateInfix(String expr) {
		return ArithmeticEvaluator.evaluatePostfix(infixToPostfix(expr));
	}

	/**
	 * is char an valid oparand
	 * 
	 * @param val character to test
	 * @return true if param is a operand
	 */
	static boolean isOperand(char val) {
		return operands.indexOf(val) > 0;
	}
	
	/**
	 * test if given parameter is a valid operator
	 * 
	 * @param val character to test
	 * @return
	 */
	static boolean isOperator(char val) {
		return operators.indexOf(val) > 0;
	}
	
	/**
	 * get Priority of Operators
	 * 
	 * @param operator character to test
	 * @return priority of operator
	 */
	static int getPrecedence(char operator) {
		if ( operator == '-' || operator == '+' )
			return 1;
		else if ( operator == '*' || operator == '/' ) 
			return 2;
		else
			return 0;
	}

	/**
	 * Compare priority of two operators
	 * 
	 * @param op1 arithmetic operator
	 * @param op2 arithmetic operator
	 * @return true if first operator has higher priority
	 */
	static boolean operatorGreaterOrEqual(char op1, char op2) {
		return getPrecedence(op1) > getPrecedence(op2);
	}

}
