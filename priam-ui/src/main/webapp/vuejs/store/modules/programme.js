/**
 * Created by benmerzoukah on 06/06/2017.
 */

const state = {

  allFichiersChecked : true,

  typeRepart : [
    {
      id : 'OEUVRE',
      value : 'Oeuvre'
    },
    {
      id : 'AYANT_DROIT',
      value : 'Ayant droit'
    }

  ],

  rions : [],

  rionsCreation : [],

  statutProgramme : [
    {
      "code" : 'EN_COURS',
      "libelle" : 'En cours',
      "orderAff" : 3,
      "checked" : false
    },
    {
      "code" : 'VALIDE',
      "libelle" : 'Validé',
      "orderAff" : 4,
      "checked" : false
    },
    {
      "code": "AFFECTE",
      "libelle": 'Affecté',
      "orderAff" : 2,
      "checked" : false
    },
    {
      "code": "ABANDONNE",
      "libelle": 'Abandonné',
      "orderAff" : 7,
      "checked" : false
    },
    {
      "code": "CREE",
      "libelle": 'Créé',
      "orderAff" : 1,
      "checked" : false
    },
    {
      "code": "MIS_EN_REPART",
      "libelle": 'Mis en répartition',
      "orderAff" : 5,
      "checked" : false
    },
    {
      "code": "REPARTI",
      "libelle": 'Réparti',
      "orderAff" : 6,
      "checked" : false
    }

  ]
}

const mutations = {
  'SET_RIONS' (state, rions) {
    state.rions = rions;
  },

  'SET_RIONS_CREATION'(state, rionsCreation) {
    state.rionsCreation = rionsCreation;
  },

  'TOUT_DESACTIVER'(state, value) {
    state.allFichiersChecked = value;
  }
};

const actions = {

  toutDesactiver : ({commit}, value) => {
    commit('TOUT_DESACTIVER', value);
  }
};

const getters = {
  statutProgramme: state => {
    return state.statutProgramme.slice();
  },

  typeRepart: state => {
      var typeRepartOpt = state.typeRepart.slice();
      typeRepartOpt.unshift({id : 'ALL', value: 'Tous'});
      return typeRepartOpt;
  },

  rions : state => {
    var options = state.rions.slice();
    options.unshift({id : 'ALL', value: 'Toutes'});
    return options;
  },

  rionsSaref : state => {
    return state.rions;
  },

  rionsAddProg : state => {
    var options = state.rionsCreation.slice();

    return options;
  },

  modeRepartition : state => {
    return state.typeRepart;
  },

  allFichiersChecked : state => {
    return state.allFichiersChecked;
  }




};

export default {
  state,
  mutations,
  actions,
  getters
};

