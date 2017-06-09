/**
 * Created by benmerzoukah on 06/06/2017.
 */

const state = {

  typeRepart : [
    {
      code : 'OEUVRE',
      libelle : 'Oeuvre'
    },
    {
      code : 'AYANT_DROIT',
      libelle : 'Ayant droit'
    }

  ],

  rions : [],

  statutProgramme : [
    {
      "code" : 'EN_COURS',
      "libelle" : 'En cours'
    },
    {
      "code" : 'VALIDE',
      "libelle" : 'Validé'
    },
    {
      "code": "AFFECTE",
      "libelle": 'Affecté',
    },
    {
      "code": "ABANDONNE",
      "libelle": 'Abandonné'
    },
    {
      "code": "CREE",
      "libelle": 'Crée'
    }

  ]
}

const mutations = {
  'SET_RIONS' (state, rions) {
    state.rions = rions;
  },
};

const actions = {
};

const getters = {
  statutProgramme: state => {
    return state.statutProgramme;
  },

  typeRepart: state => {
    return state.typeRepart;
  },

  rions : state => {
    var options = state.rions.slice();
    options.unshift({id : 'ALL', value: 'Toutes'});
    return options;
  }


};

export default {
  state,
  mutations,
  actions,
  getters
};

