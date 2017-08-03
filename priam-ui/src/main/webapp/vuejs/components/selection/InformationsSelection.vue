<template>
  <div class="container-fluid sacem-formula">
    <div class="panel-body">
      <div class="row">

        <div class="col-sm-3">
          <span class="pull-left blueText">Oeuvres sélectionées - Auto</span>
        </div>
        <div class="col-sm-3">
          {{ dureeSelection.auto }}
        </div>

        <div class="col-sm-3">
          <span class="pull-left blueText">Oeuvres sélectionées - Manuel</span>
        </div>
        <div class="col-sm-3">
          {{ dureeSelection.manuel }}
        </div>



        <div class="col-sm-3">
          <span class="pull-left blueText" v-if="typeUtilisation == 'CPRIVSONRD'">Durée sélection</span>
          <span class="pull-left blueText" v-else-if="typeUtilisation == 'CPRIVSONPH'">Quantité sélection</span>
        </div>
        <div class="col-sm-3" v-if="typeUtilisation == 'CPRIVSONRD'">
          {{ dureeFormattee }}
        </div>
        <div class="col-sm-3" v-else-if="typeUtilisation == 'CPRIVSONPH'">
          {{ dureeSelection.duree }}
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

            let hours = Math.floor( this.dureeSelection.duree / 3600);
            let minutes = Math.floor(this.dureeSelection.duree / 60);
            let seconds = this.dureeSelection.duree % 60;

            return ((hours < 10) ? '0'+hours : hours)+":"+((minutes < 10) ? '0' + minutes: minutes)+":"+ ((seconds < 10) ? '0'+seconds : seconds);
          }
      }


  }
</script>
