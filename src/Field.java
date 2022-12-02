import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    private int numberOfSheep;
    private Player shepherd;
    private final HexButton button;
    private final ArrayList<Field> neighbours;
    private final Board board;


    /**
     * A Field osztály konstruktora, létrehozza a mezőt, beállítja az attribútumait nullára,
     * vagy a megadott értékekre, és létrehozza a hozzá tartozó gombot
     * @param board
     * ehhez a táblához tartozik a gomb
     * @param panel
     * ehhez a JPanelhez adja hozzá a gombját
     * @param coords
     * ezek a gomb koordinátái
     */
    public Field(Board board, JPanel panel, Point coords){
        numberOfSheep = 0;
        this.board = board;
        neighbours = new ArrayList<>(6);
        for(int i = 0; i<6; ++i){
            neighbours.add(i, null);
        }
        button = new HexButton(this, coords);
        panel.add(button);
    }

    /**
     * @return
     * Visszaadja a gombot
     */
    public HexButton getButton() {
        return button;
    }

    /**
     * @param id
     * Ez a sorszáma a szomszédnak
     * @return
     * Visszaadja a megadott sorszámú szomszédot
     */
    public Field GetNeighbour(int id){
        return neighbours.get(id);
    }

    /**
     * @return
     * Visszaadja, hogy hány bárány áll rajta
     */
    public int GetNumberOfSheep(){
        return numberOfSheep;
    }

    /**
     *
     * @param f
     * a kérdéses szomszéd mező
     * @return
     * Igazat ad vissza, ha a szomszéd mező null, vagy ha áll rajta bárány, különben hamisat
     */
    public boolean IsNeighbourBlocked(Field f){
        return f == null || f.GetNumberOfSheep() > 0;
    }

    /**
     * @param p
     * erre az játékosra állítja be a mezőn található shepherd-öt
     */
    public void SetShepherd(Player p){
        shepherd = p;
    }

    /**
     * @return
     * Visszaadja a játékost, aki a mezőn shepherd
     */
    public Player getShepherd() {
        return shepherd;
    }

    /**
     * @param n
     * Erre az értékre állítja be a mezőn álló bárányok számát, majd a gombra is kiírja ezt a számot
     */
    public void SetNumberOfSheep(int n){
        numberOfSheep = n;
        Integer num = n;
        button.setText(num.toString());
    }

    /**
     * @return
     * Visszaadja a táblát, amelyhez tartozik
     */
    public Board getBoard() {
        return board;
    }

    /**
     * A megadott boolean érték szerint jelöli ki a gombot, illetve
     * a belőle szabályosan elérhető mezők gomjait.
     * Emellett beállítja, hogy róla akar-e bárányokat léptetni a játékos,
     * majd a változások szerint beállítja a JComboBoxot
     * @param selected
     * a megadott boolean érték
     */
    public void setStepFrom(boolean selected){
        button.setSelected(selected);
        editLegalSteps(selected, this);
        if(selected){
            board.setStepFrom(this);
        }
        else
            board.setStepFrom(null);
        getBoard().changeComboBox();
    }

    /**
     * a kezdőmezőből szabályosan elérhető mezőket kijelöli, vagy ellenkezőleg
     * @param visible
     * kijelöljön-> igaz, ne jelöljön ki-> hamis
     * @param root
     * A kezdőmező
     */
    public void editLegalSteps(boolean visible, Field root){
        for (Field step:
                root.LegalSteps()){
            if(step != null){
                step.getButton().setSelected(visible);
            }
        }
    }

    /**
     * Hozzáadja a megadott indexen a megadott mezőt a szomszédok listához
     * @param id
     * megadott index
     * @param f
     * megadott mező
     */
    public void AddNeighbour(int id, Field f){
        neighbours.add(id, f);
    }

    /**
     * Meghatározza, hogy honnan lehet lépni erről a mezőről szabályosan:
     * ez azt jelenti, hogy oda lehet lépni, ahol nekiütközünk valaminek, azaz már nem tudunk tovább haladni,
     * de ha egyből nekiütköznénk valaminek, akkor az érvénytelen-> ahol eddig álltunk, oda nem léphetünk
     * @return
     * Visszaadja a szabályos lépések listáját
     */
    public ArrayList<Field> LegalSteps() {
        ArrayList<Field> steps = new ArrayList<>(6);
        for (int i = 0; i < 6; ++i) {
            if (!IsNeighbourBlocked(GetNeighbour(i))){
                Field first = this;
                while (!first.IsNeighbourBlocked(first.GetNeighbour(i))) {
                    first = first.GetNeighbour(i);
                }
                steps.add(first);
            }
        }
        return steps;
    }
}
