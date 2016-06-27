package net.anumbrella.lkshop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.anumbrella.lkshop.config.MySql;

/**
 * author：Anumbrella
 * Date：16/5/31 下午12:48
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, MySql.DATABASE_NAME, null, MySql.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MySql.createProductTable);
        db.execSQL(MySql.createUserTable);
        db.execSQL(MySql.createCollectTable);
        db.execSQL(MySql.createShoppingTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
