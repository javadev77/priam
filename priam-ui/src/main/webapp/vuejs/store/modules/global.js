/**
 * Created by benmerzoukah on 06/06/2017.
 */

const state = {

    pagination : {

      currentPage: {
        type: Number,
        default : 1
      },
      // Total page
      totalPages: Number,
      // Items per page
      itemsPerPage: Number,
      // Total items
      totalItems: Number,
      // Visible Pages
      visiblePages: {
        type: Number,
        default: 5
      }


    },

    mipsaConfig : {},

    libelleUtilisateur : []

}

const mutations = {
  'SELECT_PAGE' (state, page) {
      state.pagination.currentPage = page;
  },
  'SELECT_PAGE_SIZE' (state, pageSize) {
      state.pagination.itemsPerPage = pageSize;
  },

  'SET_LIBELLE_UTILISATEUR' (state, data) {
      state.libelleUtilisateur = data;
  },

  'SET_MIPSA_CONFIG' (state, mipsaConfig) {
    state.mipsaConfig = mipsaConfig;
  },


};

const actions = {

  SELECT_PAGE_SIZE  : ({commit}, value) => {
      commit('SELECT_PAGE_SIZE', value);
  }


};

const getters = {
  itemsPerPage: state => {
    return state.pagination.itemsPerPage;
  },

  mipsaConfig : state => {

    return state.mipsaConfig;
  },

  libelleUtilisateur : state => {

    return state.libelleUtilisateur;
  }

};

export default {
  state,
  mutations,
  actions,
  getters
};

