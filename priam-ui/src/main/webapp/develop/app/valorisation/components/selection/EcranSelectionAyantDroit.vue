<template>

  <div>
    <priam-navbar titre1="Programme" titre2="Liste programmes" titre3="Séléction" :backButton="true"></priam-navbar>
    <div class="container-fluid sacem-formula">

      <!--En tete Panel-->
      <div class="panel panel-default">
        <div class="panel-heading">
          <h5 class="panel-title"  @click="isCollapsed = !isCollapsed">
            <a>Programme</a>
            <span class="pull-left collapsible-icon"><img src="static/images/iconescontextes/btninformation.gif" alt=""/></span>
            <span class="pull-right fa" :class="{'fui-triangle-up' : isCollapsed,  'fui-triangle-down' : !isCollapsed}"></span>
          </h5>
        </div>

        <!--Body Panel-->
        <div class="panel-collapse" :class="{collapse : isCollapsed}">
          <app-programme-info :programmeInfo="programmeInfo">
          </app-programme-info>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

  import PriamNavbar from '../../../common/components/ui/priam-navbar.vue';
  import ProgrammeInfo from '../../../common/components/programme/ProgrammeInfo.vue';

  export default {


    data() {

      return {
        isCollapsed : false,
        programmeInfo : {

        }
      }

    },


    components : {
      'priam-navbar' : PriamNavbar,
      'app-programme-info' : ProgrammeInfo
    }

  }

</script>

<style>

</style>
