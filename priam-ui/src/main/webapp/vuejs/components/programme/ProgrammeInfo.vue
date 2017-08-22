<template>
  <div class="panel-body">
    <div class="row">

      <div class="col-sm-2">
        <span class="pull-right blueText">N° programme</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.numProg }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Statut</span>
      </div>
      <div class="col-sm-2">
        {{ getStatutProgrammeByCode(programmeInfo.statut) !== undefined ? getStatutProgrammeByCode(programmeInfo.statut).libelle : '' }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Famille</span>
      </div>
      <div class="col-sm-3">
        {{ getFamilleByCode(programmeInfo.famille) !== undefined ? getFamilleByCode(programmeInfo.famille).value : '' }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Nb fichiers affectés</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.fichiers }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Date début</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.dateDbtPrg }}
      </div>


    </div>

    <br/>
    <!-- 2 eme ligne -->
    <div class="row">
      <div class="col-sm-2">
        <span class="pull-right blueText">Nom</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.nom }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Rion statuaire</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.rionTheorique }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Type d'utilisation</span>
      </div>
      <div class="col-sm-3">
        {{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? getTypeUtilisationByCode(programmeInfo.typeUtilisation).value : '' }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Mode répartition</span>
      </div>
      <div class="col-sm-2">
        {{ getModeRepartitionByCode(programmeInfo.typeRepart) !== undefined ?  getModeRepartitionByCode(programmeInfo.typeRepart).value : '' }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Date fin</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.dateFinPrg }}
      </div>
    </div>

    <br/>
    <!-- 3 eme ligne -->
    <div class="row">

      <div class="col-sm-2">
        <span class="pull-right blueText">Territoire</span>
      </div>
      <div class="col-sm-2">
        {{ getTerritoireByCode(programmeInfo.cdeTer) !== undefined ? getTerritoireByCode(programmeInfo.cdeTer).value : '' }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Date création</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.dateCreation }}
      </div>

      <div class="col-sm-2">
        <span class="pull-right blueText">Créé par</span>
      </div>
      <div class="col-sm-2">
        {{ programmeInfo.usercre }}
      </div>
    </div>
  </div>
</template>

<script>

  import ChargementMixin from '../../mixins/chargementMixin.js';
  import Vue from 'vue';

  Vue.mixin(ChargementMixin);

  export default {
    props : {
      programmeInfo: Object
    }
  }
</script>

