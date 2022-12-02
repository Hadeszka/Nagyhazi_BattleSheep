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

    /**
     * A megadott nevű fájlból beolvas egy objektumot, majd ezt visszaadja
     * @param filename
     * a megadott fájlnév
     * @return
     * a beolvasott objektum
     * @throws IOException
     * Ha a fájl nevével, megnyitásával bármi probléma van
     * @throws ClassNotFoundException
     * ha a fájl tartalma hibás
     */
    public Object getFromFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream filenameIn = new FileInputStream(filename+".txt");
        ObjectInputStream stringStreamIn = new ObjectInputStream(filenameIn);
        Object object =  stringStreamIn.readObject();
        stringStreamIn.close();
        return object;
    }
    /**
     * A megadott nevű fájlba kiírja a megadott objektumot
     * @param filename
     * a megadott fájlnév
     * @param object
     * a megadott objektum
     * @throws IOException
     * Ha a fájl nevével, megnyitásával bármi probléma van
     */
    public void putInFile(String filename, Object object) throws IOException {
        FileOutputStream filenameOut = new FileOutputStream(filename+".txt");
        ObjectOutputStream stringStreamOut = new ObjectOutputStream(filenameOut);
        stringStreamOut.writeObject(object);
        stringStreamOut.flush();
        stringStreamOut.close();
    }

    /**
     * Kirajzol egy új ablakot, amiben található egy textfield és egy gomb,
     * a textfieldben megadva egy nevet, majd a gombra kattintva elmentjük
     * az aktuális játékot a megadott nevű fájlba, majd a fájl nevét
     * elmentjük a filenames.txt fájlba, az ablak pedig bezárul
     */
    public void save() {
        JFrame frame = new JFrame("Save");
        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Name your file:");
        panel.add(label);
        JTextField textField = new JTextField(30);
        panel.add(textField);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(a->{
            if(textField.getText() != null) {
                try {
                    putInFile(textField.getText(), actualGame);
                } catch (IOException e) {
                    JFrame errorFrame = new JFrame("ERROR");
                    JOptionPane.showMessageDialog(errorFrame, "Something is wrong with the saved file!");
                }

                try {
                    File names = new File("filenames.txt");
                    if (names.length() == 0)
                        fileNames = new ArrayList<>();
                    else
                        fileNames = (ArrayList<String>) getFromFile("filenames");
                    fileNames.add(textField.getText());
                    putInFile("filenames", fileNames);

                } catch (IOException | ClassNotFoundException e) {
                    JFrame errorFrame = new JFrame("ERROR");
                    JOptionPane.showMessageDialog(errorFrame, "The filenames are damaged!");
                }
                frame.dispose();
            }
        });
        panel.add(saveButton);
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Létrehoz egy új ablakot egy JComboBox-al, aminek a tartalma
     * a filenames.txt-ből beolvasott fájlnevek.
     * (ha egyet se tartalmaz a filenames.txt, akkor a load függvény
     * egy popup windowban figyelmeztet a hibáról)
     * Egy fájlnevet kiválasztva, majd a gombra kattintva
     * felülírjuk az aktuális játékot a betöltött játékkal,
     * az ablak pedig bezárul
     */
    public void load()  {
        if((new File("filenames.txt")).length() == 0){
            JFrame frame = new JFrame("ERROR");
            JOptionPane.showMessageDialog(frame, "There is no saved file!");
            return;
        }
        try {
            fileNames = (ArrayList<String>) getFromFile("filenames");
        } catch (IOException | ClassNotFoundException e) {
            JFrame frame = new JFrame("ERROR");
            JOptionPane.showMessageDialog(frame, "The filenames are damaged!");
            return;
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
                    oldGame = (Game) getFromFile((String) comboBox.getSelectedItem());
                } catch (IOException | ClassNotFoundException e) {
                    JFrame errorFrame = new JFrame("ERROR");
                    JOptionPane.showMessageDialog(errorFrame, comboBox.getSelectedItem()+".txt file doesn't exist or it's damaged!");
                    return;
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
