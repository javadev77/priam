<template>
  <div class="navbar navbar-default navbar-sm breadcrumb">
    <div class="titre-page">
      <a v-if="backButton" class="btn-back no-select" @click="goBack()" v-shortkey="['f8']" @shortkey="goBack()">
        <span class="glyphicon glyphicon-chevron-left"></span>Retour (F8)
      </a>
      <span>{{ titre1 }} <span class="glyphicon glyphicon-chevron-right"></span>
              {{ titre2 }}
        <template v-if="titre3"><span class="glyphicon glyphicon-chevron-right"></span>{{ titre3 }}</template>
      </span>
    </div>
  </div>
</template>

<script>

  export default {

    props : {
        'titre1' : {
            type : String
        },

      'titre2' : {
        type : String
      },
      'titre3' : {
        type : String
      },

      backButton : {
          type : Boolean,
          default : false
      }


    },

    methods : {
      goBack() {
        this.$router.back();
      }
    }
  }

</script>
