
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

        /**
         * Compares which of the queue elements that have the lowest weight
         * @param comp the element to compare to
         * @return 0 if they have equal weight, 1 if the other path (qe) has the smallest weight,
         * -1 if the current path has the smallest weight
         */
        @Override
        public int compareTo(CompKruskalEdge comp) {
            return Double.compare(weight, comp.weight);
        }
    }



    /**
     * Calculates the shortest path between two nodes
     * @param from node number of the start node
     * @param to node number of the finish node
     * @return An iterator containing all edges that makes up the desired paths between the nodes
     */
    public Iterator<E> shortestPath(int from, int to) {
        PriorityQueue<CompDijkstraPath> dijkstraPaths = new PriorityQueue<>();
        dijkstraPaths.add(new CompDijkstraPath(from,0,new ArrayList<>())); // Adds the initial node to the queue

        List<Integer> visited = new ArrayList<>(); // A list to know if you've visited the node before

        while (!dijkstraPaths.isEmpty()) { // While the queue is not empty

            CompDijkstraPath currentElement = dijkstraPaths.poll(); // Retrieves the first element of the queue
            int node = currentElement.current;

            if (!visited.contains(node)) { // If the node is not visited
                if (node == to) // If the node is the end point
                    return currentElement.path.iterator(); // Return the path

                visited.add(node); // Count the node as visited
                for (E edge : this.paths.get(node)) { // for every edge on EL(node)
                    if (!visited.contains(edge.getDest())) { // If edge is not visited
                        List<E> eList = new ArrayList<>(currentElement.path);
                        eList.add(edge); // Add new queue Element
                        dijkstraPaths.add(new CompDijkstraPath(edge.getDest(), currentElement.cost+edge.getWeight(),eList));
                    }

                }
            }
        }
        return null;
    }

    /**
     * Calculates the list containing edges that connects all nodes with minimum cost,
     * using Kruscal's algorithm
     * @return The iterator containing the edges ^
     */
    public Iterator<E> minimumSpanningTree() {
        List <List<E>> cc = new ArrayList<>();
        PriorityQueue<CompKruskalEdge> pq = new PriorityQueue<>();

        for(List<E> path : paths) { //Adds all edges to a priority queue
            for (E edge : path) {
                pq.add(new CompKruskalEdge(edge.getSource(), edge.getDest(), edge.getWeight()));
            }
            cc.add(new ArrayList<>());  //Every node contains an empty list
        }
        int ccSize = cc.size(); // |cc|
        while(!pq.isEmpty() && ccSize > 1){ // pq not empty and |cc| > 1
            CompKruskalEdge e = pq.poll(); // Retrieves e from queue
            List<E> from = cc.get(e.from);
            List<E> to = cc.get(e.to);
            if(from != to){     //If from and to doesn't refer to the same list in cc
                if(from.size() > to.size()){ // Selects the smallest list
                    from.addAll(to); // Moves all elements from the smaller to the larger list
                    replaceOldReferences(to, from, cc); // Makes sure all relevant nodes refer to the new list
                    from.add((E) e); // Finally adds e to the filled list
                } else { // Selects the smallest list
                    to.addAll(from); // Moves all elements from the smaller to the larger list
                    replaceOldReferences(from, to, cc); // Makes sure all relevant nodes refer to the new list
                    to.add((E) e); // Finally adds e to the filled list
                }
                ccSize--;
            }
        }
        return cc.get(0).iterator();
    }

    /**
     * Redirects the pointers in a list of lists pointing at the old list to point at the new list
     * @param oldList The old list
     * @param newList The new list
     * @param cc The list of lists
     */
    private void replaceOldReferences(List<E> oldList, List<E> newList, List<List<E>> cc) {
        for(int i = 0; i < cc.size() ; i++){
            if(cc.get(i) == oldList){
                cc.set(i, newList);
            }
        }
    }


}
