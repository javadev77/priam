<template>
    <div>
      <div class="container-fluid">
        <navbar titre1="Catalogue Musique des Sonorisations" titre2="Ajout oeuvre CMS" :backButton="true"></navbar>
        <div>
          <div class="row">
            <div class="col-md-4" style="margin-left: 20px">
              <label class="control-label">Type du Catalogue : {{ getLibelleTypeCatalogue(this.typeCatalogue)}}</label>
            </div>
          </div>
        </div>
        <div class="container-fluid sacem-formula">
          <div class="panel panel-default">
            <div class="panel-collapse">

              <div class="panel-body">
                <app-mipsa :configuration="mipsaConfig" @ready-to-search="readyToSearch"></app-mipsa>
              </div>
            </div>
          </div>
        </div>
      </div>
      <modal v-if="showPopupExistante">
        <template slot="body">
          <div style="text-align: justify" v-html="messageExistante">
          </div>
        </template>
        <template slot="footer">
          <button class="btn btn-default btn-primary pull-right yes" @click.prevent="renouvelerOeuvre">Oui</button>
          <button class="btn btn-default btn-primary pull-right yes" @click.prevent="showPopupExistante=false">Non</button>
        </template>
      </modal>
    </div>
</template>

<script>

  import AppMipsa from '../../../common/components/ui/mipsa/AppMipsaSearch.vue';
  import Modal from '../../../common/components/ui/Modal.vue';
  import Navbar from '../../../common/components/ui/priam-navbar.vue';
  import catalogueMixin from '../../mixins/catalogueMixin';

    export default {

      mixins: [catalogueMixin],
      name: "ajouter-oeuvre",

      created() {
        const customActions = {

          findOeuvreExistanteCatalogue : {
            method: 'POST',
            url: process.env.CONTEXT_ROOT_PRIAM_CAT_RDO  + 'app/rest/catalogue/oeuvre/search'
          },

          ajouterOeuvreCatalogueRdo : {
            method : 'POST',
            url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/oeuvre'
          }

        }
        this.resource = this.$resource('', {}, customActions);
        this.typeCatalogue = this.$route.params.typeCatalogue;
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

          messageExistante: '',

          showPopup : false,

          showPopupExistante : false,

          oeuvreToAdd : {
            id : null,
            typeCMS: null,
            ide12 : null,
            typUtiGen : null,
            typeInscription : null,
            titre : null,
            roles : null,
            participants : null
          },

          oeuvreToRenew : {
            id : null,
            typeCMS: null,
            ide12 : null,
            typUtiGen : null,
            typeInscription : null,
            titre : null,
            roles : null,
            participants : null
          },

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
                console.log("onclick");
              }
            },
            linkAction: {
              href: '#',
              target: '',
              preventDefault: true,
              onclick: function(nativeEvent, oeuvre) {
                //alert("Hello  Habib !!!!");
                debugger;
                let tiersList = vm.getRoleCatalogueCMS(oeuvre.tiersList);
                let selectedOeuvre = {
                  ide12: oeuvre.ide12 ? oeuvre.ide12 : oeuvre.keyToDisplay.cde,
                  titre: oeuvre.titrePrincipal ? oeuvre.titrePrincipal : oeuvre.titrList[0].titr,
                  tiersList: tiersList !== undefined ? tiersList : new Array(oeuvre.tiersList[0])
                };
                vm.checkOeuvreExistanteCatalogue(selectedOeuvre)
                /*vm.$router.push({name: 'catalogue-cms'});*/
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
          },
          typeCatalogue : {
            type : String,
            default : 'CMS FRANCE'
          }
        }
      },

      methods : {

        getRoleCatalogueCMS(tiersList) {
          debugger;
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

        afficherEcranSelection() {
          this.showPopup = false;
          this.$emit('cancel');

        },

        /*ajouterOeuvreManuel() {
          this.showPopup = false;
          this.$emit('validate-ajout-oeuvre', this.oeuvreToAdd);
        },*/

        checkOeuvreExistanteCatalogue(selectedOeuvre) {
          console.log("Selected Oeuvre ==> " + selectedOeuvre.ide12);
          console.log("\t\t Role Size==> " + selectedOeuvre.tiersList.length);

          //role : tiers.oeuvreroleSacem,
          let participantsList = selectedOeuvre.tiersList.map(tiers => [

            tiers.nom
          ]);

          let rolesList = selectedOeuvre.tiersList.map(tiers => [

            tiers.oeuvreroleSacem
          ]);
          debugger;

          this.oeuvreToAdd.id = selectedOeuvre.id;
          this.oeuvreToAdd.typeCMS = this.typeCatalogue;
          this.oeuvreToAdd.ide12 = selectedOeuvre.ide12;
          this.oeuvreToAdd.typUtiGen = 'PHONOFR';
          this.oeuvreToAdd.typeInscription = 'Manuelle';
          this.oeuvreToAdd.titre = selectedOeuvre.titre;
          this.oeuvreToAdd.roles = rolesList.join(',');
          this.oeuvreToAdd.participants = participantsList.join(',');
          // on verifie si l'oeuvre est déja existante : on utilise la méthode findOeuvreExistanteCatalogue
          this.resource.findOeuvreExistanteCatalogue({ide12: this.oeuvreToAdd.ide12, typeCMS: this.oeuvreToAdd.typeCMS})
            .then(response => {
              debugger;
              return response.json();
            })
            .then(data => {
              console.log("id findOeuvreExistanteCatalogue = " + data.id);
              if (data !== null && data.ide12 != null) {
                debugger;
                this.oeuvreToRenew.id = data.id;
                debugger;
                this.showPopupExistante = true;
                this.messageExistante = 'Cette oeuvre est déjà existante dans le catalogue, êtes-vous sûr de vouloir la renouveler';

              } else {
                this.resource.ajouterOeuvreCatalogueRdo(this.oeuvreToAdd)
                  .then(resp => {
                    console.log(resp)
                    this.$router.push({ name: 'catalogue-cms'});
                  })
                  .catch(error => {
                    alert("Erreur lors de l'ajout !!")
                  });
              }
            });
        },

        renouvelerOeuvre() {
            this.$http.put(process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/oeuvre/{id}', {}, {params : {id : this.oeuvreToRenew.id}})
            .then(response => {
              return response.json();
            })
            .then(data => {
              console.log('data = ' + data);
              this.$router.push({ name: 'catalogue-cms'});
            })
            .catch(error => {
              alert("Erreur lors du renouvellement !!")
            });
        }

      },



      components : {
        appMipsa : AppMipsa,
        modal : Modal,
        navbar : Navbar
      }
    }
</script>

<style scoped>

</style>
