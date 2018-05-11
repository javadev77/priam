/**
 * Created by benmerzoukah on 08/11/2017.
 */
import * as actions from './actions';
import mutations from './mutations';
import getters from './getters';

const state = {
  routing : false,
  pagination : {
    userPageSize: 25,
    sizes : [25, 50, 100]
  },

  userFamille : {'id' : 'ALL', 'value' : 'Toutes'},

  mipsaConfig : {},

  libelleUtilisateur : [],


  currentUser : {},

  appInfo :{},

  selectedOeuvre  : {},

  famille: [],

  typeUtilisation : [],

  familleOptions  : [],

  familleOptions_vide : [],

  typeUtilisationOptions : [],

  typeUtilisationOptions_vide : [],

  familleTypeUtilMap : {},

  territoire : [],

  statutFichier : {
    EN_COURS: {
      "code": 'EN_COURS',
      "libelle": 'En cours'
    }
    ,


    CHARGEMENT_OK: {
      "code": 'CHARGEMENT_OK',
      "libelle": 'Chargement OK'
    }
    ,


    CHARGEMENT_KO: {
      "code": 'CHARGEMENT_KO',
      "libelle": 'Chargement KO'
    }


  },

  statut : [
    {
      "code" : 'EN_COURS',
      "libelle" : 'En cours',
      "color"  : 'orange',
      "checked" : true
    },
    {
      "code" : 'CHARGEMENT_OK',
      "libelle" : 'Chargement OK',
      "color"  : 'green',
      "checked" : true
    },
    {
      "code": "CHARGEMENT_KO",
      "libelle": 'Chargement KO',
      "color"  : 'red',
      "checked" : true
    },
    {
      "code": "AFFECTE",
      "libelle": 'Affecté',
      "color"  : 'grey',
      "checked" : false
    },
    {
      "code": "ABANDONNE",
      "libelle": 'Abandonné',
      "color"  : '#4b77be',
      "checked" : false
    }

  ],


  allFichiersChecked : true,

  typeRepart : [
    {
      id : 'OEUVRE',
      value : 'Oeuvre'
    },
    {
      id : 'AYANT_DROIT',
      value : 'Ayant droit'
    }

  ],

  rions : [],

  rionsCreation : [],

  programmesEnCoursTraitement : [],

  programmesEnErreur : [],

  statutProgramme : [
    {
      "code" : 'EN_COURS',
      "libelle" : 'En cours',
      "orderAff" : 3,
      "checked" : false
    },
    {
      "code" : 'VALIDE',
      "libelle" : 'Validé',
      "orderAff" : 4,
      "checked" : false
    },
    {
      "code": "AFFECTE",
      "libelle": 'Affecté',
      "orderAff" : 2,
      "checked" : false
    },
    {
      "code": "ABANDONNE",
      "libelle": 'Abandonné',
      "orderAff" : 7,
      "checked" : false
    },
    {
      "code": "CREE",
      "libelle": 'Créé',
      "orderAff" : 1,
      "checked" : false
    },
    {
      "code": "MIS_EN_REPART",
      "libelle": 'Mis en répartition',
      "orderAff" : 5,
      "checked" : false
    },
    {
      "code": "REPARTI",
      "libelle": 'Réparti',
      "orderAff" : 6,
      "checked" : false
    }

  ],

  etatOeuvre : [
      {
        "code" : 'AUTOMATIQUE',
        "libelle" : 'Automatique'
      },
      {
        "code" : 'CORRIGE',
        "libelle" : 'Corrigé'
      },
      {
        "code": "MANUEL",
        "libelle": 'Manuel'
      }
    ],

  lastProgrammesMisEnRepart : []
};

export default {
  state,
  actions,
  mutations,
  getters
};
