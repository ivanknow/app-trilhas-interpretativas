package br.com.ufrpe.isantos.trilhainterpretativa.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

/**
 * Created by ivan on 22/06/2017.
 */

public class Point implements Serializable {
    private long id;
    private Local local;
    private String title;
    private Date checkpoint;

    private String descr;
    private List<Image> images;

    public Point() {
    }

    public Point(long id, Local local, String title, String descr, List<Image> images) {
        this.id = id;
        this.local = local;
        this.title = title;
        this.descr = descr;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        if(checkpoint!=null){


            return title+"("+checkpoint.toString()+")";
        }

        return title;
    }

    @Override
    public boolean equals(Object o) {
        Point other = (Point) o;
       if(this.getTitle().equals(other.getTitle())     )
        return true;
        return false;

    }

    public Date getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Date checkpoint) {
        this.checkpoint = checkpoint;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
