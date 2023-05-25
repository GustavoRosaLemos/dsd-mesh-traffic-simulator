package meshtrafficsimulator.service;

import meshtrafficsimulator.Utils.Utils;
import meshtrafficsimulator.constant.Constant;
import meshtrafficsimulator.service.ImageService;
import meshtrafficsimulator.view.Home;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LevelService extends Thread {

    private volatile boolean running = false;
    private Home home;

    private CarService carService;

    public void stopThread() {
        running = false;
    }


    public LevelService() {
    }

    public LevelService(Home home, CarService carService) {
        this.home = home;
        this.carService = carService;
    }

    public void run() {
        while (running) {
            try {
                sleep(Constant.SCREEN_UPDATE_DELAY);
                if (running) {
                    home.loadLevel(carService);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public JLabel[][] convertLevel(String fileName) throws IOException {
        try {
            File file = new File("src/main/java/meshtrafficsimulator/levels/"+fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int rowSize = Integer.parseInt(bufferedReader.readLine());
            int colSize = Integer.parseInt(bufferedReader.readLine());
            JLabel[][] grid = new JLabel[rowSize][colSize];

            for (int row = 0; row < grid.length; row++) {
                String line = bufferedReader.readLine();
                String[] lineCol = line.split("\\s");
                for (int col = 0; col < grid[0].length; col++) {
                    ImageService imageService = new ImageService();
                    ImageIcon imageIcon = new ImageIcon(imageService.getImage(imageService.convertIntToFileName(lineCol[col], false, null)).getScaledInstance(Constant.IMAGE_SIZE, Constant.IMAGE_SIZE, Image.SCALE_SMOOTH));
                    imageIcon.setDescription(lineCol[col]);
                    JLabel lblMalha = new JLabel(imageIcon);
                    grid[row][col] = lblMalha;
                }
            }
            return grid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JLabel[0][];
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
