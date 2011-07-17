/* % java -cp . Sudoku    018 034 052 076 \
* 	                      113 124 169 171 \
*   	                  209 216 278 284 \
*       	              332 341 356     \
*           	          533 545 557     \
*               	      608 614 677 685 \
*                   	  712 726 761 773 \
*                    	  819 837 851 874 */
package lu.paulbertemes.sudokusolver;

public class Sudoku 
{
	//Main Function
	public static void main(String[] args) 
	{
//		args[0] = {"018","034","052" ,"076"};
////		args[1] = "113 124 169 171";
////		args[2] = "209 216 278 284";
////		args[3] = "332 341 356 000";
////		args[4] = "533 545 557 000";
////		args[5] = "608 614 677 685";
////		args[6] = "712 726 761 773";
////		args[7] = "819 837 851 874";
//		
       int[][] matrix = parseProblem(args);
       writeMatrix(matrix);
       
       if (solve(0,0,matrix))    // solves in place
           writeMatrix(matrix);
       else
           System.out.println("NONE");
	}

	//Solve SudokuBoard
    static boolean solve(int i, int j, int[][] cells) 
    {
       if (i == 9) 
       {
           i = 0;
           if (++j == 9) return true;
       }
       // skip filled cells
       if (cells[i][j] != 0) return solve(i+1,j,cells);

       for (int val = 1; val <= 9; ++val) 
       {
           if (legal(i,j,val,cells)) 
           {
               cells[i][j] = val;
               if (solve(i+1,j,cells))
                   return true;
           }
       }
       cells[i][j] = 0; // reset on backtrack
       return false;
   }

   static boolean legal(int i, int j, int val, int[][] cells) {
       for (int k = 0; k < 9; ++k)  // row
           if (val == cells[k][j])
               return false;

       for (int k = 0; k < 9; ++k) // col
           if (val == cells[i][k])
               return false;

       int boxRowOffset = (i / 3)*3;
       int boxColOffset = (j / 3)*3;
       for (int k = 0; k < 3; ++k) // box
           for (int m = 0; m < 3; ++m)
               if (val == cells[boxRowOffset+k][boxColOffset+m])
                   return false;

       return true; // no violations, so it's legal
   }

   static int[][] parseProblem(String[] args) {
       int[][] problem = new int[9][9]; // default 0 vals
       for (int n = 0; n < args.length; ++n) {
           int i = Integer.parseInt(args[n].substring(0,1));
           int j = Integer.parseInt(args[n].substring(1,2));
           int val = Integer.parseInt(args[n].substring(2,3));
           problem[i][j] = val;
       }
       return problem;
   }

   static void writeMatrix(int[][] solution) {
       for (int i = 0; i < 9; ++i) {
           if (i % 3 == 0)
               System.out.println(" -----------------------");
           for (int j = 0; j < 9; ++j) {
               if (j % 3 == 0) System.out.print("| ");
               System.out.print(solution[i][j] == 0
                                ? " "
                                : Integer.toString(solution[i][j]));

               System.out.print(' ');
           }
           System.out.println("|");
       }
       System.out.println(" -----------------------");
   }

}
