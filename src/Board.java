import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Board implements Serializable {
    private HashMap<Point, Field> boardMap;
    private final int x_max = 13;
    private final int y_max = 6;
    private Field stepFrom;
    private Player tmp;
    private final JPanel boardPanel;
    private int selectedSheep;
    private JComboBox comboBox;
    private final JLabel yourTurn;

    /**
     * Visszaad egy random választott mezőt, amely a pályán rajta is van.
     * @return
     * random mező a pályáról
     */
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

    /**
     * Beállítja, hogy mit írjon ki a JLabel, azaz hogy melyik játékos köre van jelenleg
     * @param color
     * a körön lévő játékos színe
     */
    public void setYourTurn(String color) {
        yourTurn.setText(color+"'s turn");
    }

    /**
     * Visszaadja, hogy jelenleg mit ír ki a JLabel, azaz ki van körön
     * @return
     * visszaadja a kiírt szöveget
     */
    public JLabel getYourTurn() {
        return yourTurn;
    }

    /**
     * @return
     *Visszaadja a mezőket tartalmazó hashMap-et
     */
    public HashMap<Point, Field> getBoardMap() {
        return boardMap;
    }

    /**
     * A Board osztály konstruktora, létrehoz egy Jpanel, majd ehhez hozzáad egy JLabelt, ami kiírja, hogy
     * melyik játékos van körön, egy JComboBox-ot, amelyben ki lehet választani,
     * hogy hány bárányt szeretnénk léptetni, illetve meghívja a createMap függvényt,
     * amivel létrehozza a pályát
     */
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

    /**
     * @return
     * Visszaadja a használt JPanelt
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * @return
     * Visszaadja, hogy jelenleg hány darab bárányt választottunk ki léptetésre
     */
    public int getSelectedSheep() {
        return selectedSheep;
    }

    /**
     * Beállítja, a megadott értékre a léptetendő bárányok számát
     * @param selectedSheep
     * A léptetendő bárányok száma
     */
    public void setSelectedSheep(int selectedSheep) {
        this.selectedSheep = selectedSheep;
    }

    /**
     * Visszaadja, hogy a megadott koordinátájú ponton található-e mező, azaz arra a koordinátára
     * kell e mezőt létrehozni.
     * @param x
     * az x koordináta
     * @param y
     * az y koordináta
     * @return
     * true, ha ott kell lennie mezőnek, false, ha nem
     */
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

    /**
     * Létrehozza a pályát, azokon a koordinátákon, ahol kell mezőnek lennie,
     * majd hozzáírja a boardMap-ban tárolt mezőkhöz.
     */
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

    /**
     * Minden egyes boardMapben tárolt mezőnek beállítja a szomszédait a boardMap mezőjei közül,
     * ezeket a mező neighbours listájában tárolja.
     * Minden egyes irányban megnézi, hogy van-e szomszédja, balra-fel átlósan, fel, jobbra-fel átlósan, stb.
     */
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

    /**
     * Átállítja az aktuális körön levő játékos kilétét a megadott játékosra, majd a JLabel szövegét
     * is ennek megfelelően változtatja
     * @param tmp
     * ez a játékos lesz mostantól a körön levő
     */
    public void setTmp(Player tmp) {
        this.tmp = tmp;
        setYourTurn(tmp.getColor());
    }

    /**
     * @return
     * Visszaadja a körön levő játékost
     */
    public Player getTmp() {
        return tmp;
    }

    /**
     * @param selected
     * Beállitja az aktuálisan kiválasztott mezőt, ahonnan a játékos lépni szeretne
     * a bárányaival
     */
    public void setStepFrom(Field selected) {
        stepFrom = selected;
    }

    /**
     * Törli a JComboBox tartalmát, majd újra beállítja az aktuálisan kiválasztott mezőnek
     * megfelelően, ahonnan a játékos a bárányait szeretné léptetni
     * Majd a léptetendő bárányok számát nullázza, mert újra ki kell választani, hogy hányat léptessünk
     */
    public void changeComboBox(){
        if(comboBox.getItemCount() > 0)
            comboBox.removeAllItems();
        if(stepFrom != null) {
            for (int i = 0; i < stepFrom.GetNumberOfSheep() ; ++i)
                comboBox.addItem(i);
        }
        selectedSheep = 0;
    }

    /**
     * Felülírja a mezőkön tartózkodó bárányok számát, illetve az őket birtokló shepherd kilétét.
     * Technikailag klónozzuk a megadott mezőt
     * @param newMap
     * Ez alapján a tábla alapján írjuk felül a mezők tartalmát
     */
    public void replaceFields(HashMap<Point, Field> newMap){
        boardMap.forEach((key, value)->{
            Field newValue = newMap.get(key);
            value.SetShepherd(newValue.getShepherd());
            value.SetNumberOfSheep(newValue.GetNumberOfSheep());
        });
    }
}
