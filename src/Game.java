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

    /**
     * a megadott játékosra beállítja, hogy nem tud lépni, és ha a másik sem tud, akkor vége a játéknak,
     * tehát meghívja az EndGame függvényt
     * @param player
     * a megadott játékos
     * @return
     * 1-et ad vissza, ha vége a játéknak, 0-t, ha még nincs
     */
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

    public boolean isPlayer1CanMove() {
        return player1CanMove;
    }

    public boolean isPlayer2CanMove() {
        return player2CanMove;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /**
     * Vége a játéknak-> meghívja az ezt vizuálisan megjelenítő endGame függvényét a gameVisualnak
     * @param winner
     */
    public void EndGame(Player winner){

        gameVisual.endGame(winner);
    }

    /**
     * Felülírja az aktuális játékot:
     * a játékosokat, illetve azt, hogy tudnak-e lépni
     * majd a tábla állapotát is átállítja a paraméterben megadott játék szerintire
     * @param oldGame
     * a játék, amely felülírja az addigit
     */
    public void overrideGame(Game oldGame){
        oldGame.player1.setBoard(map);
        player1 = oldGame.getPlayer1();
        player1.setGame(this);
        oldGame.player1.setBoard(map);
        player2 = oldGame.getPlayer2();
        player2.setGame(this);
        player1CanMove = oldGame.player1CanMove;
        player2CanMove = oldGame.player2CanMove;
        map.replaceFields(oldGame.map.getBoardMap());
        map.setTmp(oldGame.map.getTmp());
        map.setYourTurn(oldGame.map.getTmp().getColor());
    }

}
