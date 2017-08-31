<template>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title">
        <a>Détail Oeuvre</a>
        <span class="pull-right fa fui-triangle-down"></span>
      </h5>
    </div>
    <div class="panel-collapse">

      <div class="panel-body">
        <form class="form-horizontal" role="form">
          <div class="row">

          <div class="form-group col-md-4">
            <label class="col-md-5 control-label blueText text-right">Titre</label>
            <div class="col-md-19 control-label">
              {{ oeuvre.titre }}
            </div>
          </div>

          <div class="form-group col-md-4">
            <label class="col-md-9 control-label blueText text-right">IDE12</label>
            <div class="col-md-15 control-label">
              {{ oeuvre.ide12 }}
            </div>
          </div>


          <div class="form-group col-md-7">
            <label class="col-md-6 control-label blueText text-right">Utilisateur</label>
            <div class="col-md-18">
              <select2 class="form-control" :options="utilisateurOptions" v-model="utilisateur" :searchable="true">
              </select2>
            </div>
          </div>


          <div class="form-group col-md-5">
            <label class="col-md-6 control-label blueText text-right">Durée</label>
            <div class="col-md-18">
              <input type="text" v-model="duree">
            </div>
          </div>
        </div>
        </form>
      </div>
    </div>

  </div>
</template>

<script>

  import Select2 from '../../common/Select2.vue';

  export default {

      props: {

          oeuvre : Object

      },

     data() {

          return {
              utilisateur : '',
              utilisateursOptios : [],
              duree : ''
          }
     },

    mounted() {
        //this.oeuvre = this.$store.getters.selectedOeuvre;
    },

    components : {
        select2 :Select2
    }

  }
</script>
