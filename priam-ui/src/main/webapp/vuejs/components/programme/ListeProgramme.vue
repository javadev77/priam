<template>
  <div>
    <div class="container-fluid sacem-formula">
      <div class="row formula-buttons">
        <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
        <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercherProgrammes()">Rechercher</button>
      </div>
    </div>
    <priam-grid
      :data="priamGrid.gridData"
      :columns="priamGrid.gridColumns"
      noResultText="Aucun résultat."
      @cellClick="onCellClick"
      @update-programme="onUpdateProgramme"
      @delete-programme="onDeleteProgramme"
      @load-page="loadPage"
      @on-sort="onSort">
    </priam-grid>

    <ecran-modal v-if="showEcranModal">
      <modifier-programme slot="body" @cancel="close">
      </modifier-programme>
    </ecran-modal>

  </div>
</template>

<script>

  import Grid from '../common/Grid.vue';
  import EcranModal from '../common/EcranModal.vue';
  import ModifierProgramme from './ModifierProgramme.vue';
  import Chargement from '../chargement/Chargement.vue';

  export default {

      data() {

        var $this =this;
        var getters = this.$store.getters;


        return {
          showEcranModal : false,

            resource : {},

            defaultPageable : {
              page : 1,
              size : 25
            },

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
                  type : 'clickable-link'
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
                  type : 'clickable-icons',
                  cell : {
                    cellTemplate: function (cellValue) {
                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 5px;"></span>';
                      var tempalteUpdate = '<span class="glyphicon glyphicon-pencil" aria-hidden="true" style="padding-left: 5px;"></span>';
                      var statusCode = cellValue.statut;

                      var tempalte = [];
                      if(statusCode !== undefined && 'CREE' === statusCode) {
                            tempalte.push({event : 'delete-programme', template : tempalteTrash});
                      }

                      if(statusCode !== undefined && ('CREE' === statusCode || 'AFFECTE' === statusCode
                                                      || 'EN_COURS' === statusCode || 'VALIDE' === statusCode) ) {
                        tempalte.push({event : 'update-programme', template : tempalteUpdate});
                      }

                      return tempalte;
                    }
                  }
                }
              ],
              gridData : {"content":[{"numProg":"PR170001","nom":"Programme 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"CREE","rionPaiement":null,"fichiers":4},{"numProg":"PR170002","nom":"Programme 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170003","nom":"Programme 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170004","nom":"Programme 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170005","nom":"Programme 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0}],"last":true,"totalPages":1,"totalElements":5,"size":25,"number":0,"sort":null,"numberOfElements":5,"first":true},
              //gridData : {"content":[],"totalElements":0,"totalPages":1,"last":true,"numberOfElements":25,"sort":null,"first":true,"size":25,"number":1},
              //gridData : {},
              searchQuery : ''
            }
          }
      },

      created() {
        const customActions = {
          searchProgramme : {method : 'GET', url :'app/rest/programme/search?page={page}&size={size}'}
        }
        this.resource= this.$resource('', {}, customActions);

        this.rechercherProgrammes();
      },


      methods : {

          close() {
              console.log("Close Modal Dialog");
            this.showEcranModal = false;
          },

          onUpdateProgramme(row, column) {
            console.log("onUpdateProgramme()");
            this.showEcranModal = true;
          },

          onDeleteProgramme(row, column) {
            console.log("onDeleteProgramme()");
          },

          onCellClick(row, column) {
          },

          rechercherProgrammes() {
              this.resource.searchProgramme({page : 0, size : 25})
                .then(response => {
                  return response.json();
                })
                .then(data => {
                  this.priamGrid.gridData = data;
                  this.priamGrid.gridData.number = data.number + 1;
                });
          }

      },

      components : {
          priamGrid : Grid,
          ecranModal : EcranModal,
          modifierProgramme : ModifierProgramme,
          chargement : Chargement
      }

  }

</script>
