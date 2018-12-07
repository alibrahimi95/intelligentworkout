package com.example.allii;

/**
 * Created by Sabrina on 01/12/2018.
 */

public class Score {
  private   int id;
  private   String name;
  private int  nbdeplacement,temps,nbsecdep;
    public Score(){}
    public Score(int id, String name, int nbdeplacement, int temps, int nbsecdep) {
        this.id = id;
        this.name = name;
        this.nbdeplacement = nbdeplacement;
        this.temps = temps;
        this.nbsecdep = nbsecdep;
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

    public int getNbdeplacement() {
        return nbdeplacement;
    }

    public void setNbdeplacement(int nbdeplacement) {
        this.nbdeplacement = nbdeplacement;
        if (this.nbdeplacement!=0)
        this.nbsecdep=temps/this.nbdeplacement;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
        if (this.nbdeplacement!=0)
        this.nbsecdep=temps/this.nbdeplacement;
    }

    public int getNbsecdep() {
        return nbsecdep;
    }

    public void setNbsecdep(int nbsecdep) {
        this.nbsecdep = nbsecdep;
    }
}
