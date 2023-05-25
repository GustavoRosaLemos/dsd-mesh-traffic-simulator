package meshtrafficsimulator.models;

import meshtrafficsimulator.service.CarService;
import meshtrafficsimulator.view.Home;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Collectors;

public class Car extends Thread {
    private long id = new Random().nextLong();;
    private int positionRow;
    private int positionCol;
    private Directions direction;
    private int nextPositionRow;
    private int nextPositionCol;
    private String roadType;
    private int speed;

    private Home home;

    private CarService carService;

    private boolean spawned = false;

    private volatile boolean running = false;

    public Car(int positionRow, int positionCol, Directions direction, String roadType, int speed, Home home, CarService carService) {
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.direction = direction;
        this.roadType = roadType;
        this.speed = speed;
        this.home = home;
        this.carService = carService;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public Directions getDirection() {
        return direction;
    }

    public int getNextPositionRow() {
        return nextPositionRow;
    }

    public int getNextPositionCol() {
        return nextPositionCol;
    }

    public String getRoadType() {
        return roadType;
    }

    public int getSpeed() {
        return speed;
    }

    public void stopThread() {
        running = false;
    }

    public void next() throws IOException, InterruptedException {
        if (!running) {
            return;
        }
        if (!spawned) {
            carService.addVehicle(this);
            spawned = true;
            return;
        }
        switch (direction) {
            case RIGHT: {
                int nextPositionCol = positionCol + 1;
                boolean hasCarAhead = carService.getCars().stream()
                        .anyMatch(car -> car.positionRow == positionRow && car.positionCol == nextPositionCol && car.id != getId());
                if(hasCarAhead) {
                    return;
                }
                positionCol = nextPositionCol;
                if (positionCol >= home.getGrid()[positionRow].length) {
                    carService.stopCar(this);
                    return;
                }
                carService.removeVehicle(id);
                carService.addVehicle(this);
                break;
            }
            case LEFT: {
                int nextPositionCol = positionCol - 1;
                boolean hasCarAhead = carService.getCars().stream()
                        .anyMatch(car -> car.positionRow == positionRow && car.positionCol == nextPositionCol && car.id != getId());
                if(hasCarAhead) {
                    return;
                }
                positionCol = nextPositionCol;
                if (positionCol < 0) {
                    carService.stopCar(this);
                    return;
                }
                carService.removeVehicle(id);
                carService.addVehicle(this);
                break;
            }
            case BOTTOM: {
                int nextPositionRow = positionRow + 1;
                boolean hasCarAhead = carService.getCars().stream()
                        .anyMatch(car -> car.positionRow == nextPositionRow && car.positionCol == positionCol && car.id != getId());
                if(hasCarAhead) {
                    return;
                }
                positionRow = nextPositionRow;
                if (positionRow >= home.getGrid().length) {
                    carService.stopCar(this);
                    return;
                }
                carService.removeVehicle(id);
                carService.addVehicle(this);
                break;
            }
            case TOP: {
                int nextPositionRow = positionRow - 1;
                boolean hasCarAhead = carService.getCars().stream()
                        .anyMatch(car -> car.positionRow == nextPositionRow && car.positionCol == positionCol && car.id != getId());
                if(hasCarAhead) {
                    return;
                }
                positionRow = nextPositionRow;
                if (positionRow < 0) {
                    carService.stopCar(this);
                    return;
                }
                carService.removeVehicle(id);
                carService.addVehicle(this);
                break;
            }
            default: {
                break;
            }
        }
    }

    public long getId() {
        return id;
    }

    public void run() {
        while (running) {
            System.out.println("Executando thread...");
            try {
                sleep(speed);
                next();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
