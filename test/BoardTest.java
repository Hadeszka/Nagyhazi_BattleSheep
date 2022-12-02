import org.junit.Assert;
import org.junit.Test;


public class BoardTest {
    Board board = new Board();

    @Test
    public void isFieldTest(){
        Assert.assertFalse(board.isField(0,0));
        Assert.assertFalse(board.isField(-1,-1));
    }

    @Test
    public void setGetTmpTest(){
        Player p = new User(new Board(), new Game(new Menu()), "Blue");
        board.setTmp(p);
        Assert.assertSame(p, board.getTmp());
    }

    @Test
    public void setYourTurnTest(){
        Player p = new User(new Board(), new Game(new Menu()), "Blue");
        board.setTmp(p);
        Assert.assertEquals("Blue's turn", board.getYourTurn().getText());
    }
}
