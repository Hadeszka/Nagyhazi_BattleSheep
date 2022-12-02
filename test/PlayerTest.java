import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class PlayerTest {
    Player p = new User(new Board(), new Game(new Menu()), "Blue");


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
