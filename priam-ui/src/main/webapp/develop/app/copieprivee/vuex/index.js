/**
 * Created by benmerzoukah on 08/11/2017.
 */
import * as actions from './actions';
import mutations from './mutations';
import getters from './getters';

const state = {
  programmeEnSelection : {},
  listeUtilisateur : []
};

export default {
  state,
  actions,
  mutations,
  getters
};
