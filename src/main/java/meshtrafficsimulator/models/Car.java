package meshtrafficsimulator.models;

import meshtrafficsimulator.Utils.Utils;
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
                chooseDirection();
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
                chooseDirection();
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
                chooseDirection();
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
                chooseDirection();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void chooseDirection() {
        ImageIcon imageIcon = (ImageIcon) home.getGrid()[positionRow][positionCol].getIcon();
        String roadType = imageIcon.getDescription();

         switch (roadType) {
            case "5": {
                direction = Directions.TOP;
                break;
            }
             case "6": {
                 direction = Directions.RIGHT;
                 break;
             }
             case "7": {
                 direction = Directions.BOTTOM;
                 break;
             }
             case "8": {
                 direction = Directions.LEFT;
                 break;
             }
             case "9": {
                 int random = Utils.getRandomNumber(1, 10);
                 System.out.println("RANDOM " + random);
                 if ((random % 2) == 0) {
                    if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow - 1 && car.positionCol == positionCol)) {
                        direction = Directions.RIGHT;
                        break;
                    }
                    direction = Directions.TOP;
                 } else {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow && car.positionCol == positionCol + 1)) {
                         direction = Directions.TOP;
                         break;
                     }
                     direction = Directions.RIGHT;
                 }
                 break;
             }
             case "10": {
                 int random = Utils.getRandomNumber(1, 10);
                 if ((random % 2) == 0) {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow - 1 && car.positionCol == positionCol)) {
                         direction = Directions.LEFT;
                         break;
                     }
                     direction = Directions.TOP;
                 } else {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow && car.positionCol == positionCol - 1)) {
                         direction = Directions.TOP;
                         break;
                     }
                     direction = Directions.LEFT;
                 }
                 break;
             }
             case "11": {
                 int random = Utils.getRandomNumber(1, 10);
                 if ((random % 2) == 0) {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow && car.positionCol == positionCol + 1)) {
                         direction = Directions.BOTTOM;
                         break;
                     }
                     direction = Directions.RIGHT;
                 } else {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow + 1 && car.positionCol == positionCol)) {
                         direction = Directions.RIGHT;
                         break;
                     }
                     direction = Directions.BOTTOM;
                 }
                 break;
             }
             case "12": {
                 int random = Utils.getRandomNumber(1, 10);
                 if ((random % 2) == 0) {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow + 1 && car.positionCol == positionCol)) {
                         direction = Directions.LEFT;
                         break;
                     }
                     direction = Directions.BOTTOM;
                 } else {
                     if (carService.getCars().stream().anyMatch(car -> car.positionRow == positionRow && car.positionCol == positionCol - 1)) {
                         direction = Directions.BOTTOM;
                         break;
                     }
                     direction = Directions.LEFT;
                 }
                 break;
             }
            default:
                break;
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
