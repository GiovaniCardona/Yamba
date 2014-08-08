package com.moviles.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;


public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "StatusActivity";
	private EditText  editStatus;
	private Button buttonTweet;
	private TextView textCount; 
	private int defaultTextColor;
	
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editStatus=(EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        textCount = (TextView) findViewById(R.id.textCount);
        
        buttonTweet.setOnClickListener(this);
        
        defaultTextColor = textCount.getTextColors().getDefaultColor(); 
        editStatus.addTextChangedListener(new TextWatcher(){ 
        			
        	public void afterTextChanged(Editable s) { 
        		int count = 140 - editStatus.length(); 
        		textCount.setText(Integer.toString(count));
        		textCount.setTextColor(Color.BLUE); 
        		if ((count < 25) && (9 < count))
        			textCount.setTextColor(Color.GRAY);
        		else if (count < 10)
        			textCount.setTextColor(Color.RED);
        		else
        			textCount.setTextColor(defaultTextColor);
        	}
        			
        	public void beforeTextChanged(CharSequence s, int start, int count, int after) { 
        			}
        			
        	public void onTextChanged(CharSequence s,int start, int before, int count) { 
        			}
        });
        			
    }
    
    public void onClick(View View){	
    	String status=editStatus.getText().toString();
    	Log.d(TAG, "Tweet enviado ");
    	new PostTask().execute(status);
    }
    
    
    private final class PostTask extends AsyncTask<String, Integer, String> { 
       	
    	protected String doInBackground(String... params) { 
    	
    		YambaClient yambaCloud = new YambaClient("student", "password");
    		
    		try {
    			yambaCloud.postStatus(params[0]);
    			return "Successfully posted";
    		} catch(YambaClientException e) {
    			e.printStackTrace();
    			return "Failed to post to yamba service"; 
    		
    		}
    	}
    
       	protected void onProgressUpdate(Integer... values) {
    	super.onProgressUpdate(values);
    }
    
       	protected void onPostExecute(String result) {
       		super.onPostExecute(result);
    		Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show(); 
    	}
    }	
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
