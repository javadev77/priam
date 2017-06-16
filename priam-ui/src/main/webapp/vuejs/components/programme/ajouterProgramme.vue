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
            <div class="row">

                 <div class="col-sm-2">
                   <span class="pull-right" for="nomProgramme">Nom programme</span>
                 </div>
                 <div class="col-sm-3" :class="{'has-error': errors.has('nom') }">
                   <input name="nomProgramme" v-model="programmeData.nom" v-validate="'required|max:20'" class="form-control" :class="{'has-error': errors.has('nom') }"  type="text" >
                   <i v-show="errors.has('nom')" class="fa fa-warning"></i>
                   <label v-show="errors.has('nom')" :class="{'has-error': errors.has('nom') }">{{ errors.first('nomProgramme') }}</label>
                 </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Rion théorique</label>
              </div>
              <div class="col-sm-3">
                <v-select :searchable="false" label="value" v-model="rionTheoriqueSelected" :options="rionTheoriqueOptions">
                </v-select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Famille</label>
              </div>
              <div class="col-sm-2">
                <v-select :searchable="false" label="value" v-model="programmeData.famille" :options="familleOptions"
                          :on-change="loadTypeUtilisation">
                </v-select>
              </div>
              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-3">
                <v-select :searchable="false" label="value" v-model="programmeData.typeUtilisation"
                          :options="typeUtilisationOptions">
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
                <div class="row formula-buttons">
                <button class="btn btn-default btn-primary pull-right" type="button" @click="$emit('cancel')">Annuler</button>
                <button class="btn btn-default btn-primary pull-right" type="submit" @click="verifierLeProgramme" name="button" >Valider</button>
                  <button class="btn btn-default btn-primary pull-right" type="button" @click="ajouterUnProgramme()" name="button" >ajax</button>
                </div>
                </div>
            </div>
          </form>
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
                <p>reponse : {{mareponse}}</p>
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
        programmeExist : false,
        resources:{},
        resource: '',
        nomProgramme : '',
        rionTheoriqueSelected: {'id': 'VIDE', 'value': ''},
        famille: {'id': 'ALL', 'value': 'Toutes'},
        typeUtilisation: {'id': 'ALL', 'value': 'Tous'},

        programmeData: {
          nom: '',
          numProg : '',
          famille : '',
          typeUtilisation : '',
          rionTheorique :'',
          typeRepart: 'Oeuvre'

        },
        formSubmitted: false

      }
    },
    computed: {
      familleOptions() {
        return this.$store.getters.familleOptions;
      },

      typeUtilisationOptions() {
        return this.$store.getters.typeUtilisationOptions;
      },
      familleTypeUtilMap() {
        return this.$store.getters.familleTypeUtilMap;
      },
      rionTheoriqueOptions() {
        return this.$store.getters.rionsAddProg;
      },
    },
    methods: {
      loadTypeUtilisation(val) {
        this.programmeData.famille = val;
        this.programmeData.typeUtilisation = {'id': 'ALL', 'value': 'Tous'};
        this.$store.dispatch('loadTypeUtilisation', val);
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

        this.resource.searchProgramme({nom : programmeData.nomProgramme}).then(response => {
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
        if(this.verifierLeProgramme()){
          this.programmeData.numProg=this.programmeData.nom;
          this.programmeData.typeUtilisation=rionTheoriqueSelected.id;
          alert(this.programmeData.typeUtilisation);
          this.programmeData.famille=famille.id;
          alert(this.programmeData.famille);
          resource.addProgramme(this.programmeData).then(response => {
            alert("ajout ok");
          }, response => {
            // error
            alert("ajout ok");
          });
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
      }
      this.resource= this.$resource('', {}, customActions);
    }
  }
</script>
<style>
  .espacement {
    height: 100px;
    margin-top: 20px;
    margin-left: 40px;
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
