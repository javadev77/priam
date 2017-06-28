<template>
  <div class="container-fluid">
        <template v-if="emptyResult">
          <div class="row text-center ng-binding"  v-html="this.noResultText">
          </div>
        </template>
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
                          :class="{'fui-triangle-down': !sortAsc,
                                  'fui-triangle-up': sortAsc,
                                  'sorted' : entry.id === sortProp}">
                    </span>
                  </a>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="entry in filteredData">
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

                         <ul class="iconList">
                           <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                              <li>
                               <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                               </a>
                              </li>
                           </template>

                         </ul>

                    </td>
                  </template>
                  <template v-else-if="entryColumn.type === 'numeric-link'">
                    <td class="columnRight">
                      <template v-if="entryColumn.cell.toText(entry).isLink" >
                        <a @click="emitCellClick(entry, entryColumn)">
                          {{ entry[entryColumn.id] }}
                        </a>
                      </template>
                      <template v-else>
                        {{ entry[entryColumn.id] }}
                      </template>

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
        sort : [],
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
          console.log("this.sort.ascending="+this.sort.ascending)
          return this.sort !== undefined && this.sort !== null && this.sort.ascending;
      },

      sortProp() {
        if(this.sort !== undefined && this.sort!== null) {
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
        console.log("this.data.sort="  + this.data.sort);
        this.sort = this.data.sort !== undefined ? this.data.sort[0] : undefined;
    },

    filters: {
      capitalize(str) {
          return str.charAt(0).toUpperCase() + str.slice(1)
      }
    },


    methods: {

      emitCellClick(entry, column) {
         console.log("CellClick")
         this.$emit('cellClick', entry, column);
      },

      emitIconCellClick(event, entry, column) {
        this.$emit(event, entry, column);
      },


      sortBy(entryColumn) {
        console.log("sortBy");
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

  .iconList {
    margin: auto;
    padding: 0px;
    width: 50px;
  }

  .iconList li {
    display: inline;
    list-style: none; /* pour enlever les puces sur IE7 */
    margin: 5px;
  }

  .iconList li a {
    display: inline-block;
    padding: 5px 5px;
    text-decoration: none;
    width: 10px;
  }
</style>
