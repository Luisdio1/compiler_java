package symbol;

import java.util.List;

/**
 * Symbol table
 *
 * @param <E> The type of objects that can be stored in the symbol table.
 */
public interface SymbolTable<E extends Info> {

	/**
	 * Lookup a symbol in the symbol table.
	 *
	 * @param s The name of the symbol
	 * @return The entry for the symbol or null if not found.
	 */
	public E lookup(String s);

	/**
	 * Lookup a symbol in the symbol table but only in the innermost scope.
	 *
	 * @param s The name of the symbol
	 * @return The entry for the symbol or null if not found.
	 */
	public E innerScopeLookup(String s);

	/**
	 * Add a new symbol table entry.
	 *
	 * @param s      The name of the new entry
	 * @param symbol The actual entry
	 */
	public void put(String s, E symbol);

	/**
	 * Delete an entry from the symbol table.
	 * 
	 * @param s The name of the symbol
	 * @return The entry for the symbol or null if not found.
	 */
	public E remove(String s);

	/**
	 * Get all the symbols available in this symbol table.
	 *
	 * @return A collection of symbols.
	 */
	public List<E> getSymbols();

	/**
	 * Get all the symbols available in this symbol table but only in the innermost
	 * scope.
	 *
	 * @return A collection of symbols.
	 */
	public List<E> getInnerScopeSymbols();

}
