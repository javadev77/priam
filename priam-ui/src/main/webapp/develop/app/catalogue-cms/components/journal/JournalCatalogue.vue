<template>

  <div>
    <div class="container-fluid">
    <priam-navbar titre1="Catalogue Musique des Sonorisations"  titre2="Journal d'événements"  :backButton="false"></priam-navbar>

      <div class="container-fluid sacem-formula">

    <div class="panel panel-default">

      <div class="panel-heading">
        <h5 class="panel-title" @click="isCollapsed = !isCollapsed">
          <a>Critères de recherche</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
          <span class="pull-right fa" :class="{'fui-triangle-up' : isCollapsed,  'fui-triangle-down' : !isCollapsed}"></span>
        </h5>
      </div>

      <div class="panel-collapse" :class="{collapse : isCollapsed}">
        <div class="panel-body">
          <form class="form-horizontal">

            <div class="row" v-if="errors.count()!=0">
              <ul style="list-style: none">

                <li v-if="errors.has('periodeDebutEvenement')">
                  <i v-show="errors.has('periodeDebutEvenement')" class="fa fa-warning"></i>
                  <label v-show="errors.has('periodeDebutEvenement')" :class="{'has-error': errors.has('periodeDebutEvenement') }">{{ errors.first('periodeDebutEvenement') }}</label>
                </li>

                <li v-if="errors.has('periodeFinEvenement')">
                  <i v-show="errors.has('periodeFinEvenement')" class="fa fa-warning"></i>
                  <label v-show="errors.has('periodeFinEvenement')" :class="{'has-error': errors.has('periodeFinEvenement') }">{{ errors.first('periodeFinEvenement') }}</label>
                </li>

              </ul>
            </div>

            <div class="row">
              <div class="col-md-3">
                <label class="control-label pull-right">Type du Catalogue</label>
              </div>
              <div class="col-md-2">
                <v-select :searchable="false" label="value" v-model="filter.typeCMS" :options="typeCatalogueOptions"></v-select>
              </div>

              <div class="col-md-3">
                <label class="control-label pull-right">Type d'événement</label>
              </div>
              <div class="col-md-5">
                <v-select :searchable="false" label="value" v-model="filter.typeEvenement" :options="typeEvenementOptions"></v-select>
              </div>

              <div class="col-md-2">
                <label class="control-label pull-right">IDE12</label>
              </div>
              <div class="col-md-3">
                <div class="form-group has-feedback">
                  <input  type="number"
                          id="filterIde12"
                          v-model="filter.ide12"
                          :class="'autocomplete-input form-control input-sm'"
                          @keypress="numberKey"
                  />
                </div>
              </div>
            </div>
            <div class="row" style="margin-top: 10px">

              <div class="col-sm-3">
                <label class="control-label pull-right">Période début d'événement</label>
              </div>
              <div class="col-sm-2">
                <date-picker v-validate.disable="'date_format:DD/MM/YYYY|before:periodeFinEvenement'"
                             data-vv-value-path="periodeDebutEvenementValue"
                             data-vv-name="periodeDebutEvenement"
                             :class="{'has-error': errors.has('periodeDebutEvenement') }"
                             v-model="filter.periodeDebutEvenement"
                             date-format="dd/mm/yy"
                             :zeroHour="true"
                             place-holder="De" >

                </date-picker>
              </div>

              <div class="col-sm-3">
                <label class="control-label pull-right">Période fin d'événement</label>
              </div>
              <div class="col-sm-2">
                <date-picker v-validate.disable="'date_format:DD/MM/YYYY'"
                             data-vv-value-path="periodeFinEvenementValue"
                             data-vv-name="periodeFinEvenement"
                             :class="{'has-error': errors.has('periodeFinEvenement') }"
                             v-model="filter.periodeFinEvenement"
                             date-format="dd/mm/yy"
                             :zeroHour="false"
                             place-holder="A" >

                </date-picker>
              </div>

            </div>

          </form>
        </div>
      </div>
    </div>
        <div class="row formula-buttons">
          <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="resetForm()">Rétablir</button>
          <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="rechercherEvenements()">Rechercher</button>
        </div>
        <priam-grid
          v-if="journalCatalogueGrid.gridData.content && !dataLoading"
          :isPaginable="true"
          :data="journalCatalogueGrid.gridData"
          :columns="journalCatalogueGrid.gridColumns"
          noResultText="Aucun résultat."
          @on-sort="onSort"
          @load-page="loadPage"
          class="col-sm-24">
        </priam-grid>

  </div>
    </div>
  </div>
</template>

