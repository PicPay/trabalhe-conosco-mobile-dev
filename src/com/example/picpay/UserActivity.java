package com.example.picpay;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity implements OnClickListener{
	private ActionBar actionBar;
	private Bundle getParams;
	private DBController DB;
	private Cursor mCursor;
	private SimpleCursorAdapter adapter;
	private ListView listViewTrans;
	private String ID;
	private ImageView im;
	private Button bt;
	private Button btnTrans;
	private ProgressDialog mProgressDialog;
	private int CVV;
	private String NUM;
	private String VEN;
	private int ID_CART;
	private EditText cNUM;
	private EditText cVEN;
	private EditText cCVV;
	private TextView idCl;
	private TextView nameCl;
	private TextView idCart;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);

		Init();
		Populate();
	}
	
	private void upload(JSONObject jsonParam) {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Sincronizando");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		
		final Upload uploadTask = new Upload(DB,this, new Upload.AsynResponse() {
	        @Override
	        public void processFinish(Boolean output) {
	        	mProgressDialog.dismiss();
	        }

			@Override
			public void processReturn(String output)  {
				try {
					
					JSONArray jsonArray = new JSONArray("["+output+"]");
					JSONObject jsonObj = new JSONObject(jsonArray.getString(0));
					JSONObject obj = jsonObj.getJSONObject("transaction");

					ContentValues cv = new  ContentValues();
					cv.put("CL", ID);
					cv.put("TRANS_ID", obj.getInt("id"));
				    cv.put("TSTAMP", obj.getString("timestamp"));
				    cv.put("VAL", obj.getDouble("value"));
				    cv.put("STATUS", obj.getString("status"));
				    DB.onInsert("MOB_TRANS", cv);
					    
				    Populate();
					
				} catch (JSONException e) {
					e.printStackTrace();
					mProgressDialog.dismiss();
					Toast.makeText(getApplicationContext(),output, Toast.LENGTH_SHORT).show();
				}
			}
		},jsonParam);
		uploadTask.execute();

		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
		    @Override
		    public void onCancel(DialogInterface dialog) {
		    	uploadTask.cancel(true);
		    }
		});
	}
	
	private void Init(){
		DB = new DBController(this);
		actionBar = getActionBar();
	    
		getParams = getIntent().getExtras();
		ID 	 = (String)getParams.getSerializable("ID");

		listViewTrans = (ListView) findViewById(R.id.listTransaction);
		im = (ImageView) findViewById(R.id.image);
		
		bt = (Button) findViewById(R.id.botao);
		btnTrans = (Button) findViewById(R.id.trans);
		cNUM = (EditText) findViewById(R.id.cNUM);
    	cVEN = (EditText) findViewById(R.id.cVEN);
    	cCVV = (EditText) findViewById(R.id.cCVV);
    	idCl = (TextView) findViewById(R.id.id);
    	nameCl = (TextView) findViewById(R.id.name);
    	idCart = (TextView) findViewById(R.id.id_cart);
		
		bt.setOnClickListener(this);
		btnTrans.setOnClickListener(this);
		
	}

	private void Populate() {
		
		mCursor = DB.onSelect("select a.NAME, a.USERNAME, a.IMG, b.CL, b.NUM, b.CVV, b.VEN, b.VAL, b.ID as _id from MOB_CADA a LEFT JOIN MOB_CART b on a.ID=b.CL where a.ID = "+ID);
		if(mCursor!=null){
	        if(mCursor.getCount()>0){
	        	
	        	mCursor.moveToFirst();
	        	byte asd[]= mCursor.getBlob(mCursor.getColumnIndex("IMG"));
	        	BlobConverter bc = new BlobConverter(BitmapFactory.decodeByteArray(asd, 0, asd.length),10);
	        	im.setImageBitmap(bc.getBitmap());
	        	actionBar.setTitle(ID+" - "+mCursor.getString(mCursor.getColumnIndex("NAME")));
	        	
	        	idCl.setText("ID: "+ID);
	        	nameCl.setText("Nome: "+mCursor.getString(mCursor.getColumnIndex("NAME")));
	        	
	        	setCVV(mCursor.getInt(mCursor.getColumnIndex("CVV")));
	        	setNUM(mCursor.getString(mCursor.getColumnIndex("NUM")));
	        	setVEN(mCursor.getString(mCursor.getColumnIndex("VEN")));
	        	
	        	
	        	int id_cart = mCursor.getInt(mCursor.getColumnIndex("_id"));
	        	setID_CART(id_cart);
	        	if(id_cart>0){
	        		idCart.setText("Cartão ID: "+getID_CART());
	        		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	        		btnTrans.setVisibility(View.VISIBLE);
	        		cNUM.setText(getNUM());
        	 		cVEN.setText(getVEN());
        	 		cCVV.setText(String.valueOf(getCVV()));

	        	}else{
	        		btnTrans.setVisibility(View.INVISIBLE);
	        	}
	        }
	    }
		
		mCursor = DB.onSelect("select a.TRANS_ID, a.TSTAMP, a.VAL, a.STATUS, a.ID as _id from MOB_TRANS a where a.CL = "+ID+" ORDER BY a.ID desc");
		if(mCursor!=null){
	        if(mCursor.getCount()>0){
	        	adapter = new SimpleCursorAdapter(this,R.layout.item_list_trans,mCursor,new String[] {"TRANS_ID","TSTAMP","VAL","STATUS"},new int[] {R.id.tID,R.id.tSTAMP,R.id.tVAL,R.id.tSTD},0);
	        	adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
        		    @Override
        		    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        		        boolean binded = false;
        		        	   if (view instanceof TextView){
        		        		 TextView tVAL = (TextView)view.findViewById(R.id.tVAL);
        		        		 if(tVAL!=null){
         		        			float valor = cursor.getFloat(cursor.getColumnIndex("VAL"));
         		        			tVAL.setText(String.format("%.2f",valor));
         		        			binded = true;
        		        		 }
        		        		 
        		        		 TextView tSTD = (TextView)view.findViewById(R.id.tSTD);
        		        		 if(tSTD!=null){
         		        			String status = cursor.getString(cursor.getColumnIndex("STATUS"));
         		        			tSTD.setText(status);
         		        			if(status.contains("Aprovada"))
         		        				tSTD.setTextColor(Color.GREEN);
         		        			else 
         		        				tSTD.setTextColor(Color.RED);
         		        			binded = true;
        		        		 }
        		        	 }
        		       
        		        return binded;
        		    }
        		});
        		listViewTrans.setAdapter(adapter);
	        }
		}
	}
	
	
	public void onBackPressed()  {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}


	@Override
	public void onClick(View v) {
		//Toast.makeText(getApplicationContext(),"teste", Toast.LENGTH_SHORT).show();
		switch(v.getId()) {
	        case R.id.botao:
		        {
		        	if (cNUM.getText().toString().matches("^([0-9]{16})$")){
		        		if (cVEN.getText().toString().matches("^(0[1-9]|1[0-2])/([0-9]{2})$")){
		        			if (cCVV.getText().toString().matches("^([0-9]{3})$")){
		        				ContentValues cv = new ContentValues();
		    		        	cv.put("ID", getID_CART());
		    		        	cv.put("NUM", cNUM.getText().toString());
		    		        	cv.put("VEN", cVEN.getText().toString());
		    		        	cv.put("CVV", cCVV.getText().toString());
		    		        	cv.put("CL", ID);
		    		        	
		    		        	if(DB.onUpdateOrInsert("MOB_CART", cv)<0)
		    		        		Toast.makeText(getApplicationContext(),"Erro ao atualizar ou interir dados", Toast.LENGTH_SHORT).show();
		    		        	else
		    		        		Toast.makeText(getApplicationContext(),"Dados Atualizados", Toast.LENGTH_SHORT).show();
		    		        	Populate();
			                }
			                else{
			                	Toast.makeText(getApplicationContext(),"CVV do Cartão Inválido", Toast.LENGTH_SHORT).show();
			                	if(cCVV.requestFocus()) {
			                	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			                	    //Populate();
			                	}
			                }
		                }
		                else{
		                	Toast.makeText(getApplicationContext(),"Validade do Cartão Inválida", Toast.LENGTH_SHORT).show();
		                	if(cVEN.requestFocus()) {
		                	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		                	    //Populate();
		                	}
		                }
	                }
	                else{
	                	Toast.makeText(getApplicationContext(),"Número do Cartão Inválido", Toast.LENGTH_SHORT).show();
	                	if(cNUM.requestFocus()) {
	                	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	                	    //Populate();
	                	}
	                }
		        }
	          break;
	        case R.id.trans:
	        	{
	        		View promptsView = LayoutInflater.from(UserActivity.this).inflate(R.layout.dialog_input_num, null);
	    	 		final EditText input = (EditText) promptsView.findViewById(R.id.input);
	    	 		
	    	 		
	    	 		AlertDialog.Builder alertDialogBuilderExc = new AlertDialog.Builder(UserActivity.this,AlertDialog.THEME_HOLO_DARK);
	    	     	alertDialogBuilderExc.setView(promptsView);
	    	 		
	    	 
	    	     	alertDialogBuilderExc
	    	 			.setCancelable(false)
	    	 			.setMessage("Valor da Transação")
	    	 			.setPositiveButton("Confirmar",
	    	 			  new DialogInterface.OnClickListener() {
	    	 			    public void onClick(DialogInterface dialog,int id) {
	    	 	
	    	 			    	JSONObject jsonParam = new JSONObject();
	    	 		            try {
	    	 						jsonParam.put("card_number", getNUM());
	    	 						jsonParam.put("cvv", getCVV());
	    	 			            jsonParam.put("value", input.getText());
	    	 			            jsonParam.put("expiry_date", getVEN());
	    	 			            jsonParam.put("destination_user_id", ID);
	    	 			            
	    	 			            upload(jsonParam);
	    	 					} catch (JSONException e) {
	    	 						e.printStackTrace();
	    	 					}
	    	 			    }
	    	 			  })
	    	 			.setNegativeButton("Cancelar",
	    	 			  new DialogInterface.OnClickListener() {
	    	 			    public void onClick(DialogInterface dialog,int id) {
	    	 			    	
	    	 				dialog.cancel();
	    	 			    }
	    	 			  });
	    	 		AlertDialog alertDialog = alertDialogBuilderExc.create();
	    	 		alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	    	 		alertDialog.show();
	        	}
	          break;
	      }
	}

	public int getCVV() {
		return CVV;
	}

	public void setCVV(int cVV) {
		CVV = cVV;
	}
	
	public int getID_CART() {
		return ID_CART;
	}

	public void setID_CART(int cID_CART) {
		ID_CART = cID_CART;
	}
	

	public String getNUM() {
		return NUM;
	}

	public void setNUM(String nUM) {
		NUM = nUM;
	}

	public String getVEN() {
		return VEN;
	}

	public void setVEN(String vEN) {
		VEN = vEN;
	}
}
