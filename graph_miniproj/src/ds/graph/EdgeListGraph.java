package ds.graph;

import ds.positional_list.LinkedPositionalList;
import ds.positional_list.Position;
import ds.positional_list.PositionalList;


public class EdgeListGraph<V, E> implements Graph<V, E> {

    // ............... Nested Class ....................
    private class InnerVertex<V> implements Vertex<V> {

        // fields
        private V element;
        private Position<Vertex<V>> pos;

        // const
        public InnerVertex(V elm) {
            element = elm;
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

    }

    // ............... Nested Class ....................

    private class InnerEdge<E> implements Edge<E> {

        // field
        private E element;
        private Position<Edge<E>> pos;
        private final Vertex<V>[] endpoints;

        // cons
        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = new Vertex[]{u, v};
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
    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices;
    private PositionalList<Edge<E>> edges;

    // ............... Constructor ....................
    public EdgeListGraph(boolean directed) {
        isDirected = directed;
        vertices = new LinkedPositionalList<>();
        edges = new LinkedPositionalList<>();
    }

    // ............... Getters & Setters  ....................

    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public void setVertices(PositionalList<Vertex<V>> vertices) {
        this.vertices = vertices;
    }

    public PositionalList<Vertex<V>> getVertices() {
        return vertices;
    }

    public PositionalList<Edge<E>> getEdges() {
        return edges;
    }

    public void setEdges(PositionalList<Edge<E>> edges) {
        this.edges = edges;
    }

    // ............... Overrides ....................

    @Override
    public int numVertices() {
        return this.vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return this.edges.size();
    }

    @Override
    public PositionalList<Edge<E>> edges() {
        return edges;
    }

    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        InnerVertex<V> innerVertex1 = validate(v);
        InnerVertex<V> innerVertex2 = validate(u);
        for (Edge<E> edge : this.edges) {
            InnerEdge<E> innerEdge = validate(edge);
            if ((innerEdge.endpoints[0] == innerVertex1 && innerEdge.endpoints[1] == innerVertex2)
                    || (innerEdge.endpoints[0] == innerVertex2 && innerEdge.endpoints[1] == innerVertex1)) {
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
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        InnerVertex<V> innerVertex = validate(v);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == innerVertex) {
            return endpoints[1];
        } else if (endpoints[1] == innerVertex) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }

    @Override
    public int inDegree(Vertex<V> v) {
        InnerVertex<V> innerVertex = validate(v);
        int inDegree = 0;
        for (Edge<E> edge : this.edges) {
            InnerEdge<E> innerEdge = validate(edge);
            if (innerEdge.endpoints[1] == innerVertex) {
                inDegree++;
            }
        }
        return inDegree;
    }

    @Override
    public int outDegree(Vertex<V> v) {
        int outDegree = 0;
        InnerVertex<V> innerVertex = validate(v);
        for (Edge<E> edge : this.edges) {
            InnerEdge<E> innerEdge = validate(edge);
            if (innerEdge.endpoints[0] == innerVertex) {
                outDegree++;
            }
        }
        return outDegree;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        PositionalList<Edge<E>> outgoingEdge = new LinkedPositionalList<>();
        InnerVertex<V> innerVertex = validate(v);
        for (Edge<E> edge : this.edges) {
            InnerEdge<E> innerEdge = validate(edge);
            if (innerEdge.endpoints[0] == innerVertex ) {
                outgoingEdge.addLast(edge);
            }
        }
        return outgoingEdge;
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        PositionalList<Edge<E>> incomingEdge = new LinkedPositionalList<>();
        InnerVertex<V> innerVertex = validate(v);
        for (Edge<E> edge : this.edges) {
            InnerEdge<E> innerEdge = validate(edge);
            if (innerEdge.endpoints[1] == innerVertex) {
                incomingEdge.addLast(edge);
            }
        }
        return incomingEdge;
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element);
        v.setPos(vertices.addLast(v));
        return v;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("edge from u to v already exists");
        }
        InnerVertex<V> org = validate(u);
        InnerVertex<V> dst = validate(v);
        InnerEdge<E> edge = new InnerEdge<>(org, dst, element);
        edge.setPos(edges.addLast(edge));
        return edge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        // remove incident edges
        for (Edge<E> edge : this.edges) {
            InnerEdge<E> innerEdge = validate(edge);
            if (innerEdge.endpoints[0] == vert || innerEdge.endpoints[1] == vert) {
                removeEdge(edge);
            }
        }
        // remove vertex
        vertices.remove(vert.getPos());
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> innerEdge = validate(e);
        for (Edge<E> edge : this.edges) {
            if (edge == innerEdge) {
                removeEdge(edge);
            }
        }
    }

    // ............... Methods ....................
    private InnerVertex<V> validate(Vertex<V> v) throws IllegalArgumentException {
        //if(!(v instanceof InnerVertex<V> vert)) throw new IllegalArgumentException("invalid v");
        //if(!vertices.contains(vert)) throw new IllegalArgumentException("p is not in the list");
        return (InnerVertex<V>) v;
    }

    private InnerEdge<E> validate(Edge<E> e) throws IllegalArgumentException {
        //if(!(e instanceof InnerEdge<E> edge)) throw new IllegalArgumentException("invalid e");
        //if(!edges.contains(edge)) throw new IllegalArgumentException("e is not in the list");
        return (InnerEdge<E>) e;
    }
}

