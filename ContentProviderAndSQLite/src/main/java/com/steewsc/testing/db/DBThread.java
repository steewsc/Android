package com.steewsc.testing.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Steewsc on 1.2.14..
 */
public class DBThread extends ContentProvider {
    public final static String AUTHORITY = "com.steewsc.testing";
    private static final int T_EMP_ARRAY = 0;
    private static final int T_EMP_SINGLE = 1;
    private static final int T_SHIFT_ARRAY = 2;
    private static final int T_SHIFT_SINGLE = 3;
    private static final UriMatcher MATCHER;

    private static final int TABLE_NAME = 0;
    private static final int TABLE_DEF_SORT = 1;
    private static final int TABLE_IS_SINGLE = 2;

    static {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(AUTHORITY,
                Tables.Employees.TABLE_NAME, T_EMP_ARRAY);
//                Tables.Employees.TABLE_NAME + "/*/*", T_EMP_ARRAY);
        MATCHER.addURI(AUTHORITY,
                Tables.Employees.TABLE_NAME + "/#", T_EMP_SINGLE);

        MATCHER.addURI(AUTHORITY,
                Tables.Shifts.TABLE_NAME, T_SHIFT_ARRAY);
//                Tables.Shifts.TABLE_NAME + "/*/*", T_SHIFT_ARRAY);
        MATCHER.addURI(AUTHORITY,
                Tables.Shifts.TABLE_NAME + "/#", T_SHIFT_SINGLE);
    }

