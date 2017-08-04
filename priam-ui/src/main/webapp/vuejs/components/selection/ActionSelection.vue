<template>



  <div class="row formula-buttons" v-if="statutProgramme != 'MIS_EN_REPART' && statutProgramme != 'REPARTI'">

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
</style>
