import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Serializable {
    private final ArrayList<Field> sheeps;
    private final Board board;
    private final Game game;
    private final String color;

    public Player(Board b, Game g, String c) {
        board = b;
        game = g;
        color = c;
        sheeps = new ArrayList<>();
    }

    public void addSheeps(Field field){
        sheeps.add(field);
        field.SetShepherd(this);
    }

    public abstract void Step(Field field);

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


    public ArrayList<Field> getSteppableSheep() {
        ArrayList<Field> steppableSheep = new ArrayList<>();
        for (Field f :
                sheeps) {
            if (f.GetNumberOfSheep() > 1)
                steppableSheep.add(f);
        }
        return steppableSheep;
    }

    public void turn() {
        board.setTmp(this);
        if(IsBlocked()) {
            if(game.PlayerCantMove(this) == 0)
              getOtherPlayer().turn();
        }
    }

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
