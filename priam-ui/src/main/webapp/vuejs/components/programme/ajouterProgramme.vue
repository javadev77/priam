<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title" >
          <!--<strong>Critères de Recherche</strong>-->
          <a>Création programme</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
        </h5>
      </div>
      <div class="panel-collapse">
        <div class="container-fluid">
          <form  role="form" class="espacement">
            <div class="row">
              <div class="col-sm-2">
                <label class="control-label pull right" for="nomProgramme">Nom programme</label>
              </div>
              <div class="col-sm-3">
              <input
                  type="text"
                  id="nomProgramme"
                  class="form-control"
                  v-model="programmeData.nomProgramme">
              </div>
              <div class="col-sm-2">
                <label class="control-label pull right" for="rionTheorique">Rion théorique</label>
              </div>
                <div class="col-sm-1">
                <select
                  id="rionTheorique"
                  v-model="programmeData.rionTheorique">

                  <option>Rion1</option>
                </select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Famille</label>
              </div>
              <div class="col-sm-2">
                <v-select :searchable="false" label="value" v-model="programmeData.famille" :options="familleOptions" :on-change="loadTypeUtilisation">
                </v-select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-3">
            <v-select :searchable="false" label="value" v-model="programmeData.typeUtilisation" :options="typeUtilisationOptions">
            </v-select>
              </div>
            </div>
            <div class="row">
              <div class="col-md-2">
                <label class="control-label pull right">Type répartition</label>
              </div>
                <div class="col-md-3">
                <label class="control-label pull right" for="TypeRepartitionOeuvre">
                  <input
                    type="radio"
                    id="TypeRepartitionOeuvre"
                    value="Oeuvre"
                    v-model="programmeData.typeRepartition"
                    disabled> Oeuvre
                </label>
                </div>
                  <div class="col-md-3">
                  <label class="control-label pull right" for="TypeRepartitionOeuvreAyantDroit">
                    <input
                    type="radio"
                    id="TypeRepartitionOeuvreAyantDroit"
                    value="Ayant droit"
                    v-model="programmeData.typeRepartition"
                    disabled> Ayant droit
                </label>
                  </div>
            </div>
          </form>
        </div>


        <div class="row formula-buttons">
          <button class="btn btn-default btn-primary pull-right" type="button" @click="$emit('cancel')">Annuler</button>
          <button class="btn btn-default btn-primary pull-right" type="button" @click="$emit('valider')">Valider</button>
        </div>

      <hr>
      <div class="row">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4>Your Data</h4>
            </div>
            <div class="panel-body">
              <p>Nom programme:{{programmeData.nomProgramme}}</p>
              <p>famille:{{programmeData.famille}}</p>
              <p>Type d'utilisation:{{programmeData.typeUtilisation}}</p>
              <p>Rion théorique:{{programmeData.rionTheorique}} </p>
              <p>type répartition{{programmeData.typeRepartition}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>

</template>
<script>
  import vSelect from '../common/Select.vue';

  export default {
    data(){
      return {
        programmeData : {
          nomProgramme: '',
          rionTheorique:'',
          famille: {'id' : 'ALL', 'value' : 'Toutes'},
          typeUtilisation: {'id' : 'ALL', 'value' : 'Tous'},
          typeRepartition:'Oeuvre'
        }

      }
    },
    computed :{
      familleOptions() {
        return this.$store.getters.familleOptions;
      },

      typeUtilisationOptions() {
        return this.$store.getters.typeUtilisationOptions;
      },
      familleTypeUtilMap() {
        return this.$store.getters.familleTypeUtilMap;
      }
    },
    methods : {
      loadTypeUtilisation(val) {
        this.programmeData.famille = val;
        this.programmeData.typeUtilisation = {'id' : 'ALL', 'value': 'Tous'};
        this.$store.dispatch('loadTypeUtilisation', val);

      }
    },
    components : {
      vSelect : vSelect
    }
  }
</script>
<style>
.espacement{
  height:100px;
  margin-top: 20px;
  margin-left:40px ;
}
</style>
