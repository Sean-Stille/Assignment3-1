
public class Path {
    int pathWeight;
    /*if 2 it was the first node
     *if 1 last direction was pointing down
     *if 0 last direction was pointing up
     */
    int lastPath;

    public Path(int pathWeight, int lastPath){
        this.pathWeight = pathWeight;
        this.lastPath = lastPath;
    }

    public int getWeight(){
        return pathWeight;
    }

    public int getLastPath(){
        return lastPath;
    }
}
