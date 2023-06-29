import org.apache.commons.lang3.StringEscapeUtils;

import ast.ASTVisitor;
import ast.ASTVisitorException;
// import ast.ArrayExpression;
import ast.SpacerStatement;
import ast.BinaryExpression;
import ast.BlockStatement;
import ast.Program;
import ast.StatementGroup;
import ast.IdentifierExpression;
import ast.IfElseStatement;
import ast.IfStatement;
import ast.IntegerLiteralExpression;
// import ast.LValueExpression;
import ast.LocalDefinition;
import ast.CharLiteralExpression;
import ast.Definition;
import ast.EmptyStatement;
import ast.Expression;
import ast.FunctionCallStatement;
import ast.FunctionDeclaration;
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

public class PrintASTVisitor implements ASTVisitor {
    
    @Override
    public void visit(Program node) throws ASTVisitorException {
        node.getFd().accept(this);
    }

    @Override
    public void visit(SpacerStatement node) throws ASTVisitorException {
        node.getExpression1().accept(this);;
        System.out.print(" <- ");
        node.getExpression2().accept(this);
        System.out.println(";");
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        System.out.print(" ");
        System.out.print(node.getOperator());
        System.out.print(" ");
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        System.out.print(node.getOperator());
        System.out.print(" ");
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        System.out.print(node.getIdentifier());
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        System.out.print(node.getLiteral());
    }
    
    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        System.out.print("\"");
        System.out.print(StringEscapeUtils.escapeJava(node.getLiteral()));
        System.out.print("\"");
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        System.out.print("( ");
        node.getExpression().accept(this);
        System.out.print(" )");
    }

    @Override
    public void visit(StatementGroup node) throws ASTVisitorException {
        System.out.println("{ ");
        for(Statement st: node.getStatements()) { 
            st.accept(this);
        }
        System.out.println("}");
    }

    public void visit(WhileStatement node) throws ASTVisitorException {
        System.out.print("while ( ");
        node.getExpression().accept(this);
        System.out.println(" ) do ");
        node.getStatement().accept(this);
    }

	@Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
		System.out.print("\'");
        System.out.print(node.getLiteral());
        System.out.print("\'");
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		node.getCondition1().accept(this);
        System.out.print(" ");
        System.out.print(node.getOperator());
        System.out.print(" ");
        node.getCondition2().accept(this);
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		System.out.print(node.getOperator());
        System.out.print(" ");
        node.getCondition().accept(this);
	}

    @Override
    public void visit(ParenthesisCondition node) throws ASTVisitorException {
        System.out.print("( ");
        node.getCondition().accept(this);
        System.out.print(" )");
    }

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		System.out.print("puts( ");
        node.getExpression().accept(this);
        System.out.println(" );");
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		System.out.print("fun ");
        System.out.print(node.getIdentifier());
        System.out.print("(");
        if (node.getParameter() != null) {
            node.getParameter().accept(this);
        }
        for(Definition fp: node.getParameters()) {
            System.out.print(", ");
            fp.accept(this);
        }
        System.out.print(") : ");
        System.out.println(node.getType());
	}

	@Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
        if (node.getRef() == true) {
            System.out.print("ref ");
        }
        System.out.print(node.getIdentifier());
        for(String id: node.getIdentifiers()) {
            System.out.print(", ");
            System.out.print(id);
        }
        System.out.print(" : ");
        System.out.print(node.getType());
	}

	@Override
	public void visit(FunctionDeclaration node) throws ASTVisitorException {
		System.out.print("fun ");
        System.out.print(node.getIdentifier());
        System.out.print("(");
        for(Definition fp: node.getParameterList()) {
            fp.accept(this);
            System.out.print(", ");
        }
        System.out.print(") : ");
        System.out.println(node.getType());
        System.out.println(";");
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		System.out.print("if ");
        node.getExpression().accept(this);
        System.out.println(" then ");
        System.out.print("\t");
        node.getStatement().accept(this);
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		System.out.println(";");
	}

	@Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
        System.out.print(node.getIdentifier());
        System.out.print("(");
        for(Expression e: node.getExpressions()) {
            e.accept(this);
            System.out.print(", ");
        }
        System.out.println(");");
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		System.out.print("if ");
        node.getExpression().accept(this);
        System.out.println(" then ");
        System.out.print("\t");
        node.getStatement1().accept(this);
        System.out.println(" else ");
        System.out.print("\t");
        node.getStatement2().accept(this);
	}

	// @Override
	// public void visit(ArrayExpression node) throws ASTVisitorException {
    //     System.out.print(node.getIdentifier());
    //     System.out.print("[");
    //     node.getExpression().accept(this);
    //     System.out.print("]");
	// }

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		System.out.print("return ");
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
            System.out.println(";");
        } else {
            System.out.println(";");
        }
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		System.out.print("var ");
        System.out.print(node.getIdentifier());
        for(String id: node.getIdentifiers()) {
            System.out.print(", ");
            System.out.print(id);
        }
        System.out.print(" : ");
        System.out.print(node.getType());
        if (node.getDimensions() != null) {
            for(Integer d: node.getDimensions()) {
                System.out.print("[");
                System.out.print(d.toString());
                System.out.println("]");
            }
        }
	}

	// @Override
	// public void visit(LValueExpression node) throws ASTVisitorException {
    //     System.out.print(node.getExpression1());
    //     System.out.print("[");
    //     if (node.getExpression2() != null) {
    //         node.getExpression2().accept(this);
    //     }
    //     System.out.print("]");
	// }

	@Override
	public void visit(BlockStatement node) throws ASTVisitorException {
		System.out.println("{ ");
        for(Statement st: node.getStatements()) {
            st.accept(this);
        }
        System.out.println("}");
	}

	@Override
	public void visit(LocalDefinition node) throws ASTVisitorException {
		if (node.getDefinitions().size() > 0) {
            for (Definition d: node.getDefinitions()) {
                d.accept(this);
            }
        }
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		node.getHeader().accept(this);
        for (Definition d: node.getDefinitions()) {
            d.accept(this);
        }
        node.getBlock().accept(this);
	}
}
