public class Game {
    private Board map;
    private Player player1;
    private Player player2;

    public void StartGame(int numberOfRobots){
        GameVisual gameFrame = new GameVisual("BattleSheep");
        map = gameFrame.createBoard(40);
        gameFrame.createMenu();
        gameFrame.setUpGame(1000, 700);

        putPlayers(numberOfRobots);
    }

    public void putPlayers(int numberOfRobots){
        Field field1 = map.getRandomField();
        Field field2 = map.getRandomField();
        while(field1 == field2){
            field2 = map.getRandomField();
        }
        player1 = new User(map);
        switch (numberOfRobots){
            case 0 :
                player2 = new User(map);
                break;
            case 1:
                player2 = new Robot(map);
                break;
        }
        player1.addSheeps(field1);
        field1.SetNumberOfSheep(16);

       player2.addSheeps(field2);
        field2.SetNumberOfSheep(16);
        map.putPlayers(player1, player2);
    }

    public void EndGame(){}

    public void StepPlayers(){

    }
}
