package com.twgan.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class SQLite extends SQLiteOpenHelper {
	
	private static final String TAG = "SQLite";

    // �� DB_FILE ���w���ɮפ��s�b��
    // SQLite �|�I�s onCreate(...) �ӫإߩһݭn�� table
    private static final String DB_FILE = "sqlite.db";
    // ���Ʈw���c���ק�ɡADB_VERSION�n�[ 1
    // SQLite �~�|�I�s onUpgrade(...) �ӭק��Ʈw
    private static final int DB_VERSION = 3;
    
    public static final String TABLE_LOG = "log";
    public static final String TABLE_MEMBER = "member";
    public static final String TABLE_FRIENDS = "friends";
    public static final String TABLE_CHAT = "chat";
 
	private SQLiteDatabase db;
 
	public SQLite(Context context) {	//�غc�l
		super(context, DB_FILE, null, DB_VERSION);
		db = this.getWritableDatabase();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		String createMember =
		    "create table "+TABLE_MEMBER+" ("
		        + "uid INTEGER PRIMARY KEY AUTOINCREMENT,"
		        + "username TEXT,"
		        + "password TEXT"
		    + ");";
		String createFriends =
			    "create table "+TABLE_FRIENDS+" ("
			        + "uid INTEGER PRIMARY KEY,"
			        + "fuid INTEGER,"
			        + "fusername TEXT,"
			        + "status INTEGER,"
			        + "gid INTEGER,"
			        + "note TEXT,"
			        + "num INTEGER,"
			        + "dateline INTEGER"
			    + ");";
		
		String createChat =
			    "create table "+TABLE_CHAT+" ("
			        + "_id INTEGER PRIMARY KEY AUTOINCREMENT," // �o���O�F�L���������n���A�ӥB�n�s�o�Ӧ����W��!!!!!!!!!!!
			        + "uid INTEGER,"
			        + "fuidstr TEXT," // identify the chat members
			        + "fuid INTEGER," // who said
			        + "said TEXT," // say what
			        + "type TEXT," // text / emotion / voice / picture
			        + "dateline INTEGER"
			    + ");";
		
		String createLog =
			    "create table "+TABLE_LOG+" ("
			        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + "action TEXT NOT NULL,"
			        + "time INTEGER NOT NULL"
			    + ");";
		//�إ�config��ƪ�A�Ա��аѦ�SQL�y�k
		db.execSQL(createMember);
		db.execSQL(createFriends);
		db.execSQL(createChat);
		db.execSQL(createLog);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade");
		//oldVersion=�ª���Ʈw�����FnewVersion=�s����Ʈw����
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOG);	//�R���¦�����ƪ�
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_MEMBER);	//�R���¦�����ƪ�
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_FRIENDS);	//�R���¦�����ƪ�
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_CHAT);	//�R���¦�����ƪ�
		onCreate(db);
	}
    
	/*
	 * DB Functions
	 */
	public void addMember(String username, String password){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("username", username);
    	values.put("password", password);
    	db.insertOrThrow(SQLite.TABLE_MEMBER, null, values);
    	
    }
    
    public boolean isExistMember(String username){
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	String sql = "select username from `"+SQLite.TABLE_MEMBER+"` where `username` = '"+username+"'";
    	Cursor cursor = db.rawQuery(sql, null);
    	Log.v(TAG, "TOTAL : "+cursor.getCount() + ":"+sql);
    	
    	return (cursor.getCount() > 0) ? true : false;
    }
	
	public void addFriends(String uid, String fuid, String fusername, String status, String gid, String note, String num){
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("uid", uid);
    	values.put("fuid", fuid);
    	values.put("fusername", fusername);
    	values.put("status", status);
    	values.put("gid", gid);
    	values.put("note", note);
    	values.put("num", num);
    	values.put("dateline", ""+System.currentTimeMillis());
    	db.insertOrThrow(SQLite.TABLE_FRIENDS, null, values);
    }
    
    public boolean isExistFriend(String uid, String fuid){
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	String sql = "select fuid from `"+SQLite.TABLE_FRIENDS+"` where `uid` = '"+uid+"' and `fuid` = '" + fuid + "'";
    	Cursor cursor = db.rawQuery(sql, null);
    	Log.v(TAG, "TOTAL : "+cursor.getCount() + ":"+sql);
    	
    	return (cursor.getCount() > 0) ? true : false;
    }
	
	public void addChat(String uid, String fuidstr, String fuid, String said, String type){ 
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put("uid", uid);
    	values.put("fuidstr", fuidstr);
    	values.put("fuid", fuid);
    	values.put("said", said);
    	values.put("type", type);
    	values.put("dateline", ""+System.currentTimeMillis());
    	db.insertOrThrow(SQLite.TABLE_CHAT, null, values);
    }
    
    public boolean isExistChat(String uid, String fuidstr){
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	String sql = "select fuidstr from `"+SQLite.TABLE_CHAT+"` where `uid` = '"+uid+"' and `fuidstr` = '" + fuidstr + "'";
    	Cursor cursor = db.rawQuery(sql, null);
    	Log.v(TAG, "TOTAL : "+cursor.getCount() + ":"+sql);
    	
    	return (cursor.getCount() > 0) ? true : false;
    }
}