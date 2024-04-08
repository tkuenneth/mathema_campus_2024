import java.awt.*;
import java.util.function.*;
import javax.swing.*;

public class SimpleThreadDemo extends JFrame {

    private JLabel labelA;
    private JLabel labelB;

    public SimpleThreadDemo() {
        super("Simple Thread Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createUI());
        setSize(200, 100);
    }

    public void startCounting() {
        count(10, 20, 2000, (value) -> labelA.setText(value.toString()));
        count(20, 25, 4000, (value) -> labelB.setText(value.toString()));        
    }

    public JPanel createUI() {
        JPanel cp = new JPanel(new BorderLayout());
        Box box = Box.createVerticalBox();
        box.add(labelA = new JLabel());
        labelA.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(labelB = new JLabel());
        labelB.setAlignmentX(Component.CENTER_ALIGNMENT);
        cp.add(box, BorderLayout.CENTER);
        return cp;
    }

    private void count(int start, int end, long delay, Consumer<Integer> callback) {
        new Thread(() -> {
            for (int i = start; i <= end; i++) {
                final int _i = i;
                SwingUtilities.invokeLater(() -> {
                    callback.accept(_i);
                });
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleThreadDemo demo = new SimpleThreadDemo();
            demo.setLocationRelativeTo(null);
            demo.startCounting();
            demo.setVisible(true);
        });
    }
}
