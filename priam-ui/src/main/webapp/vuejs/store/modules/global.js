/**
 * Created by benmerzoukah on 06/06/2017.
 */

const state = {

    pagination : {
      userPageSize: 25,
      sizes : [25, 50, 100]
    },

    userFamille : {'id' : 'ALL', 'value' : 'Toutes'},

    mipsaConfig : {},

    libelleUtilisateur : [],


    currentUser : {},

    appInfo :{}

}

const mutations = {
  'SELECT_PAGE' (state, page) {
      state.pagination.currentPage = page;
  },

  'SELECT_PAGE_SIZE' (state, pageSize) {
      state.pagination.userPageSize = pageSize;
  },

  'SET_LIBELLE_UTILISATEUR' (state, data) {
      state.libelleUtilisateur = data;
  },

  'SET_MIPSA_CONFIG' (state, mipsaConfig) {
    state.mipsaConfig = mipsaConfig;
  },

  'SET_CURRENT_USER' (state, userDto) {
    state.currentUser = userDto;
  },

  'SELECT_FAMILLE' (state, userFamille) {
    if(typeof userFamille != "object") {
      state.userFamille = {'id' : userFamille.split(',')[0], 'value' : userFamille.split(',')[1]};
    }

  },

  'SET_APP_INFO' (state, appInfo) {

    state.appInfo = appInfo;

  }

};

const actions = {

  SELECT_PAGE_SIZE  : ({commit}, value) => {
      commit('SELECT_PAGE_SIZE', value);
  }


};

const getters = {
  itemsPerPage: state => {
    return state.pagination.sizes;
  },

  userPageSize: state => {
    return state.pagination.userPageSize;
  },

  userFamille: state => {
    return state.userFamille;
  },


  mipsaConfig : state => {

    return state.mipsaConfig;
  },

  libelleUtilisateur : state => {

    return state.libelleUtilisateur;
  },

  getCurrentUser : state => {
    return state.currentUser;
  },

  appInfo : state => {
    return state.appInfo;
  }

};

export default {
  state,
  mutations,
  actions,
  getters
};

