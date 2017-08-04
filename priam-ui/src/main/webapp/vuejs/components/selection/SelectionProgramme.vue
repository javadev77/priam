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
            <div class="panel-body">
              <div class="row">

                <div class="col-sm-2">
                  <span class="pull-right blueText">N° programme</span>
                </div>
                <div class="col-sm-2">
                  {{ programmeInfo.numProg }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Statut</span>
                </div>
                <div class="col-sm-2">
                  {{ getStatutProgrammeByCode(programmeInfo.statut) !== undefined ? getStatutProgrammeByCode(programmeInfo.statut).libelle : '' }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText"></span>
                </div>
                <div class="col-sm-3">
                  {{ getFamilleByCode(programmeInfo.famille) !== undefined ? getFamilleByCode(programmeInfo.famille).value : '' }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Nb fichiers affectés</span>
                </div>
                <div class="col-sm-2">
                  {{ programmeInfo.fichiers }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Créé par</span>
                </div>
                <div class="col-sm-2">
                  {{ programmeInfo.usercre }}
                </div>
              </div>

              <br/>
              <!-- 2 eme ligne -->
              <div class="row">

                <div class="col-sm-2">
                  <span class="pull-right blueText">Nom</span>
                </div>
                <div class="col-sm-2">
                  {{ programmeInfo.nom }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Rion théorique</span>
                </div>
                <div class="col-sm-2">
                  {{ programmeInfo.rionTheorique }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Type d'utilisation</span>
                </div>
                <div class="col-sm-3">
                  {{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? getTypeUtilisationByCode(programmeInfo.typeUtilisation).value : '' }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Mode répartition</span>
                </div>
                <div class="col-sm-2">
                  {{ getModeRepartitionByCode(programmeInfo.typeRepart) !== undefined ?  getModeRepartitionByCode(programmeInfo.typeRepart).value : '' }}
                </div>

                <div class="col-sm-2">
                  <span class="pull-right blueText">Date création</span>
                </div>
                <div class="col-sm-2">
                  {{ programmeInfo.dateCreation }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <app-filtre-selection
        :filter="filter"
        :showUtilisateur="getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? (programmeInfo.typeUtilisation == 'CPRIVSONPH' ? false : true ) : true"
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
              :dureeSelection="dureeSelection"
              :typeUtilisation="programmeInfo.typeUtilisation"
            >
            </app-informations-selection>

            <div v-if = "this.programmeInfo.typeUtilisation=='CPRIVSONRD'">
              <priam-grid
                v-if="priamGrid_sono.gridData_sono.content"
                :data="priamGrid_sono.gridData_sono"
                :columns="priamGrid_sono.gridColumns"
                noResultText="Aucun résultat."
                :filter-key="priamGrid_sono.searchQuery"
                @load-page="loadPage"
                @on-sort="onSort"
                @all-checked="onAllChecked"
                @entry-checked="onEntryChecked">
              </priam-grid>
            </div>
            <div v-else-if = "this.programmeInfo.typeUtilisation=='CPRIVSONPH'">
              <priam-grid
                v-if="priamGrid_phono.gridData_phono.content"
                :data="priamGrid_phono.gridData_phono"
                :columns="priamGrid_phono.gridColumns"
                noResultText="Aucun résultat."
                :filter-key="priamGrid_phono.searchQuery"
                @load-page="loadPage"
                @on-sort="onSort"
                @all-checked="onAllChecked"
                @entry-checked="onEntryChecked">
              </priam-grid>
            </div>
          </div>

        </div>
      </div>
      <app-action-selection
        :statutProgramme="programmeInfo.statut"
        :listSelectionVide="ligneProgrammeSelected.length == 0"
        :valider="validerSelection"
        :editer="editSelectionClickHandler"
        :invalider="invaliderProgramme"
        :edition="edition"
        :enregistrerEdition="enregistrerEdition"
        :annulerEdition="annulerEdition"
        :annulerSelection="annulerSelection"
        :inProcess="inProcess"
      >
      </app-action-selection>

    </div>


    <modal v-if="modalVisible">
      <label class="homer-prompt-q control-label" slot="body">
        {{modalMessage}}
      </label>
      <template slot="footer">

        <div v-if="modalWaring">
          <button class="btn btn-default btn-primary pull-right no" @click="closeModal" :disabled="inProcess">Fermer</button>
        </div>

        <div v-else="">
          <button class="btn btn-default btn-primary pull-right no" @click="noContinue" :disabled="inProcess">Non</button>
          <button class="btn btn-default btn-primary pull-right yes" @click="yesContinue" :disabled="inProcess">Oui</button>
        </div>

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

  export default {
    mixins: [chargementMixins],

    data(){

      var $this =this;

      return {

        all : false,
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
          size : 25
        },
        priamGrid_phono: {
          gridColumns: [
            {
              id: 'utilisateur',
              name: "Utilisateur",
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
              id: 'ide12',
              name: 'IDE12',
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
              id: 'quantite',
              name: "Quantité",
              sortable: false,
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
              id: 'ajout',
              name: "Ajout",
              sortable: true,
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
              id: 'action',
              name: "Actions",
              sortable: false,
              type: 'checkbox',
              cell: {
                toText: function (entry) {
                  return entry.id;
                },

                isDisabled: function () {

                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                },

                isChecked: function (entry) {

                  var notChecked = $this.unselectedLigneProgramme.find(elem => {
                    return elem == entry.id;
                  });

                  if (notChecked !== undefined) {
                    return 0;
                  }

                  var result = $this.ligneProgrammeSelected.find(elem => {
                    return elem == entry.id;
                  });

                  if (result !== undefined) {
                    return 1;
                  }

                  return 0;
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
              id: 'utilisateur',
              name: "Utilisateur",
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
              id: 'ide12',
              name: "IDE12",
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
              sortable: false,
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
              id: 'ajout',
              name: "Ajout",
              sortable: true,
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
              id: 'action',
              name: "Actions",
              sortable: false,
              type: 'checkbox',
              cell: {
                toText: function (entry) {
                  return entry.id;
                },

                isDisabled: function () {

                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                },

                isChecked: function (entry) {

                  var notChecked = $this.unselectedLigneProgramme.find(elem => {
                    return elem == entry.id;
                  });

                  if (notChecked !== undefined) {
                    return 0;
                  }

                  var result = $this.ligneProgrammeSelected.find(elem => {
                    return elem == entry.id;
                  });

                  if (result !== undefined) {
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
        edition : false
      }
    },

    created() {
      this.initProgramme();
    },

    methods :{
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
        }

        this.resource = this.$resource('', {}, customActions);

        this.resource.findByNumProg({numProg: this.$route.params.numProg})
          .then(response => {
            return response.json();
          })
          .
          then(data => {

              this.programmeInfo = data;
              this.all = this.programmeInfo.statut != 'VALIDE';
              this.tableauSelectionnable = (
                this.programmeInfo.statut != 'VALIDE'
                && this.programmeInfo.statut != 'MIS_EN_REPART'
                && this.programmeInfo.statut != 'REPARTI'
              );

              this.tableauSelectionnable = false;

              this.resource.dureeProgramme({numProg: this.$route.params.numProg, statut: this.programmeInfo.statut})
                .then(response => {
                  return response.json();
                })
                .
                then(data => {

                  for (var indicateur in data) {
                      if(data[indicateur].value =='Automatique') {
                          this.dureeSelection.auto = data[indicateur].code;
                      }
                      else if(data[indicateur].value =='Manuel') {
                          this.dureeSelection.manuel = data[indicateur].code;
                      }
                      else if(data[indicateur].value =='SOMME') {
                        this.dureeSelection.duree = data[indicateur].code;
                      }
                  }

                  this.backupDureeSelection = {
                    auto : this.dureeSelection.auto,
                    manuel : this.dureeSelection.manuel,
                    duree : this.dureeSelection.duree
                  }

                });


            if(this.programmeInfo.statut == 'EN_COURS' || this.programmeInfo.statut == 'VALIDE') {
              this.filter.selection = 'Sélectionné';
              this.all = false;
            }
            this.rechercher();

          });



      },

      launchRequest(pageNum, pageSize, sort, dir) {
        this.resource.findLigneProgrammeByProgramme({page : pageNum -1 , size : pageSize,
          sort : sort, dir: dir}, this.filter )
          .then(response => {
            return response.json();
          })
          .then(data => {
            if(this.programmeInfo.typeUtilisation==='CPRIVSONPH'){

              this.priamGrid_phono.gridData_phono = data;
              this.priamGrid_phono.gridData_phono.number = data.number + 1;
              this.ligneProgramme = this.priamGrid_phono.gridData_phono.content;

            }else if (this.programmeInfo.typeUtilisation==='CPRIVSONRD'){

              this.priamGrid_sono.gridData_sono = data;
              this.priamGrid_sono.gridData_sono.number = data.number + 1;
              this.ligneProgramme = this.priamGrid_sono.gridData_sono.content;
            }

            this.selectAll();
          });
      },

      onSort(currentPage, pageSize, sort) {

        this.launchRequest(currentPage, pageSize, sort.property, sort.direction);

        this.defaultPageable.sort = sort.property;
        this.defaultPageable.dir = sort.direction;

      },

      loadPage: function(pageNum, size, sort) {

        let pageSize = this.defaultPageable.size;
        if(size !== undefined) {
          pageSize = size;
        }

        this.launchRequest(pageNum, pageSize, sort.property, sort.direction);

      },

      // TODO a definir
      ajouterOeuvre(){

      },

      selectAll: function () {

        for (var i in this.ligneProgramme) {

            if(this.all || this.ligneProgramme[i].selection) {
              if(this.ligneProgrammeSelected.indexOf(this.ligneProgramme[i].ide12) == -1 && this.unselectedLigneProgramme.indexOf(this.ligneProgramme[i].ide12) == -1)
                this.ligneProgrammeSelected.push(this.ligneProgramme[i].ide12);
            }
            else {
              if(this.ligneProgrammeSelected.indexOf(this.ligneProgramme[i].ide12) == -1 && this.unselectedLigneProgramme.indexOf(this.ligneProgramme[i].ide12) == -1)
                this.unselectedLigneProgramme.push(this.ligneProgramme[i].ide12);
            }

        }

      },

      rechercher(){

        this.resource.findLigneProgrammeByProgramme({page : this.defaultPageable.page - 1, size : this.defaultPageable.size,
          sort : this.defaultPageable.sort, dir: this.defaultPageable.dir}, this.filter)
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log(data);
            var tab=[];
            if(this.programmeInfo.typeUtilisation==="CPRIVSONPH"){

              this.priamGrid_phono.gridData_phono = data;
              this.priamGrid_phono.gridData_phono.number = data.number + 1;
              tab = this.priamGrid_phono.gridData_phono.content;

            }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD"){

              this.priamGrid_sono.gridData_sono = data;
              this.priamGrid_sono.gridData_sono.number = data.number + 1;
              tab = this.priamGrid_sono.gridData_sono.content;
            }

            this.ligneProgramme = tab;
            this.selectAll();
          });
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
            this.dureeSelection.duree += entryChecked.quantite;
          }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD"){
            this.dureeSelection.duree += entryChecked.durDif;
          }

          var found = this.ligneProgrammeSelected.find( elem => {
            return  elem === entryChecked.ide12;
          });
          if(found !== undefined && found) {
            let number = this.unselectedLigneProgramme.indexOf(entryChecked.ide12);
            this.unselectedLigneProgramme.splice(number, 1);
          } else {

            let number = this.unselectedLigneProgramme.indexOf(entryChecked.ide12);
            this.unselectedLigneProgramme.splice(number, 1);

            this.ligneProgrammeSelected.push(entryChecked.ide12);
          }

        } else {

          if(entryChecked.ajout == 'Manuel') {
            this.dureeSelection.manuel--;
          } else {
            this.dureeSelection.auto--;
          }

          if(this.programmeInfo.typeUtilisation==="CPRIVSONPH"){
            this.dureeSelection.duree -= entryChecked.quantite;
          }else if (this.programmeInfo.typeUtilisation==="CPRIVSONRD"){
            this.dureeSelection.duree -= entryChecked.durDif;
          }

          let number = this.ligneProgrammeSelected.indexOf(entryChecked.ide12);
          this.ligneProgrammeSelected.splice(number, 1);

          this.unselectedLigneProgramme.push(entryChecked.ide12);
        }
        console.log('onEntryChecked() ==> this.ligneProgrammeSelected='+this.ligneProgrammeSelected.length);
      },

      onAllChecked(allChecked, entries) {

        this.all = allChecked;

        if(allChecked) {

          this.ligneProgrammeSelected = [];
          this.unselectedLigneProgramme = [];

          for(var i in entries) {
            if(this.ligneProgrammeSelected.indexOf(entries[i]) == -1)
              this.ligneProgrammeSelected.push(entries[i]);
          }

          this.dureeSelection = {
            auto : this.backupDureeSelection.auto,
            manuel : this.backupDureeSelection.manuel,
            duree : this.backupDureeSelection.duree
          }

        } else {
          this.ligneProgrammeSelected = [];

          this.dureeSelection = {
            auto : 0,
            manuel : 0,
            duree : 0
          }

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

          if(this.programmeInfo.typeUtilisation == 'CPRIVSONRD' && this.totalDureeSelection == 0) {
            this.modalWaring  = true;
            this.modalVisible = true;
            this.modalMessage  = 'Attention la somme des durées sélectionnées sur le programme égale à 0';
            return;
          }

          if(this.programmeInfo.typeUtilisation == 'CPRIVSONPH' && this.totalDureeSelection == 0) {
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

        if(this.all) {
          if(this.unselectedLigneProgramme.length != 0) {
            this.unselect();
          } else {
            this.onSelectAll();
          }
        } else {
          if(this.ligneProgrammeSelected.length == 0) {
            window.alert("NE RIEN FAIRE");
          } else {
            if(this.ligneProgrammeSelected.length > 0) {
              this.select();
            } else {
              this.unselect();
            }
          }
        }
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

          debugger;
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
        if(this.all) {
          if(this.unselectedLigneProgramme.length != 0) {
            this.selection = {
              all : false,
              unselected : this.unselectedLigneProgramme,
              selected : []
            };
          } else {
            this.selection = {
              all : true,
              unselected : [],
              selected : []
            };
          }
        } else {
          if(this.ligneProgrammeSelected.length == 0) {
            window.alert("NE RIEN FAIRE");
          } else {
            if(this.ligneProgrammeSelected.length > 0) {
              this.selection = {
                all : false,
                unselected : [],
                selected : this.ligneProgrammeSelected
              };
            } else {
              this.selection = {
                all : false,
                unselected : this.unselectedLigneProgramme,
                selected : []
              };
            }
          }
        }

        this.selection.numProg = this.$route.params.numProg;

        this.resource.modifierSelection(this.selection)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.filter.selection = 'Sélectionné';
            this.initProgramme();
            this.annulerEdition();
          })
          .catch(response => {
            alert("Erreur technique lors de la validation de la selection du programme !! " + response);
          });
      },

      annulerSelection() {

        this.annulerAction = true;
        this.modalVisible = true;
        this.tableauSelectionnable = true;
        this.modalMessage  = 'Toutes les opérations seront perdues. Etes-vous sûr de vouloir annuler la sélection?';


      },

      annulerEdition () {
        this.tableauSelectionnable = false;
        this.edition = false;
        this.inProcess = false;
      },

      editSelectionClickHandler () {
        this.tableauSelectionnable = true;
        this.edition = true;
      }

    },
    computed : {
      totalDureeSelection () {
        return this.dureeSelection.auto  + this.dureeSelection.manuel;
      }
    },
    components : {
      vSelect : vSelect,
      priamGrid : Grid,
      modal: Modal,
      appFiltreSelection : FiltreSelection,
      appInformationsSelection : InformationsSelection,
      appActionSelection : ActionSelection
    }
  }

</script>

<style>

</style>
