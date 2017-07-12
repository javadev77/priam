<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Modification programme</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <form @submit.prevent="validateBeforeSubmit" class="form-horizontal" role="form">
            <div class="row" v-if="errors.count()!=0">
              <ul style="list-style: none">
                <li v-if="errors.has('Nom programme')">
                  <i v-show="errors.has('Nom programme')" class="fa fa-warning"></i>
                  <label v-show="errors.has('Nom programme')" :class="{'has-error': errors.has('Nom programme') }">{{ errors.first('Nom programme') }}</label>
                </li>
                <li v-if="errors.has('Rion theorique')">
                  <i v-show="errors.has('Rion theorique')" class="fa fa-warning"></i>
                  <label v-show="errors.has('Rion theorique')" :class="{'has-error': errors.has('Rion theorique') }">{{ errors.first('Rion theorique') }}</label>
                </li>
                <li v-if="errors.has('Famille')">
                  <i v-show="errors.has('Famille')" class="fa fa-warning"></i>
                  <label v-show="errors.has('Famille')" :class="{'has-error': errors.has('Famille') }">{{ errors.first('Famille') }}</label>
                </li>
                <li v-if="errors.has('Type utilisation')">
                  <i v-show="errors.has('Type utilisation')" class="fa fa-warning"></i>
                  <label v-show="errors.has('Type utilisation')" :class="{'has-error': errors.has('Type utilisation') }">{{ errors.first('Type utilisation') }}</label>
                </li>
              </ul>
            </div>

            <div class="row espacement">
              <!-- Numero programme -->
              <div class="col-sm-2">
                <span class="pull-right" >N° programme</span>
              </div>
              <div class="col-sm-3">
                <strong>{{ numProgramme }}</strong>
              </div>
            </div>
              <div class="row espacement">
              <!-- Nom programme -->
              <div class="col-sm-2" :class="{'has-error': errors.has('Nom programme') }">
                <span class="pull-right" for="nom">Nom programme</span>
              </div>
              <div class="col-sm-3" >
                <input maxlength="20" name="Nom programme" v-model="nom" v-validate="'required|max:20'" class="form-control" :class="{'has-error': errors.has('Nom programme') }"  type="text" >
              </div>

              <!-- Rion théorique -->
              <div class="col-sm-2" :class="{'has-error': errors.has('Rion theorique') }">
                <label class="control-label pull-right">Rion théorique</label>
              </div>
              <div class="col-sm-3" >
                <v-select name="Rion theorique"
                          v-validate="'required'"
                          :searchable="false"
                          label="value"
                          v-model="rionTheoriqueSelected"
                          :options="rionTheoriqueOptions"
                          :disabled="isNonModifiable">
                </v-select>

              </div>
              <!-- Famille -->
              <div class="col-sm-2" :class="{'has-error': errors.has('Famille') }">
                <label class="control-label pull-right">Famille</label>
              </div>
              <div class="col-sm-2">
                <v-select name="Famille"
                          v-validate="'required'"
                          :searchable="false" label="value"
                          v-model="familleSelected"
                          :options="familleOptions"
                          :on-change="loadTypeUtilisation"
                          :disabled="isNonModifiable">
                </v-select>

              </div>

              <!-- Type d'utilisation -->
              <div class="col-sm-2" :class="{'has-error': errors.has('Type utilisation') }">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-3" >
                <v-select name="Type utilisation"
                          v-validate="'required'"
                          :searchable="false"
                          label="value"
                          v-model="typeUtilisationSelected"
                          :options="typeUtilisationOptions"
                          :disabled="isNonModifiable">
                </v-select>

              </div>
            </div>
            <!-- Mode de répartition -->
            <div class="row espacement">
              <div class="col-sm-2">
                <label class="pull-right">Mode de répartition</label>
              </div>
              <div class="col-sm-2">
                <label class="radio radio-inline checked disabled" for="TypeRepartitionOeuvre">
                  <input
                    type="radio"
                    id="TypeRepartitionOeuvre"
                    value="OEUVRE"
                    v-model="typeRepart"
                    disabled> Oeuvre
                  <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
                </label>
              </div>
              <div class="col-sm-3">
                <label class="radio radio-inline disabled">
                  <input
                    type="radio"
                    id="TypeRepartitionOeuvreAyantDroit"
                    value="Ayant droit"
                    v-model="typeRepart"
                    disabled> Ayant droit
                  <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
                </label>
              </div>
            </div>
          </form>
        </div>


      </div>
      <div class="row espacement">
        <button class="btn btn-default btn-primary pull-right" type="button" @click="$emit('cancel')">Annuler</button>
        <button class="btn btn-default btn-primary pull-right" type="button" @click="validateBeforeSubmit">Modifier</button>
      </div>
    </div>

    <modal v-if="showModal">
      <label class="homer-prompt-q control-label" slot="body">
        Attention un programme avec le même nom est déjà existant. Voulez-vous continuer ?
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="noContinue">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="yesContinue">Oui</button>
      </template>
    </modal>

    <modal v-if="showModalMemeRion">
      <div class="homer-prompt-q control-label" slot="body">
        Attention le programme {{ nom }} a le même Rion, Famille et Type d'utilisation que celui que vous voulez modifier. Voulez-vous continuer?
      </div>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="onNoConfirm">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="onYesConfirm">Oui</button>
      </template>
    </modal>
  </div>

