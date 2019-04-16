/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Entities;

import java.util.List;

/**
 *
 * @author frederikhelth
 */
public class Gallery {
    
    private int id;
    private List<Object> images;

    public Gallery() {
    }

    public Gallery(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }
    
}
