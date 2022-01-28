package Tester;

import annotations.Column;
import annotations.Table;

@Table(table_name = "airplane")
public class Airplane {
	
	@Column(constraints = "PRIMARY KEY", name = "id", sqlType = "SERIAL")
	private int id;
	
	@Column(constraints = "NOT NUll", name = "model", sqlType = "VARCHAR(50)")
	private String model;

}
