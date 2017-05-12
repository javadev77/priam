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
                  <select class="form-control" v-model="inputChgtCriteria.familleCode">
                    <option v-for="option in critereInit.famille"
                            :value="option.code">
                      {{ option.libelle }}
                    </option>
                  </select>
                </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type D'utilisation</label>
              </div>
              <div class="col-sm-2">
                <select class="form-control"  v-model="inputChgtCriteria.typeUtilisationCode">
                  <option v-for="option in critereInit.typeUtilisation"
                          :value="option.code">
                    {{ option.libelle }}
                    </option>
                </select>
              </div>

            </div>
            <div class="row">
              <div class="col-sm-2">
                  <label class="control-label pull-right">Statut</label>
              </div>
              <div class="col-sm-6">
                <template v-for="item in critereInit.statuts">
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
      <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercher()">Rechercher</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
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
              isCollapsed: false,
              critereInit : {},

              inputChgtCriteria : {
                familleCode : '',
                typeUtilisationCode : '',
                statutCode         : []
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
                  gridData : {"content":[{"id":11,"nomFichier":"Fichier 13","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"04-05-2017 06:15:14","dateFinChgt":"04-05-2017 10:57:04","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":6,"nomFichier":"Fichier 06","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"02-05-2017 06:15:14","dateFinChgt":null,"nbLignes":15000,"statut":"EN_COURS"},{"id":10,"nomFichier":"Fichier 12","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"02-05-2017 06:15:14","dateFinChgt":"01-05-2017 06:50:04","nbLignes":15000,"statut":"CHARGEMENT_KO"},{"id":9,"nomFichier":"Fichier 11","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-05-2017 05:10:14","dateFinChgt":"02-05-2017 01:10:00","nbLignes":45789,"statut":"CHARGEMENT_OK"},{"id":5,"nomFichier":"Fichier 05","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-05-2017 05:10:14","dateFinChgt":null,"nbLignes":7451,"statut":"EN_COURS"},{"id":8,"nomFichier":"Fichier 09","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-04-2017 05:15:14","dateFinChgt":"01-04-2017 10:10:11","nbLignes":22000,"statut":"CHARGEMENT_OK"},{"id":4,"nomFichier":"Fichier 04","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-04-2017 05:15:14","dateFinChgt":null,"nbLignes":1478,"statut":"EN_COURS"},{"id":1,"nomFichier":"Fichier 01","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"04-02-2017 05:15:14","dateFinChgt":null,"nbLignes":3000,"statut":"EN_COURS"},{"id":2,"nomFichier":"Fichier 02","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"03-02-2017 05:15:14","dateFinChgt":null,"nbLignes":9500,"statut":"EN_COURS"},{"id":3,"nomFichier":"Fichier 03","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-02-2017 05:15:14","dateFinChgt":null,"nbLignes":6500,"statut":"EN_COURS"},{"id":7,"nomFichier":"Fichier 08","famille":"COPIEPRIV","typeUtilisation":"COPRIVSON","dateDebutChgt":"01-02-2017 05:15:14","dateFinChgt":null,"nbLignes":6500,"statut":"EN_COURS"}],"last":true,"totalPages":1,"totalElements":11,"size":20,"number":0,"sort":null,"first":true,"numberOfElements":11},
                  searchQuery : ''
              }

          }
      },

      created() {

          this.$http.get('app/rest/chargement/initCritereRecherche')
              .then(response => {
                  return response.json();
              })
              .then(data => {
                  this.critereInit = data;
              });

      },

      methods: {

          rechercher() {
            this.$http.post('app/rest/chargement/search?page=0&size=20', this.inputChgtCriteria)
              .then(response => {
                return response.json();
              })
              .then(data => {
                this.priamGrid.gridData = data;
              });
          },

          retablir() {
          }
      },

      components : {
          priamGrid : Grid
      }

  }
</script>


<style scoped>
</style>
