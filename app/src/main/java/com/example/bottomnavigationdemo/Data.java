package com.example.bottomnavigationdemo;
import java.util.HashMap;
import java.util.Map;

public class Data {
    public static String titre;
    public static String contenu;
    public static int jour;
    public static String mois;
    public static String annee;

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

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("titre", titre);
        result.put("contenu", contenu);
        result.put("jour", jour);
        result.put("mois", mois);
        result.put("annee", annee);
        return result;
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
