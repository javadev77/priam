<template>
 <div>
  <div class="container-fluid sacem-formula">

    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title" @click="isCollapsed = !isCollapsed">
            <strong>Criteres de Recherche</strong>
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
                <div class="col-sm-2">
                  <select class="form-control" v-model="inputChgtCriteria.familleCode" @change="loadTypeUtilisation()">
                    <option value="ALL">Toutes</option>
                    <option v-for="(libelle, code) in familleOptions"
                            :value="code">
                      {{ libelle }}
                    </option>
                  </select>
                      <!--<select2 class="form-control" :options="familleOptions" v-model="inputChgtCriteria.familleCode" @change-list="loadTypeUtilisation()">-->
                        <!--<option value="ALL">Toutes</option>-->
                      <!--</select2>-->
                </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-2">
                <select class="form-control" :options="typeUtilisationOptions"  v-model="inputChgtCriteria.typeUtilisationCode">
                   <option value="ALL">
                    Tous
                    </option>
                    <option v-for="(libelle, code) in typeUtilisationOptions"
                          :value="code">
                    {{ libelle }}
                    </option>
                </select>
              </div>

            </div>
            <div class="row">
              <div class="col-sm-2">
                  <label class="control-label pull-right">Statut</label>
              </div>
              <div class="col-sm-6">
                <template v-for="item in statut">
                  <label class="checkbox-inline">
                    <input type="checkbox" v-model="inputChgtCriteria.statutCode" :value="item.code">{{item.libelle}}
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


  export default {

      data () {

          return {
            selected: 2,
            options: [
              { id: 1, text: 'Hello' },
              { id: 2, text: 'World' }
            ],
              isCollapsed: false,
              critereInit : {},

              inputChgtCriteria : {
                familleCode : 'ALL',
                typeUtilisationCode : 'ALL',
                statutCode         : []
              },

              resource : {},

              defaultPageable : {
                page : 0,
                size : 20
              },

              priamGrid : {
                  gridColumns : {
                    nomFichier: 'Nom Fichier',
                    famille: 'Famille',
                    typeUtilisation: 'Type Utilisation',
                    dateDebutChgt : 'Date Début Chagt',
                    dateFinChgt : 'Date Fin Chagt',
                    nbLignes : 'Nb Lignes',
                    statut : 'Statut',
                    logs: 'Logs',
                    abondon:  'Abondon'
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

          rechercher() {
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

          loadTypeUtilisation() {
              console.log("ddf");
            this.$store.dispatch('loadTypeUtilisation', this.inputChgtCriteria.familleCode);
            this.inputChgtCriteria.typeUtilisationCode = 'ALL';
          }
      },

      components : {
          priamGrid : Grid
      }

  }
</script>


<style scoped>
</style>
