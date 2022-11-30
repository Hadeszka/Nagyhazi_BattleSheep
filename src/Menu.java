import javax.swing.*;
import java.awt.*;

/**
 * A menüt kirajzoló osztály, az egyes menüpontokra kattintás esetén végrehajtja a szükséges lépéseket.
 */
public class Menu {
    private int numberOfRobots;
    private Game actualGame;

    /**
     * Létrehozza a lenyíló menüt, benne a "New Game", "Save", "Load", "Quit" menüpontokkal.
     * @param frame
     * Ebben az ablakban hozza létre a menüt.
     */
    public void createMenu(JFrame frame){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(a->{
            frame.dispose();
            startGame();
        });
        menu.add(newGame);
        JMenuItem save = new JMenuItem("Save");
        menu.add(save);
        JMenuItem load = new JMenuItem("Load");
        menu.add(load);
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(a->frame.dispose());
        menu.add(quit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    /**
     * A "Start Game" menüpontra kattintást dolgozza fel:
     * Létrehoz egy új ablakot, ahol a felhasználó egy JComboBoxban kiválszthatja, hogy hány robottal szeretne játszani,
     * majd a "Start" gombra kattintva új játékot tud indítani.
     */
    public void startGame(){
        JFrame frame = new JFrame("Start Game");
        JPanel panel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Choose the number of robots!");
        panel.add(label);

        Object[] robots = {0, 1};
        JComboBox<Object> chooseRobots = new JComboBox<>(robots);
        chooseRobots.addActionListener(a->{
            if(chooseRobots.getSelectedItem() != null)
                numberOfRobots = (int) chooseRobots.getSelectedItem();
            else
                numberOfRobots = 0;
        });
        panel.add(chooseRobots);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(a->{
            frame.dispose();
            actualGame = new Game();
            actualGame.StartGame(numberOfRobots);
        });
        panel.add(startButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
