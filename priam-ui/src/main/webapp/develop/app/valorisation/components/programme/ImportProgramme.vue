<template>
  <div>
    <modal>
      <template slot="body">

        <label class="pull-right">Répartition à :  <b>{{ programme.typeRepart }}</b></label>
        <br/>
        <br/>

        <label>Bonjour {{ displayName }}, , vous allez réaliser un import dans PRIAM,
               les données que vous avez ajoutées, <br/>modifiées ou supprimées sont réalisées sous votre responsabilité.
               Une historisation est conservée pour assurer une<br/> traçabilité des données. Votre action sera tracée dans l'outil.<br/><br/>

               Etes-vous sûr de vouloir continuer ?
        </label>
        <br/>
        <br/>
        <label style="display: block;text-align: center">Parcourir <input style="display: inline" type="file" id="file" ref="file" v-on:change="handleFileUpload()"/></label>

        <br/>
        <br/>

        <label class="checkbox checked disabled" >
          <input type="checkbox"  value="">Remplacer les données du programme avec celles du fichier chargé
          <span class="icons"><span class="fui-checkbox-checked"></span></span>
        </label>
      </template>
      <template slot="footer">
        <button class="btn btn-default btn-primary" @click="onValider">Valider</button>
        <button class="btn btn-default btn-primary" @click="$emit('cancel')">Annuler</button>
      </template>

    </modal>

    <modal v-if="showPopupOuiNon">
      <label class="homer-prompt-q control-label" slot="body">
        Attention, vous êtes sur le point d'écraser les données du programme. Etes-vous sûr de vouloir continuer ?
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showPopupOuiNon = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="onYesValidate">Oui</button>
      </template>
    </modal>

  </div>

</template>

<script>

  import Modal from '../../../common/components/ui/Modal.vue';

  export default {

    props : {

      programme :  Object


    },

    data() {

      return {
        showPopupOuiNon : false,
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

        this.showPopupOuiNon = true;
        //this.$emit("validate", this.file);
      },

      onYesValidate() {
        this.showPopupOuiNon = false;
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
