<template>
 <div class="col-sm-12">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title" @click="isCollapsed = !isCollapsed">

          <strong>Criteres de Recherche</strong>
        <span class="pull-right glyphicon" :class="{'glyphicon-triangle-top' : isCollapsed, 'glyphicon-triangle-bottom' : !isCollapsed}"></span>
      </h5>

    </div>
    <div class="priam-panel panel-collapse" :class="{collapse : isCollapsed}">
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
          <div class="row">
            <div class="col-sm-2 pull-right">
              <button class="btn btn-default btn-primary" type="button" @click="rechercher()">Rechercher</button>
              <button class="btn btn-default btn-primary" type="button" @click="retablir()">Rétablir</button>
            </div>
          </div>
        </form>
      </div>

    </div>
  </div>


      <!--
      <form id="search">
        Search <input name="query" v-model="priamGrid.searchQuery">
      </form>
      -->
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
                  gridData : [{"nomFichier":"Fichier 0","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":0,"statut":"En cours"},{"nomFichier":"Fichier 1","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":170,"statut":"Chargement OK"},{"nomFichier":"Fichier 2","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":360,"statut":"En cours"},{"nomFichier":"Fichier 3","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":510,"statut":"Chargement OK"},{"nomFichier":"Fichier 4","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":720,"statut":"En cours"},{"nomFichier":"Fichier 5","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":850,"statut":"Chargement OK"},{"nomFichier":"Fichier 6","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":1080,"statut":"En cours"},{"nomFichier":"Fichier 7","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":1190,"statut":"Chargement OK"},{"nomFichier":"Fichier 8","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":1440,"statut":"En cours"},{"nomFichier":"Fichier 9","famille":"Copie prive","typeUtilisation":"Copie privee sono","dateDebutChgt":"04/05/2017 16:02","dateFinChgt":"04/05/2017 16:02","nbLignes":1530,"statut":"Chargement OK"}
                  ],
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
            console.log(this.inputChgtCriteria);
            this.$http.post('app/rest/chargement/search', this.inputChgtCriteria)
              .then(response => {
                return response.json();
              })
              .then(data => {
                console.log(data);
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
