<template>
  <div >
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
              <li v-if="errors.has('titre')">
                <i v-show="errors.has('titre')" class="fa fa-warning"></i>
                <label v-show="errors.has('titre')" class="control-label" :class="{'has-error': errors.has('titre') }">{{ errors.first('titre') }}</label>
              </li>
              <li v-if="errors.has('ide12')">
                <i v-show="errors.has('ide12')" class="fa fa-warning"></i>
                <label v-show="errors.has('ide12')" class="control-label" :class="{'has-error': errors.has('ide12') }">{{ errors.first('ide12') }}</label>
              </li>
              <template v-if="programme.typeUtilisation == 'CPRIVSONRD'">
                <li v-if="errors.has('duree')">
                  <i v-show="errors.has('duree')" class="fa fa-warning"></i>
                  <label v-show="errors.has('duree')" class="control-label" :class="{'has-error': errors.has('duree') }">{{ errors.first('duree') }}</label>
                </li>
              </template>
              <template  v-if="programme.typeUtilisation == 'CPRIVSONPH'">
                <li v-if="errors.has('quantite')">
                  <i v-show="errors.has('quantite')" class="fa fa-warning"></i>
                  <label v-show="errors.has('quantite')" :class="{'has-error': errors.has('quantite') }">{{ errors.first('quantite') }}</label>
                </li>
              </template>

              <template  v-if="programme.typeUtilisation == 'SONOFRA'">
                <li v-if="errors.has('points')">
                  <i v-show="errors.has('points')" class="fa fa-warning"></i>
                  <label v-show="errors.has('points')" :class="{'has-error': errors.has('points') }">{{ errors.first('points') }}</label>
                </li>
              </template>

              <li v-if="errors.has('Utilisateur')">
                <i v-show="errors.has('Utilisateur')" class="fa fa-warning"></i>
                <label v-show="errors.has('Utilisateur')" :class="{'has-error': errors.has('Utilisateur') }">{{ errors.first('Utilisateur') }}</label>
              </li>
            </ul>

          </div>
          <div class="row">

          <div class="form-group col-md-4" :class="{'has-error': errors.has('titre') }">
            <label class="col-md-5 control-label blueText text-right">Titre <span class="mandatory">*</span></label>
            <div class="col-md-19 control-label">
              <input v-validate.disable="'required'"
                     name="titre"
                     class="form-control"
                     type="text"
                     disabled="disabled"
                     v-model="oeuvre.titre"
                     :class="{'has-error': errors.has('titre') }" >
            </div>
          </div>

          <div class="form-group col-md-4" :class="{'has-error': errors.has('ide12') }">
            <label class="col-md-9 control-label blueText text-right">IDE12 <span class="mandatory">*</span></label>
            <div class="col-md-15 control-label">
              <input v-validate.disable="'required'"
                     name="ide12"
                     class="form-control"
                     type="text"
                     disabled="disabled"
                     v-model="oeuvre.ide12"
                     :class="{'has-error': errors.has('ide12') }" >
            </div>
          </div>


          <!--<div class="form-group col-md-7" :class="{'has-error': errors.has('Utilisateur') }">
            <label class="col-md-6 control-label blueText text-right">Utilisateur <span class="mandatory">*</span></label>
            <div class="col-md-18">
              <select2 v-if="oeuvreManuelToCreate.utilisateur"
                       class="form-control"
                       data-vv-name="Utilisateur"
                       data-vv-value-path="innerUtilisateur"
                       v-validate.disable="'required'"
                       name="Utilisateur"
                       :options="utilisateurOptions"
                       v-model="oeuvreManuelToCreate.utilisateur"
                       :searchable="true"
                       :class="{'has-error': errors.has('Utilisateur') }">
              </select2>
            </div>
          </div>-->


            <div class="form-group col-md-5" v-if="programme.typeUtilisation == 'CPRIVSONRD'" :class="{'has-error': errors.has('duree') }">
              <label class="col-md-6 control-label blueText text-right">Durée <span class="mandatory">*</span></label>
              <div class="col-md-18">
                <input v-validate.disable="'required|numeric'"
                       name="duree"
                       class="form-control"
                       type="text"
                       v-model="oeuvreManuelToCreate.duree"
                       :class="{'has-error': errors.has('duree') }" >
              </div>
            </div>
            <div class="form-group col-md-5" :class="{'has-error': errors.has('quantite') }" v-if="programme.typeUtilisation == 'CPRIVSONPH'">
              <label class="col-md-6 control-label blueText text-right">Quantité <span class="mandatory">*</span></label>
              <div class="col-md-18">
                <input v-validate.disable="'required|numeric'"
                       :class="{'has-error': errors.has('quantite') }"
                       name="quantite"
                       class="form-control"
                       type="text"
                       v-model="oeuvreManuelToCreate.quantite">
              </div>
            </div>
            <div class="form-group col-md-5" :class="{'has-error': errors.has('points') }" v-if="programme.typeUtilisation == 'SONOFRA'">
              <label class="col-md-6 control-label blueText text-right">Points <span class="mandatory">*</span></label>
              <!--<div class="col-md-18">
                <input v-validate.disable="'required|regex:^[0-9]+'"
                       :class="{'has-error': errors.has('points') }"
                       name="points"
                       class="form-control"
                       type="number"
                       v-model="oeuvreManuelToCreate.points"
                       v-on:keypress="isNumber(event, oeuvreManuelToCreate.points)">
              </div>-->
              <decimal-input label="" v-model="oeuvreManuelToCreate.points">Points</decimal-input>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>

    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="onClickAjouterOeuvre()">Ajouter</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click.prevent="$emit('cancel-ajout')">Annuler</button>
    </div>

    </div>
