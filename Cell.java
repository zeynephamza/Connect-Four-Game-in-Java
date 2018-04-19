
package hw8;
/**
 * 
 * @author ZEYNEP
 * 
 */
public class Cell {

    private char position;
    private int row;
    private char value;

    /**
     * Default constructor
     */
    public Cell() {
        position = 'A';
        row = 0;
        value = '.';
    }
    /**
     * 
     * @param p for cell's position
     * @param r for row
     * @param v for value (X or O)
     */
    public Cell(char p, int r, char v) {
        position = p;
        row = r;
        value = v;
    }

    public Cell(char p, int r) {
        position = p;
        row = r;
    }

    public Cell(char v) {
        value = v;
    }
    
    public char getPosition() {
        return position;
    }

    public void setPosition(char position) {
        this.position = position;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
    
    
}
