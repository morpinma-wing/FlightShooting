import javax.swing.*;

public class FlightBackground extends JFrame{

    FlightBackground(){

        this.add(new FlightMap());
        this.setTitle("Flight");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new FlightBackground();
    }
}
