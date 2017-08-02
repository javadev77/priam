<template>
  <div class="container-fluid sacem-formula">
    <div class="panel-body">
      <div class="row">

        <div class="col-sm-3">
          <span class="pull-left blueText">Oeuvres sélectionées - Auto</span>
        </div>
        <div class="col-sm-3">
          {{ this.ouvresSelectioneesAuto }}
        </div>

        <div class="col-sm-3">
          <span class="pull-left blueText">Oeuvres sélectionées - Manuel</span>
        </div>
        <div class="col-sm-3">
          {{ this.ouvresSelectioneesManuel }}
        </div>



        <div class="col-sm-3">
          <span class="pull-left blueText">Durée sélection</span>
        </div>
        <div class="col-sm-3">
          {{ this.dureeSelection }}
        </div>


      </div>
    </div>
  </div>
</template>

<script>


  export default {


      props : {
           ouvresSelectioneesAuto: 0,
           ouvresSelectioneesManuel: 0,
           dureeSelection:0
      }

  }
</script>
