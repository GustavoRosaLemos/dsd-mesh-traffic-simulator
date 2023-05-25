package meshtrafficsimulator.service;

import meshtrafficsimulator.models.Car;
import meshtrafficsimulator.models.Directions;
import meshtrafficsimulator.view.Home;

import java.io.IOException;
import java.util.ArrayList;

public class CarService {
    private static ArrayList<Car> cars = new ArrayList<>();
    private Home home;

    public CarService(Home home) throws IOException {
        this.home = home;
    }

    public void startService() throws IOException {
        addVehicle(new Car(4, 9, Directions.LEFT,  "2", 500, home, this));
        addVehicle(new Car(4, 7, Directions.LEFT,  "2", 1000, home, this));
        addVehicle(new Car(5, 0, Directions.RIGHT,  "2", 500, home, this));
        addVehicle(new Car(5, 2, Directions.RIGHT,  "2", 1000, home, this));
        addVehicle(new Car(0, 7, Directions.BOTTOM,  "2", 500, home, this));
        addVehicle(new Car(2, 7, Directions.BOTTOM,  "2", 1000, home, this));
        addVehicle(new Car(9, 8, Directions.TOP,  "2", 500, home, this));
        addVehicle(new Car(7, 8, Directions.TOP,  "2", 1000, home, this));
    }


    public void stopService() throws IOException {
        cars.forEach(car -> {
            car.stopThread();
        });
        cars.removeAll(cars);
        home.loadLevel(this);
    }

    public void stopCar(Car car) throws IOException {
        car.stopThread();
        removeVehicle(car.getId());
        home.loadLevel(this);
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
