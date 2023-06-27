package com.example.bottomnavigationdemo;

public class Data {
    private String titre;
    private String contenu;
    private int jour;
    private String mois;
    private String annee;

    // Ajoutez les getters et setters appropriés pour les champs

    // Exemple :
    public String getTitre() {
        return titre;
    }
    public String getContenu() {
        return contenu;
    }
    public int getJour() {
        return jour;
    }
    public String getMois(){
        return mois;
    }
    public String getAnnee(){
        return annee;
    }

    public void setMois(String mois){
        this.mois=mois;
    }
    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public Data(String titre, String contenu, int jour, String mois, String annee){
        this.titre=titre;
        this.contenu=contenu;
        this.jour=jour;
        this.mois=mois;
        this.annee=annee;

    }
    // ...
}
