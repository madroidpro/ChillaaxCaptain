package madroid.chillaaxcaptain.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by madroid on 31-07-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Version
    public static final int DATABASE_VERSION=1;

    // Database name
    public static final String DATABASE_NAME="chillaax";

   // Maximum qty of individual item can be selected
    public static final int MAX_ITEM_SIZE=20;

    // Database Tables Names

    public static final String CART_ITEMS="cart_items";

    //Column Names
    public static final String KEY_ID = "id"; // 0
    public static final String KEY_ITEM_ID = "item_id"; // 1
    public static final String KEY_ITEM_NAME = "item_name"; // 2
    public static final String KEY_ITEM_Quantity = "item_quantity"; // 3
    public static final String KEY_ITEM_PRICE = "item_price"; // 4
    public static final String KEY_ITEM_IMAGE = "item_image"; // 5
    public static final String KEY_ITEM_STATUS = "item_status"; // 6   0-added, 1-placed
    public static final String KEY_ITEM_COMMENT = "item_comment"; // 7


    //Create Tables
    String CREATE_CART_TABLE="CREATE TABLE "+CART_ITEMS+ " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            KEY_ITEM_ID + " VARCHAR UNIQUE ,"+
            KEY_ITEM_NAME + " VARCHAR ,"+
            KEY_ITEM_Quantity + " VARCHAR ,"+
            KEY_ITEM_PRICE + " VARCHAR ,"+
            KEY_ITEM_IMAGE + " VARCHAR ,"+
            KEY_ITEM_STATUS + " VARCHAR ,"+
            KEY_ITEM_COMMENT + " VARCHAR);";

   //Creating DATABASE
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db){
        db.execSQL(CREATE_CART_TABLE);
        Log.d("Tables","Successfully Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CART_ITEMS);
        onCreate(db);
    }

    public Boolean insertTableData(String table_name, ContentValues table_data){
        SQLiteDatabase sd = this.getWritableDatabase();
        Log.d("sqldata_insertion","success");
        try{
            sd.insertOrThrow(table_name,null,table_data);
            return true;
        }catch (Exception e){
            Log.d("sqldata_exp",e+"");
            return false;
        }
    }

    //Retrieve data from table
    public Cursor getTableData(String Table_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from "+Table_name , null);
    }
    public Cursor getTableData(String Table_name,String pColumn,String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from "+Table_name+" where "+pColumn+" = "+key , null);
    }

    public Cursor getTableData(String Table_name,String pColumn,String key,String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        return db.rawQuery("select * from "+Table_name+" where "+pColumn+" = "+key+" and item_status = "+status , null);
    }

    public int updateTableData(String table_name,int operationType,String column_name,String key){
        //operationType 1=increment 0=decrement
        SQLiteDatabase sd=this.getWritableDatabase();
        ContentValues updatable_data =new ContentValues();
        try {
            int qty;
            int price;
            Cursor resultSet=getTableData(table_name,column_name,key);
            resultSet.moveToFirst();
            if(operationType==1 && Integer.parseInt(resultSet.getString(3)) < MAX_ITEM_SIZE){
               // Increment quantity & update price, base price = price/qty
                qty=Integer.parseInt(resultSet.getString(3))+1;
                price=(Integer.parseInt(resultSet.getString(4))/Integer.parseInt(resultSet.getString(3)))*qty;
                Log.d("price",price+"");
                updatable_data.put(KEY_ITEM_Quantity,qty);
                updatable_data.put(KEY_ITEM_PRICE,price);
                sd.update(table_name,updatable_data,column_name+ "=" +key,null);
                return qty;
            }else if(operationType==0 && Integer.parseInt(resultSet.getString(3))>1){
                // Decrement quantity & update price
                qty=Integer.parseInt(resultSet.getString(3))-1;
                price=(Integer.parseInt(resultSet.getString(4))/Integer.parseInt(resultSet.getString(3)))*qty;
                Log.d("price",price+"");
                updatable_data.put(KEY_ITEM_Quantity,qty);
                updatable_data.put(KEY_ITEM_PRICE,price);
                sd.update(table_name,updatable_data,column_name+ "=" +key,null);
                return qty;
            }
        }catch (Exception e){
            Log.d("err",e.toString());
            return -1;
        }


    return -1;


    }

    public Boolean updateTableRowData(String TableName,String key,String cColumn,String value){
        SQLiteDatabase sd=this.getWritableDatabase();
        ContentValues updatable_data =new ContentValues();
        try{
            updatable_data.put(cColumn,value);
            sd.update(TableName,updatable_data,KEY_ITEM_ID+ "=" +key,null);
//            String query="UPDATE "+TableName+" SET "+cColumn+" = "+value+" WHERE "+KEY_ITEM_ID+" = "+key;
//            sd.execSQL(query);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean  updateAllTableData(String TableName,String cCoulmn,String value){
        SQLiteDatabase sd=this.getWritableDatabase();
       try{
           String query="UPDATE "+TableName+" SET "+cCoulmn+" = "+value;
           sd.execSQL(query);
           return true;
       }catch (Exception e){
           return false;
       }


    }


    //Delete table data
    public void clearTableData(String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + table_name);
    }

    public void removeTableData(String table_name,String column_name,String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from "+table_name+" where "+ column_name +" = " + id;
        db.execSQL(query);
        // db.close();
//        refreshTable();
    }

    // Returns the record count
    public int getRecordCount(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return 0;
        }
        return (int)(DatabaseUtils.queryNumEntries(db, table_name));
    }

    //Returns Conditioned record count
    public int getRecordCount(String Table_name,String cCoulmn,String key){
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return 0;
        }
       // DatabaseUtils.queryNumEntries()
            Cursor cursor=db.rawQuery("select * from "+Table_name+" where "+cCoulmn+" = "+key , null);
                cursor.moveToNext();
        return cursor.getCount();
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
