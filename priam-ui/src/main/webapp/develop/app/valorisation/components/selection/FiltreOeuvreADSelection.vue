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
              <div class="form-group col-md-2"/>
              <div class="form-group col-md-2">
                <input type="radio" value="globale" @input="majVue" v-model="filtreVue">
                <label class="blueText" style="margin-bottom: 0px !important;">Vue globale</label>
              </div>
              <input type="radio" value="coad" @input="majVue" v-model="filtreVue">
              <label class="blueText" style="margin-bottom: 0px !important;">Vue COAD</label>
            </div>


            <div class="row">
              <div class="form-group col-md-1"/>
              <div v-if="filtreVue=='globale'" class="form-group col-md-10">
                <div class="form-group col-md-12">
                  <label class="col-md-6 control-label blueText text-right">IDE12</label>
                  <div class="col-md-18 control-label">
                    <autocomplete
                      id="filterIde12"
                      :url="urlAutoCompleteIDE12"
                      :custom-params="{ programme: $route.params.numProg }"
                      anchor="code"
                      :min="3"
                      :on-select="selectIde12"
                      :on-input="selectIde12"
                      :is-alpha-numeric="true"
                    >
                    </autocomplete>
                  </div>
                </div>

                <div class="form-group col-md-12">
                  <label class="col-md-6 control-label blueText text-right">Titre</label>
                  <div class="col-md-18 control-label">
                    <autocomplete
                      id="filterTitreOeuvre"
                      :url="urlAutoCompleteTitreOeuvre"
                      :custom-params="{ programme: $route.params.numProg }"
                      anchor="value"
                      :min="3"
                      :on-select="selectTitreOeuvre"
                      :on-input="selectTitreOeuvre"
                    >
                    </autocomplete>
                  </div>
                </div>
              </div>
              <div class="form-group col-md-1"/>
                <div class="form-group col-md-10">
                  <div class="form-group col-md-10">
                    <label class="col-md-6 control-label blueText text-right">COAD</label>
                    <div class="col-md-18 control-label">
                      <autocomplete
                        id="filterCoad"
                        :url="urlAutoCompleteCOAD"
                        :custom-params="{ programme: $route.params.numProg }"
                        anchor="code"
                        :min="3"
                        :on-select="selectCoad"
                        :on-input="selectCoad"
                        :is-alpha-numeric="true"
                      >
                      </autocomplete>
                    </div>
                </div>
<!--
              <div class="form-group col-md-1"/>
-->
                <div class="form-group col-md-14">
                  <label class="col-md-6 control-label blueText text-right">Participant</label>
                  <div class="col-md-18 control-label">
                    <autocomplete
                      id="filterParticipant"
                      :url="urlAutoCompleteParticipant"
                      :custom-params="{ programme: $route.params.numProg }"
                      anchor="value"
                      :min="3"
                      :on-select="selectParticipant"
                      :on-input="selectParticipant"
                    >
                    </autocomplete>
                  </div>
                </div>
              </div>

            </div>

          </form>
        </div>
      </div>
    </div>


    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="resetForm()">Rétablir</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="rechercher()">Rechercher</button>

    </div>

    <ecran-modal v-if="showMipsa">
      <ajouter-oeuvre   slot="body" @cancel="showMipsa = false" @validate-ajout-oeuvre="onValidateAjoutOeuvre"></ajouter-oeuvre>
    </ecran-modal>


  </div>
</template>

<script>
  import vSelect from '../../../common/components/ui/Select.vue';
  import Autocomplete from '../../../common/components/ui/vue-autocomplete.vue';
  import Select2 from '../../../common/components/ui/Select2.vue';
  import EcranModal from '../../../common/components/ui/EcranModal.vue';
    export default {
        name: "FiltreAyantDroitSelection",
      data() {
        return {
          showMipsa : false,
          utilisateursOptions : [],
          urlAutoCompleteIDE12 : process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ayantDroit/ide12',
          urlAutoCompleteTitreOeuvre : process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ayantDroit/titre',
          urlAutoCompleteCOAD : process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ayantDroit/coad',
          urlAutoCompleteParticipant : process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/ayantDroit/participant',
          filtreVue: 'globale'
        }
      },

      components : {
        vSelect,
        Autocomplete,
        select2 :Select2,
        ecranModal : EcranModal
      },

      methods : {

        selectIde12(data) {
          this.filter.ide12 = (data.code) ? data.code : data;
        },

        selectTitreOeuvre(data) {
          this.filter.titre = (data.value) ? data.value : data;
        },

        selectCoad(data) {
          this.filter.coad = (data.code) ? data.code : data;
        },

        selectParticipant(data) {
          this.filter.participant = (data.value) ? data.value : data;
        },

        resetForm() {
          $("#filterIde12").val("");
          $("#filterTitreOeuvre").val("");
          $("#filterCoad").val("");
          $("#filterParticipant").val("");
          this.retablir();
        },

        majVue() {
          console.log("filtreVue : " + this.filtreVue)
          this.$emit('maj-filtre-vue', this.filtreVue)
        }
      },

      props : {
        filter : Object,
        retablir : Function,
        rechercher : Function,
        edition : Boolean
      }

    }
</script>

<style scoped>

</style>
