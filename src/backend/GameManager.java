package backend;

import java.util.Random;

public class GameManager {

    private char[][] board;
    private char player = 'X';
    private char computer = 'O';
    private Random random;

    public GameManager() {
        board = new char[3][3];
        random = new Random();
        resetBoard();
    }

    // Reset the board
    public void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    // Player move
    public boolean playerMove(int r, int c) {
        if (board[r][c] == ' ') {
            board[r][c] = player;
            return true;
        }
        return false;
    }

    // Computer move (simple AI)
    public int[] computerMove() {
        // Try blocking player's winning move
        int[] blockMove = findBlockingMove();
        if (blockMove != null) {
            board[blockMove[0]][blockMove[1]] = computer;
            return blockMove;
        }

        // Otherwise place randomly
        int r, c;
        do {
            r = random.nextInt(3);
            c = random.nextInt(3);
        } while (board[r][c] != ' ');

        board[r][c] = computer;
        return new int[]{r, c};
    }

    // Look for blocking move
    private int[] findBlockingMove() {
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (count(player, i, 0, i, 1, i, 2) == 2) {
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == ' ')
                        return new int[]{i, j};
            }

            // Check columns
            if (count(player, 0, i, 1, i, 2, i) == 2) {
                for (int j = 0; j < 3; j++)
                    if (board[j][i] == ' ')
                        return new int[]{j, i};
            }
        }

        // Diagonals
        if (count(player, 0,0, 1,1, 2,2) == 2) {
            if (board[0][0] == ' ') return new int[]{0,0};
            if (board[1][1] == ' ') return new int[]{1,1};
            if (board[2][2] == ' ') return new int[]{2,2};
        }

        if (count(player, 0,2, 1,1, 2,0) == 2) {
            if (board[0][2] == ' ') return new int[]{0,2};
            if (board[1][1] == ' ') return new int[]{1,1};
            if (board[2][0] == ' ') return new int[]{2,0};
        }

        return null;
    }

    private int count(char symbol, int r1, int c1, int r2, int c2, int r3, int c3) {
        int count = 0;
        if (board[r1][c1] == symbol) count++;
        if (board[r2][c2] == symbol) count++;
        if (board[r3][c3] == symbol) count++;
        return count;
    }

    // Check for winner
    public char checkWinner() {
        // Rows, columns, diagonals
        for (int i = 0; i < 3; i++) {
            if (same(board[i][0], board[i][1], board[i][2])) return board[i][0];
            if (same(board[0][i], board[1][i], board[2][i])) return board[0][i];
        }

        if (same(board[0][0], board[1][1], board[2][2])) return board[0][0];
        if (same(board[0][2], board[1][1], board[2][0])) return board[1][1];

        return ' ';
    }

    private boolean same(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }

    // Check draw
    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return false;
        return checkWinner() == ' ';
    }
}
