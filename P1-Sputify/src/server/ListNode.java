package server;

/**
 * The class ListNode is used as links in the linked list. each node has a data
 * value and a reference to the next node unless there is none
 * 
 * @author Unknown
 * 
 * 
 */
public class ListNode<E> {
	private E data;
	private ListNode<E> next;

	/**
	 * A constructor that constructs a node
	 * 
	 * @param data
	 *            the data the node will hold
	 * @param next
	 *            the node the node will hold a reference to
	 */
	public ListNode(E data, ListNode<E> next) {
		this.data = data;
		this.next = next;
	}

	/**
	 * A method to get the data stored in the node
	 * 
	 * @return the data in the node
	 */
	public E getData() {
		return this.data;
	}

	/**
	 * A method to change the data in the node
	 * 
	 * @param data
	 *            the data we want to change it to
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * A method to see what node this node is refering to
	 * 
	 * @return the node this node holds a reference to
	 */
	public ListNode<E> getNext() {
		return this.next;
	}

	/**
	 * A method to change what the current node is refering to
	 * 
	 * @param next
	 *            what we want the node to refer to
	 */
	public void setNext(ListNode<E> next) {
		this.next = next;
	}

	/**
	 * A method that builds a string out of linked listnodes
	 * 
	 * @return the result of combining the listnodes with space etc in between
	 */
	public String toString() {
		StringBuilder str = new StringBuilder("[ ");
		str.append(data.toString());
		ListNode<E> node = next;
		while (node != null) {
			str.append("; ");
			str.append(node.getData().toString());
			node = node.getNext();
		}
		str.append(" ]");
		return str.toString();
	}
}