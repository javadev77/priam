<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <!--<strong>Critères de Recherche</strong>-->
          <a>Modification programme</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <form @submit.prevent="validateBeforeSubmit" class="form-horizontal" role="form">

            <div class="row espacement">
              <div class="col-sm-2">
                <span class="pull-right" >N° programme</span>
              </div>
              <div class="col-sm-3">
               {{ numProgramme }}
              </div>
            </div>

            <div class="row espacement">

              <div class="col-sm-2">
                <span class="pull-right" for="nomProgramme">Nom programme</span>
              </div>
              <div class="col-sm-3" :class="{'has-error': errors.has('nom') }">
                <input name="nomProgramme" v-model="nom" v-validate="'required|max:20'" class="form-control" :class="{'has-error': errors.has('nom') }"  type="text" >
                <i v-show="errors.has('nom')" class="fa fa-warning"></i>
                <label v-show="errors.has('nom')" :class="{'has-error': errors.has('nom') }">{{ errors.first('nom') }}</label>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Rion théorique</label>
              </div>
              <div class="col-sm-3">
                <v-select :searchable="false" label="value"
                          v-model="rionTheoriqueSelected"
                          :options="rionTheoriqueOptions"
                          :disabled="isNonModifiable">
                </v-select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Famille</label>
              </div>
              <div class="col-sm-2">
                <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions"
                          :on-change="loadTypeUtilisation"
                          :disabled="isNonModifiable"
                >
                </v-select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-5">
                <v-select :searchable="false" label="value" v-model="typeUtilisationSelected"
                          :disabled="isNonModifiable"
                          :options="typeUtilisationOptions">
                </v-select>
              </div>
            </div>
            <div class="row espacement">
              <div class="col-md-2">
                <label class="pull-right">Type de répartition</label>
              </div>
              <div class="col-md-2">
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
              <div class="col-md-3">
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
        <button class="btn btn-default btn-primary pull-right" type="button" @click="ajouterUnProgramme()" name="button" >Créer</button>
      </div>
    </div>
  </div>

</template>
<script>
  import vSelect from '../common/Select.vue';

  export default {

    props : {

        numProg : {
            type : String,
            default : ''
        }

    },

    data(){
      return {
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
          return this.programmeToModify !== undefined &&  (this.programmeToModify.statut === 'VALIDE' || this.programmeToModify.statut === 'EN_COURS' || this.programmeToModify.statut === 'AFFECTE')
      }
    },


    methods: {

      loadTypeUtilisation(val) {
        this.familleSelected = val;
        this.$store.dispatch('loadTypeUtilisationVide', val);
        this.typeUtilisationSelected =  this.$store.getters.typeUtilisationOptionsVide[0];
      },

      validateBeforeSubmit() {
        this.$validator.validateAll().then(() => {
          // eslint-disable-next-line
          alert('From Submitted!');
        }).catch(() => {
          // eslint-disable-next-line
          alert('Correct them errors!');
        });
      },

      verifierLeProgramme(){

        this.resource.searchProgramme({nom : this.nom}).then(response => {
          console.log(response.body);
          this.programmeExist = response.body;
          if (this.programmeExist) {
            var confirmation = confirm("Attention un programme avec le même nom est déjà existant. Voulez-vous continuer?");
            if (confirmation == true) {
              alert("ajouter programme");
              return true;
            } else {
              alert("ne pas ajouter le programme");
              return false;
            }
          }
        });
      },

      ajouterUnProgramme(){
        //if(this.verifierLeProgramme()){
        this.programmeData.numProg=this.nom;
        this.programmeData.nom=this.nom;
        this.programmeData.typeUtilisation=this.typeUtilisationSelected.id;
        this.programmeData.famille=this.familleSelected.id;
        this.programmeData.typeRepart=this.typeRepart;
        this.programmeData.rionTheorique=this.rionTheoriqueSelected.id;
        this.resource.addProgramme(this.programmeData).then(response => {
          alert("ajout ok");
          this.$emit('close');
        }, response => {
          alert("Erreur technique lors de l'ajout du programme !! ");
          this.$emit('close');
        });
        //}

      },

      initData() {
          if(this.programmeToModify !== undefined && this.programmeToModify !== null) {

            this.numProgramme = this.programmeToModify.numProg;
            this.nom = this.programmeToModify.nom;

            var familleCode = this.programmeToModify.famille;
            this.familleSelected = this.$store.getters.famille.find(function (element) {
              return element.id === familleCode;
            });

            var typeUtilCode = this.programmeToModify.typeUtilisation;
            this.typeUtilisation = this.$store.getters.typeUtilisation.find(function (element) {
              return element.id === typeUtilCode;
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
      vSelect: vSelect
    },

    created(){
      const customActions = {
          searchProgramme : {method : 'GET', url :'app/rest/programme/{nom}'},
          addProgramme : {method: 'POST', url : 'app/rest/programme/'},
          findByNumProg : {method : 'GET', url : 'app/rest/programme/{numProg}'}
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
