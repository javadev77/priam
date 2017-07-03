<template>
  <div class="container-fluid">
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
                <span class="pull-right blueText">Famille</span>
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
              <div class="col-sm-2">
                <label class="control-label pull-right blueText">Famille</label>
              </div>
              <div class="col-sm-3">

                <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                </v-select>
              </div>

              <div class="col-sm-3">
                <label class="control-label pull-right blueText">Type d'utilisation</label>
              </div>
              <div class="col-sm-4">
                <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                </v-select>
              </div>

              <div class="col-sm-3">
                <label class="control-label pull-right blueText">Statut</label>
              </div>
              <div class="col-sm-2">
                <v-select :searchable="false" label="value" v-model="statutSelected" :options="statutAffectationOptions">
                </v-select>
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
            <div class="result-panel-body panel-body" style="height:600px; overflow-y:scroll;">

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
      <button v-if="showButtonEnregistrer" class="btn btn-default btn-primary pull-right" type="button" @click="enregister()">Enregister</button>
      <button v-if="showButtonEditer" class="btn btn-default btn-primary pull-right" type="button" @click="editer()">Editer</button>
      <button v-if="showButtonToutDesactiver" class="btn btn-default btn-primary pull-right" type="button" @click="showModalDesactiver = true">Tout désactiver</button>
      <button v-if="showButtonAnnuler" class="btn btn-default btn-primary pull-right" type="button" @click="annuler()">Annuler</button>
      <span v-if="programmeInfo.dataffecte" class="pull-right">
        Affecté par {{ programmeInfo.useraffecte }} {{ programmeInfo.dataffecte | dateAffectation }}
      </span>
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
        Etes-vous sûr de voir désaffecter tous les fichiers de ce programme?
      </span>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showModalDesactiver = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="toutDesactiver">Oui</button>
      </template>
    </modal>

  </div>
</template>

<script>

  import chargementMixins from '../../mixins/chargementMixin'
  import vSelect from '../common/Select.vue';
  import Grid from '../common/Grid.vue';
  import moment from 'moment';
  import Modal from '../common/Modal.vue'

  export default {

      mixins: [chargementMixins],

      data() {
        var $this =this;
        var getters = this.$store.getters;
          return {

            fichiersToProgramme: {
                numProg,
                fichiers:[{
                    id,
                    nom
                }]
            },

            showModalAffectation :false,
            showModalDesactiver : false,
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
                        if(entry.dateFinChargt !== null) {
                            var m = moment(entry.dateFinChargt);
                            return m.format("DD/MM/YYYY HH:mm");

                        } else {
                            return '';
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
                        console.log("$this.isStatusProgrammeAffecte()=" + $this.isStatusProgrammeAffecte())
                        if(!$this.isTableauSelectionnable()) {
                            return true;
                        }
                        return false;
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
              findByNumProg : {method : 'GET', url : 'app/rest/programme/numProg/{numProg}'},
              findAllFichiers : {method : 'POST', url :'app/rest/chargement/allFichiers'},
              affectationProgramme : {method: 'POST', url : 'app/rest/programme/affectation'},

          }
          this.resource= this.$resource('', {}, customActions);

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

      },

      methods : {

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
          return this.programmeInfo.statut === 'AFFECTE';
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
            console.log("initData() ==> this.programmeInfo="+this.programmeInfo.statut)
            if(this.programmeInfo !== null && this.programmeInfo.statut === 'CREE') {

                this.familleSelected = this.getFamilleByCode(this.programmeInfo.famille);
                this.statutSelected = { id : 'ALL', value : 'Tous'};
                this.tableauSelectionnable = true;
                this.showButtonEnregistrer = true;

            } else {
                this.familleSelected = {id : 'ALL', value : 'Toutes'};
                this.typeUtilisationSelected = {id : 'ALL', value : 'Tous'};
                let statutFichier = this.getStatutFichierByCode('AFFECTE');
                this.statutSelected = { id : statutFichier.code, value : statutFichier.libelle};
                this.tableauSelectionnable = false;
                this.showButtonEditer = true;
            }

            if(this.programmeInfo.statut === 'EN_COURS' || this.programmeInfo.statut === 'ABANDONNE' || this.programmeInfo.statut === 'MIS_EN_REPART' ||this.programmeInfo.statut ==='REPARTI'){
              this.showButtonEditer = false;
              this.showButtonToutDesactiver = false;
              this.tableauSelectionnable = false;
              this.showButtonAnnuler = false;
              this.showButtonEnregistrer = false;
          }

          //this.rechercher();
        },

        rechercher() {
          console.log("rechercher() ==> this.typeUtilisationSelected="+this.typeUtilisationSelected)
          if(this.programmeInfo.statut === 'AFFECTE') {
             this.inputChgtCriteria.numProg = this.programmeInfo.numProg;
          } else {
             this.inputChgtCriteria.numProg = null;
          }

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

            });

        },

        retablir() {

        },

        onEntryChecked(isChecked, entryChecked) {
            console.log('isChecked='+isChecked);
            console.log('entryChecked='+entryChecked.id);
            if(isChecked) {
                var found = this.fichiersChecked.find( elem => {
                   return  elem === entryChecked.id;
                });
                if(found !== undefined && found) {

                } else {
                  this.fichiersChecked.push(entryChecked.id);
                }

            } else {
              console.log('this.fichiersChecked='+this.fichiersChecked);
              let number = this.fichiersChecked.indexOf(entryChecked.id);
              console.log('indexOf='+number);
              this.fichiersChecked.splice(number, 1);
            }
          console.log('this.fichiersChecked='+this.fichiersChecked);
        },

        onAllChecked(allChecked, entries) {
            console.log("entries checked=" +  entries);
            this.fichiersChecked = [];
            if(allChecked) {
                for(var i in entries) {
                  this.fichiersChecked.push(entries[i]);
                }
            } else {
              this.fichiersChecked = [];
            }

          console.log('this.fichiersChecked='+this.fichiersChecked);
        },
      affecterFichiersAuProgramme(){
        this.fichiersToProgramme.numProg = this.programmeInfo.numProg;
        for(idFichier in this.fichiersChecked){
            if(f !==null){
              this.fichiersToProgramme.fichiers.push({id:idFichier});
            }

        }

        console.log(fichiersToProgramme.fichiers);
        this.resource.affectationProgramme(this.fichiersToProgramme).then(response => {
          console.log("affacration ok");
          this.$emit('validate');
        }, response => {
          alert("Erreur technique lors de l'affectation des fichiers au programme !! ");
          this.$emit('cancel');
        });
      }

      },

      components : {
        vSelect : vSelect,
        priamGrid : Grid,
        modal: Modal
      }


  }
</script>
<style>

  .blueText {
     color: #799BC4;
  }

</style>
