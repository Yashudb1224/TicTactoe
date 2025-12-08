package backend;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private char[][] board;
    private final char player = 'X';
    private final char computer = 'O';
    private Random random;
    private String difficulty;

    public GameManager(String difficulty) {
        this.difficulty = difficulty;
        board = new char[3][3];
        random = new Random();
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
    }

    public boolean playerMove(int r, int c) {
        if (board[r][c] == ' ') {
            board[r][c] = player;
            return true;
        }
        return false;
    }

    public int[] computerMove() {
        int[] move;
        
        switch (difficulty) {
            case "Easy":
                move = easyMove();
                break;
            case "Medium":
                move = mediumMove();
                break;
            case "Hard":
                move = hardMove();
                break;
            default:
                move = hardMove();
                break;
        }
        
        board[move[0]][move[1]] = computer;
        return move;
    }

    // Easy: Random moves
    private int[] easyMove() {
        ArrayList<int[]> available = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    available.add(new int[]{i, j});
                }
            }
        }
        
        return available.get(random.nextInt(available.size()));
    }

    // Medium: Try to block player, otherwise random
    private int[] mediumMove() {
        // First, check if player is about to win and block
        int[] blockMove = findBlockingMove();
        if (blockMove != null) {
            return blockMove;
        }
        
        // Check if computer can win
        int[] winMove = findWinningMove();
        if (winMove != null) {
            return winMove;
        }
        
        // Otherwise, random move
        return easyMove();
    }

    // Hard: Use minimax algorithm
    private int[] hardMove() {
        return findBestMove();
    }

    private int[] findBlockingMove() {
        // Check if player is about to win and block
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    if (checkWinner() == player) {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }
        return null;
    }

    private int[] findWinningMove() {
        // Check if computer can win in next move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = computer;
                    if (checkWinner() == computer) {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }
        return null;
    }

    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = computer;
                    int score = minimax(0, false);
                    board[i][j] = ' ';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizing) {
        char winner = checkWinner();

        if (winner == computer) return 10 - depth;
        if (winner == player) return depth - 10;
        if (isDraw()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = computer;
                        int score = minimax(depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = player;
                        int score = minimax(depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public char checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (same(board[i][0], board[i][1], board[i][2])) return board[i][0];
            if (same(board[0][i], board[1][i], board[2][i])) return board[0][i];
        }

        if (same(board[0][0], board[1][1], board[2][2])) return board[0][0];
        if (same(board[0][2], board[1][1], board[2][0])) return board[0][2];

        return ' ';
    }

    private boolean same(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return false;
        return checkWinner() == ' ';
    }
}