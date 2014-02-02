package com.steewsc.testing.models;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.steewsc.testing.db.Tables;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Comparator;

/**
 * Created by
 * Stevica Trajanovic
 * stevica@shiftplanning.com
 * on 10/3/13.
 */
public class Employee extends BasicModel implements Comparator<Employee>, Comparable<Employee>{
    private long id;
    private long account_id;
    private long company_id;
    private long location_id;
    private long role_id;
    private String first_name;
    private String last_name;
    private String account_first_name;
    private String account_last_name;
    private String display_name;
    private String about;
    private int status;
    private String created_at;
    private String deleted_at;
    private String updated_at;

    public Employee(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAccount_first_name() {
        return account_first_name;
    }

    public void setAccount_first_name(String account_first_name) {
        this.account_first_name = account_first_name;
    }

    public String getAccount_last_name() {
        return account_last_name;
    }

    public void setAccount_last_name(String account_last_name) {
        this.account_last_name = account_last_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentValues toCValues(){
        ContentValues values =  new ContentValues();
        String object = new Gson().toJson(this);
        values.put(Tables.Employees.EMPLOYEE_ID, this.getId());
        values.put(Tables.Employees.COMPANY_ID, this.getCompany_id());
        values.put(Tables.Employees.OBJECT, object);
        return values;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getCreated_at() {
        return created_at;
    }
    @Override
    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject result = new JSONObject();

         try {
            result.put( "employee_id", this.getId() );
            result.put( "location_id", this.getLocation_id() );
            result.put( "role_id", this.getRole_id() );
            result.put( "account_id", this.getAccount_id() );
            result.put( "company_id", this.getCompany_id() );
            result.put( "first_name", this.getFirst_name() );
            result.put( "last_name", this.getLast_name() );
            result.put( "account_first_name", this.getAccount_first_name() );
            result.put( "account_last_name", this.getAccount_last_name() );
            result.put( "display_name", this.getDisplay_name() );
            result.put( "about", this.getAbout() );
            result.put( "status", this.getStatus() );
            result.put( "created_at", this.getCreated_at() );
            result.put( "deleted_at", this.getDeleted_at() );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public JSONObject forQueryString() {
        return null;
    }


    @Override
    public String toString(){
//        String result = toJSONObject().toString();
//        return result;
        return getFirst_name() + " " + getLast_name();
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Employee another) {
        return ( this.toString().toUpperCase() ).compareTo( another.toString().toUpperCase() );
    }

    /**
     * Compares the two specified objects to determine their relative ordering. The ordering
     * implied by the return value of this method for all possible pairs of
     * {@code (lhs, rhs)} should form an <i>equivalence relation</i>.
     * This means that
     * <ul>
     * <li>{@code compare(a, a)} returns zero for all {@code a}</li>
     * <li>the sign of {@code compare(a, b)} must be the opposite of the sign of {@code
     * compare(b, a)} for all pairs of (a,b)</li>
     * <li>From {@code compare(a, b) > 0} and {@code compare(b, c) > 0} it must
     * follow {@code compare(a, c) > 0} for all possible combinations of {@code
     * (a, b, c)}</li>
     * </ul>
     *
     * @param lhs an {@code Object}.
     * @param rhs a second {@code Object} to compare with {@code lhs}.
     * @return an integer < 0 if {@code lhs} is less than {@code rhs}, 0 if they are
     * equal, and > 0 if {@code lhs} is greater than {@code rhs}.
     * @throws ClassCastException if objects are not of the correct type.
     */
    @Override
    public int compare(Employee lhs, Employee rhs) {
        return lhs.toString().toUpperCase().compareToIgnoreCase(rhs.toString().toUpperCase());
    }
}
