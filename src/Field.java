import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Field {
    private int numberOfSheep;
    private Player shepherd;
    private final HexButton button;
    private final ArrayList<Field> neighbours;
    private final Board board;



    public Field(Board board, JPanel panel, Point coords, int size){
        numberOfSheep = 0;
        this.board = board;
        neighbours = new ArrayList<>(6);
        for(int i = 0; i<6; ++i){
            neighbours.add(i, null);
        }
        button = new HexButton(this, coords, size);
        panel.add(button);
    }
    public HexButton getButton() {
        return button;
    }
    public Field GetNeighbour(int id){
        return neighbours.get(id);
    }
    public int GetNumberOfSheep(){
        return numberOfSheep;
    }

    public boolean IsNeighbourBlocked(Field f){
        return f == null || f.GetNumberOfSheep() > 0;
    }

    public void SetShepherd(Player p){
        shepherd = p;
    }

    public Player getShepherd() {
        return shepherd;
    }

    public void SetNumberOfSheep(int n){
        numberOfSheep = n;
        Integer num = n;
        button.setText(num.toString());
    }

    public Board getBoard() {
        return board;
    }

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

    public void editLegalSteps(boolean visible, Field root){
        for (Field step:
                root.LegalSteps()){
            if(step != null){
                step.getButton().setSelected(visible);
            }
        }
    }

    public void AddNeighbour(int id, Field f){
        neighbours.add(id, f);
    }

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
