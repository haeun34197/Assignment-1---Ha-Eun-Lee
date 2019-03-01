package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {
		
	private Map<Integer, String> _code_map;
	
	public HuffmanEncoder(int[] symbols, int[] symbol_counts) {
		assert symbols.length == symbol_counts.length;
		
		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes
		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();
		
		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.
		for(int index = 0; index <= 255; index++) node_list.add(new LeafHuffmanNode(symbols[index], symbol_counts[index]));

		// Sort the leaf nodes
		node_list.sort(null);

		// While you still have more than one node in your list...
		while(node_list.size() > 1) {
			// Remove the two nodes associated with the smallest counts
			HuffmanNode node1 = node_list.remove(0);
			HuffmanNode node2 = node_list.remove(0);
			
			// Create a new internal node with those two nodes as children.
			InternalHuffmanNode newNode = new InternalHuffmanNode(node1,node2);
			
			// Add the new internal node back into the list
			node_list.add(newNode);
			
			// Resort
			node_list.sort(null);
		}

		assert node_list.size()==1;
		// Create a temporary empty mapping between symbol values and their code strings
		Map<Integer, String> cmap = new HashMap<Integer, String>(); 

		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.
		treeTraversal(cmap, node_list.get(0), "", 1);
		
		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in cmap).
		for(int index = 0; index <= 255; index++) sym_with_length.add(new SymbolWithCodeLength(index, cmap.get(index).length()));
		
		// Sort sym_with_length
		sym_with_length.sort(null);

		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor
		InternalHuffmanNode canonical_root = new InternalHuffmanNode();
		for(int index = 0; index <= 255; index++) canonical_root.insertSymbol(sym_with_length.get(index).codeLength(), sym_with_length.get(index).value());

		// If all went well, tree should be full.
		assert canonical_root.isFull();
		
		// Create code map that encoder will use for encoding
		_code_map = new HashMap<Integer, String>();
		
		// Walk down canonical tree forming code strings as you did before and
		// insert into map.
		treeTraversal(_code_map, canonical_root, "", 0);
	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}
	
	public void treeTraversal(Map<Integer, String> map, HuffmanNode root, String code_string, int tf) {
		if(!root.isLeaf()) {
			if(root.left() != null) treeTraversal(map, root.left(),code_string + "0", tf);
			if(root.right() != null) treeTraversal(map, root.right(),code_string+"1", tf);
			return;
		}
		
		if(tf == 1) {
			map.put(root.symbol(), code_string);
			return;
		}
		
		_code_map.put(root.symbol(), code_string);
		
	}

}
