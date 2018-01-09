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
                <template v-if="!isNonModifiable">
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
                </template>
              </ul>
            </div>


            <div class="row">

              <!-- ========================================================================= -->
              <!-- ========================== numProgramme  ================================ -->
              <!-- ========================================================================= -->

              <div class="form-group col-md-6">
                <label class="col-md-9 control-label">N° programme</label>
                <div class="col-md-15">
                  <strong>{{ numProgramme }}</strong>
                </div>
              </div>
            </div>

            <div class="row espacement">

              <!-- ========================================================================= -->
              <!-- ========================== Nom programme ================================ -->
              <!-- ========================================================================= -->

              <div class="form-group col-md-6" :class="{'has-error': errors.has('Nom programme') }">
                <label class="col-md-9 control-label">Nom programme <span class="mandatory">*</span></label>
                <div class="col-md-15">
                  <input
                         tabindex="0"
                         v-focus
                         maxlength="20"
                         name="Nom programme"
                         v-model="nom"
                         v-validate.disable="'required|max:20'"
                         class="form-control"
                         :class="{'has-error': errors.has('Nom programme') }"
                         type="text" >
                </div>
              </div>

              <!-- ========================================================================= -->
              <!-- ========================== Rion Theorique================================ -->
              <!-- ========================================================================= -->

              <template v-if="!isNonModifiable">
                <div class="form-group col-md-6" :class="{'has-error': errors.has('rion.theorique') }">
                  <label class="col-md-8 control-label">Rion statutaire <span class="mandatory">*</span></label>
                  <div class="col-md-16">
                    <v-select name="rion.theorique"
                              v-validate.disable="'required'"
                              :searchable="false"
                              label="value"
                              v-model="rionTheoriqueSelected"
                              :options="rionTheoriqueOptions">
                    </v-select>
                  </div>
                </div>

              </template>
              <template v-else>
                <div class="form-group col-md-6">
                  <label class="col-md-8 control-label">Rion statutaire</label>
                  <div class="col-md-16">
                    <v-select name="rion.theorique"
                              :searchable="false"
                              label="value"
                              v-model="rionTheoriqueSelected"
                              :options="rionTheoriqueOptions"
                              :disabled="isNonModifiable">
                    </v-select>
                  </div>
                </div>
              </template>

              <!-- ========================================================================= -->
              <!-- ========================== Famille ====================================== -->
              <!-- ========================================================================= -->

              <template v-if="!isNonModifiable">
                <div class="form-group col-md-5"  :class="{'has-error': errors.has('Famille') }">
                  <label class="col-md-8 control-label">Famille <span class="mandatory">*</span></label>
                  <div class="col-md-16">
                    <v-select name="Famille"
                              v-validate.disable="'required'"
                              :searchable="false" label="value"
                              v-model="familleSelected"
                              :options="familleOptions"
                              :on-change="loadTypeUtilisation" >
                    </v-select>
                  </div>
                </div>
              </template>
              <template v-else>
                <div class="form-group col-md-5">
                  <label class="col-md-8 control-label">Famille</label>
                  <div class="col-md-16">
                    <v-select name="Famille"
                              :searchable="false"
                              label="value"
                              v-model="familleSelected"
                              :options="familleOptions"
                              :on-change="loadTypeUtilisation"
                              :disabled="isNonModifiable">
                    </v-select>
                  </div>
                </div>
              </template>

              <!-- ========================================================================= -->
              <!-- ========================== typeUtilisation ============================== -->
              <!-- ========================================================================= -->

              <template v-if="!isNonModifiable">
                <div class="form-group col-md-7" :class="{'has-error': errors.has('typeUtilisation') }">
                  <label class="col-md-6 control-label">Type d'utilisation <span class="mandatory">*</span></label>
                  <div class="col-md-16">
                    <v-select name="typeUtilisation"
                              v-validate.disable="'required'"
                              :searchable="false"
                              label="value"
                              v-model="typeUtilisationSelected"
                              :options="typeUtilisationOptions">
                    </v-select>
                  </div>
                </div>

              </template>
              <template v-else>
                <div class="form-group col-md-7">
                  <label class="col-md-6 control-label">Type d'utilisation</label>
                  <div class="col-md-16">
                    <v-select name="typeUtilisation"
                              :searchable="false"
                              label="value"
                              v-model="typeUtilisationSelected"
                              :options="typeUtilisationOptions"
                              :disabled="isNonModifiable">
                    </v-select>
                  </div>
                </div>
              </template>
            </div> <!-- </row> -->

            <!-- Date Debut / Fin et territoire programme -->
            <div class="row espacement">
              <template v-if="!isNonModifiable">
                <div class="form-group col-md-6" :class="{'has-error': errors.has('dateDebutProgramme') }">
                  <label class="col-md-9 control-label">Date de début <span class="mandatory">*</span></label>
                  <div class="col-md-15">
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
              </template>
              <template v-else>
                <div class="form-group col-md-6">
                  <label class="col-md-9 control-label">Date de début</label>
                  <div class="col-md-15">
                    <date-picker name="dateDebutProgramme"
                                 v-model="dateDebutProgramme"
                                 date-format="dd/mm/yy"
                                 :zeroHour="true"
                                 :disabled="isNonModifiable">
                    </date-picker>
                  </div>
                </div>
              </template>

              <template v-if="!isNonModifiable">
                <div class="form-group col-md-6" :class="{'has-error': errors.has('dateFinProgramme') }">
                  <label class="col-md-8 control-label">Date de fin <span class="mandatory">*</span></label>
                  <div class="col-md-16">
                    <date-picker v-validate.disable="'required|date_format:DD/MM/YYYY'"
                                 data-vv-value-path="innerDateFinProgrammeValue"
                                 data-vv-name="dateFinProgramme"
                                 name="dateFinProgramme"
                                 v-model="dateFinProgramme"
                                 date-format="dd/mm/yy"
                                 :zeroHour="true" >
                    </date-picker>
                  </div>
                </div>
              </template>
              <template v-else>
                <div class="form-group col-md-6">
                  <label class="col-md-8 control-label">Date de fin</label>
                  <div class="col-md-16">
                    <date-picker name="dateFinProgramme"
                                 v-model="dateFinProgramme"
                                 date-format="dd/mm/yy"
                                 :zeroHour="true"
                                 :disabled="isNonModifiable" >
                    </date-picker>
                  </div>
                </div>
              </template>

              <!-- ========================================================================= -->
              <!-- =============================== territoire ============================== -->
              <!-- ========================================================================= -->

              <template v-if="!isNonModifiable">
                <div class="form-group col-md-10" :class="{'has-error': errors.has('territoire') }">
                  <label class="col-md-4 control-label">Territoire <span class="mandatory">*</span></label>
                  <div class="col-md-10">
                    <select2 class="form-control"
                             name="territoire"
                             v-validate.disable="'required'"
                             :searchable="true"
                             v-model="territoireSelected"
                             :options="territoireOptions">
                    </select2>

                  </div>
                </div>
              </template>
              <template v-else>
                <div class="form-group col-md-10">
                  <label class="col-md-4 control-label">Territoire</label>
                  <div class="col-md-10">
                    <select2 class="form-control"
                             :searchable="true"
                             v-model="territoireSelected"
                             :options="territoireOptions"
                             :disabled="isNonModifiable">
                    </select2>

                  </div>
                </div>
              </template>

            </div>

            <!-- Mode de répartition -->
            <div class="row espacement">
              <div class="form-group col-md-7">
                <label class="col-md-9 control-label">Mode de répartition</label>
                <div class="col-md-15">
                  <label class="radio radio-inline checked disabled" for="TypeRepartitionOeuvre">
                    <input
                      type="radio"
                      id="TypeRepartitionOeuvre"
                      value="OEUVRE"
                      v-model="typeRepart"
                      disabled> Oeuvre
                    <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
                  </label>

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
        Attention le programme {{ nomProgrammeMemeRion }} a les mêmes rion, famille et type d'utilisation que celui que vous voulez modifier. Voulez-vous continuer?
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
  import moment from 'moment';
  import Select2 from '../ui/Select2.vue';


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

        formSubmitted: false,

        nomProgrammeMemeRion : '',

        dateDebutProgramme : null,
        dateFinProgramme : null,
        territoireSelected : null

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
      },
      territoireOptions() {
        return this.$store.getters.territoire;
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
        console.log("this.programmeToModify.nom = ["  + typeof this.programmeToModify.nom + "]");
        console.log("this.nom = ["  + typeof this.nom + "]");

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
            debugger;
          this.verifierSiMemeRionFamilleTypeUtil();
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

        this.programmeData.dateDbtPrg=this.dateDebutProgramme;
        this.programmeData.dateFinPrg=this.dateFinProgramme;
        this.programmeData.cdeTer=this.territoireSelected;

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
              debugger;
              let progInResult = this.findProgInResult(listeProg, this.programmeToModify.numProg);
              if(progInResult !== undefined) {
                 this.showModalMemeRion = true;
                 this.nomProgrammeMemeRion = listeProg[0].nom;
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


      stringToDate(str) {
        if (typeof str === 'string' && str.length > 3 && str.length < 11) {
          var parts = str.split("/");
          var date = null;

          date = moment.utc([parts[2], parts[1] - 1, parts[0], 0, 0, 0, 0]).toDate();

          return date;
        }
        return null;
      },

      findProgInResult(listeProg, numProg) {
          var result = null;
          if(listeProg !== undefined && listeProg.length >0) {
            result =  listeProg.find(function (elem) {
                return elem.numProg !== numProg;
              })
          }

          return result;

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

            let dateDbtPrg = this.programmeToModify.dateDbtPrg;
            let dateFinPrg = this.programmeToModify.dateFinPrg;


            this.dateDebutProgramme = this.stringToDate(dateDbtPrg);
            this.dateFinProgramme = this.stringToDate(dateFinPrg);

            var cdeTer = this.programmeToModify.cdeTer;

            var cdeTerElem = this.$store.getters.territoire.find(function (element) {
              return Number.parseInt(element.id) === cdeTer;
            });

            this.territoireSelected = cdeTerElem.id;

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
          findByNumProg : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/numProg/{numProg}'},
          updateProgramme : {method: 'PUT', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/'},
          searchAllProgramme : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/programme/search?page={page}&size={size}&sort={sort},{dir}'}
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

  .mandatory {
    color: #FF0000;
  }

</style>
