package crud;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.reflections.Reflections;

import annotations.Column;
import annotations.Table;
import config.ConnectionConfig;
import config.PropertyFile;

public class CrudRepo extends Filter {

	static Reflections ref = new Reflections(PropertyFile.getPropertiesFile().getProperty("modelpath"));
	static Connection conn = ConnectionConfig.getConnection();

	// Create
	public void insert(Class<? extends Object> o) {

		String sql = "INSERT INTO " + o.getClass().getAnnotation(Table.class).table_name() + "(";

		for (Field field : o.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				sql += field.getAnnotation(Column.class).name() + ", ";
			}
			sql = sql.substring(0, sql.length() - 2);
			sql += ") VALUES( ";

			for (Field fld : o.getClass().getDeclaredFields()) {
				fld.setAccessible(true);
				try {
					if (field.getAnnotation(Column.class).sqlType().toLowerCase().startsWith("varchar")) {
						sql += "'" + fld.get(o).toString() + "', ";
					}
					sql += fld.get(o).toString() + ", ";

				} catch (IllegalArgumentException e) {

				} catch (IllegalAccessException e) {

				}
			}
			sql = sql.substring(0, sql.length() - 2) + ")";
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			} catch (SQLException ex) {

			}

		}

	}

	// Read
	@SuppressWarnings({ "unchecked", "unused" })
	public ArrayList<Optional<?>> getAll(Class<? extends Object> o) {

		ArrayList<Optional<?>> arr = new ArrayList<Optional<?>>();

		String sql = "SELECT * FROM " + o.getClass().getAnnotation(Table.class).table_name();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				for (int i = 0; i <= o.getDeclaredFields().length; i++) {
					Field field = o.getDeclaredFields()[i];
					Object obj = o.newInstance();
					for (Method method : ((Class<? extends Object>) obj).getMethods()) {
						if ((method.getName().startsWith("get"))
								&& (method.getName().length() == (field.getName().length() + 3))) {
							if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
								try {
									method.invoke(obj, rs.getObject(i));
								} catch (IllegalAccessException e) {

								} catch (InvocationTargetException e) {

								}
							}

						}
						arr.add((Optional<?>) obj);
					}

					return arr;
				}
			}
		} catch (SQLException ex) {

		} catch (InstantiationException e) {

		} catch (IllegalAccessException e) {

		}

		return null;

	}

	// Update
	public void update(Class<? extends Object> o) {
		String sql = "UPDATE " + o.getAnnotation(Table.class).table_name() + " SET ";
		for (Field field : o.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Column.class)) {
				sql += field.getAnnotation(Column.class).name() + "= ?, ";
			}
		}
		sql = sql.substring(0, sql.length() - 2) + "WHERE "
				+ o.getDeclaredFields()[0].getAnnotation(Column.class).name() + " = ";
		for (Method method : o.getMethods()) {
			if ((method.getName().startsWith("get"))
					&& (method.getName().length() == (o.getDeclaredFields()[0].getName().length() + 3))) {
				if (method.getName().toLowerCase().endsWith(o.getDeclaredFields()[0].getName().toLowerCase())) {
					try {
						sql += method.invoke(o);

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

					}
				}
			}

			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// Delete
	public void delete(Class<? extends Object> o) {
		String sql = "DELETE FROM " + o.getAnnotation(Table.class).table_name() + " WHERE "
				+ o.getDeclaredFields()[0].getAnnotation(Column.class).name() + "= ";
		for (Method method : o.getMethods()) {
			if ((method.getName().startsWith("get"))
					&& (method.getName().length() == (o.getDeclaredFields()[0].getName().length() + 3))) {
				if (method.getName().toLowerCase().endsWith(o.getDeclaredFields()[0].getName().toLowerCase())) {
					try {
						sql += method.invoke(o);

					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

					}
				}
			}
			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			} catch (SQLException e) {
				
			}
		}
		
	}
}
