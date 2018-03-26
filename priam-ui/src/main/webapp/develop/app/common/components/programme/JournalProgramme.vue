<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Journal des événements</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h5 class="panel-title">
                <a>Résultats</a>
                <span class="pull-left collapsible-icon bg-ico-tablerow"></span>
              </h5>
            </div>
            <div class="panel-collapse">
              <div class="result-panel-body panel-body" style="height:600px; overflow-y:scroll;">
                <log-grid
                  v-if="logGrid.logGridData.content"
                  :data="logGrid.logGridData"
                  :columns="logGrid.logGridColumns"
                  noResultText="Aucun résultat."
                  @load-page="loadPage"
                  @on-sort="onSort">
                  >
                </log-grid>

              </div>
              <div class="row espacement">
                <button class="btn btn-default btn-primary pull-right" type="button" @click="$emit('cancel')">Fermer</button>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
    import Grid from '../ui/Grid';
    import ListSituationRender from '../ui/ListSituationRender';
    export default {
      name: "journal-programme",
      props: {
        /*numProg: {
          type: String,
          default: ''
        },*/
        numProg: ''
      },

      data() {
        return {
          resource : {},
          defaultPageable : {
            page : 1,
            sort : 'date',
            dir : 'ASC',
            size : this.$store.getters.userPageSize
          },
          logGrid: {
            logGridColumns: [
              {
                id: 'evenement',
                name: 'Evénement',
                sortable: true,
                type: 'text-centre',
                cell: {
                  toText: function (entry) {
                    var result = entry;
                    if (result != undefined)
                      return result;
                    else
                      return "";
                  }
                }
              },
              {
                id: 'ide12',
                name: 'IDE12',
                sortable: true,
                type: 'text-centre',
                cell: {
                  toText: function (entry) {
                    var result = entry;
                    if (result != undefined)
                      return result;
                    else
                      return "";
                  }
                }
              },
              {
                id: 'date',
                name: "Date",
                sortable: true,
                type: 'date',
                cell: {
                  css: function (entry) {

                      return {style: {'text-align' : 'justify'}}
                  }
                }
              },
              {
                id: 'utilisateur',
                name: "Utilisateur",
                sortable: true,
                type: 'long-text',
                cell: {
                  toText: function (entry) {
                    var result = entry;
                    if (result != undefined)
                      return result;
                    else
                      return "";
                  }
                }
              },
              {
                id: 'listSituationAvant',
                name: "Situation avant",
                sortable: false,
                type: 'inputNum',
                cellEditorFramework : ListSituationRender,
                cell: {
                  css: function (entry) {
                    return {style: {'text-align' : 'left'}}
                  },
                  toDisabled : function (entry) {
                    return false;
                  }
                }
              },
              {
                id: 'listSituationApres',
                name: "Situation après",
                sortable: false,
                type: 'inputNum',
                cellEditorFramework : ListSituationRender,
                cell: {
                toDisabled : function (entry) {
                    return false;
                  }
                }
              },
            ],
            logGridData: {

            },
            searchQuery: ''
          },
        }
      },


      created() {
        const customActions = {
          searchJournal:      {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/journal/search?page={page}&size={size}&sort={sort},{dir}'},
          searchJournalByNumProg: {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/journal/searchEvent?page={page}&size={size}&sort={sort},{dir}'}
        }
        this.resource = this.$resource('', {}, customActions);
        this.rechercherEvenements();
      },


      methods: {

        onSort(currentPage, pageSize, sort) {

            debugger;
          this.launchRequest(currentPage, pageSize, sort.property, sort.direction);

          this.defaultPageable.sort = sort.property;
          this.defaultPageable.dir = sort.direction;
        },

        loadPage: function(pageNum, size, sort) {
          this.defaultPageable.size = size;
          let pageSize = this.defaultPageable.size;

          this.launchRequest(pageNum, pageSize, sort.property, sort.direction);

        },

        launchRequest(pageNum, pageSize, sort, dir) {
          debugger;

          this.resource.searchJournalByNumProg({page : pageNum - 1, size : pageSize,
            sort : sort, dir: dir}, this.numProg)
            .then(response => {
              debugger;
              return response.json();
            })
            .then(data => {
              debugger;
              console.log("data : " + data);
              this.logGrid.logGridData = data;
              this.logGrid.logGridData.number = data.number + 1;
            });

        },
        rechercherEvenements() {
          debugger;
          this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
            this.defaultPageable.sort, this.defaultPageable.dir);
        }
      },
      components: {
        logGrid: Grid
      }
    }
</script>

<style>



</style>
