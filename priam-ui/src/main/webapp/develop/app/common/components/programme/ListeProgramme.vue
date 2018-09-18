<template>
  <div class="container-fluid">

    <navbar titre1="Programme" titre2="Liste programmes"></navbar>

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
                  <date-picker v-model="critereRechercheData.dateCreationDebut"
                               date-format="dd/mm/yy"
                               :zeroHour="true"
                               place-holder="De" >

                  </date-picker>

                </div>
                <div class="col-sm-2">
                  <date-picker v-model="critereRechercheData.dateCreationFin"
                               date-format="dd/mm/yy"
                               :zeroHour="true"
                               place-holder="A" >

                  </date-picker>
                </div>

                <div class="col-sm-2">
                  <label class="control-label pull-right">Rion cible</label>
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
                  <select2 class="form-control" :searchable="false" label="value" v-model="rionPaiementSelected" :options="rionPaiementOptions">
                  </select2>
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
              <button style="width: 160px;"
                      class="btn btn-primary pull-right"
                      type="button"
                      @click="openEcranAjouterProgramme()"
                      :disabled="!isRightCRTPRG">Créer un programme </button>
            </div>
            <priam-grid
              v-if="priamGrid.gridData.content"
              :data="priamGrid.gridData"
              :columns="priamGrid.gridColumns"
              :rowTooltip="afficherTootlip"
              noResultText="Aucun résultat."
              @cellClick="onCellClick"
              @log-programme="onLogProgramme"
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

    <ecran-modal v-if="showPopupLog">
      <template>
        <journal-programme :numProg="selectedProgramme.numProg" slot="body" @cancel="closePopupLog"></journal-programme>
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
        @cancel="showEcranModalMisEnRepart = false"
        @close="showEcranModalMisEnRepart = false"
        @validate="onValidateMiseEnRepart"
        >

      </mise-en-repartition-programme>
    </template>

    <modal v-if="isToShowErrors">
      <template slot="body" >
        <label>Le fichier de répartition contient les erreurs suivantes :</label>
        <div style="height:300px; overflow-y:scroll;">
          <label v-for="error in fichierFelixErrors">
            {{ error.log }}
          </label>

        </div>
      </template>

      <template slot="footer">
        <button class="btn btn-default btn-primary" @click="isToShowErrors = false">Fermer</button>
      </template>
    </modal>
  </div>
</template>

