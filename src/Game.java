import java.io.Serializable;

/**
 * Egy játék inicializálásáért, elindításáért, illetve befejezéséért felelős osztály.
 */
public class Game implements Serializable {
    private final GameVisual gameVisual;
    private Board map;
    private Player player1;
    private boolean player1CanMove = true;
    private Player player2;
    private boolean player2CanMove = true;


    public Game(Menu menu){
        gameVisual = new GameVisual(menu);
    }
    /**
     * Inicializál, majd elindít egy új játékot.
     * Létrehozza a játéktáblát, a menüt, majd a játékosokat.
     * @param numberOfRobots
     * Tartalmazza, hogy a 2 játékosból hány robot.
     */
    public void StartGame(int numberOfRobots){
        map = gameVisual.StartGame();
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
        player1 = new User(map, this, "Purple");
        if (numberOfRobots == 0) {
            player2 = new User(map, this, "Blue");
        } else {
            player2 = new Robot(map, this, "Blue");
        }
        player1.addSheeps(field1);
        field1.SetNumberOfSheep(16);

        player2.addSheeps(field2);
        field2.SetNumberOfSheep(16);
        map.setTmp(player1);
    }

    public int PlayerCantMove(Player player) {
        if(player == player1) {
            player1CanMove = false;
            if (!player2CanMove){
                EndGame(player1);
                return 1;
            }
        }
        else{
            player2CanMove = false;
            if(!player1CanMove) {
                EndGame(player2);
                return 1;
            }
        }
        return 0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void EndGame(Player winner){

        gameVisual.endGame(winner);
    }

    public void overrideGame(Game oldGame){
        player1 = oldGame.getPlayer1();
        player2 = oldGame.getPlayer2();
        player1CanMove = oldGame.player1CanMove;
        player2CanMove = oldGame.player2CanMove;
        map.replaceFields(oldGame.map.boardMap);
        map.setTmp(oldGame.map.getTmp());
        map.setYourTurn(oldGame.map.getTmp().getColor());
    }

}
