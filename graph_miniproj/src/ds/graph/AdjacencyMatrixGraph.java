package ds.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class AdjacencyMatrixGraph<V, E> implements Graph<V, E> {

    // ............... Nested Class ....................

    private class InnerVertex<V> implements Vertex<V> {

        // fields
        private V element;

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
    }

    // ............... Nested Class ....................

    private class InnerEdge<E> implements Edge<E> {

        // field
        private E element;
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

    }

    // ............... Fields ....................
    private boolean isDirected;
    private Map<Vertex<V>, Integer> vertices;
    private Edge<E>[][] adj;
    private int numVertices;
    private int numEdges;

    // ............... Constructor ....................
    public AdjacencyMatrixGraph(boolean directed, int numVertices) {
        this.numVertices = numVertices;
        this.numEdges = 0;
        isDirected = directed;
        vertices = new HashMap<>();
        adj = new Edge[numVertices][numVertices];
    }

    // ............... Getters & Setters  ....................
    public boolean isDirected() {
        return isDirected;
    }

    public void setDirected(boolean directed) {
        isDirected = directed;
    }

    public Map<Vertex<V>, Integer> getVertices() {
        return vertices;
    }

    public void setVertices(Map<Vertex<V>, Integer> vertices) {
        this.vertices = vertices;
    }

    public Edge<E>[][] getAdj() {
        return adj;
    }

    public void setAdj(Edge<E>[][] adj) {
        this.adj = adj;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    // ............... Overrides ....................

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices.keySet();
    }

    @Override
    public int numEdges() {
        return numEdges;
    }

    @Override
    public Iterable<Edge<E>> edges() {
        ArrayList<Edge<E>> edges = new ArrayList<>();
        for (int i = 0; i < this.numVertices; i++) {
            for (int j = 0; j < this.numVertices; j++) {
                if (adj[i][j] != null) {
                    edges.add(adj[i][j]);
                }
            }
        }
        return edges;
    }

    @Override
    public int outDegree(Vertex<V> v) {
        int outDegree = 0;
        InnerVertex<V> vert = validate(v);
        int index = this.vertices.get(vert);
        for (int i = 0; i < numVertices; i++) {
            if (adj[index][i] != null) {
                InnerEdge<E> edge = validate(adj[index][i]);
                if (edge.getEndpoints()[0] == vert) {
                    outDegree++;
                }
            }
        }
        return outDegree;
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        ArrayList<Edge<E>> outgoingEdges = new ArrayList<>();
        InnerVertex<V> vert = validate(v);
        int index = this.vertices.get(vert);
        for (int i = 0; i < numVertices; i++) {
            if (adj[index][i] != null) {
                InnerEdge<E> edge = validate(adj[index][i]);
                if (edge.getEndpoints()[0] == vert) {
                    outgoingEdges.add(edge);
                }
            }
        }
        return outgoingEdges;
    }

    @Override
    public int inDegree(Vertex<V> v) {
        int inDegree = 0;
        InnerVertex<V> vert = validate(v);
        int index = this.vertices.get(vert);
        for (int i = 0; i < numVertices; i++) {
            if (adj[index][i] != null) {
                InnerEdge<E> edge = validate(adj[index][i]);
                if (edge.getEndpoints()[1] == vert) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        ArrayList<Edge<E>> incomingEdges = new ArrayList<>();
        InnerVertex<V> vert = validate(v);
        int index = this.vertices.get(vert);
        for (int i = 0; i < numVertices; i++) {
            if (adj[index][i] != null) {
                InnerEdge<E> edge = validate(adj[index][i]);
                if (edge.getEndpoints()[1] == vert) {
                    incomingEdges.add(edge);
                }
            }
        }
        return incomingEdges;
    }

    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        InnerVertex<V> vert1 = validate(v);
        InnerVertex<V> vert2 = validate(v);
        int indexV = this.vertices.get(vert1);
        int indexU = this.vertices.get(vert2);
        return this.adj[indexV][indexU];
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        InnerVertex<V> vert = validate(v);
        int index = this.vertices.get(vert);
        for (int i = 0; i < numVertices; i++) {
            if (adj[index][i] != null && adj[index][i] == edge) {
                InnerEdge<E> validEdge = validate(adj[index][i]);
                return validEdge.getEndpoints()[0] == vert ? validEdge.getEndpoints()[1] : validEdge.getEndpoints()[0];
            }
        }
        throw new IllegalArgumentException("v is not incident to this edge");
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element);
        Edge<E>[][] newAdj = new Edge[this.numVertices + 1][this.numVertices + 1];
        this.vertices.put(v, numVertices);

        // initialize new adj[][] array
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(this.adj[i], 0, newAdj[i], 0, numVertices);
        }

        numVertices++;
        return v;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("edge from u to v already exists");
        }
        // find vertexes
        InnerVertex<V> vert1 = validate(v);
        InnerVertex<V> vert2 = validate(v);
        int indexV = this.vertices.get(vert1);
        int indexU = this.vertices.get(vert2);
        // add new edge
        InnerEdge<E> edge = new InnerEdge<>(vert1, vert2, element);
        this.adj[indexV][indexU] = edge;

        return edge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        Edge<E>[][] newAdj = new Edge[this.numVertices - 1][this.numVertices - 1];
        this.vertices.remove(vert, numVertices);

        // initialize new indexToVertex array and adj[][] array
        for (int i = 0; i < numVertices - 1; i++) {
            System.arraycopy(this.adj[i], 0, newAdj[i], 0, numVertices);
        }

        numVertices--;
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        InnerVertex<V> org = validate(endpoints[0]);
        InnerVertex<V> dst = validate(endpoints[1]);
        int orgIndex = this.vertices.get(org);
        int dstIndex = this.vertices.get(dst);
        this.adj[orgIndex][dstIndex] = null;
        this.adj[dstIndex][orgIndex] = null;
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