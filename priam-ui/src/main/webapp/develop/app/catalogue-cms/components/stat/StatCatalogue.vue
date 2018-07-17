<template>

  <div>
    <priam-navbar titre1="Catalogue Musique des Sonorisations"  titre2="Statistiques"  :backButton="false"></priam-navbar>
  </div>
</template>

<script>

  import PriamNavbar from  './../../../common/components/ui/priam-navbar.vue';

  export default {
    components : {
      'priam-navbar' : PriamNavbar
    }
  }

</script>
