package com.steewsc.testing.models;

import org.json.JSONObject;

/**
 * Created by
 * Stevica Trajanovic
 * stevica@shiftplanning.com
 * on 10/2/13.
 */
public abstract class BasicModel {
    public abstract JSONObject toJSONObject();
    public abstract JSONObject forQueryString();

    public abstract long getId();
    public abstract String getCreated_at();
    public abstract String getUpdated_at();
}
