public class Edge {
    Node parent;
    Node child;
    int distance;
    public Edge(Node parent, Node child){
        this.parent = parent;
        this.child = child;
    }

    public Node getParent(){
        return parent;
    }

    public Node getChild(){
        return child;
    }
}