<script>

  import PriamNavbar from  './../../../common/components/ui/priam-navbar.vue';
  import vSelect from '../../../common/components/ui/Select';
  import DatePicker from '../../../common/components/ui/DatePicker.vue';
  import PriamGrid from './../../../common/components/ui/Grid.vue';

  export default {
    name: "journal-catalogue",
    data() {
      var getters = this.$store.getters;
      return {
        resource : {},
        defaultPageable : {
          page : 1,
          sort : 'date',
          dir : 'ASC',
          size : this.$store.getters.userPageSize
        },
        isCollapsed : false,
        filter : {
          typeCMS : {id :'FR', value : 'CMS France'},
          typeEvenement: {id:'ALL', value : 'Tous'},
          ide12: null,
          periodeDebutEvenement:null,
          periodeFinEvenement:null
          },

        currentFilter : {
          typeCMS : {id :'FR', value : 'CMS France'},
          typeEvenement: {id:'ALL', value : 'Tous'},
          ide12: null,
          periodeDebutEvenement:null,
          periodeFinEvenement:null
        },

        dataLoading : false,

        journalCatalogueGrid : {
          gridData : {},
          gridColumns : [
            {
              id: 'evenement',
              name: "Evénement",
              sortable: false,
              type: 'code-value-left',
              cell : {

                css : function (entry) {
                  return {
                    style : {
                      width : '300px',
                    }
                  }
                },

                toText : function(cellValue) {
                  var result  = getters.libelleTypeEvenement.find(function (element) {
                    return element.id === cellValue;
                  });
                  return result !== undefined && result.value;
                }
              }
            },
            {
              id : 'ide12',
              name :'IDE12',
              sortable : true,
              type : 'text-centre',
              cell: {
                css : function (entry) {
                  return {
                    style : {
                      width : '300px'
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
              id: 'date',
              name: "Date de l'événement",
              sortable: true,
              type: 'date',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '300px'
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
              id: 'utilisateur',
              name: "Utilisateur",
              sortable: true,
              type: 'long-text',
              cell: {
                css : function (entry) {
                  return {
                    style : {
                      width : '300px'
                    }
                  }
                },
                toText: function (entry) {
                  var result = entry;
                  if (result != undefined)
                    return result;
                  else
                    return "";
                }
              }
            }
          ]
        }

      }
    },

    created() {

      const customActions = {
        /*searchJournalCatalogue : {
          method : 'GET',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/journalCatcms/search?page={page}&size={size}&sort={sort},{dir}'},*/

        searchJournalCatalogue : {
          method : 'POST',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/journalCatcms/search?page={page}&size={size}&sort={sort},{dir}'}

      }
      this.resource= this.$resource('', {}, customActions);
      this.rechercherEvenements();
    },


    computed : {
      typeCatalogueOptions() {
        return this.$store.getters.libelleTypeCatalogue;
      },
      typeEvenementOptions() {
        return this.$store.getters.libelleTypeEvenement;
      }
    },

    methods: {
      numberKey(event) {
        debugger;
        let charCode = (event.which) ? event.which : event.keyCode;
        if (charCode != undefined) {
          if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            event.preventDefault();
          }
        }
      },
      resetForm() {
        $("#filterIde12").val("");
        this.retablir();
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

      rechercherEvenements() {
        debugger;
        this.$validator.validateAll().then(() => {
          this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
            this.defaultPageable.sort, this.defaultPageable.dir);
        }).catch(() => {
          console.log('Correct them errors!');
        });
      },

      launchRequest(pageNum, pageSize, sort, dir) {

        this.dataLoading = true;
        this.currentFilter.typeCMS = this.filter.typeCMS.id;
        if(this.filter.typeEvenement.id === 'ALL'){
          this.currentFilter.typeEvenement = null;
        } else {
          this.currentFilter.typeEvenement = this.filter.typeEvenement.id;
        }
        this.currentFilter.ide12 = this.filter.ide12;
        this.currentFilter.periodeDebutEvenement = this.filter.periodeDebutEvenement;
        this.currentFilter.periodeFinEvenement = this.filter.periodeFinEvenement;


        this.resource.searchJournalCatalogue({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir}, this.currentFilter)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.journalCatalogueGrid.gridData = data;
            this.journalCatalogueGrid.gridData.number = data.number + 1;
            this.dataLoading = false;

          });
      },

      retablir(){
        this.filter = {
          typeCMS : {id :'FR', value : 'CMS France'},
          typeEvenement: {id:'ALL', value : 'Tous'},
          ide12: null,
            periodeDebutEvenement:null,
            periodeFinEvenement:null
        }
        this.rechercherEvenements();
      }

    },
    components : {
      'priam-navbar' : PriamNavbar,
      vSelect : vSelect,
      datePicker : DatePicker,
      'priam-grid' : PriamGrid
    }
  }

</script>

<style scoped>
  .row{
    margin-top: 5px;
    margin-bottom: 5px;
  }

  .form-horizontal{
    width: 100%;
  }

</style>
