<template>
  <div class="panel-body">


    <div class="row">

      <div class="form-group col-md-1"></div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">N° programme</label>
        <div class="col-md-15 control-label">
          {{ programmeInfo.numProg }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Statut</label>
        <div class="col-md-15 control-label">
          {{ getStatutProgrammeByCode(programmeInfo.statut) !== undefined ? getStatutProgrammeByCode(programmeInfo.statut).libelle : '' }}
        </div>
      </div>

      <div class="form-group col-md-6">
        <label class="col-md-9 control-label blueText text-right">Famille</label>
        <div class="col-md-15 control-label">
          {{ getFamilleByCode(programmeInfo.famille) !== undefined ? getFamilleByCode(programmeInfo.famille).value : '' }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-12 control-label blueText text-right">Nb fichiers affectés</label>
        <div class="col-md-12 control-label">
          {{ programmeInfo.fichiers }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Date début</label>
        <div class="col-md-15 control-label">
          {{ programmeInfo.dateDbtPrg }}
        </div>
      </div>

    </div>

    <!-- 2 eme ligne -->
    <div class="row">

      <div class="form-group col-md-1"></div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Nom</label>
        <div class="col-md-15 control-label">
          {{ programmeInfo.nom }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Rion statutaire</label>
        <div class="col-md-15 control-label">
          {{ getLibelleRionById(programmeInfo.rionTheorique) }}
        </div>
      </div>

      <div class="form-group col-md-6">
        <label class="col-md-9 control-label blueText text-right">Type d'utilisation</label>
        <div class="col-md-15 control-label">
          {{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? getTypeUtilisationByCode(programmeInfo.typeUtilisation).value : '' }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-12 control-label blueText text-right">Mode répartition</label>
        <div class="col-md-12 control-label">
          {{ getModeRepartitionByCode(programmeInfo.typeRepart) !== undefined ?  getModeRepartitionByCode(programmeInfo.typeRepart).value : '' }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Date fin</label>
        <div class="col-md-15 control-label">
          {{ programmeInfo.dateFinPrg }}
        </div>
      </div>

    </div>

    <!-- 3 eme ligne -->
    <div class="row">

      <div class="form-group col-md-1"></div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Territoire</label>
        <div class="col-md-15 control-label">
          {{ getTerritoireByCode(programmeInfo.cdeTer) !== undefined ? getTerritoireByCode(programmeInfo.cdeTer).value : '' }}
        </div>
      </div>

      <div class="form-group col-md-4">
        <label class="col-md-9 control-label blueText text-right">Date création</label>
        <div class="col-md-15 control-label">
          {{ programmeInfo.dateCreation }}
        </div>
      </div>

      <div class="form-group col-md-6">
        <label class="col-md-9 control-label blueText text-right">Créé par</label>
        <div class="col-md-15 control-label">
          {{ programmeInfo.usercre }}
        </div>
      </div>


      <slot name="extra-info"></slot>

    </div>

  </div>
</template>

<script>

  import ChargementMixin from '../../mixins/chargementMixin.js';

  export default {
    mixins : [ChargementMixin],

    props : {
      programmeInfo: Object
    }
  }
</script>

