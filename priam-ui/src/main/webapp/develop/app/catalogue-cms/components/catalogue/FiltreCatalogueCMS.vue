<template>
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

                  <li v-if="errors.has('periodeEntree')">
                    <i v-show="errors.has('periodeEntree')" class="fa fa-warning"></i>
                    <label v-show="errors.has('periodeEntree')" :class="{'has-error': errors.has('periodeEntree') }">{{ errors.first('periodeEntree') }}</label>
                  </li>

                  <li v-if="errors.has('periodeRenouvellement')">
                    <i v-show="errors.has('periodeRenouvellement')" class="fa fa-warning"></i>
                    <label v-show="errors.has('periodeRenouvellement')" :class="{'has-error': errors.has('periodeRenouvellement') }">{{ errors.first('periodeRenouvellement') }}</label>
                  </li>

                  <li v-if="errors.has('periodeSortie')">
                    <i v-show="errors.has('periodeSortie')" class="fa fa-warning"></i>
                    <label v-show="errors.has('periodeSortie')" :class="{'has-error': errors.has('periodeSortie') }">{{ errors.first('periodeSortie') }}</label>
                  </li>

                </ul>
            </div>
          <div class="col-md-10">



            <div class="row">
                  <div class="col-md-5">
                    <label class="control-label pull-right">Type du Catalogue</label>
                  </div>
                  <div class="col-md-6">
                    <v-select :searchable="false" label="value" v-model="filter.typeCMS" :options="typeCatalogueOptions"></v-select>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-5">
                    <label class="control-label pull-right">IDE12</label>
                  </div>
                  <div class="col-md-8">
                    <div class="form-group has-feedback">
                      <input  type="number"
                              id="filterIde12"
                              v-model="filter.ide12"
                              :class="'autocomplete-input form-control  input-sm'"
                              @keypress="numberKey"
                      />
                    </div>
                  </div>
                  <div class="col-md-2">
                    <label class="control-label pull-right">Titre</label>
                  </div>
                  <div class="col-md-8">
                    <div class="form-group has-feedback">
                      <input  type="text"
                              id="filterTitre"
                              :class="'autocomplete-input form-control  input-sm'"
                              v-model="filter.titre"
                      />
                      <i class="form-control-feedback glyphicon glyphicon-search"></i>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-5">
                    <label class="control-label pull-right">Participant</label>
                  </div>
                  <div class="col-md-18">
                    <div class="form-group has-feedback">
                      <input  type="text"
                              :class="'autocomplete-input form-control  input-sm'"
                              v-model="filter.participant"
                      />
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col">
                    <div class="col-md-8" style="margin-left: 111px">
                      <label class="checkbox" :class="{'checked' : isPeriodeSortieSaisie || filter.displayOeuvreNonEligible}">
                        <input type="checkbox" v-model="filter.displayOeuvreNonEligible">Afficher oeuvres non éligibles
                        <span class="icons"><span class="first-icon fui-checkbox-unchecked"></span><span class="second-icon fui-checkbox-checked"></span></span>
                      </label>
                    </div>
                  </div>
                </div>


          </div>
              <div class="text-center col-md-4">
                <periode-filter
                  v-validate="'periodeRule:DD/MM/YYYY'"
                  data-vv-value-path="periodeEntreeValue"
                  data-vv-name="periodeEntree"
                  v-model="filter.periodeEntreeFilter"
                  :titre="titrePeriodeEntree"
                  ></periode-filter>
              </div>
          <div class="text-center col-md-4 col-md-offset-1">
            <periode-filter
              v-validate="'periodeRule:DD/MM/YYYY'"
              data-vv-value-path="periodeRenouvellementValue"
              data-vv-name="periodeRenouvellement"
              v-model="filter.periodeRenouvellementFilter"
              :titre="titrePeriodeRenouv"
              ></periode-filter>
          </div>
          <div class="text-center col-md-4 col-md-offset-1">
            <periode-filter
              v-validate="'periodeRule:DD/MM/YYYY'"
              data-vv-value-path="periodeSortieValue"
              data-vv-name="periodeSortie"
              v-model="filter.periodeSortieFilter"
              :titre="titrePeriodeSortie"
              ></periode-filter>
          </div>
          </form>
        </div>

      </div>
    </div>


    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="resetForm()">Rétablir</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="findCatalogue()">Rechercher</button>
    </div>

  </div>
</template>

<script>
  import vSelect from '../../../common/components/ui/Select';
  import Autocomplete from '../../../common/components/ui/vue-autocomplete.vue';
  import periodeFilter from './PeriodeFilter';

    export default {
      name: "filtre-catalogue",
      data() {
        return {
          titrePeriodeEntree:'Période d\'entrée',
          titrePeriodeRenouv:'Période de renouvellement',
          titrePeriodeSortie:'Période de sortie',
          isCollapsed : false,

          dateDebutSortie : this.filter.periodeSortieFilter.dateDebut
        }
      },
      props : {
        filter : Object,
        retablir : Function,
        rechercher : Function
      },
      computed : {
        typeCatalogueOptions() {
          return [
            {id : 'FRA', value : 'CMS France'},
            {id : 'ANF', value : 'CMS Antilles'}
          ];
        },

        isPeriodeSortieSaisie () {
          if(this.filter.periodeSortieFilter.dateDebut!=null || this.filter.periodeSortieFilter.dateFin!=null ) {
            return true;
          }
          return false;
        },
      },

      watch:{
        isPeriodeSortieSaisie: function (value) {
          this.filter.displayOeuvreNonEligible=value;
        },

        filter : function (value) {
          debugger;
          console.log(value)
        }


      },

      mounted() {
        this.dateDebutSortie = this.filter.periodeSortieFilter.dateDebut;
      },

      components : {
        vSelect : vSelect,
        Autocomplete,
        periodeFilter
      },
      methods: {
        resetForm() {
          $("#filterIde12").val("");
          $("#filterTitre").val("");
          this.retablir();
        },

        findCatalogue() {
          this.$validator.validateAll().then(() => {
            this.rechercher();
          }).catch(() => {
            console.log('Correct them errors!');
          });
        },

        numberKey(event) {
          debugger;
          let charCode = (event.which) ? event.which : event.keyCode;
          if (charCode != undefined) {
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
              event.preventDefault();
            }
          }
        },
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

  .col-md-10 {
    width: 38%;
  }
</style>
