<template>
  <div class="container-fluid sacem-formula">
    <div class="panel-body">
      <div class="row">


        <div class="form-group col-xs-8">
          <label class="col-xs-15 control-label blueText text-right">Oeuvres sélectionées - Auto</label>
          <div class="col-xs-9 control-label" v-if="!dataLoadingDuree">
            {{ dureeSelection.auto | numberFormat }}
          </div>
          <div class="col-xs-9" v-else>
            <div class="spinner" v-if="dataLoadingDuree">
              <div class="rect1"></div>
              <div class="rect2"></div>
              <div class="rect3"></div>
            </div>
          </div>
        </div>

        <div class="form-group col-xs-8">
          <label class="col-xs-15 control-label blueText text-right">Oeuvres sélectionées - Manuel</label>
          <div class="col-xs-9 control-label" v-if="!dataLoadingDuree">
            {{ dureeSelection.manuel | numberFormat }}
          </div>
          <div class="col-xs-9" v-else>
            <div class="spinner" v-if="dataLoadingDuree">
              <div class="rect1"></div>
              <div class="rect2"></div>
              <div class="rect3"></div>
            </div>
          </div>
        </div>

        <div class="form-group col-xs-8">

          <label class="col-xs-15 control-label blueText text-right" v-if="typeUtilisation == 'CPRIVSONRD'">Durée sélection</label>
          <label class="col-xs-15 control-label blueText text-right" v-else-if="typeUtilisation == 'CPRIVSONPH'">Quantité sélection</label>

          <template v-if="!dataLoadingDuree">
            <div class="col-xs-9 control-label" v-if="typeUtilisation == 'CPRIVSONRD'">
              {{ dureeFormattee }}
            </div>
            <div class="col-xs-9 control-label" v-else-if="typeUtilisation == 'CPRIVSONPH'">
              {{ dureeSelection.duree | numberFormat }}
            </div>
          </template>
          <template v-else>
            <div class="col-xs-9" >
              <div class="spinner" v-if="dataLoadingDuree">
                <div class="rect1"></div>
                <div class="rect2"></div>
                <div class="rect3"></div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<script>


  export default {

      props : {
        dureeSelection: Object,
        typeUtilisation : String,
        dataLoadingDuree : Boolean
      },

      computed : {
          dureeFormattee() {

            let jours = Math.floor( this.dureeSelection.duree / 86400);
            let reste = this.dureeSelection.duree % 86400;
            let hours = Math.floor( reste / 3600);
            reste = reste % 3600;
            let minutes = Math.floor(reste / 60);
            let seconds = reste % 60;


            return ((jours < 10) ? '0'+jours : jours) + 'j ' +((hours < 10) ? '0'+hours : hours)+"h "+((minutes < 10) ? '0' + minutes: minutes)+"m "+ ((seconds < 10) ? '0'+seconds : seconds) + "s";
          }
      }


  }
</script>


<style>

  .spinner {
    float:  left;
    width: 30px;
    height: 20px;
    text-align: center;
    font-size: 10px;
  }

  .spinner > div {
    background-color: #333;
    height: 100%;
    width: 6px;
    display: inline-block;

    -webkit-animation: sk-stretchdelay 1.2s infinite ease-in-out;
    animation: sk-stretchdelay 1.2s infinite ease-in-out;
  }

  .spinner .rect2 {
    -webkit-animation-delay: -1.1s;
    animation-delay: -1.1s;
  }

  .spinner .rect3 {
    -webkit-animation-delay: -1.0s;
    animation-delay: -1.0s;
  }

  .spinner .rect4 {
    -webkit-animation-delay: -0.9s;
    animation-delay: -0.9s;
  }

  .spinner .rect5 {
    -webkit-animation-delay: -0.8s;
    animation-delay: -0.8s;
  }

  @-webkit-keyframes sk-stretchdelay {
    0%, 40%, 100% { -webkit-transform: scaleY(0.4) }
    20% { -webkit-transform: scaleY(1.0) }
  }

  @keyframes sk-stretchdelay {
    0%, 40%, 100% {
      transform: scaleY(0.4);
      -webkit-transform: scaleY(0.4);
    }  20% {
         transform: scaleY(1.0);
         -webkit-transform: scaleY(1.0);
       }
  }

  .center-div {
    width: 0%;
    margin: 0 auto;
  }
</style>
