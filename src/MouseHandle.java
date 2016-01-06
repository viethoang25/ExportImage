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
    // Toa do goc va canh cua hinh vuong (duoc suy ra tu dx va dy)
    private int rectX;
    private int rectY;
    private int edge;
    // Kiem tra chuot dang o trong vung handle hay khong
    private boolean entered;
    // Vung hinh vuong duoc chon
    private Rectangle captureRect;

    private MouseHandle() {
        this.x = -1;
        this.y = -1;
        this.dx = -1;
        this.dy = -1;
        this.edge = -1;
        this.rectX = -1;
        this.rectY = -1;
        entered = false;
    }

    public static MouseHandle getInstance() {
        if (instance == null) {
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
            this.rectX = point.x;
            this.rectY = point.y;
            System.out.println("Press" + point);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*if (entered) {
            Point point = e.getPoint();
            this.dx = point.x - this.x;
            this.dy = point.y - this.y;
            if (Math.abs(dx) > Math.abs(dy)) edge = Math.abs(dx);
            else edge = Math.abs(dy);
            System.out.println("Release " + point);

            Component comp = e.getComponent().getComponentAt(point);
            int x = this.x, y = this.y;
            if (dx < 0) x = (x - edge >= 0) ? (x - edge) : 0;
            if (dy < 0) y = (y - edge >= 0) ? (y - edge) : 0;
            if (x + edge > comp.getWidth()) edge = comp.getWidth() - x;
            if (y + edge > comp.getHeight()) edge = comp.getHeight() - y;
            captureRect = new Rectangle(x, y, edge, edge);
        }*/
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
            edge = Math.abs(dy);
            System.out.println("Drag " + point);

            Component comp = e.getComponent().getComponentAt(point);
            int x = this.x, y = this.y;
            if (dx < 0) x = (x - edge >= 0) ? (x - edge) : 0;
            if (dy < 0) y = (y - edge >= 0) ? (y - edge) : 0;
            if (x + edge > comp.getWidth()) edge = comp.getWidth() - x;
            if (y + edge > comp.getHeight()) edge = comp.getHeight() - y;
            this.rectX = x;
            this.rectY = y;
            captureRect = new Rectangle(x, y, edge, edge);
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

    public int getRectX() {
        return rectX;
    }

    public int getRectY() {
        return rectY;
    }

    public int getEdge() {
        return edge;
    }

    public Rectangle getRectangle() {
        return captureRect;
    }

    public boolean isEntered() {
        return entered;
    }
}
