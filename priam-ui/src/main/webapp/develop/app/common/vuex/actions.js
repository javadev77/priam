/**
 * Created by benmerzoukah on 08/11/2017.
 */
export const SELECT_PAGE_SIZE  = ({commit}, value) => {
    commit('SELECT_PAGE_SIZE', value);
};

export  const loadTypeUtilisation = ({commit}, familleCode) => {
  commit('CHANGE_TYPE_UTILSATION_LIST', familleCode);
};

export const loadTypeUtilisationVide = ({commit}, familleCode) => {
  commit('CHANGE_TYPE_UTILSATION_LIST_VIDE', familleCode);
};

export const changeStatut = ({commit}, statutCode) => {
  commit('CHANGE_STATUT_VALUE', statutCode);
};

export const toutDesactiver = ({commit}, value) => {
  commit('TOUT_DESACTIVER', value);
}
