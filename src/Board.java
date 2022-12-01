import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Board implements Serializable {
    HashMap<Point, Field> boardMap;
    private final int x_max = 13;
    private final int y_max = 6;
    private Field stepFrom;
    private Player tmp;
    private final JPanel boardPanel;
    private int selectedSheep;
    private JComboBox comboBox;
    private final JLabel yourTurn;
    public Field getRandomField(){
        Random random = new Random();
        int x = random.nextInt(0, 13);
        int y = random.nextInt(0, 6);
        while(!isField(x,y)){
            x = random.nextInt(0, 13);
            y = random.nextInt(0, 6);
        }
        return boardMap.get(new Point(x,y));
    }

    public void setYourTurn(String color) {
        yourTurn.setText(color+"'s turn");
    }

    public Board(){
        boardPanel = new JPanel(null);

        yourTurn = new JLabel("Purple's turn");
        yourTurn.setBounds(150, 30, 100, 40);
        boardPanel.add(yourTurn);

        Object[] numberOfSheep = new Object[0];
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
                    boardMap.put(coords, new Field(this, boardPanel, coords));
                }
            }
        }
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
        setYourTurn(tmp.getColor());
    }

    public Player getTmp() {
        return tmp;
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

    public void replaceFields(HashMap<Point, Field> newMap){
        boardMap.forEach((key, value)->{
            Field newValue = newMap.get(key);
            value.SetShepherd(newValue.getShepherd());
            value.SetNumberOfSheep(newValue.GetNumberOfSheep());
        });
    }
}
