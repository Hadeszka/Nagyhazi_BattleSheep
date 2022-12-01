public class User extends Player{
    private Field stepFrom;
    public User(Board b, Game g, String c){
        super(b, g, c);
        stepFrom = null;
    }

    public void ChooseFieldToStepFrom(Field field){
        if(field.GetNumberOfSheep() > 1 && field.getShepherd() == field.getBoard().getTmp()){
            field.setStepFrom(true);
            stepFrom = field;
        }
    }

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
