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

	public static digitalWrite digitalwrite(Expression predicate, Expression value) {
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
		Expression predicate;
		Expression value;

		public digitalRead(Expression p, Expression value) {
			predicate = p;
			this.value = value;
		}

		public <T> T accept(Visitor<T> v) {
			return v.visit(this);
		}
	}

	public static digitalRead digitalread(Expression predicate, Expression value) {
		return new digitalRead(predicate, value);
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

	public static class ExpressionInterpreter implements Visitor<Integer> {
		Map<String, Integer> symbols;

		public ExpressionInterpreter(Map<String, Integer> symbols) {
			this.symbols = symbols;
		}

		public Integer visit(Id id) {
			if (symbols.containsKey(id.id))
				return symbols.get(id.id);
			else
				return 0;
		}

		public Integer visit(Operator op) {
			return null;
		}

		public Integer visit(Plus op) {
			return op.left.accept(this) + op.right.accept(this);
		}

		public Integer visit(Minus op) {
			return op.left.accept(this) - op.right.accept(this);
		}

		public Integer visit(Times op) {
			return op.left.accept(this) * op.right.accept(this);
		}

		public Integer visit(Divide op) {
			return op.left.accept(this) * op.right.accept(this);
		}

		public Integer visit(Number num) {
			return num.n;
		}

		public Integer visit(Loop loop) {
			return null;
		}

		public Integer visit(Branch branch) {
			return null;
		}

		public Integer visit(Block block) {
			return null;
		}

		public Integer visit(Assign assign) {
			return null;
		}

		@Override
		public Integer visit(Type type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(HIGH high) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(LOW low) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(setUp setUp) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(pinMode pinMode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(digitalWrite digitalWrite) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(analogWrite analogWrite) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(digitalRead digitalRead) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Integer visit(analogRead analogRead) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class StatementInterpreter implements Visitor<Void> {
		Map<String, Integer> symbols = new HashMap<String, Integer>();
		ExpressionInterpreter eval = new ExpressionInterpreter(symbols);

		public Void visit(Loop loop) {
			while (loop.predicate.accept(eval) != 0)
				loop.body.accept(this);
			return null;
		}

		public Void visit(Branch branch) {
			if (branch.predicate.accept(eval) != 0)
				branch.ifBranch.accept(this);
			else
				branch.elseBranch.accept(this);
			return null;
		}

		public Void visit(Block block) {
			for (Statement s : block.statements)
				s.accept(this);
			return null;
		}

		public Void visit(Assign assign) {
			symbols.put(assign.variable.id, assign.value.accept(eval));
			return null;
		}

		public Void visit(Id id) {
			return null;
		}

		public Void visit(Operator op) {
			return null;
		}

		public Void visit(Plus op) {
			return null;
		}

		public Void visit(Minus op) {
			return null;
		}

		public Void visit(Times op) {
			return null;
		}

		public Void visit(Divide op) {
			return null;
		}

		public Void visit(Number num) {
			return null;
		}

		@Override
		public Void visit(Type type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(HIGH high) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(LOW low) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(setUp setUp) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(pinMode pinMode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(digitalWrite digitalWrite) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(analogWrite analogWrite) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(digitalRead digitalRead) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void visit(analogRead analogRead) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static void main(String[] args) {
		/*
		 * Example program from book
		 */
		Node program = branch(
				plus(id("x"), id("y")),
				block(loop(id("z"), assign(id("z"), plus(id("z"), number(1)))),
						assign(id("x"), number(8))), assign(id("z"), number(7)));
		/*
		 * Factorial Program equivalent to: int factorial = 1; int i = 5; while
		 * (i != 0) { factorial = factorial * i; i = i - 1; }
		 */
		Node factorial = block(
				assign(id("factorial"), number(1)),
				assign(id("i"), number(5)),
				loop(id("i"),
						block(assign(id("factorial"),
								times(id("factorial"), id("i"))),
								assign(id("i"), minus(id("i"), number(1))))));
		StatementInterpreter runner = new StatementInterpreter();
		factorial.accept(runner);
		/*
		 * Print the symbols in the interpreter.
		 */
		for (String s : runner.symbols.keySet())
			System.out.format("%s: %d\n", s, runner.symbols.get(s));
	}
}
