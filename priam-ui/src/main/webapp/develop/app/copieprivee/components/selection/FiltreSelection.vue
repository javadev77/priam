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

              <div class="form-group col-md-5">
                <label class="col-md-6 control-label blueText text-right">Utilisateur</label>
                <div class="col-md-18">
                  <select2 class="form-control" :options="utilisateurOptions" v-model="filter.utilisateur" :searchable="true">
                  </select2>
                </div>
              </div>

              <div class="form-group col-md-4">
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
                  >
                  </autocomplete>
                </div>
              </div>

              <div class="form-group col-md-6">
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

              <div class="form-group col-md-4">
                <label class="col-md-8 control-label blueText text-right">Ajout</label>
                <div class="col-md-16">
                  <v-select :searchable="false" v-model="filter.ajout" :options="ajoutOptions"></v-select>
                </div>
              </div>

              <div class="form-group col-md-4">
                <label class="col-md-10 control-label blueText text-right">Sélection</label>
                <div class="col-md-14">
                  <v-select :searchable="false" v-model="filter.selection" :options="selectionOptions"></v-select>
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
      <button class="btn btn-default btn-primary pull-left"  type="button" @click.prevent="ajouterOeuvre()" style="width: 120px;" :disabled="!edition">Ajouter Oeuvre</button>

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
  import AjouterOeuvre from './oeuvre/AjouterOeuvre.vue';

  export default {

    data() {


      return {
        showMipsa : false,
        utilisateursOptions : [],
        urlAutoCompleteIDE12 : process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/ligneProgramme/ide12',
        urlAutoCompleteTitreOeuvre : process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/ligneProgramme/titreOeuvre'


      }
    },
    computed : {
      utilisateurOptions() {
        let result = this.utilisateursOptions.map(elem => {
          return {
            id : elem,
            value : elem
          }
        });

        result.unshift({id : 'Tous', value : 'Tous'});
        return result;
      },
      ajoutOptions() {
        return [
            'Tous',
            'Manuel',
            'Automatique'
        ];
      },
      selectionOptions() {
        return [
          'Tous',
          'Sélectionné',
          'Désélectionné',
        ];
      }
    },

    components : {
        vSelect,
        Autocomplete,
        select2 :Select2,
        ecranModal : EcranModal,
        ajouterOeuvre : AjouterOeuvre
    },

    methods : {
      selectIde12(data) {
        this.filter.ide12 = (data.code) ? data.code : data;
      },

      selectTitreOeuvre(data) {
        this.filter.titre = (data.value) ? data.value : data;
      },

      resetForm() {
        $("#filterIde12").val("");
        $("#filterTitreOeuvre").val("");
        this.retablir();
      },

      getUtilisateursByProgramme() {

        this.resource.getUtilisateursByProgramme()
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.utilisateursOptions = data;
          });
      },

      ajouterOeuvre() {
        this.showMipsa = true;
      },

      onValidateAjoutOeuvre(oeuvreToAdd) {
          var programme = this.$store.getters.programmeEnSelection;
          this.showMipsa = false;
          var lingeProgramme = {
              numProg : this.$route.params.numProg,
              ide12 : oeuvreToAdd.ide12,
              cdeTypIde12 : oeuvreToAdd.cdeTypeIde12,
              titreOeuvre : oeuvreToAdd.titre,
              durDif : oeuvreToAdd.duree,
              nbrDif : oeuvreToAdd.quantite,
              cdeUtil : oeuvreToAdd.utilisateur,
              roleParticipant1 : oeuvreToAdd.roleParticipant1,
              nomParticipant1 : oeuvreToAdd.nomParticipant1,
              libelleUtilisateur : oeuvreToAdd.libelleUtilisateur
          }
          this.resource.ajouterOeuvreManuel(lingeProgramme)
            .then(response => {
              return response.json();
            })
            .then(data => {
              //
              console.log('ok ajout');
              this.rechercher();
            })
            .catch(error => {

                alert("Erreur technique lors l'ajout d'oeuvre !!!");

              }
            );

      }
    },

    props : {
      filter : Object,
      retablir : Function,
      rechercher : Function,
      edition : Boolean
    },

    created() {
      const customActions = {
        getUtilisateursByProgramme : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/ligneProgramme/utilisateurs?programme='+this.$route.params.numProg},
        ajouterOeuvreManuel : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_CP + 'app/rest/ligneProgramme/selection/ajoutOeuvre'}
      }
      this.resource= this.$resource('', {}, customActions);

      this.getUtilisateursByProgramme();
    }
  }
</script>
