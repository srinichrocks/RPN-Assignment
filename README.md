# RPN-Assignment
RPN calculator overview
(We've talked about RPN in the lecture, and you can re/view it in Week 9B - Recursion and a Binary Search Algorithm screen recording, roughly from 16:30 to 36:30.  If you understand it already you can skip this overview.)

RPN (Reverse Polish Notation) is one way of writing math expression, and is also known as postfix notation.  This is in contrast to infix notation, the way we are more familiar with, e.g. 2 + 3, where the operator (+) is in-between the operands (2 and 3).  In RPN, the same expression is written as 2 3 +, where the operator follows the two operands.  Obviously, both expressions evaluate to the same result, 5.

For a more complex example with more operators, consider 2 3 + 10 -.  In this case, we evaluate 2 3 + first, which gives us 5; we put 5 back in the original expression where 2 3 + is, so the expression is now 5 10 -, which would evaluate to -5.  In another example, consider 10 2 3 + -.  In this case, we also evaluate 2 3 + first, which again results in 5, and we replace 2 3 + in the original expression with 5, which gives us 10 5 -, which evaluates to 5.

The general rule for evaluating a RPN expression is: we scan the expression from left to right; when we encounter an operator (+, -, *, etc), we evaluate the operation using the 2 operands (numbers) preceding the operator, and replace the operator and the 2 operands with the result.  We continue this way until we reach the end of the expression.

As it turns out, it's much easier for computer to evaluate a RPN/postfix expression than an infix expression, because there's no need for parentheses.  For example, infix expression (2 + 3) * 5 can be written as 2 3 + 5 * in postfix.  In fact, an infix expression (say, those written in Java) is often converted to postfix before it's evaluated.  All we need is a stack.

For more information about RPN, see https://en.wikipedia.org/wiki/Reverse_Polish_notation (Links to an external site.)Links to an external site..

Program overview
Our RPN calculator program consists of 3 classes.  All 3 classes should be placed in a single file like in previous assignments, named Foothill09.java.

Foothill09

This class has the usual main() method, which is basically a series of tests.  It feeds a number of (valid and invalid) RPN expressions to RpnCalculator (detailed below), and prints out either the results if there's no error, or catches the exception and prints out the error message if something goes wrong.

RpnCalculator
This class is our RPN calculator, which is capable of integer math that supports 4 operations: addition, subtraction, multiplication, and division.  It has a single public method eval() that takes a String that's an RPN expression (e.g. "2 3 +"), parses it, evaluates it (with the help of IntegerStack, detailed below), and returns the result.  This class contains a number of private methods as well (detailed below).

IntegerStack
This class is a stack of integers.  It supports the typical stack operations, such as push(), pop().  It's very similar to the CharStack class we discussed in lecture.

Program Specifications
This gives details about each of the 3 class you have to implement.  I'd suggest you start with IntegerStack class (which should be very straight-forward), then RpnCalculator with it's single eval() method (which for now can just return 0), then Foothill09 with a full suite of test cases.  Finally, return to RpnCalculator, and implement each method according to spec.

Foothill09 Class Specifications
public static void main(String[] args)
This is the usual entry point to the program.  It should have an array of String's that are RPN expressions, some of which are valid and some invalid.  Valid expressions should contain at least the following:
single number (e.g. "3")
an expression for each of the 4 supported operations (e.g. "2 3 +", "2 3 -")
3 or more increasingly complex expressions that consist of multiple operations (e.g. "2 3 4 + *")
Invalid expression should contain at least the followings:
Empty string ("")
Expression with insufficient operands (zero or one integer followed by an operator, e.g. "1 +")
Expression with insufficient operator (two integers followed by no operator, e.g. "1 1")
Expression with invalid operator (e.g. "1 1 fly")
This method iterates over the String array of RPN expressions, and passes each expression to RpnCalculator.eval().  It should surround the call to RpnCalculator.eval() with "try { ... } catch (RuntimeException e) { ... }" block.  In the try block, it prints out the expression along with result returned by RpnCalculator.eval(); in the catch block, it prints out the expression along with the exception's message.  It should not crash, and should evaluate all expressions.  It should be the only method in your entire program that prints anything to screen; you can have prints for debugging in other methods, but you should remove/comment them out before submission.
RpnCalculator Class Specifications
This class should have 4 public String symbolic constants for the supported operators:

"+", addition
"-", subtraction
"*", multiplication
"/", integer division
Remember, if you want to check if a String equals to any of these operators, use String.equals() instead of ==.

This class should have the following methods:

public static int eval(String rpnExpression)
This is the only public method of this class. Its single parameter is a String of a single RPN expression to be evaluated, such as "2 3 +". It'll use the two private methods (detailed below) to help evaluate the expression. First, it calls parse() to turn the String into individual tokens in an array so that they can be more easily processed. Then it passes the token array to the private version of eval() to calculate the result.  Finally, it returns the result.  This method should be no more than 2 or 3 lines.
private static String[] parse(String rpnExpression)
This method parses (roughly means to divide text into components, or tokens) the RPN expression and returns an array of String tokens. For example, if the RPN expression is "2 3 +", this method returns an array of String { "2", "3", "+" }. Parsing is typically a complex operation, but fortunately our RPN expressions is simple enough that it takes a single line to parse, though it involves regular expression, which we haven't covered in this class.  So here's the method in its entirety (just copy and paste it to your code):
private static String[] parse(String rpnExpression) {
    return rpnExpression.split("\\s+");
}
private static int eval(String[] tokens)
This is an overloaded version of eval(), but unlike the public version, this private version takes the parsed array of tokens as its parameter.  This method allocates a stack (you should figure out the size; it's ok to allocate more than necessary, but don't use a arbitrarily large number). It then iterates over the array of tokens, and takes the appropriate action depending on whether the token is an integer operand (pushes onto the stack), or an operator (pops operands off the stack and perform the calculation, then pushes the result back on the stack).  You should use Integer.parseInt() to figure out if a token is an integer or not (remember, Integer.parseInt() throws NumberFormatException if a string is not a number).  When you finish with all the tokens, the result should be a single number left on the stack (assuming the RPN expression is valid); if not, something's wrong (either in the RPN expression or your code).
Of the 4 supported operations, you can use Java's built-in operators for 3 of them: addition, subtraction, and division.  For multiplication, you must not use the Java's built-in operator *.  Instead, you'll write your own multiply() method (detailed below), and use it here to perform multiplication.
The above description should be sufficient to get you started to write code to evaluate the RPN expression.  But if you are really stuck, the wikipedia article linked above has pseudo code that may be useful as well.  Also, be very careful with operators whose operand order matters (e.g. minus -), because remember, stack reverses the element order.
This method should throws a RuntimeException with a descriptive message if it detects any error (unrecognized operator, too many/insufficient operands, etc).  This method should not catch any error thrown by IntegerStack, but allow those exceptions to propagate up the call stack to Foothill09.main() where they are caught.
private static int multiply(int a, int b)
This function multiplies a and b.  For example, multiply(5, 2) calculates 5 * 2, which equals 10.  You must not use Java's built-in multiplication operator, * (the asterisk), to do this. Instead, build this with the recursion technique using only addition and subtraction (and comparison).  This method must handle the case where either a or b or both are negative.  Hint: it should be very similar to the power() method explained partially in the lecture and fully in the readings.
IntegerStack Class Specifications
This class implements a stack that stores integers.  It has two private instance fields: stack, which is an array of int, and topOfStack, which tracks where the top of the stack is in the array.  It should look very similar to the CharStack class we discussed in class.  In fact, here's CharStack.javaPreview the document, and you should modify a few things in it to make it into IntegerStack.

public IntegerStack(int size)
This is the only constructor of the class.  It allocate the stack array that can store size number of int, and initialize topOfStack appropriately.  It throws a RuntimeException with a descriptive message if size is less than or equal to 0.
public void push(int item)
This method pushes an integer onto the stack. It throws a RuntimeException with a descriptive message if the stack is full.
public int pop()
This method pops the integer at the top of the stack and returns it. It throws a RuntimeException with a descriptive message if the stack is empty.
public boolean isEmpty()
This method returns true if stack is empty, false otherwise.
public boolean isFull()
This method returns true if stack is full, false otherwise.
Sample output
Here are some of my test cases, but they are insufficient.  You can start with them, but add more of your own based on the specifications for Foothill09.main() above.  Your error messages do not have to be same as mine, as long as they indicate some sort of error.

"" fails to be evaluated: IntegerStack is empty
"1 1" fails to be evaluated: Not enough operator
"1 1 + +" fails to be evaluated: IntegerStack is empty
"1 1 fly" fails to be evaluated: Unknown operator fly
"random junk" fails to be evaluated: IntegerStack is empty
(1) = 1
(1 1 +) = 2
(15 5 +) = 20
(15 -5 *) = -75
(1 1 1 + -) = -1
(15 7 1 1 + - / 3 * 2 1 1 + + -) = 5
