<template>
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
        <div class="panel-body">
          <div class="row">

            <div class="col-sm-2">
              <span class="pull-right">N° programme</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.numProg }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Statut</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ getStatutByCode(programmeInfo.statut) }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Famille</span>
            </div>
            <div class="col-sm-3">
              <strong>{{ getFamilleByCode(programmeInfo.famille) }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Nb fichiers affectés</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.fichiers }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Créé par</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.usercre }}</strong>
            </div>
          </div>

          <br/>
          <!-- 2 eme ligne -->
          <div class="row">

            <div class="col-sm-2">
              <span class="pull-right">Nom</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.nom }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Rion théorique</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.rionTheorique }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Type d'utilisation</span>
            </div>
            <div class="col-sm-3">
              <strong>{{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Mode répartition</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ getModeRepartitionByCode(programmeInfo.typeRepart) }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Date création</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.dateCreation }}</strong>
            </div>
          </div>
        </div>
      </div>

    </div>

  </div>

</template>

<script>

  import chargementMixins from '../../mixins/chargementMixin'

  export default {

      mixins: [chargementMixins],

      data() {
          return {
            isCollapsed : false,
            resource: {},
            programmeInfo : {}
          }
      },


      created() {
          console.log("router params : " + this.$route.params.numProg)
          const customActions = {
              findByNumProg : {method : 'GET', url : 'app/rest/programme/numProg/{numProg}'}
          }
          this.resource= this.$resource('', {}, customActions);


          this.resource.findByNumProg({numProg:  this.$route.params.numProg})
            .then(response => {
                return response.json();
            })
            .then(data => {
              this.programmeInfo = data;
          });
      }


  }
</script>
