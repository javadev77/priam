<template>



  <div class="row formula-buttons">

    {{debugListSelection}}
    <div v-if="statutProgramme == 'AFFECTE'">
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="rechercher()" :disabled="listSelectionVide">Valider Sélection</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="resetForm()">Editer Sélection</button>
    </div>

    <div v-else-if="statutProgramme == 'EN_COURS'">
      <button class="btn btn-default btn-primary width-140" type="button" @click.prevent="resetForm()">Annuler Sélection</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="rechercher()">Valider Sélection</button>
      <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="resetForm()">Editer Sélection</button>
    </div>

  </div>
</template>

<script>
  export default {

      props : {
        statutProgramme : String,
        listSelectionVide : Boolean,
        debugListSelection : Number
      }

  }
</script>

<style>
  .width-140{
    width: 140px !important;
  }
</style>
