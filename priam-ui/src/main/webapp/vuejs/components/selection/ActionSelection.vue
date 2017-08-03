<template>



  <div class="row formula-buttons" v-if="statutProgramme != 'MIS_EN_REPART' && statutProgramme != 'REPARTI'">

    <div v-if="statutProgramme == 'AFFECTE'">
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="valider()" :disabled="listSelectionVide">Valider Sélection</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="resetForm()">Editer Sélection</button>
    </div>

    <div v-else-if="statutProgramme == 'EN_COURS'">
      <button class="btn btn-default btn-primary width-140" type="button" @click.prevent="resetForm()">Annuler Sélection</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="valider()">Valider Sélection</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="resetForm()">Editer Sélection</button>
    </div>

    <div v-else-if="statutProgramme == 'VALIDE'">
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="invalider()">Invalider</button>
    </div>
  </div>
</template>

<script>
  export default {

      props : {
        statutProgramme : String,
        listSelectionVide : Boolean,
        valider : Function,
        invalider : Function
      }

  }
</script>

<style>
  .width-140{
    width: 140px !important;
  }
</style>
