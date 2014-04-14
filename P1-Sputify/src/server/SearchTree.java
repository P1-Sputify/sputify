package server;

import java.util.Iterator;

/**
 * the interface for a searchtree
 * 
 * @author Unkown
 * 
 * @param <K>
 * @param <V>
 */
public interface SearchTree<K, V> {
	/**
	 * places an object in a tree with the provided key and value
	 * 
	 * @param key
	 *            the key of the object
	 * @param value
	 *            the value the object holds
	 */
	public void put(K key, V value);

	/**
	 * a method that removed the object with the specified key
	 * 
	 * @param key
	 *            the key of what we wish to remove
	 * @return the removed object
	 */
	public V remove(K key);

	/**
	 * a method that gets the object with the specified key
	 * 
	 * @param key
	 *            the key of what we wish to retrieve
	 * @return the object we searched for
	 */
	public V get(K key);

	/**
	 * a method that checks if an object with the specified key exist
	 * 
	 * @param key
	 *            the key of the object we wish to know if exists
	 * @return true or false depending if the element was there
	 */
	public boolean contains(K key);

	/**
	 * a method that returns the height of the tree
	 * 
	 * @return the height of the tree
	 */
	public int height();

	/**
	 * a method that makes an iterator to go through the tree with
	 * 
	 * @return the new iterator
	 */
	public Iterator<V> iterator();

	/**
	 * a method that returns the amount of elements in the tree
	 * 
	 * @return the size of the tree
	 */
	public int size();

	/**
	 * a method that returns a list of all the keys in the tree
	 * 
	 * @return the list with all the keys
	 */
	public List<K> keys();

	/**
	 * a method that returns a list of all the values of the elements in the
	 * tree
	 * 
	 * @return the values of the objects in the tree
	 */
	public List<V> values();

	/**
	 * finds and returns the first element in the tree
	 * 
	 * @return the first element in the tree
	 */
	public V first();

	/**
	 * a method that finds and returns the last element in the tree
	 * 
	 * @return the last element in the tree
	 */
	public V last();
}
