package server;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * a class representing a binary search tree. It uses binary search tree nodes
 * to find the requested object.
 * 
 * @author Sebastian Aspegren
 * 
 * @param <K>
 * @param <V>
 */
public class BinarySearchTree<K, V> implements SearchTree<K, V>, Iterable<V> {
	private Comparator<K> comparator;
	private BSTNode<K, V> tree;
	private int size;

	/**
	 * The constructor for the binary search tree without parameters. Creates a
	 * new comparator.
	 */
	public BinarySearchTree() {
		comparator = new Comp();
	}

	/**
	 * A constructor for the binary search tree that uses the comparator
	 * provided.
	 * 
	 * @param comp
	 *            the comparator we shall use in the comparisons
	 */
	public BinarySearchTree(Comparator<K> comp) {
		comparator = comp;
	}

	/**
	 * a method to get the top of the tree
	 * 
	 * @return the top of the tree
	 */
	public BSTNode<K, V> root() {
		return tree;
	}

	/**
	 * A method that attempts to find the object using a specified key
	 * 
	 * @param key
	 *            the key of that we are searching for.
	 * 
	 * @return the value of the node
	 */
	public V get(K key) {
		BSTNode<K, V> node = find(key);
		if (node != null)
			return node.value;
		return null;
	}

	/**
	 * A method to put a key and a value in the tree
	 * 
	 * @param key
	 *            the key we wish to insert into the tree
	 * 
	 * @param value
	 *            the value we wish to insert into the tree
	 */
	public void put(K key, V value) {
		tree = put(tree, key, value);
	}

	/**
	 * A method to remove an object at the specified key
	 * 
	 * @param key
	 *            the key of what we wish to remove
	 * 
	 * @return the value we removed
	 */
	public V remove(K key) {
		V value = get(key);
		if (value != null) {
			tree = remove(tree, key);
			size--;
		}
		return value;
	}

	/**
	 * A method that checks if the tree has the specified key
	 * 
	 * @param key
	 *            the key we are checking if exists
	 * 
	 * @return true if the key was found else false
	 * 
	 */
	public boolean contains(K key) {
		return find(key) != null;
	}

	/**
	 * a method that checks how tall (or deep) the tree is
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return height(tree);
	}

	/**
	 * a method that returns an iterator
	 * 
	 * @return a new iterator
	 */
	public Iterator<V> iterator() {
		return new Iter();
	}

	/**
	 * a method that searches for the specified key
	 * 
	 * @param key
	 *            the key we are looking for
	 * 
	 * @return the node we searched for
	 */
	private BSTNode<K, V> find(K key) {
		int res;
		BSTNode<K, V> node = tree;
		while ((node != null)
				&& ((res = comparator.compare(key, node.key)) != 0)) {
			if (res < 0)
				node = node.left;
			else
				node = node.right;
		}
		return node;
	}

	private BSTNode<K, V> put(BSTNode<K, V> node, K key, V value) {
		if (node == null) {
			node = new BSTNode<K, V>(key, value, null, null);
			size++;
		} else {
			if (comparator.compare(key, node.key) < 0) {
				node.left = put(node.left, key, value);
			} else if (comparator.compare(key, node.key) > 0) {
				node.right = put(node.right, key, value);
			}
		}
		return node;
	}

	private BSTNode<K, V> remove(BSTNode<K, V> node, K key) {
		int compare = comparator.compare(key, node.key);
		if (compare == 0) {
			if (node.left == null && node.right == null)
				node = null;
			else if (node.left != null && node.right == null)
				node = node.left;
			else if (node.left == null && node.right != null)
				node = node.right;
			else {
				BSTNode<K, V> min = getMin(node.right);
				min.right = remove(node.right, min.key);
				min.left = node.left;
				node = min;
			}
		} else if (compare < 0) {
			node.left = remove(node.left, key);
		} else {
			node.right = remove(node.right, key);
		}
		return node;
	}

	private BSTNode<K, V> getMin(BSTNode<K, V> node) {
		while (node.left != null)
			node = node.left;
		return node;
	}

	private int height(BSTNode<K, V> node) {
		if (node == null)
			return -1;
		return 1 + Math.max(height(node.left), height(node.right));
	}

	/**
	 * a method that returns the size of the tree
	 * 
	 * @return the size of the tree
	 */
	public int size() {
		return size;
	}

	/**
	 * a method that returns the size of the tree even if its null
	 * 
	 * @return the size of the tree
	 */
	public int size1() {
		if (tree == null)
			return 0;
		else
			return tree.size();
	}

