const state = {
  famille: {"COPIEPRIV":"Copie Privée","FDSVAL":"Valorisation","CMS":"CMS"},
  typeUtilisation : {"CPRIVSONPH":"Copie privée sonore Phono","PRIME":"Prime","CPRIVAUDPL":"Copie Privée Audiovisuel - Part Littéraire","COPRIVSON":"Copie Privée Sonore","CPRIVSONRD":"Copie Privée Sonore radio","ENCOURG":"Encouragement","VALORIS":"Fonds de valorisation","CPRIVAUDV":"Copie Privée Audiovisuelle"},
  familleOptions  : {"COPIEPRIV":"Copie Privée","FDSVAL":"Valorisation","CMS":"CMS"},
  typeUtilisationOptions : {} ,
  familleTypeUtilMap : {"COPIEPRIV":{"CPRIVSONPH":"Copie privée sonore Phono","CPRIVAUDPL":"Copie Privée Audiovisuel - Part Littéraire","COPRIVSON":"Copie Privée Sonore","CPRIVSONRD":"Copie Privée Sonore radio","CPRIVAUDV":"Copie Privée Audiovisuelle"},"FDSVAL":{"PRIME":"Prime","VALORIS":"Fonds de valorisation"},"CMS":{"ENCOURG":"Encouragement"}},
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
    state.familleOptions = famille;
  },

  'SET_LIBELLE_TYPE_UTILSATION' (state, typeUtilisation) {
    state.typeUtilisation = typeUtilisation;
    state.typeUtilisationOptions = {};
  },

  'SET_FAMILLE_TYPE_UTILSATION_MAP' (state, data) {
    state.familleTypeUtilMap = data;
  },

  'CHANGE_TYPE_UTILSATION_LIST' (state, familleCode) {
    if(familleCode && familleCode != 'ALL') {
        state.typeUtilisationOptions = state.familleTypeUtilMap[familleCode];
    } else {
        state.typeUtilisationOptions ={};
    }

  }

};

const actions = {
  loadTypeUtilisation : ({commit}, familleCode) => {
    commit('CHANGE_TYPE_UTILSATION_LIST', familleCode);
  }
};

const getters = {
  familleOptions: state => {
    return state.familleOptions;
  },

  famille: state => {
    return state.famille;
  },

  typeUtilisationOptions: state => {
    return state.typeUtilisationOptions;
  },

  typeUtilisation: state => {
    return state.typeUtilisation;
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

