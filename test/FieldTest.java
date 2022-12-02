import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class FieldTest {
    Field field = new Field(new Board(), new JPanel(), new Point());

    @Test
    public void setGetShepherdTest(){
        Assert.assertSame(null, field.getShepherd());
        field.SetShepherd(new User(new Board(), new Game(new Menu()), "Blue"));
        Assert.assertNotSame(null, field.getShepherd());
    }

    @Test
    public void setNumberOfSheepTest(){
        Assert.assertEquals(0, field.GetNumberOfSheep());
        field.SetNumberOfSheep(7);
        Assert.assertEquals(7, field.GetNumberOfSheep());
    }

    @Test
    public void setButtonTextTest(){
        Assert.assertEquals("0", field.getButton().getText());
        field.SetNumberOfSheep(7);
        Assert.assertEquals("7", field.getButton().getText());
    }
}
