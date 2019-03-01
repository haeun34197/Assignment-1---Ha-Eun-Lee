package main;

import java.io.IOException;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffmanDecodeTree {

	private HuffmanNode _root;
	
	public HuffmanDecodeTree(List<SymbolWithCodeLength> symbols_with_code_lengths) {

		// Create empty internal root node.
		_root = new InternalHuffmanNode();
		
		// Insert symbols into the tree
		for (int index = 0; index < symbols_with_code_lengths.size(); index++){
			int code_length = symbols_with_code_lengths.get(index).codeLength();
			int value = symbols_with_code_lengths.get(index).value();
			_root.insertSymbol(code_length, value);
		}
		
		// If all went well, your tree should be full
		assert _root.isFull();
	}

	public int decode(InputStreamBitSource bit_source) throws Exception {
		
		// Start at the root
		HuffmanNode n = _root;
		
		while (!n.isLeaf()) {
			// Get next bit and walk either left or right,
			int n_bit = bit_source.next(1);
			// updating n to be as appropriate
			if (n_bit != 0) {
				n = n.right();
			} else {
				n = n.left();
			}
		}
		
		// Return symbol at leaf
		return n.symbol();
	}

}
