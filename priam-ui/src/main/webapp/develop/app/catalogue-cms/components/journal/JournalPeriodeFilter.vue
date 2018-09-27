<template>
  <div class="encadre">

    <div class="row">
      <div class="col-sm-3">
        <label class="control-label pull-right">Période début d'événement</label>
      </div>
      <div class="col-sm-2">
        <date-picker
          v-model="value.dateDebut"
          date-format="dd/mm/yy"
          :zeroHour="true"
          place-holder="De" >
        </date-picker>
      </div>


      <div class="col-sm-3">
        <label class="control-label pull-right">Période fin d'événement</label>
      </div>
      <div class="col-sm-2">
        <date-picker

          v-model="value.dateFin"
          date-format="dd/mm/yy"
          :zeroHour="false"
          place-holder="A" >
        </date-picker>
      </div>
    </div>
      <!--<div class="col-sm-3">
        <label class="control-label pull-right">Période début d'événement</label>
      </div>
      <div class="col-sm-2">
        <date-picker v-validate.disable="'date_format:DD/MM/YYYY|before:periodeFinEvenement'"
                     data-vv-value-path="innerPeriodeDebutEvenementValue"
                     data-vv-name="periodeDebutEvenement"
                     name="periodeDebutEvenement"
                     :class="{'has-error': errors.has('periodeDebutEvenement') }"
                     v-model="filter.periodeDebutEvenement"
                     date-format="dd/mm/yy"
                     :zeroHour="true"
                     place-holder="De" >

        </date-picker>
      </div>

      <div class="col-sm-3">
        <label class="control-label pull-right">Période fin d'événement</label>
      </div>
      <div class="col-sm-2">
        <date-picker v-validate.disable="'date_format:DD/MM/YYYY'"
                     data-vv-value-path="innerPeriodeFinEvenementValue"
                     data-vv-name="periodeFinEvenement"
                     name="periodeFinEvenement"
                     :class="{'has-error': errors.has('periodeFinEvenement') }"
                     v-model="filter.periodeFinEvenement"
                     date-format="dd/mm/yy"
                     :zeroHour="false"
                     place-holder="A" >

        </date-picker>
      </div>-->
  </div>


</template>

<script>
  import DatePicker from '../../../common/components/ui/DatePicker';
  export default {
    name: "journal-periode-filter",
    data() {
      return {
      }
    },

    props : {
      titre : '',
      value : Object
    },

    components : {
      datePicker : DatePicker
    }
  }
</script>

<style scoped>

  .row{
    margin-top: 5px;
    margin-bottom: 5px;
  }

  /*.encadre {
    border: 1px solid #336699;
    margin-top: 5px;
  }

  #last-row {
    margin-bottom: 10px;
  }*/


</style>
