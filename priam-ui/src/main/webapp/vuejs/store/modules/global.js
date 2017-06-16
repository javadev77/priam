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


    }

}

const mutations = {
  'SELECT_PAGE' (state, page) {
    state.pagination.currentPage = page;
  },
  'SELECT_PAGE_SIZE' (state, pageSize) {
    state.pagination.itemsPerPage = pageSize;
  }
};

const actions = {

  SELECT_PAGE_SIZE  : ({commit}, value) => {
      commit('SELECT_PAGE_SIZE', value);
  }
};

const getters = {
  itemsPerPage: state => {
    return state.pagination.itemsPerPage;
  }

};

export default {
  state,
  mutations,
  actions,
  getters
};

