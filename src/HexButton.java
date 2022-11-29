import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * A six sided toggle button. This is not guaranteed to be a perfect hexagon, it is just guaranteed to have six sides in
 * the form of a hexagon. To be a perfect hexagon the size of this component must have a height to width ratio of
 * 1 to 0.866
 *
 * @author keang
 * @date 5 Jun 2009
 *
 */
public class HexButton extends JButton
{
    @Serial
    private static final long serialVersionUID = 4865976127980106774L;

    private Polygon hexagon = new Polygon();

    private Field field;

    private Point coords;
    private int size;

    public HexButton(){
        super();
    }
    public HexButton(Field field, Point coords, int size)
    {

        this.coords = coords;
        this.field = field;
        this.size = size;
        setForeground(Color.black);
        setBackground(new Color(4,159,4));
        setBounds((int) ( coords.x*size*6/5), 100+(coords.y*size*2), (int) (1.73205081*size), 2*size);
        Integer num = field.GetNumberOfSheep();

        setText(num.toString());
    }

    public Field getField() {
        return field;
    }

    public void editLegalSteps(boolean visible, ArrayList<Field> legalSteps){
        for (Field step:
                legalSteps){
            if(step != null){
                step.getButton().setSelected(visible);
            }
        }
    }

    public void setNumber(Integer number){
        setText(number.toString());
    }

    @Override
    public boolean contains(Point p)
    {
        return hexagon.contains(p);
    }

    @Override
    public boolean contains(int x, int y)
    {
        return hexagon.contains(x, y);
    }

    @Override
    public void setSize(Dimension d)
    {
        super.setSize(d);
        calculateCoords();
    }

    @Override
    public void setSize(int w, int h)
    {
        super.setSize(w, h);
        calculateCoords();
    }

    @Override
    public void setBounds(int x, int y, int width, int height)
    {
        super.setBounds(x, y, width, height);
        calculateCoords();
    }

    @Override
    public void setBounds(Rectangle r)
    {
        super.setBounds(r);
        calculateCoords();
    }

    @Override
    public void paintImmediately(int x, int y, int w, int h) {
        paintComponent(getGraphics());
    }


    @Override
    protected void processMouseEvent(MouseEvent e)
    {
        if ( contains(e.getPoint()) && e.getID() == MouseEvent.MOUSE_CLICKED) {
            field.getBoard().getTmp().Step(field);
        }
    }



    private void calculateCoords()
    {
        int w = getWidth()-1;
        int h = getHeight()-1;

        int ratio = (int)(h*.25);
        int nPoints = 6;
        int[] hexX = new int[nPoints];
        int[] hexY = new int[nPoints];

        hexX[0] = w/2;
        hexY[0] = 0;
        hexX[1] = w;
        hexY[1] = ratio;
        hexX[2] = w;
        hexY[2] = h - ratio;
        hexX[3] = w/2;
        hexY[3] = h;
        hexX[4] = 0;
        hexY[4] = h - ratio;
        hexX[5] = 0;
        hexY[5] = ratio;

        hexagon = new Polygon(hexX, hexY, nPoints);
    }




    @Override
    protected void paintComponent(Graphics g)
    {
        if ( isSelected() )
        {
            g.setColor(Color.red);
        }
        else
        {
            g.setColor(getForeground());
        }
        g.drawPolygon(hexagon);

        g.setColor(getBackground());

        g.fillPolygon(hexagon);

        g.setColor(getForeground());



        FontMetrics fm = getFontMetrics(getFont());
        Rectangle viewR = getBounds();
        Rectangle iconR = new Rectangle();
        Rectangle textR = new Rectangle();


        SwingUtilities.layoutCompoundLabel(this, fm, getText(), null,
                SwingUtilities.CENTER, SwingUtilities.CENTER, SwingUtilities.BOTTOM, SwingUtilities.CENTER,
                viewR, iconR, textR, 0);

        Point loc = getLocation();
        if(field.getShepherd() == field.getBoard().getPlayer1()) {
            Image image1 = new ImageIcon("purpleSheep.png").getImage();
            g.drawImage(image1, 10, 15, null);
        }
        if(field.getShepherd() == field.getBoard().getPlayer2()){
            Image image2 = new ImageIcon("blueSheep1.png").getImage();
            g.drawImage(image2, 10, 15, null);
        }
        g.drawString(getText(), 30, 70);

    }

    @Override
    protected void paintBorder(Graphics g)
    {
        // do not paint a border
    }




}