public class Node {
    int topWeight;
    int bottomWeight;
    int name;
    int topOrBottomWeight;
    Boolean top;
    public Node(int name, int topWeight, int bottomWeight){
        this.name = name;
        this.topWeight = topWeight;
        this.bottomWeight = bottomWeight;
    }

    public Node(int name, int topOrBottomWeight, boolean top){
        this.top = top;
        this.name = name;
        this.topOrBottomWeight = topOrBottomWeight;
    }

    public Node(int name){
        this.name = name;
    }

    public int getTopWeight(){
        return topWeight;
    }
    
    public int getBottomWeight(){
        return bottomWeight;
    }

    public int getTopOrBottomWeight(){
        return topOrBottomWeight;
    }

    public boolean hasBothWeights(){
        if(topWeight != 0 && bottomWeight != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void setTopWeight(int topWeight){
        this.topWeight = topWeight;
    }

    public void setBottomWeight(int bottomWeight){
        this.bottomWeight = bottomWeight;
    }

    public int getName(){
        return name;
    }
}
