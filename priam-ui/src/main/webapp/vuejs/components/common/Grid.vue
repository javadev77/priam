<template>
  <div class="container-fluid">
        <template v-if="emptyResult">
          <div class="row text-center ng-binding"  v-html="this.noResultText">
          </div>
        </template>
    <template v-else>
          <paginator v-if="isPaginable"
                      :current-page="this.data.number"
                     :total-pages="this.data.totalPages"
                     :total-items="this.data.totalElements"
                     :itemsPerPage="this.data.size"
                     @page-size-changed="pageSizeChanged"
                     @page-changed="pageOneChanged">

          </paginator>

          <table class="table-responsive table-drop table-bordered table-striped table-hover resultsTable">
              <thead>
              <tr>
                <template v-for="entry in columns">
                  <template v-if="entry.type === 'checkbox'">
                    <th>
                        <input type="checkbox" v-model="allChecked" @click="emitAllCheckbox" />
                    </th>
                  </template>
                  <template v-else>
                    <th>
                      <a v-if="!isLocalSort" @click="serverSortBy(entry)">
                        <span>{{ entry.name }}</span>
                        <span v-if="entry.sortable"
                              class="fui"
                              :class="{'fui-triangle-down': !sortAsc,
                                  'fui-triangle-up': sortAsc,
                                  'sorted' : entry.id === sortProp}">
                        </span>
                      </a>
                      <a v-else @click="sortBy(entry.id)">
                        <span>{{ entry.name }}</span>
                        <span v-if="entry.sortable"
                              class="fui"
                              :class="{'fui-triangle-down': sortOrders[entry.id] <= 0,
                                  'fui-triangle-up': sortOrders[entry.id] > 0,
                                  'sorted' : entry.id === sortKey}"
                              >
                        </span>
                      </a>
                    </th>
                  </template>
                </template>

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
                  <template v-else-if="entryColumn.type === 'checkbox'">
                    <td class="columnCenter">
                      <input type="checkbox" ref="checkbox" :value="entryColumn.cell.toText(entry)" @click="emitCheckbox(entry)" />
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

          <paginator  v-if="isPaginable"
                      :current-page="this.data.number"
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

    props : {
      data : {
          type : Object
      },
      columns : {
          type : Array
      },
      filterKey : {
          type : String
      },
      noResultText : {
          type : String
      },
      isPaginable :{
          type  :  Boolean,
          default : true
      },

      isLocalSort : {
          type : Boolean,
          default : false
      }
    },

    data() {
      var sortOrders = {}

        for(let i in this.columns) {
          let entry = this.columns[i];
          sortOrders[entry.id] = 1;
        }



      return {
        selected : [],
        checkedCurrentEntry : new Map(),
        allChecked: false,
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
        return this.data.content !== undefined  && this.data.content.length === 0;
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
        }*/
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

    mounted() {
        console.log("this.data.sort="  + this.data.sort);
        if(this.isPaginable) {
          this.sort = this.data.sort !== undefined ? this.data.sort[0] : undefined;
        }

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

      emitCheckbox(entry) {
        let key = Number.parseInt(entry.id);
        console.log("emitCheckbox="+key)
          if(this.allChecked) {
              this.allChecked = false;

          }
          console.log("this.checkedCurrentEntry.get(key)="+this.checkedCurrentEntry.get(key))
          this.checkedCurrentEntry.set(key, !this.checkedCurrentEntry.get(key));

          this.$emit('entry-checked', this.checkedCurrentEntry.get(key), entry);

      },

      emitAllCheckbox() {
        console.log("emitAllCheckbox=" +  this.allChecked)

        console.log("this.$refs.checkbox=" +  this.$refs.checkbox);
        var entries = [];
        for(var i in this.$refs.checkbox) {
          let elem = this.$refs.checkbox[i];
          console.log("elem=" +  elem.checked);
          let key = Number.parseInt(elem.value);
          if(this.allChecked) {
            elem.checked =  1;

            entries.push(key);
            this.checkedCurrentEntry.set(key, true);
          } else {
            elem.checked = 0;
            this.checkedCurrentEntry.set(key, false);
          }

        }

        console.log("entries checked=" +  entries);

        this.$emit('all-checked', this.allChecked, entries);

      },


      serverSortBy(entryColumn) {
        console.log("serverSortBy");
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

      sortBy(key) {
        this.sortKey = key;
        this.sortOrders[key] = this.sortOrders[key] * -1;
        console.log("[key] = " + key);
        console.log("this.sortOrders[key] = " + this.sortOrders[key]);
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
