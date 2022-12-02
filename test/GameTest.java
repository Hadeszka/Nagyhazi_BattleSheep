import org.junit.Assert;
import org.junit.Test;

public class GameTest {
    Game game = new Game(new Menu());


    @Test
    public void playerCantMoveTest(){
        game.PlayerCantMove(game.getPlayer1());
        Assert.assertFalse(game.isPlayer1CanMove());
        Assert.assertTrue(game.isPlayer2CanMove());
    }

    @Test
    public void playerInitTest(){
        Assert.assertSame(null, game.getPlayer1());
        game.StartGame(1);
        Assert.assertNotSame(null, game.getPlayer1());
    }
}
