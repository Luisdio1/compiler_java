// NEEDS CHANGES!!!

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
import ast.Operator;
import ast.CharLiteralExpression;
import ast.Definition;
import ast.EmptyStatement;
import ast.Expression;
import org.objectweb.asm.Type;
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
import symbol.Info;
import symbol.SymbolTable;
import types.TypeException;
import types.TypeUtils;
import ast.BinaryCondition;
import ast.UnaryCondition;
import ast.PutsStatement;
import ast.ReturnStatement;

public class CollectTypesASTVisitor implements ASTVisitor {

    public CollectTypesASTVisitor() {
    }

	@Override
	public void visit(Program node) throws ASTVisitorException {
		node.getFd().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(SpacerStatement node) throws ASTVisitorException {
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
		Type leftType;
		String id = "";
		if (node.getIdentifier() != null) {
			Info idInfo = symbolTable.lookup(node.getIdentifier());
			if (idInfo == null) {
				ASTUtils.error(node, "Identifier " + node.getIdentifier() + " not declared.");
			}
			leftType = idInfo.getType();
		} else if (node.getExpression1() != null) {
			if (node.getExpression1().getClass() == IdentifierExpression.class) {
				IdentifierExpression expression = (IdentifierExpression) node.getExpression1();
				id = expression.getIdentifier();
			} else if (node.getExpression1().getClass() == LValueExpression.class ){
				LValueExpression expression = (LValueExpression) node.getExpression1();
				if (expression.getExpression1().getClass() == IdentifierExpression.class) {
					IdentifierExpression identifierExpression = (IdentifierExpression) expression.getExpression1();
					id = identifierExpression.getIdentifier();
					expression.getExpression1().accept(this);
				} else {
					ASTUtils.error(node, "Invalid SpacerStatement");
					return;
				}
			} else {
				ASTUtils.error(node, "Invalid SpacerStatement");
				return;
			}
			Info expInfo = symbolTable.lookup(id);
			if (expInfo == null) {
				ASTUtils.error(node, "Expression " + id + " not declared.");
			}
			leftType = expInfo.getType();
		} else {
			ASTUtils.error(node, "Invalid SpacerStatement");
			return;
		}
		node.getExpression2().accept(this);
		Type rightType = ASTUtils.getSafeType(node.getExpression2());

		if (!TypeUtils.isAssignable(leftType, rightType)) {
			ASTUtils.error(node, "Cannot assign " + rightType + " to " + leftType);
		}
		ASTUtils.setType(node, leftType);
	}

	@Override
	public void visit(StatementGroup node) throws ASTVisitorException {
		for (Statement st : node.getStatements()) {
			st.accept(this);
		}
        ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(IdentifierExpression node) throws ASTVisitorException {
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
		Info info = symbolTable.lookup(node.getIdentifier());
		if (info == null) {
			ASTUtils.error(node, "Identifier " + node.getIdentifier() + " not declared.");
		} else {
			Type type = info.getType();
			System.out.println("Identifier " + node.getIdentifier() + " has type " + type);
			ASTUtils.setType(node, type);
		}
	}

	@Override
	public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.INT_TYPE);
	}

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.CHAR_TYPE);
	}

	@Override
	public void visit(StringLiteralExpression node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.getType(String.class));
	}

	@Override
	public void visit(BinaryExpression node) throws ASTVisitorException {
		node.getExpression1().accept(this);
		Type type1 = ASTUtils.getSafeType(node.getExpression1());

		node.getExpression2().accept(this);
		Type type2 = ASTUtils.getSafeType(node.getExpression2());

		Operator operator = node.getOperator();
		try {
			Type resultType = TypeUtils.applyBinary(operator, type1, type2);
			ASTUtils.setType(node, resultType);
		} catch (TypeException e) {
			ASTUtils.error(node, e.getMessage());
		}
	}

	@Override
	public void visit(UnaryExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		try {
			ASTUtils.setType(node, TypeUtils.applyUnary(node.getOperator(), ASTUtils.getSafeType(node.getExpression())));
		} catch (TypeException e) {
			ASTUtils.error(node, e.getMessage());
		}
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		Type type1 = null, type2 = null;
		String id = "";
		Integer num = null;
		if (node.getExpression1() != null) {
			node.getExpression1().accept(this);
			type1 = ASTUtils.getSafeType(node.getExpression1());
			if (node.getExpression1().getClass() == IdentifierExpression.class) {
				IdentifierExpression expression = (IdentifierExpression) node.getExpression1();
				id = expression.getIdentifier();
				System.out.println("IdExp1 id: " + id + " with type: " + type1);
			} else if (node.getExpression1().getClass() == FunctionCallExpression.class) {
				FunctionCallExpression expression = (FunctionCallExpression) node.getExpression1();
				id = expression.getIdentifier();
				System.out.println("FuncCallExp1 id: " + id + " with type: " + type1);
			}
		}
		if (node.getCondition1() != null) {
			node.getCondition1().accept(this);
			type1 = ASTUtils.getSafeType(node.getCondition1());
			System.out.println("It's a condition1 with type: " + type1);
		}
		if (node.getExpression2() != null) {
			node.getExpression2().accept(this);
			type2 = ASTUtils.getSafeType(node.getExpression2());
			if (node.getExpression2().getClass() == IdentifierExpression.class) {
				IdentifierExpression expression = (IdentifierExpression) node.getExpression2();
				id = expression.getIdentifier();
				System.out.println("IdExp2 id: " + id + " with type: " + type2);
			} else if (node.getExpression2().getClass() == FunctionCallExpression.class) {
				FunctionCallExpression expression = (FunctionCallExpression) node.getExpression2();
				id = expression.getIdentifier();
				System.out.println("FuncCallExp2 id: " + id + " with type: " + type1);
			} else if (node.getExpression2().getClass() == IntegerLiteralExpression.class) {
				IntegerLiteralExpression expression = (IntegerLiteralExpression) node.getExpression2();
				num = expression.getLiteral();
				System.out.println("IntLitExp2 num: " + num + " with type: " + Type.INT_TYPE);
			}
		} 
		if (node.getCondition2() != null) {
			node.getCondition2().accept(this);
			type2 = ASTUtils.getSafeType(node.getCondition2());
			System.out.println("It's a condition2 with type: " + type2);
		}

		Operator operator = node.getOperator();
		Type resultType = null;
		try {
			resultType = TypeUtils.applyBinary(operator, type1, type2);
			ASTUtils.setType(node, resultType);
		} catch (TypeException e) {
			ASTUtils.error(node, e.getMessage() + " because of " + type1 + " and " + type2 + " and " + operator);
		} finally {
			System.out.println("BinaryCondition has type " + resultType);
		}
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
			try {
				ASTUtils.setType(node, TypeUtils.applyUnary(node.getOperator(), ASTUtils.getSafeType(node.getExpression())));
			} catch (TypeException e) {
				ASTUtils.error(node, e.getMessage());
			}
		} else {
			node.getCondition().accept(this);
			try {
				ASTUtils.setType(node, TypeUtils.applyUnary(node.getOperator(), ASTUtils.getSafeType(node.getCondition())));
			} catch (TypeException e) {
				ASTUtils.error(node, e.getMessage());
			}
		}	
	}

	@Override
	public void visit(ParenthesisExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		ASTUtils.setType(node, ASTUtils.getSafeType(node.getExpression()));
	}

	@Override
	public void visit(ParenthesisCondition node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN_TYPE)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		ASTUtils.setType(node, Type.BOOLEAN_TYPE);
	}

	@Override
	public void visit(WhileStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN_TYPE)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		node.getStatement().accept(this);
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		node.getHeader().accept(this);
		for (int i = 0; i < node.getDefinitions().size(); i++) {
            node.getDefinitions().get(i).accept(this);
        }
		node.getBlock().accept(this);
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN_TYPE)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		node.getStatement().accept(this);
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
		if (node.getExpression().getClass() == IdentifierExpression.class) {
			IdentifierExpression ie = (IdentifierExpression) node.getExpression();
			String identifier = ie.getIdentifier();
			SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
			if (symbolTable.lookup(identifier) == null) {
				ASTUtils.error(node, "Function " + identifier + " not defined!");
			} else {
				Info info = symbolTable.lookup(identifier);
				Type type = info.getType();
				System.out.println("Function " + identifier + " exists in ST and has type " + type + " !");
				ASTUtils.setType(node, type);
			}
		}
		// node.getExpression().accept(this);
		// ASTUtils.setType(node, ASTUtils.getSafeType(node.getExpression()));
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		if (!ASTUtils.getSafeType(node.getCondition()).equals(Type.BOOLEAN_TYPE)) {
			ASTUtils.error(node.getCondition(), "Invalid condition, should be boolean");
		}
		node.getStatement1().accept(this);
		node.getStatement2().accept(this);
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
			ASTUtils.setType(node, ASTUtils.getSafeType(node.getExpression()));
		} else {
			ASTUtils.setType(node, Type.VOID_TYPE);
		}
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		String id = "";
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
		if (node.getExpression1().getClass() == IdentifierExpression.class) {
			IdentifierExpression idexp = (IdentifierExpression) node.getExpression1();
			id = idexp.getIdentifier();
		}
		Info info = ASTUtils.getSafeSymbolTable(node).lookup(id);
		if (info != null) {
			ASTUtils.setType(node, info.getType());
			System.out.println("LValue " + id + " has type " + info.getType() + "!");
		} else {
			ASTUtils.setType(node, Type.VOID_TYPE);
			ASTUtils.error(node, "LValue " + id + " not declared");
		}
		
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		for (Definition d : node.getParameters()) {
            d.accept(this);
        }
		// String identifier = node.getIdentifier();
		Type type = node.getType();
		System.out.println("I SEE THAT HEADER " + node.getIdentifier() + " HAS TYPE " + type);
		ASTUtils.setType(node, type);
	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		ASTUtils.setType(node, Type.VOID_TYPE);
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		// node.getExpression().accept(this);
		for (Expression e: node.getExpressions()) {
            e.accept(this);
        }
		SymbolTable<Info> table = ASTUtils.getSafeSymbolTable(node);
		if (table.lookup(node.getIdentifier()) != null) {
			ASTUtils.setType(node, table.lookup(node.getIdentifier()).getType());
		} else {
			ASTUtils.error(node, "Function " + node.getIdentifier() + " not defined");
		}
	}
    
}
