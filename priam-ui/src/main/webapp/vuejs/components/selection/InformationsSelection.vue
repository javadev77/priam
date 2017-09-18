<template>
  <div class="container-fluid sacem-formula">
    <div class="panel-body">
      <div class="row">


        <div class="form-group col-xs-8">
          <label class="col-xs-15 control-label blueText text-right">Oeuvres sélectionées - Auto</label>
          <div class="col-xs-9 control-label">
            {{ dureeSelection.auto }}
          </div>
        </div>

        <div class="form-group col-xs-8">
          <label class="col-xs-15 control-label blueText text-right">Oeuvres sélectionées - Manuel</label>
          <div class="col-xs-9 control-label">
            {{ dureeSelection.manuel }}
          </div>
        </div>

        <div class="form-group col-xs-8">
          <label class="col-xs-15 control-label blueText text-right" v-if="typeUtilisation == 'CPRIVSONRD'">Durée sélection</label>
          <label class="col-xs-15 control-label blueText text-right" v-else-if="typeUtilisation == 'CPRIVSONPH'">Quantité sélection</label>

          <div class="col-xs-9 control-label" v-if="typeUtilisation == 'CPRIVSONRD'">
            {{ dureeFormattee }}
          </div>
          <div class="col-xs-9 control-label" v-else-if="typeUtilisation == 'CPRIVSONPH'">
            {{ dureeSelection.duree }}
          </div>
         </div>
      </div>
    </div>
  </div>
</template>

<script>


  export default {

      props : {
        dureeSelection: Object,
        typeUtilisation : String
      },

      computed : {
          dureeFormattee() {

            let jours = Math.floor( this.dureeSelection.duree / 86400);
            let reste = this.dureeSelection.duree % 86400;
            let hours = Math.floor( reste / 3600);
            reste = reste % 3600;
            let minutes = Math.floor(reste / 60);
            let seconds = reste % 60;


            return ((jours < 10) ? '0'+jours : jours) + 'j ' +((hours < 10) ? '0'+hours : hours)+"h "+((minutes < 10) ? '0' + minutes: minutes)+"m "+ ((seconds < 10) ? '0'+seconds : seconds) + "s";
          }
      }


  }
</script>
