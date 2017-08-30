<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Ajouter une Oeuvre</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">

        <div class="panel-body" style="height:350px; overflow-y:scroll;">
            <app-mipsa :configuration="mipsaConfig" @ready-to-search="readyToSearch"></app-mipsa>
            <br/>
            <detail-oeuvre-selectionne :oeuvre="selectedOeuvre"></detail-oeuvre-selectionne>
        </div>
      </div>

    </div>

  </div>
</template>


<script>

  import AppMipsa from '../mipsa/AppMipsaSearch.vue';

  export default {

      mounted() {
        var MISPA_CONFIG = this.$store.getters.mipsaConfig;
        var headEl = $('head');
        var url = MISPA_CONFIG['priam.mipsa.wc.html.url'];
        var importElem = headEl.find('link').get().find(function (elem) {
            console.log('elem = ' + elem);
            let link = $(elem);
            console.log('link = ' + link.attr('href'));
            return link.attr('href') == url && link.attr('rel') == 'import';
        })
        if(importElem == undefined || importElem == null) {
          var importHtml = $('<link rel="import" >')
            .attr('href', url)
            .on('load', function () {
              console.log('loaded ' + url);
            })
            .on('error', function () {
              console.log('Error to load ' + url);
            });
          headEl.append(importHtml);
        }


      },

      data() {
         var vm =  this;
         var MISPA_CONFIG = this.$store.getters.mipsaConfig;
         var sendToken = MISPA_CONFIG['priam.mipsa.wc.usessotoken'];
         var ssoTokenMethods = sendToken ?
          function(onTokenReceived) {
            vm.$http.get('app/rest/general/ssotoken').then( function(response) {
              onTokenReceived(response.data) ;
            }) ;
          }
          : undefined;



          return {
            mipsaConfig : {
              baseurl: MISPA_CONFIG['priam.mipsa.wc.baseurl'],
              ssoTokenMethods: ssoTokenMethods,
              defaultSearch: {
                'cdedecl': MISPA_CONFIG['priam.mipsa.wc.cdedecl'],
                'cdetypinterloc': MISPA_CONFIG['priam.mipsa.wc.cdetypinterloc'],
                'schemasToSearch':['OCTAV', 'ASSOC'],
                'applyTransRulesIfExist':true,
                'encodedTitle': '',
                'encodedTiers': '',
                'neoSearchTypes':{
                  'MUSICAL_WORK':[true]
                },
                'neoSearchOptions':{
                  'SORT_REMOVE_NONPERTINENT_OEUV':[true]
                }
              },
              page: {
                pageSizes: [5,20,50,100],
                defaultPageSize: 5,
                sortColumn: 'pertinence',
                sortDirection: 'DESC'
              },
              showRulesCriterias: false,
              showInterditColumn: true,
              typeOfSelect: 1,
              hotKey: {
                searchAssocOctav: 'F5',
                searchAssoc: 'F6',
                searchOctav: 'F7',
                iconOeuvre: 'F9',
                fireSearchAfterSchema: true
              },
              linkIconOeuvre: {
                href: '#',
                target: '',
                preventDefault: true,
                onclick: function(nativeEvent, oeuvre) {
                  window.alert('icon oeuvre click '+oeuvre.keyToDisplay.cdeFormated) ;
                }
              },
              linkAction: {
                href: '#',
                target: '',
                preventDefault: true,
                onclick: function(nativeEvent, oeuvre) {
                  window.alert('icon action click '+oeuvre.keyToDisplay.cdeFormated) ;
                }
              },
              linkOnCode: {   // used to activate the copy behaviour of IDE12
                href: 'copy',           // someting not null will do the job
                target: '_blank',
                onclick: function(nativeEvent, oeuvre) {
                  // do nothing
                },
                preventDefault: true
              }
            },

            selectedOeuvre : {}
          }



      },

      methods : {

        readyToSearch(event) {
            console.log("event  = " + event);
            var mipsaSearch = this.$el.querySelector('mipsa-search') ;
            mipsaSearch.set('formVisible', true) ;
            mipsaSearch.set('resultVisible', true) ;
            mipsaSearch.search() ;
        }

      },

      components : {
        appMipsa : AppMipsa
      }
  }
</script>
