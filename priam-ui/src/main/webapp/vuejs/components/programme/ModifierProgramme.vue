<template>
  <div class="container-fluid sacem-formula">

    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Modifier un programme</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
        </h5>


      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <form class="form-horizontal" role="form">
            <div class="row">
              <div class="col-sm-2">
                {{ data }}
              </div>
              <div class="col-sm-4">
              </div>
              <div class="col-sm-2">
              </div>
              <div class="col-sm-5">
              </div>

            </div>

          </form>
        </div>
      </div>
    </div>

    <div class="form-horizontal">
      <div class="col-sm-2">
      </div>
      <div class="col-sm-12">
      </div>
    </div>

    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click="emitEvent('cancel')">Annuler</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click="emitEvent('valider')">Valider</button>
    </div>
  </div>

</template>

<script>


  export default {

      data() {

          return {
              data : 'Hello'
          }
      },

      methods : {
          emitEvent(event) {
              console.log("event emitted = " + event);
              this.$emit(event);

          }
      }

  }

</script>

<style>

</style>
