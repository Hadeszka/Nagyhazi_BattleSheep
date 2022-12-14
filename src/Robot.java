import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Robot extends Player{
    public Robot(Board b, GameVisual g, String c){
        super(b, g, c);
    }

    /**
     * Amennyiben nincs blokkolva a játékos, beállítja magát aktuális játékosnak, majd lép,
     * ha viszont blokkolva van, szól a game-nek, hogy nem tud lépni,
     * majd ha még nem ért véget a játék, szól a másik játékosnak, hogy jöhet
     */
    @Override
    public void turn() {
        if(!IsBlocked()) {
            getBoard().setTmp(this);
            Step(null);
        }
        else{
            setCanMove(false);
            if(getOtherPlayer().IsBlocked())
                    getGameVisual().endGame(getOtherPlayer());
        }
    }

    /**
     * A Robot játékos lépése:
     * A mezők közül, ahol léptethető bárányi vannak, kiválasztja azt ahol a legtöbb bárány van,
     * majd az onnan történő szabályosan elérhető mezőre léptet random számú bárányt.
     * Egy mezőre se léphet szabályosan, akkor az következő legtöbb bárányt számláló mezőjéről
     * próbál lépni.
     * Amint ez megtörtént jelzi a másik játékosnak, hogy a köre véget ért.
     * @param field
     * nem lényeges
     */
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
