import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Board {
    HashMap<Point, Field> boardMap;
    private final int x_max = 13;
    private final int y_max = 6;
    private Field stepFrom;
    private ArrayList<Field> legalSteps;

    private Player player1;
    private Player player2;
    private Player tmp;

    private JPanel boardPanel;
    private int size;
    private Object numberOfSheep[] = new Object[0];
    private int selectedSheep;
    private JComboBox comboBox;
    private JButton pass;
    public void putPlayers(){
        Field player1Start = boardMap.get(new Point(3,0));
        player1Start.SetNumberOfSheep(16);
        player1Start.SetShepherd(player1);
        player1.getSheeps().add(player1Start);
        Field player2Start = boardMap.get(new Point(10,5));
        player2Start.SetNumberOfSheep(16);
        player2Start.SetShepherd(player2);
        player2.getSheeps().add(player2Start);

    }

    public Board(int size){
        boardPanel = new JPanel(null);
        player1 = new User(this);
        player2 = new Robot(this);
        tmp = player1;

        comboBox = new JComboBox(numberOfSheep);
        comboBox.addActionListener(a->
        {
            if(stepFrom != null) {
                if (comboBox.getSelectedItem() == null)
                    selectedSheep = 0;
                else
                    selectedSheep = (int) comboBox.getSelectedItem();
            }
        });
        comboBox.setBounds(100, 30, 50, 40);
        boardPanel.add(comboBox);
        selectedSheep = 0;
        this.size = size;
        boardMap = new HashMap<>();
        createMap();
    }


    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public int getSelectedSheep() {
        return selectedSheep;
    }

    public void setSelectedSheep(int selectedSheep) {
        this.selectedSheep = selectedSheep;
    }

    public boolean isField(int x, int y){
        //minden második koordinátán legyen csak mező
        if( (x+y) % 2 == 0)
            return false;
        //a sarkoknál a kihagyás legyen meg
        if(x + y <=2)
            return false;
        if(x_max-x+y <= 2)
            return false;
        if(y_max + x_max-x-y <= 2)
            return false;
        if(y_max + x-y <= 2)
            return false;
        //a mező közepénél legyen lyuk
        return x != 6 || y != 3;
    }
    public void createMap(){
        for(int x = 0;x<x_max;++x){
            for(int y = 0; y<y_max;++y) {
                if (isField(x, y)) {
                    Point coords = new Point(x, y);
                    boardMap.put(coords, new Field(this, boardPanel, coords, size));
                }
            }
        }
        putPlayers();
        setNeighbours();
    }
    public void setNeighbours(){
        for (Map.Entry<Point, Field> entry:
             boardMap.entrySet()) {
            for(int x = -1, id = 0; x<=1; x+=2){
                for(int y = -1; y<=1; ++y, ++id){
                    if(y == 0){
                        if (boardMap.containsKey(new Point(entry.getKey().x + 2*x, entry.getKey().y + y)))
                            entry.getValue().AddNeighbour(id, boardMap.get(new Point(entry.getKey().x + 2*x, entry.getKey().y)));
                    }
                    else
                    if (boardMap.containsKey(new Point(entry.getKey().x + x, entry.getKey().y + y)))
                            entry.getValue().AddNeighbour(id, boardMap.get(new Point(entry.getKey().x + x, entry.getKey().y + y)));

                }
            }
        }
    }

    public void setTmp(Player tmp) {
        this.tmp = tmp;
    }

    public Player getTmp() {
        return tmp;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Field getStepFrom() {
        return stepFrom;
    }

    public void setStepFrom(Field selected) {
        stepFrom = selected;
    }
    public void changeComboBox(){
        if(comboBox.getItemCount() > 0)
            comboBox.removeAllItems();
        if(stepFrom != null) {
            for (int i = 0; i < stepFrom.GetNumberOfSheep() ; ++i)
                comboBox.addItem(i);
        }
        selectedSheep = 0;
    }

    public ArrayList<Field> getLegalSteps() {
        return legalSteps;
    }

    public void setLegalSteps() {
        legalSteps = stepFrom.LegalSteps();
    }
}
