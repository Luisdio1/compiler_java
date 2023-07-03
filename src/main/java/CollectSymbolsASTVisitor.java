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
import org.objectweb.asm.Type;

import java.util.Arrays;
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
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
		Expression expression = node.getExpression1();
		String identifier = "";
		node.getExpression1().accept(this);
		if (expression.getClass() == IdentifierExpression.class) {
			IdentifierExpression exp = (IdentifierExpression) expression;
			identifier = exp.getIdentifier();
		} else {
			LValueExpression exp = (LValueExpression) expression;
			Expression exp2 = exp.getExpression1();
			if (exp2.getClass() == IdentifierExpression.class) {
				IdentifierExpression exp3 = (IdentifierExpression) exp2;
				identifier = exp3.getIdentifier();
			} else {
				ASTUtils.error(node, "LValue " + identifier + " not found");
				return;
			}
		}
		if (symbolTable.innerScopeLookup(identifier) != null) {
			System.out.println("LValue " + identifier + " is declared in this fuc scope!");
		} else if (symbolTable.lookup(identifier) != null) {
			System.out.println("LValue " + identifier + " is declared in another func scope!");
		} else {
			ASTUtils.error(node, "LValue " + identifier + " was not found in this func scope!");
		}
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
		if (node.getOperator() == Operator.AND || node.getOperator() == Operator.OR) {
			node.getCondition1().accept(this);
			node.getCondition2().accept(this);
		} else {
			node.getExpression1().accept(this);
			node.getExpression2().accept(this);
		}
		//NOT SURE
	}

	@Override
	public void visit(UnaryCondition node) throws ASTVisitorException {
		if (node.getOperator() == Operator.NOT) {
			node.getCondition().accept(this);
		} else {
			node.getExpression().accept(this);
		}
		// NOT SURE
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
		node.getStatement().accept(this);
	}

	@Override
	public void visit(PutsStatement node) throws ASTVisitorException {
		node.getExpression().accept(this);
	}

	@Override
	public void visit(FunctionDefinition node) throws ASTVisitorException {
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
		List<String> libraries = Arrays.asList("puti", "putc", "puts", 
													"geti", "getc", "gets", 
													"abs", "ord", "chr", 
													"strlen", "strcmp", "strcpy", "strcat");
		List<Type> librtypes = Arrays.asList(Type.VOID_TYPE, Type.VOID_TYPE, Type.VOID_TYPE,
											 Type.INT_TYPE, Type.CHAR_TYPE, Type.VOID_TYPE,
											 Type.INT_TYPE, Type.INT_TYPE, Type.CHAR_TYPE,
											 Type.INT_TYPE, Type.INT_TYPE, Type.VOID_TYPE, Type.VOID_TYPE);

		for (int j = 0; j < libraries.size(); j++) {
			symbolTable.put(libraries.get(j), new Info(libraries.get(j), librtypes.get(j)));
			// System.out.println("Adding library " + libraries.get(j) + " with return type " + librtypes.get(j) + " to symbol table!");
		}
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
        Type t = node.getType();

        for (String id : identifier) {
            if (symbolTable.innerScopeLookup(id) != null) {
                ASTUtils.error(node, "Parameter " + id + " already defined in this scope and has type " + t + " !");
            } else {
				System.out.println("Adding parameter " + id + " with type " + t + " to symbol table");
            	symbolTable.put(id, new Info(id, t));
			}
        }
		// System.out.println(symbolTable.getSymbols());
	}

	@Override
	public void visit(IfStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		String identifier = "";
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        Statement statement = node.getStatement();
		if (statement.getClass() == SpacerStatement.class) {
			SpacerStatement ss = (SpacerStatement) statement;
			String lvalue = ss.getExpression1().toString();
			if (ss.getExpression1().getClass() == IdentifierExpression.class) {
				identifier = ((IdentifierExpression) ss.getExpression1()).getIdentifier();
			}
			if (symbolTable.innerScopeLookup(lvalue) != null) {
				ASTUtils.error(node, "LValue " + identifier + " already defined in this func scope!");
			} else {
				Type t = symbolTable.lookup(identifier).getType();
				System.out.println("Adding LValue " + identifier + " to this func scope symbol table!");
				symbolTable.put(identifier, new Info(identifier, t));
			}
        }
        node.getStatement().accept(this);
	}

	@Override
	public void visit(EmptyStatement node) throws ASTVisitorException {
		// Nothing
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
				symbolTable.put(identifier, new Info(identifier, type));
			}
		}
		node.getExpression().accept(this);
	}

	@Override
	public void visit(IfElseStatement node) throws ASTVisitorException {
		node.getCondition().accept(this);
		String identifier = "";
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        Statement statement1 = node.getStatement1();
		if (statement1.getClass() == SpacerStatement.class) {
			SpacerStatement ss1 = (SpacerStatement) statement1;
			Expression lvalue = ss1.getExpression1();
			if (lvalue.getClass() == IdentifierExpression.class) {
				identifier = ((IdentifierExpression)lvalue).getIdentifier();
			}
			if (symbolTable.innerScopeLookup(identifier) != null) {
				ASTUtils.error(node, "LValue " + identifier + " already defined in this func scope!");
			} else {
				Type t = symbolTable.lookup(identifier).getType();
				System.out.println("Adding LValue " + identifier + " to this func scope symbol table!");
				symbolTable.put(identifier, new Info(identifier, t));
			}
        }
		node.getStatement1().accept(this);
		SymbolTable<Info> symbolTable2 = ASTUtils.getSafeSymbolTable(node);
        Statement statement2 = node.getStatement2();
		if (statement2.getClass() == SpacerStatement.class) {
			SpacerStatement ss2 = (SpacerStatement) statement2;
			Expression lvalue2 = ss2.getExpression1();
			if (lvalue2.getClass() == IdentifierExpression.class) {
				identifier = ((IdentifierExpression)lvalue2).getIdentifier();
			}
			if (symbolTable2.innerScopeLookup(identifier) != null) {
				ASTUtils.error(node, "LValue " + identifier + " already defined in this func scope!");
			} else {
				Type t2 = symbolTable2.lookup(identifier).getType();
				System.out.println("Adding LValue " + identifier + " to this func scope symbol table!");
				symbolTable.put(identifier, new Info(identifier, t2));
			}
        }
        node.getStatement2().accept(this);
	}

	@Override
	public void visit(ReturnStatement node) throws ASTVisitorException {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}

	@Override
	public void visit(VariableDefinition node) throws ASTVisitorException {
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        List<String> identifier = node.getIdentifiers();
        Type type = node.getType();

        for (String id : identifier) {
            if (symbolTable.innerScopeLookup(id) != null) {
                ASTUtils.error(node, "Variable " + id + " already defined  with type " + type + "in this func!");
            } else {
				System.out.println("Adding variable " + id + " with type " + type + "to symbol table");
            	symbolTable.put(id, new Info(id, type));
			}
        }
		// System.out.println(symbolTable.getSymbols());
	}

	@Override
	public void visit(LValueExpression node) throws ASTVisitorException {
		node.getExpression1().accept(this);
		node.getExpression2().accept(this);
	}

	@Override
	public void visit(HeaderDefinition node) throws ASTVisitorException {
        for (Definition d : node.getParameters()) {
            d.accept(this);
        }
		SymbolTable<Info> symbolTable = ASTUtils.getSafeSymbolTable(node);
        String identifier = node.getIdentifier();
        Type type = node.getType();

        if (symbolTable.innerScopeLookup(identifier) != null) {
            ASTUtils.error(node, "Function " + identifier + " already defined in this func scope!");
        } else {
			symbolTable.put(identifier, new Info(identifier, type));
			System.out.println("Adding function " + identifier + " with type " + type + " to symbol table");
		}
		
		// System.out.println(symbolTable.getSymbols());
	}

	@Override
	public void visit(ArrayType node) throws ASTVisitorException {
		// Nothing
	}

	@Override
	public void visit(FunctionCallExpression node) throws ASTVisitorException {
		node.getExpression().accept(this);
		for (Expression e: node.getExpressions()) {
            e.accept(this);
        }		
	}  
}
