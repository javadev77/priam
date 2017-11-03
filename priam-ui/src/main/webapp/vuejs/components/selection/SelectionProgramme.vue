<template>

  <div>
    <div class="container-fluid">
      <div class="navbar navbar-default navbar-sm breadcrumb">
        <div class="titre-page">
          <a class="btn-back no-select" @click="goBack()" v-shortkey="['f8']" @shortkey="goBack()" >
            <span class="glyphicon glyphicon-chevron-left"></span>Retour (F8)
          </a>
          <span>Programme <span class="glyphicon glyphicon-chevron-right"></span>
              Liste programmes <span class="glyphicon glyphicon-chevron-right"></span>
              Sélection
            </span>
        </div>
      </div>
      <div class="container-fluid sacem-formula">

        <!--En tete Panel-->
        <div class="panel panel-default">
          <div class="panel-heading">
            <h5 class="panel-title"  @click="isCollapsed = !isCollapsed">
              <a>Programme</a>
              <span class="pull-left collapsible-icon"><img src="static/images/iconescontextes/btninformation.gif" alt=""/></span>
              <span class="pull-right fa" :class="{'fui-triangle-up' : isCollapsed,  'fui-triangle-down' : !isCollapsed}"></span>
            </h5>
          </div>

          <!--Body Panel-->
          <div class="panel-collapse" :class="{collapse : isCollapsed}">
            <app-programme-info
              :programmeInfo="programmeInfo">
            </app-programme-info>
          </div>
        </div>
      </div>

      <app-filtre-selection
        :filter="filter"
        :retablir="retablirFiltre"
        :rechercher="rechercher"
        :ajouter="ajouterOeuvre"
        :edition="edition"
      >
      </app-filtre-selection>
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
            <app-informations-selection
              :dataLoadingDuree="dataLoadingDuree"
              :dureeSelection="dureeSelection"
              :typeUtilisation="programmeInfo.typeUtilisation"
            >
            </app-informations-selection>

            <div class="row center-div">
              <div class="spinner" v-if="dataLoading">
                <div class="rect1"></div>
                <div class="rect2"></div>
                <div class="rect3"></div>
                <div class="rect4"></div>
                <div class="rect5"></div>
              </div>
            </div>




            <div v-if = "!dataLoading && this.programmeInfo.typeUtilisation=='CPRIVSONRD'">
              <priam-grid
                v-if="priamGrid_sono.gridData_sono.content"
                :data="priamGrid_sono.gridData_sono"
                :columns="priamGrid_sono.gridColumns"
                noResultText="Aucun résultat."
                :filter-key="priamGrid_sono.searchQuery"
                @load-page="loadPage"
                @on-sort="onSort"
                @all-checked="onAllChecked"
                @entry-checked="onEntryChecked"
                @supprimer-ligne-programme="onSupprimerLigneProgramme">
              </priam-grid>
            </div>
            <div v-else-if = "!dataLoading && this.programmeInfo.typeUtilisation=='CPRIVSONPH'">
              <priam-grid
                v-if="priamGrid_phono.gridData_phono.content"
                :data="priamGrid_phono.gridData_phono"
                :columns="priamGrid_phono.gridColumns"
                noResultText="Aucun résultat."
                :filter-key="priamGrid_phono.searchQuery"
                @load-page="loadPage"
                @on-sort="onSort"
                @all-checked="onAllChecked"
                @entry-checked="onEntryChecked"
                @supprimer-ligne-programme="onSupprimerLigneProgramme">
              </priam-grid>
            </div>
          </div>

        </div>
      </div>
      <app-action-selection
        :programmeInfo="programmeInfo"
        :listSelectionVide="ligneProgramme.length == 0"
        :valider="validerSelection"
        :editer="editSelectionClickHandler"
        :invalider="invaliderProgramme"
        :edition="edition"
        :enregistrerEdition="enregistrerEdition"
        :annulerEdition="annulerEdition"
        :annulerSelection="annulerSelection"
        :inProcess="inProcess"
        :isLoadingDuree="dataLoadingDuree"
      >
      </app-action-selection>

    </div>


    <modal v-if="modalVisible">
      <label class="homer-prompt-q control-label" slot="body">
        {{modalMessage}}
      </label>
      <template slot="footer">

        <div v-if="modalWaring" class="columnCenter">
          <button class="btn btn-default btn-primary no" @click="closeModal" :disabled="inProcess">Fermer</button>
        </div>

        <div v-else>
          <button class="btn btn-default btn-primary pull-right no" @click="noContinue" :disabled="inProcess">Non</button>
          <button class="btn btn-default btn-primary pull-right yes" @click="yesContinue" :disabled="inProcess">Oui</button>
        </div>

      </template>
    </modal>


    <modal v-if="showPopupSuppression">
      <label class="homer-prompt-q control-label" slot="body">
        Etes-vous sûr de vouloir supprimer définitivement cette oeuvre du programme?
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showPopupSuppression = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="supprimerProgramme">Oui</button>
      </template>
    </modal>


  </div>
