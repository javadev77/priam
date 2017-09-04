<template>
  <modal>
    <template slot="body">
      <label>Le fichier de répartition contient les erreurs suivantes :</label>
      <div style="height:300px; overflow-y:scroll;">
        <label v-for="log in logs">
          {{log}}
        </label>
      </div>
    </template>
    <template slot="footer">
      <div class="text-center">
        <button class="btn btn-default btn-primary" @click="$emit('close-log')">Fermer</button>
      </div>

    </template>
  </modal>
</template>

<script>

  import Modal from '../common/Modal.vue';

  export default {

    props : {

      idFichier : {
        type : Number,
        required : true
      }
    },

    components : {
      modal : Modal
    },


    data() {
      return {
        logs : []
      }
    },

    created() {

      const customActions = {
        findLogByFichier : {method : 'GET', url : 'app/rest/chargement/{idFichier}/log'},
      }
      this.resource= this.$resource('', {}, customActions);


      this.resource.findLogByFichier({idFichier:  this.idFichier})
        .then(response => {
          return response.json();
        })
        .then(data => {
          this.logs = data;
        });
    },
  }
</script>
