package ds.graph;

import ds.doubly_linkedlist.DoublyLinkedList;
import ds.positional_list.LinkedPositionalList;
import ds.positional_list.Position;
import ds.positional_list.PositionalList;

class AdjacencyListGraph<V, E> implements Graph<V, E> {

    // ............... Nested Class ....................

    private class InnerVertex<V> implements Vertex<V> {

        // field
        private V element;
        private Position<Vertex<V>> pos;
        private final DoublyLinkedList<Edge<E>> outgoing;
        private final DoublyLinkedList<Edge<E>> incoming;

        // cons
        public InnerVertex(V elm, boolean graphIsDirected) {
            element = elm;
            outgoing = new DoublyLinkedList<>();
            if (graphIsDirected) {
                incoming = new DoublyLinkedList<>();
            } else {
                incoming = outgoing;
            }
        }

        // get & set
        @Override
        public V getElement() {
            return element;
        }

        @Override
        public void setElement(V elem) {
            element = elem;
        }

        public void setPos(Position<Vertex<V>> pos) {
            this.pos = pos;
        }

        public Position<Vertex<V>> getPos() {
            return pos;
        }

        public DoublyLinkedList<Edge<E>> getOutgoing() {
            return outgoing;
        }

        public DoublyLinkedList<Edge<E>> getIncoming() {
            return incoming;
        }
    }

    // ............... Nested Class ....................

    private class InnerEdge<E> implements Edge<E> {

        // field
        private E element;
        private Position<Edge<E>> pos;  //
        private final Vertex<V>[] endpoints;

        // cons
        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        // get & set
        @Override
        public E getElement() {
            return element;
        }

        @Override
        public void setElement(E elem) {
            element = elem;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        public Position<Edge<E>> getPos() {
            return pos;
        }

        public void setPos(Position<Edge<E>> pos) {
            this.pos = pos;
        }
    }

    // ............... Fields ....................

    private final boolean isDirected;
    private final PositionalList<Vertex<V>> vertices;
    private final PositionalList<Edge<E>> edges;

    // ............... Constructor ....................

    public AdjacencyListGraph(boolean directed) {
        isDirected = directed;
        vertices = new LinkedPositionalList<>();
        edges = new LinkedPositionalList<>();
    }

    // ............... Getters & Setters ....................

    public PositionalList<Edge<E>> getEdges() {
        return edges;
    }

    public PositionalList<Vertex<V>> getVertices() {
        return vertices;
    }

    // ............... Overrides ....................

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public PositionalList<Edge<E>> edges() {
        return edges;
    }

    @Override
    public int outDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing();
    }

    @Override
    public int inDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming();
    }

    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        InnerVertex<V> vert1 = validate(v);
        InnerVertex<V> vert2 = validate(v);
        InnerVertex<V> min = vert1.getIncoming().size() + vert1.getOutgoing().size()
                <= vert2.getIncoming().size() + vert2.getOutgoing().size() ? vert1 : vert2;
        for (Edge<E> e : min.getIncoming()) {
            InnerEdge<E> edge = validate(e);
            if (edge.getEndpoints()[0].equals(vert2) || edge.getEndpoints()[1].equals(vert2)) {
                return edge;
            }
        }
        for (Edge<E> e : min.getOutgoing()) {
            InnerEdge<E> edge = validate(e);
            if (edge.getEndpoints()[0].equals(vert2) || edge.getEndpoints()[1].equals(vert2)) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {  // endpoints are kept or not
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element, isDirected);
        v.setPos(vertices.addLast(v));
        return v;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("edge from u to v already exists");
        }
        InnerEdge<E> edge = new InnerEdge<>(u, v, element);
        edge.setPos(edges.addLast(edge));
        InnerVertex<V> org = validate(u);
        InnerVertex<V> dst = validate(v);
        org.getOutgoing().addLast(edge);
        dst.getIncoming().addLast(edge);
        return edge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        for (Edge<E> e : vert.getOutgoing()) {
            removeEdge(e);
        }
        for (Edge<E> e : vert.getIncoming()) {
            removeEdge(e);
        }
        vertices.remove(vert.getPos());
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        InnerVertex<V> org = validate(endpoints[0]);
        InnerVertex<V> dst = validate(endpoints[1]);
        org.getOutgoing().remove(edge);
        dst.getIncoming().remove(edge);
        edges.remove(edge.getPos());
    }

    // ............... Methods ....................

    private InnerVertex<V> validate(Vertex<V> v) throws IllegalArgumentException {
        //if(!(v instanceof InnerVertex<V> vert)) throw new IllegalArgumentException("invalid v");
        // if(!vertices.contains(vert)) throw new IllegalArgumentException("p is not in the list");
        return (InnerVertex<V>) v;
    }

    private InnerEdge<E> validate(Edge<E> e) throws IllegalArgumentException {
        //if(!(e instanceof InnerEdge<E> edge)) throw new IllegalArgumentException("invalid e");
        //if(!edges.contains(edge)) throw new IllegalArgumentException("e is not in the list");
        return (InnerEdge<E>) e;
    }
}
