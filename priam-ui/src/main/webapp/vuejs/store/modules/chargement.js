const state = {
  famille: {'COPIEPRIV':'Copie Privee'},
  typeUtilisation : {'COPRIVSON':'Copie Privee Sono'},
  familleOptions : {'COPIEPRIV':'Copie Privee'},
  typeUtilisationOptions :{'COPRIVSON':'Copie Privee Sono'},
  familleTypeUtilMap : {"COPIEPRIV":["COPRIVSON","CPRIVAUDPL","CPRIVAUDV","CPRIVSONPH","CPRIVSONRD"],"FDSVAL":["PRIME","VALORIS"],"CMS":["ENCOURG"]},
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
      "color"  : '',
      "checked" : true
    },
    {
      "code": "ABONDONNE",
      "libelle": 'Abondonné',
      "color"  : '',
      "checked" : false
    }

  ]
};

const mutations = {
  'SET_LIBELLE_FAMILLE' (state, famille) {
    state.famille = famille;
    state.familleOptions =famille;
  },

  'SET_LIBELLE_TYPE_UTILSATION' (state, typeUtilisation) {
    state.typeUtilisation = typeUtilisation;
    state.typeUtilisationOptions =typeUtilisation;
  },

  'SET_FAMILLE_TYPE_UTILSATION_MAP' (state, data) {
    state.familleTypeUtilMap = data;
  },

  'CHANGE_TYPE_UTILSATION_LIST' (state, familleCode) {
    if(familleCode && familleCode != 'ALL') {
      state.typeUtilisationOptions = state.familleTypeUtilMap[familleCode];
    } else {
      state.typeUtilisationOptions = state.typeUtilisation;
    }

  }

};

const actions = {
  loadTypeUtilisation : ({commit}, familleCode) => {
    commit('CHANGE_TYPE_UTILSATION_LIST', familleCode);
  }
};

const getters = {
  famille: state => {
    return state.familleOptions;
  },

  typeUtilisation: state => {
    return state.typeUtilisationOptions;
  },

  statut: state => {
    return state.statut;
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

