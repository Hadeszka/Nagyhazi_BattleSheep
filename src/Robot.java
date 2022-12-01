import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Robot extends Player{
    public Robot(Board b, Game g, String c){
        super(b, g, c);
    }

    @Override
    public void turn() {
        if(!IsBlocked()) {
            getBoard().setTmp(this);
            Step(null);
        }
        else
            if(getGame().PlayerCantMove(this) == 0)
                getOtherPlayer().turn();

    }
    public void Step(Field field) {
        Random random = new Random();
        int randomField = random.nextInt(0, 100000);
        int randomSheep = random.nextInt(0, 100000);
        ArrayList<Field> sheep = getSteppableSheep();
        sheep.sort(Comparator.comparingInt(Field::GetNumberOfSheep));
        boolean done = false;
        for(int i = sheep.size()-1; i>=0 && !done;--i) {
            Field big = sheep.get(i);
            if(big.LegalSteps().size() != 0) {
                Field nextField = big.LegalSteps().get(randomField % big.LegalSteps().size());
                nextField.SetNumberOfSheep(randomSheep % (big.GetNumberOfSheep()-1) +1);
                big.SetNumberOfSheep(big.GetNumberOfSheep() - (randomSheep % (big.GetNumberOfSheep()-1) +1));
                addSheeps(nextField);
                done = true;
            }
        }
            getOtherPlayer().turn();
    }
}
