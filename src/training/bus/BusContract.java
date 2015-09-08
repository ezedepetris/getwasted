 package training.bus;


public class BusContract {

	public static abstract class BusEntry {
		public static final String TABLE_NAME = "MySchedules";
		public static final String COLUMN_NAME_ID = "id";
		public static final String COLUMN_NAME_LINE = "line";
		public static final String COLUMN_NAME_DAY = "day";
		public static final String COLUMN_NAME_TIME = "time";
		public static final String COLUMN_NAME_PLACE = "place";
	}

	public  static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ BusContract.BusEntry.TABLE_NAME + " ("
			+ BusContract.BusEntry.COLUMN_NAME_LINE + " VARCHAR(50),"
			+ BusContract.BusEntry.COLUMN_NAME_TIME + " VARCHAR(50),"
			+ BusContract.BusEntry.COLUMN_NAME_DAY + " VARCHAR(50),"
			+ BusContract.BusEntry.COLUMN_NAME_PLACE + " VARCHAR(12),"
			+ "PRIMARY KEY (" + BusContract.BusEntry.COLUMN_NAME_LINE + ","
			+ BusContract.BusEntry.COLUMN_NAME_TIME + ","
			+ BusContract.BusEntry.COLUMN_NAME_DAY + ") )";

	public  static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ "MySchedules";

}
