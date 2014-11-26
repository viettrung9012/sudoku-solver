// basic file operations
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

bool isValidBlock(int board[][9], int cellVal, int cellRow, int cellCol){
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

bool isValidRow(int board[][9], int cellVal, int cellRow, int cellCol){
	for (int i = 0; i < 9; i++){
		if (i != cellCol && board[cellRow][i] == cellVal){
			return false;
		}
	}
	return true;
}

bool isValidCol(int board[][9], int cellVal, int cellRow, int cellCol){
	for (int i = 0; i < 9; i++){
		if (i != cellRow && board[i][cellCol] == cellVal){
			return false;
		}
	}
	return true;
}

int solve(int board[][9], int row, int col){
	if (board[row][col] != 0){
		int nextCell;
		if (col < 8 && solve(board, row, col + 1) == 0){
			return 0;
		}
		else if (row < 8 && solve(board, row + 1, 0) == 0){
			return 0;
		}
		else {
			return board[row][col];
		}
	}
	else {
		for (int i = 1; i <= 9; i++){
			if (isValidBlock(board, i, row, col) && isValidRow(board, i, row, col) && isValidCol(board, i, row, col)){
				board[row][col] = i;
				if (col < 8 && solve(board, row, col + 1) == 0){
					board[row][col] = 0;
					continue;
				}
				else if (row < 8 && solve(board, row + 1, 0) == 0){
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

int main() {
	int board[9][9];
	ifstream myfile("sudoku_input");
	streambuf *cinbuf = cin.rdbuf(); // save old buf
	cin.rdbuf(myfile.rdbuf()); // redirect std::cin to sudoku_input.txt
	if (myfile.is_open())
	{
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				cin >> board[i][j];
			}
		}
		myfile.close();
	}
	solve(board, 0, 0);
	cout << "+-----------------------------------+" << endl;
	for (int i = 0; i < 9; i++){
		for (int j = 0; j < 9; j++){
			if (j == 0){
				cout << "| ";
			}
			cout << board[i][j];
			if (j == 8){
				cout << " |" << endl;
			}
			else {
				cout << " | ";
			}
		}
		if (i != 2 && i != 5 && i != 8){
			cout << "|-----------|-----------|-----------|" << endl;
		}
		else if (i!=8) {
			cout << "|===========|===========|===========|" << endl;
		}
		else {
			cout << "+-----------------------------------+" << endl;
		}
	}
	return 0;
}