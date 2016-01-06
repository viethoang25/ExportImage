import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by VietHoang on 20/12/2015.
 */
public class MainForm extends JFrame {
    private JButton btnApply;
    private JButton btnOpen;
    private JTextField txtName;
    private JTextField txtFile;
    private JPanel rootPanel;
    private JPanel imagePanel;
    private JPanel filePanel;
    private JPanel applyPanel;
    private JLabel imageLabel;

    public RootImage rootImage;
    Rectangle captureRect;

    public MainForm() {
        super("Hello Form");
        createGui();
        actionListener();
    }

    public void actionListener() {
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtName.getText().isEmpty()) {
                    JOptionPane.showConfirmDialog(MainForm.this, "NameTextfield is not empty");
                } else {
                    JOptionPane.showConfirmDialog(MainForm.this, txtName.getText() + " is created");
                    MouseHandle mouse = MouseHandle.getInstance();
                    // Cat hinh vuong
                    rootImage.createSubImage(mouse.getRectX(), mouse.getRectY(), mouse.getEdge(), mouse.getEdge());

                    // Cat hinh chu nhat
                    // rootImage.createSubImage(mouse.getX(), mouse.getY(), mouse.getDx(), mouse.getDy());
                    rootImage.saveSubImage(txtName.getText().toString());
                }
            }
        });

        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootImage = new RootImage(txtFile.getText());
                if (!rootImage.file.exists()) {
                    JOptionPane.showConfirmDialog(MainForm.this, txtFile.getText() + " is not existed");
                } else {
                    BufferedImage screen = rootImage.resizeImage(1000, 550);
                    BufferedImage screenCopy = new BufferedImage(
                            screen.getWidth(),
                            screen.getHeight(),
                            screen.getType());
                    imagePanel.removeAll();
                    imageLabel = new JLabel(new ImageIcon(screenCopy));
                    //imageLabel = new JLabel(new ImageIcon(rootImage.resizeImage(1000, 550)));
                    imageLabel.setBounds(0, 0, 1000, 550);
                    imagePanel.add(imageLabel);
                    repaint(screen, screenCopy);
                    imageLabel.repaint();
                    imageLabel.addMouseListener(MouseHandle.getInstance());
                    imageLabel.addMouseMotionListener(MouseHandle.getInstance());
                    imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
                        @Override
                        public void mouseMoved(MouseEvent me) {
                            repaint(screen, screenCopy);
                            imageLabel.repaint();
                        }

                        @Override
                        public void mouseDragged(MouseEvent me) {
                            repaint(screen, screenCopy);
                            imageLabel.repaint();
                        }
                    });
                    JOptionPane.showConfirmDialog(MainForm.this, txtFile.getText() + " is opened");
                    revalidate();
                    repaint();
                }
            }
        });
    }

    private void createGui() {
        txtFile = new JTextField();
        txtFile.setPreferredSize(new Dimension(500, 50));
        btnOpen = new JButton("Open");
        btnOpen.setPreferredSize(new Dimension(100, 50));
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(500, 50));
        btnApply = new JButton("OK");
        btnApply.setPreferredSize(new Dimension(100, 50));

        rootPanel = new JPanel();
        rootPanel.setLayout(null);

        imagePanel = new JPanel();
        imagePanel.setBounds(0, 0, 1000, 550);

        filePanel = new JPanel();
        filePanel.add(btnOpen);
        filePanel.add(txtFile);
        filePanel.setBounds(0, 550, 1000, 50);

        applyPanel = new JPanel();
        applyPanel.add(btnApply);
        applyPanel.add(txtName);
        applyPanel.setBounds(0, 600, 1000, 50);

        rootPanel.add(imagePanel);
        rootPanel.add(filePanel);
        rootPanel.add(applyPanel);
    }

    public void showGui() {
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void repaint(BufferedImage root, BufferedImage copy) {
        Graphics2D g = copy.createGraphics();
        g.drawImage(root, 0, 0, null);
        Rectangle captureRect = MouseHandle.getInstance().getRectangle();
        if (captureRect != null) {
            g.setColor(Color.BLUE);
            g.draw(captureRect);
            g.setColor(new Color(0, 100, 255, 80));
            g.fill(captureRect);
        }
        g.dispose();
    }
}
