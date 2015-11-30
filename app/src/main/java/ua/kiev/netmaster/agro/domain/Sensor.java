package ua.kiev.netmaster.agro.domain;

/**
 * Created by ПК on 27.11.2015.
 */
public class Sensor {
    String id;
    String sensor_id;
    String type;
    String value;
    String created;
    String zone_id;

    public Sensor() {
    }

    public Sensor(String id, String sensor_id, String type, String value, String created, String zone_id) {
        this.id = id;
        this.sensor_id = sensor_id;
        this.type = type;
        this.value = value;
        this.created = created;
        this.zone_id = zone_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id='" + id + '\'' +
                ", sensor_id='" + sensor_id + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", created='" + created + '\'' +
                ", zone_id='" + zone_id + '\'' +
                '}';
    }
}

