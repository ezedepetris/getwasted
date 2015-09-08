package training.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbUtil {

	// direcciones y nombres de db
	private static String DB_PATH = "";
	private final static String DB_NAME = "unrc";

	// version de la db
	@SuppressWarnings("unused")
	private final static int DB_VERSION = 1;

	public static void createDatabaseIfNotExists(Context context)
			throws IOException {
		boolean createDb = false;
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		File dbDir = new File(DB_PATH);
		File dbFile = new File(DB_PATH + DB_NAME);

		if (!dbDir.exists()) {
			dbDir.mkdir();
			createDb = true;
		} else if (!dbFile.exists()) {
			createDb = true;
		} else {
			// Check that we have the latest version of the db
			boolean doUpgrade = false;
			if (doUpgrade) {
				dbFile.delete();
				createDb = true;
			}
		}

		if (createDb) {
			// Abrimos el fichero de base de datos como entrada
			InputStream myInput = context.getAssets().open(DB_NAME);
			// Ruta a la base de datos vacía recién creada
			String outFileName = DB_PATH + DB_NAME;
			// Abrimos la base de datos vacía como salida
			OutputStream myOutput = new FileOutputStream(outFileName);
			// Transferimos los bytes desde el fichero de entrada al de salida
			// Tener en cuenta cuanto pesa la base de datos.
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();
		}
	}

	public static SQLiteDatabase getStaticDb() {
		return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
				SQLiteDatabase.OPEN_READONLY);
	}
}