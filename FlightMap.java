import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class FlightMap extends JPanel implements ActionListener, MouseListener{

    static final int SCREEN_WIDTH = 750;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int DELAY = 175;
    int x ;
    int y ;
    int applesEaten;
    int score;
    int finalScore;
    int lives;
    final int bulletNumber = 1;
    int enemyNumber = 1;
    char direction = 'R';
    boolean running = false;
    Image background;
    String name;
    Image flight;
    //Image heart;
    //JLabel label;
    Timer timer;
    Bullet[] bullet = {};
    Enemy[] enemies = {};
    ArrayList <Bullet> bulletArrayList = new ArrayList<>();
    ArrayList<Enemy> enemyArrayList = new ArrayList<>();


    FlightMap(){

        getPlayerName();
        lives = 5;

        /*label = new JLabel("PAUSE");
        label.setBackground(Color.white);
        label.setBounds(SCREEN_WIDTH*1/4 , SCREEN_HEIGHT/2 , SCREEN_WIDTH/2 , 500);

         */

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        //this.add(label);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(this);
        flight = new ImageIcon("/Users/Wing/IdeaProjects/CIS 36B Final Project/src/hero0.png").getImage();
        background = new ImageIcon("/Users/Wing/IdeaProjects/CIS 36B Final Project/src/space.jpg").getImage();
        //heart = new ImageIcon("/Users/Wing/IdeaProjects/Flight shooting/src/heart.jpg").getImage();

        startGame();
    }

    public void newBullet(){
        bullet = new Bullet[1];

        for (int i =0 ; i < bulletNumber ; i++){
            bullet[i] = new Bullet(this.x + UNIT_SIZE / 2, this.y - 10, 10, 50);
            bulletArrayList.add(bullet[i]);
        }
    }

    public boolean bulletCounter(){
        boolean counter = false;

        if (bulletNumber == 0 ){
            counter = false;
        }
        else
            counter = true;

        return counter;
    }

    public void newEnemy(){

        Random random = new Random();
        int randomXPosition = random.nextInt(SCREEN_WIDTH);

        enemies = new Enemy[1];

        for (int i =0 ; i < enemyNumber ; i++){
            enemies[i] = new Enemy(randomXPosition, 0, 50, 50);
            enemyArrayList.add(enemies[i]);
        }
    }

    public boolean checkEnemyOutOfFrame(){

        boolean key = false;

        for (int i = 0; i < enemyArrayList.size(); i++) {
            if (enemyArrayList.get(i).y > SCREEN_HEIGHT) {
                key = true;
            }
        }

        return key;
    }

    public void deleteEnemy(){

        for (int i = 0; i < enemyArrayList.size(); i++) {
            if (enemyArrayList.get(i).y > SCREEN_HEIGHT) {
                enemyArrayList.get(i).setLocation(9999,-999999);
                //emeryArrayList.remove(i);
            }
        }

    }


    public boolean checkEnemyInFrame(){
        boolean key = false;

        for (int i = 0; i < enemyArrayList.size(); i++) {
            if ((enemyArrayList.get(i).y > SCREEN_HEIGHT*3/4)  & (enemyArrayList.get(i).y < SCREEN_HEIGHT )) {
                key = true;
            }
        }

        return key;
    }

    public void startGame() {
        newBullet();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){

        if(running) {

            g.drawImage(background , 0 , 0 , SCREEN_WIDTH , SCREEN_HEIGHT , null);  // draw Background

            g.drawImage(flight , x , y , UNIT_SIZE , UNIT_SIZE , null);   // draw flight

            g.setColor(Color.white);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

            drawLives(g);

            for (int i = 0; i< bulletArrayList.size() ; i++){
                bulletArrayList.get(i).draw(g);
            }

            for (int i = 0 ; i < enemyArrayList.size() ; i++ ){
                enemyArrayList.get(i).draw(g);
            }

        }

        else {
            timer.stop();

            if (score >= 50){
                gameWin(g);

            }
            else {
                gameOver(g);

            }

            writeScore();
        }

    }

    public void move(){

        switch(direction) {
            case 'W':
                y = y - UNIT_SIZE;
                break;
            case 'S':
                y = y + UNIT_SIZE;
                break;
            case 'A':
                x = x - UNIT_SIZE;
                break;
            case 'D':
                x = x + UNIT_SIZE;
                break;
        }

        for (int i = 0; i< bulletArrayList.size() ; i++){
            bulletArrayList.get(i).move();
        }


        for (int i = 0; i< enemyArrayList.size() ; i++){
            enemyArrayList.get(i).move();
        }


    }
    public void checkBorder() {

        if (x < 0){  // set bound for flight location
            x = 0;
        }

        if (x > SCREEN_WIDTH - UNIT_SIZE){ // set bound for flight location
            x = SCREEN_WIDTH - UNIT_SIZE;
        }

        if (y < 0){ // set bound for flight location
            y = 0;
        }

        if (y > SCREEN_HEIGHT - UNIT_SIZE){ // set bound for flight location
            y = SCREEN_HEIGHT - UNIT_SIZE;
        }

    }

    public void checkScore(){
        if (score >= 50){
            running = false;
        }
    }

    public void checkCollisions() {

        for (int i=0; i < bulletArrayList.size() ; i++){
            for (int j = 0; j < enemyArrayList.size(); j++) {
                if (bulletArrayList.get(i).intersects(enemyArrayList.get(j)) ){
                    bulletArrayList.get(i).setLocation(9999,9999);
                    enemyArrayList.get(j).setLocation(9999,-999999);
                    score++;
                    //bulletNumber++;
                }
            }
        }

        for (int i = 0; i < enemyArrayList.size(); i++) {
            if (enemyArrayList.get(i).contains(x,y)) {
                enemyArrayList.get(i).setLocation(99999, -99999);
                lives--;
            }
        }

        /*for (int i=0; i < bulletArrayList.size() ; i++){
            if (bulletArrayList.get(i).y < 0){
                bulletNumber++;
            }
        }

         */

    }

    public void gameOver(Graphics g) {
        //Score

        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ score, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));

        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    public void gameWin(Graphics g) {
        //Score

        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        g.drawString("Congratulation , you won. You have reach 100" , SCREEN_WIDTH/10 , SCREEN_HEIGHT/2 );

    }

    public void drawLives(Graphics g) {

        if (lives == 5 ){
            g.drawImage(flight, 700,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 650,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 600,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 550,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 500,700 , UNIT_SIZE, UNIT_SIZE, null);
        }

        if (lives == 4 ){
            g.drawImage(flight, 700,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 650,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 600,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 550,700 , UNIT_SIZE, UNIT_SIZE, null);
        }

        if (lives == 3 ){
            g.drawImage(flight, 700,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 650,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 600,700 , UNIT_SIZE, UNIT_SIZE, null);
        }

        if (lives == 2 ){
            g.drawImage(flight, 700,700 , UNIT_SIZE, UNIT_SIZE, null);
            g.drawImage(flight, 650,700 , UNIT_SIZE, UNIT_SIZE, null);
        }

        if (lives == 1 ){
            g.drawImage(flight, 700,700 , UNIT_SIZE, UNIT_SIZE, null);
        }

        if (lives == 0 ){
            running = false;
        }

    }

    public void getPlayerName(){
        name = JOptionPane.showInputDialog(" What is your name? ", "Hi there");

        JOptionPane.showMessageDialog(null , "Hi " + name);

    }

    public void writeScore(){

        finalScore = score;

        try {

            File file = new File("/Users/Wing/IdeaProjects/CIS 36B Final Project/src/flightScore.txt");

            FileWriter fw = new FileWriter( file,true);
            // initialize    our BufferedWriter
            BufferedWriter bw = new BufferedWriter(fw);

            // write values
            bw.newLine();
            bw.write(name + "\t" + "\t" + finalScore );

            // close the BufferedWriter object to finish operation
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            if (checkEnemyInFrame()){
                newEnemy();
            }
            if (checkEnemyOutOfFrame()){
                deleteEnemy();
            }
            move();
            checkBorder();
            checkCollisions();
            checkScore();
        }

        repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        timer.start();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        timer.stop();
    }


    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT :
                    direction = 'A';
                    break;
                case KeyEvent.VK_RIGHT:
                    direction = 'D';
                    break;
                case KeyEvent.VK_UP:
                    direction = 'W';
                    break;
                case KeyEvent.VK_DOWN:
                    direction = 'S';
                    break;
                case KeyEvent.VK_SPACE:
                    if (bulletCounter()){
                        newBullet();
                        //bulletNumber--;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    newEnemy();
                    break;
            }
        }
    }
}
