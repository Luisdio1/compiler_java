import java.util.ArrayDeque;
import java.util.Deque;

import ast.ASTUtils;
import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.ArrayType;
import ast.SpacerStatement;
import ast.BinaryExpression;
import ast.Program;
import ast.StatementGroup;
import ast.IdentifierExpression;
import ast.IfElseStatement;
import ast.IfStatement;
import ast.IntegerLiteralExpression;
import ast.LValueExpression;
import ast.CharLiteralExpression;
import ast.Definition;
import ast.EmptyStatement;
import ast.Expression;
import ast.FunctionCallExpression;
import ast.FunctionCallStatement;
import ast.FunctionDefinition;
import ast.FunctionParameterDefinition;
import ast.HeaderDefinition;
import ast.ParenthesisExpression;
import ast.ParenthesisCondition;
import ast.Statement;
import ast.StringLiteralExpression;
import ast.UnaryExpression;
import ast.VariableDefinition;
import ast.WhileStatement;
import ast.BinaryCondition;
import ast.UnaryCondition;
import ast.PutsStatement;
import ast.ReturnStatement;
import symbol.Info;
import symbol.SymbolTable;
import symbol.HashSymbolTable;

public class SymTableBuilderASTVisitor implements ASTVisitor {

    private final Deque<SymbolTable<Info>> stack;

	private int scopeCounter = 0;

    public SymTableBuilderASTVisitor() {
		stack = new ArrayDeque<>();
	}

	@Override
	public void visit(Program node) throws ASTVisitorException {
		startScope();
        ASTUtils.setSymbolTable(node, stack.element());
        node.getFd().accept(this);
        endScope();
	}

	@Override
	public void visit(SpacerStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(StatementGroup node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		for (Statement st : node.getStatements()) {
			st.accept(this);
		}
	}

	@Override
	public void visit(IdentifierExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(StringLiteralExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(BinaryExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(UnaryExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression().accept(this);
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		if (node.getExpression1() != null) {
            node.getExpression1().accept(this);
        } 
		if (node.getCondition1() != null) {
            node.getCondition1().accept(this);
        }
		if (node.getExpression2() != null) {
            node.getExpression2().accept(this);
        } 
		if (node.getCondition2() != null) {
            node.getCondition2().accept(this);
        }
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
	}

	@Override
	public void visit(ParenthesisExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression().accept(this);
	}

	@Override
	public void visit(ParenthesisCondition node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getCondition().accept(this);
	}

	@Override
	public void visit(WhileStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getCondition().accept(this);
		node.getStatement().accept(this);
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression().accept(this);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		
		ASTUtils.setSymbolTable(node, stack.element());
		startScope();
		scopeCounter++;
        node.getHeader().accept(this);
		System.out.println("Header scope " + scopeCounter);
		
        for (Definition d : node.getDefinitions()) {
			System.out.println("Definition scope " + scopeCounter);
			d.accept(this);
		}
		System.out.println("Block scope " + scopeCounter);
        node.getBlock().accept(this);
		endScope();
		scopeCounter--;
        
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getCondition().accept(this);
		node.getStatement().accept(this);
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
        node.getExpression().accept(this);
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getCondition().accept(this);
		node.getStatement1().accept(this);
        node.getStatement2().accept(this);
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
        if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
        for (Definition d : node.getParameters()) {
            d.accept(this);
        }
	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		ASTUtils.setSymbolTable(node, stack.element());
		node.getExpression().accept(this);
        for (Expression e: node.getExpressions()) {
            e.accept(this);
        }
	}

    private void startScope() {
		SymbolTable<Info> oldSymTable = stack.peek();
		SymbolTable<Info> symTable = new HashSymbolTable<>(oldSymTable);
		stack.push(symTable);
	}

	private void endScope() {
		stack.pop();
	}

	public int getScopeCounter() {
		return scopeCounter;
	}
}