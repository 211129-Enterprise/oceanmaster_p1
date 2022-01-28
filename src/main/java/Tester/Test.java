package Tester;

import config.Persistor;
import config.PropertyFile;

public class Test {

	public static void main(String[] args) {

		PropertyFile.setFilePath(
				"C:\\Users\\wledg\\Desktop\\git\\Project 1\\ocean_master_p1\\src\\main\\resources\\application.properties");

		Persistor.start();

		CarMod car = new CarMod();

		car.setModel("Honda");

		CarService carserv = new CarService();

		carserv.addCar(car);
	}

}
