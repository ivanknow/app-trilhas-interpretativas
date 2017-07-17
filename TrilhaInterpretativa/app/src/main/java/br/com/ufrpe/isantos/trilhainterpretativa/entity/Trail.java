package br.com.ufrpe.isantos.trilhainterpretativa.entity;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 22/06/2017.
 */

public class Trail {
    private long id;
    private String title;
    private String descr;
    private Date date;
    private List<Point> points;

    public Trail() {
        points = new ArrayList<Point>();
        title = "No title";
}

    public Trail(long id, String title, Date date, List<Point> points) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.points = points;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
