public class User extends Player{
    private Field stepFrom;
    public User(Board b, Game g, String c){
        super(b, g, c);
        stepFrom = null;
    }

    /**
     * Az első lehetséges állapot, amikor még nincs kiválasztva mező, hogy honnan lépjen a játékos:
     * Ha olyan mezőre kattintott, ahol a játékosnak 1-nél több báránya áll,
     * akkor ez a mező jelölődik ki arra, hogy innen lépjen a játékos
     * @param field
     * a mező, amire kattintott a játékos
     */
    public void ChooseFieldToStepFrom(Field field){
        if(field.GetNumberOfSheep() > 1 && field.getShepherd() == field.getBoard().getTmp()){
            field.setStepFrom(true);
            stepFrom = field;
        }
    }

    /**
     * A második lehetséges állapot, amikor már van kiválasztott kiinduló mező,
     * de nincs kiválasztva, hogy hány bárányt akar léptetni:
     * Ha ekkor kattint egy mezőre, ha az megfelel az ehhez szükséges feltételeknek,
     * akkor az lesz az új kiinduló mező,
     * ha nem, akkor csak törlődik a kiválasztott kiinduló mező, de új nem lesz helyette
     * @param field
     * a mező, amire kattintott a játékos
     */
    public void ChooseNumberOfSheep(Field field){
        if ((field.GetNumberOfSheep() == 0 && !stepFrom.LegalSteps().contains(field)) || field == stepFrom) {
            stepFrom.setStepFrom(false);
            stepFrom = null;
        } else if (field.GetNumberOfSheep() > 1 && field.getShepherd() == field.getBoard().getTmp()) {
            stepFrom.setStepFrom(false);
            field.setStepFrom(true);
            stepFrom = field;
        }
    }

    /**
     * Az az állapot, amikor már van kiinduló mező, és meg van határozva a léptetendő bárányok száma is:
     * Ekkor ha a játékos olyan mezőre kattint, ahol nincs báránya senkinek, illetve szabályos lépés,
     * akkor megtörténik a lépés,
     * ha viszont arra a mezőre kattintott, ami a kiinduló mező, akkor törlődik a kiinduló mező
     * @param field
     * a mező, amire kattintott a játékos
     */
    public void ChooseFieldToStepTo(Field field){
        if(field.GetNumberOfSheep() == 0 && stepFrom.LegalSteps().contains(field)){
            int number = stepFrom.getBoard().getSelectedSheep();
            stepFrom.setStepFrom(false);
            field.SetNumberOfSheep(number);
            stepFrom.SetNumberOfSheep(stepFrom.GetNumberOfSheep() - number);
            stepFrom = null;
            addSheeps(field);
            getOtherPlayer().turn();
        }
        if(field == stepFrom){
            field.getBoard().setSelectedSheep(0);
            stepFrom.setStepFrom(false);
            stepFrom = null;
        }
    }

    /**
     * A User(élő) típusú játékos lépése:
     * Egy állapot géphez hasonló, a három állapotában más-más függvény hívódik meg:
     * 1. állapot: nincs kiválasztva kezdő mező(ahonnan léptetné a bárányait)
     * 2. állapot: kezdő mező van, de nincs meghatározva a léptetendő bárányok száma
     * 3. állapot: van kezdő mező, és meg van határozva a léptetendő bárányok száma is
     * @param field
     * A mező, amelyikre a játékos kattintott
     */
    public void Step(Field field){
        if(stepFrom == null)
            ChooseFieldToStepFrom(field);
        else{
            if(field.getBoard().getSelectedSheep() == 0 )
                ChooseNumberOfSheep(field);
            else
                ChooseFieldToStepTo(field);
        }

    }

}
