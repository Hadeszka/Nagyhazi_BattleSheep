import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    JPanel boardPanel;
    public GameFrame(String title){
        super(title);
        //this.setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardPanel = new JPanel(null);
    }
    public void createBoard(int size){
        Board board = new Board(size, boardPanel);
        //boardPanel = board.getBoardPanel();
        //add(boardPanel);
    }
    public void createMenu(){
        MainMenu menu = new MainMenu(boardPanel);
        //add(menu.getPanel());
    }
    public void setUpGame(int width, int height){
        add(boardPanel);
        pack();
        setSize(width, height);
        setVisible(true);
    }
}
