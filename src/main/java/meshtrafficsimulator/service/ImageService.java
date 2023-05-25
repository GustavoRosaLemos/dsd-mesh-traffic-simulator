package meshtrafficsimulator.service;

import meshtrafficsimulator.models.Directions;

import javax.swing.*;
import java.awt.*;

public class ImageService {
    private String path;
    private String fileName;
    private ImageIcon image;

    public ImageService() {

    }

    public String getPath() {
        return path;
    }

    public Image getImage(String fileName) {
        this.fileName = fileName;
        this.path = "src/main/java/meshtrafficsimulator/images/"+fileName;
        this.image = new ImageIcon(path);
        return image.getImage();
    }

    public String convertIntToFileName(String roadType, boolean hasCar, Directions direction) {
        switch (roadType) {
            case "1": {
                if (hasCar) {
                    return "top-car.png";
                }
                return "top-road.png";
            }
            case "2": {
                if (hasCar) {
                    return "right-car.png";
                }
                return "right-road.png";
            }
            case "3": {
                if (hasCar){
                    return "bottom-car.png";
                }
                return "bottom-road.png";
            }
            case "4": {
                if (hasCar) {
                    return "left-car.png";
                }
                return "left-road.png";
            }
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "11":
            case "12": {
                if (hasCar && direction != null) {
                    if (direction.equals(Directions.RIGHT)) {
                        return "right-car-crossing.png";
                    } else if (direction.equals(Directions.TOP)) {
                        return "top-car-crossing.png";
                    } else if (direction.equals(Directions.BOTTOM)) {
                        return "bottom-car-crossing.png";
                    } else if (direction.equals(Directions.LEFT)) {
                        return "left-car-crossing.png";
                    }
                }
                return "crossing.png";
            }
            default: {
                return "empty.png";
            }
        }
    }
}
