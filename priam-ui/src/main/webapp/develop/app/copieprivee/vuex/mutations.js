/**
 * Created by benmerzoukah on 08/11/2017.
 */

export default {
  'SET_PROG_EN_COURS_SELECTION' (state, programme) {
    state.programmeEnSelection = programme;
  },

  'MAJ_LISTE_UTILISATEUR' (state, listeUtlisateur) {
    state.listeUtilisateur = listeUtlisateur;
  }
}
