import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import org.apache.commons.lang3.StringEscapeUtils;

import ast.ASTUtils;
import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.ArrayType;
import ast.BinaryCondition;
import ast.BinaryExpression;
import ast.CharLiteralExpression;
import ast.EmptyStatement;
import ast.FunctionCallExpression;
import ast.FunctionCallStatement;
import ast.FunctionDefinition;
import ast.FunctionParameterDefinition;
import ast.HeaderDefinition;
import ast.IdentifierExpression;
import ast.IfElseStatement;
import ast.IfStatement;
import ast.IntegerLiteralExpression;
import ast.LValueExpression;
import ast.ParenthesisCondition;
import ast.ParenthesisExpression;
import ast.Program;
import ast.PutsStatement;
import ast.ReturnStatement;
import ast.SpacerStatement;
import ast.Statement;
import ast.StatementGroup;
import ast.StringLiteralExpression;
import ast.UnaryCondition;
import ast.UnaryExpression;
import ast.VariableDefinition;
import ast.WhileStatement;
import threeaddr.AssignInstr;
import threeaddr.BinaryOpInstr;
import threeaddr.CondJumpInstr;
import threeaddr.Intermediate;
import threeaddr.LabelInstr;
import threeaddr.PrintInstr;
import threeaddr.UnaryOpInstr;
import threeaddr.FuctionCallInstr;
import threeaddr.GoToInstr;

public class IntermediateCodeASTVisitor implements ASTVisitor{
	
	private final Intermediate intermediate;
	private final Deque<String> stack;
	private int temp;
	
	public IntermediateCodeASTVisitor() {
		intermediate = new Intermediate();
		stack = new ArrayDeque<String>();
		temp = 0;
	}
	
	private String createTemp() {
		return "t" + Integer.toString(temp++);
	}
	
	public Intermediate getIntermediate() {
		return intermediate;
	}

	@Override
	public void visit(Program node) throws ASTVisitorException {
		System.out.println("Visiting Program");
		node.getFd().accept(this);;
		
	}

	@Override
	public void visit(SpacerStatement node) throws ASTVisitorException {
		System.out.println("Visiting SpacerStatement");
		node.getExpression1().accept(this);
		String t1 = stack.pop();
		node.getExpression2().accept(this);
		String t2 = stack.pop();
		intermediate.add(new AssignInstr(t1, t2));
	}

	@Override
	public void visit(StatementGroup node) throws ASTVisitorException {
		System.out.println("Visiting StatementGroup");
		Statement s = null, ps;
		Iterator<Statement> it = node.getStatements().iterator();
		while (it.hasNext()) {
			ps = s;
			s = it.next();
			
			if(ps != null && !ASTUtils.getNextList(ps).isEmpty()) {
				Intermediate.backpatch(ASTUtils.getNextList(ps), intermediate.addNewLabel());
			}
			
			s.accept(this);
		}
		if(s != null && !ASTUtils.getNextList(s).isEmpty()) {
			Intermediate.backpatch(ASTUtils.getNextList(s), intermediate.addNewLabel());
		}
		
	}

	@Override
	public void visit(IdentifierExpression node) throws ASTVisitorException {
		System.out.println("Visiting IdentifierExpression");
		stack.push(node.getIdentifier());
		
	}

