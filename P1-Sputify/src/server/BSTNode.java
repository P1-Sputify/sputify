package server;

/**
 * a class representing a node in a binary search tree
 * 
 * @author Sebastian Aspegren
 * 
 * @param <K>
 * @param <V>
 */
class BSTNode<K, V> {
	K key;
	V value;
	BSTNode<K, V> left;
	BSTNode<K, V> right;

	/**
	 * the constructor for a node. it has a key, a value and nodes beside itself
	 * 
	 * @param key
	 *            the key for the node
	 * @param value
	 *            the value the node holds
	 * @param left
	 *            the node to the left of this one
	 * @param right
	 *            the node to the right of this one
	 */
	public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * a method that calculates the height of the tree by looking left and right
	 * until nothing is there
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		int leftH = -1, rightH = -1;
		if (left != null)
			leftH = left.height();
		if (right != null)
			rightH = right.height();
		return 1 + Math.max(leftH, rightH);
	}

	/**
	 * a method that calculates the size of the tree by looking left and right
	 * of itself
	 * 
	 * @return the amount of nodes in the tree
	 */
	public int size() {
		int leftS = 0, rightS = 0;
		if (left != null)
			leftS = left.size();
		if (right != null)
			rightS = right.size();
		return 1 + leftS + rightS;
	}

	/**
	 * a method that prints the key and the value of all the nodes.
	 */
	public void print() {
		if (left != null)
			left.print();
		System.out.println(key + ": " + value);
		if (right != null)
			right.print();
	}
}
