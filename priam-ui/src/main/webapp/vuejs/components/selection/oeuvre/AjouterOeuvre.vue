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

        <div class="panel-body" style="height:450px; overflow-y:scroll;">
            <app-mipsa :configuration="mipsaConfig" @ready-to-search="readyToSearch"></app-mipsa>
            <br/>
            <detail-oeuvre :oeuvre="selectedOeuvre" @ajout-oeuvre="onAjouterOeuvre"></detail-oeuvre>
        </div>
      </div>
    </div>

    <modal v-if="showPopup">

        <template slot="body" v-if="programme.typeUtilisation === 'CPRIVSONPH'">
          <div style="text-align: justify" v-html="messagePhono">
          </div>
        </template>
        <template  slot="body" v-if="programme.typeUtilisation === 'CPRIVSONRD'">
          <div style="text-align: justify" v-html="messageRadio">
          </div>
        </template>

      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click.prevent="afficherEcranSelection">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click.prevent="ajouterOeuvreManuel">Oui</button>
      </template>
    </modal>

  </div>
</template>


<script>

  import AppMipsa from '../../mipsa/AppMipsaSearch.vue';
  import DetailOeuvre from '../oeuvre/DetailOeuvre.vue';
  import Modal from '../../common/Modal.vue';

  export default {

      created() {
        const customActions = {
          findLigneProgrammeByProgramme: {
            method: 'POST',
            url: 'app/rest/ligneProgramme/search'
          }
        }
        this.resource = this.$resource('', {}, customActions);
      },

      mounted() {
        var MISPA_CONFIG = this.$store.getters.mipsaConfig;
        var headEl = $('head');
        var url = MISPA_CONFIG['priam.mipsa.wc.html.url'];
        var importElem = headEl.find('link').get().find(function (elem) {
            let link = $(elem);
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
         var sendToken =  false;//MISPA_CONFIG['priam.mipsa.wc.usessotoken'];
         var ssoTokenMethods = sendToken ?
          function(onTokenReceived) {
            vm.$http.get('app/rest/general/ssotoken').then( function(response) {
              onTokenReceived(response.data) ;
            }) ;
          }
          : undefined;



          return {

            programme : null,

            messagePhono : '',

            messageRadio : '',

            showPopup : false,

            oeuvreToAdd : {},

            mipsaConfig : {
              baseurl: MISPA_CONFIG['priam.mipsa.wc.baseurl'],
              ssoTokenMethods: ssoTokenMethods,
              defaultSearch: {
                'cdedecl': MISPA_CONFIG['priam.mipsa.wc.cdedecl'],
                'cdetypinterloc': MISPA_CONFIG['priam.mipsa.wc.cdetypinterloc'],
                'schemasToSearch':[],
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
                  //window.alert('icon oeuvre click '+oeuvre.keyToDisplay.cdeFormated) ;
                }
              },
              linkAction: {
                href: '#',
                target: '',
                preventDefault: true,
                onclick: function(nativeEvent, oeuvre) {
                  window.alert('icon action click '+oeuvre.keyToDisplay.cdeFormated) ;
                  let tiersCA = vm.getRoleCA(oeuvre.tiersList);
                  vm.selectedOeuvre = {
                    ide12 : oeuvre.ide12 ? oeuvre.ide12: oeuvre.keyToDisplay.cde,
                    titre : oeuvre.titrePrincipal ? oeuvre.titrePrincipal: oeuvre.titrList[0].titr,
                    roleParticipant1 : tiersCA !== undefined ? tiersCA.oeuvreroleSacem : '',
                    nomParticipant1 : tiersCA !== undefined ? tiersCA.nom : ''
                  }

                  console.log("Selected Oeuvre = " + vm.selectedOeuvre.ide12);
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

        getRoleCA(tiersList) {
          var result =  tiersList.find(function (tiers) {
            return tiers.oeuvreroleSacem === 'CA';
          })

          return result;
        },

        readyToSearch(event) {
            console.log("event  = " + event);
            var mipsaSearch = this.$el.querySelector('mipsa-search') ;
            mipsaSearch.set('formVisible', true) ;
            mipsaSearch.set('resultVisible', true) ;
            mipsaSearch.search() ;
        },

        onAjouterOeuvre(oeuvre) {
            this.oeuvreToAdd = oeuvre;
            this.programme = this.$store.getters.programmeEnSelection;
            debugger;
            this.resource.findLigneProgrammeByProgramme({ide12 : oeuvre.ide12, utilisateur : oeuvre.utilisateur, numProg : this.programme.numProg})
              .then(response => {
                  return response.json();
              })
              .then(data => {
                  if(data && data.content.length > 0 ) {
                      // CDEUTIL et IDE12 existe Afficher un warning
                      let ligneProg = data.content[0];
                      this.showPopup = true;
                      if(this.programme.typeUtilisation === 'CPRIVSONPH') {
                          this.messagePhono = 'Attention, cette oeuvre est déjà présente au niveau du programme avec la quantité '
                            + ligneProg.nbrDif + ', êtes-vous sûr de vouloir la remplacer avec la quantité saisie ?';

                      } else if(this.programme.typeUtilisation === 'CPRIVSONRD') {
                          this.messageRadio = 'Attention, cette oeuvre est déjà présente au niveau du programme avec la durée '
                            + ligneProg.durDif + ', êtes-vous sûr de vouloir la remplacer avec la durée saisie ?';
                      }
                  } else {

                    this.$emit('validate-ajout-oeuvre', this.oeuvreToAdd);
                  }
              });
        },

        afficherEcranSelection() {
          this.showPopup = false;
          this.$emit('cancel');

        },

        ajouterOeuvreManuel() {
          this.showPopup = false;
          this.$emit('validate-ajout-oeuvre', this.oeuvreToAdd);
        }

      },



      components : {
        appMipsa : AppMipsa,
        detailOeuvre : DetailOeuvre,
        modal : Modal
      }
  }
</script>
