<template>
  <div class="container-fluid">
        <temeplate v-if="emptyResult">
          <div class="row text-center ng-binding"  v-html="this.noResultText">
          </div>
        </temeplate>
    <template v-else>
          <paginator :current-page="this.data.number"
                     :total-pages="this.data.totalPages"
                     :total-items="this.data.totalElements"
                     :itemsPerPage="this.data.size"
                     @page-size-changed="pageSizeChanged"
                     @page-changed="pageOneChanged">

          </paginator>

          <table class="table-responsive table-drop table-bordered table-striped table-hover resultsTable">
              <thead>
              <tr>
                <th v-for="entry in columns">
                  <a @click="sortBy(entry)">
                    <span>{{ entry.name }}</span>
                    <span v-if="entry.sortable"
                          class="fui"
                          :class="{'fui-triangle-down':!sortAsc,
                                  'fui-triangle-up': sortAsc,
                                  'sorted' : entry.id === sortProp}">
                    </span>
                  </a>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="entry in filteredData">
                <!--<template v-for="(value,key) in columns">
                    <td v-if="key === 'statut'" v-status-color="statusColor(entry[key])" class="columnCenter">
                      {{ getStatutLibelleByKey(entry[key]) }}
                    </td>
                    <td class="columnCenter" v-else-if="key === 'dateDebutChgt'">
                      {{entry[key]}}
                    </td>
                    <td class="columnCenter" v-else-if="key === 'dateFinChgt'">
                      {{entry[key]}}
                    </td>
                    <td v-else-if="key === 'famille'">
                      {{ libelleFamilleByKey(entry[key]) }}
                    </td>
                    <td v-else-if="key === 'typeUtilisation'">
                      {{ libelleTypeUtilisationByKey(entry[key]) }}
                    </td>
                    <td v-else-if="isToShowActions(key, entry['statut'])" class="columnCenter">
                        <a @click="showPopupSupprimer(entry)">
                          <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span>
                        </a>
                    </td>

                    <td v-else-if="key === 'nbLignes'" class="columnRight">
                      {{entry[key]}}
                    </td>
                    <td v-else>
                      {{entry[key]}}
                    </td>
                </template>-->

                <template v-for="entryColumn in columns">
                  <template v-if="entryColumn.type === 'date'">
                    <td class="columnCenter">
                      {{ entry[entryColumn.id] }}
                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'numeric'">
                    <td class="columnRight">
                      {{ entry[entryColumn.id] }}
                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'code-value'">
                    <td>
                      {{entryColumn.cell.toText(entry[entryColumn.id])}}
                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'code-value-hightlight'">
                    <td>
                      <div v-html="entryColumn.cell.cellTemplate(entry)"></div>
                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'clickable-icon'">
                    <td class="columnCenter">
                      <a v-html="entryColumn.cell.cellTemplate(entry)" @click="emitCellClick(entry, entryColumn)">
                      </a>
                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'clickable-icons'">
                    <td class="columnCenter">
                        <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                          <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                          </a>
                        </template>
                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'numeric-link'">
                    <td class="columnRight">
                      <a @click="emitCellClick(entry, entryColumn)">
                        {{ entry[entryColumn.id] }}
                      </a>
                    </td>
                  </template>
                  <template  v-else>
                    <td>
                      {{ entry[entryColumn.id] }}
                    </td>
                  </template>

                </template>

              </tr>
              </tbody>
          </table>

          <paginator :current-page="this.data.number"
                     :total-pages="this.data.totalPages"
                     :total-items="this.data.totalElements"
                     :itemsPerPage="this.data.size"
                     @page-size-changed="pageSizeChanged"
                     @page-changed="pageOneChanged">

          </paginator>
        </template>
  </div>
</template>

<script>

  import Paginator from '../common/Pagination.vue';

  export default {

    props : ['data', 'columns', 'filterKey', 'noResultText'],

    data() {
      var sortOrders = {}

        for(let entry in this.columns) {
          sortOrders[entry.id] = 1;
        }

      return {
        isCollapsed: false,
        sortKey: '',
        sortOrders: sortOrders,
        sort : {},
        currentPage : 1,
        pageSize : 25

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

      sortAsc() {

          return this.sort && this.sort != null && this.sort.ascending === true;
      },

      sortProp() {
        if(this.sort !== undefined && this.sort!= null) {
            console.log("property" + this.sort.property)
          return this.sort.property;
        }
        return '';
      },

      emptyResult() {
        return this.data.content !== undefined  && this.data.content.length === 0 && this.data.totalElements === 0;
      },

      filteredData() {
        var sortKey = this.sortKey
        var filterKey = this.filterKey && this.filterKey.toLowerCase()
        var order = this.sortOrders[sortKey] || 1;
        var data = this.data.content;

        /*if (filterKey) {
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
        }*/
        return data
      }

    },

    mounted() {
        console.log(this.data.sort);
        this.sort = this.data.sort[0];

    },

    filters: {
      capitalize(str) {
        return str.charAt(0).toUpperCase() + str.slice(1)
      }
    },


    methods: {

      emitCellClick(entry, column) {
        this.$emit('cellClick', entry, column);
      },

      emitIconCellClick(event, entry, column) {
        this.$emit(event, entry, column);
      },


      sortBy(entryColumn) {
          //this.sortKey = key;
          //this.sortOrders[key] = this.sortOrders[key] * -1;
        //sort[0].property = entryColumn.id;
        //sort[0].property
        let sortProp = entryColumn.id;
        let isAsc = false;
        if(sortProp === this.sort.property ) {
          isAsc = !this.sort.ascending;
        } else {
          isAsc = true;
        }

        this.sort.ascending = isAsc;
        this.sort.property = sortProp;
        this.sort.direction = isAsc ? 'ASC' : 'DESC';

        this.$emit('on-sort', this.currentPage, this.pageSize, this.sort);
      },

      pageOneChanged (pageNum, pageSize) {
          this.currentPage = pageNum;
          this.$emit('load-page', pageNum, pageSize, this.sort);
      },

      pageSizeChanged(pageSize) {
        console.log("type of pageSize = "  + typeof pageSize);
        this.currentPage = 1;
        this.pageSize = pageSize;
        this.$emit('load-page', 1, pageSize, this.sort);
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
