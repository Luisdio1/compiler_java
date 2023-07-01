package ast;

import symbol.Info;
import symbol.SymbolTable;

public class ASTUtils {

	public static final String SYMTABLE_PROPERTY = "SYMTABLE_PROPERTY";

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

	public static void error(ASTNode node, String message) throws ASTVisitorException {
		throw new ASTVisitorException(node.getLine() + ":" + node.getColumn() + ": " + message);
	}

}