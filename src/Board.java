import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Board {
    HashMap<Point, Field> boardMap;
    private final int x_max = 13;
    private final int y_max = 6;
    private Field stepFrom;
    private ArrayList<Field> legalSteps;
    private JPanel boardPanel;
    private int size;
    private Object numberOfSheep[] = new Object[0];

    private JComboBox comboBox;
    public void putPlayers(){
        boardMap.get(new Point(3,0)).SetNumberOfSheep(16);
        boardMap.get(new Point(10,5)).SetNumberOfSheep(16);
    }

    public Board(int size){
        boardPanel = new JPanel(null);
        comboBox = new JComboBox(numberOfSheep);
        this.size = size;
        boardMap = new HashMap<>();
        createMap();
    }


    public JPanel getBoardPanel() {
        return boardPanel;
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
        if( x == 6 && y == 3)
            return false;
        return true;
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
                            entry.getValue().AddNeighbour(id, boardMap.get(new Point(entry.getKey().x + 2*x, entry.getKey().y + 2*y)));
                    }
                    else
                    if (boardMap.containsKey(new Point(entry.getKey().x + x, entry.getKey().y + y)))
                            entry.getValue().AddNeighbour(id, boardMap.get(new Point(entry.getKey().x + x, entry.getKey().y + y)));

                }
            }
        }
    }

   /* public void AddField(Field f){
        //map.add(map.size(), f);
    }*/

    public int getX_max() {
        return x_max;
    }
    public int getY_max() {
        return y_max;
    }

    public Field getStepFrom() {
        return stepFrom;
    }

    public void setStepFrom(Field selected) {
        stepFrom = selected;
    }

    public ArrayList<Field> getLegalSteps() {
        return legalSteps;
    }

    public void setLegalSteps() {
        legalSteps = stepFrom.LegalSteps();
    }
}
