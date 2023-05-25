package meshtrafficsimulator.service;

import meshtrafficsimulator.service.ImageService;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LevelService {

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
                    ImageIcon imageIcon = new ImageIcon(imageService.getImage(imageService.convertIntToFileName(lineCol[col], false, null)).getScaledInstance(35, 35, Image.SCALE_SMOOTH));
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
}
