<template>
  <div class="container-fluid">
    <div class="navbar navbar-default navbar-sm breadcrumb">
      <div class="titre-page">
        <span>Chargement</span>
      </div>
    </div>
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
                  <label class="control-label pull-right">Famille</label>
                </div>
                <div class="col-sm-4">

                  <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                  </v-select>
                </div>
                <div class="col-sm-2">
                  <label class="control-label pull-right">Type d'utilisation</label>
                </div>
                <div class="col-sm-5">

                  <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                  </v-select>
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
              <input type="checkbox" v-model="inputChgtCriteria.statutCode" :value="item.code">{{item.libelle}}
              <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
            </label>
          </template>
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
          <div class="result-panel-body panel-body">
            <priam-grid

              v-if="priamGrid.gridData.content"
              :data="priamGrid.gridData"
              :columns="priamGrid.gridColumns"
              noResultText="Aucun résultat."
              :filter-key="priamGrid.searchQuery"
              @cellClick="onCellClick"
              @load-page="loadPage"
              @on-sort="onSort"
              @show-log="onShowLog"
            >
            </priam-grid>
          </div>
        </div>
      </div>
    </div>

    <!--<p>Selected: {{ selected }}</p>
    <select2 :options="options" v-model="selected">
      <option disabled value="0">Select one</option>
    </select2>-->

    <modal v-if="showModal">
      <label class="homer-prompt-q control-label" slot="body">
        Etes-vous sûr de vouloir supprimer les données liées à ce fichier ?
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showModal = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="supprimerDonneesFichier">Oui</button>
      </template>
    </modal>

    <template v-if="showLogChargement">
      <app-log-chargement
        :idFichier = "selectedIdFichier"
        :hasErrors = "hasErrors"
        @close-log="showLogChargement = false"
      >
      </app-log-chargement>
    </template>

  </div>
</template>



