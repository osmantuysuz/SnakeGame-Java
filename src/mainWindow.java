import javax.swing.JFrame;
import java.awt.*;

public class mainWindow extends JFrame{

    private int mHeight = 600, mWidth = 600;
    private static mainWindow mWindow = null;

    private mainWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SetDimension(mHeight, mWidth);
        setResizable(false);

        Snake snake = new Snake();
        add(snake);
    }

    public static mainWindow getWindow(){
        if (mWindow==null)
            mWindow = new mainWindow();

        return mWindow;
    }

    //Program başlatılırken ekranın merkezden başlatılması için yazıldı.
    public void SetDimension(int height, int width){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int positionX = 0, positionY = 0;

        if (mWidth+100>dim.width)
            mWidth = dim.width-100;
        if (mHeight+100>dim.height)
            mHeight = dim.height-100;

        positionX = (dim.width-mWidth)/2;
        positionY = (dim.height-mHeight)/2;

        setBounds(positionX, positionY, mWidth, mHeight);
    }
}
