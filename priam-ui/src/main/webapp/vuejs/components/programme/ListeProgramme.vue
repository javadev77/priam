<template>
  <div>
    <div class="container-fluid sacem-formula">
    </div>
    <priam-grid
      :data="priamGrid.gridData"
      :columns="priamGrid.gridColumns"
      noResultText="Aucun résultat."
      @cellClick="onCellClick"
      @load-page="loadPage"
      @on-sort="onSort">
    </priam-grid>
  </div>
</template>

<script>

  import Grid from '../common/Grid.vue';

  export default {

      data() {

        var $this =this;
        var getters = this.$store.getters;


        return {
            priamGrid : {
              gridColumns : [
                {
                  id :  'numProg',
                  name :   'N° Programme',
                  sortable : true,
                  type : 'long-text'
                },
                {
                  id :  'nom',
                  name :   'Nom',
                  sortable : true,
                  type : 'long-text'
                },
                {
                  id :  'rionTheorique',
                  name :   'Rion théorique',
                  sortable : true,
                  type : 'long-text'
                },
                {
                  id :  'famille',
                  name :   'Famille',
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    toText : function(cellValue) {
                      var result  = getters.famille.find(function (element) {
                        return element.id === cellValue;
                      });
                      return result !== undefined && result.value;
                    }
                  }
                },
                {
                  id :  'typeUtilisation',
                  name :   "Type d'utilisation",
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    toText : function(cellValue) {
                      var result  = getters.typeUtilisation.find(function (element) {
                        return element.id === cellValue;
                      });
                      return result !== undefined && result.value;
                    }
                  }
                },
                {
                  id :  'typeRepart',
                  name :   "Type répartition",
                  sortable : true,
                  type : 'long-text'
                },
                {
                  id :  'dateCreation',
                  name :   "Date création",
                  sortable : true,
                  type : 'date'
                },
                {
                  id :  'fichiers',
                  name :   "Fichiers",
                  sortable : true,
                  type : 'numeric'
                },
                {
                  id :  'statut',
                  name :   "Statut",
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    toText : function(cellValue) {
                      var result  = getters.statutProgramme.find(function (element) {
                        return element.code === cellValue;
                      });
                      return result !== undefined && result.libelle;
                    }
                  }
                },
                {
                  id :  'repartition',
                  name :   "Répartition",
                  sortable : false,
                  type : 'clickable-icon',
                  cell : {
                    cellTemplate: function (cellValue) {
                      var tempalte = '<a><span class="glyphicon glyphicon-alert" aria-hidden="true" ></span></a>';
                      var statusCode = cellValue.statut;

                      if(statusCode !== undefined && 'VALIDE' === statusCode) {
                        return tempalte;
                      }
                      return '';
                    }
                  }

                },
                {
                  id :  'rionPaiement',
                  name :   "Rion de paiement",
                  sortable : false,
                  type : 'icon'
                },
                {
                  id: 'action',
                  name: "Actions",
                  sortable: false,
                  type : 'clickable-icon',
                  cell : {
                    cellTemplate: function (cellValue) {
                      var tempalte = '<a><span class="glyphicon glyphicon-trash" aria-hidden="true" ></span></a>' +
                                      '<a><span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span></a>';
                      var statusCode = cellValue.statut;

                      if(statusCode !== undefined && 'AFFECTE' === statusCode) {
                        return tempalte;
                      }
                      return '';
                    }
                  }
                }
              ],
              gridData : {"content":[{"numProg":"PR170001","nom":"Programme 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"AFFECTE","rionPaiement":null},{"numProg":"PR170002","nom":"Programme 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null},{"numProg":"PR170003","nom":"Programme 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"VALIDE","rionPaiement":null},{"numProg":"PR170004","nom":"Programme 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null},{"numProg":"PR170005","nom":"Programme 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null}],"last":true,"totalPages":1,"totalElements":5,"size":5,"number":0,"sort":null,"numberOfElements":5,"first":true},
              //gridData : {"content":[],"totalElements":0,"totalPages":1,"last":true,"numberOfElements":25,"sort":null,"first":true,"size":25,"number":1},
              //gridData : {},
              searchQuery : ''
            }
          }
      },

      components : {
          priamGrid : Grid,
      }

  }

</script>
