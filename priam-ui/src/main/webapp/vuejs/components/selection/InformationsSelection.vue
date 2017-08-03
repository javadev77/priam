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
          <span class="pull-left blueText">Durée sélection</span>
        </div>
        <div class="col-sm-3">
          {{ dureeSelection.duree }}
        </div>


      </div>
    </div>
  </div>
</template>

<script>


  export default {


      props : {
        dureeSelection: Object
      }

  }
</script>
