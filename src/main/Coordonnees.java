package main;

public class Coordonnees {
	
	/**
	 * Numéro de la ligne.
	 */
	public int ligne;
	
	/**
	 * Numéro de la colonne.
	 */
	public int colonne;

	/**
	 * Nombre de lignes du plateau.
	 */
	static final int NB_LIGNES = 3;
	/**
	 * Nombre de colonnes du plateau.
	 */
	static final int NB_COLONNES = 3;
	
	/**
	 * Constructeur prenant les numéros de ligne/colonne en paramètre.
	 * 
	 * @param numLigne numéro de la ligne
	 * @param numColonne numéro de la colonne
	 */
	public Coordonnees(int numLigne, int numColonne) {
		ligne = numLigne;
		colonne = numColonne;
	}

	/**
	 * Indique si ces coordonnées sont dans le plateau.
	 * @return vrai ssi ces coordonnées sont dans le plateau
	 */
	boolean estDansPlateau() {
		return 0 <= ligne && ligne < NB_LIGNES 
				&& 0 <= colonne && colonne < NB_COLONNES;
	}
	
	
	/**
	 * Indique si une autre case, passée en paramètre, est sur la même ligne.
	 * 
	 * @param autre les coordonnées de l'autre case
	 * @return vrai ssi les cases sont sur la même ligne
	 */
	boolean memeLigne(Coordonnees autre) {
		return this.ligne == autre.ligne;
	}

	/**
	 * Indique si une autre case, passée en paramètre, est sur la même colonne.
	 * 
	 * @param autre les coordonnées de l'autre case
	 * @return vrai ssi les cases sont sur la même colonne
	 */
	boolean memeColonne(Coordonnees autre) {
		return this.colonne == autre.colonne;
	}

	/**
     * Test d'égalité entre coordonnées.
     * 
     * @param obj les coordonnées avec lesquelles comparer ces coordonnées
     * @return vrai ssi les coordonnées représentent la même case
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordonnees other = (Coordonnees) obj;
        if (this.ligne != other.ligne) {
            return false;
        }
        if (this.colonne != other.colonne) {
            return false;
        }
        return true;
    }


}
