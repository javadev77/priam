/**
 * Created by benmerzoukah on 08/11/2017.
 */

export default {

  'ROUTING' (state, routing) {
    state.routing = routing;
  },

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

  'SET_MIPSA_DETAIL_CONFIG' (state, mipsaDetailConfig) {
    state.mipsaDetailConfig = mipsaDetailConfig;
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
  },

  'SELECT_OEUVRE' (state, oeuvre) {
    state.selectedOeuvre = oeuvre;
  },


  'SET_LIBELLE_FAMILLE' (state, famille) {
    state.famille = famille;
    state.familleOptions=famille.slice();
    state.familleOptions.unshift({'id' :'ALL', "value" : "Toutes"});

    state.familleOptions_vide=famille.slice();
  },

  'SET_LIBELLE_TYPE_UTILSATION' (state, typeUtilisation) {
    state.typeUtilisation = typeUtilisation.slice();
    state.typeUtilisationOptions_vide = typeUtilisation.slice();
    debugger;
    state.typeUtilisationOptions = [{'id' :'ALL', "value" : "Tous"}];
  },

  'SET_FAMILLE_TYPE_UTILSATION_MAP' (state, data) {
    state.familleTypeUtilMap = data;
  },

  'CHANGE_TYPE_UTILSATION_LIST' (state, familleCode) {
    if(familleCode && familleCode.id !== 'ALL') {

      state.typeUtilisationOptions = state.familleTypeUtilMap[familleCode.id].slice();
      state.typeUtilisationOptions.unshift({'id' :'ALL', "value" : "Tous"});

    } else {
      state.typeUtilisationOptions = [{'id' :'ALL', "value" : "Tous"}];
    }

  },

  'CHANGE_TYPE_UTILSATION_LIST_VIDE' (state, familleCode) {
    debugger
    if(familleCode != null) {
      state.typeUtilisationOptions_vide = state.familleTypeUtilMap[familleCode.id].slice();

    }

  },

  'CHANGE_STATUT_VALUE' (state, statutCode) {
    if(statutCode != null) {

      for(var stat in state.statut) {
        for(var i in statutCode) {
          if(state.statut[stat].code === statutCode[i]) {
            state.statut[stat].checked = true;
          }
        }

      }

    }
  },

  'SET_TERRITOIRE_MAP' (state, data) {
    state.territoire = data;
  },


  'SET_RIONS' (state, rions) {
    state.rions = rions;
  },

  'SET_RIONS_CREATION'(state, rionsCreation) {
    state.rionsCreation = rionsCreation;
  },

  'TOUT_DESACTIVER'(state, value) {
    state.allFichiersChecked = value;
  },

  'ADD_PROG_EN_COURS'(state, numProg) {
    state.programmesEnCoursTraitement.push(numProg);
  },

  'DELETE_PROG_EN_COURS'(state, numProg) {
    let number = state.programmesEnCoursTraitement.indexOf(numProg);
    state.programmesEnCoursTraitement.splice(number, 1);
  },

  'ADD_PROG_EN_ERREUR'(state, numProg) {
    state.programmesEnErreur.push(numProg);
  },

  'DELETE_PROG_EN_ERREUR'(state, numProg) {
    let number = state.programmesEnErreur.indexOf(numProg);
    state.programmesEnErreur.splice(number, 1);
  },

  'SAVE_LAST_PROGRAMME'(state, programme) {
    var progFound = state.lastProgrammesMisEnRepart.find(function (prog) {
      return prog.numProg === programme.numProg;
    })
    if(progFound === undefined) {
      state.lastProgrammesMisEnRepart.push(programme);
    }
  }

};
