/**
 * Egy játék inicializálásáért, elindításáért, illetve befejezéséért felelős osztály.
 */
public class Game {
    private Board map;

    /**
     * Inicializál, majd elindít egy új játékot.
     * Létrehozza a játéktáblát, a menüt, majd a játékosokat.
     * @param numberOfRobots
     * Tartalmazza, hogy a 2 játékosból hány robot.
     */
    public void StartGame(int numberOfRobots){
        GameVisual gameFrame = new GameVisual("BattleSheep");
        map = gameFrame.createBoard();
        gameFrame.createMenu();
        gameFrame.setUpGame(1000, 700);

        putPlayers(numberOfRobots);
    }

    /**
     * Létrehozza a játékosokat:
     * Kisorsol nekik egy-egy mezőt, ahol a 16 bárányuk kezdetben lesz.
     * Az 1-es számú játékos mindig felhasználó, a 2-es számú a paramétertől függően felhasználó, vagy robot.
     * @param numberOfRobots
     * Meghatározza a 2-es számú játékos kilétét.
     */
    public void putPlayers(int numberOfRobots){
        Field field1 = map.getRandomField();
        Field field2 = map.getRandomField();
        while(field1 == field2){
            field2 = map.getRandomField();
        }
        Player player1 = new User(map);
        Player player2;
        switch (numberOfRobots) {
            case 0 -> player2 = new User(map);
            default -> player2 = new Robot(map);
        }
        player1.addSheeps(field1);
        field1.SetNumberOfSheep(16);

        player2.addSheeps(field2);
        field2.SetNumberOfSheep(16);
        map.putPlayers(player1, player2);
    }

    public void EndGame(){}

}
