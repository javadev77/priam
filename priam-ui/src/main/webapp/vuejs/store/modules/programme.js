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
};

const actions = {
};

const getters = {
  statutProgramme: state => {
    return state.statutProgramme;
  },

  typeRepart: state => {
    return state.typeRepart;
  }


};

export default {
  state,
  mutations,
  actions,
  getters
};

