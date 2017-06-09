import Vue from 'vue';


export const fetchInitData = ({commit}) => {

  if(process.env.DEBUG_MODE) {
    console.log("DEBUG MODE TRUE");
    var data = JSON.parse('[{"id":"CMS","value":"CMS"},{"id":"COPIEPRIV","value":"Copie Privée"},{"id":"FDSVAL","value":"Valorisation"}]');
    commit('SET_LIBELLE_FAMILLE', data);
    commit('SET_LIBELLE_TYPE_UTILSATION', JSON.parse('[{"id":"COPRIVSON","value":"Copie Privée Sonore"},{"id":"CPRIVAUDPL","value":"Copie Privée Audiovisuel - Part Littéraire"},{"id":"CPRIVAUDV","value":"Copie Privée Audiovisuelle"},{"id":"CPRIVSONPH","value":"Copie privée sonore Phono"},{"id":"CPRIVSONRD","value":"Copie Privée Sonore radio"},{"id":"ENCOURG","value":"Encouragement"},{"id":"PRIME","value":"Prime"},{"id":"VALORIS","value":"Fonds de valorisation"}]'));
    commit('SET_FAMILLE_TYPE_UTILSATION_MAP', JSON.parse('{"COPIEPRIV":[{"id":"COPRIVSON","value":"Copie Privée Sonore"},{"id":"CPRIVAUDPL","value":"Copie Privée Audiovisuel - Part Littéraire"},{"id":"CPRIVAUDV","value":"Copie Privée Audiovisuelle"},{"id":"CPRIVSONPH","value":"Copie privée sonore Phono"},{"id":"CPRIVSONRD","value":"Copie Privée Sonore radio"}],"FDSVAL":[{"id":"PRIME","value":"Prime"},{"id":"VALORIS","value":"Fonds de valorisation"}],"CMS":[{"id":"ENCOURG","value":"Encouragement"}]}'));
    commit('SET_RIONS', JSON.parse('[{"id":"639","value":"639"}]'));

  } else {
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

    Vue.http.get('app/rest/general/rions')
      .then(response => response.json())
      .then(data => {
        if (data) {
          commit('SET_RIONS', data);
        }
      });

  }

};
