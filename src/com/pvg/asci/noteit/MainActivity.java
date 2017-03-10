package com.pvg.asci.noteit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	EditText etUsername, etPasskey;
	Button bLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		checkDB();
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPasskey = (EditText) findViewById(R.id.etPasskey);
		bLogin = (Button) findViewById(R.id.bLogin);
		
		bLogin.setOnClickListener(this);
	}

	private void checkDB() {
		// TODO Auto-generated method stub
		NoteItDB db = new NoteItDB(MainActivity.this);
		try{
			db.open();
			if(!db.checkTable()){
				StaticDB sdb = new StaticDB(MainActivity.this);
				sdb.addUsers();
				sdb.addGames();
				Toast.makeText(MainActivity.this, "USER ADDED", Toast.LENGTH_SHORT).show();
			}
		}catch(Exception e){
			
		}finally{
			db.close();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bLogin:
			String name = etUsername.getText().toString();
			String passkey = etPasskey.getText().toString();
			
			NoteItDB note = new NoteItDB(MainActivity.this);
			try{
				note.open();
				for(int i=0;i<41;i++){
					note.logoutUser(i);
				}
				if(note.getPKPK(name).equals(passkey)){
					note.loginUser(note.getPKID(name));
					startActivity(new Intent("com.pvg.asci.noteit.REGISTRATIONACTIVITY"));
					finish();
				}else{
					Toast.makeText(MainActivity.this, "Please enter corrent details", Toast.LENGTH_SHORT).show();
				}
			}catch(Exception e){
				
			}finally{
				note.close();
			}
			break;
		}
	}

}