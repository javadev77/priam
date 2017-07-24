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
              :isPaginable="true"
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
  </div>
</template>

<script>
  import chargementMixins from '../../mixins/chargementMixin';
  import vSelect from '../common/Select.vue';
  import Grid from '../common/Grid.vue';
  import Modal from '../common/Modal.vue';
  import moment from 'moment';
  export default {
    mixins: [chargementMixins],

    data(){
      var $this =this;
      var getters = this.$store.getters;
      return {
        fichiersChecked : [],
        programmeInfo: {},
        programmeData: {},
        ableauSelectionnable : true,
        isCollapsed: false,
        defaultPageable : {
          page : 1,
          sort : 'utilisateur',
          dir : 'DESC',
          size : 5
        },
        priamGrid: {
          gridColumns: [
            {
              id: 'utilisateur',
              name: 'Utilisateur',
              sortable: true,
              type: 'long-text'
            },
            {
              id: 'IDE12',
              name: 'IDE12',
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
              id: 'titre',
              name: "Titre",
              sortable: true,
              type: 'long-text',
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
              id: 'role',
              name: "Rôle",
              sortable: true,
              type: 'long-text',
              cell: {
                toText: function (entry) {
                  console.log("entry: "+entry.ide12)
                  if (entry.role !== null) {
                    return entry.role;
                  }
                }
              }
            },
            {
              id: 'participant',
              name: "Participant",
              sortable: true,
              type: 'long-text',
              cell: {
                toText: function (cellValue) {
                  if (entry.role !== null) {
                    return entry.participant;
                  }
                }
              }
            },
            {
              id: 'duree',
              name: "Durée",
              sortable: true,
              type: 'long-text',
              cell: {
                toText: function (cellValue) {
                  if (entry.duree !== null) {
                    return entry.duree;
                  }
                }
              }
            },
            {
              id: 'ajout',
              name: "Ajout",
              sortable: true,
              type: 'long-text',
              cell: {
                toText: function (cellValue) {
                  if (entry.ajout !== null) {
                    return entry.ajout;
                  }
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
                  return entry.id12;
                },

                isDisabled: function () {
                  console.log("$this.isStatusProgrammeAffecte()=" + $this.isStatusProgrammeAffecte())
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
          //gridData : {"content":[{"id":254,"utilisateur":"utilisateur1","role":"COPIEPRIV","duree":"00:22","ajout":"automatique","participant":"participant1","IDE12":87933,"titre":"titre1"},{"content":[{"id":2455,"utilisateur":"utilisateur1","role":"COPIEPRIV","duree":"00:22","ajout":"automatique","participant":"participant1","IDE12":87933,"titre":"titre1"},{"id":11,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/05/2017 18:15","dateFinChargt":"04/05/2017 22:57","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":10,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":"01/05/2017 18:50","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":6,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":5,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":9,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":"02/05/2017 01:10","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":8,"nomFichier":"Fichier 09","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":"01/04/2017 22:10","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":4,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":1,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/02/2017 17:15","dateFinChargt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":2,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"03/02/2017 17:15","dateFinChargt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":3,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":7,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"}],"last":true,"totalPages":1,"totalElements":13,"size":25,"number":0,"sort":[{"direction":"DESC","property":"dateDebutChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}],"first":true,"numberOfElements":13},
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
          searchQuery : ''
        }
      }
    },

    created() {
      console.log("router params numProg = " + this.$route.params.numProg);
      const customActions = {
        findByNumProg: {method: 'GET', url: 'app/rest/programme/numProg/{numProg}'},
        findLigneProgrammeByProgramme : {method: 'POST', url: 'app/rest/ligneProgramme/numprog?page={page}&size={size}&sort={sort},{dir}'}
      }
      this.resource = this.$resource('', {}, customActions);

      this.resource.findByNumProg({numProg: this.$route.params.numProg})
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.programmeInfo = data;
        });
      this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
        this.defaultPageable.sort, this.defaultPageable.dir);
    },
    methods :{
      launchRequest(pageNum, pageSize, sort, dir) {
        this.resource.findLigneProgrammeByProgramme({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir},this.$route.params.numProg)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.priamGrid.gridData.content = data;
            this.priamGrid.gridData.sort = [{"direction":"DESC","property":"dateFinChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}];

          });
      },
      rechercher(){
        this.resource.findLigneProgrammeByProgramme(this.$route.params.numProg)
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
            this.$store.dispatch('toutDesactiver', this.fichiersChecked.length !=0 && this.priamGrid.gridData.content.length == this.fichiersChecked.length );

          });
      },
      isTableauSelectionnable() {
        return this.tableauSelectionnable;
      },
      isStatusProgrammeAffecte(){
        return this.programmeInfo.statut === 'AFFECTE';
      },
      onEntryChecked(isChecked, entryChecked) {
        console.log('entryId='+entryChecked.id);
        console.log('isChecked='+isChecked);

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
        console.log('onEntryChecked() ==> this.fichiersChecked='+this.fichiersChecked.length);
      },

      onAllChecked(allChecked, entries) {
        console.log("entries checked=" +  entries);
        this.fichiersChecked = [];
        if(allChecked) {
          this.fichiersChecked = entries.slice();
          /*for(var i in entries) {
           console.log("element of entry = " + entries[i]);
           this.fichiersChecked.push(entries[i]);
           }*/
        } else {
          this.fichiersChecked = [];
        }

        this.$store.dispatch('toutDesactiver', true);
        console.log('onAllChecked() ==> this.fichiersChecked='+this.fichiersChecked.length);
      },
    },
    components : {
      vSelect : vSelect,
      priamGrid : Grid,
      modal: Modal
    }
  }

</script>

<style>

</style>
