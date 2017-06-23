package br.com.ufrpe.isantos.trilhainterpretativa.entity;

/**
 * Created by ivan on 22/06/2017.
 */

public class Image {
    private long id;
    private String src;

    public Image() {
    }

    public Image(long id, String src) {
        this.id = id;
        this.src = src;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
