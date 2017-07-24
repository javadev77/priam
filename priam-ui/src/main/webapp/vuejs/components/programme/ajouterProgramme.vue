<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Création programme</a>
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
                  <label v-show="errors.has('Nom programme')" class="control-label" :class="{'has-error': errors.has('Nom programme') }">{{ errors.first('Nom programme') }}</label>
                </li>
                <li v-if="errors.has('rion.theorique')">
                  <i v-show="errors.has('rion.theorique')" class="fa fa-warning"></i>
                  <label v-show="errors.has('rion.theorique')" :class="{'has-error': errors.has('rion.theorique') }">{{ errors.first('rion.theorique') }}</label>
                </li>
                <li v-if="errors.has('Famille')">
                  <i v-show="errors.has('Famille')" class="fa fa-warning"></i>
                  <label v-show="errors.has('Famille')" :class="{'has-error': errors.has('Famille') }">{{ errors.first('Famille') }}</label>
                </li>
                <li v-if="errors.has('typeUtilisation')">
                  <i v-show="errors.has('typeUtilisation')" class="fa fa-warning"></i>
                  <label v-show="errors.has('typeUtilisation')" :class="{'has-error': errors.has('typeUtilisation') }">{{ errors.first('typeUtilisation') }}</label>
                </li>
              </ul>
            </div>
            <div class="row espacement">

                 <div class="col-sm-2" :class="{'has-error': errors.has('Nom programme') }">
                    <label class="control-label pull-right">Nom programme</label>
                 </div>
                 <div class="col-sm-3" >
                   <input maxlength="20" name="Nom programme" v-model="nom" v-validate="'required|max:20'" class="form-control" :class="{'has-error': errors.has('Nom programme') }"  type="text" >
                 </div>

              <!-- Rion théorique -->
              <div class="col-sm-2" :class="{'has-error': errors.has('rion.theorique') }">
                <label class="control-label pull-right">Rion théorique</label>
              </div>
              <div class="col-sm-3" >
                <v-select name="rion.theorique" v-validate="'required'" :searchable="false" label="value" v-model="rionTheoriqueSelected" :options="rionTheoriqueOptions" :classValidate="{'has-error': errors.has('Rion theorique') }">
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
              <div class="col-sm-2" :class="{'has-error': errors.has('typeUtilisation') }">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-3" >
                <v-select name="typeUtilisation" v-validate="'required'" :searchable="false" label="value" v-model="typeUtilisationSelected"
                          :options="typeUtilisationOptions" :classValidate="{'has-error': errors.has('typeUtilisation') }">
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
        <button class="btn btn-default btn-primary pull-right" type="submit" @click="validateBeforeSubmit()" name="button" >Créer</button>
      </div>
    </div>

    <modal v-if="showModalMemeNom">
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
        Attention le programme {{ nomProgrammeMemeRion }} a les mêmes rion, famille et type d'utilisation que celui que vous voulez créer. Voulez-vous continuer?
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
  import Modal from '../common/Modal.vue';

  export default {
    data(){
      return {
        showModalMemeRion: false,
        showModalMemeNom : false,
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
        formSubmitted: false,

        nomProgrammeMemeRion : ''

      }
    },
    computed: {
      familleOptions() {
        return this.$store.getters.familleOptionsVide;
      },

      typeUtilisationOptions() {
        if(this.familleSelected === null) {
            return []
        } else {
           return this.$store.getters.typeUtilisationOptionsVide;
        }
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
          console.log('Correct them errors!');
        });
      },

      verifierEtAjouterLeProgramme(){

        this.resource.searchProgramme({nom : this.nom})
            .then(response => {
              this.programmeExist = response.body;
              if (this.programmeExist) {
                  this.showModalMemeNom = true;
              } else {
                  this.checkMemeRionFamilleTypeUtil();
              }
            })
           .catch(response => {
              alert("Erreur technique lors de l'ajout du programme !! " + response);
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
            console.log("ajout ok");
            this.$emit('validate');
          }, response => {
            alert("Erreur technique lors de l'ajout du programme !! ");
            this.$emit('cancel');
          });
        //}

      },

      noContinue() {
        this.showModalMemeNom = false;
        this.$emit('cancel');
      },

      onNoConfirm() {
        this.showModalMemeRion = false;
        this.$emit('cancel');
      },

      checkMemeRionFamilleTypeUtil() {
          var critereRechercheData = {};
          critereRechercheData.typeUtilisation = this.typeUtilisationSelected !== undefined ? this.typeUtilisationSelected.id : null;
          critereRechercheData.famille = this.familleSelected !== undefined ? this.familleSelected.id : null;
          critereRechercheData.rionTheorique = this.rionTheoriqueSelected !== undefined ? this.rionTheoriqueSelected.id : null;

          this.resource.searchAllProgramme(critereRechercheData)
            .then(response => {
              return response.json();
            })
            .then(data => {
              var listeProg = data.content;
              if (listeProg.length > 0) {
                this.nomProgrammeMemeRion = listeProg[0].nom;
                this.showModalMemeRion = true;
              } else {
                  this.ajouterUnProgramme();
              }
            });
      },

      yesContinue() {
        this.showModalMemeNom = false;
        this.checkMemeRionFamilleTypeUtil();

      },

      onYesConfirm() {
        this.showModalMemeRion = false;
        this.ajouterUnProgramme()

      }


    },
    components: {
      vSelect: vSelect,
      modal : Modal
    },
    created(){
      const customActions = {
        searchProgramme : {method : 'GET', url :'app/rest/programme/nom/{nom}'},
        addProgramme : {method: 'POST', url : 'app/rest/programme/'},
        searchAllProgramme : {method : 'POST', url :'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'}
      }
      this.resource= this.$resource('', {}, customActions);

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
