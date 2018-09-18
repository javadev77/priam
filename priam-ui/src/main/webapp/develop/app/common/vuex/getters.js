/**
 * Created by benmerzoukah on 08/11/2017.
 */

export default {
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

  mipsaDetailConfig : state => {

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
  },

  selectedOeuvre: state => {
    return state.selectedOeuvre;
  },

  familleOptions: state => {
    return state.familleOptions;
  },

  familleOptionsVide: state => {
    return state.familleOptions_vide;
  },

  famille: state => {
    return state.famille;
  },

  typeUtilisationOptions: state => {
    return state.typeUtilisationOptions;
  },

  typeUtilisationOptionsVide: state => {
    return state.typeUtilisationOptions_vide;
  },

  typeUtilisation: state => {
    return state.typeUtilisation;
  },

  statut: state => {
    return state.statut.slice();
  },

  familleTypeUtilMap: state => {
    return state.statut;
  },

  statutFichier :state => {
    return state.statutFichier;
  },

  territoire :state => {
    return state.territoire;
  },

  statutProgramme: state => {
    return state.statutProgramme.slice();
  },

  typeRepart: state => {
    var typeRepartOpt = state.typeRepart.slice();
    typeRepartOpt.unshift({id : 'ALL', value: 'Tous'});
    return typeRepartOpt;
  },

  rions : state => {
    var options = state.rions.slice();
    options.unshift({id : 'ALL', value: 'Toutes'});
    return options;
  },

  rionsSaref : state => {
    return state.rions;
  },

  rionsAddProg : state => {
    var options = state.rionsCreation.slice();

    return options;
  },

  modeRepartition : state => {
    return state.typeRepart;
  },

  allFichiersChecked : state => {
    return state.allFichiersChecked;
  },

  programmesEnCoursTraitement : state => {
    return state.programmesEnCoursTraitement;
  },

  programmesEnErreur : state => {
    return state.programmesEnErreur;
  },

  getLastProgByNumProg : (state, getters) => (numProg) => {
    return state.lastProgrammesMisEnRepart.find(prog => prog.id === numProg)
  },

  etatOeuvre: state => {
    return state.etatOeuvre;
  },

  routing : state => {
    return state.routing;
  }

};