	@Override
	public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
		System.out.println("Visiting IntegerExpression");
		if (ASTUtils.isBooleanExpression(node)) {
			if (node.getLiteral() != 0) {
				GoToInstr i = new GoToInstr();
				intermediate.add(i);
				ASTUtils.getTrueList(node).add(i);
			} else {
				GoToInstr i = new GoToInstr();
				intermediate.add(i);
				ASTUtils.getFalseList(node).add(i);
			}
		} else {
			String t = createTemp();
			stack.push(t);
			AssignInstr instr = new AssignInstr(String.valueOf(node.getLiteral()), t);
			intermediate.add(instr);
		}
		
	}

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
		System.out.println("Visiting CharLiteralExpression");
		if (ASTUtils.isBooleanExpression(node)) {
			ASTUtils.error(node, "Characters cannot be used as boolean expressions");
		} else {
			String t = createTemp();
			stack.push(t);
			intermediate.add(new AssignInstr("\"" + StringEscapeUtils.escapeJava(node.getLiteral()) + "\"", t));
		}
	}

	@Override
	public void visit(StringLiteralExpression node) throws ASTVisitorException {
		System.out.println("Visiting StringLiteraExpression");
		if (ASTUtils.isBooleanExpression(node)) {
			ASTUtils.error(node, "Strings cannot be used as boolean expressions");
		} else {
			String t = createTemp();
			stack.push(t);
			intermediate.add(new AssignInstr("\"" + StringEscapeUtils.escapeJava(node.getLiteral()) + "\"", t));
		}
		
	}

	@Override
	public void visit(BinaryExpression node) throws ASTVisitorException {
		System.out.println("Visiting BinaryExpression");
		node.getExpression1().accept(this);
		String t1 = stack.pop();
		node.getExpression2().accept(this);
		String t2 = stack.pop();
		
		if (ASTUtils.isBooleanExpression(node)) {
			if (!node.getOperator().isRelational()) {
				ASTUtils.error(node, "A not boolean expression used as boolean");
			}
			CondJumpInstr condJumpInstr = new CondJumpInstr(node.getOperator(), t1, t2);
			GoToInstr goToInstr = new GoToInstr();
			intermediate.add(condJumpInstr);
			intermediate.add(goToInstr);
			
			ASTUtils.getTrueList(node).add(condJumpInstr);
			ASTUtils.getFalseList(node).add(goToInstr);
		} else {
			String t = createTemp();
			stack.push(t);
			BinaryOpInstr instr = new BinaryOpInstr(node.getOperator(), t1, t2, t);
			intermediate.add(instr);
		}
	}

	@Override
	public void visit(UnaryExpression node) throws ASTVisitorException {
		System.out.println("Visiting UnaryExpression");
		node.getExpression().accept(this);
		String t1 = stack.pop();
		String t = createTemp();
		stack.push(t);
		intermediate.add(new UnaryOpInstr(node.getOperator(),t1, t));
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		System.out.println("Visiting BinaryCondition");
		node.getExpression1().accept(this);
		String t1 = stack.pop();
		node.getExpression2().accept(this);
		String t2 = stack.pop();
		if (!node.getOperator().isRelational()) {
			ASTUtils.error(node, "A not boolean expression used as boolean");
		}
		CondJumpInstr condJumpInstr = new CondJumpInstr(node.getOperator(), t1, t2);
		GoToInstr goToInstr = new GoToInstr();
		intermediate.add(condJumpInstr);
		intermediate.add(goToInstr);
			
		ASTUtils.getTrueListCondition(node).add(condJumpInstr);
		ASTUtils.getFalseListCondition(node).add(goToInstr);
		
		// Intermediate.backpatch(ASTUtils.getTrueList(node.getExpression1()), goToInstr);
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		System.out.println("Visiting UnaryCondition");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ParenthesisExpression node) throws ASTVisitorException {
		System.out.println("Visiting ParenthesisExpression");
		node.getExpression().accept(this);
		String t1 = stack.pop();
		String t = createTemp();
		stack.push(t);
		intermediate.add(new AssignInstr(t1, t));
	}

	@Override
	public void visit(ParenthesisCondition node) throws ASTVisitorException {
		System.out.println("Visiting ParenthesisCondition");
		node.getCondition().accept(this);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileStatement node) throws ASTVisitorException {
		System.out.println("Visiting WhileStatement");
		ASTUtils.setBooleanExpression(node.getCondition(), true);
		
		LabelInstr beginLabel = intermediate.addNewLabel();
		node.getCondition().accept(this);
		LabelInstr beginStmtLabel = intermediate.addNewLabel();
		Intermediate.backpatch(ASTUtils.getTrueListCondition(node.getCondition()), beginStmtLabel);
		node.getStatement().accept(this);
		intermediate.add(new GoToInstr(beginLabel));
		Intermediate.backpatch(ASTUtils.getNextList(node.getStatement()), beginLabel);
		
		ASTUtils.getNextList(node).addAll(ASTUtils.getFalseListCondition(node.getCondition()));
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		System.out.println("Visiting PutsStatement");
		node.getExpression().accept(this);
		String t = stack.pop();
		PrintInstr instr = new PrintInstr(t);
		intermediate.add(instr);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		System.out.println("Visiting FunctionDefinition");
		node.getHeader().accept(this);
		node.getBlock().accept(this);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
		System.out.println("Visiting FunctionParameterDefinition");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		System.out.println("Visiting IfStatemtnt");
		ASTUtils.setBooleanExpression(node.getCondition(), true);
		
		node.getCondition().accept(this);
		LabelInstr trueLabel = intermediate.addNewLabel();
		node.getStatement().accept(this);
		Intermediate.backpatch(ASTUtils.getTrueListCondition(node.getCondition()), trueLabel);
		
		ASTUtils.getNextList(node).addAll(ASTUtils.getFalseListCondition(node.getCondition()));
		ASTUtils.getNextList(node).addAll(ASTUtils.getNextList(node.getStatement()));
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		System.out.println("Visiting EmptyStatement");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
		System.out.println("Visiting FunctionCallStatement");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		System.out.println("Visiting IfElseStatement");
		ASTUtils.setBooleanExpression(node.getCondition(), true);
		
		node.getCondition().accept(this);
		LabelInstr stmt1Label = intermediate.addNewLabel();
		node.getStatement1().accept(this);
		GoToInstr nextGoTo = new GoToInstr();
		intermediate.add(nextGoTo);
		LabelInstr stmt2Label = intermediate.addNewLabel();
		node.getStatement2().accept(this);
		
		Intermediate.backpatch(ASTUtils.getTrueListCondition(node.getCondition()), stmt1Label);
		Intermediate.backpatch(ASTUtils.getFalseListCondition(node.getCondition()), stmt2Label);
		
		ASTUtils.getNextList(node).addAll(ASTUtils.getNextList(node.getStatement1()));
		ASTUtils.getNextList(node).addAll(ASTUtils.getNextList(node.getStatement2()));
		ASTUtils.getNextList(node).add(nextGoTo);
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		System.out.println("Visiting ReturnStatement");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		System.out.println("Visiting VariableDefinition");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		System.out.println("Visiting LValueExpression");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		System.out.println("Visiting HeaderDefinition");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		System.out.println("Visiting ArrayTypre");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		System.out.println("Visiting FunctionCallExpression");
		// TODO Auto-generated method stub
		
	}

}