    private DBCore db = null;

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     * <p/>
     * <p>You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     * <p/>
     * <p>If you use SQLite, {@link android.database.sqlite.SQLiteOpenHelper}
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * {@link android.database.sqlite.SQLiteOpenHelper#getReadableDatabase} or
     * {@link android.database.sqlite.SQLiteOpenHelper#getWritableDatabase}
     * from this method.  (Instead, override
     * {@link android.database.sqlite.SQLiteOpenHelper#onOpen} to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        db = new DBCore( getContext() );
        return((db == null) ? false : true);
    }

    /**
     * Implement this to handle query requests from clients.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p/>
     * Example client call:<p>
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:<p>
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     * <p/>
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     * <p/>
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     * <p/>
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri           The URI to query. This will be the full URI sent by the client;
     *                      if the client is requesting a specific record, the URI will end in a record number
     *                      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *                      that _id value.
     * @param projection    The list of columns to put into the cursor. If
     *                      {@code null} all columns are included.
     * @param selection     A selection criteria to apply when filtering rows.
     *                      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the selection.
     *                      The values will be bound as Strings.
     * @param sortOrder     How the rows in the cursor should be sorted.
     *                      If {@code null} then the provider is free to define the sort order.
     * @return a Cursor or {@code null}.
     */
    @Override
    synchronized public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if( db == null ){
            db = new DBCore( getContext() );
        }
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        HashMap<Integer, String> tbl = getTableInfoFromUri(uri);
        qb.setTables( tbl.get( TABLE_NAME ) );
        String orderBy;
        if( TextUtils.isEmpty( sortOrder ) ) {
            orderBy = tbl.get( TABLE_DEF_SORT ) ;
        }else{
            orderBy = sortOrder;
        }
        Cursor c = qb.query( db.getReadableDatabase(), projection, selection,
                        selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return(c);
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * <code>vnd.android.cursor.item</code> for a single record,
     * or <code>vnd.android.cursor.dir/</code> for multiple items.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p/>
     * <p>Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or {@code null} if there is no type.
     */
    @Override
    public String getType(Uri uri) {
        HashMap<Integer, String> res = getTableInfoFromUri(uri);
        if( res.get( TABLE_IS_SINGLE ) == null ){
            return("vnd.steewsc.cursor.dir/" + res.get(TABLE_NAME));
        }
        return("vnd.steewsc.cursor.item/" + res.get(TABLE_NAME));
    }

    /**
     * Implement this to handle requests to insert a new row.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be {@code null}.
     * @return The URI for the newly inserted item.
     */
    @Override
    synchronized public Uri insert(Uri uri, ContentValues values) {
        if( db == null ){
            db = new DBCore( getContext() );
        }
        HashMap<Integer, String> res = getTableInfoFromUri(uri);
        long rowID = 0;
        try{
            rowID = db.getWritableDatabase().insert(
                res.get(TABLE_NAME),
                null,
                values);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (rowID > 0) {
            Uri nUri = ContentUris.withAppendedId(Tables.CONTENT_URI, rowID);
            try{
                getContext().getContentResolver().notifyChange(nUri, null);
            }catch(Exception ee ){
                ee.printStackTrace();
            }
            return(nUri);
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    /**
     * Implement this to handle requests to delete one or more rows.
     * The implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p/>
     * <p>The implementation is responsible for parsing out a row ID at the end
     * of the URI, if a specific row is being deleted. That is, the client would
     * pass in <code>content://contacts/people/22</code> and the implementation is
     * responsible for parsing the record number (22) when creating a SQL statement.
     *
     * @param uri           The full URI to query, including a row ID (if a specific record is requested).
     * @param where     An optional restriction to apply to rows when deleting.
     * @param whereArgs
     * @return The number of rows affected.
     * @throws SQLException
     */
    @Override
    synchronized public int delete(Uri uri, String where, String[] whereArgs) {
        if( db == null ){
            db = new DBCore( getContext() );
        }
        HashMap<Integer, String> res = getTableInfoFromUri(uri);
        int count = db.getWritableDatabase().delete(res.get(TABLE_NAME), where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return(count);
    }

    /**
     * Implement this to handle requests to update one or more rows.
     * The implementation should update all rows matching the selection
     * to set the columns according to the provided values map.
     * after updating.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri           The URI to query. This can potentially have a record ID if this
     *                      is an update request for a specific record.
     * @param values        A set of column_name/value pairs to update in the database.
     *                      This must not be {@code null}.
     * @param where     An optional filter to match rows to update.
     * @param whereArgs
     * @return the number of rows affected.
     */
    @Override
    synchronized public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        if( db == null ){
            db = new DBCore( getContext() );
        }
        HashMap<Integer, String> res = getTableInfoFromUri(uri);
        int count= db.getWritableDatabase()
                        .update(res.get(TABLE_NAME), values, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return(count);
    }

    private HashMap<Integer, String> getTableInfoFromUri(Uri url) {
        HashMap<Integer, String> result = new HashMap<Integer, String>();
        System.out.print( "getTableInfoFromUri => " + url);

        int uMatch = MATCHER.match(url);
        switch(uMatch){
            case T_EMP_ARRAY:
                result.put(TABLE_NAME, Tables.Employees.TABLE_NAME);
                result.put(TABLE_DEF_SORT, Tables.Employees.DEFAULT_SORT);
                result.put(TABLE_IS_SINGLE, null);
                break;
            case T_EMP_SINGLE:
                result.put(TABLE_NAME, Tables.Employees.TABLE_NAME);
                result.put(TABLE_DEF_SORT, Tables.Employees.DEFAULT_SORT);
                result.put(TABLE_IS_SINGLE, "1");
                break;
            case T_SHIFT_ARRAY:
                result.put(TABLE_NAME, Tables.Shifts.TABLE_NAME);
                result.put(TABLE_DEF_SORT, Tables.Shifts.DEFAULT_SORT);
                result.put(TABLE_IS_SINGLE, null);
                break;
            case T_SHIFT_SINGLE:
                result.put(TABLE_NAME, Tables.Shifts.TABLE_NAME);
                result.put(TABLE_DEF_SORT, Tables.Shifts.DEFAULT_SORT);
                result.put(TABLE_IS_SINGLE, "1");
                break;
            default:
                result.put(TABLE_NAME, Tables.Employees.TABLE_NAME);
                result.put(TABLE_DEF_SORT, Tables.Employees.DEFAULT_SORT);
                result.put(TABLE_IS_SINGLE, null);
                break;
        }
        System.out.print("getTableInfoFromUri => " + result.get(TABLE_NAME));
        return result;
    }
}
