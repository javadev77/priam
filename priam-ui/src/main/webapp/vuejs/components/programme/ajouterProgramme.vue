<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <!--<strong>Critères de Recherche</strong>-->
          <a>Création programme</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <form @submit.prevent="validateBeforeSubmit" class="form-horizontal" role="form">
            <div class="row" v-if="errors.count()!=0">
              <ul>
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
                <v-select name="Rion theorique" v-validate="'required'" :searchable="false" label="value" v-model="rionTheoriqueSelected" :options="rionTheoriqueOptions" :classValidate="{'has-error': errors.has('Rion theorique') }">
                </v-select>

              </div>
              <!-- Famille -->
              <div class="col-sm-2" :class="{'has-error': errors.has('Famille') }">
                <label class="control-label pull-right">Famille</label>
              </div>
              <div class="col-sm-2">
                <v-select name="Famille" v-validate="'required'" :searchable="false" label="value" v-model="familleSelected" :options="familleOptions"
                          :on-change="loadTypeUtilisation" :classValidate="{'has-error': errors.has('Famille') }">
                </v-select>

              </div>

              <!-- Type d'utilisation -->
              <div class="col-sm-2" :class="{'has-error': errors.has('Type utilisation') }">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-3" >
                <v-select name="Type utilisation" v-validate="'required'" :searchable="false" label="value" v-model="typeUtilisationSelected"
                          :options="typeUtilisationOptions" :classValidate="{'has-error': errors.has('Type utilisation') }">
                </v-select>

              </div>
            </div>
            <!-- Type de répartition -->
            <div class="row espacement">
              <div class="col-sm-2">
                <label class="pull-right">Type de répartition</label>
              </div>
              <div class="col-sm-2">
                <label class="radio radio-inline checked" for="TypeRepartitionOeuvre">
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
                <label class="radio radio-inline" for="TypeRepartitionOeuvreAyantDroit">
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
        <button class="btn btn-default btn-primary pull-right" type="submit" @click="validateBeforeSubmit()" name="button" >Créer</button>
      </div>
    </div>
  </div>

</template>
<script>
  import vSelect from '../common/Select.vue';
  import messagesfr from 'vee-validate/dist/locale/fr';
  export default {
    data(){
      return {
        programmeExist : false,
        resources:{},
        resource: '',
        nom : '',
        rionTheoriqueSelected : null,
        familleSelected: null,
        typeUtilisationSelected: null,
        typeRepart:'OEUVRE',
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
          this.verifierEtAjouterLeProgramme();
        }).catch(() => {
          // eslint-disable-next-line
          alert('Correct them errors!');
        });
      },
      verifierEtAjouterLeProgramme(){

        this.resource.searchProgramme({nom : this.nom}).then(response => {
            console.log(response.body);
          this.programmeExist = response.body;
          if (this.programmeExist) {
            var confirmation = confirm("Attention un programme avec le même nom est déjà existant. Voulez-vous continuer?");
            if (confirmation == true) {
              this.ajouterUnProgramme();
              this.console.log("Confirmation d'ajout de programme OK");
              return true;
            } else {
              this.console.log("Confirmation d'ajout de programme KO");
              return false;
            }
          }
        });
      },
      ajouterUnProgramme(){
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

      }

    },
    components: {
      vSelect: vSelect
    },
    created(){
      const customActions = {
        searchProgramme : {method : 'GET', url :'app/rest/programme/{nom}'},
        addProgramme : {method: 'POST', url : 'app/rest/programme/'},
      }
      this.resource= this.$resource('', {}, customActions);
      this.$validator.updateDictionary({
        fr: {
          messages: messagesfr.messages,
        }

      });
      this.$validator.setLocale('fr');
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
