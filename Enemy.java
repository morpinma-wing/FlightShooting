import javax.swing.*;
import java.awt.*;

public class Enemy extends Rectangle{

    Image image;

    Enemy(int topLeftx, int topLefty, int width, int height) { //get parameter when calling this function
        super(topLeftx, topLefty, width, height);
        image = new ImageIcon("/Users/Wing/IdeaProjects/CIS 36B Final Project/src/airplane.png").getImage();
    }

    public void move() {
        y = y + 50;
    }


    public void draw(Graphics g) {

        g.drawImage(image, x, y, width, height,null);
    }
}
