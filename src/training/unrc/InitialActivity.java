package training.unrc;
	
import training.bus.BusActivity;
import training.careers.CareersActivity;
import training.map.MapActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

public class InitialActivity extends Activity {

	private static final String ROTATE = "rotate";
	private WelcomWorkerTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			setContentView(R.layout.welcome);
			task = new WelcomWorkerTask(this);
			task.execute();
		} else
			setContentView(R.layout.initial);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(ROTATE, true);
		super.onSaveInstanceState(outState);
	}

	public void pressedBus(View view) {
		Intent intent = new Intent(InitialActivity.this, BusActivity.class);
		startActivity(intent);
	}

	public void pressedMap(View view) {
		if (isExternalStorageWritable()) {
			Intent intent = new Intent(InitialActivity.this, MapActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(this,
					getResources().getString(R.string.not_found_sd),
					Toast.LENGTH_LONG).show();
		}

	}

	public void pressedCareers(View view) {
		Intent intent = new Intent(InitialActivity.this, CareersActivity.class);
		startActivity(intent);
	}

	public void pressedService(View view) {
		Toast.makeText(this,
				getResources().getString(R.string.initial_activiy_comming),
				Toast.LENGTH_LONG).show();
	}

	public void facebook(View view) {
		if (isWifiOn()) {
			Intent intent = new Intent(InitialActivity.this,
					FacebookActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(
					getApplicationContext(),
					getResources().getString(
							R.string.facebook_activity_not_wifi),
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		System.gc();
	}

	/* Checks if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	private boolean isWifiOn() {
		// WifiManager wifi = (WifiManager)
		// getSystemService(Context.WIFI_SERVICE);
		// return wifi.isWifiEnabled();
		ConnectivityManager cm =
		        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
	}

}
