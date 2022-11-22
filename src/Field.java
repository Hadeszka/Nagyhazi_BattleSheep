import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class Field {
    private int numberOfSheep;
    private Player shepherd;
    private HexButton button;
    private ArrayList<Field> neighbours;
    private Board board;



    public Field(Board board, JPanel panel, Point coords, int size){
        this.board = board;
        neighbours = new ArrayList<>(6);
        for(int i = 0; i<6; ++i){
            neighbours.add(i, null);
        }
        button = new HexButton(this, coords, size);
        panel.add(button);
    }

    public Field(int n, Player p, boolean e){
        numberOfSheep = n;
        shepherd = p;
        neighbours = new ArrayList<>(6);
        for(int i = 0; i<6; ++i){
            neighbours.add(i, null);
        }
    }

    public Field(int n, Player p, ArrayList<Field> nbs){
        numberOfSheep = n;
        shepherd = p;
        neighbours = nbs;
    }

    public HexButton getButton() {
        return button;
    }
    public void setButton(HexButton button) {
        this.button = button;
    }

    public Field GetNeighbour(int id){
        return neighbours.get(id);
    }

    public int GetNeighbourId(Field f){
        return neighbours.indexOf(f);
    }

    public int GetNumberOfSheep(){
        return numberOfSheep;
    }

    public boolean IsNeighbourBlocked(Field f){
        if(f == null || f.GetNumberOfSheep() > 0)
            return true;
        return false;
    }

    public void SetShepherd(Player p){
        shepherd = p;
    }

    public void SetNumberOfSheep(int n){
        numberOfSheep = n;
        Integer num = n;
        button.setText(num.toString());
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isStepFrom(){
        return this == board.getStepFrom();
    }

    public void setSelected(boolean selected){
        if(selected){
            board.setStepFrom(this);
        }
        else
            board.setStepFrom(null);
    }

    public void AddNeighbour(int id, Field f){
        neighbours.add(id, f);
    }

    public ArrayList<Field> LegalSteps() {
        ArrayList<Field> steps = new ArrayList<>(6);
        for (int i = 0; i < 6; ++i) {
            if (IsNeighbourBlocked(GetNeighbour(i)))
                steps.add(i, null);
            else {
                Field first = this;
                while (!first.IsNeighbourBlocked(first.GetNeighbour(i))) {
                    first = first.GetNeighbour(i);
                }
                steps.add(i, first);
            }
        }
        return steps;
    }
}
