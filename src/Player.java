import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
    private final ArrayList<Field> sheeps;
    private Board board;
    private Game game;
    private final String color;

    public Player(Board b, Game g, String c) {
        board = b;
        game = g;
        color = c;
        sheeps = new ArrayList<>();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public ArrayList<Field> getSheeps() {
        return sheeps;
    }

    /**
     * hozzáadja a játékos bárányait tartalmazó mezőkhoz a megadottat,
     * illetve a megadott mezőn beállítja a játékost shepherdnek
     * @param field
     * a megadott mező
     */
    public void addSheeps(Field field){
        sheeps.add(field);
        field.SetShepherd(this);
    }

    public abstract void Step(Field field);

    /**
     * @return
     * Visszaadja, hogy van-e léptethető báránya a játékosnak,
     * ha pedig van, akkor van-e olyan mező, ahová léptetheti őket
     */
    public boolean IsBlocked(){
        if(getSteppableSheep().size() == 0)
            return true;
        boolean blocked = true;
        for (Field f: getSteppableSheep()
             ) {
            if(f.LegalSteps().size() != 0)
                blocked = false;
        }
        return blocked;
    }

    /**
     * @return
     * Visszaadja azokat a mezőket, ahol egynél több báránya van a játékosnak
     */
    public ArrayList<Field> getSteppableSheep() {
        ArrayList<Field> steppableSheep = new ArrayList<>();
        for (Field f :
                sheeps) {
            if (f.GetNumberOfSheep() > 1)
                steppableSheep.add(f);
        }
        return steppableSheep;
    }

    /**
     * beállytja a játékost az aktuális játékosnak,
     * majd ha a játékos blokkolva van, akkor ezt jelzi a game-nek
     */
    public void turn() {
        board.setTmp(this);
        if(IsBlocked()) {
            if(game.PlayerCantMove(this) == 0)
              getOtherPlayer().turn();
        }
    }

    /**
     * @return
     * visszaadja a másik játékost
     */
    public Player getOtherPlayer(){
        return game.getPlayer1()== this? game.getPlayer2() : game.getPlayer1();
    }

    public Board getBoard() {
        return board;
    }

    public Game getGame() {
        return game;
    }

    public String getColor() {
        return color;
    }
}
