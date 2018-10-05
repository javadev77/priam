<template>

  <div>
    <div class="container-fluid">
    <priam-navbar titre1="Catalogue Musique des Sonorisations"  titre2="Statistiques"  :backButton="false"></priam-navbar>
    <div class="container-fluid sacem-formula">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h5 class="panel-title">
            <a>Dernière mises à jour des catalogues</a>
            <span class="pull-left"></span>
          </h5>
        </div>

        <priam-grid
          v-if="statistiqueGrid.gridData.content && !dataLoading"
          :isPaginable="true"
          :data="statistiqueGrid.gridData"
          :columns="statistiqueGrid.gridColumns"
          noResultText="Aucun résultat."
          @on-sort="onSort"
          @load-page="loadPage"
          class="col-sm-24">
        </priam-grid>

      </div>
    </div>
    </div>
  </div>


</template>

<script>

  import PriamNavbar from  './../../../common/components/ui/priam-navbar.vue';
  import Grid from './../../../common/components/ui/Grid.vue';
  export default {
    data() {
      var getters = this.$store.getters;
      return {
        dataLoading : true,
        defaultPageable : {
          page : 1,
          sort : 'date',
          dir : 'DESC',
          size : this.$store.getters.userPageSize
        },
        resource : {},
        statistiqueGrid : {
          gridData : {},

          gridColumns : [

            {
              id: 'typeCMS',
              name: "Type Cat.",
              sortable: false,
              type: 'code-value-left',
              cell : {

                css : function (entry) {
                  return {
                    style : {
                      width : '400px'
                    }
                  }
                },

                toText : function(cellValue) {
                  var result = getters.libelleTypeCatalogueForStat.find(function (element) {
                    return element.id === cellValue;
                  });
                  return result !== undefined && result.value;
                }
              }
            },

            {
              id: 'date',
              name: "Date MAJ",
              sortable: true,
              type: 'date',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '400px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id : 'nomFichier',
              name :'Nom Fichier',
              sortable : false,
              type : 'text-centre',
              cell: {
                css : function (entry) {
                  return {
                    style : {
                      width : '800px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id :  'nbOeuvres',
              name :   "Nb oeuvres",
              sortable : false,
              type : 'numeric',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '400px'
                    }
                  }
                },
                toText: function (entry) {
                  return entry.nbOeuvres;
                }
              }
            },

            {
              id :  'nbCreation',
              name :   "Nb création",
              sortable : false,
              type : 'numeric',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '400px'
                    }
                  }
                },
                toText: function (entry) {
                  return entry.nbCreation;
                }
              }
            },
            {
              id :  'nbRenouvellement',
              name :   "Nb renouvellement",
              sortable : false,
              type : 'numeric',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '400px',

                    }
                  }
                },
                toText: function (entry) {
                  return entry.nbRenouvellement;
                }
              }
            },

            {
              id :  '',
              name :   "",
              sortable : false,
              type : 'column-empty',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      'background-color' : 'transparent',
                      'border' : '0px !important'
                    }
                  }
                }
              }
            },

            {
              id :  'nbTotalOeuvres',
              name :   "Nb total oeuvres",
              sortable : false,
              type : 'numeric',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '400px'
                    }
                  }
                },
                toText: function (entry) {
                  return entry.nbTotalOeuvres;
                }
              }
            }

          ],
          searchQuery : ''
        },
      }
    },
    created() {
      const customActions = {
        searchStatistiques : {
          method : 'GET',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/statistiques/search?page={page}&size={size}&sort={sort},{dir}'}
      }
      this.resource= this.$resource('', {}, customActions);
      this.findStatistiques();
    },
    methods : {
      findStatistiques() {
        this.dataLoading = true;
        debugger
        this.resource.searchStatistiques({
          page : this.defaultPageable.page - 1,
          size : this.defaultPageable.size,
          sort : this.defaultPageable.sort,
          dir  : this.defaultPageable.dir})
          .then(response => {
            return response.json();
          })
          .then(data => {

            this.statistiqueGrid.gridData = data;

            this.statistiqueGrid.gridData.number = data.number + 1;
            this.dataLoading = false;
          })
          .catch(error => {
            alert("Erreur lors de la recherche !!! ")
          });
      },
      onSort(currentPage, pageSize, sort) {

        this.launchRequest(currentPage, pageSize,
          sort.property, sort.direction);

        this.defaultPageable.sort = sort.property;
        this.defaultPageable.dir = sort.direction;
      },

      loadPage: function(pageNum, size, sort) {
        this.defaultPageable.size = size;
        let pageSize = this.defaultPageable.size;
        this.launchRequest(pageNum, pageSize, sort.property, sort.direction);
      },

      launchRequest(pageNum, pageSize, sort, dir) {

        this.dataLoading = true;
        this.resource.searchStatistiques({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir})
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.statistiqueGrid.gridData = data;
            this.statistiqueGrid.gridData.number = data.number + 1;
            this.dataLoading = false;

          });
      }
    },
    components : {
      'priam-navbar' : PriamNavbar,
      priamGrid : Grid
    },
    computed : {
      typeStatistiquesOptions() {
        return this.$store.getters.libelleTypeCatalogueForStat;
      }
    }
  }

</script>
