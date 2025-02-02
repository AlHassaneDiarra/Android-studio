package ca.collegelacite.chimie;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ElementPeriodic implements Comparable, Serializable {
    private String  nom;
    private Double  masseAtomique;
    private Double  densité;
    private Integer numéro;
    private Integer période;
    private String  phase;
    private String  wikiUrl;
    private String  imageSpectraleUrl;

    public ElementPeriodic() {
        setNom(null);
        setMasseAtomique(null);
        setDensité(null);
        setNuméro(null);
        setPériode(null);
        setPhase(null);
        setWikiUrl(null);
        setImageSpectraleUrl(null);
    }

    public ElementPeriodic(String nom, Double masseAtomique, Double densité, Integer numéro, Integer période,
                           String phase, String wikiUrl, String imageSpectraleUrl) {
        setNom(nom);
        setMasseAtomique(masseAtomique);
        setDensité(densité);
        setNuméro(numéro);
        setPériode(période);
        setPhase(phase);
        setWikiUrl(wikiUrl);
        setImageSpectraleUrl(imageSpectraleUrl);
    }

    // Fonction de comparaison utilisée pour trier la liste de éléments après la
    // lecture du JSON.
    @Override
    public int compareTo(Object autreÉlément) {
        return this.getNuméro().compareTo(((ElementPeriodic)autreÉlément).getNuméro());
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getMasseAtomique() {
        return masseAtomique;
    }

    public void setMasseAtomique(Double masseAtomique) {
        this.masseAtomique = masseAtomique;
    }

    public Double getDensité() {
        return densité;
    }

    public void setDensité(Double densité) {
        this.densité = densité;
    }

    public Integer getNuméro() {
        return numéro;
    }

    public void setNuméro(Integer numéro) {
        this.numéro = numéro;
    }

    public Integer getPériode() {
        return période;
    }

    public void setPériode(Integer période) {
        this.période = période;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public String getImageSpectraleUrl() {
        return imageSpectraleUrl;
    }

    public void setImageSpectraleUrl(String imageSpectraleUrl) {
        this.imageSpectraleUrl = imageSpectraleUrl;
    }

    // Retourne une chaîne décrivant l'élément chimique.
    @Override
    public String toString() {
        return "" + this.getNuméro() + " - " + this.getNom();
    }

    // Désérialiser une liste d'éléments d'un fichier JSON.
    public static ArrayList<ElementPeriodic> lireDonnées(Context ctx) {
        final String nomFichier = "elements.json";

        ArrayList<ElementPeriodic> liste = new ArrayList<>();
        try {
            // Charger les données dans la liste.
            String jsonString = lireJson(nomFichier, ctx);
            JSONObject json   = new JSONObject(jsonString);
            JSONArray élément    = json.getJSONArray("elements");

            // Lire chaque élément du fichier.
            for(int i = 0; i < élément.length(); i++){
                ElementPeriodic p = new ElementPeriodic();

                p.nom     = élément.getJSONObject(i).getString("nom");
                p.masseAtomique = élément.getJSONObject(i).getDouble("masse_atomique");
                p.densité = élément.getJSONObject(i).getDouble("densite");
                p.numéro = élément.getJSONObject(i).getInt("numero");
                p.période = élément.getJSONObject(i).getInt("periode");
                p.phase = élément.getJSONObject(i).getString("phase");
                p.wikiUrl = élément.getJSONObject(i).getString("source");
                p.imageSpectraleUrl = élément.getJSONObject(i).getString("image_spectrale");

                liste.add(p);
            }
        } catch (JSONException e) {
            // Une erreur s'est produite (on la journalise).
            e.printStackTrace();
            return null;
        }

        // Trier la liste.
        Collections.sort(liste);

        return liste;
    }

    // Retourne une balise lue d'un fichier JSON.
    private static String lireJson(String nomFichier, Context ctx) {
        String json = null;

        try {
            InputStream is = ctx.getAssets().open(nomFichier);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            // Une erreur s'est produite (on la journalise).
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
