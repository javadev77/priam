<template>
  <div class="container-fluid">
    <div class="navbar navbar-default navbar-sm breadcrumb">
      <div class="titre-page">
        <span>Programme <span class="glyphicon glyphicon-chevron-right"></span>
              Liste programmes
            </span>
      </div>
    </div>
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
                  <label class="control-label pull-right">N° programme</label>
                </div>
                <div class="col-sm-3">
                  <!--<autocomplete :suggestions="numProgItems" :selection="critereRechercheData.numProg" @selected-value="updateValue"></autocomplete>-->
                  <!--<input type="text" class="form-control" v-model="critereRechercheData.numProg">-->
                 <!-- <v-select :searchable="true" v-model="critereRechercheData.numProg" :options="numProgItems">
                  </v-select>-->

                  <select2 class="form-control" :options="numProgOptions" v-model="numProgSelected" :searchable="true">
                  </select2>
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Famille</label>
                </div>
                <div class="col-sm-5">
                  <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                  </v-select>
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Date de création</label>
                </div>
                <div class="col-sm-2">
                  <date-picker @update-date="updateDateDebutCreation" :value="critereRechercheData.dateCreationDebut" date-format="dd/mm/yy" :zeroHour="true" place-holder="De" ></date-picker>

                </div>
                <div class="col-sm-2">
                  <date-picker @update-date="updateDateFinCreation" :value="critereRechercheData.dateCreationFin" date-format="dd/mm/yy" :zeroHour="false" place-holder="A" ></date-picker>
                </div>

                <div class="col-sm-2">
                  <label class="control-label pull-right">Rion statuaire</label>
                </div>
                <div class="col-sm-3">
                <!--  <v-select :searchable="false" label="value" v-model="rionTheoriqueSelected" :options="rionTheoriqueOptions">
                  </v-select>-->

                  <select2 class="form-control" :searchable="false" v-model="rionTheoriqueSelected" :options="rionTheoriqueOptions">
                  </select2>
                </div>
              </div>

              <!-- 2 eme ligne du form -->
              <div class="row">
                <div class="col-sm-2">
                  <label class="control-label pull-right">Nom</label>
                </div>
                <div class="col-sm-3">
                <select2 class="form-control" :options="nomProgOptions" v-model="nomProgSelected" :searchable="true">
                </select2>
                </div>
                <!--<div class="col-sm-3">
                  <input type="text" class="form-control" v-model="critereRechercheData.nom">
                </div>
                -->
                <div class="col-sm-2">
                  <label class="control-label pull-right">Type d'utilisation</label>
                </div>
                <div class="col-sm-5">
                  <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                  </v-select>
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Mode répartition</label>
                </div>
                <div class="col-sm-4">
                  <v-select :searchable="false" label="value" v-model="typeRepartSelected" :options="typeRepartOptions">
                  </v-select>
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Rion de paiement</label>
                </div>
                <div class="col-sm-3">
                  <v-select :searchable="false" label="value" v-model="rionPaiementSelected" :options="rionPaiementOptions">
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
              <input type="checkbox" v-model="critereRechercheData.statutCode" :value="item.code">{{item.libelle}}
              <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
            </label>
          </template>
        </div>
      </div>

      <div class="row formula-buttons">
        <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
        <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercherProgrammes()">Rechercher</button>
      </div>
    </div>

    <div class="container-fluid">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h5 class="panel-title">
            <a>Résultats</a>
            <span class="pull-left collapsible-icon bg-ico-tablerow"></span>
          </h5>
        </div>
        <div class="panel-collapse">
          <div class="result-panel-body panel-body">
            <div class="row">
              <button style="width: 160px;" class="btn btn-primary pull-right" type="button" @click="openEcranAjouterProgramme()">Créer un programme </button>
            </div>
            <priam-grid
              v-if="priamGrid.gridData.content"
              :data="priamGrid.gridData"
              :columns="priamGrid.gridColumns"
              noResultText="Aucun résultat."
              @cellClick="onCellClick"
              @update-programme="onUpdateProgramme"
              @abondon-programme="onAbondonProgramme"
              @mise-en-repart="onMiseEnRepartition"
              @load-page="loadPage"
              @on-sort="onSort">
            </priam-grid>
          </div>
        </div>
      </div>
    </div>

    <ecran-modal v-if="showEcranModal">
        <template v-if="ecranAjouterProgramme">
          <ajouter-programme slot="body"  @cancel="close" @validate="onValidateEcranModal"></ajouter-programme>
        </template>
        <template v-else>
          <modifier-programme :numProg="selectedProgramme.numProg" slot="body" @cancel="close" @validate="onValidateEcranModal">
          </modifier-programme>
        </template>
    </ecran-modal>

    <modal v-if="showPopupAbandon">
      <label class="homer-prompt-q control-label" slot="body">
        Etes-vous sûr de vouloir abandonner ce programme ?
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showPopupAbandon = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="abandonnerProgramme">Oui</button>
      </template>
    </modal>

    <template v-if="showEcranModalMisEnRepart">
      <mise-en-repartition-programme
        :numProg = "selectedProgramme.numProg"
        @cancel="showEcranModalMisEnRepart = false">

      </mise-en-repartition-programme>
    </template>
  </div>
