import org.apache.commons.lang3.StringEscapeUtils;

import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.SpacerStatement;
import ast.BinaryExpression;
import ast.Program;
import ast.StatementGroup;
import ast.IdentifierExpression;
import ast.IntegerLiteralExpression;
import ast.CharLiteralExpression;
import ast.ParenthesisExpression;
import ast.ParenthesisCondition;
import ast.Statement;
import ast.StringLiteralExpression;
import ast.UnaryExpression;
import ast.WhileStatement;
import ast.BinaryCondition;
import ast.UnaryCondition;
import ast.PutsStatement;

public class PrintASTVisitor implements ASTVisitor {
    
    @Override
    public void visit(Program node) throws ASTVisitorException {
        ((SpacerStatement) node.getFd()).accept(this);
    }

    @Override
    public void visit(SpacerStatement node) throws ASTVisitorException {
        System.out.print(node.getIdentifier());
        System.out.print(" <- ");
        node.getExpression().accept(this);
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
        System.out.println(" ) ");
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
}
