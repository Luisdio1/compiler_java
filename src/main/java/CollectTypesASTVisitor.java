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
import ast.Condition;
import ast.Definition;
import ast.EmptyStatement;
import ast.Expression;
import ast.Type;
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
import types.TypeException;
import types.TypeUtils;

public class CollectTypesASTVisitor implements ASTVisitor {

    public CollectTypesASTVisitor() {
    }

	@Override
	public void visit(Program node) throws ASTVisitorException {
		node.getFd().accept(this);
        ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(SpacerStatement node) throws ASTVisitorException {
		node.getExpression2().accept(this);
        ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(StatementGroup node) throws ASTVisitorException {
		for (Statement st : node.getStatements()) {
			st.accept(this);
		}
        ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(IdentifierExpression node) throws ASTVisitorException {
		// FIXME
		// 1. set type to string
		throw new UnsupportedOperationException("PLEASE FIXME");
	}

	@Override
	public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
		// FIXME
		// 1. set type to string
		throw new UnsupportedOperationException("PLEASE FIXME");
	}

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
		// FIXME
		// 1. set type to string
		throw new UnsupportedOperationException("PLEASE FIXME");
	}

	@Override
	public void visit(StringLiteralExpression node) throws ASTVisitorException {
		// FIXME
		// 1. set type to string
		throw new UnsupportedOperationException("PLEASE FIXME");
	}

	@Override
	public void visit(BinaryExpression node) throws ASTVisitorException {
		// FIXME
		// 1. find type of expression1
		// 2. find type of expression2
		// 3. Use TypeUtils.applyBinary to figure type of result
		// 4. error if TypeException
		// 5. set type of result
		throw new UnsupportedOperationException("PLEASE FIXME");
	}

	@Override
	public void visit(UnaryExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		// try {
		// 	ASTUtils.setType(node, TypeUtils.applyUnary(node.getOperator(), ASTUtils.getSafeType(node.getExpression())));
		// } catch (TypeException e) {
		// 	ASTUtils.error(node, e.getMessage());
		// }
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		// FIXME
		// 1. find type of expression1
		// 2. find type of expression2
		// 3. Use TypeUtils.applyBinary to figure type of result
		// 4. error if TypeException
		// 5. set type of result
		throw new UnsupportedOperationException("PLEASE FIXME");
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		node.getExpression().accept(this);
		// try {
		// 	ASTUtils.setType(node, TypeUtils.applyUnary(node.getOperator(), ASTUtils.getSafeType(node.getExpression())));
		// } catch (TypeException e) {
		// 	ASTUtils.error(node, e.getMessage());
		// }
	}

	@Override
	public void visit(ParenthesisExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		ASTUtils.setType(node, ASTUtils.getSafeType(node.getExpression()));
	}

	@Override
	public void visit(ParenthesisCondition node) throws ASTVisitorException {
		node.getCondition().accept(this);
		ASTUtils.setType(node, ASTUtils.getSafeType(node.getCondition()));
	}

	@Override
	public void visit(WhileStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		node.getStatement().accept(this);
		ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
		ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		node.getStatement().accept(this);
		ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		node.getStatement1().accept(this);
		node.getStatement2().accept(this);
		ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.NOTHING);
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}
    
}
