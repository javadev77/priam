/**
 * Created by benmerzoukah on 08/11/2017.
 */

import Vue from 'vue'

export const setCurrentProgrammeEnSelection = ({commit}, programme) => {
  commit('SET_PROG_EN_COURS_SELECTION', programme);
};

export const getUtilisateursByProgramme = ({commit}, numProg) => {

  Vue.http.get(process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/ligneProgramme/utilisateurs?programme='+ numProg)
    .then(response => {
      return response.json();
    })
    .then(data => {
      commit('MAJ_LISTE_UTILISATEUR', data);
    });

};
