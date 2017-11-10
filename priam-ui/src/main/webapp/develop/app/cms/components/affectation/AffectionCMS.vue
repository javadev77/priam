<template>

  <div class="container-fluid">

    <div class="navbar navbar-default navbar-sm breadcrumb">
      <div class="titre-page">
        <a class="btn-back no-select" @click="goBack()" v-shortkey="['f8']" @shortkey="goBack()" >
          <span class="glyphicon glyphicon-chevron-left"></span>Retour (F8)
        </a>
        <span>Programme <span class="glyphicon glyphicon-chevron-right"></span>
              Liste programmes <span class="glyphicon glyphicon-chevron-right"></span>
              Affectation CMS
            </span>
      </div>
    </div>


  </div>

</template>

<script>

  export default {

      methods : {
        goBack() {
          this.$router.back();
        }
      }
  }
</script>
