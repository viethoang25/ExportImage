import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by VietHoang on 21/12/2015.
 */
public class MouseHandle implements MouseListener, MouseMotionListener {

    // Xay dung lop MouseHandle singleTon
    private static MouseHandle instance = null;
    // Toa do cua chuot
    private int x;
    private int y;
    private int dx;
    private int dy;
    // Kiem tra chuot dang o trong vung handle hay khong
    private boolean entered;

    private MouseHandle() {
        this.x = -1;
        this.y = -1;
        this.dx = -1;
        this.dy = -1;
        entered = false;
    }

    public static MouseHandle getInstance(){
        if(instance == null){
            instance = new MouseHandle();
        }
        return instance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (entered) {
            Point point = e.getPoint();
            this.x = point.x;
            this.y = point.y;
            System.out.println("Press" + point);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (entered) {
            Point point = e.getPoint();
            this.dx = point.x - this.x;
            this.dy = point.y - this.y;
            System.out.println("Release" + point);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        entered = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        entered = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (entered) {
            Point point = e.getPoint();
            this.dx = point.x - this.x;
            this.dy = point.y - this.y;
            System.out.println("Drag" + point);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public boolean isEntered() {
        return entered;
    }
}
