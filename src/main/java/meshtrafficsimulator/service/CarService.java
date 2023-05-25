package meshtrafficsimulator.service;

import meshtrafficsimulator.Utils.Utils;
import meshtrafficsimulator.constant.Constant;
import meshtrafficsimulator.models.Car;
import meshtrafficsimulator.models.Directions;
import meshtrafficsimulator.models.Spawn;
import meshtrafficsimulator.view.Home;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CarService {
    private static ArrayList<Car> cars = new ArrayList<>();
    private static  ArrayList<Spawn> spawns = new ArrayList<>();
    private Home home;
    private VehicleGenerateService vehicleGenerateService = new VehicleGenerateService(this);
    private LevelService levelService;
    private boolean running = false;

    public CarService(Home home) throws IOException {
        this.home = home;
        levelService = new LevelService(home, this);
    }

    public void startService() throws IOException {
        if (!running) {
            running = true;
            loadVehicleSpawn();
            vehicleGenerateService.start();
            vehicleGenerateService.setRunning(true);
            levelService.start();
            levelService.setRunning(true);
        }
    }

    public void loadVehicleSpawn() {
        JLabel[][] grid = home.getGrid();
        if (grid != null) {

            for (int row = 0; row < grid.length; row ++) {
                ImageIcon imageIconLeft = (ImageIcon) grid[row][0].getIcon();
                if (imageIconLeft.getDescription().equals("2")) {
                    spawns.add(new Spawn(row, 0, Directions.RIGHT));
                }

                ImageIcon imageIconRight = (ImageIcon) grid[row][grid[0].length - 1].getIcon();
                if (imageIconRight.getDescription().equals("4")) {
                    spawns.add(new Spawn(row, grid[0].length - 1, Directions.LEFT));
                }
            }

            for (int col = 0; col < grid[0].length; col ++) {
                ImageIcon imageIconTop = (ImageIcon) grid[0][col].getIcon();
                if (imageIconTop.getDescription().equals("3")) {
                    spawns.add(new Spawn(0, col, Directions.BOTTOM));
                }

                ImageIcon imageIconBottom = (ImageIcon) grid[grid.length - 1][col].getIcon();
                if (imageIconBottom.getDescription().equals("1")) {
                    spawns.add(new Spawn(grid.length - 1, col, Directions.TOP));
                }
            }
        }
    }

    public void spawnVehicle() throws IOException {
        Spawn spawn = spawns.get(new Random().nextInt(spawns.size()));
        ImageIcon imageIcon = (ImageIcon) home.getGrid()[spawn.getPositionRow()][spawn.getPositionCol()].getIcon();
        String roadType = imageIcon.getDescription();
        addVehicle(new Car(spawn.getPositionRow(), spawn.getPositionCol(), spawn.getDirection(), roadType, Utils.getRandomNumber(Constant.CAR_SPEED_MIN, Constant.CAR_SPEED_MAX), home, this));
    }


    public void stopService() throws IOException {
        if (running) {
            cars.forEach(car -> {
                car.stopThread();
            });
            cars.removeAll(cars);
            running = false;
            vehicleGenerateService.stopThread();
            levelService.stopThread();
        }
    }

    public void stopCar(Car car) throws IOException {
        car.stopThread();
        removeVehicle(car.getId());
    }

    public void addVehicle(Car car) throws IOException {
        Integer maxVehicles = home.getMaxVehicles();
        if (cars.size() < maxVehicles) {
            cars.add(car);
            if (!car.isRunning()) {
                car.setRunning(true);
                car.start();
            }
        }
    }

    public void removeVehicle(long id) throws IOException {
        cars.removeIf((car) -> car.getId() == id);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
