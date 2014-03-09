package com.flyne.menu.highscores;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoreOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "flynedb";
	private static final String TABLE_NAME = "highscore";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String SCORE = "score";
	
	private static final String TABLE_CREATE = "CREATE TABLE " +
			TABLE_NAME + " (" +
			ID + " integer primary key autoincrement, " +
			NAME + " text, " +
			SCORE + " integer" +
			");";

	private static final String INSERT_ROW_TEMPLATE = "INSERT INTO " +
			TABLE_NAME + "(" + NAME +
			", " + SCORE + ")" + " VALUES (%s, %d);";
	
	private static final String REMOVE_ROW_TEMPLATE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = %d;";

	public HighScoreOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void checkAndUpdateHighScores(String user, int score) {
		List<HighScoreEntry> highScores = findAllHighScores();
		HighScoreEntry last = highScores.get(highScores.size() - 1);
		if (last.getScore() <= score) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.beginTransaction();
			try {
				db.execSQL(String.format(REMOVE_ROW_TEMPLATE, last.getId()));
				db.execSQL(String.format(INSERT_ROW_TEMPLATE, user, score));
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
	}
	
	public List<HighScoreEntry> findAllHighScores() {
		SQLiteDatabase db = this.getWritableDatabase();
		List<HighScoreEntry> results = new ArrayList<HighScoreEntry>();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE + " DESC, " + ID + " DESC", null);
		if (cursor.moveToFirst()) {
			int index = 1;
			while (!cursor.isAfterLast()) {
				int id = cursor.getInt(cursor.getColumnIndex(ID));
				String name = cursor.getString(cursor.getColumnIndex(NAME));
				int score = cursor.getInt(cursor.getColumnIndex(SCORE));
				results.add(new HighScoreEntry(id, index++, name, score));
				cursor.moveToNext();
			}
		}
		return results;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
		String[] queries = 
			{
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 1000000000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 100000000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 10000000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 1000000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 100000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 10000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 1000),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 100),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 10),
				String.format(INSERT_ROW_TEMPLATE, "'John Doe'", 1),
			};
		for (String query : queries) {
			db.execSQL(query);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
	}

}
