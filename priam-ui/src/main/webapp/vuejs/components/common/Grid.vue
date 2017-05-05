<template>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title" @click="isCollapsed = !isCollapsed">
        <strong>Résultats</strong>
        <span class="pull-right glyphicon" :class="{'glyphicon-triangle-top' : isCollapsed, 'glyphicon-triangle-bottom' : !isCollapsed}"></span>
      </h5>
    </div>


    <div class="panel-collapse" :class="{collapse : isCollapsed}">
      <div class="panel-body">
        <paginator :current-page="pageOne.currentPage"
                   :total-pages="pageOne.totalPages"
                   :total-items="30"
                   @page-changed="pageOneChanged">

        </paginator>

        <table class="table">
            <thead>
            <tr>
              <th v-for="(value, key) in columns"
                  @click="sortBy(key)"
                  :class="{ active: sortKey == key }">

                {{ value | capitalize }}
                <span class="arrow" :class="sortOrders[key] > 0 ? 'asc' : 'dsc'">
                  </span>
              </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="entry in filteredData">
              <td v-for="(value, key) in columns" >
                {{entry[key]}}
              </td>
            </tr>
            </tbody>
        </table>

        <paginator :current-page="pageOne.currentPage"
                   :total-pages="pageOne.totalPages"
                   :total-items="30"
                   @page-changed="pageOneChanged">

        </paginator>

      </div>
    </div>
  </div>
</template>

<script>

  import Paginator from '../common/Pagination.vue'

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
        sortOrders: sortOrders,

        pageOne: {
          currentPage: 1,
          totalPages: 5
        }
      }
    },

    computed : {
      filteredData() {
        var sortKey = this.sortKey
        var filterKey = this.filterKey && this.filterKey.toLowerCase()
        var order = this.sortOrders[sortKey] || 1
        var data = this.data
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
        this.pageOne.currentPage = pageNum
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
</style>
