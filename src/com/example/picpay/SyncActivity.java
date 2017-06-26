package com.example.picpay;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SyncActivity extends Activity {
	private DBController DB;

	private Intent intentMainActivity;
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync);
		DB = new DBController(this);
		intentMainActivity			= new Intent(this, MainActivity.class);  
		
		download();

	}

	public void download() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Sincronizando");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		
		final Download downloadTask = new Download(DB,this, new Download.AsynResponse() {
	        @Override
	        public void processFinish(Boolean output) {
	        	startActivity(intentMainActivity);
	        	mProgressDialog.dismiss();
        		finish();
        		Toast.makeText(getApplicationContext(), "Sincronização realizada com sucesso!", Toast.LENGTH_SHORT).show();
	        	
	        }
		});
		downloadTask.execute();

		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
		    @Override
		    public void onCancel(DialogInterface dialog) {
		        downloadTask.cancel(true);
		    }
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.sync, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
