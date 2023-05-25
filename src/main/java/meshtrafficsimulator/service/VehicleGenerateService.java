package meshtrafficsimulator.service;

import meshtrafficsimulator.Utils.Utils;
import meshtrafficsimulator.constant.Constant;
import meshtrafficsimulator.models.Car;

import java.io.IOException;

public class VehicleGenerateService extends Thread{

    private volatile boolean running = false;
    private CarService carService;

    public VehicleGenerateService(CarService carService) {
        this.carService = carService;
    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        while (running) {
            System.out.println("Executando thread...");
            try {
                sleep(Utils.getRandomNumber(Constant.GENERATE_DELAY_MIN, Constant.GENERATE_DELAY_MAX));
                if (running) {
                    carService.spawnVehicle();
                }
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
