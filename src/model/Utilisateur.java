package model;

public class Utilisateur {
    private String username;
    private String motDePasse;
    private String email;

    public Utilisateur(String username, String motDePasse, String email) {
        this.username = username;
        this.motDePasse = motDePasse;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public boolean verifierIdentifiants(String username, String motDePasse) {
        return this.username.equals(username) && this.motDePasse.equals(motDePasse);
    }

    public static boolean verifierFormatEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean estValide() {
        return username != null && !username.isEmpty()
            && motDePasse != null && !motDePasse.isEmpty()
            && verifierFormatEmail(email);
    }
}