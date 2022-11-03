import java.util.ArrayList;
public class App {

    public static void fillOutDiagonal(int sideLength){
        int diagonalLines = (sideLength + sideLength) - 1;
        int midPoint = (diagonalLines / 2) + 1;
        int[][] diagonal = new int[sideLength][sideLength];

        int itemsInDiagonal = 0;
        int p = 1;
        for (int i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int columnIndex;

            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (i - j) - 1;
                    columnIndex = j;
                    diagonal[rowIndex][columnIndex] = p;
                    p++;
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (sideLength - 1) - j;
                    columnIndex = (i - sideLength) + j;
                    diagonal[rowIndex][columnIndex] = p;
                    p++;
                }
            }
        }
        for(int i = 0; i < sideLength; i++){
            for(int k = 0; k < sideLength; k++){ 
                System.out.print(diagonal[k][i]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws Exception {
        fillOutDiagonal(4);
    }
}