<script>

  import Grid from '../common/Grid.vue';
  import vSelect from '../common/Select.vue';
  import Modal from '../common/Modal.vue';
  import select2 from '../common/Select2.vue';
  import moment from 'moment';
  import LogChargement from '../chargement/LogChargement.vue';

  export default {

    data () {

      var $this =this;
      var getters = this.$store.getters;

      return {
        selected : 2,
        showModal : false,
        entrySelected : {},
        selected: '',
        //options: [{"id":"CMS","value":"CMS"},{"id":"COPIEPRIV","value":"Copie Privée"},{"id":"FDSVAL","value":"Valorisation"}],
        options: [
          { id: 1, text: 'Hello' },
          { id: 2, text: 'World' }
        ],
        isCollapsed: false,
        critereInit : {},

        familleSelected : {'id' : 'ALL', 'value' : 'Toutes'},
        typeUtilisationSelected : {'id' : 'ALL', 'value' : 'Tous'},

        inputChgtCriteria : {
          familleCode : '',
          typeUtilisationCode : '',
          statutCode         : []
        },

        resource : {},

        defaultPageable : {
          page : 1,
          sort : 'dateDebutChargt',
          dir : 'desc',
          size : 25
        },

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
                  var result  = getters.famille.find(function (element) {
                    return element.id === cellValue;
                  });
                  return result !== undefined ? result.value : null;
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
                  return result !== undefined ? result.value : null;
                }
              }
            },
            {
              id :  'dateDebutChargt',
              name :   "Début chargement",
              sortable : true,
              type : 'date'
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
              id :  'nbLignes',
              name :   "Nb lignes",
              sortable : true,
              type : 'numeric'
            },
            {
              id :  'statut',
              name :   "Statut",
              sortable : true,
              type : 'code-value-hightlight',
              cell : {
                cellTemplate : function (entry) {
                  let element = $this.findStatusByCode(entry.statut);
                  var template = '<div style="padding-left : 2px; color : #fff; background-color: ' + element.color + '">' +
                    element.libelle +
                    '</div>'
                  return template;
                }
              }
            },
            {
              id :  'logs',
              name :   "Logs",
              sortable : false,
              type : 'clickable-icons',
              cell : {
                cellTemplate: function (cellValue) {
                  var templateLog = '<span class="glyphicon glyphicon-list-alt" aria-hidden="true" title="Log"></span>';
                  var template = [];
                  template.push({event : 'show-log', template : templateLog});
                  return template;

                }
              }

            },
            {
              id :  'action',
              name :   "Actions",
              sortable : false,
              type : 'clickable-icon',
              cell : {
                cellTemplate: function (cellValue) {
                  var tempalte = '<span class="glyphicon glyphicon-trash" aria-hidden="true" title="Abandonner"></span>';
                  var statusCode = cellValue.statut;
                  let element = $this.findStatusByCode(statusCode);
                  if(element !== undefined && ('CHARGEMENT_KO' === element.code || 'CHARGEMENT_OK' === element.code)) {
                    return tempalte;
                  }
                  return '';
                }
              }
            }
          ],
          //gridData : {"content":[{"id":254,"nomFichier":"FF_PENEF_EXTRANA_EXTCPRIVCPRIVAUDPL_201704061020001.csv","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/06/2017 16:17","dateFinChargt":null,"nbLignes":87933,"statut":"EN_COURS"},{"id":12,"nomFichier":"Fichier 15","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"24/05/2017 16:00","dateFinChargt":"24/05/2017 22:57","nbLignes":150780,"statut":"AFFECTE"},{"id":11,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/05/2017 18:15","dateFinChargt":"04/05/2017 22:57","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":10,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":"01/05/2017 18:50","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":6,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"02/05/2017 18:15","dateFinChargt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":5,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":9,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/05/2017 17:10","dateFinChargt":"02/05/2017 01:10","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":8,"nomFichier":"Fichier 09","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":"01/04/2017 22:10","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":4,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/04/2017 17:15","dateFinChargt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":1,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"04/02/2017 17:15","dateFinChargt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":2,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"03/02/2017 17:15","dateFinChargt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":3,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":7,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChargt":"01/02/2017 17:15","dateFinChargt":null,"nbLignes":6500,"statut":"EN_COURS"}],"last":true,"totalPages":1,"totalElements":13,"size":25,"number":0,"sort":[{"direction":"DESC","property":"dateDebutChargt","ignoreCase":false,"nullHandling":"NATIVE","ascending":false,"descending":true}],"first":true,"numberOfElements":13},
          //gridData : {"content":[],"totalElements":0,"totalPages":1,"last":true,"numberOfElements":25,"sort":null,"first":true,"size":25,"number":1},
          gridData : {},
          searchQuery : ''
        },

        showLogChargement : false,
        selectedIdFichier : null,
        hasErrors : false

      }
    },

    computed : {
      familleOptions() {
        return this.$store.getters.familleOptions;
      },

      typeUtilisationOptions() {
        return this.$store.getters.typeUtilisationOptions;
      },

      familleTypeUtilMap() {
        return this.$store.getters.familleTypeUtilMap;
      },

      statut() {
        return this.$store.getters.statut;
      }

    },

    created() {
      const customActions = {
        search : {method : 'POST', url :'app/rest/chargement/search?page={page}&size={size}&sort={sort},{dir}'},
        deleteFichier : {method : 'PUT', url :'app/rest/chargement/deleteFichier'}
      }
      this.resource= this.$resource('', {}, customActions);


      let statut = this.$store.getters.statut;
      for(var stt in statut) {
        if(statut[stt] && statut[stt].checked) {
          this.inputChgtCriteria.statutCode.push(statut[stt].code);
        }
      }

      this.rechercher();


    },

    methods: {

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

      launchRequest(pageNum, pageSize, sort, dir) {
        this.resource.search({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir}, this.inputChgtCriteria)
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log(data.sort);
            this.priamGrid.gridData = data;
            this.priamGrid.gridData.number = data.number + 1;

          });
      },

      onCellClick : function(entry, column) {
        this.selectedEntry = entry;
        console.log('columnn' + column.id)
        if(column.id === 'action') {
          this.showModal = true;
        }
      },

      supprimerDonneesFichier() {
        this.showModal = false;
        console.log('Supprimer le fichier selectionne : ' + this.selectedEntry.id);
        this.resource.deleteFichier({id : this.selectedEntry.id})
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log('data = ' + data);

            this.selectedEntry.statut = data.statut;

          });
      },

      findStatusByCode(code) {
        let statut = this.$store.getters.statut;
        return statut.find(function (element) {
          return element.code === code;
        })
      },

      getStatutLibelleByKey(key) {
        var status = this.findStatusByCode(key);

        return status !== undefined && status.libelle;
      },

      changeStatut() {
        this.$store.dispatch('changeStatut', this.inputChgtCriteria.statutCode);
      },

      isChecked (code) {
        var statutCode = this.inputChgtCriteria.statutCode;
        for(var i in statutCode) {
          if (statutCode[i] === code) {
            return true;
          }
        }

        return false;

      },

      rechercher() {

        this.inputChgtCriteria.typeUtilisationCode = this.typeUtilisationSelected.id;
        this.inputChgtCriteria.familleCode = this.familleSelected.id;

        this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
          this.defaultPageable.sort, this.defaultPageable.dir);
      },

      retablir() {
          this.familleSelected = {'id' : 'ALL', 'value': 'Toutes'};
          let statutFichier = this.$store.getters.statutFichier;
          this.inputChgtCriteria.statutCode = [statutFichier['EN_COURS'].code, statutFichier['CHARGEMENT_OK'].code, statutFichier['CHARGEMENT_KO'].code];
          this.rechercher();
      },

      loadTypeUtilisation(val) {
        this.familleSelected = val;
        this.typeUtilisationSelected = {'id' : 'ALL', 'value': 'Tous'};
        this.$store.dispatch('loadTypeUtilisation', val);

      },

      onShowLog(row, column) {
        this.selectedIdFichier = row.id;
        this.hasErrors = (row.statut != 'CHARGEMENT_OK');
        this.showLogChargement = true;
      }
    },

    components : {
        priamGrid : Grid,
        vSelect : vSelect,
        modal : Modal,
        select2: select2,
        AppLogChargement : LogChargement
    }

  }
</script>


<style scoped>
</style>
