import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class Pipe {
    int gap = 150;
    int x ;
    int width  = 40;
    int topheigth;
    int speed = 4;

    public Pipe(int x,int topheigth){
        this.x =  x;

        Random random = new Random();
        this.topheigth = random.nextInt(topheigth -gap -200)+50;
    }

    public void update(){
        x -= speed;
    }
}

public class Workile extends JPanel implements ActionListener, KeyListener {

    int width = 400;
    int heigth = 600;

    int flappyHeight = 30;
    int flappyWidth = 30;
    int flappyX = 200;
    int flappyY = 300;

    private double velocity = 0;
    private final double gravity = 0.5;
    private Timer timer;

    private ArrayList<Pipe> pipes = new ArrayList<>();
    private int pipespwan = 0;

    private boolean gameOver = false;
    private int score;

    Workile(){
        this.setSize(width,heigth);
        this.setBackground(Color.CYAN);

        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(17,this);
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //ground
        g.setColor(Color.ORANGE);
        g.fillRect(0,heigth-100,width,100);

        //grass
        g.setColor(Color.GREEN);
        g.fillRect(0,heigth-110,width,10);

        //bird
        g.setColor(Color.YELLOW);
        g.fillRect(flappyX,flappyY,flappyWidth,flappyHeight);

        //pipe
        g.setColor(Color.GREEN);
        for(Pipe p : pipes){
            g.fillRect(p.x,0,p.width,p.topheigth);
            g.fillRect(p.x, p.topheigth+p.gap,p.width,heigth-p.topheigth);
        }

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("Game Over!", width / 2 - 130, heigth / 2);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Score:"+score  , width-50 , 10);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        velocity += gravity;
        flappyY += velocity;

        pipespwan++;

        if (flappyY + flappyHeight >= heigth -100){
            flappyY = heigth - 100 - flappyHeight;
            velocity = 0;
            gameOver = true;
            timer.stop();
        }

        if (flappyY < 0){
            velocity =  0 ;
            flappyY = 0;
            gameOver = true;
            timer.stop();
        }

        if (pipespwan > 90){
            pipes.add(new Pipe(width,heigth));
            pipespwan = 0;
        }

        Iterator<Pipe> iter = pipes.iterator();
        while (iter.hasNext()){
            Pipe p = iter.next();
            p.update();

            if (p.x +p.width <0){
                iter.remove();
            }
        }

        Rectangle flappyRect = new Rectangle(flappyX,flappyY,flappyWidth,flappyHeight);

        for (Pipe p : pipes) {
            Rectangle topPipe = new Rectangle(p.x, 0, p.width, p.topheigth)   ;
            Rectangle bottomPipe = new Rectangle(p.x, p.topheigth + p.gap, p.width, heigth - p.topheigth - p.gap - 100);

            if (flappyRect.intersects(topPipe) || flappyRect.intersects(bottomPipe)) {
                gameOver = true;
                timer.stop();
            }

            if (flappyRect.getX() == p.x+p.width){
                score++;
            }
        }


        repaint();
    }

    private void resetGame() {
        flappyX = 200;
        flappyY = 300;
        velocity = 0;
        pipes.clear();
        pipespwan = 0;
        score = 0;
        gameOver = false;
        timer.start();
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            velocity = -8;
        }
        if (gameOver && keyEvent.getKeyCode() == KeyEvent.VK_SPACE) resetGame();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
