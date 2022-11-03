public class Node {

    Edge upperParent;
        Edge lowerParent;
        Edge upperChild;
        Edge lowerChild;
    public Node(Edge upperParent, Edge lowerParent, Edge upperChild, Edge lowerChild){
        
        this.upperParent = upperParent;
        this.lowerParent = lowerParent;
        this.upperChild = upperChild;
        this.lowerChild = lowerChild;
    }

    
}
