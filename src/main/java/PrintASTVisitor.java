import org.apache.commons.lang3.StringEscapeUtils;

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

public class PrintASTVisitor implements ASTVisitor {
    private int indent = 0;
    String indentString = "    ";
    
    @Override
    public void visit(Program node) throws ASTVisitorException {
        node.getFd().accept(this);
    }

    @Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
        printIdentation();
        node.getHeader().accept(this);
        indent++;
        for (int i = 0; i < node.getDefinitions().size(); i++) {
            node.getDefinitions().get(i).accept(this);
        }
        indent--;
        printIdentation();
        System.out.println("{");
        indent++;
        node.getBlock().accept(this);
        indent--;
        System.out.println("}");
	}

    @Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
		System.out.print("fun ");
        System.out.print(node.getIdentifier());
        System.out.print(" (");
        for(Definition fp: node.getParameters()) {
            fp.accept(this);
            if (fp != node.getParameters().get(node.getParameters().size() - 1)) {
                System.out.print("; ");
            }
        }
        System.out.print("): ");
        System.out.println(node.getType());
	}

    @Override
	public void visit(FunctionParameterDefinition node) throws ASTVisitorException {
        for (int i = 0; i < node.getIdentifiers().size(); i++) {
            if (node.getRef() == true) {
                System.out.print("ref ");
            }
            System.out.print(node.getIdentifiers().get(i));
            System.out.print(" : ");
            System.out.print(node.getType());
            if (i != node.getIdentifiers().size() - 1) {
                System.out.print("; ");
            }
        }
	}

    @Override
    public void visit(StatementGroup node) throws ASTVisitorException {
        for(Statement st: node.getStatements()) { 
            st.accept(this);
        }
        indent--;
        printIdentation();
        indent++;
    }

    @Override
	public void visit(ArrayType node) throws ASTVisitorException {
		System.out.print("HELLO");
	}

    @Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		printIdentation();
        System.out.print("var ");
        for(String id: node.getIdentifiers()) {
            System.out.print(id);
            if (id != node.getIdentifiers().get(node.getIdentifiers().size() - 1)) {
                System.out.print(", ");
            }
        }
        System.out.print(" : ");
        System.out.print(node.getType());
        if (node.getDimensions().size() > 0) {
            for(Integer d: node.getDimensions()) {
                System.out.print("[");
                System.out.print(d.toString());
                System.out.print("]");
            }
        }
        System.out.println(";");
	}

    @Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		System.out.println(";");
	}

    @Override
    public void visit(SpacerStatement node) throws ASTVisitorException {
        printIdentation();
        if (node.getExpression1() != null) {
            node.getExpression1().accept(this);
        } else {
            System.out.print(node.getIdentifier());
        }
        System.out.print(" <- ");
        if (node.getExpression2() != null) {
            node.getExpression2().accept(this);
        }
        System.out.println(";");
    }

    @Override
	public void visit(FunctionCallStatement node) throws ASTVisitorException {
        printIdentation();
        node.getExpression().accept(this);
        System.out.println(";");
	}

    @Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		System.out.print(node.getIdentifier());
        System.out.print("(");
        node.getExpression().accept(this);
        for(Expression e: node.getExpressions()) {
            System.out.print(", ");
            e.accept(this);
        }
        System.out.print(")");
	}

    @Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		printIdentation();
        System.out.print("puts(");
        node.getExpression().accept(this);
        System.out.println(");");
	}

    @Override
	public void visit(IfStatement node) throws ASTVisitorException {
		printIdentation();
        System.out.print("if ");
        for (int i = 0; i < node.getCondition().size(); i++) {
            node.getCondition().get(i).accept(this);
            if (i < node.getCondition().size() - 1) {
                System.out.print(" && ");
            }
        }
        System.out.println(" then {");
        indent++;
        node.getStatement().accept(this);
        indent--;
        System.out.println("}");
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		printIdentation();
        System.out.print("if ");
        for (int i = 0; i < node.getCondition().size(); i++) {
            node.getCondition().get(i).accept(this);
            if (i < node.getCondition().size() - 1) {
                System.out.print(" && ");
            }
        }
        System.out.println(" then {");
        indent++;
        node.getStatement1().accept(this);
        indent--;
        printIdentation();
        indent++;
        if (node.getStatement2().getClass() == IfElseStatement.class) {
            indent--;
            System.out.print("} else ");
            node.getStatement2().accept(this);
        } else {
            System.out.println("} else {");
            node.getStatement2().accept(this);
            System.out.println("}");
        }
	}

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        printIdentation();
        System.out.print("while (");
        for (int i = 0; i < node.getCondition().size(); i++) {
            node.getCondition().get(i).accept(this);
            if (i < node.getCondition().size() - 1) {
                System.out.print(" && ");
            }
        }
        System.out.println(") do {");
        indent++;
        node.getStatement().accept(this);
        indent--;
        System.out.println("}");
    }

    @Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		printIdentation();
        System.out.print("return ");
        if (node.getExpression() != null) {
            node.getExpression().accept(this);
            System.out.println(";");
        } else {
            System.out.println(";");
        }
	}

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        System.out.print(node.getIdentifier());
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        System.out.print("\"");
        System.out.print(StringEscapeUtils.escapeJava(node.getLiteral()));
        System.out.print("\"");
    }

    @Override
	public void visit(LValueExpression node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        System.out.print("[");
        if (node.getExpression2() != null) {
            node.getExpression2().accept(this);
        }
        System.out.print("]");
	}

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        System.out.print(node.getLiteral());
    }

    @Override
	public void visit(CharLiteralExpression node) throws ASTVisitorException {
        System.out.print(StringEscapeUtils.escapeJava(node.getLiteral()));
	}

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        System.out.print("(");
        node.getExpression().accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        System.out.print(node.getOperator());
        System.out.print(" ");
        node.getExpression().accept(this);
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
    public void visit(ParenthesisCondition node) throws ASTVisitorException {
        System.out.print("(");
        node.getCondition().accept(this);
        System.out.print(")");
    }

    @Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		System.out.print(node.getOperator());
        System.out.print(" ");
        node.getCondition().accept(this);
	}

	@Override
	public void visit(BinaryCondition node) throws ASTVisitorException {
		if (node.getCondition1() != null) {
            node.getCondition1().accept(this);
        } else {
            node.getExpression1().accept(this);
        }
        System.out.print(" ");
        System.out.print(node.getOperator());
        System.out.print(" ");
        if (node.getCondition2() != null) {
            node.getCondition2().accept(this);
        } else {
            node.getExpression2().accept(this);
        }
	}

    public void printIdentation() {
        for (int i = 0; i < indent; i++) {
            System.out.print(indentString);
        }
    }
}