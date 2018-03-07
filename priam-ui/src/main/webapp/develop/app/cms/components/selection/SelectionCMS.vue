<template>

  <div>
    <div class="container-fluid">

      <navbar titre1="Programme" titre2="Liste programmes" titre3="Sélection" :backButton="true"></navbar>

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
              <template slot="extra-info" v-if="infoTraitementCMS.dateFinTmt !== null">
                <div class="form-group col-md-6">
                  <label class="col-md-9 control-label blueText text-right">Fin traitement CMS</label>
                  <div class="col-md-10 control-label">
                    <a @click="openPopupInfoTraitementCMS" style="text-decoration: underline;">
                      {{ infoTraitementCMS.dateFinTmt | formatDate('DD/MM/YYYY à HH:mm')}}
                    </a>
                  </div>
                </div>
              </template>
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

           <div v-if = "!dataLoading">
              <priam-grid
                v-if="priamGrid_SONOCMS.gridData_SONOCMS.content"
                :data="priamGrid_SONOCMS.gridData_SONOCMS"
                :columns="priamGrid_SONOCMS.gridColumns"
                noResultText="Aucun résultat."
                :filter-key="priamGrid_SONOCMS.searchQuery"
                @load-page="loadPage"
                @on-sort="onSort"
                @all-checked="onAllChecked"
                @entry-checked="onEntryChecked"
                @onCellValueChanged="onCellValueChanged"
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


    <modal v-if="showPopupInfoTmtCMS">
      <label class="homer-prompt-q control-label" slot="body">
        Démarrage traitement le {{ infoTraitementCMS.dateDebutTmt | formatDate('DD/MM/YYYY à HH:mm') }}<br/><br/>
          <ul>
            <li>Oeuvres extraites : {{ infoTraitementCMS.nbOeuvresExtraction | numberFormat}}</li>
            <li>Oeuvres catalogue : {{ infoTraitementCMS.nbOeuvresCatalogue | numberFormat}}</li>
            <li>Oeuvres retenues : {{ infoTraitementCMS.nbOeuvresRetenues | numberFormat}}</li>
            <li>Somme points :{{  infoTraitementCMS.sommePoints | numberFormat}}</li>
          </ul>
        Fin traitement le {{ infoTraitementCMS.dateFinTmt | formatDate(' DD/MM/YYYY à HH:mm') }}
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showPopupInfoTmtCMS = false">Fermer</button>
      </template>
    </modal>



  </div>
</template>

