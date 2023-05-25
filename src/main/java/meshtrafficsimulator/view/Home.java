package meshtrafficsimulator.view;

import meshtrafficsimulator.constant.Constant;
import meshtrafficsimulator.models.Car;
import meshtrafficsimulator.models.Directions;
import meshtrafficsimulator.service.CarService;
import meshtrafficsimulator.service.LevelService;
import meshtrafficsimulator.service.ImageService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Home extends JFrame {
    private JButton buttonStop;
    private JButton buttonStart;
    private JPanel panelMail;
    private JTextField textFieldMaxVehicles;
    private JPanel panelTable;

    LevelService levelService = new LevelService();
    ImageService imageService = new ImageService();
    CarService carService = new CarService(this);

    private JLabel[][] grid;





    public Home() throws IOException {
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.out.println("Iniciar!");
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancelar!");
            }
        });
        loadLevel(carService);
        setContentPane(panelMail);
        setTitle("Simulador de Tráfego em Malha Viária");
        setSize(1000, 800);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public synchronized void loadLevel(CarService carService) throws IOException {
        System.out.println("carregando nível...");
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        panelTable.removeAll();
        panelTable.setLayout(layout);
        panelTable.revalidate();

        this.grid = levelService.convertLevel("malha-exemplo-1.txt");
//        carService.addVehicle(new Car(5, 0, Directions.RIGHT, 5, 1, "2", 3000, this, carService));
        ArrayList<Car> cars = carService.getCars();

        for (int row = 0; row < grid.length; row++) {
            constraints.gridy = row;
            for (int col = 0; col < grid[0].length; col++) {
                int finalRow = row;
                int finalCol = col;
                Optional<Car> carOptional = cars.stream().filter(car -> car.getPositionRow() == finalRow && car.getPositionCol() == finalCol).findFirst();
                if (carOptional.isPresent()) {
                    Car car = carOptional.get();
                    System.out.println("position " + car.getPositionCol());
                    ImageIcon imageIcon = new ImageIcon(imageService.getImage(imageService.convertIntToFileName(car.getRoadType(), true, car.getDirection())).getScaledInstance(Constant.IMAGE_SIZE, Constant.IMAGE_SIZE, Image.SCALE_SMOOTH));
                    grid[row][col].setIcon(imageIcon);
                }
                constraints.gridx = col;
                panelTable.add(grid[row][col], constraints);
            }
        }
    }

    public int getMaxVehicles() {
        return Integer.parseInt(textFieldMaxVehicles.getText());
    }
}
