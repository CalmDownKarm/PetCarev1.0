package teamfirefighters.petcarev10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karm on 17/6/16.
 */
public class CardDBHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_CARDS =
            "CREATE TABLE " + CardDBContract.CardTable.TABLE_NAME + " (" +
                    CardDBContract.CardTable.COLUMN_NAME_CARD_ID + " INTEGER PRIMARY KEY," +
                    CardDBContract.CardTable.COLUMN_NAME_CARD_TITLE + TEXT_TYPE + COMMA_SEP +
                    CardDBContract.CardTable.COLUMN_NAME_CARD_IMAGE + TEXT_TYPE + COMMA_SEP +
                    CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT  + TEXT_TYPE + COMMA_SEP +
                    CardDBContract.CardTable.COLUMN_NAME_CARD_LIST  + TEXT_TYPE + COMMA_SEP +
                    CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION + TEXT_TYPE + COMMA_SEP +
                    CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION + TEXT_TYPE + COMMA_SEP +
                    CardDBContract.CardTable.COLUMN_NAME_SUBSUBCLASSIFICATION + TEXT_TYPE+" )";
    private static final String SQL_CREATE_CLASSIFICATION=
            "CREATE TABLE "+ CardDBContract.Classifications.TABLE_NAME+" ("+
             CardDBContract.Classifications.COLUMN_NAME_CLASS_ID+" INTEGER PRIMARY KEY, "+
                    CardDBContract.Classifications.COLUMN_NAME_CLASSIFICATION + TEXT_TYPE + ")";
    private static final String SQL_CREATE_SUBCLASSIFICATION="CREATE TABLE "+ CardDBContract.SubClassifications.TABLE_NAME+" ("+
            CardDBContract.SubClassifications.COLUMN_NAME_CLASS_ID+" INTEGER PRIMARY KEY, "+
            CardDBContract.SubClassifications.COLUMN_NAME_SUBCLASSIFICATION + TEXT_TYPE + ")";
    private static final String SQL_CREATE_SUBSUBCLASSIFICATION="CREATE TABLE "+ CardDBContract.SubSubClassifications.TABLE_NAME+" ("+
            CardDBContract.SubSubClassifications.COLUMN_NAME_CLASS_ID+" INTEGER PRIMARY KEY, "+
            CardDBContract.SubSubClassifications.COLUMN_NAME_SUBSUBCLASSIFICATION + TEXT_TYPE + ")";

    private static final String SQL_DELETE_CARDS =
            "DROP TABLE IF EXISTS " + CardDBContract.CardTable.TABLE_NAME;
    private static final String SQL_DELETE_CLASSIFICATIONS =
            "DROP TABLE IF EXISTS " + CardDBContract.Classifications.TABLE_NAME;
    private static final String SQL_DELETE_SUBCLASSIFICATIONS =
            "DROP TABLE IF EXISTS " + CardDBContract.SubClassifications.TABLE_NAME;
    private static final String SQL_DELETE_SUBSUBCLASSIFICATIONS =
            "DROP TABLE IF EXISTS " + CardDBContract.SubSubClassifications.TABLE_NAME;

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="PetCare.db";//Change this for neatness

    public CardDBHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CARDS);
        db.execSQL(SQL_CREATE_CLASSIFICATION);
        db.execSQL(SQL_CREATE_SUBCLASSIFICATION);
        db.execSQL(SQL_CREATE_SUBSUBCLASSIFICATION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_CARDS);
        db.execSQL(SQL_DELETE_CLASSIFICATIONS);
        db.execSQL(SQL_DELETE_SUBCLASSIFICATIONS);
        db.execSQL(SQL_DELETE_SUBSUBCLASSIFICATIONS);
        //TODO SET DB READY FLAG TO FALSE
        onCreate(db);
        //TODO FIX THIS SHIT
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }
}