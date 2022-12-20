import javax.swing.*;
import java.awt.*;

public class Bullet extends Rectangle{

    Image image;

    Bullet(int topLeftx, int topLefty, int width, int height) {
        super(topLeftx, topLefty, width, height);
        image = new ImageIcon("/Users/Wing/IdeaProjects/CIS 36B Final Project/src/bullet.png").getImage();
    }

    public void move() {
        y = y - 50;
    }

    public void draw(Graphics g) {
        //g.fillRect(x, y, width, height);
        g.drawImage(image, x , y , width , height , null);

    }
}
