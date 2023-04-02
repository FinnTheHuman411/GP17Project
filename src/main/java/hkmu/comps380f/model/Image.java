package hkmu.comps380f.model;

import jakarta.persistence.*;

@Entity
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int id;

    private String filename;
    private byte[] data;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //@Column(insertable = false, updatable = false)
    private String username;

    public Image(){}

    public Image(String filename, byte[] data, String username){
        this.filename = filename;
        this.data = data;
        this.username = username;
    }
}


