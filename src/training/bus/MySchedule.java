package training.bus;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MySchedule {

	private String line;
	public static BusDbHelper dbHelper = null;
	private String time;
	private String day;
	private String place;
	private Context context;

	/**
	 * @param line
	 *            {@link String} Linea de colectivo(numero solamente).
	 * @param time
	 *            {@link String} Hora que se eligio
	 * @param day
	 *            {@link String} dia que se eligio
	 * @param context
	 *            {@link Context} Contexto de la aplicacion
	 */
	public MySchedule(String line, String time, String day, String place,
			Context context) {
		this.line = line;
		this.time = time;
		this.day = day;
		this.place = place;
		this.context = context;
	}

	public MySchedule(String line, String time, String day, String place) {
		this.line = line;
		this.time = time;
		this.place = place;
		this.day = day;

	}

	/**
	 * Guarda un horario
	 * 
	 * @throws ExceptionExistSchedule
	 *             sii Elemento a guardar ya existe
	 */
	public void save() throws ExceptionExistSchedule {
		dbHelper = new BusDbHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// Create a new map of values, where column names are the keys
		if (!existsRow(line, time, day, db)) {
			ContentValues values = new ContentValues();
			values.put(BusContract.BusEntry.COLUMN_NAME_LINE, this.line);
			values.put(BusContract.BusEntry.COLUMN_NAME_TIME, this.time);
			values.put(BusContract.BusEntry.COLUMN_NAME_DAY, this.day);
			values.put(BusContract.BusEntry.COLUMN_NAME_PLACE, this.place);
			db.insert(BusContract.BusEntry.TABLE_NAME, null, values);
		} else {
			dbHelper.close();
			db.close();
			throw new ExceptionExistSchedule();
		}
		dbHelper.close();
		db.close();
	}

	// true si la fila existe
	private boolean existsRow(String line, String time, String day,
			SQLiteDatabase db) {

		String[] selectionArgs = { line, time, day };
		String selecction = BusContract.BusEntry.COLUMN_NAME_LINE + "=? and "
				+ BusContract.BusEntry.COLUMN_NAME_TIME + "=? and "
				+ BusContract.BusEntry.COLUMN_NAME_DAY + "=?";
		Cursor c = db.query(BusContract.BusEntry.TABLE_NAME, // The table to
				null, // The columns to return
				selecction, // The columns for the WHERE clause
				selectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}

	/**
	 * @return true ssi se borro el horario exitosamente
	 */
	public boolean delete() {
		int i = 0;
		SQLiteDatabase db = null;
		BusDbHelper dbHelper = null;
		dbHelper = new BusDbHelper(context);
		db = dbHelper.getWritableDatabase();
		String selecction = BusContract.BusEntry.COLUMN_NAME_LINE + "=? and "
				+ BusContract.BusEntry.COLUMN_NAME_TIME + "=? and "
				+ BusContract.BusEntry.COLUMN_NAME_DAY + "=?";
		String[] selectionArgs = { this.line, this.time, this.day };
		//
		i = db.delete(BusContract.BusEntry.TABLE_NAME, selecction,
				selectionArgs);
		dbHelper.close();
		db.close();
		return (i > 0);
	}

	/**
	 * @param line
	 * @param time
	 * @param day
	 */
	public void update(String line, String time, String day) {

	}

	/**
	 * @return {@link LinkedList} con todos los horarios. Null si no hay
	 *         ninguno.
	 */
	public static List<MySchedule> getAllMySchedules(Context context) {
		dbHelper = new BusDbHelper(context);
		List<MySchedule> schedules = new LinkedList<MySchedule>();
		SQLiteDatabase db = null;
		db = dbHelper.getWritableDatabase();
		String sortOrder = BusContract.BusEntry.COLUMN_NAME_LINE + ","
				+ BusContract.BusEntry.COLUMN_NAME_TIME + " DESC";
		Cursor c = db.query(BusContract.BusEntry.TABLE_NAME, null, null,
		// The columns for the WERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);
		c.moveToFirst();
		if (c.getCount() == 0) {
			c.close();
			db.close();
			dbHelper.close();
			return null;
		}
		for (int i = 0; i < c.getCount(); i++) {
			String line = c.getString(0);
			String time = c.getString(1);
			String day = c.getString(2);
			String place = c.getString(3);
			schedules.add(new MySchedule(line, time, day,place));
			c.moveToNext();
		}
		c.close();
		db.close();
		dbHelper.close();
		return schedules;
	}

	public String getLine() {
		return line;
	}

	public String getTime() {
		return time;
	}

	public String getDay() {
		return day;
	}
	
	public String getPlace(){
		return place;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
