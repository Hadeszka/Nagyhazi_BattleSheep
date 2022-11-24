import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newGame;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem quit;

    public Menu(JFrame frame){
        this.frame = frame;
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
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

    public JMenu getMenu() {
        return menu;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