</template>
<script>
  import vSelect from '../common/Select.vue';
  import messagesfr from 'vee-validate/dist/locale/fr';
  import Modal from '../common/Modal.vue';

  export default {

    props : {

        numProg : {
            type : String,
            default : ''
        }

    },

    data(){
      return {
        showModalMemeRion : false,
        showModal: false,
        programmeToModify: null,
        programmeExist : false,
        resources:{},
        numProgramme :'',
        nom :  '',
        rionTheoriqueSelected : null,
        familleSelected:  null,
        typeUtilisationSelected:  null,
        typeRepart: 'OEUVRE',

        programmeData: {
          nom: '',
          numProg : '',
          famille : '',
          typeRepart: 'OEUVRE',
          typeUtilisation : '',
          rionTheorique :'',
        },

        formSubmitted: false

      }
    },
    computed: {
      familleOptions() {
        return this.$store.getters.familleOptionsVide;
      },

      typeUtilisationOptions() {
        return this.$store.getters.typeUtilisationOptionsVide;
      },

      rionTheoriqueOptions() {
        return this.$store.getters.rionsAddProg;
      },

      isNonModifiable() {
          return this.programmeToModify !== undefined && this.programmeToModify !== null && (this.programmeToModify.statut === 'VALIDE' || this.programmeToModify.statut === 'EN_COURS' || this.programmeToModify.statut === 'AFFECTE')
      }
    },


    methods: {

      loadTypeUtilisation(val) {
        console.log('loadTypeUtilisation=' + val)
        this.familleSelected = val;
        this.$store.dispatch('loadTypeUtilisationVide', val);

        if(this.typeUtilisationSelected == null) {
          var typeUtilCode = this.programmeToModify.typeUtilisation;
          this.typeUtilisationSelected  = this.$store.getters.typeUtilisation.find(function (element) {
            return element.id === typeUtilCode;
          });

        } else {
          this.typeUtilisationSelected =  this.$store.getters.typeUtilisationOptionsVide[0];
        }


      },

      validateBeforeSubmit() {
        console.log("validateBeforeSubmit()")
        this.$validator.validateAll().then(() => {
            console.log("All is ok")

          this.verifierEtModifierLeProgramme();
        }).catch(() => {
          // eslint-disable-next-line
          console.log('Correct them errors!');
        });
      },

      verifierEtModifierLeProgramme() {

        if(this.programmeToModify.nom !== this.nom) {
          this.resource.searchProgramme({nom: this.nom})
            .then(response => {
              return response.json();
            })
            .then(data => {
              this.programmeExist = data;
              if (this.programmeExist) {
                this.showModal = true;
              } else {
                this.verifierSiMemeRionFamilleTypeUtil();
              }


            });
        } else {
          this.modifierProgramme();
        }
      },

      modifierProgramme(){
        //if(this.verifierLeProgramme()){
        this.programmeData.numProg=this.numProgramme;
        this.programmeData.nom=this.nom;
        this.programmeData.typeUtilisation=this.typeUtilisationSelected.id;
        this.programmeData.famille=this.familleSelected.id;
        this.programmeData.typeRepart=this.typeRepart;
        this.programmeData.rionTheorique=this.rionTheoriqueSelected.id;
        this.resource.updateProgramme(this.programmeData)
            .then(response => {
              return response.json();
            })
            .then(data => {
              console.log("modification ok");
              this.$emit('validate');
            })
            .catch(response => {
                alert("Erreur technique lors de la modification du programme !! ");
                this.$emit('cancel');
            });
        //}

      },

      noContinue() {
        this.showModal = false;
        this.$emit('cancel');
      },

      yesContinue() {
        this.showModal = false;
        this.verifierSiMemeRionFamilleTypeUtil();

      },

      verifierSiMemeRionFamilleTypeUtil() {
        if(this.programmeToModify.statut === 'CREE') {
          var critereRechercheData = {};
          critereRechercheData.typeUtilisation = this.typeUtilisationSelected !== undefined ? this.typeUtilisationSelected.id : null;
          critereRechercheData.famille = this.familleSelected !== undefined ? this.familleSelected.id : null;
          critereRechercheData.rionTheorique= this.rionTheoriqueSelected !== undefined ? this.rionTheoriqueSelected.id : null;
          critereRechercheData.statutCode = ['CREE', 'AFFECTE', 'EN_COURS'];

          this.resource.searchAllProgramme(critereRechercheData)
            .then(response => {
              return response.json();
            })
            .then(data => {
              var listeProg = data.content;
              if(listeProg.length > 0) {
                 this.showModalMemeRion = true;
              } else {
                  this.modifierProgramme();
              }
            })
            .catch(response => {
              alert("Erreur technique lors de la modification du programme !! " + response);
            });
        } else {
          this.modifierProgramme();
        }
      },

      onNoConfirm() {
        this.showModalMemeRion = false;
        this.$emit('cancel');
      },

      onYesConfirm() {
        this.showModalMemeRion = false;
        this.modifierProgramme();
      },

      initData() {
          if(this.programmeToModify !== undefined && this.programmeToModify !== null) {

            this.numProgramme = this.programmeToModify.numProg;
            this.nom = this.programmeToModify.nom;

            var familleCode = this.programmeToModify.famille;
            this.familleSelected = this.$store.getters.famille.find(function (element) {
              return element.id === familleCode;
            });


            var rionTheoriqueCode = this.programmeToModify.rionTheorique;
            console.log('rionTheoriqueCode=' + rionTheoriqueCode)
            this.rionTheoriqueSelected = this.$store.getters.rionsAddProg.find(function (element) {
              return Number.parseInt(element.id) === rionTheoriqueCode;
            });

            console.log('rionTheoriqueSelected=' + this.rionTheoriqueSelected)
          }

      }

    },
    components: {
      vSelect: vSelect,
      modal : Modal
    },

    created(){
      const customActions = {
          searchProgramme : {method : 'GET', url :'app/rest/programme/nom/{nom}'},
          findByNumProg : {method : 'GET', url : 'app/rest/programme/numProg/{numProg}'},
          updateProgramme : {method: 'PUT', url : 'app/rest/programme/'},
          searchAllProgramme : {method : 'POST', url :'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'}
      }
      this.resource= this.$resource('', {}, customActions);


      this.resource.findByNumProg({numProg:  this.numProg})
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.programmeToModify = data;
          this.initData();
        });




    }
  }
</script>
<style>
  .espacement {
    height: 30px;
    margin-top: 10px;
  }

  body {
    font-family: Helvetica, sans-serif;
  }

  .container {
    width: 500px;
  }

  h1 {
    text-align: center
  }

  img {
    text-align: center
  }

  .submitted {
    color: #4fc08d;
  }

  .has-error {
    color: #a94442;
  }
</style>