</template>

<script>

  import Select2 from '../../../../common/components/ui/Select2.vue';
  import {Validator} from 'vee-validate';
  import DecimalInput from '../../../../common/components/ui/Decimal-input';


  const dictionary = {

    fr: {
      attributes: {
        'titre' : 'Titre',
        'ide12' : 'IDE12',
        'duree' : 'Durée',
        'quantite' : 'Quantité'
      }
    }
  };

  Validator.updateDictionary(dictionary);

  export default {
      props: {
        oeuvre : Object
      },

     data() {
          return {
              oeuvreManuelToCreate : {
                  titre : '',
                  ide12 : '',
                  roleParticipant1 : '',
                  nomParticipant1: '',
                  cdeTypeIde12 :'',
                  points: 0
              },
              utilisateursOptions : [],
              programme : {},
              event: null
          }
     },

    computed:  {
      utilisateurOptions() {
        let libelleUtilisateur = this.$store.getters.libelleUtilisateur;
        let result = libelleUtilisateur.map(elem => {

          return {
            id : elem.id,
            value : elem.value
          }
        });
        return result;
      }
    },

    mounted() {
        //this.oeuvre = this.$store.getters.selectedOeuvre;
        this.programme = this.$store.getters.programmeEnSelection;
        /*const customActions = {
          getUtilisateursByProgramme : {method : 'GET', url :'app/rest/ligneProgramme/utilisateurs?programme='+this.programme.numProg}
        }
        this.resource = this.$resource('', {}, customActions);

        this.resource.getUtilisateursByProgramme()
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.utilisateursOptions = data;

              console.log('data[0] = ' + this.oeuvreManuelToCreate.utilisateur);
              //debugger;
            });*/

        //this.oeuvreManuelToCreate.utilisateur = this.$store.getters.libelleUtilisateur[0].id;
    },

    methods : {

      onClickAjouterOeuvre() {
        let sef = this;
        sef.$validator.validateAll().then(() => {
            sef.oeuvreManuelToCreate.ide12 = sef.oeuvre.ide12;
            sef.oeuvreManuelToCreate.titre = sef.oeuvre.titre;
            sef.oeuvreManuelToCreate.roleParticipant1 = sef.oeuvre.roleParticipant1;
            sef.oeuvreManuelToCreate.nomParticipant1 = sef.oeuvre.nomParticipant1;
            sef.oeuvreManuelToCreate.cdeTypeIde12 = sef.oeuvre.cdeTypeIde12;
            sef.$emit('ajout-oeuvre', sef.oeuvreManuelToCreate);
        }).catch(() => {
          console.log('Correct them errors!');
        });

      },

      validateBeforeSubmit() {


        var validator = this.$validator;
        validator.validateAll().then(() => {
          console.log('All things are OK !!');
        }).catch(() => {
          console.log('Correct them errors!');
        });
      },

      isNumber(event, points) {
        var regexp = /^[0-9]+(.[0-9]{0,1})?$/;
        event = (event) ? event : window.event;
        // var charCode = (event.which) ? event.which : event.keyCode;
        var charCode = (event.which) ? event.which : event.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          event.preventDefault();
        } else {
          if (points) {
            if (regexp.test(points)) {
              return true;
            } else {
              event.preventDefault();
              ;
            }
          }
        }
      }

      },

    components : {
        select2: Select2,
        decimalInput: DecimalInput
    }

  }
</script>

<style>

  .mandatory {
    color: #FF0000;
  }
</style>
