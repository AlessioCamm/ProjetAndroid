package com.example.alessio.projetfinal.BDD;


public class User {

    private int id;
    private String email;
    private String password;
    private String nom;
    private String prenom;

    public User(){}

    public User(String e, String p, String n, String pr){

        this.email = e;
        this.password = p;
        this.nom = n;
        this.prenom = pr;

    }

    public int getId() {
        return id;
    }
    public void setId(int i) {
        this.id = i;
    }

    public String getEmail() {return email;}
    public void setEmail(String e) {this.email = e;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String p) {
        this.password = p;
    }

    public String getNom() {return nom; }
    public void setNom(String n) {this.nom = n; }

    public String getPrenom() {return prenom; }
    public void setPrenom(String pr) {this.prenom = pr; }


    @Override
    public String toString() {
        return "ID : " +
                Integer.toString(id) + "\n"
                + "Email : " + email + "\n"
                + "Password : " + password + "\n"
                + "Nom : " + nom + "\n"
                + "Prenom : " + prenom + "\n";
    }
}
