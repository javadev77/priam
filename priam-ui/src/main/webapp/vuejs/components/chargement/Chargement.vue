<template>
 <div>
  <div class="container-fluid sacem-formula">

    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title" @click="isCollapsed = !isCollapsed">
            <!--<strong>Critères de Recherche</strong>-->
          <a>Critères de Recherche</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
          <span class="pull-right glyphicon" :class="{'glyphicon-triangle-top' : isCollapsed, 'glyphicon-triangle-bottom' : !isCollapsed}"></span>
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
                  <!--<select class="form-control select select-primary" v-model="inputChgtCriteria.familleCode" @change="loadTypeUtilisation()">
                    <option value="ALL">Toutes</option>
                    <option v-for="(libelle, code) in familleOptions"
                            :value="code">
                      {{ libelle }}
                    </option>
                  </select>
                  -->
                  <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                  </v-select>
                </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-5">
               <!-- <select class="form-control" :options="typeUtilisationOptions"  v-model="inputChgtCriteria.typeUtilisationCode">
                   <option value="ALL">
                    Tous
                    </option>
                    <option v-for="(libelle, code) in typeUtilisationOptions"
                          :value="code">
                    {{ libelle }}
                    </option>
                </select>
                -->
                <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                </v-select>
              </div>

            </div>
            <div class="row">
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
          </form>
        </div>
      </div>
    </div>

    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercher()">Rechercher</button>
    </div>
 </div>

  <priam-grid
    :data="priamGrid.gridData"
    :columns="priamGrid.gridColumns"
    :filter-key="priamGrid.searchQuery">
  </priam-grid>
</div>
</template>



<script>

  import Grid from '../common/Grid.vue';
  import vSelect from '../common/Select.vue';

  export default {

      data () {

          return {
              selected: '',
              options: [{"id":"CMS","value":"CMS"},{"id":"COPIEPRIV","value":"Copie Privée"},{"id":"FDSVAL","value":"Valorisation"}],
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
                page : 0,
                size : 20
              },

              priamGrid : {
                  gridColumns : {
                    nomFichier: 'Fichier',
                    famille: 'Famille',
                    typeUtilisation: "Type d'utilisation",
                    dateDebutChgt : 'Début chargement',
                    dateFinChgt : 'Fin chargement',
                    nbLignes : 'Nb lignes',
                    statut : 'Statut',
                    logs: 'Logs',
                    action:  'Actions'
                  },
                  gridData : {"content":[{"id":11,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"04-05-2017 06:15:14","dateFinChgt":"04-05-2017 10:57:04","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":6,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"CPRIVAUDPL","dateDebutChgt":"02-05-2017 06:15:14","dateFinChgt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":10,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"02-05-2017 06:15:14","dateFinChgt":"01-05-2017 06:50:04","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":9,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"CPRIVSONRD","dateDebutChgt":"01-05-2017 05:10:14","dateFinChgt":"02-05-2017 01:10:00","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":5,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-05-2017 05:10:14","dateFinChgt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":8,"nomFichier":"Fichier 09","famille":"FDSVAL","typeUtilisation":"VALORIS","dateDebutChgt":"01-04-2017 05:15:14","dateFinChgt":"01-04-2017 10:10:11","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":4,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-04-2017 05:15:14","dateFinChgt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":1,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"04-02-2017 05:15:14","dateFinChgt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":2,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"03-02-2017 05:15:14","dateFinChgt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":3,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-02-2017 05:15:14","dateFinChgt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":7,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-02-2017 05:15:14","dateFinChgt":null,"nbLignes":6500,"statut":"EN_COURS"}],"last":true,"totalPages":1,"totalElements":11,"size":20,"number":0,"sort":null,"first":true,"numberOfElements":11},
                  //gridData : {},
                  searchQuery : ''
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

          familleTypeUtilMap() {
            return this.$store.getters.familleTypeUtilMap;
          },

          statut() {
            return this.$store.getters.statut;
          }

      },

      created() {
          const customActions = {
              search : {method : 'POST', url :'app/rest/chargement/search?page={page}&size={size}'}
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

              this.resource.search({page : this.defaultPageable.page, size : this.defaultPageable.size}, this.inputChgtCriteria)
                  .then(response => {
                      return response.json();
                  })
                  .then(data => {
                      this.priamGrid.gridData = data;
                  });
          },

          retablir() {

          },

          loadTypeUtilisation(val) {
              this.familleSelected = val;
              this.typeUtilisationSelected = {'id' : 'ALL', 'value': 'Tous'};
              this.$store.dispatch('loadTypeUtilisation', val);

          }
      },

      components : {
          priamGrid : Grid,
          vSelect : vSelect
      }

  }
</script>


<style scoped>
</style>
