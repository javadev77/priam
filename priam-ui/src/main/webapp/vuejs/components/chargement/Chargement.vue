<template>
 <div>
  <div class="container-fluid sacem-formula">

    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title" @click="isCollapsed = !isCollapsed">
            <!--<strong>Critères de Recherche</strong>-->
          <a>Critères de recherche</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
          <span class="pull-right fa" :class="{'fui-triangle-up' : isCollapsed,  'fui-triangle-down' : !isCollapsed}"></span>
        </h5>


      </div>
      <div class="panel-collapse" :class="{collapse : isCollapsed}">
        <div class="panel-body">
          <form class="form-horizontal" role="form">
            <div class="row">
                <div class="col-sm-2">
                  <label class="control-label pull-right">Famille</label>
                </div>
                <div class="col-sm-4">

                  <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                  </v-select>
                </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-5">

                <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                </v-select>
              </div>

            </div>

          </form>
        </div>
      </div>
    </div>

    <div class="form-horizontal">
      <div class="col-sm-2">
        <label class="control-label pull-right">Statut</label>
      </div>
      <div class="col-sm-12">
          <template v-for="item in statut">
            <label class="checkbox checkbox-inline" :class="{'checked' : isChecked(item.code)}">
              <input type="checkbox" v-model="inputChgtCriteria.statutCode" :value="item.code">{{item.libelle}}
              <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
            </label>
          </template>
        </div>
    </div>

    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercher()">Rechercher</button>
    </div>
 </div>

  <priam-grid
    :data="priamGrid.gridData"
    :columns="priamGrid.gridColumns"
    :filter-key="priamGrid.searchQuery"
    @cellClick="onCellClick"
    @load-page="loadPage">
  </priam-grid>

   <modal v-if="showModal" @close="showModal = false" @yes="supprimerDonneesFichier">
     <label class="homer-prompt-q control-label" slot="body">
       Etes-vous sûr de vouloir supprimer les données liées à ce fichier ?
     </label>
   </modal>
</div>
</template>



