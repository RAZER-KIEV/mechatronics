package ua.kiev.netmaster.agro.domain;

/**
 * Created by ПК on 24.11.2015.
 */
public class Zone implements Comparable{
    private String id;
    private String owner_id;
    private String name;
    private String token;
    private String lat;
    private String lng;
    private String map_zoom;

    public Zone() {
    }

    public Zone(String id, String owner_id, String name, String token, String lat, String lng, String map_zoom) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.token = token;
        this.lat = lat;
        this.lng = lng;
        this.map_zoom = map_zoom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMap_zoom() {
        return map_zoom;
    }

    public void setMap_zoom(String map_zoom) {
        this.map_zoom = map_zoom;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id='" + id + '\'' +
                ", owner_id='" + owner_id + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", map_zoom='" + map_zoom + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        int  id = Integer.parseInt(((Zone)o).getId());
        int thisid = Integer.parseInt(this.getId());
        return thisid-id;
    }
}
