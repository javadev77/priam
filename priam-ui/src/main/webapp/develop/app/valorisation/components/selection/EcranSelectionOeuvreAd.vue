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
            </app-programme-info>
          </div>
        </div>
      </div>

      <app-filtre-selection
        :filter="filter"
        :retablir="retablirFiltre"
        :rechercher="doSearch"
        @maj-filtre-vue="changeVue"
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
            <div class="form-group col-xs-6">
              <label class="col-xs-3 control-label blueText text-right">Points</label>
              <div class="col-xs-9 control-label text-left" v-if="!dataLoadingPoints">
                {{sommePointsAyantDroit}}
              </div>
              <div class="col-xs-9">
                <div class="spinner" v-if="dataLoadingPoints">
                  <div class="rect1"></div>
                  <div class="rect2"></div>
                  <div class="rect3"></div>
                </div>
              </div>
            </div>
            <div class="row center-div">
              <div class="spinner" v-if="dataLoading">
                <div class="rect1"></div>
                <div class="rect2"></div>
                <div class="rect3"></div>
                <div class="rect4"></div>
                <div class="rect5"></div>
              </div>
            </div>

            <div v-if = "!dataLoading" :style="[filtreVue ? {'width': '50%'} : {'width': '80%'}]">
              <priam-grid
                v-if="priamGridFondsAyantDroit.gridData.content"
                :data="priamGridFondsAyantDroit.gridData"
                :columns="priamGridFondsAyantDroit.gridColumns"
                noResultText="Aucun résultat."
                :filter-key="priamGridFondsAyantDroit.searchQuery"
                @load-page="loadPage"
                @on-sort="onSort">
              </priam-grid>
            </div>
          </div>

        </div>
      </div>
      <app-action-selection
        :programmeInfo="programmeInfo"
        :listSelectionVide="ligneProgramme.length == 0"
        :valider="validerSelection"
        :invalider="invaliderProgramme"
        :desaffecter="desaffecterProgramme"
        :inProcess="inProcess"
        :isLoadingPoints="dataLoadingPoints"
      >
      </app-action-selection>
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
      <modal v-if="showModalDesactiver">
      <span class="homer-prompt-q control-label" slot="body">
        Etes-vous sûr de vouloir désaffecter tous les fichiers de ce programme?<br>
        Attention, ces fichiers ne seront affectables qu’après leur enrichissement qui peut prendre plusieurs minutes.
      </span>
        <template slot="footer">
          <button class="btn btn-default btn-primary pull-right no" @click="showModalDesactiver = false">Non</button>
          <button class="btn btn-default btn-primary pull-right yes" @click="onYesConfirmDesaffectation">Oui</button>
        </template>
      </modal>
    </div>
  </div>

</template>

