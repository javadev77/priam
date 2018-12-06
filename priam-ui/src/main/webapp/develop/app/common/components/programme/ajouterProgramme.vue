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
                <li v-if="errors.has('dateDebutProgramme')">
                  <i v-show="errors.has('dateDebutProgramme')" class="fa fa-warning"></i>
                  <label v-show="errors.has('dateDebutProgramme')" :class="{'has-error': errors.has('dateDebutProgramme') }">{{ errors.first('dateDebutProgramme') }}</label>
                </li>
                <li v-if="errors.has('dateFinProgramme')">
                  <i v-show="errors.has('dateFinProgramme')" class="fa fa-warning"></i>
                  <label v-show="errors.has('dateFinProgramme')" :class="{'has-error': errors.has('dateFinProgramme') }">{{ errors.first('dateFinProgramme') }}</label>
                </li>

                <li v-if="errors.has('territoire')">
                  <i v-show="errors.has('territoire')" class="fa fa-warning"></i>
                  <label v-show="errors.has('territoire')" :class="{'has-error': errors.has('territoire') }">{{ errors.first('territoire') }}</label>
                </li>
              </ul>

            </div>

            <div class="row">

              <div class="form-group col-md-6" :class="{'has-error': errors.has('Nom programme') }">
                <label class="col-md-9 control-label">Nom programme <span class="mandatory">*</span></label>
                <div class="col-md-15">
                  <input v-focus tabindex="0" ref="nomProgramme" maxlength="20" name="Nom programme" v-model="nom" v-validate.disable="'required|max:20'" class="form-control" :class="{'has-error': errors.has('Nom programme') }"  type="text" >
                </div>
              </div>

              <div class="form-group col-md-6" :class="{'has-error': errors.has('rion.theorique') }">
                <label class="col-md-8 control-label">Rion cible <span class="mandatory">*</span></label>
                <div class="col-md-16">
                  <v-select name="rion.theorique" v-validate.disable="'required'" :searchable="false" label="value" v-model="rionTheoriqueSelected"
                            :options="rionTheoriqueOptions" :classValidate="{'has-error': errors.has('rion.theorique') }">
                  </v-select>
                </div>
              </div>

              <div class="form-group col-md-5"  :class="{'has-error': errors.has('Famille') }">
                <label class="col-md-8 control-label">Famille <span class="mandatory">*</span></label>
                <div class="col-md-16">
                  <v-select name="Famille" v-validate.disable="'required'" :searchable="false" label="value" v-model="familleSelected" :options="familleOptions"
                            :on-change="loadTypeUtilisation" :classValidate="{'has-error': errors.has('Famille') }">
                  </v-select>
                </div>
              </div>

              <div class="form-group col-md-7" :class="{'has-error': errors.has('typeUtilisation') }">
                <label class="col-md-6 control-label">Type d'utilisation <span class="mandatory">*</span></label>
                <div class="col-md-16">
                  <v-select name="typeUtilisation" v-validate.disable="'required'" :searchable="false" label="value" v-model="typeUtilisationSelected"
                            :options="typeUtilisationOptions" :classValidate="{'has-error': errors.has('typeUtilisation') }">
                  </v-select>
                </div>
              </div>
            </div>

            <!-- Date Debut / Fin et territoire programme -->
            <div class="row  espacement">
              <div class="form-group col-md-6" :class="{'has-error': errors.has('dateDebutProgramme') }">
                <label class="col-md-9 control-label">Date de début <span class="mandatory">*</span></label>
                <div class="col-md-15">
                  <!--<date-picker @update-date="updateDebutProgramme" name="dateDebutProgramme" :value="dateDebutProgramme" date-format="dd/mm/yy" :zeroHour="true" ></date-picker>-->
                  <date-picker v-validate.disable="'required|date_format:DD/MM/YYYY|before:dateFinProgramme'"
                               data-vv-value-path="innerDateDebutProgrammeValue"
                               data-vv-name="dateDebutProgramme"
                               name="dateDebutProgramme"
                               v-model="dateDebutProgramme"
                               date-format="dd/mm/yy"
                               :zeroHour="true" >
                  </date-picker>
                </div>
              </div>

              <div class="form-group col-md-6" :class="{'has-error': errors.has('dateFinProgramme') }">
                <label class="col-md-8 control-label">Date de fin <span class="mandatory">*</span></label>
                <div class="col-md-16">
                  <date-picker v-validate.disable="'required|date_format:DD/MM/YYYY'"
                               data-vv-value-path="innerDateFinProgrammeValue"
                               data-vv-name="dateFinProgramme"
                               name="dateFinProgramme"
                               v-model="dateFinProgramme"
                               date-format="dd/mm/yy"
                               :zeroHour="true">
                  </date-picker>
                </div>
              </div>

              <div class="form-group col-md-5" :class="{'has-error': errors.has('territoire') }">
                <label class="col-md-8 control-label">Territoire <span class="mandatory">*</span></label>
                <div class="col-md-16">
                    <select2 class="form-control"
                             name="territoire"
                             v-validate.disable="'required'"
                             :searchable="true"
                             v-model="territoireSelected"
                             :options="territoireOptions"
                             :class="{'has-error': errors.has('territoire') }" >
                    </select2>
                </div>
              </div>

              <div v-if="familleSelected.id === 'FDSVAL'" class="form-group col-md-7" >
                <label class="col-md-6 control-label">Type de droit <span>&nbsp;</span></label>
                <div class="col-md-16">
                    <input v-model="typeDroit" :value="typeDroitValue" disabled/>
                </div>
              </div>

            </div>

            <!-- Mode de répartition -->
            <div class="row  espacement">
              <div class="form-group col-md-8">
                <label class="col-md-7 control-label">Mode de répartition</label>
                <div class="col-md-15">
                  <label class="radio radio-inline" :class="{'checked' : typeRepart === 'OEUVRE', 'disabled' : isFamilleNotValorisation}" for="TypeRepartitionOeuvre">
                    <input
                      type="radio"
                      id="TypeRepartitionOeuvre"
                      value="OEUVRE"
                      v-model="typeRepart"
                      :disabled="isFamilleNotValorisation"> Oeuvre
                    <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
                  </label>
                </div>
              </div>

              <div class="form-group col-md-6">
                <label class="radio radio-inline" :class="{'checked' : typeRepart === 'AYANT_DROIT', 'disabled' : isFamilleNotValorisation}">
                  <input
                    type="radio"
                    id="TypeRepartitionOeuvreAyantDroit"
                    value="AYANT_DROIT"
                    v-model="typeRepart"
                    :disabled="isFamilleNotValorisation"> Ayant droit
                      <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
                </label>
              </div>

              <div class="form-group col-md-5">
                <label class="radio radio-inline" :class="{'checked' : typeRepart === 'OEUVRE_AD', 'disabled' : isFamilleNotValorisation}">
                  <input
                    type="radio"
                    id="TypeRepartitionOeuvreEtAyantDroit"
                    value="OEUVRE_AD"
                    v-model="typeRepart"
                    :disabled="isFamilleNotValorisation"> Oeuvre/Ayant droit
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
  import vSelect from '../ui/Select.vue';
  import Modal from '../ui/Modal.vue';
  import DatePicker from '../ui/DatePicker.vue';
  import Select2 from '../ui/Select2.vue';

  const ID_FRANCE = 250;


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
        familleSelected: this.$store.getters.userFamille.id === 'ALL' ? this.$store.getters.familleOptionsVide[0]: this.$store.getters.userFamille,
        typeUtilisationSelected: null,
        typeRepart: 'OEUVRE',
        programmeData: {
          nom: '',
          numProg : '',
          famille : '',
          typeRepart: 'OEUVRE',
          typeUtilisation : '',
          rionTheorique :'',
          typeDroit : ''
        },
        formSubmitted: false,

        nomProgrammeMemeRion : '',

        dateDebutProgramme : null,
        dateFinProgramme : null,
        territoireSelected : null,

        typeDroitMap : new Map([
          ["FD01,OEUVRE_AD", "DE"],
          ["FD02,OEUVRE_AD", "PH"],
          ["FD03,AD", "DR"],
          ["FD04,AD", "DE"],
          ["FD05,OEUVRE_AD", "DE"],
          ["FD06,OEUVRE", "PH"],
          ["FD07,OEUVRE_AD", "PH"],
          ["FD09,AD", "DE"],
          ["FD10,AD", "PH"],
          ["FD11,AD", "DE"],
          ["FD12,OEUVRE", "DE/DR"],
          ["FD13,AD", "DE"],
          ["FD14,OEUVRE", ""],

        ]),

        typeDroit : ""
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
      territoireOptions() {
        return this.$store.getters.territoire;
      },

      typeDroitValue() {
        let typeDroit = "";
        if(this.familleSelected.id === "FDSVAL" && this.typeUtilisationSelected !== null) {
          debugger;
          var typeUil = this.typeUtilisationSelected.id;
          let key = typeUil + ","  + this.typeRepart;

          typeDroit = this.typeDroitMap.get(key);
          console.log("typeDroit="+typeDroit)
        }

        this.typeDroit = typeDroit;
        return typeDroit;

      },


      isFamilleNotValorisation() {
        return this.familleSelected !== null && this.familleSelected.id !== 'FDSVAL';
      }


    },

    watch : {


      familleSelected : function (newVal) {

        if(newVal.id !== "FDSVAL") {
          this.typeRepart = 'OEUVRE';
        }
      }
    },

    methods: {
      loadTypeUtilisation(val) {
        this.familleSelected = val;
        this.$store.dispatch('loadTypeUtilisationVide', val);
        this.typeUtilisationSelected =  this.$store.getters.typeUtilisationOptionsVide[0];


      },

      validateBeforeSubmit() {


        var validator = this.$validator;
        validator.validateAll().then(() => {
          this.verifierEtAjouterLeProgramme();
        }).catch(() => {
          // eslint-disable-next-line
          console.log('Correct them errors!');
          this.$refs.nomProgramme.focus();
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
          this.programmeData.dateDbtPrg=this.dateDebutProgramme;
          this.programmeData.dateFinPrg=this.dateFinProgramme;
          this.programmeData.cdeTer=this.territoireSelected;
          this.programmeData.typeDroit=this.typeDroit === '' ? null : this.typeDroit.trim();

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
          critereRechercheData.statutCode = ['EN_COURS', 'AFFECTE', 'CREE'];

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

      },

      updateDebutProgramme(date) {
        this.dateDebutProgramme = date;
        var error = {"field":"dateDebutProgramme","msg":"Le champ 'Date de debut' est obligatoire et non renseigné.","rule":"required","scope":"__global__"};

        if(this.dateDebutProgramme == null) {
          this.$validator.errorBag.errors.push(error);
        }else {
          let number = this.indexOf(this.$validator.errorBag.errors, error);
          this.$validator.errorBag.errors.splice(number, 1);
        }
      },

      updateDateFinProgramme(date) {
        this.dateFinProgramme = date;

        var error = {"field":"dateFinProgramme","msg":"Le champ 'Date de fin' est obligatoire et non renseigné.","rule":"required","scope":"__global__"};

        if(this.dateFinProgramme == null) {
          this.$validator.errorBag.errors.push(error);
        }else {
          let number = this.indexOf(this.$validator.errorBag.errors, error);
          this.$validator.errorBag.errors.splice(number, 1);
        }

      },

      indexOf(array, obj) {

        for(let i = 0; i < array.length; i++ ) {
          if(obj.field == array[i].field) {
            return i;
          }
        }
        return -1;

      },


    },
    components: {
      vSelect: vSelect,
      modal : Modal,
      datePicker : DatePicker,
      select2 : Select2
    },


    created(){
      const customActions = {
        searchProgramme : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/nom/{nom}'},
        addProgramme : {method: 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON +  'app/rest/programme/'},
        searchAllProgramme : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'}
      }
      this.resource= this.$resource('', {}, customActions);

      this.territoireOptions.forEach((territoire) => {


        if(territoire.id == ID_FRANCE)
          {
            this.territoireSelected = territoire.id;
          }
      })
    }
  }
</script>
<style>

  .dropdown-menu {
    overflow-y: auto;
  }

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

  .mandatory {
    color: #FF0000;
  }
</style>
