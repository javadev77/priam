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
        :showUtilisateur="getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? (getTypeUtilisationByCode(programmeInfo.typeUtilisation).value == 'Copie privée sonore phono' ? false : true ) : true"
        :retablir="retablirFiltre"
        :rechercher="rechercher"
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

            <priam-grid

              v-if="priamGrid.gridData.content"
              :data="priamGrid.gridData"
              :columns="priamGrid.gridColumns"
              noResultText="Aucun résultat."
              :filter-key="priamGrid.searchQuery"
              @load-page="loadPage"
              @on-sort="onSort"
              @all-checked="onAllChecked"
              @entry-checked="onEntryChecked"
            >
            </priam-grid>

          </div>
        </div>
      </div>
      <app-action-selection
        :statutProgramme="programmeInfo.statut"
        :listSelectionVide="ligneProgrammeSelected.length == 0"
        :debugListSelection="ligneProgrammeSelected.length"
      >
      </app-action-selection>

    </div>
  </div>
</template>

<script>
  import chargementMixins from '../../mixins/chargementMixin';
  import vSelect from '../common/Select.vue';
  import Grid from '../common/Grid.vue';
  import Modal from '../common/Modal.vue';
  import moment from 'moment';
  import FiltreSelection from './FiltreSelection.vue';
  import ActionSelection from './ActionSelection.vue';

  export default {
    mixins: [chargementMixins],

    data(){

      var $this =this;

      return {
        ligneProgrammeSelected : [],
        programmeInfo: {},
        programmeData: {},
        tableauSelectionnable : true,
        isCollapsed: false,
        defaultPageable : {
          page : 1,
          sort : 'ide12',
          dir : 'desc',
          size : 25
        },
        priamGrid: {
          gridColumns: [
            {
              id: 'role',
              name: 'role',
              sortable: true,
              type: 'code-value',
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
              id: 'ide12',
              name: 'ide12',
              sortable: true,
              type: 'code-value',
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
              id: 'titre',
              name: "titre",
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
              id: 'participant',
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
              id: 'duree',
              name: "Durée",
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
          gridData : {},
          searchQuery : ''
        },
        filter : {
            ide12 : null,
            numProg : this.$route.params.numProg,
            utilisateur : 'Tous',
            titre : null,
            ajout : 'Tous',
            selection : 'Tous'
        }
      }
    },

    created() {
      console.log("router params numProg = " + this.$route.params.numProg);
      const customActions = {
        findByNumProg: {method: 'GET', url: 'app/rest/programme/numProg/{numProg}'},
        findLigneProgrammeByProgramme : {method: 'POST', url: 'app/rest/ligneProgramme/search?page={page}&size={size}&sort={sort},{dir}'}
      }
      this.resource = this.$resource('', {}, customActions);

      this.resource.findByNumProg({numProg: this.$route.params.numProg})
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.programmeInfo = data;
        });

      this.rechercher();
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
      launchRequest(pageNum, pageSize, sort, dir) {
        this.resource.findLigneProgrammeByProgramme({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir}, this.filter )
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.priamGrid.gridData = data;
           // this.priamGrid.gridData.number = data.number + 1;

          });
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
      rechercher(){

        this.resource.findLigneProgrammeByProgramme({page : this.defaultPageable.page - 1, size : this.defaultPageable.size,
          sort : this.defaultPageable.sort, dir: this.defaultPageable.dir}, this.filter)
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log(data);
            this.priamGrid.gridData = data;
            this.priamGrid.gridData.number = data.number + 1;

            this.ligneProgrammeSelected = [];
            var tab = this.priamGrid.gridData.content;
            for(var i in tab) {
              if(tab[i] && tab[i].selection) {
                this.ligneProgrammeSelected.push(tab[i].id);
              }
            }

            console.log("length this.ligneProgrammeSelected=" + this.ligneProgrammeSelected.length);
            this.$store.dispatch('toutDesactiver', this.ligneProgrammeSelected.length !=0 && this.priamGrid.gridData.content.length == this.ligneProgrammeSelected.length );

          });
      },
      isTableauSelectionnable() {
        return this.tableauSelectionnable;
      },
      onEntryChecked(isChecked, entryChecked) {
        console.log('entryId='+entryChecked.id);
        console.log('isChecked='+isChecked);

        if(isChecked) {
          var found = this.ligneProgrammeSelected.find( elem => {
            return  elem === entryChecked.id;
          });
          if(found !== undefined && found) {

          } else {
            this.ligneProgrammeSelected.push(entryChecked.id);
          }

        } else {
          console.log('this.ligneProgrammeSelected='+this.ligneProgrammeSelected);
          let number = this.ligneProgrammeSelected.indexOf(entryChecked.id);
          console.log('indexOf='+number);
          this.ligneProgrammeSelected.splice(number, 1);
        }
        console.log('onEntryChecked() ==> this.ligneProgrammeSelected='+this.ligneProgrammeSelected.length);
      },

      onAllChecked(allChecked, entries) {
        console.log("entries checked=" +  entries);

        if(allChecked) {
          for(var i in entries) {
           console.log("element of entry = " + entries[i]);
           this.ligneProgrammeSelected.push(entries[i]);
           }
        } else {
          this.ligneProgrammeSelected = [];
        }

        this.$store.dispatch('toutDesactiver', true);
        console.log('onAllChecked() ==> this.ligneProgrammeSelected='+this.ligneProgrammeSelected.length);
      },
    },
    computed : {
      statut() {
        return [];
      }
    },
    components : {
      vSelect : vSelect,
      priamGrid : Grid,
      modal: Modal,
      appFiltreSelection : FiltreSelection,
      appActionSelection : ActionSelection
    }
  }

</script>

<style>

</style>
