import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("The Flappy Bird!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setResizable(false);


        Workile tile = new Workile();


        frame.setLayout(null);
        frame.add(tile);

        frame.setVisible(true);
    }
}
