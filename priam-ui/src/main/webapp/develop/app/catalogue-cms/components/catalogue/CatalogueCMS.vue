<template>

  <div>
    <priam-navbar titre1="Catalogue Musique des Sonorisations":backButton="false"></priam-navbar>
    <app-filtre-catalogue
      :filter="filter"
      :retablir="retablirFiltre"
      :rechercher="onSearchCatalogue"
    >
    </app-filtre-catalogue>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Résultats</a>
          <span class="pull-left collapsible-icon bg-ico-tablerow"></span>
        </h5>
      </div>

      <div class="panel-collapse">
        <div class="result-panel-body panel-body">

          <div class="row">
            <!--<button class="btn btn-default btn-primary pull-right"  type="button" style="width: 120px;" @click="onAjouterOeuvre">Ajouter Oeuvre</button>-->
            <button class="btn btn-default btn-primary pull-right"  type="button" style="width: 120px;" @click="onAjouterOeuvre">Ajouter Oeuvre</button>
          </div>


          <div class="row center-div">
            <div class="spinner" v-if="dataLoading">
              <div class="rect1"></div>
              <div class="rect2"></div>
              <div class="rect3"></div>
              <div class="rect4"></div>
              <div class="rect5"></div>
            </div>
          </div>

          <div class="row">
            <div class="panel panel-default col-sm-4 sacem-formula" style="margin-top: 23px">
              <div class="panel-heading">
                <h5 class="panel-title">
                  <a>Critères supplémentaires</a>
                </h5>
              </div>
              <div class="panel-collapse sacem-formula" :class="{collapse : isCollapsed}">
                <div class="panel-body">
                  <img src="static/images/iconescontextes/ajxrep-search3.gif" alt="">
                  <span> Type d'inscription</span>



                  <ul style="list-style: none;">
                    <div class="row">
                      <div class="spinner" v-if="dataLoadingCompteur">
                        <div class="rect1"></div>
                        <div class="rect2"></div>
                        <div class="rect3"></div>
                        <div class="rect4"></div>
                        <div class="rect5"></div>
                      </div>
                    </div>
                    <li v-if="!dataLoadingCompteur" v-for="metric in typeInscription">
                      <a href="#" @click.prevent="findCatalogueByExtraCriteria(metric.code,null)">{{metric.code}} : (<b>{{metric.value}}</b>)</a>
                    </li>
                  </ul>

                  <img src="static/images/iconescontextes/ajxrep-search3.gif" alt="">
                  <span> Type utilisation générateur</span>
                  <ul style="list-style: none;">

                    <div class="row">
                      <div class="spinner" v-if="dataLoadingCompteur">
                        <div class="rect1"></div>
                        <div class="rect2"></div>
                        <div class="rect3"></div>
                        <div class="rect4"></div>
                        <div class="rect5"></div>
                      </div>
                    </div>
                    <li v-if="!dataLoadingCompteur" v-for="metric in typeUtilisation">
                      <a href="#" @click.prevent="findCatalogueByExtraCriteria(null, metric.code)">{{metric.code}} : (<b>{{metric.value}}</b>)</a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
              <priam-grid
                v-if="catalogueGrid.gridData.content && !dataLoading"
                :isPaginable="true"
                :data="catalogueGrid.gridData"
                :columns="catalogueGrid.gridColumns"
                noResultText="Aucun résultat."
                @delete-oeuvre="onDeleteOeuvre"
                @on-sort="onSort"
                @load-page="loadPage"
                @show-detail="onShowDetail"
                class="col-sm-20">
              </priam-grid>
          </div>
        </div>
      </div>
    </div>


    <modalWithTitle :showHeader="true" v-show="showPopupDeleteOeuvre">
      <template slot="header" >
        <h4 class="modal-title" style="font-size: medium">Suppression de l'oeuvre du CMS</h4>
      </template>
      <template slot="body">
        <div class="form-group">
          <label class="control-label col-sm-7">Raison de la suppression :</label>
          <div class="col-sm-17">
            <input class="form-control input-normal" type="text" maxlength="60" v-model="deletedOeuvre.raisonSortie"/>
          </div>
        </div>
      </template>

      <template slot="footer">
        <div class="form-horizontal" style="padding-top: 15px">
          <div class="col-sm-24">
            <label class="control-label pull-left" style="color: red; font-weight: bold">Attention: Cette action est irréversible</label>
            <div class="pull-right">
              <button class="btn btn-default btn-primary pull-right no" @click="onYesDeleteOeuvre">Valider</button>
              <button class="btn btn-default btn-primary pull-right yes" @click="showPopupDeleteOeuvre = false">Annuler</button>
            </div>
          </div>

        </div>
      </template>
    </modalWithTitle>

    <!--<ecran-modal v-if="showEcranAjoutOeuvreMipsa">
      <ajouter-oeuvre :typeCatalogue="filter.typeCMS.value"
                      slot="body"
                      @ajoutOeuvre="onActionAjouterOeuvre"
                      @cancel="showEcranAjoutOeuvreMipsa = false"></ajouter-oeuvre>
    </ecran-modal>-->
    <modal-detail-mipsa v-if="detailOpen" @close="detailOpen = false">
      <template slot="body">
        <app-mipsa-detail :configuration="mipsaDetailConfig" :request="request" ></app-mipsa-detail>
      </template>
    </modal-detail-mipsa>
  </div>