</template>

<script>
  import chargementMixins from '../../mixins/chargementMixin';
  import vSelect from '../common/Select.vue';
  import Grid from '../common/Grid.vue';
  import Modal from '../common/Modal.vue';
  import moment from 'moment';
  import FiltreSelection from './FiltreSelection.vue';
  import InformationsSelection from './InformationsSelection.vue';
  import ActionSelection from './ActionSelection.vue';
  import ProgrammeInfo from '../programme/ProgrammeInfo.vue';

  export default {
    mixins: [chargementMixins],

    data(){

      var $this =this;

      return {

        all : false,
        edition : false,
        ligneProgramme : [],
        unselectedLigneProgramme : [],
        ligneProgrammeSelected : [],
        programmeInfo: {},
        tableauSelectionnable : false,
        isCollapsed: false,
        defaultPageable : {
          page : 1,
          sort : 'ide12',
          dir : 'desc',
          size : this.$store.getters.userPageSize
        },

        currentGridState : {},

        priamGrid_phono: {
          gridColumns: [
            {
              id: 'libAbrgUtil',
              name: "Utilisateur",
              sortable: true,
              sortProperty : 'cdeUtil',
              type: 'long-text',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'ide12',
              name: 'IDE12',
              sortable: true,
              type: 'text-centre',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'titreOeuvre',
              name: "Titre",
              sortable: true,
              type: 'long-text',
              cell : {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'roleParticipant1',
              name: 'Rôle',
              sortable: true,
              type: 'text-centre',
              cell : {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'nomParticipant1',
              name: "Participant",
              sortable: true,
              type: 'long-text',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'nbrDif',
              name: "Quantité",
              sortable: true,
              sortProperty : 'sum(nbrDif)',
              type: 'numeric',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'ajout',
              name: "Ajout",
              sortable: true,
              type: 'text-with-action',
              cell: {
                toText : function(entry) {
                  var result = entry;

                  if(result !=undefined)
                  {
                    if(result.ajout == 'Manuel') {

                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;" title="Supprimer"></span>';
                      var template = [];
                      template.push({event : 'supprimer-ligne-programme', template : tempalteTrash, disabled : !$this.edition});
                      return {value : result.ajout, template : template ,action : true};

                    }else {
                      return {value : result, action : false};
                    }

                  }
                  else
                    return "";
                }
              }
            },
            {
              id: 'action',
              name: "Actions",
              sortable: false,
              type: 'checkbox',
              cell: {
                toText: function (entry) {
                  return JSON.stringify({ide12 : entry.ide12, libAbrgUtil : entry.libAbrgUtil});
                },

                isDisabled: function () {

                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                },

                isChecked: function (entry) {
                  /*var notChecked = $this.unselectedLigneProgramme.find(elem => {
                    return elem.ide12 == entry.ide12 && elem.libAbrgUtil == entry.libAbrgUtil;
                  });

                  if (notChecked !== undefined) {
                    return 0;
                  }

                  var result = $this.ligneProgrammeSelected.find(elem => {
                    return elem.ide12 == entry.ide12 && elem.libAbrgUtil == entry.libAbrgUtil;
                  });

                  if (result !== undefined) {
                    return 1;
                  }*/

                  if(entry.selection) {
                    return 1;
                  }
                  return 0;
                },

                isAllNotChecked: function () {

                  if ($this.ligneProgrammeSelected.length > 1) {
                    return false;
                  }
                  return true;
                }
              }
            }

          ],
          gridData_phono : {},
          searchQuery : ''
        },
        priamGrid_sono: {
          gridColumns: [
            {
              id: 'libAbrgUtil',
              name: "Utilisateur",
              sortable: true,
              sortProperty : 'cdeUtil',
              type: 'long-text',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'ide12',
              name: "IDE12",
              sortable: true,
              type: 'text-centre',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'titreOeuvre',
              name: "Titre",
              sortable: true,
              type: 'long-text',
              cell : {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'roleParticipant1',
              name: 'Rôle',
              sortable: true,
              type: 'text-centre',
              cell : {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },
            {
              id: 'nomParticipant1',
              name: "Participant",
              sortable: true,
              type: 'long-text',
              cell: {
                toText : function(entry) {
                  var result = entry;
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'durDif',
              name: "Durée",
              sortable: true,
              sortProperty : 'sum(durDif)',
              type: 'seconds-as-time',
              cell: {
                toText : function(entry) {
                  var result = entry;

                  if(result !=undefined)
                    return result;
                  else
                    return "";
                }
              }
            },
            {
              id: 'ajout',
              name: "Ajout",
              sortable: true,
              type: 'text-with-action',
              cell: {
                toText : function(entry) {
                  var result = entry;

                  if(result !=undefined)
                  {
                    if(result.ajout == 'Manuel') {

                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;" title="Supprimer"></span>';
                      var template = [];
                      template.push({event : 'supprimer-ligne-programme', template : tempalteTrash, disabled : !$this.edition});
                      return {value : result.ajout, template : template ,action : true};

                    }else {
                      return {value : result, action : false};
                    }

                  }
                  else
                    return "";
                }
              }
            },
            {
              id: 'action',
              name: "Actions",
              sortable: false,
              type: 'checkbox',
              cell: {
                toText: function (entry) {
                  return JSON.stringify({ide12 : entry.ide12, libAbrgUtil : entry.libAbrgUtil});
                },

                isDisabled: function () {

                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                },

                isChecked: function (entry) {

                  debugger;
                  /*var notChecked = $this.unselectedLigneProgramme.find(elem => {
                    return elem.ide12 == entry.ide12 && elem.libAbrgUtil == entry.libAbrgUtil;
                  });

                  if (notChecked !== undefined) {
                    return 0;
                  }

                  var result = $this.ligneProgrammeSelected.find(elem => {
                    return elem.ide12 == entry.ide12 && elem.libAbrgUtil == entry.libAbrgUtil;
                  });

                  if (result !== undefined) {
                    return 1;
                  }*/

                  if(entry.selection) {
                      return 1;
                  }
                  return 0;
                },

                isAllNotChecked: function () {

                  if ($this.ligneProgrammeSelected.length > 1) {
                    return false;
                  }
                  return true;
                }
              }
            }
          ],
          gridData_sono : {},
          searchQuery : ''
        },

        currentFilter : {
          ide12 : null,
          numProg : this.$route.params.numProg,
          utilisateur : 'Tous',
          titre : null,
          ajout : 'Tous',
          selection : 'Tous'
        },

        filter : {
          ide12 : null,
          numProg : this.$route.params.numProg,
          utilisateur : 'Tous',
          titre : null,
          ajout : 'Tous',
          selection : 'Tous'
        },
        dureeSelection : {
          auto : 0,
          manuel : 0,
          duree : 0
        },
        modalVisible : false,
        modalMessage : '',
        modalWaring : false,
        inProcess : false,
        backupDureeSelection : {},

        dataLoading : false,
        dataLoadingDuree : false,
        showPopupSuppression : false,
        isActionAnnulerEdition : false
      }
    },

    created() {
      this.initProgramme();
    },

    methods :{
      goBack() {
        this.$router.back();
      },

      retablirFiltre() {
        this.filter = {
          ide12 : null,
          numProg : this.$route.params.numProg,
          utilisateur : 'Tous',
          titre : null,
          ajout : 'Tous',
          selection : 'Tous'
        }

        this.rechercher();
      },

      getDuree: function (statut) {
        this.dataLoadingDuree = true;
        this.resource.dureeProgramme({numProg: this.$route.params.numProg, statut: statut})
          .then(response => {
          return response.json();
        }).then(data => {

          this.dureeSelection.auto = data.Automatique;
          this.dureeSelection.manuel = data.Manuel;
          this.dureeSelection.duree = data.SOMME;

          this.backupDureeSelection = {
            auto: this.dureeSelection.auto,
            manuel: this.dureeSelection.manuel,
            duree: this.dureeSelection.duree
          }

          this.dataLoadingDuree = false;
      })
        ;
      },
      initProgramme() {
        console.log("router params numProg = " + this.$route.params.numProg);

        const customActions = {
          findByNumProg: {method: 'GET', url: 'app/rest/programme/numProg/{numProg}'},
          findLigneProgrammeByProgramme: {
            method: 'POST',
            url: 'app/rest/ligneProgramme/search?page={page}&size={size}&sort={sort},{dir}'
          },
          validerSelection: {method: 'POST', url: 'app/rest/ligneProgramme/selection/valider'},
          modifierSelection: {method: 'POST', url: 'app/rest/ligneProgramme/selection/modifier'},
          invaliderSelection: {method: 'POST', url: 'app/rest/ligneProgramme/selection/invalider'},
          dureeProgramme: {method: 'GET', url: 'app/rest/programme/durdif?numProg={numProg}&statut={statut}'},
          annulerSelection: {method: 'POST', url: 'app/rest/ligneProgramme/selection/annuler'},
          supprimerLigneProgramme: {method: 'DELETE', url: 'app/rest/ligneProgramme/{numProg}/{ide12}/'},
          calculerDureeAllSelection: {method: 'POST', url: 'app/rest/ligneProgramme/durdifAllSelect'},
          enregistrerEdition : {method: 'POST', url: 'app/rest/ligneProgramme/selection/enregistrerEdition'},
          annulerEdition : {method: 'POST', url: 'app/rest/ligneProgramme/selection/annulerEdition'}
        }

        this.resource = this.$resource('', {}, customActions);

        this.resource.findByNumProg({numProg: this.$route.params.numProg})
          .then(response => {
            return response.json();
          })
          .
          then(data => {

              this.programmeInfo = data;

             this.$store.dispatch('setCurrentProgrammeEnSelection', this.programmeInfo);

              this.all = this.programmeInfo.statut != 'VALIDE';
              this.tableauSelectionnable = (
                this.programmeInfo.statut != 'VALIDE'
                && this.programmeInfo.statut != 'MIS_EN_REPART'
                && this.programmeInfo.statut != 'REPARTI'
              );

              this.tableauSelectionnable = false;


            if(this.programmeInfo.typeUtilisation==="CPRIVSONPH"){
              this.defaultPageable.sort = 'nbrDif';
            }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD") {
              this.defaultPageable.sort = 'durDif';
            }

            if(this.programmeInfo.statut == 'EN_COURS' || this.programmeInfo.statut == 'VALIDE') {
              this.filter.selection = 'Sélectionné';
              this.all = false;
            }
            this.rechercher();

          });
      },

      onSupprimerLigneProgramme(row, column) {
        this.showPopupSuppression = true;
        this.selectedLineProgramme = row;

      },

      supprimerProgramme() {

        this.resource.supprimerLigneProgramme({numProg : this.programmeInfo.numProg, ide12 : this.selectedLineProgramme.ide12}, this.selectedLineProgramme)
          .then(response => {
            return response.json();
          })
          .then(data => {
              this.showPopupSuppression = false;
              //this.launchRequest(this.currentGridState.pageNum, this.currentGridState.pageSize, this.currentGridState.sort, this.currentGridState.dir);
              var index = this.indexOf(this.ligneProgramme, this.selectedLineProgramme);

              if (index > -1) {
                this.ligneProgramme.splice(index, 1);
              }
              this.getDuree(this.programmeInfo.statut);
          });

      },

      launchRequest(pageNum, pageSize, sort, dir) {

        this.currentGridState = {
          pageNum : pageNum, pageSize : pageSize, sort : sort, dir :dir
        };

        this.dataLoading = true;

        this.resource.findLigneProgrammeByProgramme({page : pageNum -1 , size : pageSize,
          sort : sort, dir: dir}, this.currentFilter )
          .then(response => {
            return response.json();
          })
          .then(data => {

            this.dataLoading = false;
            if(this.programmeInfo.typeUtilisation==='CPRIVSONPH'){

              this.priamGrid_phono.gridData_phono = data;
              this.priamGrid_phono.gridData_phono.number = data.number + 1;
              this.ligneProgramme = this.priamGrid_phono.gridData_phono.content;

            }else if (this.programmeInfo.typeUtilisation==='CPRIVSONRD'){

              this.priamGrid_sono.gridData_sono = data;
              this.priamGrid_sono.gridData_sono.number = data.number + 1;
              this.ligneProgramme = this.priamGrid_sono.gridData_sono.content;
            }

            //this.$store.dispatch('toutDesactiver', this.countNbSelected(this.ligneProgramme) == this.ligneProgramme.length);


            //this.selectAll();
          });
      },

      onSort(currentPage, pageSize, sort) {


        this.modifierSelectionTemporaire();

        this.resource.modifierSelection(this.selection)
          .then(response => {
            return response.json();
          }).then(data => {


          this.launchRequest(currentPage, pageSize, sort.property, sort.direction);

          this.defaultPageable.sort = sort.property;
          this.defaultPageable.dir = sort.direction;

        })
          .catch(response => {
            console.log("Erreur technique lors de la validation de la selection du programme !! " + response);
          });


      },

      loadPage: function(pageNum, size, sort) {
        this.defaultPageable.size = size;
        let pageSize = this.defaultPageable.size;


        this.modifierSelectionTemporaire();

        this.resource.modifierSelection(this.selection)
          .then(response => {
            return response.json();
          }).then(data => {

            this.launchRequest(pageNum, pageSize, sort.property, sort.direction);

        }).catch(response => {
            console.log("Erreur technique lors de la validation de la selection du programme !! " + response);
          });


      },

      // TODO a definir
      ajouterOeuvre(){

      },

      selectAll() {

        debugger;
        //if(!this.all) {
          for (var i in this.ligneProgramme) {
            if (this.indexOf(this.ligneProgrammeSelected, this.ligneProgramme[i]) == -1
              && this.indexOf(this.unselectedLigneProgramme, this.ligneProgramme[i]) == -1) {

              if (this.ligneProgramme[i].selection) {
                this.ligneProgrammeSelected.push(this.ligneProgramme[i]);
              } else {
                this.unselectedLigneProgramme.push(this.ligneProgramme[i]);
              }

            }
          }


      },

      rechercher(){

        function doSearch() {
          this.dataLoading = true;
          this.resource.findLigneProgrammeByProgramme({
            page: this.defaultPageable.page - 1, size: this.defaultPageable.size,
            sort: this.defaultPageable.sort, dir: this.defaultPageable.dir
          }, this.filter)
            .then(response => {
              return response.json();
            })
            .then(data => {

              var tab = [];
              if (this.programmeInfo.typeUtilisation === "CPRIVSONPH") {

                this.priamGrid_phono.gridData_phono = data;
                this.priamGrid_phono.gridData_phono.number = data.number + 1;
                tab = this.priamGrid_phono.gridData_phono.content;

              } else if (this.programmeInfo.typeUtilisation === "CPRIVSONRD") {

                this.priamGrid_sono.gridData_sono = data;
                this.priamGrid_sono.gridData_sono.number = data.number + 1;
                tab = this.priamGrid_sono.gridData_sono.content;
              }

              this.ligneProgramme = tab;
              this.dataLoading = false;

             // this.$store.dispatch('toutDesactiver', this.countNbSelected(this.ligneProgramme) == this.ligneProgramme.length);
              //this.selectAll();
            });
        }

        this.currentFilter.ide12 = this.filter.ide12;
        this.currentFilter.ajout = this.filter.ajout;
        this.currentFilter.numProg = this.filter.numProg;
        this.currentFilter.utilisateur = this.filter.utilisateur;
        this.currentFilter.titre = this.filter.titre;
        this.currentFilter.selection = this.filter.selection;

        if(this.isActionAnnulerEdition) {
          doSearch.call(this);
          this.getDuree(this.programmeInfo.statut);
        } else {
          this.modifierSelectionTemporaire();

          this.resource.modifierSelection(this.selection)
            .then(response => {
              return response.json();
            }).then(data => {

            doSearch.call(this);
            this.getDuree(this.programmeInfo.statut);

          })
            .catch(response => {
              console.log("Erreur technique lors de la validation de la selection du programme !! " + response);
            });
        }

      },



      isTableauSelectionnable() {
        return this.tableauSelectionnable;
      },

      onEntryChecked(isChecked, entryChecked) {

        if(isChecked) {

          if(entryChecked.ajout == 'Manuel') {
            this.dureeSelection.manuel ++;

          } else {
            this.dureeSelection.auto++;
          }

          if(this.programmeInfo.typeUtilisation==="CPRIVSONPH"){
            this.dureeSelection.duree += entryChecked.nbrDif;
          }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD"){
            this.dureeSelection.duree += entryChecked.durDif;
          }

          var found = this.ligneProgrammeSelected.find( elem => {
            return  elem.ide12 === entryChecked.ide12 && elem.libAbrgUtil === entryChecked.libAbrgUtil;
          });
          if(found !== undefined && found) {

            let number = this.indexOf(this.unselectedLigneProgramme, entryChecked);
            this.unselectedLigneProgramme.splice(number, 1);
          } else {

            let number = this.indexOf(this.unselectedLigneProgramme, entryChecked);
            this.unselectedLigneProgramme.splice(number, 1);

            this.ligneProgrammeSelected.push(entryChecked);
          }

        } else {

          if(entryChecked.ajout == 'Manuel') {
            this.dureeSelection.manuel--;
          } else {
            this.dureeSelection.auto--;
          }

          if(this.programmeInfo.typeUtilisation==="CPRIVSONPH"){
            this.dureeSelection.duree -= entryChecked.nbrDif;
          }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD"){
            this.dureeSelection.duree -= entryChecked.durDif;
          }

          let number = this.indexOf(this.ligneProgrammeSelected, entryChecked);
          this.ligneProgrammeSelected.splice(number, 1);

          this.unselectedLigneProgramme.push(entryChecked);
        }

        entryChecked.selection = isChecked;
       // this.$store.dispatch('toutDesactiver', this.countNbSelected(this.ligneProgramme) == this.ligneProgramme.length);


        /*this.selection = {
          deselectAll : false,
          all : false,
          unselected : this.unselectedLigneProgramme,
          selected : this.ligneProgrammeSelected
        };

        this.selection.numProg = this.$route.params.numProg;
        this.resource.modifierSelection(this.selection)
          .then(response => {
            return response.json();
          })
          .then(data => {
            //this.selectedLineProgramme = [];
            //this.unselectedLigneProgramme = [];
          })
          .catch(response => {
            alert("Erreur technique lors de la validation de la selection du programme !! " + response);
          });*/

         console.log('onEntryChecked() ==> this.ligneProgrammeSelected='+this.ligneProgrammeSelected.length);
      },

      indexOf(array, obj) {

          for(let i = 0; i < array.length; i++ ) {
              if(obj.ide12 == array[i].ide12 && obj.libAbrgUtil == array[i].libAbrgUtil) {
                  return i;
              }
          }

          return -1;

      },

      onAllChecked(allChecked, entries) {

        this.all = allChecked;
        this.ligneProgrammeSelected = [];
        this.unselectedLigneProgramme = [];
        /*if(allChecked) {
          for(var i in entries) {
              this.ligneProgrammeSelected.push(entries[i]);
          }

        } else {
          for(var i in entries) {
            this.unselectedLigneProgramme.push(entries[i]);
          }
        }*/

        for(var i in entries) {
            let index = this.indexOf(this.ligneProgramme, entries[i]);
            if(index > -1 && this.ligneProgramme[index].selection !== allChecked) {
              this.ligneProgramme[index].selection = allChecked;
              this.recalculerCompteurs(this.ligneProgramme[index]);
            }

        }

        //this.$store.dispatch('toutDesactiver', this.all);
       /* this.selection = {
          deselectAll : false,
          all : false,
          unselected : this.unselectedLigneProgramme,
          selected : this.ligneProgrammeSelected
        };

        this.selection.numProg = this.$route.params.numProg;
        this.dataLoadingDuree = true;
        this.resource.modifierSelection(this.selection)
          .then(response => {
            return response.json();
          })
          .then(data => {
            //this.selectedLineProgramme = [];
            //this.unselectedLigneProgramme = [];
            this.getDuree(this.programmeInfo.statut);
          })
          .catch(response => {
            console.log("Erreur technique lors de la validation de la selection du programme !! " + response);
          });*/

      },

      recalculerCompteurs(entry) {
        if(entry.ajout == 'Manuel') {
          if(entry.selection) {
            this.dureeSelection.manuel++;
          } else {
            this.dureeSelection.manuel--;
          }

        } else {
          if(entry.selection) {
            this.dureeSelection.auto++;
          } else {
            this.dureeSelection.auto--;
          }
        }

        let duree;
        if(this.programmeInfo.typeUtilisation==="CPRIVSONPH"){
          duree = entry.nbrDif;
        }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD"){
          duree = entry.durDif;
        }

        if(entry.selection) {
          this.dureeSelection.duree += duree;
        } else {
          this.dureeSelection.duree -= duree;
        }
      },



      onSelectAll() {
        this.valider({
          all : true,
          unselected : [],
          selected : []
        });
      },

      select() {
        this.valider({
          all : false,
          unselected : [],
          selected : this.ligneProgrammeSelected
        });
      },

      unselect() {
        this.valider({
          all : false,
          unselected : this.unselectedLigneProgramme,
          selected : []
        });
      },

      valider(selection) {
        debugger;
        this.selection = selection;

        if(this.programmeInfo.statut == 'AFFECTE' || this.programmeInfo.statut == 'EN_COURS') {

          if(this.programmeInfo.typeUtilisation == 'CPRIVSONRD' && this.dureeSelection.duree == 0) {
            this.modalWaring  = true;
            this.modalVisible = true;
            this.modalMessage  = 'Attention la somme des durées sélectionnées sur le programme égale à 0';
            return;
          }

          if(this.programmeInfo.typeUtilisation == 'CPRIVSONPH' && this.dureeSelection.duree == 0) {
            this.modalWaring  = true;
            this.modalVisible = true;
            this.modalMessage  = 'Attention la somme des quantités sélectionnées sur le programme égale à 0';
            return;
          }
        }
        this.modalWaring  = false;
        this.modalVisible = true;
        this.modalMessage = 'Etes-vous sûr de vouloir valider cette sélection?';

      },

      validerSelection () {
        debugger;
        /*if(this.all) {
          if(this.unselectedLigneProgramme.length != 0) {
            this.unselect();
          } else {
            this.onSelectAll();
          }
        } else {
          if(this.ligneProgrammeSelected.length == 0) {

          } else {
            if(this.ligneProgrammeSelected.length > 0) {
              this.select();
            } else {
              this.unselect();
            }
          }
        }*/

        this.valider({
          all : false,
          unselected : [],
          selected : []
        });
      },

      closeModal () {
        this.modalVisible = false;
        this.annulerAction = undefined;
      },

      noContinue() {
        this.modalVisible = false;
        this.annulerAction = undefined;
        this.$emit('cancel');
      },

      yesContinue() {

        if(this.annulerAction != undefined &&  this.annulerAction == true) {


          this.inProcess = true;

          this.selection = {
            numProg : this.$route.params.numProg
          };

          this.resource.annulerSelection(this.selection)
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.inProcess = false;
              this.modalVisible = false;
              this.$emit('cancel');
              this.$router.push({ name: 'programme'});
            })
            .catch(response => {
              alert("Erreur technique lors de la validation de la selection du programme !! " + response);
            });
        }else
        if(this.annulerAction != true &&  this.programmeInfo.statut == 'VALIDE') {
          this.inProcess = true;

          this.resource.invaliderSelection(this.$route.params.numProg)
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.inProcess = false;
              this.modalVisible = false;
              this.$emit('cancel');

              this.initProgramme();
            })
            .catch(response => {
              alert("Erreur technique lors de la validation de la selection du programme !! " + response);
            });
        }else if(this.annulerAction != true) {
          this.selection.numProg = this.$route.params.numProg;

          this.inProcess = true;

          this.resource.validerSelection(this.selection)
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.inProcess = false;
              this.modalVisible = false;
              this.$emit('cancel');
              this.$router.push({ name: 'programme'});
            })
            .catch(response => {
              alert("Erreur technique lors de la validation de la selection du programme !! " + response);
            });
        }


        this.annulerAction = undefined;

      },

      invaliderProgramme() {
        this.modalVisible = true;
        this.modalMessage  = 'Etes-vous sûr de vouloir invalider ce programme?';
      },

      enregistrerEdition() {

        this.inProcess = true;

        this.tableauSelectionnable = false;
        this.modifierSelectionTemporaire();

        this.resource.modifierSelection(this.selection)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.selection = {
              deselectAll : false,
              all : false,
              unselected : [],
              selected : []
            };

            this.selection.numProg = this.$route.params.numProg;
            this.resource.enregistrerEdition(this.selection)
              .then(response => {
                this.filter.selection = 'Sélectionné';
                this.selectedLineProgramme = [];
                this.unselectedLigneProgramme = [];
                this.tableauSelectionnable = false;
                this.edition = false;
                this.inProcess = false;
                this.initProgramme();
              });
          })
          .catch(response => {
            alert("Erreur technique lors de la validation de la selection du programme !! " + response);
          });





      },

      modifierSelectionTemporaire() {

        this.unselectedLigneProgramme = [];
        this.ligneProgrammeSelected= [];

        for(var i in this.ligneProgramme) {
          if(this.ligneProgramme[i].selection) {
            this.ligneProgrammeSelected.push(this.ligneProgramme[i]);

          } else {
            this.unselectedLigneProgramme.push(this.ligneProgramme[i]);
          }
        }

        this.selection = {
          deselectAll : false,
          all : false,
          unselected : this.unselectedLigneProgramme,
          selected : this.ligneProgrammeSelected
        };

        this.selection.numProg = this.$route.params.numProg;
      },

      annulerSelection() {

        this.annulerAction = true;
        this.modalVisible = true;
        this.tableauSelectionnable = true;
        this.modalMessage  = 'Toutes les opérations seront perdues. Etes-vous sûr de vouloir annuler la sélection?';


      },

      annulerEdition () {
        this.isActionAnnulerEdition = true;
        this.resource.annulerEdition({numProg : this.$route.params.numProg})
          .then(response => {
            this.selectedLineProgramme = [];
            this.unselectedLigneProgramme = [];
            this.rechercher();
            this.tableauSelectionnable = false;
            this.edition = false;
            this.inProcess = false;
            this.getDuree(this.programmeInfo.statut);
        });

      },

      editSelectionClickHandler () {
        this.tableauSelectionnable = true;
        this.edition = true;
      }

    },

    computed : {
      totalDureeSelection () {
        return this.dureeSelection.auto  + this.dureeSelection.manuel;
      },

      countNbSelected() {
        var count = 0;
        for(var i in this.ligneProgramme) {
          if(this.ligneProgramme[i].selection) {
            count++;
          }
        }

        return count;
      },

      lengthOfTabLigneProgramme() {
       return this.ligneProgramme.length;
      }
    },

    watch: {
      lengthOfTabLigneProgramme : function (newValue) {
        console.log("The length of tab has changed  "+  newValue);
        this.$store.dispatch('toutDesactiver', newValue && newValue === this.countNbSelected);
      },

      countNbSelected : function (newValue) {
        console.log("Le nombre de selection a cahnge "+  newValue);
        this.$store.dispatch('toutDesactiver', newValue && newValue === this.lengthOfTabLigneProgramme);
      }

    },

    components : {
      vSelect : vSelect,
      priamGrid : Grid,
      modal: Modal,
      appFiltreSelection : FiltreSelection,
      appInformationsSelection : InformationsSelection,
      appActionSelection : ActionSelection,
      appProgrammeInfo : ProgrammeInfo
    }
  }

