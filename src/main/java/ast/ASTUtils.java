package ast;

import symbol.Info;
import symbol.SymbolTable;
import threeaddr.GoToInstr;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Type;

public class ASTUtils {

	public static final String SYMTABLE_PROPERTY = "SYMTABLE_PROPERTY";
	public static final String IS_BOOLEAN_EXPR_PROPERTY = "IS_BOOLEAN_EXPR_PROPERTY";
	public static final String TYPE_PROPERTY = "TYPE_PROPERTY";
	public static final String NEXT_LIST_PROPERTY = "NEXT_LIST_PROPERTY";
	public static final String TRUE_LIST_PROPERTY = "TRUE_LIST_PROPERTY";
	public static final String FALSE_LIST_PROPERTY = "FALSE_LIST_PROPERTY";

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

	public static void setBooleanExpression(Condition node, boolean value) {
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
	
	@SuppressWarnings("unchecked")
	public static List<threeaddr.GoToInstr> getTrueList(Expression node){
		List<threeaddr.GoToInstr> l =(List<threeaddr.GoToInstr>) node.getProperty(TRUE_LIST_PROPERTY);
		if (l == null) {
			l = new ArrayList<threeaddr.GoToInstr>();
			node.setProperty(TRUE_LIST_PROPERTY, l);
		}
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public static List<threeaddr.GoToInstr> getTrueListCondition(Condition node){
		List<threeaddr.GoToInstr> l =(List<threeaddr.GoToInstr>) node.getProperty(TRUE_LIST_PROPERTY);
		if (l == null) {
			l = new ArrayList<threeaddr.GoToInstr>();
			node.setProperty(TRUE_LIST_PROPERTY, l);
		}
		return l;
	}
	
	public static void setTrueList(Expression node, List<threeaddr.GoToInstr> list) {
		node.setProperty(TRUE_LIST_PROPERTY, list);
	}
	
	public static void setTrueListCondition(Condition node, List<threeaddr.GoToInstr> list) {
		node.setProperty(TRUE_LIST_PROPERTY, list);
	}
	
	@SuppressWarnings("unchecked")
	public static List<threeaddr.GoToInstr> getFalseList(Expression node){
		List<threeaddr.GoToInstr> l =(List<threeaddr.GoToInstr>) node.getProperty(FALSE_LIST_PROPERTY);
		if (l == null) {
			l = new ArrayList<threeaddr.GoToInstr>();
			node.setProperty(FALSE_LIST_PROPERTY, l);
		}
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public static List<threeaddr.GoToInstr> getFalseListCondition(Condition node){
		List<threeaddr.GoToInstr> l =(List<threeaddr.GoToInstr>) node.getProperty(FALSE_LIST_PROPERTY);
		if (l == null) {
			l = new ArrayList<threeaddr.GoToInstr>();
			node.setProperty(FALSE_LIST_PROPERTY, l);
		}
		return l;
	}
	
	public static void setFalseList(Expression node, List<threeaddr.GoToInstr> list) {
		node.setProperty(FALSE_LIST_PROPERTY, list);
	}
	
	public static void setFalseListCondition(Condition node, List<threeaddr.GoToInstr> list) {
		node.setProperty(FALSE_LIST_PROPERTY, list);
	}
	
	@SuppressWarnings("unchecked")
	public static List<GoToInstr> getNextList(Statement node){
		List<GoToInstr> l =(List<GoToInstr>) node.getProperty(NEXT_LIST_PROPERTY);
		if (l == null) {
			l = new ArrayList<GoToInstr>();
			node.setProperty(NEXT_LIST_PROPERTY, l);
		}
		return l;
	}
	
	public static void setNextList(Statement node, List<threeaddr.GoToInstr> list) {
		node.setProperty(NEXT_LIST_PROPERTY, list);
	}

	public static void error(ASTNode node, String message) throws ASTVisitorException {
		throw new ASTVisitorException(node.getLine() + ":" + node.getColumn() + ": " + message);
	}
}