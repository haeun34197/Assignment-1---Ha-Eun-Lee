package main;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {
	
	// Instance fields should be declared here.
	private int _symbol_value;
	private int _length_code;
	
	// Constructor
	public SymbolWithCodeLength(int value, int code_length) {
		_symbol_value = value;
		_length_code = code_length;
	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		return this._length_code;
	}

	// value() returns the symbol value of the symbol
	public int value() {
		return this._symbol_value;
	}

	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	public int compareTo(SymbolWithCodeLength other) {
		int this_length = this._length_code;
		int other_length = other.codeLength();
		
		// Compare first by code length
		if (this_length < other_length) return -1;
		if (this_length > other_length) return 1;
		
		// Then by symbol value
		if (this._symbol_value < other.value()) return -1;
		if (this._symbol_value > other.value()) return 1;
		
		// If everything is equal
		return 0;
	}
}