	/**
	 * a method that calculates the size of the tree by adding one for each node
	 * found
	 * 
	 * @return the size of the tree
	 */
	public int size2() {

		return size2(tree);
	}

	private int size2(BSTNode<K, V> node) {
		if (node == null)
			return 0;
		return 1 + size2(node.left) + size2(node.right);
	}

	/**
	 * a method that prints all nodes key and value
	 */
	public void printPreorder() {
		printPreorder(tree);
	}

	private void printPreorder(BSTNode<K, V> node) {
		if (node != null) {
			System.out.println(node.key + ", " + node.value);
			printPreorder(node.left);
			printPreorder(node.right);
		}
	}

	/**
	 * a method that prints all nodes keys and values
	 */
	public void printPostorder() {
		printPostorder(tree);
	}

	private void printPostorder(BSTNode<K, V> node) {
		if (node != null) {
			printPostorder(node.left);
			printPostorder(node.right);
			System.out.println(node.key + ", " + node.value);
		}
	}

	/**
	 * a method that returns a list containing all the keys of all the nodes
	 * 
	 * @return the list with the keys
	 */
	public List<K> keys() {
		ArrayList<K> list = new ArrayList<K>();
		keys(tree, list);
		return list;
	}

	private void keys(BSTNode<K, V> node, ArrayList<K> list) {
		if (node != null) {
			keys(node.left, list);
			list.add(list.size(), node.key);
			keys(node.right, list);
		}
	}

	private void values(BSTNode<K, V> node, LinkedList<V> list) {
		if (node != null) {
			values(node.left, list);
			list.add(list.size(), node.value);
			values(node.right, list);
		}
	}

	private void traverse(BSTNode<K, V> node, Action<V> action) {
		if (node != null) {
			traverse(node.left, action);
			action.action(node.value);
			traverse(node.right, action);
		}
	}

	/**
	 * a method that traverses all the nodes in the tree
	 * 
	 * @param action
	 *            an interface used to provide a value
	 */
	public void traverse(Action<V> action) {
		traverse(tree, action);
	}

	/**
	 * a method that returns a list with all the values in the tree
	 * 
	 * @return the list with all the values
	 */
	public List<V> values() {
		LinkedList<V> list = new LinkedList<V>();
		values(tree, list);
		return list;
	}

	/**
	 * returns the node farthest to the left in the tree
	 * 
	 * @return the nodes value
	 */
	public V first() {
		BSTNode<K, V> node = tree;
		if (node == null)
			return null;
		while (node.left != null) {
			node = node.left;
		}
		return node.value;
	}

	/**
	 * a method that returns the value of the node farthest to the right
	 * 
	 * @return the value of the node farthest to the right
	 */
	public V last() {
		BSTNode<K, V> node = tree;
		if (node == null)
			return null;
		while (node.right != null) {
			node = node.right;
		}
		return node.value;
	}

	/**
	 * a method that prints all the nodes values and keys in the tree
	 */
	public void print() {
		print(tree);
	}

	private void print(BSTNode<K, V> node) {
		if (node != null) {
			print(node.left);
			System.out.println(node.key + ", " + node.value);
			print(node.right);
		}
	}

	/**
	 * a comparator class used to compare objects
	 * 
	 * @author Sebastian Aspegren
	 * 
	 */
	private class Comp implements Comparator<K> {
		/**
		 * compares a key with a second key
		 * 
		 * @return a method that compares the keys
		 */
		public int compare(K key1, K key2) {
			Comparable<K> k1 = (Comparable<K>) key1;
			return k1.compareTo(key2);
		}
	}

	/**
	 * a class that works as an iterator
	 * 
	 * @author Sebastian aspegren
	 * 
	 */
	private class Iter implements Iterator<V> {
		ArrayList<V> list = new ArrayList<V>();
		int index = -1;

		/**
		 * a constructor for the interator class using the tree
		 */
		public Iter() {
			inOrder(tree);
		}

		private void inOrder(BSTNode<K, V> node) {
			if (node != null) {
				inOrder(node.left);
				list.add(node.value);
				inOrder(node.right);
			}
		}

		/**
		 * a method that checks if there is another item after the current
		 * 
		 * @return true or false depending if there is an object after the
		 *         current one.
		 */
		public boolean hasNext() {
			return index < list.size() - 1;
		}

		/**
		 * a method that returns the object after the one we are currently on
		 * 
		 * @return the object after this object
		 */
		public V next() {
			if (!hasNext())
				throw new NoSuchElementException();
			index++;
			return list.get(index);
		}

		/**
		 * an unsupported method that is not used.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
