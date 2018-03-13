package com.byethost12.kitm.mobiliaplikacija;

/**
 * Created by mokytojas on 2018-02-05.
 */

public class Pokemonas {
    private int id;
    private String user;
    private String name;
    private double weight;
    private double height;
    private String cp;
    private String abilities;
    private String type;

    //Naudojamas cloudui
    public Pokemonas(int id,String user, String name, double weight, double height, String cp, String abilities, String type) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.cp = cp;
        this.abilities = abilities;
        this.type = type;
    }

    //JUnit testavimui
    public Pokemonas(String name, double weight, double height, String cp, String abilities, String type) {
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

    public String getUser() {return user;}

    public void setUser(String user) {this.user = user;}

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pokemonas) {
            Pokemonas pokemonas = (Pokemonas) o;
            boolean name = pokemonas.getName().equals(getName());
            boolean weight = Double.compare(pokemonas.getWeight(),getWeight())==0;
            boolean height = Double.compare(pokemonas.getHeight(),getHeight())==0;
            boolean cp = pokemonas.getCp().equals(getCp());
            boolean abilities = pokemonas.getAbilities().equals(getAbilities());
            boolean type = pokemonas.getType().equals(getType());
            return (name
                    &&weight
                    &&height
                    &&cp
                    &&abilities
                    &&type);
        }
    return false;
    }

}
