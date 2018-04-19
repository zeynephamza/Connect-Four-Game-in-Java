package hw8;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConnectFour {

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random(System.currentTimeMillis());
    public final static int connectedStones = 4;
    private List< List<Cell>> gameCells = new ArrayList<>();
    private boolean gameOver;
    private int width;
    private int height;
    private int player;
    private char mode;
    private char position;
    private char user;
    private int j;

    public void displayBoard() {

        for (j = 0; j < width; j++) {
            System.out.print((char) ('A' + j) + " ");
        }
        System.out.println();

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                System.out.print(gameCells.get(i).get(j).getValue());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void initializeGame(char mode,int width, int height ){
        this.mode=mode;

        setWidth(width);
        setHeight(height);
        /* initialize the 2D vector */
        gameCells = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell('.'));
            }

            gameCells.add(row);
        }

        displayBoard();

        player = 1;
        gameOver = false;

    }

    public void findUser(){
          if (player == 1) {
            user = 'X';
            /* First player's stone */
        } else {
            user = 'O';
        }
    }
    public void changePlayer(){
        if (player == 1)/*Changing the turn*/ {
            player = 2;
        } else {
            player = 1;
        }

    }
     public boolean checkGameOver(){
         gameOver = checkGameOver(user);
         return gameOver;
     }
    
    public void playTurn() {
        if (player == 1) {
            user = 'X';
            /* First player's stone */
        } else {
            user = 'O';
        }
        if (mode == 'C' && player == 2) {
            play();
        } else {
            System.out.print("Player " + player + " position: "); /* which player's turn*/
            position=scanner.next().charAt(0);
            play(position, user);
        }

        gameOver = checkGameOver(user);

        if (player == 1)/*Changing the turn*/ {
            player = 2;
        } else {
            player = 1;
        }

        //displayBoard();
    }
