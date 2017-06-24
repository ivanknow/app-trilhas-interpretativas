package br.com.ufrpe.isantos.trilhainterpretativa.entity;

import java.util.List;

/**
 * Created by ivan on 22/06/2017.
 */

public class Point {
    private long id;
    private Local local;
    private String descricao;
    private List<Image> images;

    public Point() {
    }

    public Point(long id, Local local, String descricao, List<Image> images) {
        this.id = id;
        this.local = local;
        this.descricao = descricao;
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
        return "Point{" +
                "id=" + id +
                ", local=" + local +
                ", descricao='" + descricao + '\'' +
                ", images=" + images +
                '}';
    }
}
