import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class App {

    static Path[] bestRoute;

    public static Node[][] fillOutDiagonal(int sideLength, int[] arr){
        int diagonalLines = (sideLength + sideLength) - 1;
        int midPoint = (diagonalLines / 2) + 1;
        Node[][] diagonal = new Node[sideLength][sideLength];

        int itemsInDiagonal = 0;
        int p = 1;
        int arrIndex = 0; 

        for (int i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int columnIndex;

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (i - j) - 1;
                    columnIndex = j;
                    //if the number is on the last column it can only point down
                    if(columnIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setBottomWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex++;
                    }
                    //if the number is on the last row it can only point up
                    else if(rowIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setTopWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex++;
                    }
                    //all other nodes that aren't in the right column or the bottom row will have 2 weights
                    else{
                        Node currentNode = new Node(p, arr[arrIndex], arr[arrIndex + 1]); 
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex = arrIndex + 2;
                    }
                    p++;
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (sideLength - 1) - j;
                    columnIndex = (i - sideLength) + j;
                    //if the number is on the last column it can only point down
                    //this shouldn't ever happen here but just to be safe;
                    if(columnIndex + 1 == sideLength && rowIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex++;
                    }
                    else if(columnIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setTopWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex++;
                    }
                    //if the number is on the last row it can only point up
                    else if(rowIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setBottomWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex++;
                    }
                    //all other nodes that aren't in the right column or the bottom row will have 2 weights
                    else{
                        Node currentNode = new Node(p, arr[arrIndex], arr[arrIndex + 1]); 
                        diagonal[rowIndex][columnIndex] = currentNode;
                        arrIndex = arrIndex + 2;
                    }
                    p++;
                }
            }
        }

        for(int i = 0; i < sideLength; i++){
            for(int k = 0; k < sideLength; k++){ 
                System.out.print(diagonal[k][i].getName() + "(");
                try{
                    System.out.print(diagonal[k][i].getTopWeight() + ", ");
                    System.out.print(diagonal[k][i].getBottomWeight());
                    System.out.print(") ");
                }catch(Exception e){
                   
                }
            }
            System.out.println();
        }
        return diagonal;
    }

    public static void GetBestPath(int turnPenalty, Node[][] graph, int sideLength){
        int length = sideLength;
        int diagonalLines = (length + length) - 1;
        int midPoint = (diagonalLines / 2) + 1;
        int itemsInDiagonal = 0;
        int p = 0;

        for (int i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int columnIndex;

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (i - j) - 1;
                    columnIndex = j;
                    if(rowIndex + columnIndex == 0){
                       bestRoute[p] = new Path(0, 2); 
                       System.out.println(p + 1 + " " + bestRoute[0].getWeight() + " " + bestRoute[0].getLastPath());
                       p++;
                    }
                    //if they are on the first row and not the first column they only have one way to get to the node
                    //ie the previous node's pointer up
                    else if(rowIndex == 0 && columnIndex > 0){
                        int topRow = graph[rowIndex][columnIndex - 1].getBottomWeight();
                        //this way of retreiving is crucial
                        //the -1 is because the name is placed at the name -1 index
                        Path current = new Path(topRow + bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1].getWeight(), 1);
                        bestRoute[p] = current;
                        System.out.println(p + 1 + " " + bestRoute[p].getWeight() + " " + bestRoute[p].getLastPath());
                        p++;
                        //get the previous weight from the way we are storing it in a list and add that to the topRow and store that in a list
                    }
                    //if they are in the fist column and not the first row they only have one way to get to the node
                    //ie the previous node's pointer down
                    else if(columnIndex == 0 && rowIndex > 0){
                        int bottomRow = graph[rowIndex - 1][columnIndex].getTopWeight();
                        Path current = new Path(bottomRow + bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1].getWeight(), 0);
                        bestRoute[p] = current;
                        System.out.println(p  + 1 + " " + bestRoute[p].getWeight()+ " " + bestRoute[p].getLastPath());
                        p++;
                    }
                    //here both options are avaliable so you need to take the max of them and put that in the global array
                    //so you can either take the topWeight from the column index to the left or the bottomWeight form the row index above
                    //Im thinking the only way to keep track of the direction would be to have something else in the global array 
                    //maybe a bool where true is up as the last and false when bottom was the last 
                    //so if it is false and you pull from the row above you incur no penalty but if it is true and you pull
                    //from the top row you do incur the penalty
                    //then if it is false and you pull from the left column you do incure the penalty and if it is true and 
                    //you pull from the left column you would not incur a penalty
                    else if(columnIndex > 0 && rowIndex > 0){
                        //these are the paths that can point to the current node 
                        //so you need to get the lastPathdirection from the node and 
                        Path abovePath = bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1];
                        Path belowPath = bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1];

                        int abovePathLastDirection = abovePath.getLastPath();
                        int belowPathLastDirection = belowPath.getLastPath();

                        int weightCommingFromAbove = 0;
                        int weightCommingFromBelow = 0;
                        int directionAbove = 2;
                        int directionBelow = 2;
                        int bestWeight = 0;
                        int direction = 2;

                        //the node above got pointed to by going down and will point down so it won't inncur a penalty
                        if(abovePathLastDirection == 1){
                            int potentialWeight1 = graph[rowIndex - 1][columnIndex].getTopWeight();
                            weightCommingFromAbove = potentialWeight1;
                            directionAbove = 1;
                        }
                        //the node above got pointed to by going up and will point down so it will inccur a penalty
                        else if(abovePathLastDirection == 0){
                            int potentialWeight1 = (graph[rowIndex - 1][columnIndex].getTopWeight()) + turnPenalty;
                            weightCommingFromAbove = potentialWeight1;
                            directionAbove = 0;
                        }

                        //the node below got pointed to by going up and will point up so it won't inncur a penalty
                        if(belowPathLastDirection == 0){
                            int potentialWeight2 = graph[rowIndex][columnIndex - 1].getBottomWeight();
                            weightCommingFromBelow = potentialWeight2;
                            directionBelow = 0;
                        }
                        //the node below got pointed to by going down and will poing up so it will inncur a penalty
                        else if(belowPathLastDirection == 1){
                            int potentialWeight2 = (graph[rowIndex][columnIndex - 1].getBottomWeight()) + turnPenalty;
                            weightCommingFromBelow = potentialWeight2;
                            directionBelow = 1;
                        }

                        if(weightCommingFromAbove > weightCommingFromBelow){
                            bestWeight = weightCommingFromAbove;
                            direction = directionAbove;
                        }
                        else{
                            bestWeight = weightCommingFromBelow;
                            direction = directionBelow;
                        }

                        bestRoute[p] = new Path(bestWeight, direction);
                        System.out.println(p + 1 + " " + bestWeight + " " + direction);
                        p++;

                    }
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (length - 1) - j;
                    columnIndex = (i - length) + j;
                    if(rowIndex + columnIndex == 0){
                        bestRoute[p] = new Path(0, 2); 
                        System.out.println(p + 1 + " " + bestRoute[0].getWeight()+ " " + bestRoute[p].getLastPath());
                        p++;
                     }
                     //if they are on the first row and not the first column they only have one way to get to the node
                     //ie the previous node's pointer up
                     else if(rowIndex == 0 && columnIndex > 0){
                         int topRow = graph[rowIndex][columnIndex - 1].getBottomWeight();
                         //this way of retreiving is crucial
                         //the -1 is because the name is placed at the name -1 index
                         Path current = new Path(topRow + bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1].getWeight(), 1);
                         bestRoute[p] = current;
                         System.out.println(p + 1 + " " + bestRoute[p].getWeight()+ " " + bestRoute[p].getLastPath());
                         p++;
                         //get the previous weight from the way we are storing it in a list and add that to the topRow and store that in a list
                     }
                     //if they are in the fist column and not the first row they only have one way to get to the node
                     //ie the previous node's pointer down
                     else if(columnIndex == 0 && rowIndex > 0){
                         int bottomRow = graph[rowIndex - 1][columnIndex].getTopWeight();
                         Path current = new Path(bottomRow + bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1].getWeight(), 0);
                         bestRoute[p] = current;
                         System.out.println(p + 1 + " " + bestRoute[p].getWeight()+ " " + bestRoute[p].getLastPath());
                         p++;
                     }
                     //here both options are avaliable so you need to take the max of them and put that in the global array
                     //so you can either take the topWeight from the column index to the left or the bottomWeight form the row index above
                     //Im thinking the only way to keep track of the direction would be to have something else in the global array 
                     //maybe a bool where true is up as the last and false when bottom was the last 
                     //so if it is false and you pull from the row above you incur no penalty but if it is true and you pull
                     //from the top row you do incur the penalty
                     //then if it is false and you pull from the left column you do incure the penalty and if it is true and 
                     //you pull from the left column you would not incur a penalty
                     else if(columnIndex > 0 && rowIndex > 0){
                         //these are the paths that can point to the current node 
                         //so you need to get the lastPathdirection from the node and 
                         Path abovePath = bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1];
                         Path belowPath = bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1];
 
                         int abovePathLastDirection = abovePath.getLastPath();
                         int belowPathLastDirection = belowPath.getLastPath();
 
                         int weightCommingFromAbove = 0;
                         int weightCommingFromBelow = 0;
                         int directionAbove = 2;
                         int directionBelow = 2;
                         int bestWeight = 0;
                         int direction = 2;
 
                         //the node above got pointed to by going down and will point down so it won't inncur a penalty
                         if(abovePathLastDirection == 1){
                             int potentialWeight1 = graph[rowIndex - 1][columnIndex].getTopWeight();
                             weightCommingFromAbove = potentialWeight1;
                             directionAbove = 1;
                         }
                         //the node above got pointed to by going up and will point down so it will inccur a penalty
                         else if(abovePathLastDirection == 0){
                             int potentialWeight1 = (graph[rowIndex - 1][columnIndex].getTopWeight()) + turnPenalty;
                             weightCommingFromAbove = potentialWeight1;
                             directionAbove = 0;
                         }
 
                         //the node below got pointed to by going up and will point up so it won't inncur a penalty
                         if(belowPathLastDirection == 0){
                             int potentialWeight2 = graph[rowIndex][columnIndex - 1].getBottomWeight();
                             weightCommingFromBelow = potentialWeight2;
                             directionBelow = 0;
                         }
                         //the node below got pointed to by going down and will poing up so it will inncur a penalty
                         else if(belowPathLastDirection == 1){
                             int potentialWeight2 = (graph[rowIndex][columnIndex - 1].getBottomWeight()) + turnPenalty;
                             weightCommingFromBelow = potentialWeight2;
                             directionBelow = 1;
                         }
 
                         if(weightCommingFromAbove > weightCommingFromBelow){
                             bestWeight = weightCommingFromAbove;
                             direction = directionAbove;
                         }
                         else{
                             bestWeight = weightCommingFromBelow;
                             direction = directionBelow;
                         }
 
                         bestRoute[p] = new Path(bestWeight, direction);
                         System.out.println(p + 1 + " " + bestWeight + " " + direction);
                         p++;
                     }
                }
            }

        }
        
    }
    
    public static void main(String[] args) throws Exception {
        // File path is passed as parameter
        File file = new File(
            "test.txt");
 
        BufferedReader br
            = new BufferedReader(new FileReader(file));

        String st = br.readLine();

        String[] strinums = st.split(" ");

        int[] nums = new int[strinums.length];

        int turnPenalty = Integer.parseInt(strinums[0]);
        int numberOfNodes = Integer.parseInt(strinums[1]);

        for(int i = 2; i < nums.length; i++){
            nums[i - 2] = Integer.parseInt(strinums[i]);
        }

        int k = numberOfNodes/3;

        Node[][] paths = fillOutDiagonal(k, nums);

        bestRoute = new Path[numberOfNodes];

        for(int i = 1; i < numberOfNodes; i++){
            GetBestPath(turnPenalty, paths, k);
        }

        System.out.println(bestRoute[numberOfNodes - 1].getWeight());

        // Set<Integer> all = bestRoute.keySet();
        // Integer[] arr = all.toArray(new Integer[all.size()]);

        // int minPath = Integer.MAX_VALUE;
        // for(int i = 0; i < all.size(); i++){
        //     if(minPath > arr[i]){
        //         minPath = arr[i];
        //     }
        // }
        // System.out.println(minPath);
    }
}
