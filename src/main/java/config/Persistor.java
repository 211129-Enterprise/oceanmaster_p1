package config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.reflections.Reflections;

import annotations.Column;
import annotations.Table;

public class Persistor {
	@SuppressWarnings("rawtypes")
	public static ArrayList<Class> persist = new ArrayList<Class>();
	static Connection  sesh = ConnectionConfig.getConnection();
	static Session ses = new Session();
	static Properties prop = new Properties(); 
	
	
	static ArrayList<String> tableList= ses.getTablesmethod();

	@SuppressWarnings("rawtypes")
	public ArrayList<Class> persist(Class cls) {

		persist.add(cls);

		return persist;
	}
@SuppressWarnings({ "unchecked", "rawtypes" })
public static Boolean start() {
	boolean made = false;
	try {
		prop.load(new FileReader(PropertyFile.getFilePath()));
	} catch (FileNotFoundException e) {
		System.out.println("file not found doublecheck your application.properties path set in Properties.setFilePath()");
	} catch (IOException e) {
		System.out.println("file not found doublecheck your application.properties path set in Properties.setFilePath()");
		e.printStackTrace();
	}
	
	Reflections reflect = new Reflections(prop.getProperty("modelpath"));
	for (Class cls : reflect.getTypesAnnotatedWith(Table.class)) {
		persist.add(cls);
	}
	try {
	
		for(Class cs:persist) {
		
			if(tableList.contains(((Table) cs.getDeclaredAnnotation(Table.class)).table_name())) {
				continue;
			}else {
			String sql = "CREATE TABLE test.";
			
			sql += ( ((Table) cs.getDeclaredAnnotation(Table.class)).table_name()+ "(");
			for(Field field : cs.getDeclaredFields()) {
				field.setAccessible(true);
			sql+=	field.getAnnotation(Column.class).name()+ " "+ field.getAnnotation(Column.class).sqlType()+ " "
					+ field.getAnnotation(Column.class).constraints() + ", ";
				
			}
			
			sql = sql.substring(0, sql.length()-2);
			
			sql+=" );";
			Statement ps = sesh.createStatement();
			ps.execute(sql);
			}
		}
	}catch(SQLException sq) {
		
	}
	
	return made;
}
}
