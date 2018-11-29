public class SLCWithGet<E extends Comparable<? super E>>
                          extends LinkedCollection<E>
                          implements CollectionWithGet<E> {
  @Override
  public boolean add(E element) {
    if ( element == null )
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

  public E get(E e) {
    if (head == null) return null;
    for (Entry current = head; current != null; current = current.next) {
      if (current.element.compareTo(e) == 0) {
        return current.element;
      }
    }
    return null;
  }
}
