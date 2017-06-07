/**
 * Created by benmerzoukah on 06/06/2017.
 */

const state = {

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
  }


};

export default {
  state,
  mutations,
  actions,
  getters
};

