package com.byethost12.kitm.mobiliaplikacija;

/**
 * Created by mokytojas on 2018-02-05.
 */

public class Pokemonas {
    private int id;
    private String name;
    private double weight;
    private double height;
    private String cp;
    private String abilities;
    private String type;

    public Pokemonas(int id, String name, double weight, double height, String cp, String abilities, String type) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.cp = cp;
        this.abilities = abilities;
        this.type = type;
    }

    public Pokemonas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Pokemonas{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", cp='" + cp + '\'' +
                ", abilities='" + abilities + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
