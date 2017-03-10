package com.pvg.asci.noteit;

import java.io.File;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class NoteItDB {
	// database main variables
	private static final String DATABASE_NAME = "noteit_db";
	private static final String GAME_TABLE = "gametable";
	private static final String PASSKEY_TABLE = "passkeytable";
	private static final String COUNTER_STRIKE_TABLE = "counterstriketable";
	private static final String AGE_OF_EMPIRES_TABLE = "ageofempirestable";
	private static final String FIFA_TABLE = "fifatable";
	private static final String POCKET_TANKS_TABLE = "pockettankstable";
	private static final String NEED_FOR_SPEED_TABLE = "needforspeedtable";
	private static final String REGISTRATION_TABLE = "registrationtable";
	private static final int DATABASE_VERSION = 1;

	// game table variables
	public static final String GAME_ROWID = "_id";
	public static final String GAME_NAME = "game_name";
	public static final String GAME_RS = "game_rs";
	public static final String GAME_NO_OF_MEMBERS = "game_no_of_members";

	// passkey table variables
	public static final String PASSKEY_ROWID = "_id";
	public static final String PASSKEY_NAME = "passkey_name";
	public static final String PASSKEY_PASSKEY = "passkey_passkey";
	public static final String PASSKEY_LOGGED = "passkey_logged";

	// registration table variables
	public static final String REGISTRATION_ROWID = "_id";
	public static final String REGISTRATION_GAMEID = "game_id";
	public static final String REGISTRATION_NAME = "reg_name";
	public static final String REGISTRATION_MOBILE = "reg_mobile";
	public static final String REGISTRATION_EMAIL = "reg_email";
	public static final String REGISTRATION_RS = "reg_rs";
	public static final String REGISTRATION_NOTE = "reg_note";
	public static final String REGISTRATION_DELETE = "reg_delete";

	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	@SuppressLint("SdCardPath")
	private static class DBHelper extends SQLiteOpenHelper {

		@SuppressLint("SdCardPath")
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// SQLiteDatabase.openOrCreateDatabase("/sdcard/"+DATABASE_NAME,null);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

			// create game table
			db.execSQL("CREATE TABLE " + GAME_TABLE + " (" + GAME_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + GAME_NAME
					+ " TEXT NOT NULL, " + GAME_RS + " INTEGER NOT NULL, "
					+ GAME_NO_OF_MEMBERS + " INTEGER NOT NULL);");

			// create passkey table
			db.execSQL("CREATE TABLE " + PASSKEY_TABLE + " (" + PASSKEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + PASSKEY_NAME
					+ " TEXT NOT NULL, " + PASSKEY_PASSKEY + " TEXT NOT NULL, "
					+ PASSKEY_LOGGED + " TEXT NOT NULL);");

			// create registration table
			db.execSQL("CREATE TABLE " + REGISTRATION_TABLE + " ("
					+ REGISTRATION_ROWID + " TEXT PRIMARY KEY, "
					+ REGISTRATION_GAMEID + " INTEGER NOT NULL, "
					+ REGISTRATION_NAME + " TEXT NOT NULL, "
					+ REGISTRATION_MOBILE + " TEXT NOT NULL, "
					+ REGISTRATION_EMAIL + " TEXT NOT NULL, " + REGISTRATION_RS
					+ " INTEGER NOT NULL, " + REGISTRATION_NOTE + " TEXT, "
					+ REGISTRATION_DELETE + " TEXT NOT NULL);");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + GAME_TABLE);
			db.execSQL("DROP TABLE IF EXISTS" + PASSKEY_TABLE);
			db.execSQL("DROP TABLE IF EXISTS" + REGISTRATION_TABLE);
			onCreate(db);
		}

	}

	public NoteItDB(Context c) {
		ourContext = c;
	}

	public NoteItDB open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourHelper.close();
	}

	public long addGame(String name, int rs, int noOfMembers) {
		ContentValues cv = new ContentValues();
		cv.put(GAME_NAME, name);
		cv.put(GAME_RS, rs);
		cv.put(GAME_NO_OF_MEMBERS, noOfMembers);
		return ourDatabase.insert(GAME_TABLE, null, cv);
	}

	public long addPassKey(String name, String passkey, String loggedIn) {
		ContentValues cv = new ContentValues();
		cv.put(PASSKEY_NAME, name);
		cv.put(PASSKEY_PASSKEY, passkey);
		cv.put(PASSKEY_LOGGED, loggedIn);
		return ourDatabase.insert(PASSKEY_TABLE, null, cv);
	}

	public int getLoggedInUser() {
		int result = -1;
		Cursor c = ourDatabase.rawQuery("SELECT " + PASSKEY_ROWID + " FROM "
				+ PASSKEY_TABLE + " WHERE " + PASSKEY_LOGGED + "=\"TRUE\"",
				null);

		int iRow = c.getColumnIndex(PASSKEY_ROWID);

		if (c.moveToFirst()) {
			result = c.getInt(iRow);
		}
		return result;
	}

	public long logoutUser(int pid) {
		ContentValues cv = new ContentValues();
		cv.put(PASSKEY_ROWID, pid);
		cv.put(PASSKEY_LOGGED, "FALSE");
		return ourDatabase.update(PASSKEY_TABLE, cv, PASSKEY_ROWID + "=" + pid,
				null);
	}

	public long loginUser(int pid) {
		ContentValues cv = new ContentValues();
		cv.put(PASSKEY_ROWID, pid);
		cv.put(PASSKEY_LOGGED, "TRUE");
		return ourDatabase.update(PASSKEY_TABLE, cv, PASSKEY_ROWID + "=" + pid,
				null);
	}

	public boolean checkTable() {
		String count = "SELECT count(*) FROM " + PASSKEY_TABLE;
		Cursor mcursor = ourDatabase.rawQuery(count, null);
		mcursor.moveToFirst();
		int icount = mcursor.getInt(0);
		if (icount > 0)
			return true;
		else
			return false;
	}

	public long addEntry(String pid, int gid, String name, String mobile,
			String email, int rs, String note, String entryDeleted) {
		ContentValues cv = new ContentValues();
		cv.put(REGISTRATION_ROWID, pid);
		cv.put(REGISTRATION_GAMEID, gid);
		cv.put(REGISTRATION_NAME, name);
		cv.put(REGISTRATION_MOBILE, mobile);
		cv.put(REGISTRATION_EMAIL, email);
		cv.put(REGISTRATION_RS, rs);
		cv.put(REGISTRATION_NOTE, note);
		cv.put(REGISTRATION_DELETE, entryDeleted);
		return ourDatabase.insert(REGISTRATION_TABLE, null, cv);
	}

	public long deleteEntry(String pid) {
		ContentValues cv = new ContentValues();
		cv.put(REGISTRATION_ROWID, pid);
		cv.put(REGISTRATION_DELETE, "TRUE");
		return ourDatabase.update(REGISTRATION_TABLE, cv, REGISTRATION_ROWID
				+ "=\"" + pid + "\"", null);
	}

	public Cursor getRegistrationTableCursor() {
		Cursor c = ourDatabase.rawQuery("SELECT * FROM " + REGISTRATION_TABLE,
				null);
		return c;
	}

	public String getAllEntries() {
		String result = "";
		Cursor c = ourDatabase.rawQuery("SELECT * FROM " + REGISTRATION_TABLE,
				null);

		int iRow = c.getColumnIndex(REGISTRATION_ROWID);
		int iGame = c.getColumnIndex(REGISTRATION_GAMEID);
		int iName = c.getColumnIndex(REGISTRATION_NAME);
		int iEmail = c.getColumnIndex(REGISTRATION_EMAIL);
		int iMobile = c.getColumnIndex(REGISTRATION_MOBILE);
		int iRs = c.getColumnIndex(REGISTRATION_RS);
		int iNote = c.getColumnIndex(REGISTRATION_NOTE);
		int iDelete = c.getColumnIndex(REGISTRATION_DELETE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + ","
					+ Integer.toString(c.getInt(iGame)) + ","
					+ c.getString(iName) + "," + c.getString(iEmail) + ","
					+ c.getString(iMobile) + ","
					+ Integer.toString(c.getInt(iRs)) + ","
					+ c.getString(iNote) + "," + c.getString(iDelete) + "\n";
		}
		return result;
	}

	public int getPKID(String name) {
		int result = -1;
		Cursor c = ourDatabase.rawQuery("SELECT " + PASSKEY_ROWID + " FROM "
				+ PASSKEY_TABLE + " WHERE " + PASSKEY_NAME + " = \"" + name
				+ "\"", null);

		if (c.moveToLast())
			result = c.getInt(c.getColumnIndex(PASSKEY_ROWID));

		return result;
	}

	public String getPKPK(String name) {
		String result = "";
		Cursor c = ourDatabase.rawQuery("SELECT " + PASSKEY_PASSKEY + " FROM "
				+ PASSKEY_TABLE + " WHERE " + PASSKEY_NAME + " = \"" + name
				+ "\"", null);

		if (c.moveToLast())
			result = c.getString(c.getColumnIndex(PASSKEY_PASSKEY));

		return result;
	}

	public int getGameID(String name) {
		int result = -1;
		Cursor c = ourDatabase.rawQuery("SELECT " + GAME_ROWID + " FROM "
				+ GAME_TABLE + " WHERE " + GAME_NAME + " = \"" + name + "\"",
				null);

		if (c.moveToLast())
			result = c.getInt(c.getColumnIndex(GAME_ROWID));

		return result;
	}

	public int getGameRs(String name) {
		int result = -1;
		Cursor c = ourDatabase.rawQuery("SELECT " + GAME_RS + " FROM "
				+ GAME_TABLE + " WHERE " + GAME_NAME + " = \"" + name + "\"",
				null);

		if (c.moveToLast())
			result = c.getInt(c.getColumnIndex(GAME_RS));

		return result;
	}

	public int getGameMembers(String name) {
		int result = -1;
		Cursor c = ourDatabase.rawQuery("SELECT " + GAME_NO_OF_MEMBERS
				+ " FROM " + GAME_TABLE + " WHERE " + GAME_NAME + " = \""
				+ name + "\"", null);

		if (c.moveToLast())
			result = c.getInt(c.getColumnIndex(GAME_NO_OF_MEMBERS));

		return result;
	}

	public String getAllGameEntries() {
		String columns[] = new String[] { GAME_ROWID, GAME_NAME, GAME_RS,
				GAME_NO_OF_MEMBERS };
		Cursor c = ourDatabase.query(GAME_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int iRow = c.getColumnIndex(GAME_ROWID);
		int iName = c.getColumnIndex(GAME_NAME);
		int iRs = c.getColumnIndex(GAME_RS);
		int iMem = c.getColumnIndex(GAME_NO_OF_MEMBERS);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + "\t" + c.getString(iName)
					+ "\t" + c.getInt(iRs) + "\t" + c.getInt(iMem) + "\n";
		}
		return result;
	}

}