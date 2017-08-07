<template>



  <div class="row formula-buttons" v-if="statutProgramme != 'MIS_EN_REPART' && statutProgramme != 'REPARTI'">


    <div class="spinner" v-if="inProcess">
      <div class="rect1"></div>
      <div class="rect2"></div>
      <div class="rect3"></div>
      <div class="rect4"></div>
      <div class="rect5"></div>
    </div>

    <div v-if="!edition">
      <div v-if="statutProgramme == 'AFFECTE'">
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="valider()" :disabled="inProcess || listSelectionVide">Valider Sélection</button>
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="editer()" :disabled="inProcess">Editer Sélection</button>
      </div>

      <div v-else-if="statutProgramme == 'EN_COURS'">
        <button class="btn btn-default btn-primary width-140" type="button" @click.prevent="annulerSelection()" :disabled="inProcess">Annuler Sélection</button>
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="valider()" :disabled="inProcess || listSelectionVide">Valider Sélection</button>
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="editer()" :disabled="inProcess">Editer Sélection</button>
      </div>

      <div v-else-if="statutProgramme == 'VALIDE'">
        <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="invalider()">Invalider</button>
      </div>
    </div>

    <div v-else-if="edition">
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="enregistrerEdition()" :disabled="inProcess">Enregistrer</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="annulerEdition()" :disabled="inProcess">Annuler</button>
    </div>

  </div>
</template>

<script>
  export default {

      props : {
        statutProgramme : String,
        listSelectionVide : Boolean,
        valider : Function,
        invalider : Function,
        editer : Function,
        enregistrerEdition : Function,
        annulerEdition : Function,
        edition : Boolean,
        inProcess : Boolean,
        annulerSelection : Function
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
</style>
