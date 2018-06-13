public class Foothill09 {
    public static void main(String[] args) {
        String[] rpnExpressions = {
                new String("3"),
                new String("23+"),
                new String("23-"),
                new String("23*"),
                new String("23/"),
                new String("2345+-*"),
                new String("2345/+*"),
                new String("234+-"),
        };
        for (int i = 0; i < rpnExpressions.length; i++) {
            try {
                RpnCalculator.eval(rpnExpressions[i]);
                System.out.println("Expression is " + rpnExpressions[i] + " and solution is " +
                        RpnCalculator.eval(rpnExpressions[i]));
            } catch (RuntimeException e) {
                System.out.println("Error is with " + rpnExpressions[i]);
            }
        }
    }
}

class RpnCalculator {
    public static final String additionOperation = "+";
    public static final String subtractionOperation = "-";
    public static final String multiplicationOperation = "*";
    public static final String divisionOperation = "/";

    public static int eval(String rpnExpression) {
        String[] bob = parse(rpnExpression);
        return eval(bob);
    }

    private static String[] parse(String rpnExpression) {
        return rpnExpression.split("\\s+");
    }

    private static int eval(String[] tokens) {
        IntegerStack integerstack = new IntegerStack(10);
        for (int i = 0; i < tokens.length; i++) {
            try {
                int tob = Integer.parseInt(tokens[i]);
                integerstack.push(tob);
            } catch (NumberFormatException e) {
                int op2 = integerstack.pop();
                int op1 = integerstack.pop();
                if (tokens[i].equals(additionOperation)){
                    op1 += op2;
                } else if (tokens[i].equals(subtractionOperation)){
                    op1 -= op2;
                }
                else if (tokens[i].equals(multiplicationOperation)){
                    op1 = multiply(op1, op2);
                }
                else if (tokens[i].equals(divisionOperation)){
                    op1 /= op2;
                }else {
                    throw new RuntimeException("Value in string was neither operand or operator");
                }
                integerstack.push(op1);
            }
            integerstack.peek();
        }
        return integerstack.pop();
    }

    private static int multiply(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        } else if (a == 1) {
            return b;
        } else if (a < 0 && b < 0) {
            return a + multiply(-a, 1 - b);
        } else if (a < 0 && b >= 0) {
            return a - multiply(-a, b - 1);
        } else if (a >= 0 && b < 0) {
            return a - multiply(-a, b - 1);
        }
        return a + multiply(a, b - 1);
    }

}

class IntegerStack {
    public static final int MIN_SIZE = 0;

    private int[] stack;
    private int topOfStack;

    public IntegerStack(int size) {
        if (size <= MIN_SIZE) {
            throw new RuntimeException("Size for stack should not be less than or equal to " + MIN_SIZE);
        }
        stack = new int[size];
        topOfStack = 0;
    }

    public void push(int item) {
        if (isFull()) {
            throw new RuntimeException("Stack is full when pushing " + item);
        }
        stack[topOfStack++] = item;
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack[--topOfStack];
    }

    public boolean isFull() {
        return (topOfStack == stack.length);
    }

    public boolean isEmpty() {
        return (topOfStack == 0);
    }
    public void peek(){
        for (int x: stack){
            System.out.println(x);
        }
    }
}


