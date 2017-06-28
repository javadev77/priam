const state = {
  famille: [],
  typeUtilisation : [],
  familleOptions  : [],
  familleOptions_vide : [],
  typeUtilisationOptions : [],
  typeUtilisationOptions_vide : [],
  familleTypeUtilMap : {},
  statut : [
    {
      "code" : 'EN_COURS',
      "libelle" : 'En cours',
      "color"  : 'orange',
      "checked" : true
    },
    {
      "code" : 'CHARGEMENT_OK',
      "libelle" : 'Chargement OK',
      "color"  : 'green',
      "checked" : true
    },
    {
      "code": "CHARGEMENT_KO",
      "libelle": 'Chargement KO',
      "color"  : 'red',
      "checked" : true
    },
    {
      "code": "AFFECTE",
      "libelle": 'Affecté',
      "color"  : 'grey',
      "checked" : false
    },
    {
      "code": "ABANDONNE",
      "libelle": 'Abandonné',
      "color"  : '#4b77be',
      "checked" : false
    }

  ]
};

const mutations = {
  'SET_LIBELLE_FAMILLE' (state, famille) {
      state.famille = famille;
      state.familleOptions=famille.slice();
      state.familleOptions.unshift({'id' :'ALL', "value" : "Toutes"});

      state.familleOptions_vide=famille.slice();
  },

  'SET_LIBELLE_TYPE_UTILSATION' (state, typeUtilisation) {
      state.typeUtilisation = typeUtilisation.slice();
      state.typeUtilisationOptions_vide = typeUtilisation.slice();
      state.typeUtilisationOptions = [{'id' :'ALL', "value" : "Tous"}];
  },

  'SET_FAMILLE_TYPE_UTILSATION_MAP' (state, data) {
      state.familleTypeUtilMap = data;
  },

  'CHANGE_TYPE_UTILSATION_LIST' (state, familleCode) {
    if(familleCode && familleCode.id !== 'ALL') {

      state.typeUtilisationOptions = state.familleTypeUtilMap[familleCode.id].slice();
      state.typeUtilisationOptions.unshift({'id' :'ALL', "value" : "Tous"});

    } else {
        state.typeUtilisationOptions = [{'id' :'ALL', "value" : "Tous"}];
    }

  },

  'CHANGE_TYPE_UTILSATION_LIST_VIDE' (state, familleCode) {
    if(familleCode != null) {
      state.typeUtilisationOptions_vide = state.familleTypeUtilMap[familleCode.id].slice();

    }

  },

  'CHANGE_STATUT_VALUE' (state, statutCode) {
      if(statutCode != null) {

        for(var stat in state.statut) {
          for(var i in statutCode) {
            if(state.statut[stat].code === statutCode[i]) {
              state.statut[stat].checked = true;
            }
          }

        }

      }
  }

};

const actions = {
  loadTypeUtilisation : ({commit}, familleCode) => {
    commit('CHANGE_TYPE_UTILSATION_LIST', familleCode);
  },

  loadTypeUtilisationVide : ({commit}, familleCode) => {
    commit('CHANGE_TYPE_UTILSATION_LIST_VIDE', familleCode);
  },

  changeStatut : ({commit}, statutCode) => {
    commit('CHANGE_STATUT_VALUE', statutCode);
  }
};

const getters = {
  familleOptions: state => {
    return state.familleOptions;
  },

  familleOptionsVide: state => {
    return state.familleOptions_vide;
  },

  famille: state => {
    return state.famille;
  },

  typeUtilisationOptions: state => {
    return state.typeUtilisationOptions;
  },

  typeUtilisationOptionsVide: state => {
    return state.typeUtilisationOptions_vide;
  },

  typeUtilisation: state => {
    return state.typeUtilisation;
  },

  statut: state => {
    return state.statut.slice();
  },

  familleTypeUtilMap: state => {
    return state.statut;
  },
};

export default {
  state,
  mutations,
  actions,
  getters
};