</script>

<style>

  .spinner {
    float:  left;
    width: 50px;
    height: 40px;
    text-align: center;
    font-size: 10px;
  }

  .spinner > div {
    background-color: #333;
    height: 100%;
    width: 6px;
    display: inline-block;

    -webkit-animation: sk-stretchdelay 1.2s infinite ease-in-out;
    animation: sk-stretchdelay 1.2s infinite ease-in-out;
  }

  .spinner .rect2 {
    -webkit-animation-delay: -1.1s;
    animation-delay: -1.1s;
  }

  .spinner .rect3 {
    -webkit-animation-delay: -1.0s;
    animation-delay: -1.0s;
  }

  .spinner .rect4 {
    -webkit-animation-delay: -0.9s;
    animation-delay: -0.9s;
  }

  .spinner .rect5 {
    -webkit-animation-delay: -0.8s;
    animation-delay: -0.8s;
  }

  @-webkit-keyframes sk-stretchdelay {
    0%, 40%, 100% { -webkit-transform: scaleY(0.4) }
    20% { -webkit-transform: scaleY(1.0) }
  }

  @keyframes sk-stretchdelay {
    0%, 40%, 100% {
      transform: scaleY(0.4);
      -webkit-transform: scaleY(0.4);
    }  20% {
         transform: scaleY(1.0);
         -webkit-transform: scaleY(1.0);
       }
  }

  .center-div {
    width: 0%;
    margin: 0 auto;
  }
</style>
