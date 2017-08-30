<template>
  <div>
    <mipsa-search></mipsa-search>
  </div>
</template>

<script>

  import 'webcomponents.js/webcomponents-lite'

  var promise ;
  var errorHandler = function($window, $log, $timeout) {
    return function(response) {

      $log.error('An unknown error occurs, response data is : ', response) ;

      if ( promise ) {
        $timeout.cancel(promise) ;
      }

      promise = $timeout(function() {
        var detail = '' ;
        if ( response && response.originalEvent && response.originalEvent.detail && response.originalEvent.detail.error ) {
          detail = '\nDétail : "'+ response.originalEvent.detail.error+'"' ;
        }
        $window.alert('Une erreur s\'est produite au sein du service mips '+detail) ;
      }, 1000) ;
    } ;
  };


  export default {


      props : {

          configuration : {
              type : Object,
              required : false
          }

      },

      data() {

          return {
              mipsaHtml : '<mipsa-search></mipsa-search>'
          }

      },


      mounted() {
        let self = this;
        var element = $(self.$el);

          //element.append("<mipsa-search></mipsa-search>");

          var domEL = element.find('mipsa-search').get(0);
          console.log('domEL = ' + domEL);

          if ( domEL.ready ) {
              domEL.configuration = self.configuration ;     // give configuration to Web Component
          }
          element.bind('component-ready', function() {
              domEL.configuration = self.configuration ;     // give configuration to Web Component
          }) ;

          element.bind('ready-to-search', function(event) {
            self.$emit('ready-to-search', event);
          }) ;


          element.bind('selection-changed', function(event) {
            self.$emit('selection-changed', event);
          });

          element.bind('error', function () {
            return function(response) {
              console.error('An unknown error occurs, response data is : ', response) ;
            }
          });
      }

  }

</script>
