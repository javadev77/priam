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
          <log-grid
            v-if="logGrid.logGridData.content"
            :data="logGrid.logGridData"
            :columns="logGrid.logGridColumns"
            noResultText="Aucun résultat.">
          </log-grid>
          <div class="row espacement">
            <button class="btn btn-default btn-primary pull-right" type="button" @click="$emit('cancel')">Fermer</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
    import Grid from '../ui/Grid';
    export default {
        name: "journal-programme",
        props : {
          numProg: {
            type: String,
            default: ''
          }
        },

          data() {
            return {
              logGrid : {
                logGridColumns : [
                  {
                    id: 'evenement',
                    name: 'Evénement',
                    sortable: true,
                    type: 'text-centre',
                    cell: {
                      toText : function(entry) {
                        var result = entry;
                        if(result !=undefined)
                          return result ;
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
                      toText : function(entry) {
                        var result = entry;
                        if(result !=undefined)
                          return result ;
                        else
                          return "";
                      }
                    }
                  },
                  {
                    id :  'date',
                    name :   "Date",
                    sortable : true,
                    type : 'date',
                    cell : {
                      css: function (entry) {
                        if(entry !=undefined)
                        return {style: null}
                      }
                    }
                  },
                  {
                    id: 'utilisateur',
                    name: "Utilisateur",
                    sortable: true,
                    type: 'long-text',
                    cell: {
                      toText : function(entry) {
                        var result = entry;
                        if(result !=undefined)
                          return result ;
                        else
                          return "";
                      }
                    }
                  },
                  {
                    id: 'situationAvant',
                    name: "Situation avant",
                    sortable: true,
                    type: 'long-text',
                    cell: {
                      toText : function(entry) {
                        var result = entry;
                        if(result !=undefined)
                          return result ;
                        else
                          return "";
                      }
                    }
                  },
                  {
                    id: 'situationApres',
                    name: "Situation après",
                    sortable: true,
                    type: 'long-text',
                    cell: {
                      toText : function(entry) {
                        var result = entry;
                        if(result !=undefined)
                          return result ;
                        else
                          return "";
                      }
                    }
                  },
                  ],
                logGridData : {"content":[{"evenement":"evenement","ide12":"ide12","date":"06/06/2017 00:00","utilisateur":"utilisateur","situationAvant":"situationAvant","situationApres":"situationApres"}],"totalElements":1,"totalPages":1,"last":true,"numberOfElements":25,"sort":null,"first":true,"size":25,"number":1},
                searchQuery : ''
              },
            }
          },

          components : {
            logGrid : Grid,
          }
    }
</script>

<style>

</style>
