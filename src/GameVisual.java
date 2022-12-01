import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GameVisual implements Serializable {
    private final Menu menu;
    public GameVisual(Menu menu) {
        this.menu = menu;
    }

    public Board StartGame(){
        JFrame gameFrame = new JFrame("BattleSheep");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board map = createBoard(gameFrame);
        createMenu(gameFrame);
        setUpGame(gameFrame, 1000, 700);
        return map;
    }
    public Board createBoard(JFrame gameFrame){
        Board board = new Board();
        JPanel boardPanel = board.getBoardPanel();
        gameFrame.add(boardPanel);
        return board;
    }
    public void createMenu(JFrame gameFrame){
        menu.createMenu(gameFrame);
    }
    public void setUpGame(JFrame gameFrame, int width, int height){
        gameFrame.pack();
        gameFrame.setSize(width, height);
        gameFrame.setVisible(true);
    }
    public void endGame(Player winner){
        JFrame frame = new JFrame("Game Over");
        JLabel label = new JLabel("The winner is: " + winner.getColor());
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(label);
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
