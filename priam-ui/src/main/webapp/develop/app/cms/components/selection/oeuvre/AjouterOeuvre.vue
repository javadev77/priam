<template>
  <div style="overflow-y: auto; max-height: 730px;">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Ajouter une Oeuvre</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">

        <div class="panel-body">
            <app-mipsa :configuration="mipsaConfig" @ready-to-search="readyToSearch"></app-mipsa>
            <br/>
            <detail-oeuvre :oeuvre="selectedOeuvre"
                           @ajout-oeuvre="onAjouterOeuvre"
                           @cancel-ajout="$emit('cancel')">

            </detail-oeuvre>
        </div>
      </div>
    </div>

    <modal v-if="showPopup">

        <template slot="body" v-if="programme.typeUtilisation === 'SONOFRA'">
          <div style="text-align: justify" v-html="messageSonoFra">
          </div>
        </template>
        <template  slot="body" v-if="programme.typeUtilisation === 'SONOANT'">
          <div style="text-align: justify" v-html="messageSonoAnt">
          </div>
        </template>

      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click.prevent="afficherEcranSelection">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click.prevent="ajouterOeuvreManuel">Oui</button>
      </template>
    </modal>

    <modal v-if="showPopupEligibilite">
      <template slot="body">
        <div style="text-align: justify" v-html="messageEligibilite">
        </div>
      </template>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right yes" @click.prevent="showPopupEligibilite=false">Oui</button>
      </template>
    </modal>

  </div>
</template>


<script>

  import AppMipsa from '../../../../common/components/ui/mipsa/AppMipsaSearch.vue';
  import DetailOeuvre from './DetailOeuvre.vue';
  import Modal from '../../../../common/components/ui/Modal.vue';

  export default {

      created() {
        const customActions = {
          findLigneProgrammeByProgramme: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/search'
          },

          isEligible : {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/isEligible'
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

            messageSonoFra : '',

            messageSonoAnt : '',

            messageEligibilite: '',

            showPopup : false,

            showPopupEligibilite : false,

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
                  let tiersCA = vm.getRoleCA(oeuvre.tiersList);
                  vm.selectedOeuvre = {
                    ide12 : oeuvre.ide12 ? oeuvre.ide12: oeuvre.keyToDisplay.cde,
                    cdeTypeIde12 : oeuvre.cdetypcleoeuv,
                    titre : oeuvre.titrePrincipal ? oeuvre.titrePrincipal: oeuvre.titrList[0].titr,
                    roleParticipant1 : tiersCA !== undefined ? tiersCA.oeuvreroleSacem : oeuvre.tiersList[0].oeuvreroleSacem,
                    nomParticipant1 : tiersCA !== undefined ? tiersCA.nom : oeuvre.tiersList[0].nom
                  }
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

            this.resource.findLigneProgrammeByProgramme({ide12 : oeuvre.ide12, numProg : this.programme.numProg})
              .then(response => {
                  return response.json();
              })
              .then(data => {
                  if(data && data.content.length > 0 ) {
                      // IDE12 existe Afficher un warning
                      let ligneProg = data.content[0];
                      this.showPopup = true;
                      if(this.programme.typeUtilisation === 'SONOFRA') {
                          this.messageSonoFra = 'Attention, cette oeuvre est déjà présente au niveau du programme avec Points = '
                            + ligneProg.pointsMontant + ', êtes-vous sûr de vouloir la remplacer ?';

                      } else if(this.programme.typeUtilisation === 'CPRIVSONRD') {
                          this.messageSonoAnt = 'Attention, cette oeuvre est déjà présente au niveau du programme avec la durée '
                            + ligneProg.durDif + ', êtes-vous sûr de vouloir la remplacer avec la durée saisie ?';
                      }
                  } else {
                    // IDE12 n'existe pas alors verifier avec le catalogue
                    this.resource.isEligible({ide12 : oeuvre.ide12})
                      .then(response => {
                        return response.json();
                      })
                      .then(isElegible => {
                        console.log ("isElegible = "+  isElegible);
                        if(isElegible) {
                          this.$emit('validate-ajout-oeuvre', this.oeuvreToAdd);
                        } else {
                          this.showPopupEligibilite = true;
                          this.messageEligibilite = 'Cette oeuvre n\'est pas éligible et donc ne peut être ajoutée au programme !';
                        }
                      });
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
