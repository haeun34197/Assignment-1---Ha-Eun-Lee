package main;

public class LeafHuffmanNode implements HuffmanNode {

	private int _count;
	private int _symbol;
	
	public LeafHuffmanNode(int symbol, int count) { //initialize values
		_symbol = symbol;
		_count = count;
	}
	
	//For leaf nodes, count is the count assoicated with the symbol
	//represented by the leaf
	@Override
	public int count() {
		return _count;
	}

	//Returns true since leaf node
	@Override
	public boolean isLeaf() {
		return true;
	}

	//Returns symbol associated with leaf node
	@Override
	public int symbol() {
		return _symbol;
	}

	//By definition, 0 for leaf nodes
	@Override
	public int height() {
		return 0;
	}

	//True by definition for leaf nodes
	@Override
	public boolean isFull() {
		return true;
	}

	//Calling this on a leaf node should be an exception
	@Override
	public boolean insertSymbol(int length, int symbol) {
		throw new RuntimeException("Insert symbol function is not applicable to leaf nodes");
	}

	//Calling this on a leaf node should be an exception
	@Override
	public HuffmanNode left() {
		throw new RuntimeException("Leaf nodes by definition has no children");
	}
	
	//Calling this on a leaf node should be an exception
	@Override
	public HuffmanNode right() {
		throw new RuntimeException("Leaf nodes by definition has no children");
	}

}