<script>
  import vSelect from '../../../common/components/ui/Select.vue';
  import Grid from '../../../common/components/ui/Grid.vue';
  import Modal from '../../../common/components/ui/Modal.vue';
  import ProgrammeInfo from '../../../common/components/programme/ProgrammeInfo.vue';
  import FiltreSelection from './FiltreOeuvreADSelection.vue';
  import Navbar from '../../../common/components/ui/priam-navbar.vue';
  import InformationsSelection from '../../../common/components/selection/InformationsSelection.vue';
  import ActionSelection from '../../../valorisation/components/selection/ActionSelectionAD';


  export default {
    name: "EcranSelectionOeuvreAd",

    data() {
      var $this = this;
      return {
        defaultPageable: {
          page: 1,
          sort: 'ide12',
          dir: 'desc',
          size: this.$store.getters.userPageSize
        },

        isCollapsed : false,
        programmeInfo : {
        },

        ligneProgramme: [],

        filter: {
          ide12: null,
          numProg: this.$route.params.numProg,
          titre: null
        },

        filtreVue : false,

        sommePointsAyantDroit: 0,

        currentGridState: {},

        priamGridFondsAyantDroit: {
          gridColumns: [
            {
              id: 'ide12',
              name: 'IDE12',
              sortable: false,
              hidden : $this.filtreVue,
              type: 'text-centre',
              cell: {
                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },
            {
              id: 'titre',
              name: "Titre",
              sortable: false,
              hidden : $this.filtreVue,
              type: 'long-text',
              cell: {
                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },
            {
              id: 'coad',
              name: 'COAD',
              sortable: false,
              type: 'text-centre',
              cell: {
                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },
            {
              id: 'role',
              name: "Rôle",
              sortable: false,
              type: 'text-centre',
              cell: {
                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },
            {
              id: 'participant',
              name: 'Participant',
              sortable: false,
              type: 'long-text',
              cell: {
                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },
            {
              id: 'points',
              name: "Points",
              sortable: false,
              type: 'text-centre',
              cell: {
                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },

          ],
          gridData: {},
          searchQuery: ''
        },
        dataLoading: false,
        dataLoadingPoints: false,
        modalVisible: false,
        modalMessage: '',
        modalWaring: false,
        showModalDesactiver: false,
        inProcess: false,

        fichiersDesaffectes: {
          numProg: '',
          allDesaffecte: false,
          fichersAvantDesaffectation : []
        },
        fichersAvantDesaffectation : [],
      }

    },

    created() {

      this.initProgramme();
    },

    methods: {
      initProgramme() {
        console.log("router params numProg = " + this.$route.params.numProg);

        const customActions = {
          findByNumProg: {
            method: 'GET',
            url: process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/numProg/{numProg}'
          },
          /*findLigneProgrammeByProgramme: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ligneProgramme/search?page={page}&size={size}&sort={sort},{dir}'
          },*/
          findAyantDroitByProgramme: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ayantDroit/search?page={page}&size={size}&sort={sort},{dir}'
          },
          calculerPointsByProgramme: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ayantDroit/points'
          },
          validerSelection: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ligneProgramme/selection/valider'
          },
          invaliderSelection: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ligneProgramme/selection/invalider'
          },
          findFichiersAffecte: {
            method: 'GET',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/allFichiersAffectesByNumprog/{numProg}'
          },
          desaffecterProg: {
            method: 'PUT',
            url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/programme/toutDesaffecter'
          }
        }

        this.resource = this.$resource('', {}, customActions);

        this.resource.findByNumProg({numProg: this.$route.params.numProg})
          .then(response => {
            return response.json();
          })
          .then(data => {

            this.programmeInfo = data;
            debugger;

            this.$store.dispatch('setCurrentProgrammeEnSelection', this.programmeInfo);

            this.all = this.programmeInfo.statut != 'VALIDE';

            this.doSearch();

          });
      },

      doSearch() {
        this.dataLoading = true;
        this.resource.findAyantDroitByProgramme({
          page: this.defaultPageable.page - 1, size: this.defaultPageable.size,
          sort: this.defaultPageable.sort, dir: this.defaultPageable.dir
        }, this.filter)
          .then(response => {
            return response.json();
          })
          .then(data => {

            var tab = [];

            this.priamGridFondsAyantDroit.gridData = data;
            this.priamGridFondsAyantDroit.gridData.number = data.number + 1;
            tab = this.priamGridFondsAyantDroit.gridData.content;

            this.compterSommePoints();
            this.ligneProgramme = tab;
            this.dataLoading = false;

          });
      },

      compterSommePoints() {
        this.dataLoadingPoints = true;
        this.sommePointsAyantDroit = 0;
        this.resource.calculerPointsByProgramme({}, this.filter)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.sommePointsAyantDroit = data;
          });
        this.dataLoadingPoints = false;
      },

      loadPage: function (pageNum, size, sort) {
        this.dataLoading = true;
        this.defaultPageable.size = size;
        let pageSize = this.defaultPageable.size;

      },

      /*launchRequest(pageNum, pageSize, sort, dir) {

        this.currentGridState = {
          pageNum: pageNum, pageSize: pageSize, sort: sort, dir: dir
        };

        this.dataLoading = true;

        this.resource.findLigneProgrammeByProgramme({
          page: pageNum - 1, size: pageSize,
          sort: sort, dir: dir
        }, this.currentFilter)
          .then(response => {
            return response.json();
          })
          .then(data => {

            this.dataLoading = false;

            this.priamGridFondsAyantDroit.gridData = data;
            this.priamGridFondsAyantDroit.gridData.number = data.number + 1;
            this.ligneProgramme = this.priamGridFondsAyantDroit.gridData.content;

          });
      },*/

      onSort(currentPage, pageSize, sort) {

        this.dataLoading = true;
        /*this.launchRequest(currentPage, pageSize, sort.property, sort.direction);*/
        this.doSearch();
        this.defaultPageable.sort = sort.property;
        this.defaultPageable.dir = sort.direction;
      },

      retablirFiltre() {
        this.filter = {
          ide12: null,
          numProg: this.$route.params.numProg,
          titre: null
        }

        this.doSearch();
      },


      valider(selection) {
        this.selection = selection;

        this.modalWaring = false;
        this.modalVisible = true;
        this.modalMessage = 'Etes-vous sûr de vouloir valider cette sélection?';

      },

      validerSelection() {
        this.valider({
          all: false,
          unselected: [],
          selected: []
        });
      },
      closeModal() {
        this.modalVisible = false;
        this.annulerAction = undefined;
      },

      noContinue() {
        this.modalVisible = false;
        this.annulerAction = undefined;
        this.$emit('cancel');
      },

      yesContinue() {

        if (this.annulerAction != undefined && this.annulerAction == true) {


          this.inProcess = true;

          this.selection = {
            numProg: this.$route.params.numProg
          };

        } else if (this.annulerAction != true && this.programmeInfo.statut == 'VALIDE') {
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
        } else if (this.annulerAction != true) {
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
              this.$router.push({name: 'programme'});
            })
            .catch(response => {
              alert("Erreur technique lors de la validation de la selection du programme !! " + response);
            });
        }


        this.annulerAction = undefined;

      },

      invaliderProgramme() {
        this.modalVisible = true;
        this.modalMessage = 'Etes-vous sûr de vouloir invalider ce programme?';
      },

      changeVue(filtreVue){
        let ide12ELement = this.priamGridFondsAyantDroit.gridColumns.find(function (element) {
          return element.id === 'ide12';
        });

        let titreELement = this.priamGridFondsAyantDroit.gridColumns.find(function (element) {
          return element.id === 'titre';
        });

        if(filtreVue != 'globale'){
          this.filtreVue = true;
          ide12ELement.hidden = true;
          titreELement.hidden = true;
        } else {
          this.filtreVue = false;
          ide12ELement.hidden = false;
          titreELement.hidden = false;
        }
      },

      desaffecterProgramme(){
        this.showModalDesactiver = true;
        },

      onYesConfirmDesaffectation(){
        this.showModalDesactiver = false;
        const numProgramme = this.$route.params.numProg;
        if (numProgramme !== null || numProgramme !== "") {
          this.resource.findFichiersAffecte({numProg: numProgramme})
            .then(response => {
              return response.json();
            })
            .then(data => {
              for (let key in data) {
                this.fichersAvantDesaffectation.push(data[key].id);
              }
              this.fichiersDesaffectes = {
                numProg: numProgramme,
                allDesaffecte: true,
                fichersAvantDesaffectation: this.fichersAvantDesaffectation
              }
              this.resource.desaffecterProg(this.fichiersDesaffectes)
                .then(response => {
                  return response.json();
                })
                .then(data => {
                  console.log("Déaffactation ok");
                  this.showModalDesactiver = false;
                  this.$router.push({name: 'programme'});

                })
                .catch(response => {
                  console.error(response);
                  alert("Erreur technique lors de désaffectation des fichiers du programme !! ");
                });

            });
        }

      }

      },
    components : {
      vSelect: vSelect,
      priamGrid: Grid,
      modal: Modal,
      appFiltreSelection: FiltreSelection,
      appInformationsSelection: InformationsSelection,
      appActionSelection: ActionSelection,
      'app-programme-info' : ProgrammeInfo,
      navbar: Navbar
    }

  }

</script>

<style scoped>
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

