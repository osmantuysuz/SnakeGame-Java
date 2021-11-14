import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JLabel {

    public Box              box     = new Box();
    public Timer            mTimer  = null;
    public Bait             mBait   = new Bait();
    public Random           mRandom = null;
    public ArrayList<Box>   list    = new ArrayList<Box>();

    //Ekranda yılanın içinde bulunacağı oyun alanının çizimi.
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D r2 = new Rectangle2D.Double(5,5, getWidth()-10, getHeight()-10);

        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(10));
        g2.draw(r2);
    }


    Snake() {
        mRandom = new Random(System.currentTimeMillis());
        addKeyListener(new KeyboardControl());
        setFocusable(true);
        mTimer = new Timer(100, new TimerControl());
        mTimer.start();

        list.add(box);
        for (int i=1; i<10;i++){
            addQueue();
        }

        add(mBait);
        add(box);
    }

    public void addQueue(){
        Box box = list.get(list.size()-1).addTail();

        list.add(box);
        add(box);
    }

    public void addBait(){
        int width = getWidth()-30- mBait.mWidth;
        int height = getHeight()-30-mBait.mWidth;

        int posX = 10 + Math.abs(mRandom.nextInt())%width;
        int posY = 10 + Math.abs(mRandom.nextInt())%height;

        posX = posX - posX%20;
        posY = posY - posY%20;

        for (int i=0; i<list.size(); i++){
            if (posX==list.get(i).getX() && posY==list.get(i).getY()){
                addBait();
                return;
            }
        }

        mBait.setPosition(posX, posY);
    }

    public void allMovement(){
        for (int i=list.size()-1; i>0; i--){
            Box previous = list.get(i-1);
            Box next = list.get(i);

            list.get(i).movement();
            next.mDirection = previous.mDirection;
        }
        box.movement();
    }

    //Çarpışma durumları kontrol ediliyor
    public boolean isImpact(){
        int wall = 10;
        int width = getWidth();
        int height = getHeight();

        if (box.getX()<=10)
            return true;
        if (box.getX()+ box.mBoxSquare >= width-wall)
            return true;
        if (box.getY()<=10)
            return true;
        if (box.getY()+ box.mBoxSquare >= height-wall)
            return true;

        //Kendisi ile çarpışma
        for (int i=1; i<list.size(); i++){
            int x = list.get(i).getX();
            int y = list.get(i).getY();

            if ((x==box.getX())&&(y==box.getY()))
                return true;
        }

        if (mBait.getX() == box.getX() && mBait.getY() == box.getY()){
            addQueue();
            addBait();
        }


        return false;
    }

    //Kullanıcının klavyeden hangi tuşa bastığının kontrolü
    class KeyboardControl implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                if (box.mDirection != Direction.right)
                    box.mDirection = Direction.left;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                if (box.mDirection != Direction.left)
                    box.mDirection = Direction.right;
            }

            if (e.getKeyCode() == KeyEvent.VK_UP){
                if (box.mDirection != Direction.down)
                    box.mDirection = Direction.up;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN){
                if (box.mDirection != Direction.up)
                    box.mDirection = Direction.down;
            }


        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    class TimerControl implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            allMovement();
            if (isImpact())
                mTimer.stop();
        }
    }

}
