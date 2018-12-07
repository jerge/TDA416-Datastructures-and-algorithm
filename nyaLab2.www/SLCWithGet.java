public class SLCWithGet<E extends Comparable<? super E>> extends LinkedCollection<E> implements CollectionWithGet<E> {
  /**
   * Add the element into the collection at first occurence of a smaller (with
   * respect to its natural order) element, or null
   * 
   * @param E the element to be included
   * @returns true if the element is included in the collection.
   */
  @Override
  public boolean add(E element) {
    if (element == null)
      throw new NullPointerException();
    if (isEmpty() || element.compareTo(head.element) <= 0) {
      head = new Entry(element, head);
      return true;
    }
    for (Entry current = head; current != null; current = current.next) {
      if (current.next == null || element.compareTo(current.next.element) < 0) {
        current.next = new Entry(element, current.next);
        return true;
      }
    }
    return false;
  }

  /**
   * Find the first occurence of an element in the collection that is equal to the
   * argument <tt>e</tt> with respect to its natural order. I.e.
   * <tt>e.compareTo(element)</tt> is 0.
   * 
   * @param e The dummy element to compare to.
   * @return An element <tt>e'</tt> in the collection satisfying
   *         <tt>e.compareTo(e') == 0</tt>. If no element is found, <tt>null</tt>
   *         is returned
   */
  public E get(E e) {
    if (head == null)
      return null;
    for (Entry current = head; current != null; current = current.next) {
      if (current.element.compareTo(e) == 0) {
        return current.element;
      }
    }
    return null;
  }
}