/*
    public void playGame(char mode) {

        initializeGame(char mode);

        while (!isGameOver()) {
            playTurn();
        }
    }
*/
    public boolean checkGameOver(char user) {

        if (checkStone(user)) {
            System.out.println("Player " + player + " won!");
            return true;
        } else if (checkFull()) {
            System.out.println("Draw! All board cells are full.");
            return true;
        } else {
            return false;
        }

    }

    /* Checks and sets in same column, same cross and returns 1 if there are 
		4 stones next to each other */
    public boolean checkStone(char s) {
        int i, j;

        for (i = 0; i < height; i++) {
            for (j = 0; j < width; j++) {
                if (sameRow(i, j, s)) {
                    markRow(i, j, s);
                    return true;
                } else if (sameColumn(i, j, s)) {
                    markColumn(i, j, s);
                    return true;
                } else if (sameAscending(i, j, s)) {
                    markAscending(i, j, s);
                    return true;
                } else if (sameDiscending(i, j, s)) {
                    markDiscending(i, j, s);
                    return true;
                }
            }
        }

        return false;

    }

   /**
    * Calculates if the four stones next to each other in same rows, 
		same column, same cross
   * Aynı satırda mı denetler
   * @param i Satır numarası.
   * @param j sütun numarası.
   * @param s stone in the cell X or O
   * @return Aynı sırada olup olmadığı
   */
    
    public boolean sameRow(int i, int j, char s) {
        boolean same = false;
        int k;

        if (j + connectedStones > width) {
            return false;
        }
        if (gameCells.get(i).get(j).getValue() == s) {

            same = true;
            for (k = 1; k < connectedStones && j + k < width; k++) {
                if (gameCells.get(i).get(j + k).getValue() != s) {
                    same = false;
                    break;
                }
            }
        }
        return same;
    }
   /**
   * Aynı sütunda mı denetler
   * @param i Satır numarası.
   * @param j sütun numarası.
   * @param s stone in the cell X or O
   * @return Aynı sütunda olup olmadığı
   */
    public boolean sameColumn(int i, int j, char s) {
        boolean same = false;
        int k;
        if (i + connectedStones > height) {
            return false;
        }
        if (gameCells.get(i).get(j).getValue() == s) {
            same = true;
            for (k = 1; k < connectedStones && i + k < height; k++) {
                if (gameCells.get(i + k).get(j).getValue() != s) {

                    same = false;
                    break;
                }
            }
        }
        return same;
    }
    /**
   * Çapraza azalarak gidenleri denetler
   * @param i Satır numarası.
   * @param j sütun numarası.
   * @param s stone in the cell X or O
   * @return Aynı sırada olup olmadığı
   */
    public boolean sameAscending(int i, int j, char s) {
        boolean same = false;
        int k;

        if (i + connectedStones > height || j + connectedStones > width) {
            return false;
        }

        if (gameCells.get(i).get(j).getValue() == s) {
            same = true;
            for (k = 1; k < connectedStones && i + k < height && j + k < width; k++) {
                if (gameCells.get(i + k).get(j + k).getValue() != s) {
                    same = false;
                    break;
                }
            }
        }
        return same;
    }

    public boolean sameDiscending(int i, int j, char s) {
        boolean same = false;
        int k;

        if (i - connectedStones <=-1  || j + connectedStones >width) {
            return false;
        }

        if (gameCells.get(i).get(j).getValue() == s) {
            same = true;
            for (k = 1; k < connectedStones && i - k >= 0 && j + k < width; k++) {
                if (gameCells.get(i - k).get(j + k).getValue() != s) {
                    same = false;
                    break;
                }
            }
        }
        return same;
    }
    /**
     * Converting the stone to small which player has been won
     * @param i row number
     * @param j column number
     * @param s entered stone ( X or O )
     */
    public void markRow(int i, int j, char s) {
        char ss = (char) (s + ('a' - 'A'));
        for (int k = 0; k < connectedStones && j + k < width; k++) {
            gameCells.get(i).get(j + k).setValue(ss);
        }
    }
    /**
     * Converting the stone to small which player has been won
     * @param i row number
     * @param j column number
     * @param s entered stone ( X or O )
     */
    public void markColumn(int i, int j, char s) {
        char ss = (char) (s + ('a' - 'A'));
        for (int k = 0; k < connectedStones && i + k < height; k++) {
            gameCells.get(i + k).get(j).setValue(ss);
        }
    }
    /**
     * Converting the stone to small which player has been won
     * @param i row number
     * @param j column number
     * @param s entered stone ( X or O )
     */
    public void markAscending(int i, int j, char s) {
        char ss = (char) (s + ('a' - 'A'));
        for (int k = 0; k < connectedStones && i + k < height; k++) {
            gameCells.get(i + k).get(j + k).setValue(ss);
        }
    }
    /**
     * Converting the stone to small which player has been won
     * @param i row number
     * @param j column number
     * @param s entered stone ( X or O )
     */
    public void markDiscending(int i, int j, char s) {
        char ss = (char) (s + ('a' - 'A'));
        for (int k = 0; k < connectedStones && i - k >= 0 && j + k < width; k++) {
            gameCells.get(i - k).get(j+k).setValue(ss);
        }
    }
    /**
     * 
     * @return if the all cells are full
     */
    public boolean checkFull() {
        boolean full = true;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (gameCells.get(i).get(j).getValue() == '.') {
                    full = false;
                }
            }
        }

        return full;
    }
    /**
     * For PVP play
     * @param position for endered value
     * @param user 1 or 2
     */
    public void play(char position, char user) {
        int j;
        if (position == 's' || position == 'l') {
            return;
        }
        j = ((int) (position - 'A'));
        /* player's move converts to integer */

        if (j < 0 || j >= width) {
            System.err.println("Invalid column: " + position);
            System.out.print("Player " + player + " position: ");
            /* which player's turn*/
            position = scanner.next().charAt(0);;
            play(position, user);
        }
        if (!moveStone(j, user)) {
            System.err.println("Selected column is full!");
            System.out.print("Player " + player + " position: ");
            /* which player's turn*/
            position = scanner.next().charAt(0);
            play(position, user);
        }

    }
    /**
     * For PVC playing
     */
    public void play() {
        char position;
        int j;
        position = (char) ('A' + ( Math.abs(random.nextInt()) % width));
        /*computer move is 0 to board size*/
        j = ((int) (position - 'A'));
        /* player's move converts to integer */
        if (!moveStone(j, 'O')) {
            play();
        }
    }

    /**
     * 
     * @param j position
     * @param user X or O
     * @return legality 
     */
    public boolean moveStone(int j, char user) {
        int i;
        for (i = height - 1; i >= 0; i--) {

            if (gameCells.get(i).get(j).getValue() == '.') {

                gameCells.get(i).get(j).setValue(user);
                break;
            }
        }
        if (i < 0) {
            return false;
        } else {
            return true;
        }

    }
    public boolean isGameOver() {
        return gameOver;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public List<List<Cell>> getGameCells() {
        return gameCells;
    }

    public int getPlayer() {
        return player;
    }

    public char getUser() {
        return user;
    }

    public char getMode() {
        return mode;
    }
    
    

}
