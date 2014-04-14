package server;

import java.util.Iterator;

/**
 * 
 * @author Sebastian Aspegren 10/02/14
 * 
 * @param <E>
 *            What type of list it is
 */
public class LinkedList<E> implements List<E>, Iterable<E> {
	private ListNode<E> list = null;

	/**
	 * Method that finds an object at the specific index
	 * 
	 * @param index
	 *            the index we are looking for
	 * @return the object at the index
	 */
	private ListNode<E> locate(int index) {
		ListNode<E> node = list;
		for (int i = 0; i < index; i++)
			node = node.getNext();
		return node;
	}

	/**
	 * Method that calculates the size of the list
	 */
	public int size() {
		int n = 0;
		ListNode<E> node = list;
		while (node != null) {
			node = node.getNext();
			n++;
		}
		return n;
	}

	/**
	 * Method that fetches the object at a specific index
	 * 
	 * @param index
	 *            the index of the object
	 * 
	 * @return the object we fetched
	 */
	public E get(int index) {
		if ((index < 0) || (index >= size()))
			throw new IndexOutOfBoundsException("size=" + size() + ", index="
					+ index);

		ListNode<E> node = locate(index);
		return node.getData();
	}

	/**
	 * Method that sets the data for a specific index
	 * 
	 * @param index
	 *            the index of the object we wish to change
	 * @param data
	 *            the data we wish the object to hold
	 * 
	 * @return the previous data
	 */
	public E set(int index, E data) {
		if ((index < 0) || (index >= size()))
			throw new IndexOutOfBoundsException("size=" + size() + ", index="
					+ index);

		E node = locate(index).getData();

		locate(index).setData(data);

		return node;
	}

	/**
	 * Method that adds an object to the list
	 * 
	 * @param data
	 *            the data we wish to add to the list
	 */
	public void add(E data) {
		if (list == null)
			list = new ListNode<E>(data, list);
		else
			locate(size() - 1).setNext(new ListNode<E>(data, null));
	}

	/**
	 * Method that adds an object at the start of the list
	 * 
	 * @param data
	 *            the data we want the object to have
	 */
	public void addFirst(E data) {
		list = new ListNode<E>(data, list);

	}

	/**
	 * Method that adds an object at the end of the list
	 * 
	 * @param data
	 *            the data we wish to add
	 */
	public void addLast(E data) {
		add(size(), data);

	}

	/**
	 * Method that adds an object to the list at a specified index
	 * 
	 * @param index
	 *            the index where we want it
	 * @param data
	 *            the data we wish to add
	 */
	public void add(int index, E data) {
		if ((index < 0) || (index > size()))
			throw new IndexOutOfBoundsException("size=" + size() + ", index="
					+ index);

		if (index == 0)
			list = new ListNode<E>(data, list);
		else {
			ListNode<E> node = locate(index - 1);
			ListNode<E> newNode = new ListNode<E>(data, node.getNext());
			node.setNext(newNode);
		}
	}

	/**
	 * Method that removes the object first in the list
	 * 
	 * @return the value of the object we removed
	 */
	public E removeFirst() {
		E node = list.getData();
		list = list.getNext();
		return node;
	}

	/**
	 * Method that removes the last object in the list
	 * 
	 * @return the value of the object we removed
	 */
	public E removeLast() {
		// if (size() == 1) {
		// return removeFirst();
		//
		// } else {
		// E node = list.getNext().getData();
		//
		// locate(size() - 1).setData(null);
		// locate(size() - 2).setNext(null);
		//
		// return node;
		// }
		return (remove(size() - 1));
	}

	/**
	 * Method that removes an object at a specified index
	 * 
	 * @param index
	 *            the index of the object we want to remove
	 * @return the object we removed
	 */
	public E remove(int index) {
		if ((index < 0) || (index >= size()))
			throw new IndexOutOfBoundsException("size=" + size() + ", index="
					+ index);

		E res;
		if (index == 0) {
			res = list.getData();
			list = list.getNext();
		} else {
			ListNode<E> node = locate(index - 1);
			res = node.getNext().getData();
			node.setNext(node.getNext().getNext());
		}
		return res;
	}

	/**
	 * Method that clears the list and sets it to null
	 */
	public void clear() {
		for (int i = size(); i > 0; i--) {
			locate(i - 1).setData(null);
			locate(i - 1).setNext(null);

		}
		list = null;
	}

	/**
	 * Method that checks where the data of an object is located
	 * 
	 * @param data
	 *            The data we wish to know the index of
	 * @return either the index of -1 if it was not found
	 */
	public int indexOf(E data) {
		int res = 0;
		ListNode<E> node = list;
		while (node != null) {
			if (data.equals(node.getData())) {
				return res;
			}
			node = node.getNext();
			res++;
		}
		return -1;
	}

	/**
	 * Method that searches for the object with the specified data from a
	 * specified index to the end
	 * 
	 * @param startIndex
	 *            the index we wish to start the search from
	 * @param data
	 *            the data we are looking for
	 * @return either the index of -1 if it was not found
	 */
	public int indexOf(int startIndex, E data) {
		if ((startIndex < 0) || (startIndex >= size()))
			throw new IndexOutOfBoundsException();

		ListNode<E> node = locate(startIndex);
		for (int i = startIndex; i < size(); i++) {
			if (data.equals(node.getData())) {
				return i;
			}
			node = node.getNext();
		}
		return -1;
	}

	/**
	 * Method that returns an iterator
	 * 
	 * @return a new iterator
	 */
	public Iterator<E> iterator() {
		return new Iter();
	}

	/**
	 * The toString method. A way to print the list
	 * 
	 * @return the toString of the listNode if it is not null
	 */
	public String toString() {
		if (list != null)
			return list.toString();
		else
			return "[]";
	}

	/**
	 * An inner class used as an iterator, it can get the next element and show
	 * if there is a next element
	 * 
	 * @author Sebastian Aspegren 11/02/14
	 * 
	 * 
	 */
	private class Iter implements Iterator<E> {
		private ListNode<E> node = list;

		/**
		 * A method that checks if there is still more data in the list
		 * 
		 * @return true if the list has data in it
		 */
		public boolean hasNext() {
			return node != null;
		}

		/**
		 * A method that retrieves the next element in the list
		 * 
		 * @return the data in the next element
		 */
		public E next() {
			E data = node.getData();
			node = node.getNext();
			return data;

		}

		/**
		 * An unused method. Should not be called upon.
		 */
		public void remove() {

			throw new UnsupportedOperationException();

		}
	}
}
