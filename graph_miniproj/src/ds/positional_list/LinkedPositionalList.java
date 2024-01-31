package ds.positional_list;

import ds.positional_list.Position;
import ds.positional_list.PositionalList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E> {

    // ............... Nested Class ....................

    private static class Node<E> implements Position<E> {

        // field
        private E element;
        private Node<E> prev;
        private Node<E> next;

        // cons
        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }

        // override
        @Override
        public E getElement() throws IllegalStateException {
            if (next == null) {
                throw new IllegalStateException("ds.positional_list.Position no longer valid");
            }
            return element;
        }

        // get & set
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

    // ............... Fields ....................

    private Node<E> header;
    private Node<E> trailer;
    private int size;

    // ............... Constructor ....................

    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    // ............... Override Methods ....................

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> first() {
        return header.getNext();
    }

    @Override
    public Position<E> last() {
        return trailer.getPrev();
    }

    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getPrev());
    }

    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return position(node.getNext());
    }

    private Position<E> addBetween(E e, Node<E> pred, Node<E> suc) {
        Node<E> newest = new Node<>(e, pred, suc);
        pred.setNext(newest);
        suc.setPrev(newest);
        size++;
        return newest;
    }

    @Override
    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    @Override
    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node.getPrev(), node);
    }

    @Override
    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return addBetween(e, node, node.getNext());
    }

    @Override
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E ans = p.getElement();
        node.setElement(e);
        return ans;
    }

    @Override
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        Node<E> pred = node.getPrev();
        Node<E> suc = node.getNext();
        pred.setNext(suc);
        suc.setPrev(pred);
        size--;
        E ans = node.getElement();
        node.setElement(null);
        node.setPrev(null);
        node.setNext(null);
        return ans;
    }

    // ............... Getters & Setters ....................

    public Node<E> getHeader() {
        return header;
    }

    public void setHeader(Node<E> header) {
        this.header = header;
    }

    public Node<E> getTrailer() {
        return trailer;
    }

    public void setTrailer(Node<E> trailer) {
        this.trailer = trailer;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // ............... Methods ....................

    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> node)) throw new IllegalArgumentException("invalid p");
        if (node.getNext() == null) throw new IllegalArgumentException("p is no longer in the list");
        return node;
    }

    private Position<E> position(Node<E> node) {
        if (node == header || node == trailer) {
            return null;
        }
        return node;
    }

    // ............... Iterator ....................

    private class PositionIterator implements Iterator<Position<E>> {
        private Position<E> cursor = first();
        private Position<E> recent = null;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public Position<E> next() throws NoSuchElementException {
            if (cursor == null) {
                throw new NoSuchElementException();
            }
            recent = cursor;
            cursor = after(cursor);
            return recent;

        }
    }

    private class PositionIterable implements Iterable<Position<E>> {
        @Override
        public Iterator<Position<E>> iterator() {
            return new PositionIterator();
        }
    }

    public Iterable<Position<E>> positions() {
        return new PositionIterable();
    }

    private class ElementIterator implements Iterator<E> {

        Iterator<Position<E>> postIterator = new PositionIterator();

        @Override
        public boolean hasNext() {
            return postIterator.hasNext();
        }

        @Override
        public E next() {
            return postIterator.next().getElement();
        }
    }

    public Iterator<E> iterator() {
        return new ElementIterator();
    }
}
