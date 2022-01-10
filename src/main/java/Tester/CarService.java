package Tester;

public class CarService {
	static CarDao card = new CarDao();
	
	
	public void addCar(CarMod car) {
		
		card.insert(car.getClass());
	}
	

}
