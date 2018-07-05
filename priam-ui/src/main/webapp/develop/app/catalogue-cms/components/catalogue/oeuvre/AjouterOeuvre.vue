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
            <div class="row">
              <div class="col-md-3">
                <label class="control-label">Type du Catalogue : {{ typeCatalogue }}</label>
              </div>
            </div>

            <app-mipsa :configuration="mipsaConfig" @ready-to-search="readyToSearch"></app-mipsa>

        </div>
      </div>
    </div>


  </div>
</template>


<script>

  import AppMipsa from '../../../../common/components/ui/mipsa/AppMipsaSearch.vue';
  import Modal from '../../../../common/components/ui/Modal.vue';

  export default {

      props : {

        typeCatalogue : {
            type : String,
            default : 'CMS FRANCE'
        }

      },

      created() {
        const customActions = {
          findLigneProgrammeByProgramme: {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/search'
          },

          isEligible : {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/isEligible'
          },

          downloadCatalogueOctav : {
              method : 'GET',
              url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/downloadCatalogueOctav'
          },

          getLastCatalogueOctav : {
            method : 'GET',
            url: process.env.CONTEXT_ROOT_PRIAM_CMS + 'app/rest/ligneProgramme/selection/catalogueOctav'
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

            chgtCatalogueEncours:  false,

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
                    //alert("Hello  Habib !!!!");
                  let tiersList = vm.getRoleCatalogueCMS(oeuvre.tiersList);
                  let selectedOeuvre = {
                    ide12 : oeuvre.ide12 ? oeuvre.ide12: oeuvre.keyToDisplay.cde,
                    titre : oeuvre.titrePrincipal ? oeuvre.titrePrincipal: oeuvre.titrList[0].titr,
                    tiersList : tiersList !== undefined ? tiersList : new Array(oeuvre.tiersList[0])
                  }

                  vm.$emit('ajoutOeuvre', selectedOeuvre);

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

            selectedOeuvre : {
                ide12 : '',
                titre : ''
            }
          }



      },

      methods : {

        getRoleCatalogueCMS(tiersList) {
          var result =  tiersList.filter(function (tiers) {
            return tiers.oeuvreroleSacem === 'A'  ||
                   tiers.oeuvreroleSacem === 'AR' ||
                   tiers.oeuvreroleSacem === 'C'  ||
                   tiers.oeuvreroleSacem === 'CA' ||
                   tiers.oeuvreroleSacem === 'SA';
          });

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

                      } else if(this.programme.typeUtilisation === 'SONOANT') {
                          this.messageSonoAnt = 'Attention, cette oeuvre est déjà présente au niveau du programme avec Points '
                            + ligneProg.pointsMontant + ', êtes-vous sûr de vouloir la remplacer ?';
                      }
                  } else {
                    // IDE12 n'existe pas alors verifier avec le catalogue


                    //Lancer le chargement du catalogue octav
                    var self =this;
                    /*this.resource.downloadCatalogueOctav()
                      .then(response => {
                         self.chgtCatalogueEncours = true;
                         console.log("Lancement OK de chargement !!!");
                      });
                    var timer = setInterval(function () {
                      debugger;
                      self.resource.getLastCatalogueOctav()
                        .then(response => {
                          return response.json();
                        })
                        .then(fichierCatalogueOctav => {
                            debugger
                          if(fichierCatalogueOctav.statut === 'CHARGEMENT_OK') {
                            clearInterval(timer);
                            self.chgtCatalogueEncours = false;
                            self.resource.isEligible({ide12: oeuvre.ide12})
                              .then(response => {
                                return response.json();
                              })
                              .then(isEligible => {
                                console.log("isEligible = " + isEligible);
                                debugger;
                                if (isEligible) {
                                  self.$emit('validate-ajout-oeuvre', self.oeuvreToAdd);
                                } else {
                                  self.showPopupEligibilite = true;
                                  self.messageEligibilite = 'Cette oeuvre n\'est pas éligible et donc ne peut être ajoutée au programme !';
                                }
                              });
                          }
                        });
                    }, 2000);*/
                    var typeCMS = '';
                    if(this.programme.typeUtilisation === 'SONOFRA') {
                      typeCMS = 'FR';
                    } else if (this.programme.typeUtilisation === 'SONOANT') {
                      typeCMS = 'ANF';
                    }
                    self.resource.isEligible({ide12: oeuvre.ide12, typeCMS: typeCMS})
                      .then(response => {
                        return response.json();
                      })
                      .then(isEligible => {
                        console.log("isEligible = " + isEligible);
                        debugger;
                        if (isEligible) {
                          self.$emit('validate-ajout-oeuvre', self.oeuvreToAdd);
                        } else {
                          self.showPopupEligibilite = true;
                          self.messageEligibilite = 'Cette oeuvre n\'est pas éligible et donc ne peut être ajoutée au programme !';
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
        modal : Modal
      }
  }
</script>

<style>

  .spinner {
    float:  left;
    width: 50px;
    height: 40px;
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

  .mask {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 1050;
    outline: 0;
    background-color: black;
    opacity: 0.5;
  }

  .mask .center-div {
    width: 0%;
    margin: 0 auto;
    margin-top: 50vh; /* poussé de la moitié de hauteur de viewport */
    transform: translateY(-50%);
  }

</style>
