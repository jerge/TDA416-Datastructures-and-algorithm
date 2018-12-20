
import java.util.*;

public class DirectedGraph<E extends Edge> {

    private List<List<E>> paths;

    public DirectedGraph(int noOfNodes) {
        paths = new ArrayList<>(noOfNodes);
        for (int i = 0; i < noOfNodes; i++) {
            paths.add(new ArrayList<>());
        }
    }

    public void addEdge(E e) {
        paths.get(e.getSource()).add(e);
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
        PriorityQueue<CompDijkstraPath> dijkstraPaths = new PriorityQueue<>();
        dijkstraPaths.add(new CompDijkstraPath(from,0,new ArrayList<>()));

        List<Integer> visited = new ArrayList<>();

        while (!dijkstraPaths.isEmpty()) {

            CompDijkstraPath currentElement = dijkstraPaths.poll();
            int node = currentElement.current;

            if (!visited.contains(node)) {
                if (node == to)
                    return currentElement.path.iterator();

                visited.add(node);
                for (E edge : this.paths.get(node)) {
                    if (!visited.contains(edge.getDest())) {
                        List<E> eList = new ArrayList<>(currentElement.path);
                        eList.add(edge);
                        dijkstraPaths.add(new CompDijkstraPath(edge.getDest(), currentElement.cost+edge.getWeight(),eList));
                    }

                }
            }
        }
        return null;
    }

    public Iterator<E> minimumSpanningTree() {
        List<List<CompKruskalEdge>> cc = new ArrayList<>();
        PriorityQueue<CompKruskalEdge> pq = new PriorityQueue<>();

        for (List<E> path : paths) {
            for (E edge : path) {
                pq.add(new CompKruskalEdge(edge.from, edge.to, edge.getWeight()));
            }
        }

        while (!pq.isEmpty() && cc.size() > 1) {
            CompKruskalEdge e = pq.poll();
            List<CompKruskalEdge> from = cc.get(e.from);
            List<CompKruskalEdge> to = cc.get(e.to);
            if (from != to) {
                if (from.size() > to.size()) {
                    from.addAll(to);
                    replaceOldReferences(cc,to,from);
                    from.add(e);
                } else {
                    to.addAll(from);
                    replaceOldReferences(cc,from,to);
                    to.add(e);
                }
            }
        }
        return null;
    }

    private void replaceOldReferences(List<List<CompKruskalEdge>> cc,
                                      List<CompKruskalEdge> oldList,
                                      List<CompKruskalEdge> newList) {
        for (int i = 0; i < cc.size(); i++) {
            if (oldList.equals(cc.get(i))) {
                cc.set(i,newList);
            }
        }
    }

}
