<template>
  <div class="container-fluid">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title" @click="isCollapsed = !isCollapsed">
        <a>Résultats</a>
        <span class="pull-left collapsible-icon bg-ico-tablerow"></span>
        <span class="pull-right glyphicon" :class="{'glyphicon-triangle-top' : isCollapsed, 'glyphicon-triangle-bottom' : !isCollapsed}"></span>
      </h5>
    </div>


    <div class="panel-collapse" :class="{collapse : isCollapsed}">
      <div class="result-panel-body panel-body">
        <paginator :current-page="this.data.number"
                   :total-pages="this.data.totalPages"
                   :total-items="this.data.totalElements"
                   :itemsPerPage="this.data.numberOfElements"
                   :visiblePages="this.data.size"
                   @page-changed="pageOneChanged">

        </paginator>

        <table class="table-responsive table-drop table-bordered table-striped table-hover resultsTable">
            <thead>
            <tr>
              <th v-for="(value, key) in columns"
                  @click="sortBy(key)"
                  :class="{ active: sortKey == key }">

                {{ value }}
                <span class="arrow" :class="sortOrders[key] > 0 ? 'asc' : 'dsc'">
                  </span>
              </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="entry in filteredData">
              <!--<td v-for="key in columns" class="statusColor" :class="statusColor(key)">-->
              <template v-for="(value,key) in columns">
                  <!--<td class="statusColor" v-if="key === 'statut'" v-status-color="" :class="getStatutLibelleByKey(entry[key]).color">-->
                  <td v-if="key === 'statut'" v-status-color="getStatutLibelleByKey(entry[key]).color">
                    {{ getStatutLibelleByKey(entry[key]).libelle }}
                  </td>
                  <td class="columnCenter" v-else-if="key === 'dateDebutChgt'">
                    {{entry[key]}}
                  </td>
                  <td v-else-if="key === 'famille'">
                  {{ libelleFamilleByKey(entry[key]) }}
                  </td>
                  <td v-else-if="key === 'typeUtilisation'">
                  {{ libelleTypeUtilisationByKey(entry[key]) }}
                  </td>
                  <td v-else>
                    {{entry[key]}}
                  </td>
              </template>

            </tr>
            </tbody>
        </table>

        <paginator :current-page="this.data.number"
                   :total-pages="this.data.totalPages"
                   :total-items="this.data.totalElements"
                   :itemsPerPage="this.data.numberOfElements"
                   :visiblePages="this.data.size"
                   @page-changed="pageOneChanged">

        </paginator>

      </div>
    </div>
  </div>
  </div>
</template>

<script>

  import Paginator from '../common/Pagination.vue'
  import StatutFichier from '../../data/statutFichier'

  export default {

    props : ['data', 'columns', 'filterKey'],

    data() {
      var sortOrders = {}

        for(let key in this.columns) {
          sortOrders[key] = 1;
        }

      return {
        isCollapsed: false,
        sortKey: '',
        sortOrders: sortOrders

        /*pagination: {
          currentPage: this.data.number,
          totalPages: this.data.totalPages,
          totalElements : this.data.totalElements,
          numberOfElements : this.data.numberOfElements,
          size : this.data.size
        }*/
      }
    },

    computed : {
      filteredData() {
        var sortKey = this.sortKey
        var filterKey = this.filterKey && this.filterKey.toLowerCase()
        var order = this.sortOrders[sortKey] || 1
        var dataTemp = this.data.content;
        var data = dataTemp;

        if (filterKey) {
          data = data.filter(function (row) {
            return Object.keys(row).some(function (key) {
              return String(row[key]).toLowerCase().indexOf(filterKey) > -1
            })
          })
        }
        if (sortKey) {
          data = data.slice().sort(function (a, b) {
            a = a[sortKey]
            b = b[sortKey]
            return (a === b ? 0 : a > b ? 1 : -1) * order
          })
        }
        return data
      }

    },

    filters: {
      capitalize(str) {
        return str.charAt(0).toUpperCase() + str.slice(1)
      }
    },


    methods: {

      sortBy(key) {
        this.sortKey = key
        this.sortOrders[key] = this.sortOrders[key] * -1
      },

      pageOneChanged (pageNum) {
        this.pagination.currentPage = pageNum;
      },

      getStatutLibelleByKey (key) {

        var stat = StatutFichier;
        for(var i in stat) {
          if(stat[i].code === key) {

            console.log(stat[i]);
            return stat[i];
          }
        }
      },

      statusColor(key) {
        var stat = StatutFichier;
        for(var i in stat) {
          if(stat[i].code === key) {
            return stat[i].color;
          }
        }
      },

      libelleFamilleByKey(code) {
          var result = this.$store.getters.famille.find(function (element) {
            return element.id === code;
          });
          return result.value;

      },

      libelleTypeUtilisationByKey(code) {
          var result  = this.$store.getters.typeUtilisation.find(function (element) {
            return element.id === code;
          });
          return result.value;

      }
    },

    components : {
        paginator : Paginator
    }

  }
</script>

<style>
  .arrow {
    display: inline-block;
    vertical-align: middle;
    width: 0;
    height: 0;
    margin-left: 5px;
    opacity: 0.66;
  }

  .arrow.asc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-bottom: 4px solid #fff;
  }

  .arrow.dsc {
    border-left: 4px solid transparent;
    border-right: 4px solid transparent;
    border-top: 4px solid #fff;
  }

  .table-striped > tbody > tr:nth-child(odd) > td.statusColor.orange {
    color: black;
    background-color: orange;
  }

  .table-striped > tbody > tr:nth-child(even) > td.statusColor.orange {
    color: black;
    background-color: orange;
  }

  .table-striped > tbody > tr:nth-child(odd) > td.statusColor.green {
    color: black;
    background-color: green;
  }

  .table-striped > tbody > tr:nth-child(even) > td.statusColor.green {
    color: black;
    background-color: green;
  }

  .table-striped > tbody > tr:nth-child(odd) > td.statusColor.red {
    color: black;
    background-color: red;
  }

  .table-striped > tbody > tr:nth-child(even) > td.statusColor.red {
    color: black;
    background-color: red;
  }
</style>
