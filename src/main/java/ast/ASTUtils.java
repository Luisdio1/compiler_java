package ast;

import symbol.Info;
import symbol.SymbolTable;

public class ASTUtils {

	public static final String SYMTABLE_PROPERTY = "SYMTABLE_PROPERTY";
	public static final String IS_BOOLEAN_EXPR_PROPERTY = "IS_BOOLEAN_EXPR_PROPERTY";
	public static final String TYPE_PROPERTY = "TYPE_PROPERTY";

	private ASTUtils() {
	}

	@SuppressWarnings("unchecked")
	public static SymbolTable<Info> getSymbolTable(ASTNode node) {
		return (SymbolTable<Info>) node.getProperty(SYMTABLE_PROPERTY);
	}

	@SuppressWarnings("unchecked")
	public static SymbolTable<Info> getSafeSymbolTable(ASTNode node) throws ASTVisitorException {
		SymbolTable<Info> symTable = (SymbolTable<Info>) node.getProperty(SYMTABLE_PROPERTY);
		if (symTable == null) {
			ASTUtils.error(node, "Symbol table not found.");
		}
		return symTable;
	}

	public static void setSymbolTable(ASTNode node, SymbolTable<Info> symbolTable) {
		node.setProperty(SYMTABLE_PROPERTY, symbolTable);
	}

	public static boolean isBooleanExpression(Expression node) {
		Boolean b = (Boolean) node.getProperty(IS_BOOLEAN_EXPR_PROPERTY);
		if (b == null) {
			return false;
		}
		return b;
	}

	public static void setBooleanExpression(Expression node, boolean value) {
		node.setProperty(IS_BOOLEAN_EXPR_PROPERTY, value);
	}

	public static Type getType(ASTNode node) {
		return (Type) node.getProperty(TYPE_PROPERTY);
	}

	public static Type getSafeType(ASTNode node) throws ASTVisitorException {
		Type type = (Type) node.getProperty(TYPE_PROPERTY);
		if (type == null) {
			ASTUtils.error(node, "Type not found.");
		}
		return type;
	}

	public static void setType(ASTNode node, Type type) {
		node.setProperty(TYPE_PROPERTY, type);
	}

	public static void error(ASTNode node, String message) throws ASTVisitorException {
		throw new ASTVisitorException(node.getLine() + ":" + node.getColumn() + ": " + message);
	}


}