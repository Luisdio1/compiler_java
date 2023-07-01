package symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashSymbolTable<E extends Info> implements SymbolTable<E> {

	private final Map<String, E> table;
	private final SymbolTable<E> nextSymbolTable;

	public HashSymbolTable() {
		this(null);
	}

	public HashSymbolTable(SymbolTable<E> nextSymTable) {
		this.table = new HashMap<String, E>();
		this.nextSymbolTable = nextSymTable;
	}

	@Override
	public E lookup(String s) {
		E r = table.get(s);
		if (r != null) {
			return r;
		}
		if (nextSymbolTable != null) {
			return nextSymbolTable.lookup(s);
		}
		return null;
	}

	@Override
	public E innerScopeLookup(String s) {
		return table.get(s);
	}

	@Override
	public void put(String s, E symbol) {
		table.put(s, symbol);
	}

	@Override
	public E remove(String s) {
		E oldValue = table.remove(s);
		if (oldValue != null) { 
			return oldValue;
		}
		if (nextSymbolTable != null) {
			return nextSymbolTable.remove(s);
		}
		return null;
	}
	
	@Override
	public List<E> getSymbols() {
		List<E> symbols = new ArrayList<E>();
		symbols.addAll(table.values());
		if (nextSymbolTable != null) {
			symbols.addAll(nextSymbolTable.getSymbols());
		}
		return symbols;
	}

	@Override
	public List<E> getInnerScopeSymbols() {
		List<E> symbols = new ArrayList<E>();
		symbols.addAll(table.values());
		return symbols;
	}



}
