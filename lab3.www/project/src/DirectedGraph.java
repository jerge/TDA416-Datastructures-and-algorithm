
import java.util.*;

public class DirectedGraph<E extends Edge> {

    private List<List<E>> edges;

    public DirectedGraph(int noOfNodes) {
        edges = new ArrayList<>(noOfNodes);
        for (int i = 0; i < noOfNodes; i++) {
            edges.add(new ArrayList<>());
        }
    }

    public void addEdge(E e) {
        edges.get(e.getSource()).add(e);
    }

    private class CompDijkstraPath implements Comparable<CompDijkstraPath> {
        int current;
        double cost;
        List <E> path;

        CompDijkstraPath(int current, double cost, List<E> path) {
            this.current = current;
            this.cost = cost;
            this.path = path;
        }

        /**
         * Compares which of the queue elements that have the lowest cost
         * @param comp the element to compare to
         * @return 0 if they are equal, 1 if the other path (qe) has the smallest cost,
         * -1 if the current path has the smallest cost
         */
        @Override
        public int compareTo(CompDijkstraPath comp) {
            return Double.compare(cost, comp.cost);
        }
    }

    private class CompKruskalEdge extends BusEdge implements Comparable<CompKruskalEdge> {
        double weight;

        CompKruskalEdge (int from, int to, double weight) {
            super (from, to, weight, "");
            this.weight = weight;
        }

        @Override
        public int compareTo(CompKruskalEdge comp) {
            return Double.compare(weight, comp.weight);
        }
    }



    /**
     *
     * @param from node number of the start node
     * @param to node number of the finish node
     * @return null
     */
    public Iterator<E> shortestPath(int from, int to) {
        PriorityQueue<CompDijkstraPath> paths = new PriorityQueue<>();
        List <Integer> visited = new ArrayList<>();
        paths.add(new CompDijkstraPath(from,0,new ArrayList<>()));
        while (!paths.isEmpty()) {
            CompDijkstraPath currentElement = paths.poll();
            int node = currentElement.current;
            if (!visited.contains(node)) {
                if (node == to)
                    return currentElement.path.iterator();
                else {
                    visited.add(node);
                    for (E edge : edges.get(node)) {
                        if (!visited.contains(edge.getDest())) {
                            List<E> eList = new ArrayList<>();
                            eList.add(edge);
                            paths.add(new CompDijkstraPath(edge.getDest(), currentElement.cost+edge.getWeight(),eList));
                        }

                    }

                }
            }
        }
        return null;
    }

    public Iterator<E> minimumSpanningTree() {
        List<List<CompKruskalEdge>> cc = new ArrayList<>();
        PriorityQueue<CompKruskalEdge> pq = new PriorityQueue<>();
        for (List<E> edge : edges) {
            for (E path : edge) {
                pq.add(new CompKruskalEdge(path.from, path.to, path.getWeight()));
            }
        }
        while (!pq.isEmpty() && cc.size() > 1) {
            CompKruskalEdge e = pq.poll();
            List<CompKruskalEdge> from = cc.get(e.from);
            List<CompKruskalEdge> to = cc.get(e.to);
            if (from != to) {
                if (from.size() > to.size()) {
                    for (CompKruskalEdge edge : to) {
                        from.add(edge);
                    }
                }
            }
        }
        return null;
    }

}
