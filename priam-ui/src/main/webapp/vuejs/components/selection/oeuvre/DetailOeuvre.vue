<template>
  <div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title">
        <a>Détail Oeuvre</a>
        <span class="pull-right fa fui-triangle-down"></span>
      </h5>
    </div>
    <div class="panel-collapse">

      <div class="panel-body">
        <form class="form-horizontal" role="form">
          <div class="row">

          <div class="form-group col-md-4">
            <label class="col-md-5 control-label blueText text-right">Titre</label>
            <div class="col-md-19 control-label">
              {{ oeuvre.titre }}
            </div>
          </div>

          <div class="form-group col-md-4">
            <label class="col-md-9 control-label blueText text-right">IDE12</label>
            <div class="col-md-15 control-label">
              {{ oeuvre.ide12 }}
            </div>
          </div>


          <div class="form-group col-md-7">
            <label class="col-md-6 control-label blueText text-right">Utilisateur</label>
            <div class="col-md-18">
              <select2 class="form-control" :options="utilisateurOptions" v-model="oeuvreManuelToCreate.utilisateur" :searchable="true">
              </select2>
            </div>
          </div>


            <div class="form-group col-md-5" v-if="programme.typeUtilisation == 'CPRIVSONRD'">
              <label class="col-md-6 control-label blueText text-right">Durée</label>
              <div class="col-md-18">
                <input class="form-control" type="text" v-model="oeuvreManuelToCreate.duree">
              </div>
            </div>
            <div class="form-group col-md-5" v-if="programme.typeUtilisation == 'CPRIVSONPH'">
              <label class="col-md-6 control-label blueText text-right">Quantité</label>
              <div class="col-md-18">
                <input class="form-control" type="text" v-model="oeuvreManuelToCreate.quantite">
              </div>
            </div>

          </div>
        </form>
      </div>
    </div>
  </div>

    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="onClickAjouterOeuvre()">Ajouter</button>
    </div>

    </div>
</template>

<script>

  import Select2 from '../../common/Select2.vue';

  export default {
      props: {
        oeuvre : Object
      },

     data() {

          return {
              oeuvreManuelToCreate : {
                  utilisateur : '',
                  duree : '',
                  quantite : '',
                  titre : '',
                  ide12 : ''
              },

              utilisateursOptions : [],

              programme : {}

          }
     },

    computed:  {
      utilisateurOptions() {
        let result = this.utilisateursOptions.map(elem => {
          return {
            id : elem,
            value : elem
          }
        });

        return result;
      }
    },

    mounted() {
        //this.oeuvre = this.$store.getters.selectedOeuvre;
        this.programme = this.$store.getters.programmeEnSelection;

        const customActions = {
          getUtilisateursByProgramme : {method : 'GET', url :'app/rest/ligneProgramme/utilisateurs?programme='+this.programme.numProg}
        }
        this.resource = this.$resource('', {}, customActions);

        this.resource.getUtilisateursByProgramme()
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.utilisateursOptions = data;
            });
    },

    methods : {

      onClickAjouterOeuvre() {
          this.oeuvreManuelToCreate.ide12 = this.oeuvre.ide12;
          this.oeuvreManuelToCreate.titre = this.oeuvre.titre;
          this.$emit('ajout-oeuvre', this.oeuvreManuelToCreate);
      }

    },

    components : {
        select2 :Select2
    }

  }
</script>
