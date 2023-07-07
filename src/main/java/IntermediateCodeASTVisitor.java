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
import threeaddr.Intermediate;
import threeaddr.PrintInstr;
import threeaddr.UnaryOpInstr;
import threeaddr.FuctionCallInstr;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SpacerStatement node) throws ASTVisitorException {
		node.getExpression1().accept(this);
		String t1 = stack.pop();
		node.getExpression2().accept(this);
		String t2 = stack.pop();
		intermediate.add(new AssignInstr(t1, t2));
	}

	@Override
	public void visit(StatementGroup node) throws ASTVisitorException {
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
		stack.push(node.getIdentifier());
		
	}

	@Override
	public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UnaryExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		String t1 = stack.pop();
		String t = createTemp();
		stack.push(t);
		intermediate.add(new UnaryOpInstr(node.getOperator(),t1, t));
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ParenthesisExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		String t1 = stack.pop();
		String t = createTemp();
		stack.push(t);
		intermediate.add(new AssignInstr(t1, t));
	}

	@Override
	public void visit(ParenthesisCondition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
		String t = stack.pop();
		PrintInstr instr = new PrintInstr(t);
		intermediate.add(instr);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		
	}

}
