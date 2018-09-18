<template>
  <div>
    <mipsa-detail-oeuvre></mipsa-detail-oeuvre>
  </div>
</template>

<script>
  import 'webcomponents.js/webcomponents-lite'
  export default {
    props : {

      configuration : {
        type : Object,
        required : false
      },

      request : {
        type : Object,
        required : false
      }

    },

    data() {

      return {
//        mipsaHtml : '<mipsa-detail-oeuvre></mipsa-detail-oeuvre>'
      }

    },

    mounted() {
      let self = this;
      var element = $(self.$el);

      var domEL = element.find('mipsa-detail-oeuvre').get(0) ;

      console.log('domEL = ' + domEL);

      if ( domEL.ready ) {
        domEL.configuration = self.configuration ;     // give configuration to Web Component
      }
      element.bind('component-ready', function() {
        domEL.configuration = self.configuration ;     // give configuration to Web Component
      }) ;

      element.bind('error', function (response) {
        console.log("on error");

        //promise = setInterval(function() {
        var detail = '';
        if (response && response.originalEvent && response.originalEvent.detail && response.originalEvent.detail.error) {
          detail = '\nDétail : "' + response.originalEvent.detail.error + '"';
        }
        window.alert('Une erreur s\'est produite au sein du reader mips ' + detail);
//          }, 1000);
      });

      domEL.set('request', self.request) ;

    }

  }
</script>
