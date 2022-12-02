import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class GameVisual implements Serializable {
    private final Menu menu;
    public GameVisual(Menu menu) {
        this.menu = menu;
    }

    /**
     * A játék elindításakor kirajzolja a játék elemeit:
     * Létrehozza az ablakot, ahol a játék zajlik majd,
     * létrehozza a menüt, illetve a táblát,
     * @return
     * visszaadja a kirajzolt táblát
     */
    public Board StartGame(){
        JFrame gameFrame = new JFrame("BattleSheep");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Board map = createBoard(gameFrame);
        createMenu(gameFrame);
        setUpGame(gameFrame, 1000, 700);
        return map;
    }

    /**
     *Létrehozza a táblát, és hozzáadja a megadott ablakhoz
     * @param gameFrame
     * a megadott ablak
     * @return
     * visszaadja a létrehozott táblát
     */
    public Board createBoard(JFrame gameFrame){
        Board board = new Board();
        JPanel boardPanel = board.getBoardPanel();
        gameFrame.add(boardPanel);
        return board;
    }

    /**
     * létrehozza a menüt, hozzáadva a megadott ablakhoz
     * @param gameFrame
     * a megadott ablak
     */
    public void createMenu(JFrame gameFrame){
        menu.createMenu(gameFrame);
    }

    /**
     * A játékkirajzolás utolsó lépéseit, beállításait hajtja végre
     * @param gameFrame
     * az ablak
     * @param width
     * az ablak szélessége
     * @param height
     * az ablak magassága
     */
    public void setUpGame(JFrame gameFrame, int width, int height){
        gameFrame.pack();
        gameFrame.setSize(width, height);
        gameFrame.setVisible(true);
    }

    /**
     * A játék vége esetén kirajzol egy újabb ablakot, amelyre kiírja, hogy melyik játékos győzött
     * @param winner
     * a győztes játékos
     */
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
