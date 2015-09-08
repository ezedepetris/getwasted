package training.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import training.map.MapActivity;
import training.unrc.R;
import android.R.bool;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PointF;
import android.os.Environment;

public class MapUtil {

	static int currentHeight = 0;
	static int currentWidth = 0;
	static Config currentType = null;
	static int currentRowBytes = 0;
	static final String FILE_NAME = "tempp.tmp";
	private File file;
	private PointsMap pointsMap = new PointsMap();

	/**
	 * Converts a immutable bitmap to a mutable bitmap. This operation doesn't
	 * allocates more memory that there is already allocated.
	 * 
	 * @param imgIn
	 *            - Source image. It will be released, and should not be used
	 *            more
	 * @return a copy of imgIn, but muttable.
	 */
	public Bitmap convertToMutable(Bitmap imgIn) {
		try {
			// this is the file going to use temporally to save the bytes.
			// This file will not be a image, it will store the raw image data.
			File fileAux = new File(Environment.getExternalStorageDirectory(),
					FILE_NAME);
			if (fileAux.exists()) {
				fileAux.delete();
			}

			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + FILE_NAME);
			// Open an RandomAccessFile
			// Make sure you have added uses-permission
			// android:name="android.permission.WRITE_EXTERNAL_STORAGE"
			// into AndroidManifest.xml file
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

			// get the width and height of the source bitmap.
			int width = imgIn.getWidth();
			currentWidth = width;
			int height = imgIn.getHeight();
			currentHeight = height;
			Config type = imgIn.getConfig();
			currentType = type;
			currentRowBytes = imgIn.getRowBytes();
			// Copy the byte to the file
			// Assume source bitmap loaded using options.inPreferredConfig =
			// Config.ARGB_8888;
			FileChannel channel = randomAccessFile.getChannel();
			MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0,
					imgIn.getRowBytes() * height);
			imgIn.copyPixelsToBuffer(map);
			// recycle the source bitmap, this will be no longer used.
			imgIn.recycle();
			imgIn = null;
			System.gc();// try to force the bytes from the imgIn to be
						// released

			// Create a new bitmap to load the bitmap again. Probably the
			// memory
			// will be available.
			imgIn = Bitmap.createBitmap(width, height, type);
			map.position(0);
			// load it back from temporary
			imgIn.copyPixelsFromBuffer(map);
			// close the temporary file and channel , then delete that also
			channel.close();
			randomAccessFile.close();
			// delete the temp file
			// file.delete();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgIn;
	}

	/**
	 * Lee de memoria el archivo temp.tmp
	 * 
	 * @param imgIn
	 *            {@link Bitmap}
	 * @return {@link Bitmap} el que guardo en memoria convertToMutable
	 */
	public Bitmap readMutableBitmap(Bitmap imgIn) {
		try {
			// this is the file going to use temporally to save the bytes.
			// This file will not be a image, it will store the raw image data.

			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + FILE_NAME);

			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

			int width = currentWidth;
			int height = currentHeight;
			Config type = currentType;

			// Copy the byte to the file
			// Assume source bitmap loaded using options.inPreferredConfig =
			// Config.ARGB_8888;
			FileChannel channel = randomAccessFile.getChannel();
			MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0,
					currentRowBytes * height);

			// Create a new bitmap to load the bitmap again. Probably the
			// memory
			// will be available.
			imgIn = Bitmap.createBitmap(width, height, type);
			map.position(0);
			// load it back from temporary
			imgIn.copyPixelsFromBuffer(map);
			// close the temporary file and channel , then delete that also
			channel.close();
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imgIn;

	}

	/**
	 * @param place
	 *            {@link Integer} Representa el aula a buscar o otro lugar.
	 * @param selector
	 *            {@link Integer} Representa a los pabellones, 1 o 2 o 3.
	 * @return {@link PointF} cordenandas en la imagen a marcar.
	 */
	public PointF getClassRoom(int place, int selector) {
		// place hace referencia a la posicion en el arreglo. Coincide con el
		// que
		// esta en el archivo XML para sacar los px py.
		return pointsMap.getPoint(place, selector); 
	}

	/**
	 * @param pavilion
	 *            {@link Integer} pabellon selecionado
	 * @param place
	 *            {@link Integer} aula del pabellon
	 * @return {@link bool} <code>true</code> si place pertenece a la planta
	 *         alta del pavilion
	 */
	public boolean belongUpStairs(int pavilion, String place, Resources res) {
		String[] places = null;
		switch (pavilion) {
		case MapActivity.PAVILION_1:
			places = (res
					.getStringArray(R.array.classroom_pavilion_1_up_stairs));
			break;
		case MapActivity.PAVILION_2:
			places = (res
					.getStringArray(R.array.classroom_pavilion_2_up_stairs));
			break;
		case MapActivity.PAVILION_3:
			places = (res
					.getStringArray(R.array.classroom_pavilion_3_up_stairs));
			break;
		case MapActivity.MORE_PLACES:
			places = (res
					.getStringArray(R.array.more_places_general));
			break;
		default:
			return false;
		}
		boolean below = false;
		for (int i = 0; i < places.length; i++) {
			if (places[i].compareTo(place) == 0)
				below = true;
		}
		return below;
	}

	/**
	 * Borra el archivo que genera convertToMutable en la SD con el nombre de
	 * temp.tmp
	 */
	public void deleteFile() {
		try {

			// this is the file going to use temporally to save the bytes.
			// This file will not be a image, it will store the raw image data.
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + FILE_NAME);

			// delete the temp file
			file.delete();

		} catch (Exception e) {
		}

	}
}
