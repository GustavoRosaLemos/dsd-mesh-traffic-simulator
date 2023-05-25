package meshtrafficsimulator.service;

import meshtrafficsimulator.models.Car;
import meshtrafficsimulator.models.Directions;
import meshtrafficsimulator.view.Home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CarService {
    private static ArrayList<Car> cars = new ArrayList<>();
    private Home home;

    public CarService(Home home) throws IOException {
        this.home = home;
        addVehicle(new Car(5, 0, Directions.RIGHT, 5, 1, "2", 2000, home, this));
    }


    public void stopService() {
        cars.forEach(Car::stopThread);
    }

    public void addVehicle(Car car) throws IOException {
        Integer maxVehicles = home.getMaxVehicles();
        if (cars.size() <= maxVehicles) {
            cars.add(car);
            if (!car.isRunning()) {
                car.setRunning(true);
                car.start();
            }
            home.loadLevel(this);
        }
    }

    public void removeVehicle(long id) throws IOException {
        cars.removeIf((car) -> car.getId() == id);
        home.loadLevel(this);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }
}
