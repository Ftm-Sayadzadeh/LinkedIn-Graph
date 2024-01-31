package ds.doubly_linkedlist;

import java.util.Iterator;

public class DoublyLinkedList<E> implements Iterable<E> {

    // ............... Fields ....................

    private final Node<E> header;
    private final Node<E> trailer;
    private int size;

    // ............... Nested Class ....................
    private static class Node<E> {

        // fields
        private E element;
        private Node<E> prev;
        private Node<E> next;

        // const
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        // get & set
        public E getElement() {
            return element;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    // ............... Methods ....................

    public DoublyLinkedList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public E first() {
        if (isEmpty())
            throw new IllegalArgumentException();
        return header.next.element;
    }

    public E last() {
        if (isEmpty())
            throw new IllegalArgumentException();
        return trailer.prev.element;
    }

    public E get(int i) {
        if (i >= size()) throw new IllegalArgumentException();
        return getNode(i).element;
    }

    private Node<E> getNode(int i) {
        Node<E> cur = header;
        while (i >= 0) {
            cur = cur.next;
            i--;
        }
        return cur;
    }

    public void addFirst(E e) {
        addBetween(e, header, header.getNext());
    }

    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }

    public void add(int i, E e) {
        if (i > size)
            throw new IllegalArgumentException();
        addBetween(e, getNode(i).getPrev(), getNode(i));
    }

    public void addBetween(E e, Node<E> p, Node<E> s) {
        Node<E> n = new Node<>(e, p, s);
        p.setNext(n);
        s.setPrev(n);
        size++;
    }

    public E removeFirst() {
        if (isEmpty())
            throw new IllegalArgumentException();
        return removeNode(header.next);
    }

    public E removeLast() {
        if (isEmpty()) throw new IllegalArgumentException();
        return removeNode(trailer.prev);
    }

    public E remove(int i) {
        if (i >= size)
            throw new IllegalArgumentException();
        return removeNode(getNode(i));
    }

    public E removeNode(Node<E> n) {
        n.prev.setNext(n.next);
        n.next.setPrev(n.prev);
        size--;
        return n.element;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = header.next; x != null; x = x.next) {
                if (x.element == null) {
                    removeNode(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = header.next; x != null; x = x.next) {
                if (o.equals(x.element)) {
                    removeNode(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> cur = header.next;

            @Override
            public boolean hasNext() {
                return cur.element != null;
            }

            @Override
            public E next() {
                E ans = cur.element;
                cur = cur.next;
                return ans;
            }
        };
    }
}
