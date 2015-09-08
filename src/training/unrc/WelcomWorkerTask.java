package training.unrc;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class WelcomWorkerTask extends AsyncTask<Integer, Integer, Integer> {

	private int i = 10;
	private Context context;
	private TextView welcome;
	private String[] text = { "b", "i", "e", "n", "v", "e", "n", "i", "d", "o",
			"s" };

	public WelcomWorkerTask(Context context) {
		this.context = context;
		welcome = (TextView) ((InitialActivity) context)
				.findViewById(R.id.welcome_text);
	}

	@Override
	protected Integer doInBackground(Integer... params) {
		for (i = 0; i < 10; i++) {
			publishProgress(i);
			try {
				Thread.sleep(210);
			} catch (InterruptedException e) {
				;
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if (welcome.getText().length() > 10) {
			welcome.setText("");
		} else {
			welcome.setText(welcome.getText() + text[i]);
		}
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Integer result) {
		((InitialActivity) context).setContentView(R.layout.initial);
		super.onPostExecute(result);
	}

}
