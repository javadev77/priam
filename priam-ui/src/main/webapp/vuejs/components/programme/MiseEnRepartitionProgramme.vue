<template>
  <modal>
     <template slot="body" v-if="!isToShowErrors">

       <div class="row" v-if="modeRepartition == 'MISE_EN_REPART'">
         <div class="col-sm-1">
         </div>
         <div class="col-sm-15">
            <label class="orangeText">
              Attention, vous êtes sur le point d'envoyer le programme en répartition
            </label>
        </div>
       </div>

      <div class="row">
        <div class="col-sm-3">
          <label class="pull-right blueText">N° programme</label>
        </div>
        <div class="col-sm-5">
          {{ programmeInfo.numProg }}
        </div>


        <div class="col-sm-4">
          <span class="pull-right blueText">Famille</span>
        </div>
        <div class="col-sm-5">
          {{ getFamilleByCode(programmeInfo.famille) !== undefined ? getFamilleByCode(programmeInfo.famille).value : '' }}
        </div>

        <div class="col-sm-3">
          <span class="pull-right blueText">Rion statutaire</span>
        </div>
        <div class="col-sm-3">
          {{ getLibelleRionById(programmeInfo.rionTheorique) }}
        </div>

      </div>

      <br/>
      <!-- 2 eme ligne -->
      <div class="row">

        <div class="col-sm-3">
          <span class="pull-right blueText">Nom</span>
        </div>
        <div class="col-sm-5">
          {{ programmeInfo.nom }}
        </div>


        <div class="col-sm-4">
          <span class="pull-right blueText">Type d'utilisation</span>
        </div>
        <div class="col-sm-6">
          {{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? getTypeUtilisationByCode(programmeInfo.typeUtilisation).value : '' }}
        </div>
      </div>


        <br/>
        <!-- 3 eme ligne -->
        <div class="row">

          <div class="col-sm-1">
          </div>
        <div class="col-sm-9">
          <label class="radio radio-inline" :class="{'checked' : modeRepartition == 'REPART_BLANC', 'disabled' : !isRightMSEREP }">
            <input
              type="radio"
              value="REPART_BLANC"
              v-model="modeRepartition"
              :disabled="!isRightMSEREP"
              > Répartition à blanc
            <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
          </label>
        </div>

        <div class="col-sm-9">
            <label class="radio radio-inline" :class="{'checked' : modeRepartition == 'MISE_EN_REPART', 'disabled' : !isRightMSEREP }">
            <input
              type="radio"
              value="MISE_EN_REPART"
              v-model="modeRepartition"
              :disabled="!isRightMSEREP"
              > Mise en répatition
            <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
          </label>
        </div>
      </div>
    </template>
    <template slot="body" v-else>
      <label>Le fichier de répartition contient les erreurs suivantes :</label>
      <div style="height:300px; overflow-y:scroll;">
        <label v-for="error in fichierFelixError.errors">
          {{ error }}
        </label>

      </div>

    </template>
    <template slot="footer" v-if="!isToShowErrors">
      <button class="btn btn-default btn-primary pull-right yes" @click="validateFelixData">Valider</button>
      <button class="btn btn-default btn-primary pull-right no" @click="$emit('cancel')">Annuler</button>
    </template>
    <template slot="footer" v-else>
      <button class="btn btn-default btn-primary" @click="$emit('close')">Fermer</button>
    </template>
  </modal>
</template>

<script>

  import Modal from '../common/Modal.vue';
  import chargementMixins from '../../mixins/chargementMixin';
  import programmeMixins from '../../mixins/programmeMixin';

  export default {
      mixins: [chargementMixins, programmeMixins],

      props : {

        numProg : {
            type : String,
            required : true
        }
      },

    mounted() {

      var modalElem = $('.modal-dialog');
      modalElem.css('width', "60%");

    },

      data() {
        return {
          programmeInfo : {},
          fichierFelixError : {},
          modeRepartition : 'REPART_BLANC',
          isToShowErrors : false
        }
      },

      created() {
        console.log("numProg = " + this.numProg);

        const customActions = {
          findByNumProg : {method : 'GET', url : 'app/rest/programme/numProg/{numProg}'},
          validateFelixData : {method : 'GET', url : 'app/rest/repartition/validateFelixData/{numProg}'},
          generateFelixData : {method : 'POST', url : 'app/rest/repartition/generateFelixData'}
        }
        this.resource= this.$resource('', {}, customActions);


        this.resource.findByNumProg({numProg:  this.numProg})
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.programmeInfo = data;
          });
      },



      methods : {

        downloadCsvFile(url, data, filename) {
          this.$http.post(url, data , {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              }, emulateJSON: true
            }).then( response => {
              var anchor = $('<a></a>');

              anchor.attr({
                href: 'data:attachment/csv;charset=utf-8,' + encodeURI(response.data),
                target: '_blank',
                download: filename
              })[0].click();

          }, error => {

          });
        },

        validateFelixData() {

            this.resource.validateFelixData({numProg:  this.numProg})
              .then(response => {
                return response.json();
              })
              .then(data => {
                if(data.errors !== undefined && data.errors.length >0) {
                  this.downloadCsvFile('app/rest/repartition/downloadFichierFelixError',
                    {numProg: this.programmeInfo.numProg, tmpFilename : data.tmpFilename, filename : data.filename}, data.filename);

                  /*_open('POST', 'app/rest/repartition/downloadFichierFelixError',
                    {numProg: this.programmeInfo.numProg, filename : data.filename}, '_blank');*/
                  this.fichierFelixError = data;
                  this.isToShowErrors = true;
                } else {
                    if(this.modeRepartition == 'REPART_BLANC') {
                        this.downloadCsvFile('app/rest/repartition/downloadFichierFelix', {numProg: this.programmeInfo.numProg}, data.filename);
                        this.$emit('close');
                    } else if(this.modeRepartition == 'MISE_EN_REPART') {
                        this.resource.generateFelixData({numProg : this.programmeInfo.numProg})
                          .then(response => {
                              console.log("Genetation OK");
                              this.$emit('validateMiseEnRepart');
                          })
                          .catch(error => {
                              alert("Erreur technique lors de la Genetation du fichier Felix !! ");
                              this.$emit('close');
                          });

                    }
                }

              });

          var _open = function(verb, url, data) {
            var form = document.createElement('form');
            form.action = url;
            form.method = verb;
            form.target = target || '_self';
            if (data) {
              for (var key in data) {
                var input = document.createElement('textarea');
                input.name = key;
                input.value = typeof data[key] === 'object' ? JSON.stringify(data[key]) : data[key];
                form.appendChild(input);
              }
            }
            document.body.appendChild(form);
            form.submit();
            document.body.removeChild(form);
          };

        }
      },

    computed : {
      isRightMSEREP() {
        return this.hasRight('MSEREP');
      }
    },

      components : {
          modal : Modal
      }

  }
</script>

<style>

  .orangeText {
    color : orange;
    font-size: 13px !important;
    line-height: 1.5;
  }

</style>
