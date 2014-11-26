import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class Sudoku {
	static int[][] board;
	
	static boolean isValidBlock(int cellVal, int cellRow, int cellCol){
		int blockStartRow = (cellRow / 3) * 3;
		int blockStartCol = (cellCol / 3) * 3; 
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				if ((blockStartRow + i) != cellRow
					&&(blockStartCol + j) != cellCol
					&&board[blockStartRow + i][blockStartCol + j] == cellVal){
					return false;
				}
			}
		}
		return true;
	}

	static boolean isValidRow(int cellVal, int cellRow, int cellCol){
		for (int i = 0; i < 9; i++){
			if (i != cellCol && board[cellRow][i] == cellVal){
				return false;
			}
		}
		return true;
	}

	static boolean isValidCol(int cellVal, int cellRow, int cellCol){
		for (int i = 0; i < 9; i++){
			if (i != cellRow && board[i][cellCol] == cellVal){
				return false;
			}
		}
		return true;
	}

	static int solve(int row, int col){
		if (board[row][col] != 0){
			if (col < 8 && solve(row, col + 1) == 0){
				return 0;
			}
			else if (row < 8 && solve(row + 1, 0) == 0){
				return 0;
			}
			else {
				return board[row][col];
			}
		}
		else {
			for (int i = 1; i <= 9; i++){
				if (isValidBlock(i, row, col) && isValidRow(i, row, col) && isValidCol(i, row, col)){
					board[row][col] = i;
					if (col < 8 && solve(row, col + 1) == 0){
						board[row][col] = 0;
						continue;
					}
					else if (row < 8 && solve(row + 1, 0) == 0){
						board[row][col] = 0;
						continue;
					}
					else {
						return board[row][col];
					}
				}
			}
			return board[row][col];
		}
	}

	
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("sudoku_input"));
		board = new int[9][9];
		for (int i=0; i<9; i++){
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line);
			for (int j=0; j<9; j++){
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		br.close();
		solve(0, 0);
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		pr.println("+-----------------------------------+");
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (j == 0){
					pr.print("| ");
				}
				pr.print(board[i][j]);
				if (j == 8){
					pr.println(" |");
				}
				else {
					pr.print(" | ");
				}
			}
			if (i != 2 && i != 5 && i != 8){
				pr.println("|-----------|-----------|-----------|");
			}
			else if (i!=8) {
				pr.println("|===========|===========|===========|");
			}
			else {
				pr.println("+-----------------------------------+");
			}
		}
		pr.close();
	}
}
