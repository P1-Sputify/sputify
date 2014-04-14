package server;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that imitates an arraylist. the user can input and remove or get
 * information from anywhere in the list using their position in the list
 * (index) or search for a specific element.
 * 
 * @author Sebastian Aspegren 11/02/14
 * 
 * 
 */
public class ArrayList<E> implements List<E> {
	private E[] elements;
	private int size;

	/**
	 * A Method that increases the size of the list to the double of the
	 * current.
	 */
	private void grow() {
		E[] temp = (E[]) new Object[size * 2];
		for (int i = 0; i < size; i++) {
			temp[i] = elements[i];
		}
		elements = temp;
	}

	/**
	 * A constructor that constructs a list wish space for 20 elements.
	 */
	public ArrayList() {
		this(20);
	}

	/**
	 * A constructor that constructs a list with space for the given capacity
	 * 
	 * @param initialCapacity
	 *            the amount of elements that can fit in the list
	 */
	public ArrayList(int initialCapacity) {
		initialCapacity = Math.max(1, initialCapacity);
		elements = (E[]) new Object[initialCapacity];
	}

	/**
	 * A Method that adds a given element at the given index in the list
	 * 
	 * @param index
	 *            the index where we want the element to be
	 * @param element
	 *            the element we want to add to the list
	 */
	public void add(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (size == elements.length)
			grow();
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		size++;
	}

	/**
	 * A Method that adds an element at the end of the list
	 * 
	 * @param element
	 *            the element we want to add to the list
	 */
	public void add(E element) {
		add(size, element);
	}

	/**
	 * A Method that adds the given element first in the list
	 * 
	 * @param element
	 *            the element we wish to add to the list
	 */
	public void addFirst(E element) {
		if (size == elements.length)
			grow();
		for (int i = size; i > 0; i--) {
			elements[i] = elements[i - 1];
		}
		elements[0] = element;
		size++;
	}

	/**
	 * A Method that adds an element to the end of the list
	 * 
	 * @param element
	 *            the element we wish to add to the list
	 */
	public void addLast(E element) {
		if (size == elements.length)
			grow();

		elements[size] = element;
		size++;
	}

	/**
	 * A Method that removed an element at the specified index
	 * 
	 * @param index
	 *            the index of the element we wish to remove
	 */
	public E remove(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		E element = elements[index];
		size--;
		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		return element;
	}

	/**
	 * A method that removes the first element in the list and returns it
	 * 
	 * @return the element which was removed
	 * 
	 */
	public E removeFirst() {
		E element = elements[0];
		size--;
		for (int i = 0; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		return element;
	}

	/**
	 * A method that removes the last element in the list and returns it
	 * 
	 * @return the element which was removed
	 */
	public E removeLast() {

		return remove(size() - 1);
	}

	/**
	 * A method that clears the list of any elements
	 */
	public void clear() {

		for (int i = size; i > 0; i--) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * A method that returns the value of the element at a given index
	 * 
	 * @param index
	 *            the index of the element we wish to know
	 * @return the element we searched for
	 */
	public E get(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		return elements[index];
	}

	/**
	 * A method that sets the value of an element at a given index with the
	 * given element
	 * 
	 * @param index
	 *            the index where the element is we want to replace
	 * @param element
	 *            the element we wish to replace it with
	 * @return returns the removed element
	 */
	public E set(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		E elem = elements[index];
		elements[index] = element;
		return elem;
	}

	/**
	 * A method that locates the index of an element
	 * 
	 * @param element
	 *            the element we are searching for
	 * @return the index of the element or -1 if it was not found
	 */
	public int indexOf(E element) {

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(element))
				return i;
		}

		return -1;
	}

	/**
	 * A method that returns the index of an element
	 * 
	 * @param startIndex
	 *            the index we wish to start the search from
	 * @param element
	 *            the element we are looking for
	 * @return the index of the element or -1 if it was not found
	 */
	public int indexOf(int startIndex, E element) {
		if (startIndex < 0 || startIndex > size)
			throw new IndexOutOfBoundsException();
		for (int i = startIndex; i < size; i++) {
			if (elements[i].equals(element))
				return i;
		}
		return -1;
	}

	/**
	 * A method that returns the current amount of elements in the list
	 * 
	 * @return the variable size which holds the current amount of elements in
	 *         the list
	 */
	public int size() {
		return this.size;
	}

	/**
	 * A method that creates and returns a neat string
	 * 
	 * @return the new string
	 */
	public String toString() {
		StringBuilder res = new StringBuilder("[ ");
		for (int i = 0; i < size; i++) {
			res.append(elements[i]);
			if (i < size - 1)
				res.append("; ");
		}
		res.append(" ]");
		return res.toString();
	}

	/**
	 * A method that returns an iterator
	 * 
	 * @return the iterator
	 */
	public Iterator<E> iterator() {
		return new Iter();

	}

	/**
	 * An inner class for the iterator
	 * 
	 * @author Sebastian Aspegren 11/02/14
	 * 
	 */
	private class Iter implements Iterator<E> {
		private int index = 0;

		/**
		 * A method that checks if there are more elements in the list and
		 * returns a boolean
		 * 
		 * @return returns true if there is more elements in the list, otherwise
		 *         false.
		 */
		public boolean hasNext() {
			return index < size;
		}

		/**
		 * A method that returns the next element in the list
		 * 
		 * @return the next element in the list
		 */
		public E next() {
			if (index == size)
				throw new NoSuchElementException();
			return elements[index++];
		}

		/**
		 * A method which should not be used. It is not supported in this list.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
