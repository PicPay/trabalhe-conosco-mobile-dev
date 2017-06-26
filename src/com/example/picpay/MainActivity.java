package com.example.picpay;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener{
	private SimpleCursorAdapter adapter;
	private ListView listView;
	private Cursor mCursor;
	private DBController DB;
	private SearchView searchView;
	private Intent iUserActivity;
	private Bundle setParams;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Init();
		Populate();
	}
	
	private void Init(){
		DB = new DBController(this);
		listView = (ListView) findViewById(R.id.list);		
		iUserActivity    	 = new Intent(this, UserActivity.class);
		setParams = new Bundle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
	}

	
	private void Populate() {
		
		mCursor = DB.onSelect("select a.ID as _id, a.NAME, a.USERNAME, a.IMG from MOB_CADA a ORDER BY a.NAME ");
        if(mCursor!=null){
	        if(mCursor.getCount()>0){
	        	adapter = new SimpleCursorAdapter(this,R.layout.item_list_image,mCursor,new String[] {"NAME","NAME","USERNAME","_id","NAME"},new int[] {R.id.image,R.id.tvDES,R.id.tvDES2,R.id.tvVAL,R.id.tvCOD},0);
	        	adapter.setFilterQueryProvider(new FilterQueryProvider() {    
	                 @Override
	                 public Cursor runQuery(CharSequence arg0) {
	                	 return DB.onSelect("select a.ID as _id, a.NAME, a.USERNAME, a.IMG from MOB_CADA a where a.NAME like '%"+arg0.toString()+"%' or a.USERNAME like '%"+arg0.toString()+"%' OR a.ID like '%"+arg0.toString()+"%'  ORDER BY a.NAME"); 
	                 }
	             });
	        	adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

	    		    @Override
	    		    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
	    		        boolean binded = false;
	    		        if (view instanceof ImageView) {
	    		        	byte asd[]= cursor.getBlob(cursor.getColumnIndex("IMG"));
	    		        	BlobConverter bc = new BlobConverter(BitmapFactory.decodeByteArray(asd, 0, asd.length),45);
	    		        		    		        	
	    		        	((ImageView) view).setImageBitmap( bc.getBitmap());
	    		        	
	    		            binded = true;
	    		        }else{
	    		        	   if (view instanceof TextView){
	    		        	    	
	    		        	    TextView tNom = (TextView)view.findViewById(R.id.tvDES);
	        		        	if(tNom!=null){
	        		        		String nome = cursor.getString(cursor.getColumnIndex("NAME"));
	        		        		tNom.setText(nome.substring(0, ((nome.length()>35)?35:nome.length())  ));
	        		        		binded = true;
	        		        	}
	    		        	    	
	    		        	    	
	        		        	TextView tCod = (TextView)view.findViewById(R.id.tvCOD);
	       		        		if(tCod!=null){
	       		        			 String codigo = cursor.getString(cursor.getColumnIndex("USERNAME"));
	         		        			 tCod.setText(codigo);
	         		        			 binded = true;
	       		        		 }
	    		        	    	
	    		        		 TextView tDes2 = (TextView)view.findViewById(R.id.tvDES2);
	    		        		 if(tDes2!=null){
	    		        			 tDes2.setText("");
	      		        			 binded = true;
	    		        		 }

	    		        	 }
	    		        }
	    		        return binded;
	    		    }
	    		});

	        	listView.setAdapter(adapter);
	        	
	        	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	     public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
	        	    	 ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	        	    	 
	        	    	 setParams.putSerializable("ID", String.valueOf(id));
	        	    	 iUserActivity.putExtras(setParams);
	        	    	 startActivity(iUserActivity);
	 
					     finish();
					   						
	        	     }
	        	});
	        }else{
	        	startActivity(new Intent(this, SyncActivity.class));
	    		finish();
	        }
        }
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SyncActivity.class));
    		finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		adapter.getFilter().filter(newText.toString());
		return false;
	}
}
