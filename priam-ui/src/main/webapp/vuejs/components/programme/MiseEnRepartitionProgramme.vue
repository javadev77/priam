<template>
  <modal>
    <template slot="body">
    <div class="row">

      <div class="col-sm-4">
        <span class="pull-right blueText">N° programme</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.numProg }}
      </div>


      <div class="col-sm-4">
        <span class="pull-right blueText">Famille</span>
      </div>
      <div class="col-sm-4">
        {{ getFamilleByCode(programmeInfo.famille) !== undefined ? getFamilleByCode(programmeInfo.famille).value : '' }}
      </div>

      <div class="col-sm-9">
        <span class="pull-right blueText">Rion théorique</span>
      </div>
      <div class="col-sm-1">
        {{ programmeInfo.rionTheorique }}
      </div>

    </div>

    <br/>
    <!-- 2 eme ligne -->
    <div class="row">

      <div class="col-sm-1">
        <span class="pull-right blueText">Nom</span>
      </div>
      <div class="col-sm-4">
        {{ programmeInfo.nom }}
      </div>


      <div class="col-sm-7">
        <span class="pull-right blueText">Type d'utilisation</span>
      </div>
      <div class="col-sm-8">
        {{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? getTypeUtilisationByCode(programmeInfo.typeUtilisation).value : '' }}
      </div>
    </div>

      <br/>
      <!-- 3 eme ligne -->
      <div class="row">
        <div class="col-sm-8">
          <label class="radio radio-inline" :class="{'checked' : modeRepartition == 'REPART_BLANC' }">
            <input
              type="radio"
              value="REPART_BLANC"
              v-model="modeRepartition"
              > Répartition à blanc
            <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
          </label>
        </div>
        <div class="col-sm-8">
          <label class="radio radio-inline" :class="{'checked' : modeRepartition == 'MISE_EN_REPART' }">
            <input
              type="radio"
              value="MISE_EN_REPART"
              v-model="modeRepartition"
              > Mise en répatition
            <span class="icons"><span class="first-icon fui-radio-unchecked"></span><span class="second-icon fui-radio-checked"></span></span>
          </label>
        </div>
      </div>
    </template>
    <template slot="footer">
      <button class="btn btn-default btn-primary pull-right yes" @click="$emit('validate')">Valider</button>
      <button class="btn btn-default btn-primary pull-right no" @click="$emit('cancel')">Annuler</button>
    </template>
  </modal>
</template>

<script>

  import Modal from '../common/Modal.vue';
  import chargementMixins from '../../mixins/chargementMixin';
  export default {
      mixins: [chargementMixins],

      props : {

        numProg : {
            type : String,
            required : true
        }
      },

      created() {
        console.log("numProg = " + this.numProg);

        const customActions = {
          findByNumProg : {method : 'GET', url : 'app/rest/programme/numProg/{numProg}'},
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

      data() {
          return {
            programmeInfo : {},
            modeRepartition : 'REPART_BLANC'
          }
      },

      components : {
          modal : Modal
      }

  }
</script>

<style>

</style>
