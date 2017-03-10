package com.pvg.asci.noteit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends ActionBarActivity implements
		OnClickListener, OnItemSelectedListener {

	Button bSubmit, bCancel, bAsci;
	TextView tvRs;
	Spinner spGame;
	EditText etName, etMobile, etEmail, etNote;

	DrawerLayout dwMenu;

	String gameSelected;
	int rs;
	int uID, gid, hour, min;
	String pid, date;
	String email, name, mobile;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub

		tvRs = (TextView) findViewById(R.id.tvRs);

		spGame = (Spinner) findViewById(R.id.spGame);

		etName = (EditText) findViewById(R.id.etName);
		etMobile = (EditText) findViewById(R.id.etMobile);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etNote = (EditText) findViewById(R.id.etNote);

		bCancel = (Button) findViewById(R.id.bCancel);
		bSubmit = (Button) findViewById(R.id.bSubmit);
		bAsci = (Button) findViewById(R.id.bAsci);

		// spinner adding items
		List<String> list = new ArrayList<String>();
		list.add("Counter Strike");
		list.add("Age Of Empires");
		list.add("FIFA");
		list.add("Need For Speed");
		list.add("Pocket Tanks");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spGame.setAdapter(dataAdapter);
		spGame.setOnItemSelectedListener(this);
		bSubmit.setOnClickListener(this);
		bCancel.setOnClickListener(this);
		bAsci.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bCancel:
			clearAllViews();
			break;

		case R.id.bSubmit:
			name = etName.getText().toString();
			email = etEmail.getText().toString();
			mobile = etMobile.getText().toString();
			int Rs = Integer.parseInt(tvRs.getText().toString());
			String note = etNote.getText().toString();

			if (name.equals("")) {
				Toast.makeText(RegistrationActivity.this, "ENTER NAME",
						Toast.LENGTH_SHORT).show();
			}

			if (email.equals("")) {
				Toast.makeText(RegistrationActivity.this, "ENTER EMAIL",
						Toast.LENGTH_SHORT).show();
			}

			if (mobile.equals("")) {
				Toast.makeText(RegistrationActivity.this, "ENTER MOBILE",
						Toast.LENGTH_SHORT).show();
			}

			if (!name.equals("") && !email.equals("") && !mobile.equals("")) {

				NoteItDB entry = new NoteItDB(RegistrationActivity.this);
				try {
					entry.open();
					uID = entry.getLoggedInUser();

					gid = entry.getGameID(spGame.getSelectedItem().toString());

					date = new SimpleDateFormat("dd").format(new Date());

					Calendar c = Calendar.getInstance();
					hour = c.get(Calendar.HOUR_OF_DAY);
					min = c.get(Calendar.MINUTE);

					pid = Integer.toString(uID) + Integer.toString(gid) + date
							+ Integer.toString(hour) + Integer.toString(min);

					entry.addEntry(pid, gid, name, mobile, email, Rs, note,
							"FALSE");

					callDialog(RegistrationActivity.this);

				} catch (Exception e) {
					Toast.makeText(RegistrationActivity.this, "ERROR",
							Toast.LENGTH_SHORT).show();
				} finally {
					if (entry != null)
						entry.close();
				}
			}

			break;

		case R.id.bAsci:
			if (bAsci.getText().toString().equals("NO")) {
				bAsci.setText("YES");
				rs = 0;
			} else {
				bAsci.setText("NO");
				NoteItDB n = new NoteItDB(RegistrationActivity.this);
				try {
					n.open();
					rs = n.getGameRs((String) spGame.getSelectedItem());
				} catch (Exception e) {

				} finally {
					n.close();
				}
			}

			tvRs.setText(Integer.toString(rs));
			break;
		}
	}

	private void callDialog(Context context) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Notify");

		// set dialog message
		alertDialogBuilder
				.setMessage("Click To notify via")
				.setCancelable(false)
				.setPositiveButton("Email",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								final Intent emailLauncher = new Intent(
										Intent.ACTION_SEND);
								emailLauncher.setType("message/rfc822");
								emailLauncher.putExtra(Intent.EXTRA_EMAIL,
										new String[] { email,
												"sugapravin7@gmail.com" });
								emailLauncher.putExtra(Intent.EXTRA_SUBJECT,
										"KURUKSHETRA REGISTRATION");
								emailLauncher
										.putExtra(
												Intent.EXTRA_TEXT,
												"HI "
														+ name
														+ ",\n Thank You for participating in KURUKSHETRA. Your Unique ID: "
														+ pid
														+ "\n Hope you enjoy the event !!!");
								try {
									startActivity(emailLauncher);
								} catch (ActivityNotFoundException e) {

								}
							}
						});
						
		//add another button to Alertdialog
		//then connect it with your SMS module
		//make sure you add setNeutralButton or setNegativeButton for SMS !!!! 

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void clearAllViews() {
		// TODO Auto-generated method stub
		etName.setText("");
		etEmail.setText("");
		etMobile.setText("");
		etNote.setText("");
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		NoteItDB n = new NoteItDB(RegistrationActivity.this);
		try {
			n.open();
			changeRs(Integer.toString(n.getGameRs((String) spGame
					.getSelectedItem())));
		} catch (Exception e) {

		} finally {
			n.close();
		}
	}

	private void changeRs(String string) {
		// TODO Auto-generated method stub
		tvRs.setText(string);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		NoteItDB n = new NoteItDB(RegistrationActivity.this);
		try {
			n.open();
			changeRs(Integer.toString(n.getGameRs((String) spGame
					.getSelectedItem())));
		} catch (Exception e) {

			Toast.makeText(RegistrationActivity.this,
					spGame.getSelectedItem().toString(), Toast.LENGTH_SHORT)
					.show();
		} finally {
			n.close();
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
		switch (id) {
		case R.id.action_delete:
			callDeleteDialog();
			break;

		case R.id.action_sendDB:
			sendDBviaEmail();
			break;
			
		case R.id.action_contactus:
			Toast.makeText(RegistrationActivity.this, "Piyush: 8149963563", Toast.LENGTH_SHORT).show();
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void sendDBviaEmail() {
		// TODO Auto-generated method stub
		Boolean returnCode = false;
		int i = 0;
		String csvValues = "";

		NoteItDB entry = new NoteItDB(RegistrationActivity.this);
		try {
			entry.open();
			String dbContent = entry.getAllEntries();
			File outFile = new File(Environment.getExternalStorageDirectory(),
					Integer.toString(uID) + "_BACKUP.csv");
			FileWriter fileWriter = new FileWriter(outFile.getAbsoluteFile());
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(dbContent);
			out.close();
			entry.close();
			returnCode = true;

			// send it via email

			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("text/plain");
			emailIntent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "sugapravin7@gmail.com" });
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, Integer.toString(uID)
					+ " BACKUP KURUKSHETRA");
			emailIntent.putExtra(Intent.EXTRA_TEXT, "");
			File root = Environment.getExternalStorageDirectory();
			String pathToMyAttachedFile = Integer.toString(uID) + "_BACKUP.csv";
			File file = new File(root, pathToMyAttachedFile);
			if (!file.exists() || !file.canRead()) {
				return;
			}
			Uri uri = Uri.fromFile(file);
			emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
			startActivity(Intent.createChooser(emailIntent,
					"Pick an Email provider, to send backup"));
		} catch (IOException e) {
			returnCode = false;
		}
	}

	private void callDeleteDialog() {
		// TODO Auto-generated method stub

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				RegistrationActivity.this);
		alertDialog.setTitle("Delete Entry");
		alertDialog.setMessage("Enter Unique ID");

		final EditText input = new EditText(RegistrationActivity.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		input.setLayoutParams(lp);
		alertDialog.setView(input);

		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String deleteID = input.getText().toString();
						NoteItDB noteDelete = new NoteItDB(
								RegistrationActivity.this);
						try {
							noteDelete.open();
							if(noteDelete.deleteEntry(deleteID) == -1)
								Toast.makeText(RegistrationActivity.this, "Error! Please try again", Toast.LENGTH_SHORT).show();
						} catch (Exception e) {

						} finally {
							noteDelete.close();
						}
					}
				});

		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();

	}

}