</template>

<script>

  import Grid from '../common/Grid.vue';
  import EcranModal from '../common/EcranModal.vue';
  import ModifierProgramme from './ModifierProgramme.vue';
  import AjouterProgramme from './ajouterProgramme.vue';
  import vSelect from '../common/Select.vue';
  import DatePicker from '../common/DatePicker.vue';
  import {DateUtils} from '../../utils/DateUtils'
  import Modal from '../common/Modal.vue';
  import Autocomplete from '../common/Autocomplete.vue'
  import Select2 from '../common/Select2.vue';
  import MiseEnRepartitionProgramme from './MiseEnRepartitionProgramme.vue';


  export default {

      data() {

        var $this =this;
        var getters = this.$store.getters;


        return {
            numProgItems : [],
            nomProgItems : [],
            isCollapsed : false,
            showEcranModal : false,
            showPopupAbandon : false,
            ecranAjouterProgramme : false,
            showEcranModalMisEnRepart : false,
            resource : {},

            defaultPageable : {
              page : 1,
              sort : 'dateCreation',
              dir : 'DESC',
              size : 25
            },

            selectedProgramme : null,

            date : null,

            familleSelected : {'id' : 'ALL', 'value' : 'Toutes'},
            typeUtilisationSelected : {'id' : 'ALL', 'value' : 'Tous'},
            rionTheoriqueSelected :  'ALL',//{'id' : 'ALL', 'value' : 'Toutes'},
            rionPaiementSelected : {'id' : 'ALL', 'value' : 'Toutes'},
            typeRepartSelected : {'id' : 'ALL', 'value' : 'Tous'},
            numProgSelected: 'ALL',
            nomProgSelected: 'ALL',

            critereRechercheData : {
                numProg : '',
                famille : null,
                typeUtilisation: null,
                nom : null,
                rionTheorique : null,
                rionPaiement : null,
                typeRepart : null,
                dateCreationDebut : null,
                dateCreationFin : null,
                statutCode : ['EN_COURS', 'AFFECTE', 'CREE', 'VALIDE', 'MIS_EN_REPART']
            },

            priamGrid : {
              gridColumns : [
                {
                  id :  'numProg',
                  name :   'N° programme',
                  sortable : true,
                  type : 'numeric-link',
                  cell : {
                    toText : function(entry) {

                        console.log("statut "+entry.statut);
                        if(entry.statut === 'CREE' || entry.statut === 'ABANDONNE' ) {
                          return {value: entry.numProg, isLink: false}
                        }else {
                        return {value : entry.numProg, isLink : true}
                        }
                    }
                  }

                  },
                {
                  id :  'nom',
                  name :   'Nom',
                  sortable : true,
                  type : 'long-text'
                },
                {
                  id :  'rionTheorique',
                  name :   'Rion statuaire',
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
                  name :   "Mode répartition",
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    toText : function(cellValue) {
                      var result  = getters.typeRepart.find(function (element) {
                        return element.id === cellValue;
                      });
                      return result !== undefined && result.value;
                    }
                  }
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
                  type : 'numeric-link',
                  cell : {
                    toText : function(entry) {
                      var result  = getters.statutProgramme.find(function (element) {
                        return element.code === entry.statut;
                      });
                      if(result.code === 'ABANDONNE') {
                        return {value : entry.fichiers, isLink : false}
                      } else {
                        return {value : entry.fichiers, isLink : true}
                      }

                    }
                  }
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
                  type : 'clickable-icons',
                  cell : {
                    cellTemplate: function (cellValue) {
                      var tempalteRepartABlanc = '<img src="static/images/iconescontextes/transfertgestionnaire.gif" title="Mise en répartition" width="20px"/>';
                      var statusCode = cellValue.statut;
                      var template = [];
                      if(statusCode !== undefined && 'VALIDE' === statusCode) {
                        template.push({event : 'mise-en-repart', template : tempalteRepartABlanc});
                        return template;
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
                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;" title="Abandonner"></span>';
                      var tempalteUpdate = '<span class="glyphicon glyphicon-pencil" aria-hidden="true" style="padding-left: 0px;" title="Modifier"></span>';
                      var statusCode = cellValue.statut;

                      var tempalte = [];
                      if(statusCode !== undefined && ('CREE' === statusCode || 'AFFECTE' === statusCode
                        || 'EN_COURS' === statusCode || 'VALIDE' === statusCode) ) {
                        tempalte.push({event : 'update-programme', template : tempalteUpdate});
                      }

                      if(statusCode !== undefined && 'CREE' === statusCode) {
                            tempalte.push({event : 'abondon-programme', template : tempalteTrash});
                      }

                      if(tempalte.length == 1) {
                        tempalte.push({event : 'no', template : '<span aria-hidden="true" style="padding-left: 0px;"></span>'});
                      }


                      return tempalte;
                    }
                  }
                }
              ],
              //gridData : {"content":[{"numProg":"PR170001","nom":"Programme 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":4},{"numProg":"PR170003","nom":"Programme 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170005","nom":"Programme 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170002","nom":"Programme 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170004","nom":"Programme 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0}],"totalPages":1,"totalElements":5,"last":true,"size":25,"number":0,"sort":[{"direction":"ASC","property":"dateCreation","ignoreCase":false,"nullHandling":"NATIVE","ascending":true,"descending":false}],"numberOfElements":5,"first":true},
              //gridData : {"content":[],"totalElements":0,"totalPages":1,"last":true,"numberOfElements":25,"sort":null,"first":true,"size":25,"number":1},
              gridData : {},
              searchQuery : ''
            }
          }
      },

      created() {
        const customActions = {
            searchProgramme : {method : 'POST', url :'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'},
            abandonnerProgramme : {method : 'PUT', url :'app/rest/programme/abandon'},
            getAllNumProgForAutocmplete : {method : 'GET', url :'app/rest/programme/numprog/autocomplete'},
            getAllNomProgForAutocmplete : {method : 'GET', url :'app/rest/programme/nomprog/autocomplete'}
        }
        this.resource= this.$resource('', {}, customActions);

        this.rechercherProgrammes();


        this.getAllNumProgramme();
        this.getAllNomProgramme();
      },

      computed : {
        numProgOptions() {

            var result = this.numProgItems.map(elem => {
                console.log("elem="+elem);
                return {
                    id : elem,
                    value : elem
                }
            });

          console.log("result=" +typeof result);
          result.unshift({id : 'ALL', value : 'Tous'});
          return result !== undefined ? result : [];
        },
        nomProgOptions() {
          var result = this.nomProgItems.map(elem => {
            console.log("elem="+elem);
            return {
              id : elem,
              value : elem
            }
          });
          console.log("result=" +typeof result);
          result.unshift({id : 'ALL', value : 'Tous'});
          return result !== undefined ? result : [];
        },
        familleOptions() {
          return this.$store.getters.familleOptions;
        },

        typeUtilisationOptions() {
          return this.$store.getters.typeUtilisationOptions;
        },

        rionTheoriqueOptions() {
            return this.$store.getters.rions;
        },

        rionPaiementOptions() {
          return this.$store.getters.rions;
        },

        typeRepartOptions() {
            return this.$store.getters.typeRepart;
        },

        statut() {
            var statutProgramme = this.$store.getters.statutProgramme.sort(function (a, b) {
              if (a.orderAff < b.orderAff)
                return -1;
              if (a.orderAff > b.orderAff )
                return 1;
              // a doit être égal à b
              return 0;
            });

            console.log(statutProgramme)

            return statutProgramme;

        }
      },


      methods : {

        getAllNumProgramme() {
            this.resource.getAllNumProgForAutocmplete()
              .then(response => {
                return response.json();
              })
              .then(data => {
                this.numProgItems = data;
              });
        },
        getAllNomProgramme() {
          this.resource.getAllNomProgForAutocmplete()
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.nomProgItems = data;
            });
        },


        updateValue(selectedValue) {

            this.critereRechercheData.numProg = selectedValue;

          },

          retablir() {
              this.numProgSelected = 'ALL';
              this.critereRechercheData.numProg = null;
              this.critereRechercheData.famille = null;
              this.critereRechercheData.typeUtilisation = null;
              this.nomProgSelected = 'ALL'
              this.critereRechercheData.nom = null;
              this.critereRechercheData.rionTheorique = null;
              this.critereRechercheData.rionPaiement =  null;
              this.critereRechercheData.typeRepart = null;
              this.critereRechercheData.dateCreationDebut = null;
              this.critereRechercheData.dateCreationFin = null;
              this.critereRechercheData.statutCode = ['EN_COURS', 'AFFECTE', 'CREE', 'VALIDE', 'MIS_EN_REPART'];

              this.familleSelected = {'id' : 'ALL', 'value' : 'Toutes'};
              this.typeUtilisationSelected = {'id' : 'ALL', 'value' : 'Tous'};
              this.rionTheoriqueSelected = 'ALL';//{'id' : 'ALL', 'value' : 'Toutes'};
              this.rionPaiementSelected = {'id' : 'ALL', 'value' : 'Toutes'};
              this.typeRepartSelected = {'id' : 'ALL', 'value' : 'Tous'};

              this.rechercherProgrammes();
          },

          onSort(currentPage, pageSize, sort) {

            this.launchRequest(currentPage, pageSize,
              sort.property, sort.direction);

            this.defaultPageable.sort = sort.property;
            this.defaultPageable.dir = sort.direction;
          },

          loadPage: function(pageNum, size, sort) {
            let pageSize = this.defaultPageable.size;
            if(size !== undefined) {
              pageSize = size;
            }

            this.launchRequest(pageNum, pageSize,
              sort.property, sort.direction);

          },

          launchRequest(pageNum, pageSize, sort, dir) {
            this.resource.searchProgramme({page : pageNum - 1, size : pageSize,
              sort : sort, dir: dir}, this.critereRechercheData)
              .then(response => {
                return response.json();
              })
              .then(data => {
                this.priamGrid.gridData = data;
                this.priamGrid.gridData.number = data.number + 1;

              });
          },

          openEcranAjouterProgramme() {
              console.log("openEcranAjouterProgramme()");
              this.ecranAjouterProgramme = true;
              this.showEcranModal = true;
          },

          close() {
              console.log("Close Modal Dialog");
            this.showEcranModal = false;
          },

          onUpdateProgramme(row, column) {
            console.log("onUpdateProgramme()");
             this.selectedProgramme = row;

            this.ecranAjouterProgramme = false;
            this.showEcranModal = true;
          },

          onAbondonProgramme(row, column) {
              console.log("onAbondonProgramme()");
              this.showPopupAbandon = true;
              this.selectedProgramme = row;

          },

          onCellClick(row, column) {
              console.log("Clicked row row=" + row.numProg + ", column="+column.id )
              if(column.id !== undefined && column.id === 'fichiers') {
                  //redirect to Affectation.vue
                this.$router.push({ name: 'affectation', params: { numProg: row.numProg }});
              } else if(column.id !== undefined && column.id === 'numProg') {
                this.$router.push({ name: 'selection', params: { numProg: row.numProg }});
              }
          },

          rechercherProgrammes() {

              this.critereRechercheData.numProg = this.numProgSelected !== undefined && this.numProgSelected !== 'ALL' ? this.numProgSelected : null;
              console.log(" this.critereRechercheData.numProg = " +  this.critereRechercheData.numProg)
              this.critereRechercheData.nom = this.nomProgSelected !== undefined && this.nomProgSelected !== 'ALL' ? this.nomProgSelected : null;
              this.critereRechercheData.typeUtilisation = this.typeUtilisationSelected !== undefined ? this.typeUtilisationSelected.id : null;
              this.critereRechercheData.famille = this.familleSelected !== undefined ? this.familleSelected.id : null;
              this.critereRechercheData.rionTheorique= this.rionTheoriqueSelected !== undefined ? this.rionTheoriqueSelected : null;
              this.critereRechercheData.rionPaiement= this.rionPaiementSelected !== undefined ? this.rionPaiementSelected.id : null;
              this.critereRechercheData.typeRepart = this.typeRepartSelected !== undefined ? this.typeRepartSelected.id : null;

              this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
                                 this.defaultPageable.sort, this.defaultPageable.dir);


          },

          loadTypeUtilisation(val) {
              this.familleSelected = val;
              this.typeUtilisationSelected = {'id' : 'ALL', 'value': 'Tous'};
              this.$store.dispatch('loadTypeUtilisation', val);

          },

          updateDateDebutCreation(date) {
              console.log("dateCreationDebut="+date)
              this.critereRechercheData.dateCreationDebut = date;
          },

          updateDateFinCreation(date) {
              this.critereRechercheData.dateCreationFin = date;
          },

          isChecked (code) {
            var result = this.critereRechercheData.statutCode.find(function (element) {
              return element === code;
            });

            return result !== undefined && result;
          },

          onValidateEcranModal() {
              console.log('onValidateEcranModal()')
              this.close();
              this.rechercherProgrammes();
              this.getAllNumProgramme();
              this.getAllNomProgramme();
          },

          abandonnerProgramme() {
            this.showPopupAbandon = false;
            console.log('Abandonner le programme selectionne : ' + this.selectedProgramme.numProg);
            this.resource.abandonnerProgramme({numProg : this.selectedProgramme.numProg})
              .then(response => {
                return response.json();
              })
              .then(data => {
                console.log('data = ' + data);
                this.selectedProgramme.statut = data.statut;

              });

          },


          onMiseEnRepartition(row, column) {
            console.log('--- onMiseEnRepartition button click---');
            this.showEcranModalMisEnRepart = true;
            this.selectedProgramme = row;
            console.log('--- selectedProgramme numProg = ' + this.selectedProgramme.numProg);

          }


      },

      components : {
          priamGrid : Grid,
          ecranModal : EcranModal,
          modifierProgramme : ModifierProgramme,
          ajouterProgramme : AjouterProgramme,
          vSelect : vSelect,
          datePicker : DatePicker,
          modal: Modal,
          autocomplete : Autocomplete,
          select2 :Select2,
          miseEnRepartitionProgramme : MiseEnRepartitionProgramme
      }

  }

</script>
