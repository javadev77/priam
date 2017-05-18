import Vue from 'vue';

export const fetchInitData = ({commit}) => {
  Vue.http.get('app/rest/general/libellefamille')
    .then(response => response.json())
    .then(data => {
      if (data) {
        commit('SET_LIBELLE_FAMILLE', data);
      }
    });

  Vue.http.get('app/rest/general/libelletypeutil')
    .then(response => response.json())
    .then(data => {
      if (data) {
        commit('SET_LIBELLE_TYPE_UTILSATION', data);
      }
    });

  Vue.http.get('app/rest/general/familleByTypeUil')
    .then(response => response.json())
    .then(data => {
      if (data) {
        commit('SET_FAMILLE_TYPE_UTILSATION_MAP', data);
      }
    });

};
