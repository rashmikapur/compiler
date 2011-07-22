import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AST {
	// Create an interface Node to reuse
	public interface Node {
		<T> T accept(Visitor<T> v);
	}

	// Create an interface Visitor
	public interface Visitor<T> {
		T visit(Loop loop);

		T visit(Branch branch);

		T visit(Block block);

		T visit(Assign assign);

		T visit(Id id);

		T visit(Operator op);

		T visit(Plus op);

		T visit(Minus op);

		T visit(Times op);

		T visit(Divide op);

		T visit(Number num);

		T visit(Type type);

		T visit(HIGH high);

		T visit(LOW low);

		T visit(setUp setUp);

		T visit(pinMode pinMode);

		T visit(digitalWrite digitalWrite);

		T visit(analogWrite analogWrite);

		T visit(digitalRead digitalRead);

		T visit(analogRead analogRead);

		T visit(Program program);

		T visit(forLoop forLoop);

	}

	public static class Program implements Node {
		public Node body;

		Program(Sequence sequence) {
			body = sequence;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public class Sequence implements Node {
		ArrayList<Node> children = new ArrayList<Node>();

		@Override
		public <T> T accept(Visitor<T> v) {
			for (Node child : children) {
				child.accept(v);
			}
			return null;
		}

		public void addNode(Node n) {
			children.add(n);
		}
	}

	// Create interface Statement
	public interface Statement extends Node {
	}

	// Create interface Expression for condition cases
	public interface Expression extends Node {
	}

	// Create interface ID for variable names
	public static class Id implements Expression {
		String id;

		public Id(String id) {
			this.id = id;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Id id(String id) {
		return new Id(id);
	}

	// Create interface Type for variable types
	public static class Type implements Expression {
		String type;

		public Type(String type) {
			this.type = type;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Type type(String type) {
		return new Type(type);
	}

	// Create interface Assign for "is"
	public static class Assign implements Statement {
		Id variable;
		Expression value;

		public Assign(Id variable, Expression value) {
			this.variable = variable;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Assign assign(Id var, Expression val) {
		return new Assign(var, val);
	}

	// Create Block for group of statements
	public static class Block implements Statement {
		Statement[] statements;

		public Block(Statement... statements) {
			this.statements = statements;
		}
		

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Block block(Statement... statements) {
		return new Block(statements);
	}

	// Create if/else Branch
	public static class Branch implements Statement {
		Expression predicate;
		Statement ifBranch;
		Statement elseBranch;

		// If Branch
		public Branch(Expression p, Statement a) {
			predicate = p;
			ifBranch = a;

		}

		// If - Else Branch
		public Branch(Expression p, Statement a, Statement b) {
			predicate = p;
			ifBranch = a;
			elseBranch = b;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	// If only
	public static Branch branch(Expression predicate, Statement ifBranch) {
		return new Branch(predicate, ifBranch);
	}

	// If - Else
	public static Branch branch(Expression predicate, Statement ifBranch,
			Statement elseBranch) {
		return new Branch(predicate, ifBranch, elseBranch);
	}

	// Create Loop
	public static class Loop implements Statement {
		Expression predicate;
		Statement body;

		public Loop(Expression p, Statement body) {
			predicate = p;
			this.body = body;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Loop loop(Expression predicate, Statement body) {
		return new Loop(predicate, body);
	}
	
	// Create For Loop
	public static class forLoop implements Statement {
		Expression id;
		Expression initValue;
		Expression maxValue;
		Sequence[] body;

		public forLoop(Expression id, Expression init, Expression max, Sequence[] body) {
			this.id = id;
			initValue = init;
			maxValue = max;
			this.body = body;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static forLoop forloop(Expression id, Expression initValue, Expression maxValue, Sequence[] body) {
		return new forLoop(id, initValue, maxValue, body);
	}

	// Create setUp function
	public static class setUp implements Statement {
		Statement body;

		public setUp(Statement body) {
			this.body = body;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static setUp setup(Statement body) {
		return new setUp(body);
	}

	// Create pinMode
	public static class pinMode implements Statement {
		Expression predicate;
		Expression value;

		public pinMode(Expression p, Expression value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	// Create digitalWrite
	public static class digitalWrite implements Statement {
		Expression predicate;
		Expression value;

		public digitalWrite(Expression p, Expression value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static digitalWrite digitalwrite(Expression predicate,
			Expression value) {
		return new digitalWrite(predicate, value);
	}

	// Create analogWrite
	public static class analogWrite implements Statement {
		Expression predicate;
		Expression value;

		public analogWrite(Expression p, Expression value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static analogWrite analogwrite(Expression predicate, Expression value) {
		return new analogWrite(predicate, value);
	}

	// Create digitalRead
	public static class digitalRead implements Statement {
		Expression id;

		public digitalRead(Expression id) {
			this.id = id;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static digitalRead digitalread(Expression predicate) {
		return new digitalRead(predicate);
	}

	// Create digitalRead
	public static class analogRead implements Statement {
		Expression predicate;
		Expression value;

		public analogRead(Expression p, Expression value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static analogRead analogread(Expression predicate, Expression value) {
		return new analogRead(predicate, value);
	}

	// Create HIGH and LOW expression
	public static class HIGH implements Expression {
		int value;

		public HIGH() {
			this.value = 1;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static HIGH high() {
		return new HIGH();
	}

	public static class LOW implements Expression {
		int value;

		public LOW() {
			this.value = 0;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static LOW low() {
		return new LOW();
	}

	public static class Number implements Expression {
		int n;

		public Number(int n) {
			this.n = n;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Number number(int n) {
		return new Number(n);
	}

	public static class Operator implements Expression {
		Expression left;
		Expression right;

		private Operator(Expression left, Expression right) {
			this.left = left;
			this.right = right;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static class Plus extends Operator {
		public Plus(Expression left, Expression right) {
			super(left, right);
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Plus plus(Expression left, Expression right) {
		return new Plus(left, right);
	}

	public static class Minus extends Operator {
		public Minus(Expression left, Expression right) {
			super(left, right);
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Minus minus(Expression left, Expression right) {
		return new Minus(left, right);
	}

	public static class Times extends Operator {
		public Times(Expression left, Expression right) {
			super(left, right);
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Times times(Expression left, Expression right) {
		return new Times(left, right);
	}

	public static class Divide extends Operator {
		public Divide(Expression left, Expression right) {
			super(left, right);
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Divide divide(Expression left, Expression right) {
		return new Divide(left, right);
	}

	// -----------------------------------------------------------
	public static Program parse(String str) {
		return new Program(new AST().doParse(str));
	}
	
	public Sequence doParse(String source) {
		int index = 0;
		String[] statements = null;
		String[] tokens = null;
		Sequence sequence = new Sequence();

		// Split source codes to different statements end with .
		String seqDelim = "\\.";
		statements = source.split(seqDelim);

		String tokenDelim = " ";
		// Parse each statement to tokens
		while (index < statements.length) {
			tokens = statements[index].split(tokenDelim);
			// digital Write statement must contain HIGH or LOW
			if (tokens[0].trim().equalsIgnoreCase("digitalWrite")) {
				if (tokens[1].trim().equalsIgnoreCase("LOW")
						&& tokens[2].trim().equalsIgnoreCase("to")) {
					sequence.addNode(new digitalWrite(new Id(tokens[3].trim()),
							new LOW()));
				}else if (tokens[1].trim().equalsIgnoreCase("HIGH")
						&& tokens[2].trim().equalsIgnoreCase("to")) {
					sequence.addNode(new digitalWrite(new Id(tokens[3].trim()),
							new HIGH()));
				}else{
					System.out.println("Error");
					break;
				}
			} 
			// analog Write statement must contain an int
			else if (tokens[0].trim().equalsIgnoreCase("analogWrite")) {
				if (isDigit(tokens[1].trim())
						&& tokens[2].trim().equalsIgnoreCase("to")) {
					sequence.addNode(new analogWrite(new Id(tokens[3].trim()),
							new Number(Integer.parseInt(tokens[1].trim()))));
				}else{
					System.out.println("Error");
					break;
				}
			}
			// digital Read statement
			else if (tokens[0].trim().equalsIgnoreCase("digitalRead")) {
				if (tokens[1].trim().equalsIgnoreCase("from")) {
					sequence.addNode(new digitalRead(new Id(tokens[2].trim())));
				}else{
					System.out.println("Error");
					break;
				}
			}
			// analog Read statement
			else if (tokens[0].trim().equalsIgnoreCase("analogRead")) {
				if (tokens[1].trim().equalsIgnoreCase("from")) {
					sequence.addNode(new digitalRead(new Id(tokens[2].trim())));
				}else{
					System.out.println("Error");
					break;
				}
			}
			// For Loop statement
			else if (tokens[0].trim().equalsIgnoreCase("For")) {
				if (isDigit(tokens[3].trim()) && isDigit(tokens[6].trim())) {
					String[] newTokens;
					Sequence[] seq = null;
					int index2 = 0;
					index++;
					newTokens = statements[index].split(tokenDelim);
					
					while(!newTokens[0].trim().equalsIgnoreCase("End") && !newTokens[1].trim().equalsIgnoreCase("for")){
						newTokens = statements[index].split(tokenDelim);
						seq[index2] = doParse(statements[index]);
						index2++;
					}
					sequence.addNode(new forLoop(new Id(tokens[1].trim()), new Number(Integer.parseInt(tokens[3].trim())), new Number(Integer.parseInt(tokens[6].trim())), seq));
					index++;
				}else{
					System.out.println("Error");
					break;
				}
			}
			// Last case - Assign - when first token doesn't match any
			else {
				if (tokens[1].trim().equalsIgnoreCase("is") && isDigit(tokens[2])) {
					sequence.addNode(new Assign(new Id(tokens[0].trim()), new Number(Integer.parseInt(tokens[2].trim()))));
				}else{
					System.out.println("Unknown Statement.");
					break;
				}
			}
		}
		return sequence;

	}

	public static boolean isDigit(String token) {
		int index = 0;
		while (index < token.length()) {
			Character c = token.charAt(index);
			if (!Character.isDigit(c)) {
				return false;
			}
			index++;
		}
		return true;
	}

	// -------------------------------------------------------------------------------

	public static void main(String[] args) {
		String source = "pinX is 5.";
		//Invalid source String source = "pinX is five."
		
		Node newNode = AST.parse(source);
	}
}
