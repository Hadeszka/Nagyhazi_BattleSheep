import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class PlayerTest {
    Player p = new User(new Board(), new GameVisual(new Menu()), "Blue");

    @Test
    public void playerCantMoveTest(){
        p.setCanMove(false);
        Assert.assertFalse(p.CanMove());
    }
    @Test
    public void addSheepTest(){
        Assert.assertEquals(0, p.getSheeps().size());
        p.addSheeps(new Field(new Board(), new JPanel(), new Point()));
        Assert.assertNotEquals(0, p.getSheeps().size());
    }

    @Test
    public void isBlockedTest(){
        Assert.assertTrue(p.IsBlocked());
    }
}
