package training.util;

import java.io.IOException;

import training.unrc.R;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Clase que realiza todas las consultas con la base de datos. Tambien es
 * Tracking de todo el programa. fact= en la base de datos cuando se cargue la
 * una materia se deben cargar todos los campos exepto el linkWeb
 */

public class Request {

	private String ORDER = "DESC";

	private String mCurrentFaculty;// facultad seleccionada.
	private String[] mCurrentFaculties;// facultades

	private String mCurrentCareer;// Carrera seleccionada.
	private String[] mCurrentCareers;// Carreras corriente de una
										// facultad.
	private String mCurrentYear; // Es el año que se selecciono.
	private String[] mCurrentYears; // Los años de la carrera corriente.

	private String mCurrentSubject;
	private String[] mCurrentSubjects; // Materias del año seleccionado,
										// de la carrera corriente.

	/** [codigo nombre ] **/
	private String[] mCurrentSubjectCorrelatives;

	/** [codigo nombre cuatrimestre año link] **/
	private String mCurrentDescription;

	private SQLiteDatabase db;
	private Resources r;
	private Cursor c;

	/**
	 * Crea una instancia de la base de datos, si la app se ejecuta por primera
	 * ves aca se la crea tambien. Luego abre la base de datos.
	 * 
	 * Resources (r) es para trabajar con los nombres de las tablas de la db.
	 */
	public Request(Context context, Resources res) {
		try {
			DbUtil.createDatabaseIfNotExists(context);
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		db = DbUtil.getStaticDb();
		r = res;
	}

	/**
	 * Libera los recursos de la base de datos. Cierra la db.
	 */
	@Override
	public void finalize() {
		db.close();
		c.close();
		r = null;
		mCurrentFaculty = null;
		mCurrentFaculties = null;
		mCurrentCareer = null;
		mCurrentCareers = null;
		mCurrentYear = null;
		mCurrentYears = null;
		mCurrentSubject = null;
		mCurrentSubjects = null;
		mCurrentSubjectCorrelatives = null;
		mCurrentDescription = null;
		try {
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		/** acelero la liberacion de memoria **/
		java.lang.System.gc();
	}

	/**
	 * Get all names of the faculties in UNRC
	 */
	public String[] getAllFaculties() {
		if (mCurrentFaculties == null) {
			String[] columns = { r.getString(R.string.FACULTY_0_ID) };
			c = db.query(r.getString(R.string.FACULTY_TABLE), columns, null,
					null, null, null, r.getString(R.string.FACULTY_0_ID) + " "
							+ ORDER);

			mCurrentFaculties = new String[c.getCount()];
			int i = 0;
			while (c.moveToNext()) {
				mCurrentFaculties[i] = c.getString(0);
				i++;
			}
		}
		c.close();
		return mCurrentFaculties;
	}

	/**
	 * Get name of the selected position. PreCondicion : mCurrentFaculties tiene
	 * que estar cargado.
	 * 
	 * @param position
	 *            mCurrentDescription; {@link int}
	 * @return {@link String}
	 */
	public String getNameOfFaculty(int position) {
		/** usa la lista que se cargo con la consulta getAllFaculties. **/
		mCurrentFaculty = mCurrentFaculties[position];
		return mCurrentFaculty;
	}

	/**
	 * Get all Careers of faculty.
	 * 
	 * @param faculty
	 *            {@link String}
	 * @return {@link String[]}
	 */
	public String[] getAllCareersOfFaculty(String faculty) {
		String[] columns = { r.getString(R.string.CAREER_0_ID) };
		String[] selectArgs = { faculty };
		c = db.query(r.getString(R.string.CARERR_TABLE), columns,
				r.getString(R.string.CAREER_3_NAMEFACULTY) + "=?", selectArgs,
				null, null, r.getString(R.string.CAREER_0_ID) + " " + ORDER);

		mCurrentCareers = new String[c.getCount()];
		int i = 0;
		while (c.moveToNext()) {
			mCurrentCareers[i] = c.getString(0);
			i++;
		}
		c.close();
		return mCurrentCareers;
	}

	/**
	 * Para usar este metodo, es obligacion antes que se use el
	 * setCurrentCareer. Retorna el nombre de la carrera corriente seleccionada
	 * de las lista de carreras.
	 * 
	 * @return {@link String}
	 */
	public String getCurrentCareer() {
		return mCurrentCareer;
	}

	/**
	 * Para usar este metodo la carrera tiene que estar previament cargada.
	 * Retorna todos los años que tiene la carrera.
	 * 
	 * @return {@link String[]}
	 */
	public String[] getYearsOfCareer() {
		String[] selectionArgs = { mCurrentCareer };
		String[] columns = { r.getString(R.string.CAREER_1_YEARS) };
		c = db.query(r.getString(R.string.CARERR_TABLE), columns,
				r.getString(R.string.CAREER_0_ID) + "=?", selectionArgs, null,
				null, null);
		c.moveToNext();
		int year = Integer.parseInt(c.getString(0));
		String[] result = new String[year];
		for (int i = 0; i < year; i++) {
			result[i] = "Año " + (i + 1);
		}
		mCurrentYears = result;
		c.close();
		return mCurrentYears;
	}

	/**
	 * PreC: se tiene que setear la carrera corriente para su uso. Retorna todas
	 * las materias de la carrera corriente en un año determinado por la
	 * seleccion del usuario.
	 * 
	 * @return {@link String[]}
	 */
	public String[] getSubjectOfCareer() {
		String columns = r.getString(R.string.ADDITIONAL_CODE_SUBJECT) + " , "
				+ r.getString(R.string.ADDITIONAL_NAME_SUBJECT);

		String year;
		if (mCurrentYear == null)
			year = "";
		else {
			year = mCurrentYear.substring(mCurrentYear.length() - 1,
					mCurrentYear.length());
		}
		String[] selectionArgs = { mCurrentCareer, year };

		String sql = "SELECT " + columns + " FROM ( SELECT h."
				+ r.getString(R.string.HAVE_1_CODE_SUBJECT) + " FROM "
				+ r.getString(R.string.HAVE_TABLE) + " h  WHERE h."
				+ r.getString(R.string.HAVE_0_NAME_CAREER) + " =?  and h."
				+ r.getString(R.string.HAVE_3_YEAR) + " =? ) AS n JOIN "
				+ r.getString(R.string.SUBJECT_TABLE) + " s ON s."
				+ r.getString(R.string.SUBJECT_0_CODE_ID) + " = n."
				+ r.getString(R.string.ADDITIONAL_CODE_SUBJECT) + " ORDER BY "
				+ r.getString(R.string.ADDITIONAL_CODE_SUBJECT) + " ;";
		c = db.rawQuery(sql, selectionArgs);
		mCurrentSubjects = new String[c.getCount()];
		int i = 0;
		while (c.moveToNext()) {
			/** CODIGO \B NOMBRE **/
			mCurrentSubjects[i] = c.getString(0) + " " + c.getString(1);
			i++;
		}
		c.close();
		return mCurrentSubjects;
	}

	/**
	 * Sete el año corriente de la carrera el cual seleciono le usuario.
	 * 
	 * @param position
	 */
	public void setCurrentYear(int position) {
		mCurrentYear = mCurrentYears[position];
	}

	/**
	 * Carga la carrera que se seleciona de la lista de carreras que se muestra.
	 * 
	 * @param position
	 */
	public void setCurrentCareer(int position) {
		mCurrentCareer = mCurrentCareers[position];
	}

	/**
	 * Carga la materia que se seleeciono de la lista de matieras en Reques
	 * carga toda la informacion.
	 * 
	 * @param position
	 * 
	 */
	public void setCurrentSubject(int position) {
		/** en la lista se encuentra de esta forma [codigo \b nombre ]: materia **/
		mCurrentSubject = mCurrentSubjects[position];

		/** codigo de la materia seleccionada **/
		String code = mCurrentSubject
				.substring(0, mCurrentSubject.indexOf(" "));
		String[] selectionArgs = { mCurrentCareer, code };

		/** consulta de correlatividades de una materia **/
		String sql = "SELECT " + r.getString(R.string.ADDITIONAL_CODE_SUBJECT)
				+ " , " + r.getString(R.string.ADDITIONAL_NAME_SUBJECT)
				+ " FROM (SELECT "
				+ r.getString(R.string.ADDITIONAL_CODE_SUBJECT)
				+ " FROM (SELECT c."
				+ r.getString(R.string.CORRELATIVE_2_SUBJECT_CODE_CORRELATIVE)
				+ " , c."
				+ r.getString(R.string.CORRELATIVE_3_NAME_CAREER_CORRELATIVE)
				+ " FROM " + r.getString(R.string.CORRELATIVE_TABLE)
				+ " c WHERE c."
				+ r.getString(R.string.CORRELATIVE_1_ID_NAME_CAREER)
				+ " =?  and c."
				+ r.getString(R.string.CORRELATIVE_0_ID_SUBJECT)
				+ " =?  ) AS t JOIN " + r.getString(R.string.HAVE_TABLE)
				+ " v ON t."
				+ r.getString(R.string.CORRELATIVE_2_SUBJECT_CODE_CORRELATIVE)
				+ " = v." + r.getString(R.string.HAVE_1_CODE_SUBJECT)
				+ " and t."
				+ r.getString(R.string.CORRELATIVE_3_NAME_CAREER_CORRELATIVE)
				+ " = v." + r.getString(R.string.HAVE_0_NAME_CAREER)
				+ " ) AS n JOIN " + r.getString(R.string.SUBJECT_TABLE)
				+ " s ON s." + r.getString(R.string.SUBJECT_0_CODE_ID)
				+ " = n." + r.getString(R.string.ADDITIONAL_CODE_SUBJECT)
				+ " ORDER BY " + r.getString(R.string.ADDITIONAL_CODE_SUBJECT)
				+ " ;";

		c = db.rawQuery(sql, selectionArgs);
		if (c.getCount() > 0) {
			mCurrentSubjectCorrelatives = new String[c.getCount()];
			int i = 0;
			while (c.moveToNext()) {
				/** codigo nombre : materias correlativas **/
				mCurrentSubjectCorrelatives[i] = c.getString(0) + " "
						+ c.getString(1);
				i++;
			}
		} else {
			/** no posse correlativas **/
			mCurrentSubjectCorrelatives = null;

		}
		c.close();
		/** descripcion de materia correinte **/
		String description = code
				+ " ,"
				+ mCurrentSubject.substring(mCurrentSubject.indexOf(" "),
						mCurrentSubject.length());
		String[] sArgs = { code, mCurrentCareer };
		String[] columns = { r.getString(R.string.HAVE_2_DESCRIPTION),
				r.getString(R.string.HAVE_3_YEAR) };
		String where = r.getString(R.string.HAVE_1_CODE_SUBJECT) + "=? and "
				+ r.getString(R.string.HAVE_0_NAME_CAREER) + "=? ";
		c = db.query(r.getString(R.string.HAVE_TABLE), columns, where, sArgs,
				null, null, null);
		c.moveToFirst();
		description += " ," + c.getString(0) + " ," + c.getString(1);

		String[] cols = { r.getString(R.string.CAREER_2_LINK) };
		String[] selcs = { mCurrentCareer };
		c.close();
		c = db.query(r.getString(R.string.CARERR_TABLE), cols,
				r.getString(R.string.CAREER_0_ID) + " =? ", selcs, null, null,
				null);
		c.moveToFirst();
		description += " ," + c.getString(0);

		/** descripcion biene dada de esta manera **/
		/** [codigo ,nombre ,cuatrimestre,año ,link] **/
		mCurrentDescription = new String();
		mCurrentDescription = description;
		c.close();
	}

	/**
	 * Retorna el nombre de la materia seleccionada.
	 * 
	 * @return {@link String}
	 */
	public String getCurrentSubjectName() {
		int start = mCurrentSubject.indexOf(" ");
		return mCurrentSubject.substring(start, mCurrentSubject.length());
	}

	/**
	 * Retorna la lista de materias correlativas a la materia corriente.
	 * 
	 * @return {@link String[]}
	 */
	public String[] getCurrentSubjectsCorrelatives() {
		return mCurrentSubjectCorrelatives;
	}

	/**
	 * Retorna un String con la descripcion de la materia corriente
	 * 
	 * @return {@link String}
	 */
	public String getDescriptionCurrentSubject() {
		return mCurrentDescription;
	}

	/**
	 * Retorna la descipcion de la materia pasada por partametro
	 * 
	 * @param subjectName
	 *            {@link String} Codigo de Materia a buscar descripcion.
	 * @return {@link String} [codigo ,nombre ,año ,cuatrimestre ,condicion]
	 * 
	 */
	public String getDescriptionCorrelativeSelected(String subjectCode) {
		subjectCode.trim();
		String subjectC = subjectCode.substring(0, subjectCode.indexOf(" "));
		String name = subjectCode.substring(subjectCode.indexOf(" "),
				subjectCode.length());
		subjectC.trim();
		name.trim();

		/** descripcion de materia correinte **/
		String description = subjectC + " ," + name;
		String[] sArgs = { subjectC, mCurrentCareer };
		String[] columns = { r.getString(R.string.HAVE_2_DESCRIPTION),
				r.getString(R.string.HAVE_3_YEAR) };
		String where = r.getString(R.string.HAVE_1_CODE_SUBJECT) + "=? and "
				+ r.getString(R.string.HAVE_0_NAME_CAREER) + "=? ";
		c = db.query(r.getString(R.string.HAVE_TABLE), columns, where, sArgs,
				null, null, null);
		c.moveToFirst();
		description += " ," + c.getString(0) + " ," + c.getString(1);

		String subjectCodeOld = mCurrentSubject.substring(0,
				mCurrentSubject.indexOf(" "));
		String[] cols = { r.getString(R.string.CORRELATIVE_4_CONDICTION) };
		String where2 = r.getString(R.string.CORRELATIVE_0_ID_SUBJECT)
				+ "=? and "
				+ r.getString(R.string.CORRELATIVE_1_ID_NAME_CAREER)
				+ "=?  and "
				+ r.getString(R.string.CORRELATIVE_2_SUBJECT_CODE_CORRELATIVE)
				+ " =? and "
				+ r.getString(R.string.CORRELATIVE_3_NAME_CAREER_CORRELATIVE)
				+ " =? ";
		String[] sArgs2 = { subjectCodeOld, mCurrentCareer, subjectC,
				mCurrentCareer };
		c.close();
		c = db.query(r.getString(R.string.CORRELATIVE_TABLE), cols, where2,
				sArgs2, null, null, null);
		c.moveToFirst();
		description += " ," + c.getString(0);
		c.close();
		return description;
	}

	/**
	 * @return {@link String} el anio corriente seleccionado. la forma de
	 *         retorno es { anio (en el idioma que este) numero}
	 * 
	 */
	public String getYearSelected() {
		return mCurrentYear;
	}

	/**
	 * @return {@link Integer} es la posicion de la carrera corriente en la
	 *         lista de carreras.
	 */
	public int getPositionOnCareers() {
		boolean find = false;
		int res = 0;
		for (int i = 0; i < mCurrentCareers.length && !find; i++) {
			find = mCurrentCareers[i].compareTo(mCurrentCareer) == 0;
			if (find)
				res = i;
		}
		return res;
	}

	/**
	 * @return {@link Integer} es la posicion de la Facultad corriente en la
	 *         lista de Facultades.
	 */
	public int getPositionOnFaculties() {
		boolean find = false;
		int res = 0;
		for (int i = 0; i < mCurrentFaculties.length && !find; i++) {
			find = mCurrentFaculties[i].compareTo(mCurrentFaculty) == 0;
			if (find)
				res = i;
		}
		return res;
	}

}