<script>

  import Grid from '../ui/Grid.vue';
  import EcranModal from '../ui/EcranModal.vue';
  import ModifierProgramme from './ModifierProgramme.vue';
  import JournalProgramme from './JournalProgramme'
  import AjouterProgramme from './ajouterProgramme.vue';
  import vSelect from '../ui/Select.vue';
  import DatePicker from '../ui/DatePicker.vue';
  import Modal from '../ui/Modal.vue';
  import Autocomplete from '../ui/Autocomplete.vue'
  import Select2 from '../ui/Select2.vue';
  import MiseEnRepartitionProgramme from './MiseEnRepartitionProgramme.vue';

  import ChargementMixin from '../../mixins/chargementMixin';
  import programmeMixin from '../../mixins/programmeMixin';
  import { FAMILLES_PRIAM } from '../../../../consts';
  import Navbar from '../../../common/components/ui/priam-navbar.vue';


  export default {
      mixins : [ChargementMixin, programmeMixin],

      data() {

        var $this =this;
        var getters = this.$store.getters;


        return {
            numProgItems : [],
            nomProgItems : [],
            isCollapsed : false,
            showEcranModal : false,
            showPopupLog : false,
            showPopupAbandon : false,
            ecranAjouterProgramme : false,
            showEcranModalMisEnRepart : false,
            resource : {},

            modeRepartition : '',

            programmesEnCoursTraitement :[],
            programmesEnErreur :[],
            intervalIDs : [],
            statusTimers : [],

            isToShowErrors : false,
            fichierFelixErrors : [],

            defaultPageable : {
              page : 1,
              sort : 'dateCreation',
              dir : 'DESC',
              size : this.$store.getters.userPageSize
            },

            selectedProgramme : null,

            date : null,

            familleSelected : this.$store.getters.userFamille,
            typeUtilisationSelected : {'id' : 'ALL', 'value' : 'Tous'},
            rionTheoriqueSelected :  'ALL',//{'id' : 'ALL', 'value' : 'Toutes'},
            rionPaiementSelected : 'ALL', //{'id' : 'ALL', 'value' : 'Toutes'},
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
                statutCode : ['EN_COURS', 'AFFECTE', 'CREE', 'VALIDE', 'MIS_EN_REPART', 'EN_COURS_MISE_EN_REPART']
            },


            priamGrid : {

              gridColumns : [
                {
                  id: 'numProg',
                  name: 'N° programme',
                  sortable: true,
                  type: 'numeric-link',
                  cell: {
                    toText: function (entry) {
                      if (entry.statut === 'CREE' || entry.statut === 'ABANDONNE'
                        || entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {

                        return {value: entry.numProg, isLink: false
                        }
                      } else {
                        return {value: entry.numProg, isLink: true}
                      }
                    },

                    css: function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                            || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                            || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {

                          return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }
                  }
                },
                {
                  id :  'nom',
                  name :   'Nom',
                  sortable : true,
                  type : 'long-text',
                  cell : {

                    css: function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }

                  }
                },
                {
                  id :  'rionTheorique',
                  name :   'Rion cible',
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    toText : function(rionTheorique) {
                      return $this.getLibelleRionById(rionTheorique);
                    },

                    css: function (entry) {
                      debugger;
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {

                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }
                  }
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
                    },

                    css: function (entry) {
                      debugger;
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
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
                    },

                    css: function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
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
                    },

                    css: function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }
                  }
                },
                {
                  id :  'dateCreation',
                  name :   "Date création",
                  sortable : true,
                  type : 'date',
                  cell : {

                    css: function (entry) {
                        debugger;
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }

                  }
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
                      if(result.code === 'ABANDONNE' ||  entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {value : entry.fichiers, isLink : false}
                      } else {
                        return {value : entry.fichiers, isLink : true}
                      }

                    },


                    css: function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
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
                    },

                    css: function (entry) {
                        debugger;
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {

                          return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }
                  }
                },
                {
                  id :  'repartition',
                  name :   "Répartition",
                  sortable : false,
                  type : 'clickable-icons-or-text',
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
                    },

                    isText : function (entry) {
                      var statusCode = entry.statut;
                      var statutFichierFelix = entry.statutFichierFelix !== null && entry.statutFichierFelix !== undefined ? entry.statutFichierFelix : undefined;
                      if(statusCode !== undefined && 'REPARTI' == statusCode ) {
                          return true;

                      }  else  if('VALIDE' == statusCode) {
                        if ($this.programmesEnCoursTraitement.indexOf(entry.numProg) !== -1
                              ||  $this.programmesEnErreur.indexOf(entry.numProg) !== -1
                              || (statutFichierFelix !== undefined
                                  && statutFichierFelix  !== 'GENERE'
                                  && statutFichierFelix  !== 'ENVOYE') ) {
                            return true;
                        }
                      }

                      return false;
                    },

                    toText : function (entry) {
                        var statusCode = entry.statut;
                        var statutFichierFelix = entry.statutFichierFelix !== null && entry.statutFichierFelix !== undefined ? entry.statutFichierFelix : undefined;
                        if(statusCode !== undefined && 'REPARTI' == statusCode) {
                          return {value : entry.dateRepartition, isLink : false };
                        } else if ('VALIDE' == statusCode
                                    && ($this.programmesEnCoursTraitement.indexOf(entry.numProg) !== -1)
                                          || (statutFichierFelix !== undefined && (statutFichierFelix == 'EN_COURS' || statutFichierFelix == 'EN_COURS_ENVOI')) ) {
                            return {value: 'En cours de traitement', isLink: false};

                        } else if ('VALIDE' == statusCode &&
                                      ($this.programmesEnErreur.indexOf(entry.numProg) !== -1)
                                        || (statutFichierFelix !== undefined && entry.statutFichierFelix == 'EN_ERREUR')) {


                            return {value: 'Génération en erreur', isLink: true};
                        }
                        return {};

                    },

                    css: function (entry) {
                      debugger;
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    }

                  }

                },
                {
                  id :  'rionPaiement',
                  name :   "Rion de paiement",
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    css : function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    },

                    toText : function(rionTheorique) {
                      return $this.getLibelleRionById(rionTheorique);
                    }
                  }
                },
                {
                  id: 'action',
                  name: "Actions",
                  sortable: false,
                  type : 'clickable-icons',
                  cell : {

                    css : function (entry) {
                      if ( entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE'
                        || entry.statutEligibilite === 'EN_COURS_DESAFFECTATION') {
                        return {style: {'background-color': 'grey'}}
                      }

                      return {style: null}
                    },

                    cellTemplate: function (cellValue) {
                      var tempalteLog = '<span class="glyphicon glyphicon-list-alt" aria-hidden="true" style="padding-left: 0px;" title="Journal"></span>';
                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;" title="Abandonner"></span>';
                      var tempalteUpdate = '<span class="glyphicon glyphicon-pencil" aria-hidden="true" style="padding-left: 0px;" title="Modifier"></span>';
                      var statusCode = cellValue.statut;

                      var tempalte = [{}, {}, {}];



                      if(cellValue.statutEligibilite === 'FIN_ELIGIBILITE' || cellValue.statutEligibilite === 'FIN_DESAFFECTATION' || cellValue.statutEligibilite === null) {
                        if(statusCode !== undefined && ('CREE' === statusCode || 'AFFECTE' === statusCode
                          || 'EN_COURS' === statusCode || 'VALIDE' === statusCode) ) {
                          if($this.isRightMDYPRG){
                            tempalte[0] = {event : 'update-programme', template : tempalteUpdate};
                          }
                        }

                        if(statusCode !== undefined && 'CREE' === statusCode) {
                          if($this.isRightABDPRG){
                            tempalte[1] = {event : 'abondon-programme', template : tempalteTrash};
                          }
                        }

                        if($this.hasRight('VIJREV')) {
                          tempalte[2] = {event : 'log-programme', template : tempalteLog};
                        }
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


      mounted() {

//        for(var i in  this.priamGrid.gridData.content) {
//
//          var prog = this.priamGrid.gridData.content[i];
//          console.log("prog = " + prog);
//          if(prog.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE'
//            || prog.statutEligibilite === 'EN_COURS_ELIGIBILITE') {
//            $("#" + prog.numProg).css("background-color", "grey");
//          }
//        }

      },

      created() {

         let rights = this.$store.getters.getCurrentUser.rights;
         if(this.hasRight('MENUCATAL') && rights.length === 1) {
             this.$router.push('/catalogue')
         }

        const customActions = {
            searchProgramme : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'},
            abandonnerProgramme : {method : 'PUT', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/abandon'},
            getAllNumProgForAutocmplete : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/numprog/autocomplete'},
            getAllNomProgForAutocmplete : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/nomprog/autocomplete'},
            validateFelixData : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/validateFelixData/{numProg}'},
            generateFelixData : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/generateFelixData'},
            checkIfDone : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/fichierfelix/{numProg}'}

        }
        this.resource= this.$resource('', {}, customActions);

        this.rechercherProgrammes();


        this.getAllNumProgramme();
        this.getAllNomProgramme();
      },

      computed : {
        isRightCRTPRG() {
         return this.hasRight('CRTPRG');
        },

        isRightMDYPRG() {
          return this.hasRight('MDYPRG');
        },

        isRightABDPRG() {
          return this.hasRight('ABDPRG');
        },

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

        afficherTootlip(entry) {

          if(entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ) {

              return "Le programme est en cours de traitement d'éligibilité et de dédoublonnage";
          } else if(entry.statutEligibilite === 'EN_COURS_DESAFFECTATION'  ) {

              return "Le programme est en cours de desaffectation";
          }
          return '';
        },

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
              this.rionTheoriqueSelected = 'ALL';
              this.rionPaiementSelected = 'ALL';
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
            this.defaultPageable.size = size;
            let pageSize = this.defaultPageable.size;

            this.launchRequest(pageNum, pageSize, sort.property, sort.direction);

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
              this.ecranAjouterProgramme = true;
              this.showEcranModal = true;
          },

          close() {
            this.showEcranModal = false;
          },

          onLogProgramme(row, column){
            this.selectedProgramme = row;
            this.showPopupLog = true;
          },

          closePopupLog(){
            this.showPopupLog = false;
          },

          onUpdateProgramme(row, column) {
             this.selectedProgramme = row;

            this.ecranAjouterProgramme = false;
            this.showEcranModal = true;
          },

          onAbondonProgramme(row, column) {
              this.showPopupAbandon = true;
              this.selectedProgramme = row;

          },

          onCellClick(row, column) {
              console.log("Clicked row row=" + row.numProg + ", column="+column.id )
              if(column.id !== undefined && column.id === 'fichiers') {
                  //redirect to Affectation.vue

                if (row.famille === FAMILLES_PRIAM['UC']) { //CMS
                    this.$router.push({ name: 'affectation-cms', params: { numProg: row.numProg }});
                } else if (row.famille === FAMILLES_PRIAM['COPIE_PRIVEE']) { //CP
                    this.$router.push({ name: 'affectation', params: { numProg: row.numProg }});
                }
              } else if(column.id !== undefined && column.id === 'numProg') {
                if (row.famille === FAMILLES_PRIAM['UC']) { //CMS
                  this.$router.push({ name: 'selection-cms', params: { numProg: row.numProg }});
                } else if (row.famille === FAMILLES_PRIAM['COPIE_PRIVEE']) { //CP
                  this.$router.push({ name: 'selection', params: { numProg: row.numProg }});
                }

              } else if(column.id !== undefined && column.id === 'repartition') {
                 var statutFichierFelix = row.statutFichierFelix !== null && row.statutFichierFelix !== undefined ? row.statutFichierFelix : undefined;
                 if(statutFichierFelix !== undefined && statutFichierFelix == 'EN_ERREUR') {
                   this.resource.checkIfDone({numProg: row.numProg})
                     .then(response => {
                       return response.json();
                     })
                     .then(fichierFelix => {
                       this.fichierFelixErrors = fichierFelix.logs;
                       this.isToShowErrors = true;
                     });

                 }
              }
          },

          rechercherProgrammes() {
              this.critereRechercheData.numProg = this.numProgSelected !== undefined && this.numProgSelected !== 'ALL' ? this.numProgSelected : null;
              this.critereRechercheData.nom = this.nomProgSelected !== undefined && this.nomProgSelected !== 'ALL' ? this.nomProgSelected : null;
              this.critereRechercheData.typeUtilisation = this.typeUtilisationSelected !== undefined ? this.typeUtilisationSelected.id : null;
              this.critereRechercheData.famille = this.familleSelected !== undefined ? this.familleSelected.id : null;
              this.critereRechercheData.rionTheorique= this.rionTheoriqueSelected !== undefined ? this.rionTheoriqueSelected : null;
              this.critereRechercheData.rionPaiement= this.rionPaiementSelected !== undefined ? this.rionPaiementSelected : null;
              this.critereRechercheData.typeRepart = this.typeRepartSelected !== undefined ? this.typeRepartSelected.id : null;

              this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
                                 this.defaultPageable.sort, this.defaultPageable.dir);


          },

          loadTypeUtilisation(val) {
              this.familleSelected = val;
              this.typeUtilisationSelected = {'id' : 'ALL', 'value': 'Tous'};
              this.$store.dispatch('loadTypeUtilisation', val);

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

          },


          onValidateMiseEnRepart(modeRepartition) {
              this.showEcranModalMisEnRepart = false;
              this.modeRepartition = modeRepartition;
              var self = this;
              let numProg = this.selectedProgramme.numProg;
              this.resource.validateFelixData({numProg:  numProg})
                .then(response => {
                  return response.json();
                })
                .then(data => {
                   self.programmesEnCoursTraitement.push(numProg);
                  // self.$store.commit('ADD_PROG_EN_COURS', numProg);
                   self.intervalIDs[numProg] = setInterval(function () {
                   self.resource.checkIfDone({numProg: numProg})
                      .then(response => {
                        return response.json();
                      })
                      .then(data => {
                          var fichierFelix = data;

                          if(fichierFelix !== undefined && fichierFelix.statut == 'GENERE') {
                            let number = self.programmesEnCoursTraitement.indexOf(numProg);
                            self.programmesEnCoursTraitement.splice(number, 1);

                            if(self.modeRepartition == 'REPART_BLANC') {
                              clearInterval(self.intervalIDs[numProg]);
                              self.downloadCsvFile(process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/downloadFichierFelix', {numProg: numProg}, fichierFelix.nomFichier);

                            } else if(self.modeRepartition == 'MISE_EN_REPART') {
                              self.programmesEnCoursTraitement.push(numProg);
                              self.resource.generateFelixData({numProg : numProg})
                                .then(response => {
                                  console.log("Genetation OK");
                                  //clearInterval(self.intervalIDs[numProg]);
                                  //self.rechercherProgrammes();

                                })
                                .catch(error => {
                                  alert("Erreur technique lors de la Genetation du fichier Felix !! ");

                                });

                            }
                          } else if(fichierFelix.statut == 'EN_ERREUR' ) {
                              clearInterval(self.intervalIDs[numProg]);
                              self.programmesEnErreur.push(numProg);

                              if(fichierFelix.logs !== undefined && fichierFelix.logs.length > 0) {
                                self.fichierFelixErrors = fichierFelix.logs;
                                self.downloadCsvFile(process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/downloadFichierFelixError',
                                  {numProg: numProg, tmpFilename : fichierFelix.nomFichier, filename : fichierFelix.nomFichier},
                                  fichierFelix.nomFichier);
                              }
                          } else if(fichierFelix.statut == 'ENVOYE' ) {
                              let number = self.programmesEnCoursTraitement.indexOf(numProg);
                              self.programmesEnCoursTraitement.splice(number, 1);
                              clearInterval(self.intervalIDs[numProg]);
                              self.rechercherProgrammes();
                          }

                        });
                  },

                    1000);

              });

          },

        downloadCsvFile(url, data, filename) {
          var _open = function(verb, url, data, target) {
            var form = document.createElement('form');
            form.action = url;
            form.method = verb;
            form.target = target || '_self';
            if (data) {
              for (var key in data) {
                var input = document.createElement('textarea');
                input.name = key;
                input.value = typeof data[key] === 'object' ? JSON.stringify(data[key]) : data[key];
                form.appendChild(input);
              }
            }
            document.body.appendChild(form);
            form.submit();
            document.body.removeChild(form);
          };

          _open('POST', url, data, '_blank');

        }


      },

      components : {
        JournalProgramme,
        priamGrid : Grid,
          ecranModal : EcranModal,
          modifierProgramme : ModifierProgramme,
          ajouterProgramme : AjouterProgramme,
          vSelect : vSelect,
          datePicker : DatePicker,
          modal: Modal,
          autocomplete : Autocomplete,
          select2 :Select2,
          miseEnRepartitionProgramme : MiseEnRepartitionProgramme,
          navbar : Navbar
      }

  }

</script>
