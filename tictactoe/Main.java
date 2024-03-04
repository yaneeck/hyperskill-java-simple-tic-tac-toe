package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static Scanner input = new Scanner(System.in);
	static String comment = "";

    public static void main(String[] args) {
		boolean isGameFinished = false;
		String gameStatus;

		// X begins the game
		char currentPlayer = 'X';
		char[][] board = initBoard();
		String previousBoard = "";

		while (! isGameFinished) {
			board = getInput(board, currentPlayer);
			gameStatus = checkStatus(board);

			if (gameStatus.equals("X wins") || gameStatus.equals("O wins") || gameStatus.equals("Draw")) {
				isGameFinished = true;
				System.out.println(gameStatus);
			}

			if (! previousBoard.equals(Arrays.deepToString(board))) {
				currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
			}

			previousBoard = Arrays.deepToString(board);
		}
	}

	private static char[][] initBoard() {
		char[][] board = new char[3][3];
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 3; j++) {
    			board[i][j] = ' ';
    		}
    	}
    	return board;
	}

	private static int calculateCells(char[][] board, char symbol) {
		int cells = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == symbol) {
					cells++;
            	}
			}
		}
		return cells;
	}

	private static String checkStatus(char[][] board) {
		boolean xWins = false;
		boolean oWins = false;

		int emptyCells = calculateCells(board, ' ');
		int xCells = calculateCells(board, 'X');
		int oCells = calculateCells(board, 'O');

		// check rows
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				if (board[i][0] == 'X') {
					xWins = true;
				} else if (board[i][0] == 'O') {
					oWins = true;
				}
			}
		}

		// check columns
		for (int j = 0; j < 3; j++) {
			if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
				if (board[0][j] == 'X') {
					xWins = true;
				} else if (board[0][j] == 'O') {
					oWins = true;
				}
			}
		}

		// check diagonals
		if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
			(board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
			if (board[1][1] == 'X') {
				return "X wins";
			} else if (board[1][1] == 'O') {
				return "O wins";
			} else {
				return "Game not finished";
			}
		}

		if (xCells - oCells > 1 || oCells - xCells > 1) {
			return "Impossible";
		} else if (oWins && xWins) {
			return "Impossible";
		} else if (oWins) {
			return "O wins";
		} else if (xWins) {
			return "X wins";
		}

		// game's on when there are empty cells
		if (emptyCells > 0) {
			return "Game not finished";
		}

		// last resort: draw
		return "Draw";
	}

	private static char[][] getInput(char[][] board, char currentPlayer) {
		String txt = input.nextLine();
		// incorrect symbols
		 if (txt.matches("\\D+")) {
			comment = "You should enter numbers!";
			System.out.println(comment);
		}
		// digits, but not between 1 and 3
		else if (txt.matches("^\\d+ \\d+$") && ! txt.matches("^[1-3] [1-3]$")) {
			comment = "Coordinates should be from 1 to 3!";
			System.out.println(comment);
		}
		// proper symbols: digits between 1 and 3
		else if (txt.matches("^[1-3] [1-3]$")) {
			int x = Integer.parseInt(txt.split(" ")[0]) - 1;
			int y = Integer.parseInt(txt.split(" ")[1]) - 1;
			// check if a chosen field is empty
			if (board[x][y] != ' ') {
				comment = "This cell is occupied! Choose another one!";
				System.out.println(comment);
			}
			// put a symbol in the chosen field
			else {
				board[x][y] = currentPlayer;
				comment = "";
				printBoard(board);
			}
		}

		return board;
	}

	public static void printBoard(char[][] board) {
		System.out.println("---------");
		for (int i = 0; i < 3; i++) {
			System.out.println("| " + board[i][0] + " " + board[i][1] + " " + board[i][2] + " |");
		}
		System.out.println("---------");
	}
}
