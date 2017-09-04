/**
 * Created by benmerzoukah on 27/04/2017.
 */

import Vue from 'vue';
import Vuex from 'vuex';

import chargement from './modules/chargement';
import programme from './modules/programme';
import global from './modules/global';
import mipsa from './modules/mipsa';
import selection from './modules/selection';
import * as actions from './actions';


Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  actions,
  modules: {
    chargement, programme,global, mipsa, selection
  },
  strict: debug
});
