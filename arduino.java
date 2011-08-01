import java.util.ArrayList;


public class Arduino {
	// Create an interface Node to reuse
	public interface Node {
		<T> T accept(Visitor<T> v);
	}

	// Create an interface Visitor
	public interface Visitor<T> {
		T visit(Loop loop);

		T visit(Branch branch);

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

		T visit(IOvalue iOvalue);

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

	// Create interface IOvalue for variable names
	public static class IOvalue implements Expression {
		String value;

		public IOvalue(String value) {
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static IOvalue ioValue(String value) {
		return new IOvalue(value);
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
		Number value;

		public Assign(Id variable, Number value) {
			this.variable = variable;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Assign assign(Id var, Number val) {
		return new Assign(var, val);
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
		Sequence body;

		public Loop(Sequence body) {
			this.body = body;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static Loop loop(Sequence body) {
		return new Loop(body);
	}

	// Create For Loop
	public static class forLoop implements Statement {
		Id id;
		Number initValue;
		Number maxValue;
		Sequence body;

		public forLoop(Id id, Number init, Number max, Sequence body) {
			this.id = id;
			initValue = init;
			maxValue = max;
			this.body = body;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static forLoop forloop(Id id, Number initValue, Number maxValue,
			Sequence body) {
		return new forLoop(id, initValue, maxValue, body);
	}

	// Create setUp function
	public static class setUp implements Statement {
		Sequence body;

		public setUp(Sequence body) {
			this.body = body;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static setUp setup(Sequence body) {
		return new setUp(body);
	}

	// Create pinMode
	public static class pinMode implements Statement {
		Id predicate;
		IOvalue value;

		public pinMode(Id p, IOvalue value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static pinMode pinMode(Id name, IOvalue value) {
		return new pinMode(name, value);
	}

	// Create digitalWrite
	public static class digitalWrite implements Statement {
		Id predicate;
		Expression value;

		public digitalWrite(Id p, Expression value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static digitalWrite digitalwrite(Id predicate, Expression value) {
		return new digitalWrite(predicate, value);
	}

	// Create analogWrite
	public static class analogWrite implements Statement {
		Id predicate;
		Number value;

		public analogWrite(Id p, Number value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static analogWrite analogwrite(Id predicate, Number value) {
		return new analogWrite(predicate, value);
	}

	// Create digitalRead
	public static class digitalRead implements Statement {
		Id id;

		public digitalRead(Id id) {
			this.id = id;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static digitalRead digitalread(Id predicate) {
		return new digitalRead(predicate);
	}

	// Create digitalRead
	public static class analogRead implements Statement {
		Id id;

		public analogRead(Id p) {
			id = p;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static analogRead analogread(Id predicate) {
		return new analogRead(predicate);
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
		return new Program(new Arduino().doParse(str));
	}

	public Sequence doParse(String source) {
		int index = 0;
		String[] statements;
		String[] tokens;
		Sequence sequence = new Sequence();

		// Split source codes to different statements end with .
		String seqDelim = "\\.";
		statements = source.trim().split(seqDelim);

		String tokenDelim = " ";
		// Parse each statement to tokens
		while (index < statements.length) {
			// System.out.println(statements[index]);
			tokens = statements[index].trim().split(tokenDelim);
			// digital Write statement must contain HIGH or LOW
			if (tokens[0].trim().equalsIgnoreCase("digitalWrite")
					&& tokens.length == 4) {
				System.out.println("digitalWrite Called");
				if (tokens[1].trim().equalsIgnoreCase("LOW")
						&& tokens[2].trim().equalsIgnoreCase("to")) {
					sequence.addNode(new digitalWrite(new Id(tokens[3].trim()),
							new LOW()));
				} else if (tokens[1].trim().equalsIgnoreCase("HIGH")
						&& tokens[2].trim().equalsIgnoreCase("to")) {
					sequence.addNode(new digitalWrite(new Id(tokens[3].trim()),
							new HIGH()));
				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}
				index++;
			}
			// analog Write statement must contain an int
			else if (tokens[0].trim().equalsIgnoreCase("analogWrite")) {
				System.out.println("analogWrite Called");
				if (isDigit(tokens[1].trim())
						&& tokens[2].trim().equalsIgnoreCase("to")
						&& tokens.length == 4) {
					sequence.addNode(new analogWrite(new Id(tokens[3].trim()),
							new Number(Integer.parseInt(tokens[1].trim()))));
				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}
				index++;
			}
			// pinMode statement
			else if (tokens[0].trim().equalsIgnoreCase("Set")) {
				System.out.println("Set PinMode Called");
				if (tokens[1].trim().equalsIgnoreCase("pinMode")
						&& tokens.length == 6) {
					sequence.addNode(new pinMode(new Id(tokens[3].trim()),
							new IOvalue(tokens[5].trim())));
				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}
				index++;
			}
			// digital Read statement
			else if (tokens[0].trim().equalsIgnoreCase("digitalRead")) {
				System.out.println("digitalRead Called");
				if (tokens[1].trim().equalsIgnoreCase("from")
						&& tokens.length == 3) {
					sequence.addNode(new digitalRead(new Id(tokens[2].trim())));
				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}
				index++;
			}
			// analog Read statement
			else if (tokens[0].trim().equalsIgnoreCase("analogRead")) {
				System.out.println("analogRead Called");
				if (tokens[1].trim().equalsIgnoreCase("from")
						&& tokens.length == 3) {
					sequence.addNode(new analogRead(new Id(tokens[2].trim())));
				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}
				index++;
			}
			// For Loop statement
			else if (tokens[0].trim().equalsIgnoreCase("For")) {
				System.out.println("For Loop Called");
				if (isDigit(tokens[3].trim()) && isDigit(tokens[6].trim())) {
					String[] newTokens;
					Sequence seq = new Sequence();
					index++;
					newTokens = statements[index].split(tokenDelim);

					while (!newTokens[0].trim().equalsIgnoreCase("End")
							&& !newTokens[1].trim().equalsIgnoreCase("forloop")) {
						// System.out.println("Inner block of For Loop");
						// System.out.println("Statement:"+statements[index]);
						newTokens = statements[index].trim().split(tokenDelim);
						seq.addNode(doParse(statements[index]));
						index++;
					}
					sequence.addNode(new forLoop(new Id(tokens[1].trim()),
							new Number(Integer.parseInt(tokens[3].trim())),
							new Number(Integer.parseInt(tokens[6].trim())), seq));

				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}

			}
			// Set Up statement
			else if (tokens[0].trim().equalsIgnoreCase("Setup")) {
				System.out.println("Setup Called");
				if (statements[index].trim().length() == 5) {
					String[] newTokens;
					Sequence seq = new Sequence();
					index++;
					newTokens = statements[index].split(tokenDelim);

					while (!newTokens[0].trim().equalsIgnoreCase("End")
							&& !newTokens[1].trim().equalsIgnoreCase("setup")) {
						newTokens = statements[index].trim().split(tokenDelim);
						seq.addNode(doParse(statements[index]));
						index++;
					}
					sequence.addNode(new setUp(seq));

				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}

			}
			// Loop statement
			else if (tokens[0].trim().equalsIgnoreCase("Loop")) {
				System.out.println("Loop Called");
				if (statements[index].trim().length() == 4) {
					String[] newTokens;
					Sequence seq = new Sequence();
					// index++;
					newTokens = statements[index].split(tokenDelim);

					while (!newTokens[0].trim().equalsIgnoreCase("End")
							&& !newTokens[1].trim().equalsIgnoreCase("loop")) {
						newTokens = statements[index].trim().split(tokenDelim);
						seq.addNode(doParse(statements[index]));
						index++;
					}
					sequence.addNode(new Loop(seq));

				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}

			}
			// LArduino case - Assign - when first token doesn't match any
			else {

				if (tokens[1].trim().equalsIgnoreCase("is")
						&& isDigit(tokens[2])) {
					System.out.println("Assign Called");
					sequence.addNode(new Assign(new Id(tokens[0].trim()),
							new Number(Integer.parseInt(tokens[2].trim()))));
				} else if (tokens[0].trim().equalsIgnoreCase("End")) {
					if (tokens[1].trim().equalsIgnoreCase("setup")) {
						System.out.println("End Setup Called");
					} else if (tokens[1].trim().equalsIgnoreCase("loop")) {
						System.out.println("End Loop Caled");
					} else if (tokens[1].trim().equalsIgnoreCase("if")) {
						System.out.println("End If Caled");
					} else if (tokens[1].trim().equalsIgnoreCase("ifelse")) {
						System.out.println("End IfElse Caled");
					} else if (tokens[1].trim().equalsIgnoreCase("forloop")) {
						System.out.println("End For Loop Caled");
					} else if (tokens[1].trim().equalsIgnoreCase("switch")) {
						System.out.println("End Switch Caled");
					} else if (tokens[1].trim().equalsIgnoreCase("while")) {
						System.out.println("End While Caled");
					} else if (tokens[1].trim().equalsIgnoreCase("dowhile")) {
						System.out.println("End DoWhile Caled");
					}
				} else {
					System.out.println("Error @ " + statements[index]);
					break;
				}
				index++;
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

	// -------------------------------------------------------
	public static class ArduinoCompiler implements Visitor<String> {

		@Override
		public String visit(Loop loop) {
			System.out.println("Loop(){");
			loop.body.accept(this);
			System.out.println("}");
			return null;
		}

		@Override
		public String visit(Branch branch) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Assign assign) {
			Id name = assign.variable;
			Number value = assign.value;
			System.out.println("Int " + name.id + " = " + value.n + ";");
			return null;
		}

		@Override
		public String visit(Id id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Operator op) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Plus op) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Minus op) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Times op) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Divide op) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Number num) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(Type type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(HIGH high) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(LOW low) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String visit(setUp setUp) {
			System.out.println("Setup(){");
			setUp.body.accept(this);
			System.out.println("}");
			return null;
		}

		@Override
		public String visit(pinMode pinMode) {
			Id name = pinMode.predicate;
			IOvalue value = pinMode.value;
			System.out
					.println("pinMode(" + name.id + ", " + value.value + ");");
			return null;
		}

		@Override
		public String visit(digitalWrite digitalWrite) {
			Id name = digitalWrite.predicate;
			Expression value = digitalWrite.value;
			if (value.getClass().equals(new HIGH())) {
				System.out.println("digitalWrite(" + name.id + ", HIGH);");
			} else {
				System.out.println("digitalWrite(" + name.id + ", LOW);");
			}
			return null;
		}

		@Override
		public String visit(analogWrite analogWrite) {
			Id name = analogWrite.predicate;
			Number value = analogWrite.value;
			System.out
					.println("analogWrite(" + name.id + ", " + value.n + ");");
			return null;
		}

		@Override
		public String visit(digitalRead digitalRead) {
			Id name = digitalRead.id;
			System.out.println("digitalRead(" + name.id + ");");
			return null;
		}

		@Override
		public String visit(analogRead analogRead) {
			Id name = analogRead.id;
			System.out.println("analogRead(" + name.id + ");");
			return null;
		}

		@Override
		public String visit(Program program) {
			program.body.accept(this);
			return null;
		}

		@Override
		public String visit(forLoop forLoop) {
			Id name = forLoop.id;
			Number start = forLoop.initValue;
			Number end = forLoop.maxValue;
			System.out.println("for(int " + name.id + " = " + start.n + "; "
					+ name.id + " <" + end.n + "; " + name.id + "++){");
			forLoop.body.accept(this);
			System.out.println("}");
			return null;
		}

		@Override
		public String visit(IOvalue iOvalue) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	// -------------------------------------------------------------------------------

	public static void main(String[] args) {
		String source = " analogWrite 23 to LED.";
		// String source =
		// "Setup. Set pinMode of LedPin to INPUT. PinX is 0. PinY is 0. End setup. "
		// +
		// " analogRead from LedPin. Loop. digitalWrite LOW to LedPin. End Loop. For x is 0 increasing to 100. PinX is 5. analogRead from LedPin. PinY is 10. End for."
		// +
		// " digitalWrite HIGH to LedPin.";
		// String source =
		// "For x is 0 increasing to 100. pinX is 5. End forloop.";

		Node newNode = Arduino.parse(source);
		System.out.println("Finished Scanning and Parsing.");
		System.out.println("#########");
		System.out.println("Source: " + source);
		System.out.println("#########");
		System.out.println("Arduino Compiled: ");
		newNode.accept(new Arduino.ArduinoCompiler());
	}
}
