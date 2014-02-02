package com.steewsc.testing.models;

import android.content.ContentValues;

import com.google.gson.Gson;
import com.steewsc.testing.db.Tables;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Comparator;

/**
 * Created by svetomir on 11/7/13.
 */
public class Shift extends BasicTimeModel implements Comparator<Shift>, Comparable<Shift>, Cloneable {
    private long shift_id;
    private long company_id;
    private long location_id;
    private long position_id;
    private String start;
    private String end;
    private String name;
    private String description;
    private int working;
    private boolean published;
    private long published_by;
    private String published_at;
    private long created_by;
    private String created_at;
    private long updated_by;
    private String updated_at;
    private long deleted_by;
    private String deleted_at;

    public long getShift_id() {
        return shift_id;
    }

    public void setShift_id(long shift_id) {
        this.shift_id = shift_id;
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

    public long getPosition_id() {
        return position_id;
    }

    public String getPositionColor(){
        return "#FFF555";
    }

    public void setPosition_id(long position_id) {
        this.position_id = position_id;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setStart(int start) {
        this.start = String.valueOf( start );
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public int getWorking() {
        return working;
    }

    public void setWorking(int working) {
        this.working = working;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public long getPublished_by() {
        return published_by;
    }

    public void setPublished_by(long published_by) {
        this.published_by = published_by;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(long created_by) {
        this.created_by = created_by;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(long updated_by) {
        this.updated_by = updated_by;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public long getDeleted_by() {
        return deleted_by;
    }

    public void setDeleted_by(long deleted_by) {
        this.deleted_by = deleted_by;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public ContentValues toCValues(){
        ContentValues values = new ContentValues();

        String object = new Gson().toJson( this );
        values.put(Tables.Shifts.SHIFT_ID, this.getShift_id());
        values.put(Tables.Shifts.LOCATION_ID, this.getLocation_id());
        values.put(Tables.Shifts.COMPANY_ID, this.getCompany_id());
        values.put(Tables.Shifts.POSITION_ID, this.getPosition_id());
        values.put(Tables.Shifts.OBJECT, object);

        return values;
    }


    @Override
    public String getStart() {
        return start;
    }

    @Override
    public String getEnd() {
        return end;
    }

    @Override
    public String getName() {
        return "Shift";
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return int:<br>
     * 0 - SHIFT<br>
     * 1 - AVAILABILITY<br>
     * 2 - EVENT<br>
     * 3 - LEAVE<br>
     * 4 - TIMECLOCK<br>
     * 5 - HOLIDAY<br>
     */
    @Override
    public int getItemType() {
        return BasicTimeModel.SHIFT;
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject result = new JSONObject();
        try {
            result.put("shift_id", this.getShift_id());
            result.put("company_id", this.getCompany_id());
            result.put("position_id", this.getPosition_id());
            result.put("location_id", this.getLocation_id());
            result.put("start", this.getStart());
            result.put("end", this.getEnd());
            result.put("name", this.getName());
            result.put("description", this.getDescription());
            result.put("working", this.getWorking());

            result.put("published", this.isPublished());
            result.put("published_by", this.getPublished_by());
            result.put("published_at", this.getPublished_at());
            result.put("created_by", this.getCreated_by());
            result.put("created_at", this.getCreated_at());
            result.put("updated_by", this.getUpdated_by());
            result.put("updated_at", this.getUpdated_at());
            result.put("deleted_by", this.getDeleted_by());
            result.put("deleted_at", this.getDeleted_at());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public long getId() {
        return shift_id;
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
    public JSONObject forQueryString(){
        JSONObject result = new JSONObject();
        try {
            result.put( "position_id", this.position_id );
            result.put( "start", this.start );
            result.put( "end", this.end );
            result.put( "name", this.name );
            result.put( "description", this.description );
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString(){
        String result = toJSONObject().toString();
        return result;
    }

    @Override
    public int compareTo(Shift shift) {
        return ( this.start ).compareTo( shift.start );
    }

    @Override
    public int compare(Shift shift, Shift shift2) {
        return BasicTimeModelSorter.getDateTime( shift.start ) - BasicTimeModelSorter.getDateTime( shift2.start );
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
