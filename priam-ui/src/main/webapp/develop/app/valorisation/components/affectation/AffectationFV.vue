<template>

  <div class="container-fluid">

    <navbar titre1="Programme" titre2="Liste programmes" titre3="Affectation" :backButton="true"></navbar>

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

    <div class="container-fluid sacem-formula">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h5 class="panel-title">
            <a>Critères de recherche</a>
            <span class="pull-left collapsible-icon formula-criteria-search"></span>
            <span class="pull-right blueText"></span>
          </h5>


        </div>
        <div class="panel-collapse">
          <div class="panel-body">
            <form class="form-horizontal" role="form">
              <div class="row">

                <div class="form-group col-md-1"></div>

                <div class="form-group col-md-7">
                  <label class="col-md-9 control-label blueText text-right">Famille destination</label>
                  <div class="col-md-15">
                    <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                    </v-select>
                  </div>
                </div>

                <div class="form-group col-md-8">
                  <label class="col-md-9 control-label blueText text-right">Type d’utilisation destination</label>
                  <div class="col-md-15">
                    <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                    </v-select>
                  </div>
                </div>

                <div class="form-group col-md-6">
                  <label class="col-md-9 control-label blueText text-right">Statut</label>
                  <div class="col-md-15">
                    <v-select :searchable="false" label="value" v-model="statutSelected" :options="statutAffectationOptions">
                    </v-select>
                  </div>
                </div>

              </div>

            </form>
          </div>
        </div>
      </div>


      <div class="row formula-buttons">
        <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
        <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercher()">Rechercher</button>
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
          <div class="result-panel-body panel-body" style="height:250px; overflow-y:scroll;">

            <priam-grid
              :isPaginable="false"
              :isLocalSort="true"
              :data="priamGrid.gridData"
              :columns="priamGrid.gridColumns"
              noResultText="Aucun résultat."
              :filter-key="priamGrid.searchQuery"
              @entry-checked="onEntryChecked"
              @all-checked="onAllChecked"
            >
            </priam-grid>
          </div>
        </div>
      </div>
    </div>
    <div class="row formula-buttons">
      <button v-show="showButtonToutDesactiver" style="width: 160px;"  class="btn btn-default btn-primary pull-left" type="button" @click="showModalDesactiver = true">Tout désaffecter</button>
      <button v-show="showButtonAnnuler" class="btn btn-default btn-primary pull-right" type="button" @click="annuler()">Annuler</button>
      <button v-show="showButtonEnregistrer" class="btn btn-default btn-primary pull-right" type="button" @click="enregister()" :disabled="!isRightRECAFC">Enregister</button>
      <button v-show="showButtonEditer" class="btn btn-default btn-primary pull-right" type="button" @click="editer()" :disabled="!isRightEDTAFC">Editer</button>
      <span v-if="isStatusProgrammeAffecte()" class="pull-right">
        Affecté par {{ programmeInfo.useraffecte }} {{ programmeInfo.dataffecte | dateAffectation }}
      </span>

    </div>

    <div class="mask" v-if="deaffectationEncours" >
      <div class="center-div">
        <div class="spinner">
          <div class="rect1"></div>
          <div class="rect2"></div>
          <div class="rect3"></div>
          <div class="rect4"></div>
          <div class="rect5"></div>
        </div>
      </div>
    </div>

    <modal v-if="showModalAffectation">
      <span class="homer-prompt-q control-label" slot="body">
        Etes-vous sûr de vouloir affecter au programme un fichier qui n'a pas les mêmes famille / type d'utilisation ?
      </span>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showModalAffectation = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="onYesConfirmAffectation">Oui</button>
      </template>
    </modal>

    <modal v-if="showModalDesactiver">
      <span class="homer-prompt-q control-label" slot="body">
        Etes-vous sûr de vouloir désaffecter tous les fichiers de ce programme?
        <div v-if="programmeInfo.typeRepart!=='OEUVRE'">
          Attention, ces fichiers ne seront affectables qu’après leur enrichissement qui peut prendre plusieurs minutes.
        </div>
      </span>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showModalDesactiver = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="toutDeaffecter">Oui</button>
      </template>
    </modal>
  </div>