</template>

<script>

  import PriamGrid from './../../../common/components/ui/Grid.vue';
  import PriamNavbar from  './../../../common/components/ui/priam-navbar.vue';
  import ModalWithTitle from './../../../common/components/ui/ModalWithTitle';
  import FiltreCatalogue from './FiltreCatalogueCMS.vue';

  import EcranModal from '../../../common/components/ui/EcranModal.vue';
  import ModalDetailMipsa from '../../../catalogue-cms/components/catalogue/mipsa/ModalDetailMipsa.vue';
  import AppMipsaDetail from '../../../common/components/ui/mipsa/AppMipsaDetail.vue';

  export default {

    props : {

    },

    data() {

      var vm =  this;
      var MISPA_CONFIG = this.$store.getters.mipsaConfig;
      var MISPA_DETAIL_CONFIG = this.$store.getters.mipsaDetailConfig;
      var sendToken =  false;//MISPA_CONFIG['priam.mipsa.wc.usessotoken'];
      var ssoTokenMethods = sendToken ?
        function(onTokenReceived) {
          vm.$http.get('app/rest/general/ssotoken').then( function(response) {
            onTokenReceived(response.data) ;
          }) ;
        }
        : undefined;

      return {

        dataLoading : true,

        showPopupDeleteOeuvre : false,

        oeuvreToDelete : {},

        detailOpen : false,

        resource : {},

        catalogueGrid : {
          gridData : {},

          gridColumns : [
            {
              id : 'iconIde12',
              name : '',
              sortable :false,
              type : 'clickable-icons',
              cell : {
                css : function (entry) {

                  return {
                    style : {
                      width : '50px'
                    }
                  }
                },

                cellTemplate: function (cellValue) {

                  var template = [
                    {event : 'show-detail',
                      template : '<img src="static/images/Musique.png" width="20px"/>'}];
                  return template;
                }

              }

            },

            {
              id : 'ide12',
              name :'IDE12',
              sortable : true,
              type : 'text-centre',
              cell: {
                css : function (entry) {
                  return {
                    style : {
                      width : '100px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'titre',
              name: "Titre",
              sortable: true,
              type: 'text',
              cell : {

                css : function (entry) {
                  return {
                    style : {
                      width : '200px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'participants',
              name: "Participant",
              sortable: false,
              type: 'list-render',
              listStyle : 'circle',
              cell: {
                css : function (entry) {
                  return {
                    style : {
                      'text-align' : 'left',
                      width : '200px'
                    }
                  }
                },

                getData: function (entry) {
                  let participants = entry.participants;
                  return participants !== null &&
                         participants !== undefined &&
                         participants.split(",").slice(0,4);
                }


              }
            },

            {
              id: 'roles',
              name: 'Rôle',
              sortable: false,
              type: 'list-render',
              listStyle : 'none',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      'text-align' : 'center',
                       width : '50px'
                    }
                  }
                },

                getData: function (entry) {
                  let roles = entry.roles;
                  return roles !== null &&
                    roles !== undefined &&
                    roles.split(",").slice(0,4);
                }


              }
            },

            {
              id: 'typUtilGen',
              name : 'Type utilisation<br/>générateur',
              sortable: true,
              type: 'text-centre',
              cell : {

                css : function (entry) {
                  return {
                    style : {
                      width : '150px'
                    }
                  }
                },
                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'dateEntree',
              name: "Date d'entrée",
              sortable: true,
              type: 'date',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '150px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'typeInscription',
              name: "Type d'inscription",
              type: 'text-centre',
              sortable: true,
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '150px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'dateRenouvellement',
              name: "Date de<br/>renouvellement",
              sortable: true,
              type: 'date',
              cell: {
                css: function (entry) {
                  return {
                    style: {
                      width: '150px'
                    }
                  }
                },

                toText: function (entry) {
                  var result = entry;
                  if (result !== undefined)
                    return result;
                  else
                    return "";
                }
              }
            },

            {
              id: 'dateSortie',
              name: "Date de sortie",
              sortable: true,
              type: 'date',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '150px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'typeSortie',
              name: "Type de sortie",
              sortable: true,
              type: 'text-centre',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '150px'
                    }
                  }
                },

                toText : function(entry) {
                  var result = entry;
                  if(result !== undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'action',
              name: "Actions",
              sortable: false,
              type: 'clickable-icons',
              cell: {

                cellTemplate: function (cellValue) {
                  var tempalteTrash = '<span class="glyphicon glyphicon-trash" aria-hidden="true" style="padding-left: 0px;" title="Abandonner"></span>';
                  /*var template = [
                    {event : 'delete-oeuvre',
                      template : tempalteTrash}];*/
                  var template = [{}];
                  if(cellValue.typeSortie==null){
                    template[0] = {event : 'delete-oeuvre', template : tempalteTrash};
                  }
                  return template;
                }
              }
            }
          ],
          searchQuery : ''
        },

        mipsaDetailConfig : {
          mipsaurl: MISPA_CONFIG['priam.mipsa.wc.baseurl'],
          octavurl: MISPA_CONFIG['priam.mipsa.wc.octav.url'],
          ssoTokenMethods: ssoTokenMethods,
          viewBloc: {
            assoc: 'hide',
            octav: 'maximize'
          }
        },

        request : {
          cde: null,
          typCde: 'COCV',
          seqassocoeuv: '',
          cdecombi: '',
          encodedTitle: '',
          encodedTiers: ''
        },

        filter : {
          typeCMS : {id :'FR', value : 'CMS France'},
          ide12: null,
          titre: null,
          participant:null,
          periodeEntreeFilter: {
            dateDebut: null,
            dateFin: null
          },
          periodeRenouvellementFilter: {
            dateDebut: null,
            dateFin: null
          },
          periodeSortieFilter: {
            dateDebut: null,
            dateFin: null
          },
          displayOeuvreNonEligible: false,
          typeInscription: null,
          typeUtilisation: null
        },

        currentFilter : {
          typeCMS : {id : 'FR', value : 'CMS France'},
          ide12: null,
          titre: null,
          participant:null,
          periodeEntreeDateDebut: null,
          periodeEntreeDateFin: null,
          periodeRenouvellementDateDebut: null,
          periodeRenouvellementDateFin: null,
          periodeSortieDateDebut: null,
          periodeSortieDateFin: null,
          displayOeuvreNonEligible: false,
          typeInscription: null,
          typeUtilisation: null
        },

        defaultPageable : {
          page : 1,
          sort : 'dateEntree',
          dir : 'DESC',
          size : this.$store.getters.userPageSize
        },

        showEcranAjoutOeuvreMipsa : false,

        deletedOeuvre : {
          raisonSortie: ''
        },
        isCollapsed : false,

        typeInscription : {
          code: '',
          value: ''
        },
        typeUtilisation : {
          code: '',
          value: ''
        },
        dataLoadingCompteur: false
      }
    },

    mounted(){
      var MISPA_CONFIG = this.$store.getters.mipsaConfig;
      var headEl = $('head');
      var url = MISPA_CONFIG['priam.mipsa.wc.detail.html.url'];
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

    created() {

      const customActions = {
        searchCatalogueRdo : {
          method : 'POST',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/search?page={page}&size={size}&sort={sort},{dir}'},
        deleteOeuvreCatalogueRdo : {
          method : 'DELETE',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/oeuvre/{id}'
        },

        ajouterOeuvreCatalogueRdo : {
          method : 'POST',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/oeuvre'
        },
        compteurCatalogueRdo : {
          method : 'POST',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/compteur'
        }
      }
      this.resource= this.$resource('', {}, customActions);
      this.findCatalogueByCriteria();
      this.calculerCompteurs();
    },

    methods : {



      findCatalogueByCriteria() {
        this.currentFilter.typeCMS = this.filter.typeCMS.id;
        this.currentFilter.ide12 = this.filter.ide12;
        this.currentFilter.titre = this.filter.titre;
        this.currentFilter.participant = this.filter.participant;
        this.currentFilter.periodeEntreeDateDebut = this.filter.periodeEntreeFilter.dateDebut;
        this.currentFilter.periodeEntreeDateFin = this.filter.periodeEntreeFilter.dateFin;
        this.currentFilter.periodeRenouvellementDateDebut = this.filter.periodeRenouvellementFilter.dateDebut;
        this.currentFilter.periodeRenouvellementDateFin = this.filter.periodeRenouvellementFilter.dateFin;
        this.currentFilter.periodeSortieDateDebut = this.filter.periodeSortieFilter.dateDebut;
        this.currentFilter.periodeSortieDateFin = this.filter.periodeSortieFilter.dateFin;
        this.currentFilter.displayOeuvreNonEligible = this.filter.displayOeuvreNonEligible;
        this.currentFilter.typeInscription = this.filter.typeInscription;
        this.currentFilter.typeUtilisation = this.filter.typeUtilisation;



        this.dataLoading = true;
        debugger;

        this.resource.searchCatalogueRdo({
          page : this.defaultPageable.page - 1,
          size : this.defaultPageable.size,
          sort : this.defaultPageable.sort,
          dir  : this.defaultPageable.dir}, this.currentFilter)
          .then(response => {
            return response.json();
          })
          .then(data => {

            this.catalogueGrid.gridData = data;

            this.catalogueGrid.gridData.number = data.number + 1;
            this.dataLoading = false;
          })
          .catch(error => {
            alert("Erreur lors de la recherche !!! ")
          });
      },

      findCatalogueByExtraCriteria(typeInscriptionCriteria, typeUtilisationCriteria){

        this.filter.typeInscription = typeInscriptionCriteria;
        this.filter.typeUtilisation = typeUtilisationCriteria;
        this.findCatalogueByCriteria();
      },

      onSearchCatalogue(){
        this.findCatalogueByExtraCriteria(null, null);
        this.calculerCompteurs();
      },


      onDeleteOeuvre(row, column) {
        this.showPopupDeleteOeuvre = true;
        this.deletedOeuvre.raisonSortie = '';
        this.oeuvreToDelete = row;
        console.log('this.oeuvreToDelete => ide12 = ', this.oeuvreToDelete.ide12)
      },

      onYesDeleteOeuvre() {
        console.log('Deleting Oeuvre ide12 = ', this.oeuvreToDelete.ide12);
        this.showPopupDeleteOeuvre = false;
        this.resource.deleteOeuvreCatalogueRdo({id : this.oeuvreToDelete.id}, this.deletedOeuvre)
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log('data = ' + data);
            this.oeuvreToDelete.typeSortie = data.typeSortie;
            this.oeuvreToDelete.dateSortie = data.dateSortie;
            debugger;
            this.calculerCompteurs();
          });
      },

      onAjouterOeuvre() {
          /*this.showEcranAjoutOeuvreMipsa = true;*/
        this.$router.push({ name: 'ajout-oeuvre', params: { typeCatalogue: this.filter.typeCMS.id }});
        /*this.$router.push({ name: 'ajout-oeuvre'});*/
      },

      retablirFiltre() {
        this.filter = {
          typeCMS : {'id' :'FR', 'value' : 'CMS France'},
          ide12: null,
          titre: null,
          participant: null,
          periodeEntreeFilter: {
            dateDebut: null,
            dateFin: null
          },
          periodeRenouvellementFilter: {
            dateDebut: null,
            dateFin: null
          },
          periodeSortieFilter: {
            dateDebut: null,
            dateFin: null
          },
          displayOeuvreNonEligible: false,
          typeInscription: null,
          typeUtilisation: null
        }
        this.findCatalogueByCriteria();
        this.calculerCompteurs();
      },

      onSort(currentPage, pageSize, sort) {

        this.launchRequest(currentPage, pageSize,
          sort.property, sort.direction);

        this.defaultPageable.sort = sort.property;
        this.defaultPageable.dir = sort.direction;
      },

      launchRequest(pageNum, pageSize, sort, dir) {

        this.dataLoading = true;
        this.resource.searchCatalogueRdo({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir}, this.currentFilter)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.catalogueGrid.gridData = data;
            this.catalogueGrid.gridData.number = data.number + 1;
            this.dataLoading = false;

          });
      },

      loadPage: function(pageNum, size, sort) {
        this.defaultPageable.size = size;
        let pageSize = this.defaultPageable.size;
        this.launchRequest(pageNum, pageSize, sort.property, sort.direction);
      },


        onActionAjouterOeuvre(selectedOeuvre) {
          console.log("Selected Oeuvre ==> " + selectedOeuvre.ide12);
          console.log("\t\t Role Size==> " + selectedOeuvre.tiersList.length);


          //this.showEcranAjoutOeuvreMipsa = false;


        let participantsList = selectedOeuvre.tiersList.map(tiers => [{
          typeCMS : this.filter.value,
          ide12:  selectedOeuvre.ide12,
          role : tiers.oeuvreroleSacem,
          nomParticpant : tiers.nom
        }]);
        debugger;
         let oeuvreCatalogue = {
           typeCMS : this.filter.value,
           ide12 : selectedOeuvre.ide12,
           typUtiGen : 'PHONOFR',
           typeInscription : 'Manuelle',
           titre : selectedOeuvre.titre,
           participantsCatcms : participantsList
         }

         this.resource.ajouterOeuvreCatalogueRdo(oeuvreCatalogue)
           .then(resp => {
              console.log(resp)
           });
      },

      calculerCompteurs: function () {
          this.dataLoadingCompteur = true;
        this.resource.compteurCatalogueRdo(this.currentFilter)
          .then(response => {
            return response.json();
          }).then(data => {
          this.typeInscription = data.TYPE_INSCRIPTION;
          this.typeUtilisation = data.TYPE_UTILISATION;
          this.dataLoadingCompteur = false;
        })
        ;
      },
      onShowDetail: function(row) {
          this.request = {
            cde: row.ide12,
            typCde: 'COCV',
            seqassocoeuv: '',
            cdecombi: '',
            encodedTitle: row.titre,
            encodedTiers: ''
          }
          this.detailOpen = true;
      }
    },


    components : {
      'priam-grid' : PriamGrid,
      'priam-navbar' : PriamNavbar,
      'modalWithTitle': ModalWithTitle,
      appFiltreCatalogue : FiltreCatalogue,
      ecranModal : EcranModal,
      modalDetailMipsa : ModalDetailMipsa,
      'app-mipsa-detail' : AppMipsaDetail
    }
  }

</script>

<style scoped>
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

  .center-div {
    width: 0%;
    margin: 0 auto;
  }
</style>
