public class OleDoleDoff {

  public static void main (String[] args) {
    OleDoleDoff oleDoleDoff = new OleDoleDoff();
    oleDoleDoff.singelLinkedImplementation(7, 3);
    System.out.println();
    oleDoleDoff.dubbleLinkedImplementation(7, 3);
  }
  
  private void singelLinkedImplementation(int antal, int mte) {
    SingelLinkedList<Integer> list = new SingelLinkedList<Integer>();
    for (int i = 0; i < antal; i++) {
      list.add(new SingelLinkedListElement(i + 1, null));
    }
    while (list.size != 0) {
      list.remove(mte - 1);
    }
  }

  private void dubbleLinkedImplementation(int antal, int mte) {
    DubbleLinkedList<Integer> list = new DubbleLinkedList<Integer>();
    for (int i = 0; i < antal; i++) {
      list.add(new DubbleLinkedListElement(i + 1, null, null));
    }
    while (list.size != 0) {
      list.remove(mte - 1);
    }
  }

  private class DubbleLinkedList<E> {
    DubbleLinkedListElement first;
    DubbleLinkedListElement last;
    DubbleLinkedListElement current;
    int size;

    public DubbleLinkedList() {
      first = null;
      last = null;
      current = null;
      size = 0;
    }

    public void add(DubbleLinkedListElement e) {
      if (size == 0) {
        first = e;
        last = e;
        current = e;
      } else if (size == 1) {
        first.next = e;
        e.previous = first;
        last = e;
      } else {
        last.next = e;
        e.previous = last;
        last = e;
      }
      last.next = first;
      first.previous = last;
      size++;
  }

  public void remove(int n) {
    if (n < 0) {
      System.out.println("too low index");
    } else if (n == 0) {
      System.out.println(first.element);
      first = first.next;
      last.next = first;
      current = first;
    } else {
      for (int i = 0; i < n; i++) {
        current = current.next;
      }
      // current is now the element we want to remove
      System.out.println(current.element);
      current.previous.next = current.next;
      current.next.previous = current.previous;
      current = current.next;
    }
    size--;
  }
}


  private class DubbleLinkedListElement<E> {
    public DubbleLinkedListElement next;
    public DubbleLinkedListElement previous;
    public E element;

    public DubbleLinkedListElement(E element, DubbleLinkedListElement next, DubbleLinkedListElement previous) {
      this.next = next;
      this.previous = previous;
      this.element = element;
    }
  }
  
  private class SingelLinkedList<E> {
    public int size;
    public SingelLinkedListElement<E> current;
    public SingelLinkedListElement<E> first;
    public SingelLinkedListElement<E> last;
  
    public SingelLinkedList () {
      size = 0;
      current = null;
      first = null;
      last = null;
    }

    public void print() {
      SingelLinkedListElement x = first;
      for (int i = 0; i < size; i++) {
        System.out.println(i + ": " + x.element);
        x = x.next;
      }
    }
  
    private void add (SingelLinkedListElement<E> e) {
      if (size == 0) {
        first = e;
        last = e;
        current = e;
      } else if (size == 1) {
        last = e;
        first.next = last;
      } else {
        last.next = e;
        last = e;
      }
      last.next = first;
      size++;
    }
  
    private void remove (int n) {
      if (n < 0) {
        System.out.println("Too low index specified");
      } else if (n == 0) {
        System.out.println(first.element);
        first = first.next;
        last.next = first;
      } else {
        for (int i = 0; i < n - 1; i++) {
          current = current.next;
        }
        // current is now the element before the one we want to remove
        System.out.println(current.next.element);
        current.next = current.next.next;
        current = current.next;
      }
      size--;
    }
  }
  
  private class SingelLinkedListElement<E> {
    public SingelLinkedListElement next;
    public E element;
  
    public SingelLinkedListElement(E element, SingelLinkedListElement next) {
      this.element = element;
      this.next = next;
    }
  }
}
