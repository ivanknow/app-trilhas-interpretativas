package br.com.ufrpe.isantos.trilhainterpretativa.entity;

import java.util.List;

/**
 * Created by ivan on 22/06/2017.
 */

public class Point {
    private long id;
    private Local local;
    private String title;

    private String descricao;
    private List<Image> images;

    public Point(long id, Local local, String title, String descricao, List<Image> images) {
        this.id = id;
        this.local = local;
        this.title = title;
        this.descricao = descricao;
        this.images = images;
    }


    public Point() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
