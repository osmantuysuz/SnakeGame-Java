import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Box extends JLabel {

    public int mBoxSquare = 20;
    public int mDirection = Direction.right;

    Box() {
        setBounds(100, 100, mBoxSquare, mBoxSquare );
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D r2 = new Rectangle2D.Double(1,1, getWidth()-2, getHeight()-2);

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2));
        g2.draw(r2);
        g2.setColor(Color.red);
        g2.fill(r2);
    }

    public void goLeft(){
        int posX = getX();
        int posY = getY();

        posX -= getWidth();
        setBounds(posX, posY, getWidth(), getHeight());
    }
    public void goRight(){
        int posX = getX();
        int posY = getY();

        posX += getWidth();
        setBounds(posX, posY, getWidth(), getHeight());
    }
    public void goUp(){
        int posY = getY();
        int posX = getX();

        posY -= getHeight();
        setBounds(posX, posY, getWidth(), getHeight());
    }
    public void goDown(){
        int posY = getY();
        int posX = getX();

        posY += getHeight();
        setBounds(posX, posY, getWidth(), getHeight());
    }

    //Yılanın birer birer büyümesi
    public Box addTail(){
        Box box = new Box();

        int x = getX();
        int y = getY();

        box.setBounds(x, y, getWidth(), getHeight());
        box.mDirection = -mDirection;
        box.movement();
        box.mDirection = mDirection;


        return box;
    }

    public void movement(){
        if (mDirection == Direction.left)
            goLeft();
        else if (mDirection == Direction.right)
            goRight();
        else if (mDirection == Direction.down)
            goDown();
        else
            goUp();
    }
}
