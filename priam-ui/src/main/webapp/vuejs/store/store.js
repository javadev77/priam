/**
 * Created by benmerzoukah on 27/04/2017.
 */

import Vue from 'vue';
import Vuex from 'vuex';

import chargement from './modules/chargement';


import * as actions from './actions';

Vue.use(Vuex);

export default new Vuex.Store({
  actions
});