</template>

<script>
  import Navbar from '../../../common/components/ui/priam-navbar.vue';
  import chargementMixins from '../../../common/mixins/chargementMixin'
  import vSelect from '../../../common/components/ui/Select.vue';
  import Grid from '../../../common/components/ui/Grid.vue';
  import moment from 'moment';
  import Modal from '../../../common/components/ui/Modal.vue'
  import ProgrammeInfo from '../../../common/components/programme/ProgrammeInfo.vue';
  import programmeMixins from '../../../common/mixins/programmeMixin';

  export default {

    mixins: [chargementMixins, programmeMixins],

    data() {
      var $this = this;
      var getters = this.$store.getters;
      return {

        deaffectationEncours : false,

        fichiersToProgramme: {
          numProg: '',
          fichiers: [],
          fichiersUnChecked : [],
          fichersAvantAffectation : []
        },

        fichersAvantDesaffectation : [],
        fichiersDesaffectes: {
          numProg: '',
          allDesaffecte: false,
          fichersAvantDesaffectation : []
        },

        showModalAffectation: false,
        showModalDesactiver: false,
        isCollapsed: false,
        resource: {},
        programmeInfo: {},
        familleSelected: null,
        typeUtilisationSelected: null,
        statutSelected: null,

        inputChgtCriteria: {
          familleCode: '',
          typeUtilisationCode: '',
          statutCode: [],
          numProg: null
        },

        tableauSelectionnable: true,

        showButtonEnregistrer: false,
        showButtonToutDesactiver: false,
        showButtonEditer: false,
        showButtonAnnuler: false,

        fichiersChecked: [],
        fichiersUnChecked : [],

        fichiersAvantDesaffectation: [],

        priamGrid: {
          gridColumns: [
            {
              id: 'nomFichier',
              name: 'Fichier',
              sortable: true,
              type: 'long-text'
            },
            {
              id: 'famille',
              name: 'Famille',
              sortable: true,
              type: 'code-value',
              cell: {
                toText: function (cellValue) {
                  var result = getters.famille.find(function (element) {
                    return element.id === cellValue;
                  });
                  return result !== undefined && result.value;
                }
              }
            },
            {
              id: 'typeUtilisation',
              name: "Type d'utilisation",
              sortable: true,
              type: 'code-value',
              cell: {
                toText: function (cellValue) {
                  var result = getters.typeUtilisation.find(function (element) {
                    return element.id === cellValue;
                  });
                  return result !== undefined && result.value;
                }
              }
            },

            {
              id: 'dateFinChargt',
              name: "Fin chargement",
              sortable: true,
              type: 'date',
              cell: {
                toText: function (entry) {
                  return entry.dateFinChargt;
                }
              }
            },
            {
              id: 'statut',
              name: "Statut",
              sortable: true,
              type: 'code-value',
              cell: {
                toText: function (cellValue) {
                  let element = $this.getStatutFichierByCode(cellValue);
                  return element !== undefined && element.libelle;
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
                  if (!$this.isRightRECAFC) {
                    return true;
                  }
                  if (!$this.isTableauSelectionnable()) {
                    return true;
                  }
                  return false;
                },

                isChecked: function (entry) {
                  var result = $this.fichiersChecked.find(elem => {
                    return elem == entry.id;
                  });
                  if (result !== undefined) {
                    return 1;
                  }
                  return 0;
                },

                isAllNotChecked: function () {
                  if ($this.fichiersChecked.length > 1) {
                    return false;
                  }
                  return true;
                }
              }
            }
          ],
          //gridData : {"content":[{"id":254,"nomFichier":"FF_PENEF_EXTRANA_EXTCPRIVCPRIVAUDPL_201704061020001.csv","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/06/2017 16:17","dateFinChargt":null,"nbLignes":87933,"statut":"EN_COURS"},{"id":12,"nomFichier":"Fichier 15","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"24/05/2017 16:00","dateFinChargt":"24/05/2017 22:57","nbLignes":150780,"statut":"AFFECTE"},{"id":11,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/05/2017 18:15","dateFinChargt":"04/05/2017 22:57","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":10,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":"01/05/2017 18:50","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":6,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":5,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":9,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":"02/05/2017 01:10","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":8,"nomFichier":"Fichier 09","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":"01/04/2017 22:10","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":4,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":1,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/02/2017 17:15","dateFinChargt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":2,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"03/02/2017 17:15","dateFinChargt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":3,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":7,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"}],"last":true,"totalPages":1,"totalElements":13,"size":25,"number":0,"sort":[{"direction":"DESC","property":"dateDebutChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}],"first":true,"numberOfElements":13},
          gridData: {
            "content": [],
            "sort": [{
              "direction": "DESC",
              "property": "dateFinChargt",
              "ignoreCase": false,
              "nullHandling": "NATIVE",
              "ascending": false,
              "descending": true
            }]
          },
          //gridData : {},
          searchQuery: ''
        }

      }
    },


    created() {

      const customActions = {
        findByNumProg: {
          method: 'GET',
          url: process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/numProg/{numProg}'
        },
        affectationProgramme: {
          method: 'PUT',
          url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/programme/affectation'
        },
        toutDeaffecterProg: {
          method: 'PUT',
          url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/programme/toutDesaffecter'
        },
        findAllFichiers: {
          method: 'POST',
          url: process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/chargement/allFichiers'
        },
        findFichiersAffecte: {
          method: 'GET',
          url: process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/allFichiersAffectesByNumprog/{numProg}'
        }
      }
      this.resource = this.$resource('', {}, customActions);

      this.$store.commit('TOUT_DESACTIVER', false);

      this.resource.findByNumProg({numProg: this.$route.params.numProg})
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.programmeInfo = data;
          this.initData();
        });

    },


    watch: {

      typeUtilisationSelected: function (val, oldVal) {
        console.log('typeUtilisationSelected : %s, old: %s', val, oldVal);
        if (oldVal === null) {
          //Premier chargement de l'ecran
          this.rechercher();

        }
      },

      lengthOfTabLigneProgramme : function (newValue) {
        console.log("The length of tab has changed  "+  newValue);
        let nbOfUnChecked = this.fichiersUnChecked !== undefined ? this.fichiersUnChecked.length : 0;
        debugger;
        this.$store.dispatch('toutDesactiver', newValue && newValue === this.countNbSelected && nbOfUnChecked === 0);
      },

      countNbSelected : function (newValue) {
        console.log("Le nombre de selection a cahnge "+  newValue);
        let nbOfUnChecked = this.fichiersUnChecked !== undefined ? this.fichiersUnChecked.length : 0;
        debugger
        this.$store.dispatch('toutDesactiver', newValue && newValue === this.lengthOfTabLigneProgramme && nbOfUnChecked === 0);
      }

    },

    computed: {
      familleOptions() {
        return this.$store.getters.familleOptions;
      },

      typeUtilisationOptions() {
        return this.$store.getters.typeUtilisationOptions;
      },

      statutAffectationOptions() {

        var options = this.statutFichier().map(status => {
          return {
            id: status.code,
            value: status.libelle
          }
        });
        options.unshift({id: 'ALL', value: 'Tous'})

        console.log("tab=" + options);

        return options;
      },

      isRightRECAFC() {
        return this.hasRight('RECAFC');
      },

      isRightEDTAFC() {
        return this.hasRight('EDTAFC');
      },

      countNbSelected() {
        let nbOfChecked = this.fichiersChecked !== undefined ? this.fichiersChecked.length : 0;
        return nbOfChecked;
      },

      lengthOfTabLigneProgramme() {
        let nbOfChecked = this.fichiersChecked !== undefined ? this.fichiersChecked.length : 0;
        let nbOfUnChecked = this.fichiersUnChecked !== undefined ? this.fichiersUnChecked.length : 0;
        console.log("Length =" + (nbOfChecked + nbOfUnChecked))
        return nbOfChecked + nbOfUnChecked;
      }

    },

    methods: {

      goBack() {
        this.$router.back();
      },

      //PRIAM-108(régle:T07)
      toutDesactiver(){


        //Régles métier


        //Tous les fichiers sont désaffectés du programme
        //Statut des fichiers = "Chargement OK"
        //Statut programme = "Créé"

        //régles javascript

        //Afficher l'écran "Liste programmes"
        //Non: Revenir à l'écran affectation en mode non éditable

      },

      enregister(){
        console.log("Start of enregister()")
        this.controlerFamilleEtTypeUtilisation();
        console.log("End of enregister()")
        if (!this.showModalAffectation) {
          this.affecterFichiersAuProgramme();
        }
      },

      onYesConfirmAffectation() {
        this.showModalAffectation = false;
        //PRIAM-108(T09) Implementer le controle ici

        this.affecterFichiersAuProgramme();
      },

      //PRIAM-108(régle:T10)
      controlerFamilleEtTypeUtilisation(){
          debugger;
        var familleProgramme = this.programmeInfo.famille;
        var typeUtilisationProgramme = this.programmeInfo.typeUtilisation;
        var confirmation = false;

        //Implementation en utilisant le find()
        var fichiersChecked = this.fichiersChecked;
        var result = this.priamGrid.gridData.content.find(function (entry) {
          var fileId = Number.parseInt(entry.id);
          return (fichiersChecked.indexOf(fileId) !== -1 && (entry.typeUtilisation !== typeUtilisationProgramme || entry.famille !== familleProgramme));
        });

        console.log("typeUtilisationProgramme=" + typeUtilisationProgramme)
        console.log("result=" + result)
        if (result !== undefined) {
          this.showModalAffectation = true;
        }

      },

      isStatusProgrammeAffecte(){
        return this.programmeInfo.statut !== 'CREE';
      },

      isTableauSelectionnable() {
        return this.tableauSelectionnable;
      },

      editer() {
        if(this.programmeInfo.typeRepart === 'OEUVRE') {
          this.tableauSelectionnable = true;
          this.showButtonEnregistrer = true;
        } else {
          this.tableauSelectionnable = false;
          this.showButtonEnregistrer = false;
        }

        this.showButtonToutDesactiver = true;
        this.showButtonEditer = false;
        this.showButtonAnnuler = true;

      },

      annuler() {

        this.showButtonEditer = true;
        this.showButtonToutDesactiver = false;
        this.tableauSelectionnable = false;
        this.showButtonAnnuler = false;
        this.showButtonEnregistrer = false;

        this.rechercher();

      },

      statutFichier() {
        var filtredStatut = this.$store.getters.statut
          .filter(elem => {
            return elem.code === 'AFFECTE' || elem.code === 'CHARGEMENT_OK'
          });

        return filtredStatut;
      },


      loadTypeUtilisation(val) {

        this.familleSelected = val;
        this.$store.dispatch('loadTypeUtilisation', val);

        if (this.typeUtilisationSelected == null && this.programmeInfo.statut === 'CREE') {
          // Premier chargement de l'ecran
          this.typeUtilisationSelected = this.getTypeUtilisationByCode(this.programmeInfo.typeUtilisation);
          //this.rechercher();
        } else {
          this.typeUtilisationSelected = {id: 'ALL', value: 'Tous'};
        }

        console.log("loadTypeUtilisation() ==> this.typeUtilisationSelected=" + this.typeUtilisationSelected)

      },

      initData() {
        console.log("initData() ==> this.programmeInfo=" + this.programmeInfo.statut);

        if (this.programmeInfo !== null && this.programmeInfo.statut === 'CREE') {
          this.familleSelected = this.getFamilleByCode(this.programmeInfo.famille);
          this.statutSelected = {id: 'ALL', value: 'Tous'};
          this.tableauSelectionnable = true;
          this.showButtonEnregistrer = true;
          this.showButtonAnnuler = false;
          this.showButtonToutDesactiver = false;
          this.showButtonEditer = false;

        } else {

          this.familleSelected = {id: 'ALL', value: 'Toutes'};
          this.typeUtilisationSelected = {id: 'ALL', value: 'Tous'};
          let statutFichier = this.getStatutFichierByCode('AFFECTE');
          this.statutSelected = {id: statutFichier.code, value: statutFichier.libelle};
          this.tableauSelectionnable = false;
          if (this.programmeInfo.statut === 'AFFECTE') {


            this.showButtonEditer = true;

            this.showButtonEnregistrer = false;
          }
          else if (this.programmeInfo.statut === 'EN_COURS'
            || this.programmeInfo.statut === 'ABANDONNE'
            || this.programmeInfo.statut === 'MIS_EN_REPART'
            || this.programmeInfo.statut === 'REPARTI') {
            this.showButtonEditer = false;
            this.showButtonToutDesactiver = false;

            this.showButtonAnnuler = false;
            this.showButtonEnregistrer = false;
          }
        }

      },

      rechercher() {
        console.log("Begin rechercher()")

        this.inputChgtCriteria.numProg = this.programmeInfo.numProg;

        this.inputChgtCriteria.typeUtilisationCode = this.typeUtilisationSelected.id;
        this.inputChgtCriteria.familleCode = this.familleSelected.id;

        let statusCode = this.statutSelected.id;
        if (statusCode === 'ALL') {
          this.inputChgtCriteria.statutCode = this.statutFichier().map(status => {
            return status.code;
          });
        } else {
          let statutFichier = this.getStatutFichierByCode(statusCode);
          this.inputChgtCriteria.statutCode = [statutFichier.code];
        }


        this.resource.findAllFichiers(this.inputChgtCriteria)
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log(data);
            this.priamGrid.gridData.content = data;
            this.priamGrid.gridData.sort = [{
              "direction": "DESC",
              "property": "dateFinChargt",
              "ignoreCase": false,
              "nullHandling": "NATIVE",
              "ascending": false,
              "descending": true
            }];

            this.fichiersChecked = [];
            this.fichiersUnChecked = [];
            var tab = this.priamGrid.gridData.content;
            for (var i in tab) {
              if (tab[i] && tab[i].statut == 'AFFECTE') {
                this.fichiersChecked.push(tab[i].id);
              } else if(tab[i] && tab[i].statut == 'CHARGEMENT_OK') {
                this.fichiersUnChecked.push(tab[i].id);
              }
            }

            this.$store.dispatch('toutDesactiver', this.fichiersChecked.length === this.priamGrid.gridData.content.length)

          });

      },

      retablir() {

        if ('CREE' == this.programmeInfo.statut) {

          this.familleSelected = this.getFamilleByCode(this.programmeInfo.famille);
          this.statutSelected = {id: 'ALL', value: 'Tous'};

          var $this = this;
          setTimeout(function () {
            $this.typeUtilisationSelected = $this.getTypeUtilisationByCode($this.programmeInfo.typeUtilisation);
            $this.rechercher();
          }, 200);

        }
        else {
          this.familleSelected = {id: 'ALL', value: 'Toutes'};
          this.typeUtilisationSelected = {id: 'ALL', value: 'Tous'};

          let statut = this.getStatutFichierByCode('AFFECTE');
          this.statutSelected = {'id': statut.code, 'value': statut.libelle};

          this.rechercher();
        }


      },


      onEntryChecked(isChecked, entryChecked) {
        console.log('entryId='+entryChecked.id);
        console.log('isChecked='+isChecked);

        debugger
        if(isChecked) {
          var found = this.fichiersChecked.find( elem => {
            return  elem === entryChecked.id;
          });
          if(found !== undefined && found) {
            let number = this.fichiersUnChecked.indexOf(entryChecked.id);
            this.fichiersUnChecked.splice(number, 1);
          } else {
            let number = this.fichiersUnChecked.indexOf(entryChecked.id);
            this.fichiersUnChecked.splice(number, 1);

            this.fichiersChecked.push(entryChecked.id);
          }

        } else {
          let number = this.fichiersChecked.indexOf(entryChecked.id);
          this.fichiersChecked.splice(number, 1);

          this.fichiersUnChecked.push(entryChecked.id);

        }


      },

      onAllChecked(allChecked, entries) {
        console.log("entries checked=" +  entries);
        this.fichiersChecked = [];
        if(allChecked) {
          this.fichiersChecked = entries.slice();
          this.fichiersUnChecked = [];
        } else {
          this.fichiersUnChecked = entries.slice();
          this.fichiersChecked = [];
        }

        this.$store.dispatch('toutDesactiver', allChecked);
      },

      affecterFichiersAuProgramme(){
        debugger;
        let numProg = this.programmeInfo.numProg;
        this.fichiersToProgramme.numProg = numProg;
        this.fichiersToProgramme.fichiers=[];

        this.fichiersToProgramme.fichiers = this.fichiersChecked.map(function (idFichier) {
          return {id : idFichier}
        });

        this.fichiersToProgramme.fichiersUnChecked = this.fichiersUnChecked.map(function (idFichier) {
          return {id : idFichier}
        });


        let inputChgtCriteria=  {
          numProg  : numProg,
          statutCode : ['AFFECTE']

        };

        this.deaffectationEncours=true;
        this.resource.findAllFichiers(inputChgtCriteria)

          .then(response => {
            return response.json();
          })
          .then(data => {
            for (let key in data) {
              this.fichiersDesaffectes.fichersAvantDesaffectation.push(data[key].id);
              this.fichiersToProgramme.fichersAvantAffectation.push(data[key].id)
            }

            this.fichiersDesaffectes.numProg = numProg;
            this.fichiersDesaffectes.allDesaffecte = false;


            this.resource.affectationProgramme(this.fichiersToProgramme)
              .then(response => {
                return response.json();
              })
              .then(data => {
                debugger;
                this.deaffectationEncours = false;
                this.$router.push({name: 'programme'});

              })
              .catch(response => {
                debugger;
                alert("Erreur technique lors de l'affectation des fichiers au programme !! ");
              });

          });



      },


      toutDeaffecter() {
        console.log('toutDeaffecter()');
        var numProgramme = this.programmeInfo.numProg;

        if (numProgramme !== null || numProgramme !== "") {
          console.log("fichiers envoyes" + this.fichiersToProgramme.fichiers);
          debugger;

          this.resource.findFichiersAffecte({numProg: numProgramme})
            .then(response => {
              return response.json();
            })
            .then(data => {
              for (let key in data) {
                this.fichersAvantDesaffectation.push(data[key].id);
              }
              debugger;
              this.fichiersDesaffectes = {
                numProg: numProgramme,
                allDesaffecte: true,
                fichersAvantDesaffectation: this.fichersAvantDesaffectation
              }

              this.resource.toutDeaffecterProg(this.fichiersDesaffectes)
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


    components: {
      vSelect: vSelect,
      priamGrid: Grid,
      modal: Modal,
      appProgrammeInfo: ProgrammeInfo,
      navbar: Navbar
    }


  }
</script>
<style>

  .blueText {
    color: #799BC4;
  }

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

  .mask {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1050;
    outline: 0;
    background-color: black;
    opacity: 0.5;
  }

  .mask .center-div {
    width: 0%;
    margin: 0 auto;
    margin-top: 50vh; /* poussé de la moitié de hauteur de viewport */
    transform: translateY(-50%);
  }

</style>