<script>

  import Grid from '../common/Grid.vue';
  import vSelect from '../common/Select.vue';
  import Modal from '../common/Modal.vue';

  export default {

      data () {

          var $this =this;
          var getters = this.$store.getters;

          return {
              showModal : false,
              entrySelected : {},
              selected: '',
              options: [{"id":"CMS","value":"CMS"},{"id":"COPIEPRIV","value":"Copie Privée"},{"id":"FDSVAL","value":"Valorisation"}],
              isCollapsed: false,
              critereInit : {},

              familleSelected : {'id' : 'ALL', 'value' : 'Toutes'},
              typeUtilisationSelected : {'id' : 'ALL', 'value' : 'Tous'},

              inputChgtCriteria : {
                familleCode : '',
                typeUtilisationCode : '',
                statutCode         : []
              },

              resource : {},

              defaultPageable : {
                page : 0,
                size : 5
              },

              priamGrid : {
                  gridColumns : [
                    {
                         id :  'nomFichier',
                         name :   'Fichier',
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
                        id :  'dateDebutChgt',
                        name :   "Début chargement",
                        sortable : true,
                        type : 'date'
                    },
                    {
                        id :  'dateFinChgt',
                        name :   "Fin chargement",
                        sortable : true,
                        type : 'date'
                    },
                    {
                        id :  'nbLignes',
                        name :   "Nb lignes",
                        sortable : true,
                        type : 'numeric'
                    },
                    {
                        id :  'statut',
                        name :   "Statut",
                        sortable : true,
                        type : 'code-value-hightlight',
                        cell : {
                          cellTemplate : function (entry) {
                            let element = $this.findStatusByCode(entry.statut);
                            var template = '<div style="padding-left : 2px; color : #fff; background-color: ' + element.color + '">' +
                                              element.libelle +
                                            '</div>'
                            return template;
                          }
                        }
                    },
                    {
                        id :  'logs',
                        name :   "Logs",
                        sortable : false,
                        type : 'icon'

                    },
                    {
                        id :  'action',
                        name :   "Actions",
                        sortable : false,
                        type : 'clickable-icon',
                        cell : {
                          cellTemplate: function (cellValue) {
                            var tempalte = '<a @click="$emit(\'showPopup\')"><span class="glyphicon glyphicon-trash" aria-hidden="true" ></span></a>';
                            var statusCode = cellValue.statut;
                            let element = $this.findStatusByCode(statusCode);
                            if(element !== undefined && ('CHARGEMENT_KO' === element.code || 'CHARGEMENT_OK' === element.code)) {
                                return tempalte;
                            }
                            return '';
                          }
                        }
                    }
                  ],
                  gridData : {"content":[{"id":31,"nomFichier":"Fichier 14","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"24/05/2017 18:15","dateFinChgt":"24/05/2017 22:57","nbLignes":12000,"statut":"AFFECTE"},{"id":30,"nomFichier":"Fichier 14","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"24/05/2017 18:15","dateFinChgt":"24/05/2017 22:57","nbLignes":12000,"statut":"AFFECTE"},{"id":32,"nomFichier":"Fichier 17","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"24/05/2017 16:00","dateFinChgt":"24/05/2017 22:57","nbLignes":150780,"statut":"ABANDONNE"},{"id":29,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"04/05/2017 18:15","dateFinChgt":"04/05/2017 22:57","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":28,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"02/05/2017 18:15","dateFinChgt":"01/05/2017 18:50","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":24,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"02/05/2017 18:15","dateFinChgt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":23,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01/05/2017 17:10","dateFinChgt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":27,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01/05/2017 17:10","dateFinChgt":"02/05/2017 01:10","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":22,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01/04/2017 17:15","dateFinChgt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":26,"nomFichier":"Fichier 09","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01/04/2017 17:15","dateFinChgt":"01/04/2017 22:10","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":19,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"04/02/2017 17:15","dateFinChgt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":20,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"03/02/2017 17:15","dateFinChgt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":25,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01/02/2017 17:15","dateFinChgt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":21,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01/02/2017 17:15","dateFinChgt":null,"nbLignes":6500,"statut":"EN_COURS"}],"totalElements":14,"totalPages":1,"last":true,"numberOfElements":14,"sort":null,"first":true,"size":20,"number":0},
                  //gridData : {},
                  searchQuery : ''
              }

          }
      },

      computed : {
          familleOptions() {
              return this.$store.getters.familleOptions;
          },

         typeUtilisationOptions() {
              return this.$store.getters.typeUtilisationOptions;
          },

          familleTypeUtilMap() {
            return this.$store.getters.familleTypeUtilMap;
          },

          statut() {
            return this.$store.getters.statut;
          }

      },

      created() {
          const customActions = {
              search : {method : 'POST', url :'app/rest/chargement/search?page={page}&size={size}'},
              deleteFichier : {method : 'GET', url :'app/rest/chargement/deleteFichier{/fileId}'}
          }
          this.resource= this.$resource('', {}, customActions);


          let statut = this.$store.getters.statut;
          for(var stt in statut) {
            if(statut[stt] && statut[stt].checked) {
              this.inputChgtCriteria.statutCode.push(statut[stt].code);
            }
          }

          this.rechercher();


      },

      methods: {

          loadPage: function(pageNum, size) {
            let pageSize = this.defaultPageable.size;
             if(size !== undefined) {
               pageSize = size;
             }
             this.resource.search({page : pageNum - 1, size : pageSize}, this.inputChgtCriteria)
                  .then(response => {
                     return response.json();
                  })
                  .then(data => {
                      this.priamGrid.gridData = data;
                      this.priamGrid.gridData.number = data.number + 1;
                  });
          },

          onCellClick : function(entry, column) {
              this.selectedEntry = entry;
              console.log('columnn' + column.id)
              if(column.id === 'action') {
                this.showModal = true;
              }
          },

          supprimerDonneesFichier() {
            this.showModal = false;
            console.log('Supprimer le fichier selectionne : ' + this.selectedEntry.id);
            this.resource.deleteFichier({fileId : this.selectedEntry.id})
              .then(response => {
                return response.json();
              })
              .then(data => {
                //this.priamGrid.gridData = data;
                console.log('data = ' + data);

                this.selectedEntry.statut = data.statut;

              });
          },

          findStatusByCode(code) {
            let statut = this.$store.getters.statut;
            return statut.find(function (element) {
              return element.code === code;
            })
          },

          getStatutLibelleByKey(key) {
            var status = this.findStatusByCode(key);

            return status !== undefined && status.libelle;
          },

          changeStatut() {
              this.$store.dispatch('changeStatut', this.inputChgtCriteria.statutCode);
          },

          isChecked (code) {
              var statutCode = this.inputChgtCriteria.statutCode;
              for(var i in statutCode) {
                if (statutCode[i] === code) {
                  return true;
                }
              }

              return false;

          },

          rechercher() {

              this.inputChgtCriteria.typeUtilisationCode = this.typeUtilisationSelected.id;
              this.inputChgtCriteria.familleCode = this.familleSelected.id;

              this.resource.search({page : this.defaultPageable.page, size : this.defaultPageable.size}, this.inputChgtCriteria)
                  .then(response => {
                      return response.json();
                  })
                  .then(data => {
                      this.priamGrid.gridData = data;
                      this.priamGrid.gridData.number = data.number + 1;
                  });
          },

          retablir() {

          },

          loadTypeUtilisation(val) {
              this.familleSelected = val;
              this.typeUtilisationSelected = {'id' : 'ALL', 'value': 'Tous'};
              this.$store.dispatch('loadTypeUtilisation', val);

          }
      },

      components : {
          priamGrid : Grid,
          vSelect : vSelect,
          modal : Modal
      }

  }
</script>


<style scoped>
</style>
