<template>
  <div class="container-fluid">
    <div class="navbar navbar-default navbar-sm breadcrumb">
        <div class="titre-page">
            <a class="btn-back no-select" @click="goBack()" v-shortkey="['f8']" @shortkey="goBack()" >
              <span class="glyphicon glyphicon-chevron-left"></span>Retour (F8)
            </a>
            <span>Programme <span class="glyphicon glyphicon-chevron-right"></span>
              Liste programmes <span class="glyphicon glyphicon-chevron-right"></span>
              Affectation
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
            <div class="result-panel-body panel-body" style="height:400px; overflow-y:scroll;">

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
      <button v-show="showButtonEditer" class="btn btn-default btn-primary pull-right" type="button" @click="editer()" :disabled="!isRightEDTAFC">Modifier</button>
      <span v-if="isStatusProgrammeAffecte()" class="pull-right">
        Affecté par {{ programmeInfo.useraffecte }} {{ programmeInfo.dataffecte | dateAffectation }}
      </span>
    </div>

    <div class="mask" v-if="affectationEncours" >
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
      </span>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showModalDesactiver = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="toutDeaffecter">Oui</button>
      </template>
    </modal>

  </div>
</template>

<script>

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
        var $this =this;
        var getters = this.$store.getters;
          return {

            fichiersToProgramme: {
                numProg : '',
                fichiers : [],
                fichiersUnChecked : [],
                fichersAvantAffectation : []
            },

            fichiersDesaffectes: {
              numProg: '',
              allDesaffecte: false,
              fichersAvantDesaffectation : []
            },

            showModalAffectation :false,
            showModalDesactiver : false,
            affectationEncours : false,
            isCollapsed : false,
            resource: {},
            programmeInfo : {},
            familleSelected : null,
            typeUtilisationSelected : null,
            statutSelected : null,

            inputChgtCriteria : {
              familleCode : '',
              typeUtilisationCode : '',
              statutCode         : [],
              numProg : null
            },

            tableauSelectionnable : true,

            showButtonEnregistrer : false,
            showButtonToutDesactiver : false,
            showButtonEditer : false,
            showButtonAnnuler : false,

            fichiersChecked : [],
            fichiersUnChecked : [],

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
                      var result  =  getters.famille.find(function (element) {
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
                  id :  'dateFinChargt',
                  name :   "Fin chargement",
                  sortable : true,
                  type : 'date',
                  cell : {
                    toText : function (entry) {
                        return entry.dateFinChargt;
                    }
                  }
                },
                {
                  id :  'statut',
                  name :   "Statut",
                  sortable : true,
                  type : 'code-value',
                  cell : {
                    toText : function (cellValue) {
                      let element = $this.getStatutFichierByCode(cellValue);
                      return element !== undefined && element.libelle;
                    }
                  }
                },
                {
                  id :  'action',
                  name :   "Actions",
                  sortable : false,
                  type : 'checkbox',
                  cell : {
                    toText: function (entry) {
                      return entry.id;
                    } ,

                    isDisabled : function() {
                        if(!$this.isRightRECAFC){
                            return true;
                        }
                        if(!$this.isTableauSelectionnable()) {
                            return true;
                        }
                        return false;
                    },

                    isChecked: function (entry) {
                      var result = $this.fichiersChecked.find( elem => {
                        return elem == entry.id;
                      });
                      if(result !== undefined) {
                          return  1;
                      }
                      return 0;
                    },

                    isAllNotChecked : function () {
                      if($this.fichiersChecked.length > 1 ) {
                        return  false;
                      }
                      return true;
                    }
                  }
                }
              ],
              //gridData : {"content":[{"id":254,"nomFichier":"FF_PENEF_EXTRANA_EXTCPRIVCPRIVAUDPL_201704061020001.csv","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/06/2017 16:17","dateFinChargt":null,"nbLignes":87933,"statut":"EN_COURS"},{"id":12,"nomFichier":"Fichier 15","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"24/05/2017 16:00","dateFinChargt":"24/05/2017 22:57","nbLignes":150780,"statut":"AFFECTE"},{"id":11,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/05/2017 18:15","dateFinChargt":"04/05/2017 22:57","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":10,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":"01/05/2017 18:50","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":6,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":5,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":9,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":"02/05/2017 01:10","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":8,"nomFichier":"Fichier 09","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":"01/04/2017 22:10","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":4,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":1,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/02/2017 17:15","dateFinChargt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":2,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"03/02/2017 17:15","dateFinChargt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":3,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":7,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"}],"last":true,"totalPages":1,"totalElements":13,"size":25,"number":0,"sort":[{"direction":"DESC","property":"dateDebutChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}],"first":true,"numberOfElements":13},
              gridData : {"content" :[],
                          "sort" : [{"direction":"DESC","property":"dateFinChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}] },
              //gridData : {},
              searchQuery : ''
            }

          }
      },


      created() {
          console.log("router params numProg = " + this.$route.params.numProg)
          const customActions = {
              findByNumProg : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/numProg/{numProg}'},
              affectationProgramme : {method: 'PUT', url : process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/programme/affectation'},
              toutDeaffecterProg : {method: 'PUT', url : process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/programme/toutDesaffecter'},
              findAllFichiers : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/chargement/allFichiers'}

          }
          this.resource= this.$resource('', {}, customActions);

          this.$store.commit('TOUT_DESACTIVER', false);

          this.resource.findByNumProg({numProg:  this.$route.params.numProg})
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.programmeInfo = data;
              this.initData();
            });



      },



      mounted() {

      },

      watch : {

        typeUtilisationSelected : function (val, oldVal) {
          console.log('typeUtilisationSelected : %s, old: %s', val, oldVal);
          if(oldVal === null) {
              //Premier chargement de l'ecran
            this.rechercher();

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

        statutAffectationOptions() {

          var options =   this.statutFichier().map(status => {
                return {
                  id : status.code,
                  value : status.libelle
              }
          });
          options.unshift({id : 'ALL', value : 'Tous'})

          console.log("tab="+options);

          return options;
        },

        isRightRECAFC() {
          return this.hasRight('RECAFC');
        },

        isRightEDTAFC() {
          return this.hasRight('EDTAFC');
        }

      },

      methods : {

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
          this.affectationEncours = true;
          this.controlerFamilleEtTypeUtilisation();
          console.log("End of enregister()")
          if( !this.showModalAffectation) {
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
          var familleProgramme = this.programmeInfo.famille;
          var typeUtilisationProgramme = this.programmeInfo.typeUtilisation;
          var confirmation=false;

          //Implementation en utilisant le find()
          var fichiersChecked = this.fichiersChecked;
          var result = this.priamGrid.gridData.content.find(function (entry) {
              var fileId = Number.parseInt(entry.id);
              return (fichiersChecked.indexOf(fileId) !== -1 && (entry.typeUtilisation !== typeUtilisationProgramme || entry.famille !== familleProgramme));
          });

          console.log("typeUtilisationProgramme=" + typeUtilisationProgramme)
          console.log("result=" + result)
          if(result !== undefined) {
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
            this.tableauSelectionnable = true;
            this.showButtonEnregistrer = true;
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

          if(this.typeUtilisationSelected == null && this.programmeInfo.statut === 'CREE') {
              // Premier chargement de l'ecran
            this.typeUtilisationSelected = this.getTypeUtilisationByCode(this.programmeInfo.typeUtilisation);
            //this.rechercher();
          } else {
            this.typeUtilisationSelected = {id : 'ALL', value: 'Tous'};
          }

          console.log("loadTypeUtilisation() ==> this.typeUtilisationSelected=" +  this.typeUtilisationSelected)

        },

        initData() {
            console.log("initData() ==> this.programmeInfo="+this.programmeInfo.statut);

            if(this.programmeInfo !== null && this.programmeInfo.statut === 'CREE') {
                this.familleSelected = this.getFamilleByCode(this.programmeInfo.famille);
                this.statutSelected = { id : 'ALL', value : 'Tous'};
                this.tableauSelectionnable = true;
                this.showButtonEnregistrer = true;
                this.showButtonAnnuler = false;
                this.showButtonToutDesactiver = false;
                this.showButtonEditer = false;

            } else {

                this.familleSelected = {id : 'ALL', value : 'Toutes'};
                this.typeUtilisationSelected = {id : 'ALL', value : 'Tous'};
                let statutFichier = this.getStatutFichierByCode('AFFECTE');
                this.statutSelected = { id : statutFichier.code, value : statutFichier.libelle};

                if (this.programmeInfo.statut === 'AFFECTE') {
                    this.tableauSelectionnable = false;
                    this.showButtonEditer = true;
                    this.showButtonEnregistrer = false;
                }
                else if(this.programmeInfo.statut === 'EN_COURS' || this.programmeInfo.statut === 'ABANDONNE' || this.programmeInfo.statut === 'MIS_EN_REPART' ||this.programmeInfo.statut ==='REPARTI'){
                    this.showButtonEditer = false;
                    this.showButtonToutDesactiver = false;
                    this.tableauSelectionnable = false;
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
          if(statusCode === 'ALL') {
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
              this.priamGrid.gridData.sort = [{"direction":"DESC","property":"dateFinChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}];

              this.fichiersChecked = [];
              var tab = this.priamGrid.gridData.content;
              for(var i in tab) {
                if(tab[i] && tab[i].statut == 'AFFECTE') {
                  this.fichiersChecked.push(tab[i].id);
                }
              }

              console.log("length this.fichiersChecked=" + this.fichiersChecked.length);

              this.$store.dispatch('toutDesactiver', this.fichiersChecked.length === this.priamGrid.gridData.content.length)

            });

        },

        retablir() {

            if('CREE' == this.programmeInfo.statut) {

              this.familleSelected = this.getFamilleByCode(this.programmeInfo.famille);
              this.statutSelected = {id : 'ALL', value : 'Tous'};

              var $this = this;
              setTimeout(function(){ $this.typeUtilisationSelected = $this.getTypeUtilisationByCode($this.programmeInfo.typeUtilisation); $this.rechercher(); }, 200);

            }
            else {
              this.familleSelected = {id : 'ALL', value : 'Toutes'};
              this.typeUtilisationSelected = {id : 'ALL', value : 'Tous'};

              let statut = this.getStatutFichierByCode('AFFECTE');
              this.statutSelected = { 'id' : statut.code, 'value': statut.libelle };

              this.rechercher();
            }


        },

        onEntryChecked(isChecked, entryChecked) {
            console.log('entryId='+entryChecked.id);
            console.log('isChecked='+isChecked);

            if(isChecked) {
                var found = this.fichiersUnChecked.find( elem => {
                   return  elem === entryChecked.id;
                });
                if(found !== undefined && found) {
                  let number = this.fichiersUnChecked.indexOf(entryChecked.id);
                  this.fichiersUnChecked.splice(number, 1);
                } else {
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

            this.resource.toutDeaffecterProg(this.fichiersDesaffectes)
              .then(response => {
                return response.json();
              })
              .then(data => {

                var self = this;
                var timer = setInterval(function () {

                  self.resource.findByNumProg({numProg: numProg})
                    .then(response => {
                      return response.json();
                    })
                    .then(programme => {
                      if (programme.statutEligibilite === 'FIN_DESAFFECTATION') {
                        clearInterval(timer);

                        self.resource.affectationProgramme(self.fichiersToProgramme)
                          .then(response => {
                            return response.json();
                          })
                          .then(data => {
                            self.affectationEncours = false;

                            self.$router.push({name: 'ListePrg'});
                          })
                          .catch(response => {
                            alert("Erreur technique lors de l'affectation des fichiers au programme !! ");
                          });
                      }
                    });


                }, 1000);

              })
              .catch(response => {
                alert("Erreur technique !!! ");
              });
          });

//        this.resource.affectationProgramme(this.fichiersToProgramme)
//          .then(response => {
//              return response.json();
//          })
//          .then(data => {
//              console.log("affectation ok");
//              this.affectationEncours = false;
//
//              this.$router.push({ name: 'ListePrg'});
//          })
//          .catch(response => {
//              debugger;
//              alert("Erreur technique lors de l'affectation des fichiers au programme !! ");
//          });

      },


      toutDeaffecter(){
          console.log('toutDeaffecter()');
          var numProgramme =this.programmeInfo.numProg;
          if(numProgramme!==null || numProgramme!==""){
            let inputChgtCriteria=  {
              numProg  : this.programmeInfo.numProg,
              statutCode : ['AFFECTE']

            };
            this.resource.findAllFichiers(inputChgtCriteria)
              .then(response => {
                return response.json();
              })
              .then(data => {
                for (let key in data) {
                  this.fichiersDesaffectes.fichersAvantDesaffectation.push(data[key].id);
                }

                this.fichiersDesaffectes.numProg = numProgramme;
                this.fichiersDesaffectes.allDesaffecte = true;

                this.resource.toutDeaffecterProg(this.fichiersDesaffectes)
                  .then(response => {
                    return response.json();
                  })
                  .then(data => {
                    console.log("Déaffactation ok");
                    this.showModalDesactiver = false;

                    this.$router.push({name: 'ListePrg'});


                  })
                  .catch(response => {
                    alert("Erreur technique lors de désaffectation des fichiers du programme !! ");
                  });
              });
          }
        },
      },



      components : {
        vSelect : vSelect,
        priamGrid : Grid,
        modal: Modal,
        appProgrammeInfo : ProgrammeInfo
      }


  }
</script>
<style>

  .blueText {
     color: #799BC4;
  }

</style>
