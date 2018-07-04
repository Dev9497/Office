package com.example.office.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.office.model.OfficeUser
import java.util.*

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // create table sql query
    private val CREATE_OFFICE_USER_TABLE = ("CREATE TABLE " + TABLE_OFFICE_USER + "("
            + OFFICE_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + OFFICE_USER_NAME + " TEXT,"
            + OFFICE_USER_EMAIL + " TEXT," + OFFICE_USER_PASSWORD + " TEXT" + ")")

    // drop table sql query
    private val DROP_OFFICE_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_OFFICE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_OFFICE_USER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //Drop User Table if exist
        db.execSQL(DROP_OFFICE_USER_TABLE)

        // Create tables again
        onCreate(db)

    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    fun getAllOfficeUser(): List<OfficeUser> {

        // array of columns to fetch
        val columns = arrayOf(OFFICE_USER_ID, OFFICE_USER_EMAIL, OFFICE_USER_NAME, OFFICE_USER_PASSWORD)

        // sorting orders
        val sortOrder = "$OFFICE_USER_NAME ASC"
        val officeUserList = ArrayList<OfficeUser>()

        val db = this.readableDatabase

        // query the user table
        val cursor = db.query(TABLE_OFFICE_USER, //Table to query
                columns,            //columns to return
                null,     //columns for the WHERE clause
                null,  //The values for the WHERE clause
                null,      //group the rows
                null,       //filter by row groups
                sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val officeUser = OfficeUser(id = cursor.getString(cursor.getColumnIndex(OFFICE_USER_ID)).toInt(),
                        name = cursor.getString(cursor.getColumnIndex(OFFICE_USER_NAME)),
                        email = cursor.getString(cursor.getColumnIndex(OFFICE_USER_EMAIL)),
                        password = cursor.getString(cursor.getColumnIndex(OFFICE_USER_PASSWORD)))

                officeUserList.add(officeUser)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return officeUserList
    }


    /**
     * This method is to create user record
     *
     * @param user
     */
    fun addUser(officeUser: OfficeUser) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(OFFICE_USER_NAME, officeUser.name)
        values.put(OFFICE_USER_EMAIL, officeUser.email)
        values.put(OFFICE_USER_PASSWORD, officeUser.password)

        // Inserting Row
        db.insert(TABLE_OFFICE_USER, null, values)
        db.close()
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateUser(officeUser: OfficeUser) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(OFFICE_USER_NAME, officeUser.name)
        values.put(OFFICE_USER_EMAIL, officeUser.email)
        values.put(OFFICE_USER_PASSWORD, officeUser.password)

        // updating row
        db.update(TABLE_OFFICE_USER, values, "$OFFICE_USER_ID = ?",
                arrayOf(officeUser.id.toString()))
        db.close()
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(officeUser: OfficeUser) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_OFFICE_USER, "$OFFICE_USER_ID = ?",
                arrayOf(officeUser.id.toString()))
        db.close()


    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(OFFICE_USER_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$OFFICE_USER_EMAIL = ?"

        // selection argument
        val selectionArgs = arrayOf(email)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(TABLE_OFFICE_USER, //Table to query
                columns,        //columns to return
                selection,      //columns for the WHERE clause
                selectionArgs,  //The values for the WHERE clause
                null,  //group the rows
                null,   //filter by row groups
                null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(OFFICE_USER_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$OFFICE_USER_EMAIL = ? AND $OFFICE_USER_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(email, password)

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TABLE_OFFICE_USER, //Table to query
                columns, //columns to return
                selection, //columns for the WHERE clause
                selectionArgs, //The values for the WHERE clause
                null,  //group the rows
                null, //filter by row groups
                null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }

    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "OfficeUserManager.db"

        // User table name
        private val TABLE_OFFICE_USER = "user"

        // User Table Columns names
        private val OFFICE_USER_ID = "user_id"
        private val OFFICE_USER_NAME = "user_name"
        private val OFFICE_USER_EMAIL = "user_email"
        private val OFFICE_USER_PASSWORD = "user_password"
    }
}