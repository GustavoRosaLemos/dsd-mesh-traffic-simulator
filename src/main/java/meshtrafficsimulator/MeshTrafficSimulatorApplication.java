package meshtrafficsimulator;


import meshtrafficsimulator.models.Car;
import meshtrafficsimulator.models.Directions;
import meshtrafficsimulator.service.CarService;
import meshtrafficsimulator.view.Home;

import javax.swing.*;
import java.io.IOException;

public class MeshTrafficSimulatorApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SwingUtilities.invokeLater(() -> {
			try {
				Home home = new Home();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

}
