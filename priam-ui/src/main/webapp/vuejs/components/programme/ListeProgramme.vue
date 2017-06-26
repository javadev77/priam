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
                  <label class="control-label pull-right">N° programme</label>
                </div>
                <div class="col-sm-3">
                  <input type="text" class="form-control" v-model="critereRechercheData.numProg">
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
                  <date-picker @update-date="updateDateDebutCreation" date-format="dd/mm/yy" :zeroHour="true" place-holder="De" v-once></date-picker>

                </div>
                <div class="col-sm-2">
                  <date-picker @update-date="updateDateFinCreation" date-format="dd/mm/yy" :zeroHour="false" place-holder="A" v-once></date-picker>
                </div>

                <div class="col-sm-2">
                  <label class="control-label pull-right">Rion théorique</label>
                </div>
                <div class="col-sm-3">
                  <v-select :searchable="false" label="value" v-model="rionTheoriqueSelected" :options="rionTheoriqueOptions">
                  </v-select>
                </div>
              </div>

              <!-- 2 eme ligne du form -->
              <div class="row">
                <div class="col-sm-2">
                  <label class="control-label pull-right">Nom</label>
                </div>
                <div class="col-sm-3">
                  <input type="text" class="form-control" v-model="critereRechercheData.nom">
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Type d'utilisation</label>
                </div>
                <div class="col-sm-5">
                  <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                  </v-select>
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Type répartition</label>
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
            <div>
              <button style="width: 160px;" class="btn btn-primary pull-right" type="button" @click="openEcranAjouterProgramme()">Créer un programme </button>
            </div>
            <priam-grid
              v-if="this.priamGrid.gridData.content"
              :data="priamGrid.gridData"
              :columns="priamGrid.gridColumns"
              noResultText="Aucun résultat."
              @cellClick="onCellClick"
              @update-programme="onUpdateProgramme"
              @delete-programme="onDeleteProgramme"
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

  export default {

      data() {

        var $this =this;
        var getters = this.$store.getters;


        return {
            isCollapsed : false,
            showEcranModal : false,
            ecranAjouterProgramme : false,

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
            rionTheoriqueSelected : {'id' : 'ALL', 'value' : 'Toutes'},
            rionPaiementSelected : {'id' : 'ALL', 'value' : 'Toutes'},
            typeRepartSelected : {'id' : 'ALL', 'value' : 'Tous'},

            critereRechercheData : {
                numProg : null,
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
                        console.log("entry"  + entry.statut)
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
                      var tempalteRepartABlanc = '<img src="static/images/iconescontextes/transfertgestionnaire.gif" width="20px"/>';
                      var tempalteMiseEnRepart = '<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>';
                      var statusCode = cellValue.statut;
                      var template = [];
                      if(statusCode !== undefined && 'VALIDE' === statusCode) {
                        template.push({event : 'nop', template : tempalteMiseEnRepart});
                        template.push({event : 'nop', template : tempalteRepartABlanc});
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
                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;"></span>';
                      var tempalteUpdate = '<span class="glyphicon glyphicon-pencil" aria-hidden="true" style="padding-left: 0px;"></span>';
                      var statusCode = cellValue.statut;

                      var tempalte = [];
                      if(statusCode !== undefined && ('CREE' === statusCode || 'AFFECTE' === statusCode
                        || 'EN_COURS' === statusCode || 'VALIDE' === statusCode) ) {
                        tempalte.push({event : 'update-programme', template : tempalteUpdate});
                      }

                      if(statusCode !== undefined && 'CREE' === statusCode) {
                            tempalte.push({event : 'delete-programme', template : tempalteTrash});
                      }

                      if(tempalte.length == 1) {
                        tempalte.push({event : 'no', template : '<span aria-hidden="true" style="padding-left: 0px;"></span>'});
                      }


                      return tempalte;
                    }
                  }
                }
              ],
              gridData : {"content":[{"numProg":"PR170001","nom":"Programme 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":4},{"numProg":"PR170003","nom":"Programme 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170005","nom":"Programme 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170002","nom":"Programme 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0},{"numProg":"PR170004","nom":"Programme 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","rionTheorique":619,"dateCreation":"06/06/2017 00:00","typeRepart":"OEUVRE","statut":"EN_COURS","rionPaiement":null,"fichiers":0}],"totalPages":1,"totalElements":5,"last":true,"size":25,"number":0,"sort":[{"direction":"ASC","property":"dateCreation","ignoreCase":false,"nullHandling":"NATIVE","ascending":true,"descending":false}],"numberOfElements":5,"first":true},
              //gridData : {"content":[],"totalElements":0,"totalPages":1,"last":true,"numberOfElements":25,"sort":null,"first":true,"size":25,"number":1},
              //gridData : {},
              searchQuery : ''
            }
          }
      },

      created() {
        const customActions = {
          searchProgramme : {method : 'POST', url :'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'}
        }
        this.resource= this.$resource('', {}, customActions);

        this.rechercherProgrammes();
      },

      computed : {
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
              return a.orderAff > b.orderAff;
            });

            return statutProgramme;

        }
      },


      methods : {

          retablir() {
              this.critereRechercheData.numProg = null;
              this.critereRechercheData.famille = null;
              this.critereRechercheData.typeUtilisation = null;
              this.critereRechercheData.nom = null;
              this.critereRechercheData.rionTheorique = null;
              this.critereRechercheData.rionPaiement =  null;
              this.critereRechercheData.typeRepart = null;
              this.critereRechercheData.dateCreationDebut = null;
              this.critereRechercheData.dateCreationFin = null;
              this.critereRechercheData.statutCode = ['EN_COURS', 'AFFECTE', 'CREE', 'VALIDE', 'MIS_EN_REPART'];

              this.familleSelected = {'id' : 'ALL', 'value' : 'Toutes'};
              this.typeUtilisationSelected = {'id' : 'ALL', 'value' : 'Tous'};
              this.rionTheoriqueSelected = {'id' : 'ALL', 'value' : 'Toutes'};
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

          onDeleteProgramme(row, column) {
            console.log("onDeleteProgramme()");
          },

          onCellClick(row, column) {
          },

          rechercherProgrammes() {
              this.critereRechercheData.typeUtilisation = this.typeUtilisationSelected !== undefined ? this.typeUtilisationSelected.id : null;
              this.critereRechercheData.famille = this.familleSelected !== undefined ? this.familleSelected.id : null;
              this.critereRechercheData.rionTheorique= this.rionTheoriqueSelected !== undefined ? this.rionTheoriqueSelected.id : null;
              this.critereRechercheData.rionPaiement= this.rionPaiementSelected !== undefined ? this.rionPaiementSelected.id : null;
              this.critereRechercheData.typeRepart = this.typeRepartSelected !== undefined ? this.typeRepartSelected.id : null;
              //this.critereRechercheData.dateCreationDebut = DateUtils.stringToDateZeroHour(this.critereRechercheData.dateCreationDebut);
              //this.critereRechercheData.dateCreationFin = DateUtils.stringToDate24Hour(this.critereRechercheData.dateCreationFin);

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
          }


      },

      components : {
          priamGrid : Grid,
          ecranModal : EcranModal,
          modifierProgramme : ModifierProgramme,
          ajouterProgramme : AjouterProgramme,
          vSelect : vSelect,
          datePicker : DatePicker
      }

  }

</script>
