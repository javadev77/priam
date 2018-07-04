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
                      <span>
                        <input
                          :disabled="entry.cell.isDisabled()"
                          type="checkbox"
                          v-model="allChecked"
                          @click="emitAllCheckbox" />
                      </span>
                    </th>
                  </template>
                  <template v-else>
                    <th v-if="entry.sortable">
                      <a v-if="!isLocalSort" @click="serverSortBy(entry)">
                        <span>{{ entry.name }}</span>
                        <span v-if="entry.sortable"
                              class="fui"
                              :class="{'fui-triangle-down': !sortAsc,
                                  'fui-triangle-up': sortAsc,
                                  'sorted' : isSorted(entry)}">
                        </span>
                      </a>
                      <a v-else @click="sortBy(entry.id)">
                        <span>{{ entry.name }}</span>
                        <span v-if="entry.sortable"
                              class="fui"
                              :class="{'fui-triangle-down': sortOrders[entry.id] < 0,
                                  'fui-triangle-up': sortOrders[entry.id] > 0,
                                  'sorted' : entry.id === sortKey}"
                              >
                        </span>
                      </a>
                    </th>
                    <th v-else>
                      <span>{{ entry.name }}</span>
                    </th>
                  </template>
                </template>

              </tr>
              </thead>
              <tbody>

              <template v-for="entry in filteredData">
                <!--<tr v-if="entry['numProg'] !== undefined" class="special-row" :id="entry['numProg']"
                    :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}"
                  :title="afficherTootlip(entry)">
                  <template v-for="entryColumn in columns">
                    <template v-if="entryColumn.type === 'date'">
                      <td class="columnCenter" :style="">

                          {{ entry[entryColumn.id] }}


                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'numeric'">
                      <td class="columnRight" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        {{ entryColumn.cell.toText(entry) | numberFormat }}
                      </td>
                    </template>

                    <template v-else-if="entryColumn.type === 'code-value'">
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        {{entryColumn.cell.toText(entry[entryColumn.id])}}
                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'code-value-hightlight'">
                      <td :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        <div v-html="entryColumn.cell.cellTemplate(entry)"></div>
                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'clickable-icon'">
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        <a v-html="entryColumn.cell.cellTemplate(entry)" @click="emitCellClick(entry, entryColumn)">
                        </a>
                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'clickable-icons'">
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">

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
                    <template v-else-if="entryColumn.type === 'clickable-icons-or-text'">
                      <td class="columnCenter"   v-if="!entryColumn.cell.isText(entry)"
                          :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        <ul class="iconList">
                          <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                            <li>
                              <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                              </a>
                            </li>
                          </template>

                        </ul>
                      </td>
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}" v-else>
                        <template v-if="entryColumn.cell.toText(entry).isLink" >
                          <a @click="emitCellClick(entry, entryColumn)">
                            {{ entryColumn.cell.toText(entry).value }}
                          </a>
                        </template>
                        <template v-else>
                          {{ entryColumn.cell.toText(entry).value }}
                        </template>
                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'numeric-link'">
                      <td class="columnRight" :style="entryColumn.cell.toText(entry).style">
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
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        <input :disabled="entryColumn.cell.isDisabled()"
                               type="checkbox"
                               ref="checkbox"
                               :value="entryColumn.cell.toText(entry)"
                               :checked="entryColumn.cell.isChecked(entry)"
                               @click="emitCheckbox(entry, entryColumn.cell.toText(entry), entryColumn.cell.isChecked(entry))" />

                        &lt;!&ndash;<label class="checkbox checkbox-inline" :class="{'checked' : entryColumn.cell.isChecked(entry) == 1}">
                          <input class="checkbox checkbox-inline"
                                  :disabled="entryColumn.cell.isDisabled()"
                                 type="checkbox"
                                 ref="checkbox"
                                 :value="entryColumn.cell.toText(entry)"
                                 :checked="entryColumn.cell.isChecked(entry)"
                                 @click="emitCheckbox(entry)" />

                          <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
                        </label>&ndash;&gt;

                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'seconds-as-time'">
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        {{ dureeFormattee(entry[entryColumn.id]) }}
                      </td>
                    </template>
                    <template v-else-if="entryColumn.type === 'text-centre'">
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        {{ entry[entryColumn.id] }}
                      </td>
                    </template>

                    <template v-else-if="entryColumn.type === 'text-with-action'">
                      <td class="columnCenter" v-if="entryColumn.cell.toText(entry).action"
                          :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">

                        {{entryColumn.cell.toText(entry).value}}
                        <template v-for="elem in entryColumn.cell.toText(entry).template">
                          <a v-html="elem.template" v-if="elem.disabled" class="disabled"></a>
                          <a v-html="elem.template" v-if="!elem.disabled" @click="emitIconCellClick(elem.event, entry, entryColumn)"></a>
                        </template>

                      </td>
                      <td class="columnCenter" :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}"
                          v-if="!entryColumn.cell.toText(entry).action">
                        {{ entryColumn.cell.toText(entry.ajout).value }}
                      </td>
                    </template>

                    <template  v-else>
                      <td :style="{'background-color' : entry.statutEligibilite === 'EN_ATTENTE_ELIGIBILITE' || entry.statutEligibilite === 'EN_COURS_ELIGIBILITE' ? 'grey': ''}">
                        {{ entry[entryColumn.id] }}
                      </td>
                    </template>

                  </template>
                </tr>-->
                <template v-if="rowTooltip == undefined">
                  <tr>
                    <template v-for="entryColumn in columns">
                      <template v-if="entryColumn.type === 'date'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{ entry[entryColumn.id] }}
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'numeric'">
                        <td class="columnRight" :style="styleValue(entryColumn, entry)">
                          {{ entryColumn.cell.toText(entry) | numberFormat }}
                        </td>
                      </template>

                      <template v-else-if="entryColumn.type === 'code-value'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{entryColumn.cell.toText(entry[entryColumn.id])}}
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'code-value-hightlight'">
                        <td :style="styleValue(entryColumn, entry)">
                          <div v-html="entryColumn.cell.cellTemplate(entry)"></div>
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'clickable-icon'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          <a v-html="entryColumn.cell.cellTemplate(entry)" @click="emitCellClick(entry, entryColumn)">
                          </a>
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'clickable-icons'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">

                          <table style="margin: auto;">
                            <tr>
                              <!--<ul class="iconList">-->
                              <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                                <td style="width: 20px; border: none !important">
                                  <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                                  </a>
                                </td>
                              </template>
                            </tr>
                          </table>

                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'clickable-icons-or-text'">
                        <td class="columnCenter"  v-if="!entryColumn.cell.isText(entry)" :style="styleValue(entryColumn, entry)">
                          <!--<ul class="iconList">-->
                           <table>
                             <tr>
                            <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                              <td>
                                <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                                </a>
                              </td>
                            </template>
                             </tr>
                           </table>
                          <!--</ul>-->
                        </td>
                        <td class="columnCenter"  v-else :style="styleValue(entryColumn, entry)">
                          <template v-if="entryColumn.cell.toText(entry).isLink" >
                            <a @click="emitCellClick(entry, entryColumn)">
                              {{ entryColumn.cell.toText(entry).value }}
                            </a>
                          </template>
                          <template v-else>
                            {{ entryColumn.cell.toText(entry).value }}
                          </template>
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'numeric-link'">
                        <td class="columnRight" :style="styleValue(entryColumn, entry)">
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
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          <input :disabled="entryColumn.cell.isDisabled()"
                                 type="checkbox"
                                 ref="checkbox"
                                 :value="entryColumn.cell.toText(entry)"
                                 :checked="entryColumn.cell.isChecked(entry)"
                                 @click="emitCheckbox(entry, entryColumn.cell.toText(entry), entryColumn.cell.isChecked(entry))" />

                          <!--<label class="checkbox checkbox-inline" :class="{'checked' : entryColumn.cell.isChecked(entry) == 1}">
                            <input class="checkbox checkbox-inline"
                                    :disabled="entryColumn.cell.isDisabled()"
                                   type="checkbox"
                                   ref="checkbox"
                                   :value="entryColumn.cell.toText(entry)"
                                   :checked="entryColumn.cell.isChecked(entry)"
                                   @click="emitCheckbox(entry)" />

                            <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
                          </label>-->

                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'seconds-as-time'">
                        <td v-if="!entryColumn.editable" class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{ dureeFormattee(entry[entryColumn.id]) }}
                        </td>
                        <td v-else class="columnCenter">
                          <component :is="entryColumn.cellEditorFramework" v-model="entry[entryColumn.id]"></component>

                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'text-centre'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{ entry[entryColumn.id] }}
                        </td>
                      </template>

                      <template v-else-if="entryColumn.type === 'text-with-action'">
                        <td class="columnCenter" v-if="entryColumn.cell.toText(entry).action" :style="styleValue(entryColumn, entry)">

                          {{entryColumn.cell.toText(entry).value}}
                          <template v-for="elem in entryColumn.cell.toText(entry).template">
                            <a v-html="elem.template" v-if="elem.disabled" class="disabled"></a>
                            <a v-html="elem.template" v-if="!elem.disabled" @click="emitIconCellClick(elem.event, entry, entryColumn)"></a>
                          </template>

                        </td>
                        <td class="columnCenter" v-if="!entryColumn.cell.toText(entry).action" :style="styleValue(entryColumn, entry)">
                          {{ entryColumn.cell.toText(entry).value }}
                        </td>
                      </template>

                      <template v-else-if="entryColumn.type === 'inputNum'">
                        <td style="width: 130px;">

                          <template v-if="entryColumn.cell.toDisabled(entry)">
                            <component :is="entryColumn.cellEditorFramework"
                                       v-model="entry[entryColumn.id]"
                                       :disabled="true"
                            ></component>
                          </template>
                          <tempalte v-else>
                            <component :is="entryColumn.cellEditorFramework"
                                       v-model="entry[entryColumn.id]"
                                       :disabled="false"
                                       @valueChanged="entryColumn.cell.onCellValueChanged(entry, $event)"
                            ></component>
                          </tempalte>

                        </td>
                      </template>

                      <template  v-else>
                        <td :style="styleValue(entryColumn, entry)">
                          {{ entry[entryColumn.id] }}
                        </td>
                      </template>

                    </template>
                  </tr>
                </template>
                <template v-else>
                  <tr :title="rowTooltip(entry)">
                    <template v-for="entryColumn in columns">
                      <template v-if="entryColumn.type === 'date'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{ entry[entryColumn.id] }}
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'numeric'">
                        <td class="columnRight" :style="styleValue(entryColumn, entry)">
                          {{ entryColumn.cell.toText(entry) | numberFormat }}
                        </td>
                      </template>

                      <template v-else-if="entryColumn.type === 'code-value'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{entryColumn.cell.toText(entry[entryColumn.id])}}
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'code-value-hightlight'">
                        <td :style="styleValue(entryColumn, entry)">
                          <div v-html="entryColumn.cell.cellTemplate(entry)"></div>
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'clickable-icon'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          <a v-html="entryColumn.cell.cellTemplate(entry)" @click="emitCellClick(entry, entryColumn)">
                          </a>
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'clickable-icons'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          <table style="margin: auto;">
                            <tr>
                          <!--<ul class="iconList">-->
                            <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                              <td style="width: 20px; border: none !important">
                                <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                                </a>
                              </td>
                            </template>
                            </tr>
                          </table>

                          <!--</ul>-->

                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'clickable-icons-or-text'">
                        <td class="columnCenter"  v-if="!entryColumn.cell.isText(entry)" :style="styleValue(entryColumn, entry)">
                          <table style="margin: auto;">
                            <tr>
                              <template v-for="elem in entryColumn.cell.cellTemplate(entry)">
                                <td style="width: 20px; border: none !important">
                                  <a v-html="elem.template" @click="emitIconCellClick(elem.event, entry, entryColumn)">
                                  </a>
                                </td>
                              </template>
                            </tr>
                          </table>
                        </td>
                        <td class="columnCenter"  v-else :style="styleValue(entryColumn, entry)">
                          <template v-if="entryColumn.cell.toText(entry).isLink" >
                            <a @click="emitCellClick(entry, entryColumn)">
                              {{ entryColumn.cell.toText(entry).value }}
                            </a>
                          </template>
                          <template v-else>
                            {{ entryColumn.cell.toText(entry).value }}
                          </template>
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'numeric-link'">
                        <td class="columnRight" :style="styleValue(entryColumn, entry)">
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
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          <input :disabled="entryColumn.cell.isDisabled()"
                                 type="checkbox"
                                 ref="checkbox"
                                 :value="entryColumn.cell.toText(entry)"
                                 :checked="entryColumn.cell.isChecked(entry)"
                                 @click="emitCheckbox(entry, entryColumn.cell.toText(entry), entryColumn.cell.isChecked(entry))" />

                          <!--<label class="checkbox checkbox-inline" :class="{'checked' : entryColumn.cell.isChecked(entry) == 1}">
                            <input class="checkbox checkbox-inline"
                                    :disabled="entryColumn.cell.isDisabled()"
                                   type="checkbox"
                                   ref="checkbox"
                                   :value="entryColumn.cell.toText(entry)"
                                   :checked="entryColumn.cell.isChecked(entry)"
                                   @click="emitCheckbox(entry)" />

                            <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
                          </label>-->

                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'seconds-as-time'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{ dureeFormattee(entry[entryColumn.id]) }}
                        </td>
                      </template>
                      <template v-else-if="entryColumn.type === 'text-centre'">
                        <td class="columnCenter" :style="styleValue(entryColumn, entry)">
                          {{ entry[entryColumn.id] }}
                        </td>
                      </template>

                      <template v-else-if="entryColumn.type === 'text-with-action'">
                        <td class="columnCenter" v-if="entryColumn.cell.toText(entry).action" :style="styleValue(entryColumn, entry)">

                          {{entryColumn.cell.toText(entry).value}}
                          <template v-for="elem in entryColumn.cell.toText(entry).template">
                            <a v-html="elem.template" v-if="elem.disabled" class="disabled"></a>
                            <a v-html="elem.template" v-if="!elem.disabled" @click="emitIconCellClick(elem.event, entry, entryColumn)"></a>
                          </template>

                        </td>
                        <td class="columnCenter" v-if="!entryColumn.cell.toText(entry).action" :style="styleValue(entryColumn, entry)">
                          {{ entryColumn.cell.toText(entry.ajout).value }}
                        </td>
                      </template>

                      <template  v-else>
                        <td :style="styleValue(entryColumn, entry)">
                          {{ entry[entryColumn.id] }}
                        </td>
                      </template>

                    </template>
                  </tr>
                </template>


              </template>
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

  import Paginator from './Pagination.vue';
  import moment from 'moment';

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
      },

      rowTooltip : {
          type : Function,
          default : null
      }
    },

    data() {
      var sortOrders = {}

        for(let i in this.columns) {
          let entry = this.columns[i];
          sortOrders[entry.id] = -1;
        }

      return {
        allCheckedHidden : false,
        checkedList : [],
        selected : [],
        checkedCurrentEntry : new Map(),
        allChecked: false,
        isCollapsed: false,
        sortKey: '',
        sortOrders: sortOrders,
        sort : [],
        currentPage : 1,
        pageSize : this.$store.getters.userPageSize

      }
    },

    watch : {

      allNotChecked : function (newVal) {
        console.log('watch() => allChecked : newVal : %s',  newVal);
        this.allChecked = newVal;
      }
    },

    computed : {


      allNotChecked() {
        return this.$store.getters.allFichiersChecked;
      },

      sortAsc() {
          console.log("this.sort.ascending="+this.sort.ascending)
          return this.sort !== undefined && this.sort !== null && this.sort.ascending;
      },



      emptyResult() {
        return this.data.content !== undefined  && this.data.content.length === 0;
      },

      filteredData() {
        var sortKey = this.sortKey
        var order = this.sortOrders[sortKey] || 1;
        var data = this.data.content;
        var $this = this;

        if (sortKey) {
            let columnDef = this.columns.find(function (elem) {
              return elem.id === sortKey;
            });
            if(columnDef !== undefined && columnDef.type === 'date') {
              data = data.slice().sort(function (a, b) {
                a = a[sortKey];
                b = b[sortKey];
                console.log("typeof a "+  typeof a);
                let date1 = moment(a, "DD/MM/YYYY HH:mm");
                let date2 = moment(b, "DD/MM/YYYY HH:mm");

                return (date1.isSame(date2) ? 0 : date1.isAfter(date2) ? 1 : -1) * order;
              });
            } else {

              data = data.slice().sort(function (a, b) {
                a = a[sortKey];
                b = b[sortKey];
                return (a === b ? 0 : a > b ? 1 : -1) * order
              });

            }

        }
        return data
      }

    },

    created() {

        debugger;
        this.sort = this.data.sort !== undefined ? this.data.sort[0] : undefined;

        if(this.isLocalSort) {
            this.sortKey = this.sort.property;
        }

        this.allChecked = this.$store.getters.allFichiersChecked;

    },

    filters: {
      capitalize(str) {
          return str.charAt(0).toUpperCase() + str.slice(1)
      }
    },


    methods: {





      isSorted(entryColumn) {
        if(this.sort !== undefined && this.sort!== null) {
          let sortProp = entryColumn.sortProperty !== undefined  ? entryColumn.sortProperty : entryColumn.id;
          console.log("property" + this.sort.property)
          return sortProp === this.sort.property;
        }
        return false;
      },

      dureeFormattee(duree) {

        let jours = Math.floor( duree / 86400);
        let reste = duree % 86400;
        let hours = Math.floor( reste / 3600);
        reste = reste % 3600;
        let minutes = Math.floor(reste / 60);
        let seconds = reste % 60;


        return ((jours < 10) ? '0'+jours : jours) + 'j ' +((hours < 10) ? '0'+hours : hours)+"h "+((minutes < 10) ? '0' + minutes: minutes)+"m "+ ((seconds < 10) ? '0'+seconds : seconds) + "s";

      },
      emitCellClick(entry, column) {
         console.log("CellClick")
         this.$emit('cellClick', entry, column);
      },

      emitIconCellClick(event, entry, column) {
        this.$emit(event, entry, column);
      },

      inputValue(event, column, columnModel) {
          debugger;
        columnModel = event;
        this.$emit('onCellValueChanged', column, columnModel);
      },

      emitCheckbox(entry, entryKey, value) {
        var key = null;//Number.parseInt(entry.id);

        if(this.isNumber(entryKey)) {
          key = Number.parseInt(entryKey);
        } else {
          key = JSON.parse(entryKey);
        }

        console.log("emitCheckbox id=%s, value=%s", key, value);
          if(this.allChecked) {
              this.allChecked = false;

          }

          this.checkedCurrentEntry.set(key, value == 1 ? false : true);
         console.log("this.checkedCurrentEntry.get(key)="+this.checkedCurrentEntry.get(key));

          this.$emit('entry-checked', this.checkedCurrentEntry.get(key), entry);

      },

      emitAllCheckbox() {

        console.log("emitAllCheckbox=" +  this.allChecked)

        console.log("this.$refs.checkbox=" +  this.$refs.checkbox.length);
        var entries = [];
        for(var i in this.$refs.checkbox) {
          let elem = this.$refs.checkbox[i];
          console.log("elem=" +  elem.value);

          if( elem.value !== undefined) {

            var key = null;
            if(this.isNumber(elem.value)) {
              key = Number.parseInt(elem.value);
            } else {
              key = JSON.parse(elem.value);
            }

            entries.push(key);
            if(this.allChecked) {
              elem.checked =  1;


              this.checkedCurrentEntry.set(key, true);
            } else {
              elem.checked = 0;
              this.checkedCurrentEntry.set(key, false);
            }
          }

        }

        console.log("this.checkedCurrentEntry=" +  entries.length);

        this.$emit('all-checked', this.allChecked, entries);

      },


      serverSortBy(entryColumn) {
        console.log("serverSortBy");
        let sortProp = entryColumn.sortProperty !== undefined  ? entryColumn.sortProperty : entryColumn.id;
        let isAsc = false;
        if(sortProp == this.sort.property ) {
          isAsc = !this.sort.ascending;
        } else {
          isAsc = true;
        }

        this.sort.ascending = isAsc;
        this.sort.property = sortProp;
        this.sort.direction = isAsc ? 'ASC' : 'DESC';


        debugger;

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
      },
      isNumber(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
      },

      styleValue(entryColumn, entry) {
          if(entryColumn.cell !== undefined && entryColumn.cell.css !== undefined) {
            return entryColumn.cell.css(entry).style
          }
          return {}
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

  .iconList td {
    width: 20px;
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

  .disabled {
    pointer-events: none;
    cursor: default;
    color: gray;
  }

  tr.special-row > td {
    background-color: gray;
  }

</style>
