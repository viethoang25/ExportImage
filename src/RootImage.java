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
    private static final String ROOT_IMAGE_FOLDER = "./image/";
    private static final String SUB_IMAGE_FOLDER = "./subImage/";
    final String INPUT_FILE = "PNG";
    final String OUTPUT_FILE = "PNG";

    BufferedImage subImage;
    BufferedImage image;
    File file;

    public RootImage() {
    }

    public RootImage(String filename) {
        readFile(filename);
    }

    public BufferedImage getSubImage() {
        return subImage;
    }

    // Doc file hinh anh va luu vao bien image
    private void readFile(String filename) {
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
            System.out.println(index);
            stringIndex = getStringNumber(index);
            director = SUB_IMAGE_FOLDER + name + "/" + name + stringIndex + "." + OUTPUT_FILE;
            checkFile = new File(director);
        }
        checkFile.getParentFile().mkdirs();
        try {
            checkFile.createNewFile();
            ImageIO.write(subImage, OUTPUT_FILE, new FileOutputStream(director));
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
}
