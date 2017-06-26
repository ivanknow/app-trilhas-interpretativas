package br.com.ufrpe.isantos.trilhainterpretativa.entity;

import java.util.List;

/**
 * Created by ivan on 22/06/2017.
 */

public class Point {
    private long id;
    private Local local;
    private String title;

    private String desc;
    private List<Image> images;

    public Point() {
    }

    public Point(long id, Local local, String title, String desc, List<Image> images) {
        this.id = id;
        this.local = local;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return id == point.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
