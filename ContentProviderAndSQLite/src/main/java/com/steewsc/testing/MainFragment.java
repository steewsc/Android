package com.steewsc.testing;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.steewsc.testing.db.DBThread;
import com.steewsc.testing.db.Tables;
import com.steewsc.testing.models.Employee;
import com.steewsc.testing.models.Shift;

/**
 * Created by Steewsc on 2.2.14..
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private Button btnInsertEmployees;
    private Button btnInsertShifts;
    private Button btnReloadEmployees;
    private Button btnReloadShifts;
    private ListView lstEmployees;
    private ListView lstShifts;
    private Activity mActivity;
    private Cursor curEmployees;
    private Cursor curShifts;
    private EmployeeCursorAdapter adapterEmps;
    private ShiftCursorAdapter adapterShifts;
    public DBThread dbThread;

    public MainFragment() {

    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        btnInsertEmployees = (Button) view.findViewById(R.id.btn_insert_emps);
        btnInsertShifts = (Button) view.findViewById(R.id.btn_insert_shifts);
        btnReloadEmployees = (Button) view.findViewById(R.id.btn_load_emps);
        btnReloadShifts = (Button) view.findViewById(R.id.btn_load_shifts);

        btnInsertEmployees.setOnClickListener(this);
        btnInsertShifts.setOnClickListener(this);
        btnReloadEmployees.setOnClickListener(this);
        btnReloadShifts.setOnClickListener(this);

        lstEmployees = (ListView) view.findViewById(R.id.lst_employees);
        lstShifts = (ListView) view.findViewById(R.id.lst_shifts);
        //dbThread = new DBThread();

        curEmployees = mActivity.getContentResolver().query(Tables.Employees.CONTENT_URI, Tables.Employees.PROJECTION, null, null, null);
        curShifts = mActivity.getContentResolver().query(Tables.Shifts.CONTENT_URI, Tables.Shifts.PROJECTION, null, null, null);

        adapterEmps = new EmployeeCursorAdapter(
                mActivity,
                curEmployees);

        adapterShifts = new ShiftCursorAdapter(
                mActivity,
                curShifts);

        lstEmployees.setAdapter(adapterEmps);
        lstShifts.setAdapter(adapterShifts);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_insert_emps:
                insertEmployees();
                break;
            case R.id.btn_insert_shifts:
                insertShifts();
                break;
            case R.id.btn_load_emps:
                curEmployees = mActivity.getContentResolver().query(Tables.Employees.CONTENT_URI, Tables.Employees.PROJECTION, null, null, null);
                adapterEmps.mCursor = curEmployees;
                adapterEmps.notifyDataSetChanged();
                break;
            case R.id.btn_load_shifts:
                curShifts = mActivity.getContentResolver().query(Tables.Shifts.CONTENT_URI, Tables.Shifts.PROJECTION, null, null, null);
                adapterShifts.mCursor = curShifts;
                adapterShifts.notifyDataSetChanged();
                break;
        }
    }

    private void insertEmployees(){
        final Handler hnd = new Handler(mActivity.getMainLooper()){
            @Override
            public void handleMessage( Message msg){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnReloadEmployees.performClick();
                    }
                });
            }
        };

        new Thread(){
            public void run(){
                for(int i = 0; i < 10; i++){
                    Employee emp = new Employee();
                    emp.setFirst_name("Imenko" + i);
                    emp.setLast_name("Prezimenovic" + i);
                    emp.setAbout("Blabbla_" + i);
                    mActivity.getContentResolver().insert( Tables.Employees.CONTENT_URI, emp.toCValues() );
//                    dbThread.insert( Tables.Employees.CONTENT_URI, emp.toCValues() );
                }
//                hnd.handleMessage(new Message());
                hnd.sendEmptyMessage(0);
            }
        }.start();
    }

    private void insertShifts(){
        final Handler hnd = new Handler(mActivity.getMainLooper()){
            @Override
            public void handleMessage( Message msg){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnReloadShifts.performClick();
                    }
                });
            }
        };

        new Thread(){
            public void run(){
                for(int i = 0; i < 10; i++){
                    Shift sht = new Shift();
                    sht.setName("Shifta" + i);
                    sht.setStart("2014-01-1" + i);
                    sht.setEnd("2014-01-1" + i);
                    mActivity.getContentResolver().insert( Tables.Shifts.CONTENT_URI, sht.toCValues() );
//                    dbThread.insert( Tables.Shifts.CONTENT_URI, sht.toCValues() );
                }
//                hnd.handleMessage(new Message());
                hnd.sendEmptyMessage(0);
            }
        }.start();
    }


    private class EmployeeCursorAdapter extends SimpleCursorAdapter{
        private int mColID;
        private int mColObject;
        private Gson gsonFormatter;
        public Cursor mCursor;
        Context mCtx;

        public EmployeeCursorAdapter(Context context, Cursor c) {
            super(context, R.layout.employee_item, c,
                    Tables.Employees.PROJECTION,
                    new int[] {R.id.txt_id, R.id.txt_value_1},
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            mCtx = context;
            mCursor = c;
            gsonFormatter = new Gson();
            if( mCursor != null ){
                mColObject = mCursor.getColumnIndex(Tables.Employees.OBJECT);
                mColID = mCursor.getColumnIndex(Tables.Employees.ID);
            }
        }

        @Override
        public int getCount(){
            if( mCursor != null ){
                return mCursor.getCount();
            }else{
                return 0;
            }
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup){
            LstItemHolder lstItemHolder;

            if ( view == null ){
                view = mActivity.getLayoutInflater().inflate(R.layout.employee_item, viewGroup, false);
                lstItemHolder = new LstItemHolder();
                lstItemHolder.txtID = (TextView) view.findViewById(R.id.txt_id);
                lstItemHolder.txtValue1 = (TextView) view.findViewById(R.id.txt_value_1);
                lstItemHolder.txtValue2 = (TextView) view.findViewById(R.id.txt_value_2);
                lstItemHolder.txtValue3 = (TextView) view.findViewById(R.id.txt_value_3);
                view.setTag(lstItemHolder);
            } else {
                lstItemHolder = (LstItemHolder) view.getTag();
            }

            if( getCount() > 0 ){
                mCursor.moveToPosition(position);

                String object = mCursor.getString(mColObject);
                Employee employee = gsonFormatter.fromJson(object, Employee.class);

                lstItemHolder.txtID.setText( String.valueOf( mCursor.getInt( mColID ) ) );
                lstItemHolder.txtValue1.setText( employee.getFirst_name() );
                lstItemHolder.txtValue2.setText( employee.getLast_name() );
                lstItemHolder.txtValue3.setText( employee.getAbout() );
            }else{
                lstItemHolder.txtID.setText( "" );
                lstItemHolder.txtValue1.setText( "NO EMPLYEES" );
                lstItemHolder.txtValue2.setText( "" );
                lstItemHolder.txtValue3.setText( "" );
            }

            return view;
        }
    }


    private class ShiftCursorAdapter extends SimpleCursorAdapter{
        private int mColID;
        private int mColObject;
        private Gson gsonFormatter;
        public Cursor mCursor;
        Context mCtx;

        public ShiftCursorAdapter(Context context, Cursor c) {
            super(context, R.layout.employee_item, c,
                    Tables.Shifts.PROJECTION,
                    new int[] {R.id.txt_id, R.id.txt_value_1},
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            mCtx = context;
            mCursor = c;
            gsonFormatter = new Gson();
            if( mCursor != null ){
                mColObject = mCursor.getColumnIndex(Tables.Shifts.OBJECT);
                mColID = mCursor.getColumnIndex(Tables.Shifts.ID);
            }
        }

        @Override
        public int getCount(){
            if( mCursor != null ){
                return mCursor.getCount();
            }else{
                return 0;
            }
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup){
            LstItemHolder lstItemHolder;
            if ( view == null ){
                view = mActivity.getLayoutInflater().inflate(R.layout.employee_item, viewGroup, false);
                lstItemHolder = new LstItemHolder();
                lstItemHolder.txtID = (TextView) view.findViewById(R.id.txt_id);
                lstItemHolder.txtValue1 = (TextView) view.findViewById(R.id.txt_value_1);
                lstItemHolder.txtValue2 = (TextView) view.findViewById(R.id.txt_value_2);
                lstItemHolder.txtValue3 = (TextView) view.findViewById(R.id.txt_value_3);
                view.setTag(lstItemHolder);
            } else {
                lstItemHolder = (LstItemHolder) view.getTag();
            }

            if( getCount() > 0 ){
                mCursor.moveToPosition(position);

                String object = mCursor.getString(mColObject);
                Shift shift = gsonFormatter.fromJson(object, Shift.class);

                lstItemHolder.txtID.setText( String.valueOf( mCursor.getInt( mColID ) ) );
                lstItemHolder.txtValue1.setText( shift.getName() );
                lstItemHolder.txtValue2.setText( shift.getStart() );
                lstItemHolder.txtValue3.setText( shift.getEnd() );
            }else{
                lstItemHolder.txtID.setText("");
                lstItemHolder.txtValue1.setText( "NO SHIFTS" );
                lstItemHolder.txtValue2.setText( "" );
                lstItemHolder.txtValue3.setText( "" );
            }

            return view;
        }
    }

    private static class LstItemHolder{
        TextView txtID;
        TextView txtValue1;
        TextView txtValue2;
        TextView txtValue3;
    }
}
