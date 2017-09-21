package trios.vrcontroller.vrcontroller.model;

/**
 * Created by kimiboo on 2017-09-21.
 */

public class GeoInfo {

    private int id;
    private String longitube;
    private String latitube;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongitube() {
        return longitube;
    }

    public void setLongitube(String _long) {
        this.longitube = _long;
    }

    public String getLatitube() {
        return latitube;
    }

    public void setLatitube(String _lat) {
        this.latitube = _lat;
    }
}
