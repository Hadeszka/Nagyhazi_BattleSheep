import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * A menüt kirajzoló osztály, az egyes menüpontokra kattintás esetén végrehajtja a szükséges lépéseket.
 */
public class Menu  implements Serializable {
    private int numberOfRobots;
    private Game actualGame;

    ArrayList<String> fileNames;
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
        save.addActionListener(a->save());
        menu.add(save);
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(a->load());
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
            actualGame = new Game(this);
            actualGame.StartGame(numberOfRobots);
        });
        panel.add(startButton);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void save() {
        JFrame frame = new JFrame("Save");
        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Name your file:");
        panel.add(label);
        JTextField textField = new JTextField(30);
        panel.add(textField);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(a->{
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(textField.getText()+".txt");
                ObjectOutputStream objectOutputStream
                        = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(actualGame);
                objectOutputStream.flush();
                objectOutputStream.close();

                File names = new File("filenames.txt");
                if(names.length() == 0){
                    fileNames = new ArrayList<>();
                }
                else{
                    FileInputStream filenameIn = new FileInputStream("filenames.txt");
                    ObjectInputStream stringStreamIn = new ObjectInputStream(filenameIn);
                    fileNames = (ArrayList<String>) stringStreamIn.readObject();
                    stringStreamIn.close();


                }
                fileNames.add(textField.getText());

                FileOutputStream filenameOut = new FileOutputStream("filenames.txt");
                ObjectOutputStream stringStreamOut = new ObjectOutputStream(filenameOut);
                stringStreamOut.writeObject(fileNames);
                stringStreamOut.flush();
                stringStreamOut.close();

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            frame.dispose();
        });
        panel.add(saveButton);
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void load()  {
        try {
            FileInputStream filenameIn = new FileInputStream("filenames.txt");
            ObjectInputStream stringStreamIn = new ObjectInputStream(filenameIn);
            fileNames = (ArrayList<String>) stringStreamIn.readObject();
            stringStreamIn.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        JFrame frame = new JFrame("Load");
        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Choose file:");
        panel.add(label);
        String[] names = fileNames.toArray(new String[0]);
        JComboBox<String> comboBox = new JComboBox<>(names);
        panel.add(comboBox);
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(a->{
            Game oldGame;
            if(comboBox.getSelectedItem() != null) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(comboBox.getSelectedItem() + ".txt");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    oldGame = (Game) objectInputStream.readObject();
                    objectInputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                frame.dispose();
                actualGame.overrideGame(oldGame);
            }
        });
        panel.add(loadButton);
        frame.add(panel);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
