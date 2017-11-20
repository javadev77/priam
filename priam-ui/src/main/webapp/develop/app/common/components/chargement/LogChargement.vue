<template>
  <modal>
    <template slot="body">
      <label v-if="hasErrors">Le fichier ne peut être chargé à cause de(s) erreur(s) suivante(s) :</label>
      <div style="height:300px; overflow-y:auto;">
        <ul v-if="hasErrors">
          <li v-for="log in logs">
            {{log}}
          </li>
        </ul>
        <ul v-else>
          <div v-for="log in logs">
            {{log}}
          </div>
        </ul>
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

  import Modal from '../ui/Modal.vue';

  export default {

    props : {

      hasErrors : {
        type : Boolean,
        required : true
      },
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
        findLogByFichier : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_COMMON + 'app/rest/chargement/{idFichier}/log'},
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
