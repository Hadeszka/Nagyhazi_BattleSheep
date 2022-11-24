import javax.swing.*;

public class GameVisual {
    JFrame gameFrame;
    public GameVisual(String title){
        gameFrame = new JFrame(title);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void createBoard(int size){
        Board board = new Board(size);
        JPanel boardPanel = board.getBoardPanel();
        gameFrame.add(boardPanel);
    }
    public void createMenu(){
        Menu menu = new Menu(gameFrame);
    }
    public void setUpGame(int width, int height){
        gameFrame.pack();
        gameFrame.setSize(width, height);
        gameFrame.setVisible(true);
    }
}
