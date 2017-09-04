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

          <div class="row" v-if="errors.count()!=0">
            <ul style="list-style: none">
              <li v-if="errors.has('duree')">
                <i v-show="errors.has('duree')" class="fa fa-warning"></i>
                <label v-show="errors.has('duree')" class="control-label" :class="{'has-error': errors.has('duree') }">{{ errors.first('duree') }}</label>
              </li>
              <li v-if="errors.has('quantite')">
                <i v-show="errors.has('quantite')" class="fa fa-warning"></i>
                <label v-show="errors.has('quantite')" :class="{'has-error': errors.has('quantite') }">{{ errors.first('quantite') }}</label>
              </li>
              <li v-if="errors.has('Utilisateur')">
                <i v-show="errors.has('Utilisateur')" class="fa fa-warning"></i>
                <label v-show="errors.has('Utilisateur')" :class="{'has-error': errors.has('Utilisateur') }">{{ errors.first('Utilisateur') }}</label>
              </li>

            </ul>

          </div>
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


          <div class="form-group col-md-7" :class="{'has-error': errors.has('Utilisateur') }">
            <label class="col-md-6 control-label blueText text-right">Utilisateur</label>
            <div class="col-md-18">
              <select2 class="form-control"
                       data-vv-name="Utilisateur"
                       data-vv-value-path="innerUtilisateur"
                       v-validate="'required'"
                       :options="utilisateurOptions"
                       v-model="oeuvreManuelToCreate.utilisateur"
                       :searchable="true"
                       :class="{'has-error': errors.has('Utilisateur') }">
              </select2>
            </div>
          </div>


            <div class="form-group col-md-5" v-if="programme.typeUtilisation == 'CPRIVSONRD'" :class="{'has-error': errors.has('duree') }">
              <label class="col-md-6 control-label blueText text-right">Durée</label>
              <div class="col-md-18">
                <input v-validate="'required|numeric'"
                       name="duree"
                       class="form-control"
                       type="text"
                       v-model="oeuvreManuelToCreate.duree"
                       :class="{'has-error': errors.has('duree') }" >
              </div>
            </div>
            <div class="form-group col-md-5" :class="{'has-error': errors.has('quantite') }" v-if="programme.typeUtilisation == 'CPRIVSONPH'">
              <label class="col-md-6 control-label blueText text-right">Quantité</label>
              <div class="col-md-18">
                <input v-validate="'required|numeric'" :class="{'has-error': errors.has('quantite') }" name="quantite"  class="form-control" type="text" v-model="oeuvreManuelToCreate.quantite">
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
              this.oeuvreManuelToCreate.utilisateur = data !== null && data.length > 0 ? data[0] : '';
              console.log('data[0] = ' + data[0]);
            });
    },

    methods : {

      onClickAjouterOeuvre() {

        this.$validator.validateAll().then(() => {
          this.oeuvreManuelToCreate.ide12 = this.oeuvre.ide12;
          this.oeuvreManuelToCreate.titre = this.oeuvre.titre;
          this.$emit('ajout-oeuvre', this.oeuvreManuelToCreate);
        });
      }

    },

    components : {
        select2 :Select2
    }

  }
</script>
