<template>


  <div>


    <div class="row formula-buttons" v-if="programmeInfo.statut == 'MIS_EN_REPART' || programmeInfo.statut == 'REPARTI'">
      <span class="pull-right">
        {{ userValidation }} {{ programmeInfo.dateValidation | dateAffectation}}
      </span>
    </div>
    <div class="row formula-buttons" v-if="programmeInfo.statut != 'MIS_EN_REPART' && programmeInfo.statut != 'REPARTI'">

      <div class="mask" v-if="inProcess" >
        <div class="center-div">
          <div class="spinner">
            <div class="rect1"></div>
            <div class="rect2"></div>
            <div class="rect3"></div>
            <div class="rect4"></div>
            <div class="rect5"></div>
          </div>
        </div>
      </div>

      <div v-if="!edition">


        <div v-if="programmeInfo.statut == 'AFFECTE'">

          <div class="pull-right" :title="listSelectionVide ? 'Il faut sélectionner au moins une oeuvre' : ''">
            <button class="btn btn-default btn-primary width-140" type="button" @click.prevent="valider()" :disabled="isLoadingDuree || inProcess || listSelectionVide || !isRightVLDSEL">Valider Sélection</button>
          </div>

          <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="editer()" :disabled="isLoadingDuree || inProcess || !isRightEDTSEL">Modifier Sélection</button>
        </div>

        <div v-else-if="programmeInfo.statut == 'EN_COURS'">
          <button class="btn btn-default btn-primary width-140" type="button" @click.prevent="annulerSelection()" :disabled="isLoadingDuree || inProcess || !isRightCLDSEL">Annuler Sélection</button>
          <div class="pull-right" :title="listSelectionVide ? 'Il faut sélectionner au moins une oeuvre' : ''">
            <button class="btn btn-default btn-primary width-140" type="button" @click.prevent="valider()" :disabled="isLoadingDuree || inProcess || listSelectionVide || !isRightVLDSEL">Valider Sélection</button>
          </div>
          <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="editer()" :disabled="isLoadingDuree || inProcess || !isRightEDTSEL">Modifier Sélection</button>
        </div>

        <div v-else-if="programmeInfo.statut == 'VALIDE'">
          <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="invalider()" :disabled="isLoadingDuree || !isRightINVSEL">Invalider</button>
          <span class="pull-right">
             {{ userValidation }} {{ programmeInfo.dateValidation | dateAffectation}}
          </span>
        </div>
      </div>

      <div v-else-if="edition">
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="enregistrerEdition()" :disabled="isLoadingDuree || inProcess">Enregistrer</button>
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="annulerEdition()" :disabled="isLoadingDuree || inProcess">Annuler</button>
      </div>

    </div>


  </div>

</template>

<script>

  import programmeMixins from '../../../common/mixins/programmeMixin';

  export default {

    mixins: [programmeMixins],

      props : {
        programmeInfo : Object,
        listSelectionVide : Boolean,
        valider : Function,
        invalider : Function,
        editer : Function,
        enregistrerEdition : Function,
        annulerEdition : Function,
        edition : Boolean,
        inProcess : Boolean,
        annulerSelection : Function,
        isLoadingDuree : Boolean
      },

     computed :{
       userValidation () {
         return 'Validé par ' + this.programmeInfo.userValidation;
       },

       isRightEDTSEL() {
         return this.hasRight('EDTSEL');
       },

       isRightINVSEL() {
         return this.hasRight('INVSEL');
       },

       isRightCLDSEL() {
         return this.hasRight('CLDSEL');
       },

       isRightVLDSEL() {
         return this.hasRight('VLDSEL');
       }

     }

  }
</script>

<style>
  .width-140{
    width: 140px !important;
  }

  .spinner {
    float:  left;
    width: 50px;
    height: 40px;
    text-align: center;
    font-size: 10px;
  }

  .spinner > div {
    background-color: #333;
    height: 100%;
    width: 6px;
    display: inline-block;

    -webkit-animation: sk-stretchdelay 1.2s infinite ease-in-out;
    animation: sk-stretchdelay 1.2s infinite ease-in-out;
  }

  .spinner .rect2 {
    -webkit-animation-delay: -1.1s;
    animation-delay: -1.1s;
  }

  .spinner .rect3 {
    -webkit-animation-delay: -1.0s;
    animation-delay: -1.0s;
  }

  .spinner .rect4 {
    -webkit-animation-delay: -0.9s;
    animation-delay: -0.9s;
  }

  .spinner .rect5 {
    -webkit-animation-delay: -0.8s;
    animation-delay: -0.8s;
  }

  @-webkit-keyframes sk-stretchdelay {
    0%, 40%, 100% { -webkit-transform: scaleY(0.4) }
    20% { -webkit-transform: scaleY(1.0) }
  }

  @keyframes sk-stretchdelay {
    0%, 40%, 100% {
      transform: scaleY(0.4);
      -webkit-transform: scaleY(0.4);
    }  20% {
         transform: scaleY(1.0);
         -webkit-transform: scaleY(1.0);
       }
  }

  .mask {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1050;
    outline: 0;
    background-color: black;
    opacity: 0.5;
  }

  .mask .center-div {
    width: 0%;
    margin: 0 auto;
    margin-top: 50vh; /* poussé de la moitié de hauteur de viewport */
    transform: translateY(-50%);
  }
</style>
