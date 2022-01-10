package Tester;

import annotations.Column; 
import annotations.Table;
import config.Persistor;

@Table(table_name = "car_mod")
public class CarMod extends Persistor{
	
	@Column(constraints = "PRIMARY KEY", name = "id", sqlType = "SERIAL")
	private int id;
	@Column(constraints = "NOT NUll", name = "model", sqlType = "VARCHAR(50)")
	private String Model;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	


}
