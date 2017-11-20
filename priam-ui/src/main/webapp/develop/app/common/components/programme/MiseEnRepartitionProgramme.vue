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
              > Mise en répartition
            <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
          </label>
        </div>
      </div>


       <div v-if="startDownload">
         {{ downloadFileText }}
       </div>
    </template>
    <template slot="body" v-else>
      <label>Le fichier de répartition contient les erreurs suivantes :</label>
      <div style="height:300px; overflow-y:scroll;">
        <label v-for="error in fichierFelixErrors">
          {{ error.log }}
        </label>

      </div>

    </template>
    <template slot="footer" v-if="!isToShowErrors">
      <button class="btn btn-default btn-primary pull-right yes" :disabled="startDownload" @click.prevent="validateFelixData">Valider</button>
      <button class="btn btn-default btn-primary pull-right no" :disabled="startDownload" @click.prevent="$emit('cancel')">Annuler</button>
    </template>
    <template slot="footer" v-else>
      <button class="btn btn-default btn-primary" @click="$emit('close')">Fermer</button>
    </template>
  </modal>
</template>

<script>

  import Modal from '../ui/Modal.vue';
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
          fichierFelixErrors : [],
          modeRepartition : 'REPART_BLANC',
          isToShowErrors : false,
          startDownload : false,
          downloadFileText : '',
          interval: null
        }
      },

      created() {
        console.log("numProg = " + this.numProg);

        const customActions = {
          findByNumProg : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON +  'app/rest/programme/numProg/{numProg}'},
          validateFelixData : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/validateFelixData/{numProg}'},
          generateFelixData : {method : 'POST', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/generateFelixData'},
          checkIfDone : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/repartition/fichierfelix/{numProg}'}
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

          var _open = function(verb, url, data, target) {
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

          _open('POST', url, data, '_blank');
          /*this.$http.post(url, data , {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              }, emulateJSON: true
            }).then( response => {
              this.downloadFileText = 'Fichier Telecharge OK.';
              var anchor = $('<a></a>');
              //console.log(encodeURI(response.data))
             var hrefData = 'data:text/csv;charset=utf-8,' + encodeURIComponent(response.data);
              anchor.attr({
                href: hrefData,
                target: '_blank',
                download: filename
              })[0].click();

          }, error => {

          });*/
        },

        validateFelixData() {
            this.$emit('validate', this.modeRepartition);
            /*if(this.modeRepartition == 'REPART_BLANC') {

            } else if(this.modeRepartition == 'MISE_EN_REPART') {
              this.$emit('validateRepartABlan');
            }*/

            /*this.resource.validateFelixData({numProg:  this.numProg})
              .then(response => {
                return response.json();
              })
              .then(data => {
                this.startDownload = true;
                var self = this;
                this.downloadFileText = 'En cours de traitement ....';
                this.interval = setInterval(function () {
                      self.resource.checkIfDone({numProg:  self.numProg})
                        .then(response => {
                          return response.json();
                        })
                        .then(data => {
                            var fichierFelix = data;
                            if(fichierFelix !== undefined && fichierFelix.statut == 'GENERE') {
                              self.downloadFileText = 'Traitement OK.';
                              self.startDownload = false;
                              clearInterval(self.interval);
                              if(fichierFelix.logs !== undefined && fichierFelix.logs.length > 0) {
                                self.downloadCsvFile('app/rest/repartition/downloadFichierFelixError',
                                  {numProg: self.programmeInfo.numProg, tmpFilename : fichierFelix.nomFichier, filename : fichierFelix.nomFichier},
                                  fichierFelix.nomFichier);
                                self.fichierFelixErrors = fichierFelix.logs;
                                self.isToShowErrors = true;
                              } else {
                                if(self.modeRepartition == 'REPART_BLANC') {
                                  self.downloadCsvFile('app/rest/repartition/downloadFichierFelix', {numProg: self.programmeInfo.numProg}, fichierFelix.nomFichier);
                                  self.$emit('close');
                                } else if(self.modeRepartition == 'MISE_EN_REPART') {
                                  self.resource.generateFelixData({numProg : self.programmeInfo.numProg})
                                    .then(response => {
                                      console.log("Genetation OK");
                                      self.$emit('validateMiseEnRepart');
                                    })
                                    .catch(error => {
                                      alert("Erreur technique lors de la Genetation du fichier Felix !! ");
                                      self.$emit('close');
                                    });

                                }
                              }
                            }

                        });
                  }
                    ,
                  1000 * 5);


              });*/



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
