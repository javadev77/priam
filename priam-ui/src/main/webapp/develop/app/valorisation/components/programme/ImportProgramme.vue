<template>

  <modal>
    <template slot="body">
      <label>Bonjour {{ displayName }}, vous êtes sur le point d'importer un fichier sur PRIAM.<br/>
             Votre action sera tracée dans l'outil.<br/>
             Etes-vous sûr de vouloir continuer?
      </label>

      <label>Parcourir
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload()"/>
      </label>

    </template>
    <template slot="footer">
      <button class="btn btn-default btn-primary" @click="onValider">Valider</button>
      <button class="btn btn-default btn-primary" @click="$emit('cancel')">Annuler</button>
    </template>

  </modal>

</template>

<script>

  import Modal from '../../../common/components/ui/Modal.vue';

  export default {

    data() {

      return {
        file : ''
      }

    },

    methods : {
      handleFileUpload(){
        this.file = this.$refs.file.files[0];
        debugger;
        console.log("File uploaded : " + this.file)
      },

      onValider() {

        this.$emit("validate", this.file);
      }

    },

    computed : {
      displayName() {
        let currentUser = this.$store.getters.getCurrentUser;
        return currentUser && currentUser !== null ? currentUser.displayName : 'Guest';
      }
    },

    components : {
      modal : Modal
    }
  }

</script>
