import java.io.*;

//contributors: MAX GRIMM, Scott Weber, Sean Stile
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
                    columnIndex = (i - j) - 1;
                    rowIndex = j;
                    //if the number is on the last column it can only point down
                    if(columnIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setBottomWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex++;
                    }
                    //if the number is on the last row it can only point up
                    else if(rowIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setTopWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex++;
                    }
                    //all other nodes that aren't in the right column or the bottom row will have 2 weights
                    else{
                        Node currentNode = new Node(p, arr[arrIndex], arr[arrIndex + 1]); 
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex = arrIndex + 2;
                    }
                    p++;
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    columnIndex = (sideLength - 1) - j;
                    rowIndex = (i - sideLength) + j;
                    //if the number is on the last column it can only point down
                    //this shouldn't ever happen here but just to be safe;
                    if(columnIndex + 1 == sideLength && rowIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex++;
                    }
                    else if(columnIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setBottomWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex++;
                    }
                    //if the number is on the last row it can only point up
                    else if(rowIndex + 1 == sideLength){
                        Node currentNode = new Node(p);
                        currentNode.setTopWeight(arr[arrIndex]);
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex++;
                    }
                    //all other nodes that aren't in the right column or the bottom row will have 2 weights
                    else{
                        Node currentNode = new Node(p, arr[arrIndex], arr[arrIndex + 1]); 
                        diagonal[rowIndex][columnIndex] = currentNode;
                        System.out.println("row" + rowIndex + " " + "column" + columnIndex + "node" + diagonal[rowIndex][columnIndex].getName());
                        arrIndex = arrIndex + 2;
                    }
                    p++;
                }
            }
        }

        for(int i = 0; i < sideLength; i++){
            for(int k = 0; k < sideLength; k++){ 
                System.out.print(diagonal[i][k].getName() + "(");
                    System.out.print(diagonal[i][k].getTopWeight() + ", ");
                    System.out.print(diagonal[i][k].getBottomWeight());
                    System.out.print(") ");
            }
            System.out.println();
        }
        return diagonal;
    }


    //bottom up approach does not work because the turn penalty can't be foretold unless you store both options for the weights and do all of them
    public static void GetBestPath(int turnPenalty, Node[][] graph, int sideLength){
        int neither = 2;
        int up = 0;
        int down = 1;
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
                    columnIndex = (i - j) - 1;
                    rowIndex = j;
                    if(rowIndex + columnIndex == 0){
                       bestRoute[p] = new Path(0, neither); 
                       System.out.println("the first node");
                       p++;
                    }
                    //if they are on the first row and not the first column they only have one way to get to the node
                    //ie the previous node's pointer up
                    else if(rowIndex == 0 && columnIndex > 0){
                        int topRow = graph[rowIndex][columnIndex - 1].getTopWeight();
                        System.out.println(topRow + "top");
                        //this way of retreiving is crucial
                        //the -1 is because the name is placed at the name -1 index
                        if(columnIndex == sideLength - 1){
                            Path current = new Path(topRow + bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1].getWeight() + turnPenalty, down);
                            bestRoute[p] = current;
                        }
                        else{
                            Path current = new Path(topRow + bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1].getWeight(), up);
                            bestRoute[p] = current;
                        }
                        p++;
                        //get the previous weight from the way we are storing it in a list and add that to the topRow and store that in a list
                    }
                    //if they are in the fist column and not the first row they only have one way to get to the node
                    //ie the previous node's pointer down
                    else if(columnIndex == 0 && rowIndex > 0){
                        int bottomRow = graph[rowIndex - 1][columnIndex].getBottomWeight();
                        System.out.println(bottomRow + "bot");
                        if(rowIndex == sideLength - 1){
                            Path current = new Path(bottomRow + bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1].getWeight() + turnPenalty, up);
                            bestRoute[p] = current;
                        }
                        else{
                            Path current = new Path(bottomRow + bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1].getWeight(), down);
                            bestRoute[p] = current;
                        }
                        p++;
                    }
                    else if(columnIndex > 0 && rowIndex > 0){
                          //the node that is below the one we are trying to get
                          Path abovePath = bestRoute[graph[rowIndex][columnIndex -1].getName() - 1];
                          int belowNodeTopWeight = graph[rowIndex][columnIndex -1].getTopWeight();
                          System.out.println("below node top weight" + belowNodeTopWeight);
                          int belowNodeLastDirection = abovePath.getLastPath();
                           
                          //the node that is above the one we are trying to get
                          Path belowPath = bestRoute[graph[rowIndex -1][columnIndex].getName() - 1];
                          int aboveNodeBottomWeight = graph[rowIndex -1][columnIndex].getBottomWeight();
                          System.out.println("above node bottom Weight" + aboveNodeBottomWeight);
                          int aboveNodeLastDirection = belowPath.getLastPath();
   
  
                          Node curNode = graph[rowIndex][columnIndex];
                          System.out.println("current node" + curNode.getName());
                          int curNodeTopWeight = curNode.getTopWeight();
                          System.out.println(curNodeTopWeight);
                          int curNodeBottomWeight = curNode.getBottomWeight();
                          System.out.println(curNodeBottomWeight);
  
                          boolean wasNotMeantToBe = false;
  
                          int weightUsingBelowNode = 0;
                          int weightUsingAboveNode = 0;
  
                          int bestWeight = 0;
                          int direction;
  
                          //no penalty 
                          if(belowNodeLastDirection == 0){
                              //if it can't point in the same direction as the node it comes from
                              //preemptivley add the turnPenalty before comparison
                              if(curNodeTopWeight == 0){
                                  weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight() + turnPenalty;
                                  wasNotMeantToBe = true;
                              }
                              else{
                                  weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight();
                              }
                          }
                          //yes penalty
                          else{
                              if(curNodeBottomWeight == 0){
                                  weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight() + turnPenalty * 2;
                                  wasNotMeantToBe = true;
                              }
                              else{
                                  weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight() + turnPenalty;
                              }
                          }
                          //no penalty
                          if(aboveNodeLastDirection == 1){
                              if(curNodeBottomWeight == 0){
                                  weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight() + turnPenalty;
                                  wasNotMeantToBe = true;
                              }
                              else{
                                  weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight();
                              } 
                          }
                          //yes penalty
                          else{
                              if(curNodeTopWeight == 0){
                                  weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight() + turnPenalty * 2;
                                  wasNotMeantToBe = true;
                              }
                              else{
                                 weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight() + turnPenalty; 
                              }
                          }
                          
                          if(weightUsingBelowNode > weightUsingAboveNode){
                              direction = 0;
                              if(wasNotMeantToBe){
                                bestWeight = weightUsingBelowNode - turnPenalty;
                              }    
                            bestWeight = weightUsingBelowNode;
                          }
                          else{
                              direction = 1;
                              if(wasNotMeantToBe){
                                bestWeight = weightUsingAboveNode - turnPenalty;
                              }  
                            bestWeight = weightUsingAboveNode;
                          }
                           bestRoute[p] = new Path(bestWeight, direction);
                           p++;
                    }
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    columnIndex = (length - 1) - j;
                    rowIndex = (i - length) + j;
                    if(rowIndex + columnIndex == 0){
                        bestRoute[p] = new Path(0, neither); 
                        p++;
                     }
                     //if they are on the first row and not the first column they only have one way to get to the node
                     //ie the previous node's pointer up
                     else if(rowIndex == 0 && columnIndex > 0){
                         int topRow = graph[rowIndex][columnIndex - 1].getTopWeight();
                         System.out.println(topRow + "top");
                         //this way of retreiving is crucial
                         //the -1 is because the name is placed at the name -1 index
                         if(columnIndex == sideLength - 1){
                            Path current = new Path(topRow + bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1].getWeight() + turnPenalty, down);
                            bestRoute[p] = current;
                         }
                         else{
                            Path current = new Path(topRow + bestRoute[graph[rowIndex][columnIndex - 1].getName() - 1].getWeight(), up);
                            bestRoute[p] = current;
                         }
                         p++;
                         //get the previous weight from the way we are storing it in a list and add that to the topRow and store that in a list
                     }
                     //if they are in the fist column and not the first row they only have one way to get to the node
                     //ie the previous node's pointer down
                     else if(columnIndex == 0 && rowIndex > 0){
                         int bottomRow = graph[rowIndex - 1][columnIndex].getBottomWeight();
                         System.out.println(bottomRow + "bot");
                         if(rowIndex == sideLength - 1){
                            Path current = new Path(bottomRow + bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1].getWeight() + turnPenalty, up);
                            bestRoute[p] = current;
                         }
                         else{
                            Path current = new Path(bottomRow + bestRoute[graph[rowIndex - 1][columnIndex].getName() - 1].getWeight(), down);
                            bestRoute[p] = current;
                         }
                         p++;
                     }
                     else if(columnIndex > 0 && rowIndex > 0){
                        //the node that is below the one we are trying to get
                        Path abovePath = bestRoute[graph[rowIndex][columnIndex -1].getName() - 1];
                        int belowNodeTopWeight = graph[rowIndex][columnIndex -1].getTopWeight();
                        int belowNodeLastDirection = abovePath.getLastPath();
                         
                        //the node that is above the one we are trying to get
                        Path belowPath = bestRoute[graph[rowIndex -1][columnIndex].getName() - 1];
                        int aboveNodeBottomWeight = graph[rowIndex -1][columnIndex].getBottomWeight();
                        int aboveNodeLastDirection = belowPath.getLastPath();
 

                        Node curNode = graph[rowIndex][columnIndex];
                        System.out.println("current node" + curNode.getName());
                        int curNodeTopWeight = curNode.getTopWeight();
                        System.out.println(curNodeTopWeight);
                        int curNodeBottomWeight = curNode.getBottomWeight();
                        System.out.println(curNodeBottomWeight);

                        boolean wasNotMeantToBe = false;

                        int weightUsingBelowNode = 0;
                        int weightUsingAboveNode = 0;

                        int bestWeight = 0;
                        int direction;

                        //no penalty 
                        if(belowNodeLastDirection == 0){
                            //if it can't point in the same direction as the node it comes from
                            //preemptivley add the turnPenalty before comparison
                            if(curNodeTopWeight == 0){
                                weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight() + turnPenalty;
                                wasNotMeantToBe = true;
                            }
                            else{
                                weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight();
                            }
                        }
                        //yes penalty
                        else{
                            if(curNodeBottomWeight == 0){
                                weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight() + turnPenalty * 2;
                                wasNotMeantToBe = true;
                            }
                            else{
                                weightUsingBelowNode = belowNodeTopWeight + abovePath.getWeight() + turnPenalty;
                            }
                        }
                        //no penalty
                        if(aboveNodeLastDirection == 1){
                            if(curNodeBottomWeight == 0){
                                weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight() + turnPenalty;
                                wasNotMeantToBe = true;
                            }
                            else{
                                weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight();
                            } 
                        }
                        //yes penalty
                        else{
                            if(curNodeTopWeight == 0){
                                weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight() + turnPenalty * 2;
                                wasNotMeantToBe = true;
                            }
                            else{
                               weightUsingAboveNode = aboveNodeBottomWeight + belowPath.getWeight() + turnPenalty; 
                            }
                        }

                        if(weightUsingBelowNode > weightUsingAboveNode){
                            direction = 0;
                            if(wasNotMeantToBe){
                                bestWeight = weightUsingBelowNode - turnPenalty; 
                            }
                            bestWeight = weightUsingBelowNode;
                        }
                        else{
                            direction = 1;
                            if(wasNotMeantToBe){
                                bestWeight = weightUsingAboveNode - turnPenalty;
                            }  
                            bestWeight = weightUsingAboveNode;
                        }

                        //turn penalty is added an extra turn penalty so we get rid of it
                        if(columnIndex == sideLength -1 && rowIndex == sideLength -1){
                            bestWeight = bestWeight - turnPenalty;
                        }

                         bestRoute[p] = new Path(bestWeight, direction);
                         p++;
                     }
                }
            }

        }
        
    }

    public static void GetBestPathTopDown(int turnPenalty, Node[][] graph, int sideLength){
        int length = sideLength;
        int diagonalLines = (length + length) - 1;
        int midPoint = (diagonalLines / 2) + 1;
        int itemsInDiagonal = 0;
        int p = 0;

        for (int i = diagonalLines; i > 1; i--) {
            int rowIndex;
            int columnIndex;

            
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

        System.out.println("turn penalty: " + turnPenalty);

        GetBestPath(turnPenalty, paths, k);

        for(int i = 0; i < bestRoute.length; i++){
            System.out.println(i+1 + ": " + bestRoute[i].getWeight() + " " + bestRoute[i].getLastPath());
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
