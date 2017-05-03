<template>
 <div class="col-sm-12">
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
          <div class="row">
            <div class="col-xs-2 pull-right">
              <button class="btn btn-default btn-primary" type="button" @click="rechercher()">Rechercher</button>
              <button class="btn btn-default btn-primary" type="button" @click="retablir()">Rétablir</button>
            </div>
          </div>
        </form>
      </div>

    </div>
  </div>

  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title">
        <strong>Liste de résultat</strong>
      </h5>
    </div>

    <div class="panel-body">
      <form id="search">
        Search <input name="query" v-model="priamGrid.searchQuery">
      </form>
        <priam-grid
          :data="priamGrid.gridData"
          :columns="priamGrid.gridColumns"
          :filter-key="priamGrid.searchQuery">
        </priam-grid>
    </div>


  </div>

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
                gridColumns : ['nomFichier', 'famille', 'typeUtilisation', 'dateDebutChgt', 'dateFinChgt', 'nbLignes'],
                gridData : [],
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
