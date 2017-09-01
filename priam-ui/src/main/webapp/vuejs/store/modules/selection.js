/**
 * Created by benmerzoukah on 01/09/2017.
 */

const state = {
    programmeEnSelection : {}
}

const mutations = {
    'SET_PROG_EN_COURS_SELECTION' (state, programme) {
      state.programmeEnSelection = programme;
    }
};

const actions = {
    setCurrentProgrammeEnSelection : ({commit}, programme) => {
      commit('SET_PROG_EN_COURS_SELECTION', programme);
    }
}

const getters = {
    programmeEnSelection: state => {
      return state.programmeEnSelection;
    }
}

export default {
    state,
    mutations,
    actions,
    getters
};
