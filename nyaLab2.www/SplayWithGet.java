public class SplayWithGet<E extends Comparable<? super E>> extends BinarySearchTree<E> implements CollectionWithGet<E> {
  /*
   * Rotera 1 steg i hogervarv, dvs x' y' / \ / \ y' C --> A x' / \ / \ A B B C
   */
  private void zag(Entry x) {
    Entry y = x.left;
    E temp = x.element;
    x.element = y.element;
    y.element = temp; // switch x's and y's elements
    x.left = y.left; // make x's grandchild x's new child
    if (x.left != null)
      x.left.parent = x; // x's new child has x as parent
    y.left = y.right;
    y.right = x.right;
    if (y.right != null)
      y.right.parent = y;
    x.right = y;
  } // rotateRight
  // ========== ========== ========== ==========

  /*
   * Rotera 1 steg i vanstervarv, dvs x' y' / \ / \ A y' --> x' C / \ / \ B C A B
   */
  private void zig(Entry x) {
    Entry y = x.right;
    E temp = x.element;
    x.element = y.element;
    y.element = temp;
    x.right = y.right;
    if (x.right != null)
      x.right.parent = x;
    y.right = y.left;
    y.left = x.left;
    if (y.left != null)
      y.left.parent = y;
    x.left = y;
  } // rotateLeft
  // ========== ========== ========== ==========

  /*
   * Rotera 2 steg i hogervarv, dvs x' z' / \ / \ y' D --> y' x' / \ / \ / \ A z'
   * A B C D / \ B C
   */
  private void zagzig(Entry x) {
    Entry y = x.left, z = x.left.right;
    E e = x.element;
    x.element = z.element;
    z.element = e;
    y.right = z.left;
    if (y.right != null)
      y.right.parent = y;
    z.left = z.right;
    z.right = x.right;
    if (z.right != null)
      z.right.parent = z;
    x.right = z;
    z.parent = x;
  } // doubleRotateRight
  // ========== ========== ========== ==========

  /*
   * Rotera 2 steg i vanstervarv, dvs x' z' / \ / \ A y' --> x' y' / \ / \ / \ z D
   * A B C D / \ B C
   */
  private void zigzag(Entry x) {
    Entry y = x.right, z = x.right.left;
    E e = x.element;
    x.element = z.element;
    z.element = e;
    y.left = z.right;
    if (y.left != null)
      y.left.parent = y;
    z.right = z.left;
    z.left = x.left;
    if (z.left != null)
      z.left.parent = z;
    x.left = z;
    z.parent = x;
  } // doubleRotateLeft
  // ========== ========== ========== ==========

  /**
   * zagzagga
   * 
   * @param a the root of the rotation
   */

  private void zagzag(Entry a) {
    Entry b = a.left;
    Entry c = a.left.left;
    E temp = a.element;

    a.element = c.element;
    c.element = temp;

    b.left = c.right;
    c.right = a.right;
    a.left = c.left;
    c.left = b.right;

    if (b.left != null)
      b.left.parent = b;
    if (c.right != null)
      c.right.parent = c;
    if (a.left != null)
      a.left.parent = a;
    if (c.left != null)
      c.left.parent = c;

    a.right = b;
    b.right = c;
  }

  /**
   * zigzigga
   * 
   * @param a the root of the rotation
   */
  private void zigzig(Entry a) {
    Entry b = a.right;
    Entry c = a.right.right;
    E temp = a.element;

    a.element = c.element;
    c.element = temp;

    b.right = c.left;
    c.left = a.left;
    a.right = c.right;
    c.right = b.left;

    if (b.right != null)
      b.right.parent = b;
    if (c.left != null)
      c.left.parent = c;
    if (a.right != null)
      a.right.parent = a;
    if (c.right != null)
      c.right.parent = c;

    a.left = b;
    b.left = c;
  }

  /**
   * Find the first occurence of an element in the collection that is equal to the
   * argument <tt>e</tt> with respect to its natural order. I.e.
   * <tt>e.compareTo(element)</tt> is 0. Also splays the tree
   * 
   * @param e The dummy element to compare to.
   * @return An element <tt>e'</tt> in the collection satisfying
   *         <tt>e.compareTo(e') == 0</tt>. If no element is found, <tt>null</tt>
   *         is returned
   */
  @Override
  public E get(E element) {
    Entry e = find(element, root);
    if (e == null)
      return null;
    return e.element;
  }

  /**
   * helper function for get
   */
  @Override
  public Entry find(E element, Entry target) {
    if (target == null || element == null) {
      return null;
    } else {
      int cmp = element.compareTo(target.element);
      if (cmp < 0) {
        if (target.left == null) {
          splay(target);
          return null;
        }
        return find(element, target.left);
      } else if (cmp > 0) {
        if (target.right == null) {
          splay(target);
          return null;
        }
        return find(element, target.right);
      } else {
        splay(target);
        return target;
      }
    }
  }

  /**
   * Splays the given entry to the top of the tree
   * 
   * @param target the target to splay to the top
   */
  private void splay(Entry target) {
    if (target == null)
      return;

    while (target.parent != null && target.parent.parent != null) {
      Entry parpar = target.parent.parent;
      if (parpar.right != null && target.equals(parpar.right.right))
        zigzig(parpar);
      else if (parpar.right != null && target.equals(parpar.right.left))
        zigzag(parpar);
      else if (parpar.left != null && target.equals(parpar.left.right))
        zagzig(parpar);
      else if (parpar.left != null && target.equals(parpar.left.left))
        zagzag(parpar);
      target = parpar;
    }
    if (target.parent != null) {
      Entry parent = target.parent;
      if (target.equals(parent.right))
        zig(parent);
      else if (target.equals(parent.left))
        zag(parent);
    }
  }

  /**
   * Just for testing
   * 
   * @param args
   */
  public static void main(String[] args) {
    SplayWithGet<Integer> st = new SplayWithGet<Integer>();
    st.add(100);
    st.add(50);
    st.add(150);
    st.add(25);
    // st.add(75);

    st.add(125);
    st.add(175);
    st.add(120);
    st.add(110);

    st.add(10);
    st.add(30);

    st.add(60);
    st.add(80);
    st.add(110);
    st.add(130);
    st.add(160);
    st.add(180);

    System.out.println("Tree before:");
    System.out.println(st);

    System.out.println(st.get(80));

    System.out.println("Tree after zigzig");
    System.out.println(st);

  }
}
