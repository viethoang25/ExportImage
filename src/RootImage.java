import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by VietHoang on 20/12/2015.
 */

public class RootImage {
    // Size
    private final int IMAGE_SIZE = 128;
    // Folder
    private static final String ROOT_IMAGE_FOLDER = "./image/";
    private static final String SUB_IMAGE_FOLDER = "./subImage/";
    // Dinh dang du lieu vao ra
    final String INPUT_FILE = "PNG";
    final String OUTPUT_FILE = "PNG";

    BufferedImage subImage;
    BufferedImage image;
    File file;

    // Ti le anh go so voi anh da scale
    float heightRate;
    float widthRate;

    public RootImage() {
    }

    public RootImage(String filename) {
        readFile(filename);
    }

    public BufferedImage getSubImage() {
        return subImage;
    }

    public BufferedImage getImage() {
        return image;
    }

    // Doc file hinh anh va luu vao bien image
    private void readFile(String filename) {
        heightRate = 1;
        widthRate = 1;
        String director = ROOT_IMAGE_FOLDER + filename + "." + INPUT_FILE;
        file = new File(director);
        System.out.println(file);
        try {
            if (file.exists()) image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cat hinh anh theo tao do da cho va luu vao bien subImage
    public void createSubImage(int x, int y, int dx, int dy) {
        if (dx < 0) x = x + dx;
        if (dy < 0) y = y + dy;
        int width = Math.abs(dx);
        int height = Math.abs(dy);

        // Bien doi toa do dua vao ti le scale
        x = (int) (x / widthRate);
        y = (int) (y / heightRate);
        width = (int) (width / widthRate);
        height = (int) (height / heightRate);

        System.out.println(widthRate + " " + heightRate);
        if (image != null) {
            subImage = image.getSubimage(x, y, width, height);
        }
    }

    // Luu hinh anh trong subImage vao mot file nao do
    public void saveSubImage(String name) {
        int index = 1;
        String stringIndex = getStringNumber(index);
        String director = SUB_IMAGE_FOLDER + name + "/" + name + stringIndex + "." + OUTPUT_FILE;
        File checkFile = new File(director);
        while (index < 999 && checkFile.exists()) {
            index++;
            stringIndex = getStringNumber(index);
            director = SUB_IMAGE_FOLDER + name + "/" + name + stringIndex + "." + OUTPUT_FILE;
            checkFile = new File(director);
        }
        checkFile.getParentFile().mkdirs();
        try {
            checkFile.createNewFile();
            BufferedImage resizeSubImage;
            resizeSubImage = toBufferedImage(subImage.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_DEFAULT));
            ImageIO.write(resizeSubImage, OUTPUT_FILE, new FileOutputStream(director));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Chuyen int thanh String 3 ky tu;
    private String getStringNumber(int index) {
        String number = "";
        number += Integer.toString((int) (index % Math.pow(10, 3)));
        switch (number.length()) {
            case 1:
                number = "00" + number;
                break;
            case 2:
                number = "0" + number;
                break;
        }
        return number;
    }

    public BufferedImage resizeImage(int width, int height) {
        int original_width = image.getWidth();
        int original_height = image.getHeight();
        int new_width = original_width;
        int new_height = original_height;

        if (original_width > width) {
            new_width = width;
            new_height = (new_width * original_height) / original_width;
        }

        if (new_height > height) {
            new_height = height;
            new_width = (new_height * original_width) / original_height;
        }

        // Dinh gia tri cho rate
        heightRate = (float) new_height / original_height;
        widthRate = (float) new_width / original_width;

        return toBufferedImage(image.getScaledInstance(new_width, new_height, Image.SCALE_DEFAULT));
    }

    private BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }
        // Tao mot buffered image trong
        BufferedImage buffImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Ve hinh vao trong buffered image vua tao
        Graphics2D g = buffImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        // Tra ve buffered image da ve
        return buffImage;
    }
}