<script>
  import chargementMixins from '../../../common/mixins/chargementMixin';
  import vSelect from '../../../common/components/ui/Select.vue';
  import Grid from '../../../common/components/ui/Grid.vue';
  import Modal from '../../../common/components/ui/Modal.vue';
  import moment from 'moment';
  import FiltreSelection from './FiltreSelection.vue';
  import InformationsSelection from './InformationsSelection.vue';
  import ActionSelection from './ActionSelection.vue';
  import ProgrammeInfo from '../../../common/components/programme/ProgrammeInfo.vue';
  import Navbar from '../../../common/components/ui/priam-navbar.vue';

  import PointsMontantEditor from './cellEditors/PointsMontantEditor.vue';
  import PointsEditor from './cellEditors/PointsEditor.vue';

  export default {
    mixins: [chargementMixins],

    data(){

      var $this =this;

      return {

        infoTraitementCMS : {

          dateDebutTmt : Date,
          dateFinTmt : Date,

          nbOeuvresCatalogue : Number,
          nbOeuvresRetenues : Number,

          nbOeuvresExtraction : Number,

          sommePoints : Number

        },

        showPopupInfoTmtCMS : false,

        pointsSelection : 0,

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

        priamGrid_SONOCMS: {
          gridColumns: [
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
              id: 'pointsMontant',
              name: "Points",
              sortable: true,
              sortProperty : 'sum(mt)', // l'equivalent du JpaSort dans le back
              type: 'inputNum',
              cellEditorFramework : undefined,
              cell: {
                toDisabled: function(entry){

                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                }
              }
            },
            {
              id: 'ajout',
              name: "Etat",
              sortable: true,
              type: 'text-with-action',
              cell: {
                toText : function(entry) {
                  var result = entry;

                  debugger
                  if(result !=undefined)
                  {
                    var element = $this.getEtatOeuvre(result.ajout);
                    if(result.ajout == 'MANUEL' || result.ajout == 'CORRIGE') {
                      var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;" title="Supprimer"></span>';
                      var template = [];
                      template.push({event : 'supprimer-ligne-programme', template : tempalteTrash, disabled : !$this.edition});
                      return {value : element.libelle, template : template ,action : true};
                    } else {
                      return {value : element.libelle, action : false};
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
                  return JSON.stringify({ide12 : entry.ide12});
                },

                isDisabled: function () {

                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                },

                isChecked: function (entry) {
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
          gridData_SONOCMS : {},
          searchQuery : ''
        },


        currentFilter : {
          ide12 : null,
          numProg : this.$route.params.numProg,
          titre : null,
          ajout : 'Tous',
          selection : 'Tous'
        },

        filter : {
          ide12 : null,
          numProg : this.$route.params.numProg,
          titre : null,
          ajout : 'Tous',
          selection : 'Tous'
        },
        dureeSelection : {
          auto : 0,
          corrige : 0,
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

    methods : {

      openPopupInfoTraitementCMS() {
          this.showPopupInfoTmtCMS = true;

      },

      retablirFiltre() {
        this.filter = {
          ide12 : null,
          numProg : this.$route.params.numProg,
          titre : null,
          ajout : 'Tous',
          selection : 'Tous'
        }

        this.rechercher();
      },

      calculerCompteurs: function (statut) {
        this.dataLoadingDuree = true;
        this.resource.compteursProgramme({numProg: this.$route.params.numProg, statut: statut})
          .then(response => {
          return response.json();
        }).then(data => {

          this.dureeSelection.auto = data.AUTOMATIQUE;
          this.dureeSelection.manuel = data.MANUEL;
          this.dureeSelection.corrige = data.CORRIGE
          this.dureeSelection.duree = data.SOMME;
          //this.pointsSelection = this.countPointsSelection();

          this.backupDureeSelection = {
            auto: this.dureeSelection.auto,
            corrige: this.dureeSelection.corrige,
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
          findByNumProg: {method: 'GET', url: process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/numProg/{numProg}'},
          findLigneProgrammeByProgramme: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_CMS +  'app/rest/ligneProgramme/search?page={page}&size={size}&sort={sort},{dir}'
          },
          validerSelection: {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS +  'app/rest/ligneProgramme/selection/valider'},
          modifierSelection: {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS +  'app/rest/ligneProgramme/selection/modifier'},
          updateSelectionTemporaire : {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS +  'app/rest/ligneProgramme/selection/temporaire/modifier'},
          invaliderSelection: {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/invalider'},
          compteursProgramme: {method: 'GET', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/compteurs?numProg={numProg}&statut={statut}'},
          annulerSelection: {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/annuler'},
          supprimerLigneProgramme: {method: 'DELETE', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/{numProg}/{ide12}/'},
          enregistrerEdition : {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/enregistrerEdition'},
          annulerEdition : {method: 'POST', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/annulerEdition'},
          getLastFinished : {method: 'GET', url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/programme/eligibilite/tmt/{numProg}'}
        }

        this.resource = this.$resource('', {}, customActions);

        this.resource.getLastFinished({numProg: this.$route.params.numProg})
          .then(response => {
            return response.json();
          })
          .then(data => {
              this.infoTraitementCMS.dateFinTmt = data.dateFinTmt;
              this.infoTraitementCMS.dateDebutTmt = data.dateDebutTmt;

              this.infoTraitementCMS.nbOeuvresCatalogue = data.nbOeuvresCatalogue;
              this.infoTraitementCMS.nbOeuvresExtraction = data.nbOeuvresExtraction;
              this.infoTraitementCMS.nbOeuvresRetenues = data.nbOeuvresRetenues;
              this.infoTraitementCMS.sommePoints = data.sommePoints;

          });

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


              this.defaultPageable.sort = 'pointsMontant';

                var pointsColumn = this.priamGrid_SONOCMS.gridColumns.find(function (elem) {
                  return elem.id === 'pointsMontant';
                });

                if(pointsColumn !== undefined) {
                  if(this.programmeInfo.typeUtilisation === "SONOANT"){
                    pointsColumn.sortProperty = 'sum(nbrDifEdit)';
                    pointsColumn.cellEditorFramework = PointsEditor;
                  } else {
                    pointsColumn.sortProperty = 'sum(mtEdit)';
                    pointsColumn.cellEditorFramework = PointsMontantEditor;
                  }

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

      onCellValueChanged(column, columnModel) {
        console.log("Cell Value has changed !!!")
      },

      supprimerProgramme() {

        this.resource.supprimerLigneProgramme({numProg : this.programmeInfo.numProg, ide12 : this.selectedLineProgramme.ide12}, this.selectedLineProgramme)
          .then(response => {
            return response.json();
          })
          .then(data => {
              this.showPopupSuppression = false;
              var index = this.indexOf(this.ligneProgramme, this.selectedLineProgramme);

              if (index > -1) {
                this.ligneProgramme.splice(index, 1);
              }
              this.calculerCompteurs(this.programmeInfo.statut);
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

              this.priamGrid_SONOCMS.gridData_SONOCMS = data;
              this.priamGrid_SONOCMS.gridData_SONOCMS.number = data.number + 1;
              this.ligneProgramme = this.priamGrid_SONOCMS.gridData_SONOCMS.content;

          });
      },

      onSort(currentPage, pageSize, sort) {

        this.dataLoading = true;
        this.modifierSelectionTemporaire();

        this.resource.updateSelectionTemporaire(this.selection)
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
        this.dataLoading = true;
        this.defaultPageable.size = size;
        let pageSize = this.defaultPageable.size;


        this.modifierSelectionTemporaire();

        this.resource.updateSelectionTemporaire(this.selection)
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

                this.priamGrid_SONOCMS.gridData_SONOCMS = data;
                this.priamGrid_SONOCMS.gridData_SONOCMS.number = data.number + 1;
                tab = this.priamGrid_SONOCMS.gridData_SONOCMS.content;


              this.ligneProgramme = tab;
              this.dataLoading = false;

             // this.$store.dispatch('toutDesactiver', this.countNbSelected(this.ligneProgramme) == this.ligneProgramme.length);
              //this.selectAll();
            });
        }

        this.currentFilter.ide12 = this.filter.ide12;
        this.currentFilter.ajout = this.filter.ajout;
        this.currentFilter.numProg = this.filter.numProg;
        this.currentFilter.titre = this.filter.titre;
        this.currentFilter.selection = this.filter.selection;

        if(this.isActionAnnulerEdition) {
          doSearch.call(this);
          this.calculerCompteurs(this.programmeInfo.statut);
        } else {
          this.modifierSelectionTemporaire();

          this.resource.updateSelectionTemporaire(this.selection)
            .then(response => {
              return response.json();
            }).then(data => {

            doSearch.call(this);
            this.calculerCompteurs(this.programmeInfo.statut);

          })
            .catch(response => {
              console.log("Erreur technique lors de la validation de la selection du programme !! " + response);
            });

          /*doSearch.call(this);
          this.calculerCompteurs(this.programmeInfo.statut);*/
        }

      },



      isTableauSelectionnable() {
        return this.tableauSelectionnable;
      },

      onEntryChecked(isChecked, entryChecked) {

        if(isChecked) {

          if(entryChecked.ajout == 'MANUEL') {
            this.dureeSelection.manuel ++;
          } else if(entryChecked.ajout == 'CORRIGE'){
            this.dureeSelection.corrige ++;
          } else {
            this.dureeSelection.auto++;
          }

            this.dureeSelection.duree += entryChecked.pointsMontant;

          var found = this.ligneProgrammeSelected.find( elem => {
            return  elem.ide12 === entryChecked.ide12;
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

          if(entryChecked.ajout == 'MANUEL') {
            this.dureeSelection.manuel--;
          } else if(entryChecked.ajout == 'CORRIGE'){
            this.dureeSelection.corrige--;
          }else {
            this.dureeSelection.auto--;
          }

            this.dureeSelection.duree -= entryChecked.pointsMontant;

          let number = this.indexOf(this.ligneProgrammeSelected, entryChecked);
          this.ligneProgrammeSelected.splice(number, 1);

          this.unselectedLigneProgramme.push(entryChecked);
        }

        entryChecked.selection = isChecked;

         console.log('onEntryChecked() ==> this.ligneProgrammeSelected='+this.ligneProgrammeSelected.length);
      },

      indexOf(array, obj) {

          for(let i = 0; i < array.length; i++ ) {
              if(obj.ide12 == array[i].ide12) {
                  return i;
              }
          }

          return -1;

      },

      onAllChecked(allChecked, entries) {

        this.all = allChecked;
        this.ligneProgrammeSelected = [];
        this.unselectedLigneProgramme = [];

        for(var i in entries) {
            let index = this.indexOf(this.ligneProgramme, entries[i]);
            if(index > -1 && this.ligneProgramme[index].selection !== allChecked) {
              this.ligneProgramme[index].selection = allChecked;
              this.recalculerCompteurs(this.ligneProgramme[index]);
            }

        }

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

        let points;
          points = entry.pointsMontant;

        if(entry.selection) {
          this.dureeSelection.duree += points;
        } else {
          this.dureeSelection.duree -= points;
        }

        if(this.dureeSelection.duree < 0) {
          this.dureeSelection.duree  = 0;
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
        this.selection = selection;

        if(this.programmeInfo.statut == 'AFFECTE' || this.programmeInfo.statut == 'EN_COURS') {

          if(this.dureeSelection.duree == 0) {
            this.modalWaring  = true;
            this.modalVisible = true;
            this.modalMessage  = 'Attention la somme des points sur le programme est égale à 0';

            return;
          }

        }
        this.modalWaring  = false;
        this.modalVisible = true;
        this.modalMessage = 'Etes-vous sûr de vouloir valider cette sélection?';

      },

      validerSelection () {
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

        this.resource.updateSelectionTemporaire(this.selection)
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
            this.calculerCompteurs(this.programmeInfo.statut);
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
       return this.ligneProgramme !== undefined ? this.ligneProgramme.length : 0;
      },

      countPointsSelection() {
        var countPt = 0;
        for(var i in this.ligneProgramme) {
          if(this.ligneProgramme[i].selection) {
            countPt += this.ligneProgramme[i].pointsMontant ;
          }
        }

        return countPt;

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
      },

      countPointsSelection : function (newValue) {
        console.log("Points sélection a cahnge "+  newValue);
        //this.dureeSelection.duree = newValue;
        //this.calculerCompteurs();
      }

    },

    components : {
      vSelect : vSelect,
      priamGrid : Grid,
      modal: Modal,
      appFiltreSelection : FiltreSelection,
      appInformationsSelection : InformationsSelection,
      appActionSelection : ActionSelection,
      appProgrammeInfo : ProgrammeInfo,
      navbar : Navbar
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
