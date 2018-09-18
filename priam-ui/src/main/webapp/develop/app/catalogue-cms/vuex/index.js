import getters from './getters';

const state = {
  routing : false,
  libelleTypeCatalogue : [
    {
      "id" : 'FR',
      "value" : 'CMS France'
    },
    {
      "id" : 'ANF',
      "value" : 'CMS Antilles'
    }
  ],

  libelleTypeEvenement : [
    {
      "id" : 'ALL',
      "value" : 'Tous'
    },
    {
      "id" : 'AJOUT_MANUEL_OEUVRE',
      "value" : 'Ajout manuel œuvre'
    },
    {
      "id" : 'RENOUVELLEMENT_MANUEL_OEUVRE',
      "value" : 'Renouvellement manuel œuvre'
    },
    {
      "id" : 'SUPPRESSION_MANUELLE_OEUVRE',
      "value" : 'Suppression manuelle œuvre'
    },
    {
      "id" : 'MOUVEMENT_REPARTITION_ENTREE_OEUVRE',
      "value" : 'Mouvement de répartition - Entrée oeuvre'
    },
    {
      "id" : 'MOUVEMENT_REPARTITION_RENOUVELLEMENT_OEUVRE',
      "value" : 'Mouvement de répartition - Renouvellement oeuvre'
    },
    {
      "id" : 'SORTIE_CATALOGUE',
      "value" : 'Sortie du catalogue'
    },
    {
      "id" : 'PURGE_CATALOGUE',
      "value" : 'Purge du catalogue'
    },
    {
      "id" : 'SORTIE_NPU',
      "value" : 'Sortie NPU'
    },
    {
      "id" : 'CREATION_NPU',
      "value" : 'Création NPU'
    },
    {
      "id" : 'MIGRATION_RDO',
      "value" : 'Migration RDO'
    }
  ]

};

export default {
  state,
  getters
};

