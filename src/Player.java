import java.util.ArrayList;

public abstract class Player {
    private ArrayList<Field> sheeps;
    private final Board board;

    public Player(Board b) {
        board = b;
        sheeps = new ArrayList<>();
    }

    public void addSheeps(Field field){
        sheeps.add(field);
        field.SetShepherd(this);
    }

    public abstract void Step(Field field);

    public ArrayList<Field> getSheeps() {
        return sheeps;
    }

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
        if(!IsBlocked())
            board.setTmp(this);
    }

    public Player getOtherPlayer(){
        return getBoard().getPlayer1()== this? getBoard().getPlayer2() : getBoard().getPlayer1();
    }

    public Board getBoard() {
        return board;
    }

}
