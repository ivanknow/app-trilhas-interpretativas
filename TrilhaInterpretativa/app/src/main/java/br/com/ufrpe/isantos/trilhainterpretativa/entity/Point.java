package br.com.ufrpe.isantos.trilhainterpretativa.entity;

import java.util.List;

/**
 * Created by ivan on 22/06/2017.
 */

public class Point {
    private long id;
    private Local local;
    private String desc;
    private List<Image> images;

    public Point() {
    }

    public Point(long id, Local local, String desc, List<Image> images) {
        this.id = id;
        this.local = local;
        this.desc = desc;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
