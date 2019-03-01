package main;

public class InternalHuffmanNode implements HuffmanNode {

	private HuffmanNode _left;
	private HuffmanNode _right;
	
	//default constructor
	public InternalHuffmanNode() {
		_left = null;
		_right = null;
	}
	
	public InternalHuffmanNode(HuffmanNode left, HuffmanNode right) {
		_left = left;
		_right = right;
	}
	
	//For internal nodes, count is the sum of the count assoicated with children
	@Override
	public int count() {
		return _left.count() + _right.count();
	}

	//Returns false for internal nodes
	@Override
	public boolean isLeaf() {
		return false;
	}

	//Not applicable to internal node--calling on internal node should throw exception
	@Override
	public int symbol() {
		throw new RuntimeException("Symbol function is not applicable to internal nodes");
	}

	//Return distance between this node and the farthest leaf underneath it
	@Override
	public int height() {
		int max_h = Math.max(_left.height(), _right.height());
		return max_h + 1;
	}

	@Override
	public boolean isFull() {
	       if (_left == null || _right == null){ //true only if both children are not null
	            return false;
	        } else {
	            if (_left.isFull() && _right.isFull()) { //true only if children are also full
	                return true;
	            } else {
	                return false;
	            }
	        }
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		if (length < 0) return false;
		
       if (_left != null) { //first try to go left
            if (!_left.isFull()){ //if the left is not full
                return _left.insertSymbol(length - 1, symbol); //recursively handle, reducing length by 1
            } else if (_right != null){ //if right node exists
                if (!_right.isFull()) { //and it is not full
                    return _right.insertSymbol(length - 1, symbol);
                } else {
                    return false; //the case when both left and right children are full--failure condition
                }
            } else { //the case where left is full but right node does not exist
                if (length != 1){ //if the length is not one, create another internal huffman node, and then handle recursively
                    _right = new InternalHuffmanNode();
                    return _right.insertSymbol(length - 1, symbol);
                } else { //if the length is one
                    _right = new LeafHuffmanNode(symbol, 0); //point new leaf mode with symbol should be created and attached
                    return true;
                }
            }
        } else { //the case where you cannot go left
            if (length != 1) { 
                _left = new InternalHuffmanNode();
                return _left.insertSymbol(length - 1, symbol);
            } else {
                _left = new LeafHuffmanNode(symbol, 0); // *
                return true;
            }
        }
    }

	//Return left child
	@Override
	public HuffmanNode left() {
		return _left;
	}

	//Return right child
	@Override
	public HuffmanNode right() {
		return _right;
	}

}
