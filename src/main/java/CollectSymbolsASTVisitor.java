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

import java.util.List;

public class CollectSymbolsASTVisitor implements ASTVisitor {

    public CollectSymbolsASTVisitor() {
    }

	@Override
	public void visit(Program node) throws ASTVisitorException {
		node.getFd().accept(this);
	}

	@Override
	public void visit(SpacerStatement node) throws ASTVisitorException {
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(StatementGroup node) throws ASTVisitorException {
		for (Statement st : node.getStatements()) {
			st.accept(this);
		}
	}

	@Override
	public void visit(IdentifierExpression node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(StringLiteralExpression node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(BinaryExpression node) throws ASTVisitorException {
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(UnaryExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(ParenthesisExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(ParenthesisCondition node) throws ASTVisitorException {
		node.getCondition().accept(this);
	}

	@Override
	public void visit(WhileStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		node.getHeader().accept(this);
		for (int i = 0; i < node.getDefinitions().size(); i++) {
            node.getDefinitions().get(i).accept(this);
        }
		node.getBlock().accept(this);
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        List<String> identifier = node.getIdentifiers();
        String type = node.getType();

        for (String id : identifier) {
            if (symbolTable.innerScopeLookup(id) != null) {
                ASTUtils.error(node, "Variable " + id + " already defined");
            } else {
				System.out.println("Adding variable " + id + " to symbol table");
            	symbolTable.put(id, new Info(id, type));
			}
        }
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
        node.getStatement().accept(this);
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		node.getStatement1().accept(this);
        node.getStatement2().accept(this);
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        List<String> identifier = node.getIdentifiers();
        ast.Type type = node.getType();

        for (String id : identifier) {
            if (symbolTable.innerScopeLookup(id) != null) {
                ASTUtils.error(node, "Variable " + id + " already defined");
            } else {
				System.out.println("Adding variable " + id + " to symbol table");
            	symbolTable.put(id, new Info(id, type));
			}
        }
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		for (Definition d : node.getParameters()) {
            d.accept(this);
        }
        SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        String identifier = node.getIdentifier();
        ast.Type type = node.getType();

        if (symbolTable.innerScopeLookup(identifier) != null) {
            ASTUtils.error(node, "Variable " + identifier + " already defined");
        }
        symbolTable.put(identifier, new Info(identifier, type));

	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		for (Expression e: node.getExpressions()) {
            e.accept(this);
        }
	}  
}
