/**
 * Created by benmerzoukah on 31/08/2017.
 */

const state = {

  selectedOeuvre  : {}

}


const mutations = {
  'SELECT_OEUVRE' (state, oeuvre) {
    state.selectedOeuvre = oeuvre;
  }

}


const actions = {

  SELECT_OEUVRE_ACTION  : ({commit}, oeuvre) => {
    commit('SELECT_OEUVRE', oeuvre);
  }

};

const getters = {
  selectedOeuvre: state => {
    return state.selectedOeuvre;
  },

};

export default {
  state,
  mutations,
  actions,
  getters
};
