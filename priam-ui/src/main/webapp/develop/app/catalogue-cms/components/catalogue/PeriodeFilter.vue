<template>
  <div class="encadre">

    <label class="control-label ">{{ titre }}</label>

    <div class="row">
      <div class="col-md-10">
        <label class="control-label pull-right">Date de début</label>
      </div>
      <div class="col-md-11">
        <date-picker
          v-model="value.dateDebut"
          date-format="dd/mm/yy"
          :zeroHour="true"
          place-holder="De" >
        </date-picker>
      </div>
    </div>
    <div class="row" id="last-row">
      <div class="col-md-10">
        <label class="control-label pull-right">Date de fin</label>
      </div>
      <div class="col-md-11">
        <date-picker

          v-model="value.dateFin"
          date-format="dd/mm/yy"
          :zeroHour="false"
          place-holder="A" >
        </date-picker>
      </div>
    </div>
  </div>


</template>

<script>
  import DatePicker from '../../../common/components/ui/DatePicker';
  export default {
    name: "periode-filter",
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

  .encadre {
    border: 1px solid #336699;
    margin-top: 5px;
  }

  #last-row {
    margin-bottom: 10px;
  }


</style>
