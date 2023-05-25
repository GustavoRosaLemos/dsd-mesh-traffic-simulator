package meshtrafficsimulator.models;

import meshtrafficsimulator.service.CarService;
import meshtrafficsimulator.view.Home;

import java.io.IOException;
import java.util.Random;

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

    public Car(int positionRow, int positionCol, Directions direction, int nextPositionRow, int nextPositionCol, String roadType, int speed, Home home, CarService carService) {
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.direction = direction;
        this.nextPositionRow = nextPositionRow;
        this.nextPositionCol = nextPositionCol;
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

    public void next() throws IOException {
        if (!spawned) {
            carService.addVehicle(this);
            spawned = true;
            return;
        }
        switch (direction) {
            case RIGHT: {
                //TODO fazer ele excluir o veículo para adicionar novamente.
                carService.removeVehicle(id);
                positionCol = positionCol + 1;
                carService.addVehicle(this);
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                next();
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
