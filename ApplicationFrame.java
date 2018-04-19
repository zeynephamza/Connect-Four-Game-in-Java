package hw8;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.*;

/**
 * This is a class which makes all the graphices methods, extends 
 *  JFrame for Java and implementations MouseListener for the mouse operations. 
 * @author ZEYNEP
 */

public class ApplicationFrame extends JFrame implements MouseListener {
   /**
    * Four main pages in the game 
    */
    private final static int SCENE_PLAYERS = 0;
    private final static int SCENE_SIZE = 1;
    private final static int SCENE_GAME = 2;
    private final static int SCENE_OVER = 3;

    private int sceneIndex = SCENE_PLAYERS;
    
    /**
     * Definitions of images to be used.
     */
    private Image twoPlayerImage;
    private Image onePlayerImage;
    private Image leftImage;
    private Image rightImage;
    private Image continueImage;
    private Image cellImage;
    private Image oImage;
    private Image xImage;
    private Image oWonImage;
    private Image xWonImage;
    private Image replayImage;
    
    /**
     *  Definetions and initiallizes some frames for the images 
     */
    private Rectangle twoPlayerRectangle = new Rectangle(500, 300, 154, 88);
    private Rectangle onePlayerRectangle = new Rectangle(300, 300, 166, 95);
    private Rectangle leftRectangle = new Rectangle(300, 200, 83, 87);
    private Point sizePoint = new Point(450, 250);
    private Rectangle rightRectangle = new Rectangle(500, 200, 83, 87);
    private Rectangle continueRectangle = new Rectangle(400, 350, 165, 95);
    private Rectangle replayRectangle = new Rectangle(600, 70, 167, 97);
            
    private Point playerPoint = new Point(300, 70);
    private Point tablePoint = new Point(100, 100);
    private Dimension cellDimension = new Dimension(92, 92);
     
    
    private char mode;
    private int size = 5;
    private ConnectFour connectFour = new ConnectFour();

    /**
     * Images loading
     */
    public ApplicationFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 800);
        twoPlayerImage = loadImage("/res/twoPlayer.png");
        onePlayerImage = loadImage("/res/onePlayer.png");
        leftImage = loadImage("/res/left.png");
        rightImage = loadImage("/res/right.png");
        continueImage = loadImage("/res/continue.png");
        cellImage = loadImage("/res/cell.png");
        oImage = loadImage("/res/o.png");
        xImage = loadImage("/res/x.png");
        oWonImage = loadImage("/res/owon.png");
        xWonImage = loadImage("/res/xwon.png");
        replayImage = loadImage("/res/replay.png");

        addMouseListener(this);
    }

    /**
     * Painting the write player turn, size, game over etc.
     * @param g parametre for graphics
     */
    @Override
    public void paint(Graphics g) {
        /**
         * backgroud color
         */
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        /**
         * Drawing the player choice scene */
        if (sceneIndex == SCENE_PLAYERS) {
            g.drawImage(twoPlayerImage, twoPlayerRectangle.x, twoPlayerRectangle.y, this);
            g.drawImage(onePlayerImage, onePlayerRectangle.x, onePlayerRectangle.y, this);
        } else if (sceneIndex == SCENE_SIZE) {
            g.drawImage(leftImage, leftRectangle.x, leftRectangle.y, this);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString(Integer.toString(size), sizePoint.x, sizePoint.y);
            g.drawImage(rightImage, rightRectangle.x, rightRectangle.y, this);
            g.drawImage(continueImage, continueRectangle.x, continueRectangle.y, this);

        } else if (sceneIndex == SCENE_GAME) {

            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.RED);
            
            g.drawString("Player "+Integer.toString(connectFour.getPlayer()), playerPoint.x, playerPoint.y);
            paintTable(g);
           
        } else if (sceneIndex == SCENE_OVER) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER!", playerPoint.x, playerPoint.y);
             paintTable(g);
             g.drawImage(replayImage, replayRectangle.x, replayRectangle.y, this);
        }

    }
    /**
     * Table initializing and drawing the game table
     * @param g parameter for graphics
     */
    private void paintTable(Graphics g){
         for (int i = 0; i < connectFour.getHeight(); i++) {
                for (int j = 0; j < connectFour.getWidth(); j++) {

                    int x = tablePoint.x + j * cellDimension.width;
                    int y = tablePoint.y + i * cellDimension.height;
                    Cell cell = connectFour.getGameCells().get(i).get(j);
                    if (cell.getValue() == '.') {
                        g.drawImage(cellImage, x, y, this);
                    } else if (cell.getValue() == 'X') {
                        g.drawImage(xImage, x, y, this);
                    } else if (cell.getValue() == 'O') {
                        g.drawImage(oImage, x, y, this);
                    } else if (cell.getValue() == 'x') {
                        g.drawImage(xWonImage, x, y, this);
                    } else if (cell.getValue() == 'o') {
                        g.drawImage(oWonImage, x, y, this);
                    } else {
                        System.out.println("Invalid Value!" + cell.getValue());
                    }
                }
            }
    }
    /**
     * 
     * @param imagePath string path for the loading image
     * @return image used to load
     */
    private Image loadImage(String imagePath) {
        URL imageUrl = getClass().getResource(imagePath);
        return Toolkit.getDefaultToolkit().getImage(imageUrl);
    }
    
    /**
     * 
     * @param e parametre for mouse operations
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (sceneIndex == SCENE_PLAYERS) { 
            if (twoPlayerRectangle.contains(e.getPoint())) {
                mode = 'P'; /* for pvp*/
                sceneIndex = SCENE_SIZE; /* for asking scene */
                repaint();
            } else if (onePlayerRectangle.contains(e.getPoint())) {
                mode = 'C'; /* for pvc */
                sceneIndex = SCENE_SIZE;/* for asking scene */
                repaint();
            }
            /* Boaer size scene */
        } else if (sceneIndex == SCENE_SIZE) {
            if (leftRectangle.contains(e.getPoint())) {
                size--; /* clicking the left side button */
                repaint();
            } else if (rightRectangle.contains(e.getPoint())) {
                size++;/* clicking the right side button */
                repaint();
                
            } else if (continueRectangle.contains(e.getPoint())) {
                sceneIndex = SCENE_GAME;
                connectFour.initializeGame(mode, size, size);
                repaint();
            }
        } else if (sceneIndex == SCENE_GAME) {
            /**
             * Finding the position to int
             */
            int j = (e.getPoint().x - tablePoint.x) / cellDimension.width;
            if (j < 0 || j >= connectFour.getWidth()) {
                return;
            }
            connectFour.findUser();
            if (!connectFour.moveStone(j, connectFour.getUser())) {
                System.err.println("Selected column is full!");

            } else {
                
                
            connectFour.changePlayer();
                System.out.println("MODE"+connectFour.getMode()+" PL"+connectFour.getPlayer());

                if (connectFour.getMode() == 'C' && connectFour.getPlayer() == 2) {
                    System.out.println("C P");

                    connectFour.play();
                    
                     connectFour.changePlayer();
                    repaint();
                }

                if (connectFour.checkGameOver()) {
                    System.out.println("GAME OVER!");
                    sceneIndex = SCENE_OVER;
                }
            }

        }
        else if(replayRectangle.contains(e.getPoint())){
            sceneIndex = SCENE_PLAYERS;
            repaint();
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        ApplicationFrame af = new ApplicationFrame();
        af.setVisible(true);
    }
}
