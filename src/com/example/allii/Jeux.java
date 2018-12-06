package com.example.allii;

/**
 * Created by kahina on 04/12/2017.
 */

public class Jeux {

    // pour la table sauvgarde

    private int[][] matrice;
    private int nbtimer,nbmove,level,id;
    private String name;
    public Jeux(){}

    public Jeux(int id, String name,int nbtimer, int nbmove, int level,int[][] matrice)
    {
        this.id=id;
        this.matrice = matrice;
        this.nbtimer = nbtimer;
        this.nbmove = nbmove;
        this.level = level;
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

    public int[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(int[][] matrice) {
        this.matrice = matrice;
    }

    public int getNbtimer() {
        return nbtimer;
    }

    public void setNbtimer(int nbtimer) {
        this.nbtimer = nbtimer;
    }

    public int getNbmove() {
        return nbmove;
    }

    public void setNbmove(int nbmove) {
        this.nbmove = nbmove;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
