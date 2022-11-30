import javax.swing.*;
import java.awt.*;

public class Menu {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newGame;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem quit;
    private int numberOfRobots;
    private boolean robotsAreSet;
    private Game actualGame;

    public Menu(){
    }
    public void createMenu(JFrame frame){
        this.frame = frame;
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(a->{
            frame.dispose();
            startGame();
        });
        menu.add(newGame);
        save = new JMenuItem("Save");
        menu.add(save);
        load = new JMenuItem("Load");
        menu.add(load);
        quit = new JMenuItem("Quit");
        quit.addActionListener(a->frame.dispose());
        menu.add(quit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }

    public void startGame(){
        actualGame = new Game();
        robotsAreSet = false;
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
            actualGame.StartGame(numberOfRobots);
        });
        panel.add(startButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public boolean isRobotsAreSet() {
        return robotsAreSet;
    }

    public JMenu getMenu() {
        return menu;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
