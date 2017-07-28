<template>
  <div class="container-fluid sacem-formula">

    <div class="panel panel-default">

      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Critères de recherche</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
          <span class="pull-right blueText"></span>
        </h5>
      </div>

      <div class="panel-collapse">
        <div class="panel-body">

          <form class="form-horizontal" role="form">
            <div class="row">


              <div class="col-sm-2" v-if="showUtilisateur">
                  <label class="control-label pull-right blueText">Utilisateur</label>
              </div>
              <div class="col-sm-3" v-if="showUtilisateur">
                  <v-select :searchable="false" label="value" v-model="filter.utilisateur" :options="utilisateurOptions"> </v-select>
              </div>

              <div class="col-sm-2">
                  <label class="control-label pull-right blueText">IDE12</label>
              </div>
              <div class="col-sm-3">
                <autocomplete
                  id="filterIde12"
                  url="ligneProgramme/ide12"
                  :custom-params="{ programme: $route.params.numProg }"
                  anchor="code"
                  :min="3"
                  :on-select="selectIde12"
                  placeholder="TOUS"
                >
                </autocomplete>
              </div>

              <div class="col-sm-2">
                  <label class="control-label pull-right blueText">Titre</label>
              </div>
              <div class="col-sm-3">
                  <v-select :searchable="false" label="value" v-model="filter.titre" :options="titreOptions"> </v-select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right blueText">Ajout</label>
              </div>
              <div class="col-sm-3">
                <v-select :searchable="false" label="value" v-model="filter.ajout" :options="ajoutOptions"></v-select>
              </div>
            </div>

            <div id="row">
              <div class="col-sm-2">
                <label class="control-label pull-right blueText">Sélection</label>
              </div>
              <div class="col-sm-3">
                <v-select :searchable="false" label="value" v-model="filter.selection" :options="selectionOptions"></v-select>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>


    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="resetForm()">Rétablir</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="rechercher()">Rechercher</button>
      <button class="btn btn-default btn-primary pull-left disabled"  type="button" @click.prevent="ajouterOeuvre()" style="width: 120px;">Ajouter Oeuvre</button>

    </div>

  </div>
</template>

<script>

  import vSelect from '../common/Select.vue';
  import Autocomplete from '../common/vue-autocomplete.vue';

  export default {

    computed : {
      utilisateurOptions() {
        return [{'id' :'ALL', "value" : "Tous"}];
      },
      titreOptions() {
        return [{'id' :'ALL', "value" : "Tous"}];
      },
      ajoutOptions() {
        return [
            {
                'id' :'ALL', "value" : "Tous"
            }
        ];
      },
      selectionOptions() {
        return [{'id' :'ALL', "value" : "Tous"}];
      }
    },

    components : {
        vSelect,
        Autocomplete
    },

    methods : {
      selectIde12(data) {
        this.filter.ide12 = data.code;
      },

      resetForm() {
        $("#filterIde12").val("");
        this.retablir();
      }
    },

    props : {
      filter : Object,
      retablir : Function,
      rechercher : Function,
      showUtilisateur : Boolean
    }
  }
</script>
