package model;

public class Utilisateur {
    private String nomUtilisateur;
    private String email;
    private String motDePasse;

    /**
     * Constructeur de la classe Utilisateur
     * @param nomUtilisateur Le nom d'utilisateur
     * @param email L'adresse email
     * @param motDePasse Le mot de passe
     */
    public Utilisateur(String nomUtilisateur, String email, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    /**
     * Vérifie les identifiants de connexion
     * @param nomUtilisateur Le nom d'utilisateur saisi
     * @param motDePasse Le mot de passe saisi
     * @return true si les identifiants sont corrects, false sinon
     */
    public boolean verifierIdentifiants(String nomUtilisateur, String motDePasse) {
        return this.nomUtilisateur.equals(nomUtilisateur) && this.motDePasse.equals(motDePasse);
    }

    // Getters et Setters
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "nomUtilisateur='" + nomUtilisateur + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}