import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
    private ArrayList<Field> sheeps;
    private Board board;
    private GameVisual gameVisual;
    private Player otherPlayer;
    private final String color;
    private boolean canMove = true;

    public Player(Board b, GameVisual g, String c) {
        board = b;
        gameVisual = g;
        color = c;
        sheeps = new ArrayList<>();
    }

    public GameVisual getGameVisual() {
        return gameVisual;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }

   /* public void setGame(Game game) {
        this.game = game;
    }*/

    public void setSheeps(ArrayList<Field> s){
        sheeps = s;
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

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean CanMove() {
        return canMove;
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
            setCanMove(false);
            if(getOtherPlayer().IsBlocked())
                gameVisual.endGame(getOtherPlayer());
        }
    }

    /**
     * @return
     * visszaadja a másik játékost
     */
    public Player getOtherPlayer(){
        return otherPlayer;
    }

    public Board getBoard() {
        return board;
    }

    /*public Game getGame() {
        return game;
    }*/

    public String getColor() {
        return color;
    }
}
