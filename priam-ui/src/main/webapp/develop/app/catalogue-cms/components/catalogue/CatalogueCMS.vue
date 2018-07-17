<template>

  <div>
    <priam-navbar titre1="Catalogue Musique des Sonorisations":backButton="false"></priam-navbar>
    <app-filtre-catalogue
      :filter="filter"
      :retablir="retablirFiltre"
      :rechercher="findCatalogueByCriteria"
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


          <priam-grid
            v-if="catalogueGrid.gridData.content && !dataLoading"
            :isPaginable="true"
            :data="catalogueGrid.gridData"
            :columns="catalogueGrid.gridColumns"
            noResultText="Aucun résultat."
            @delete-oeuvre="onDeleteOeuvre"
            @on-sort="onSort"
            @load-page="loadPage">
          </priam-grid>
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
  </div>
</template>

<script>

  import PriamGrid from './../../../common/components/ui/Grid.vue';
  import PriamNavbar from  './../../../common/components/ui/priam-navbar.vue';
  import ModalWithTitle from './../../../common/components/ui/ModalWithTitle';
  import FiltreCatalogue from './FiltreCatalogueCMS.vue';

  import EcranModal from '../../../common/components/ui/EcranModal.vue';

  export default {

    props : {

    },


    data() {

      return {

        dataLoading : true,

        showPopupDeleteOeuvre : false,

        oeuvreToDelete : {
        },

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
                    {event : 'oeuvre',
                      template : '<img src="static/images/Musique.png" title="Mise en répartition" width="20px"/>'}];
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
                         participants.split(",");
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
                    roles.split(",");
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
          displayOeuvreNonEligible: false
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
          displayOeuvreNonEligible: false
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
        }
      }
      this.resource= this.$resource('', {}, customActions);
      this.findCatalogueByCriteria();
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
          displayOeuvreNonEligible: false
        }

        this.findCatalogueByCriteria();
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
      }



    },


    components : {
      'priam-grid' : PriamGrid,
      'priam-navbar' : PriamNavbar,
      'modalWithTitle': ModalWithTitle,
      appFiltreCatalogue : FiltreCatalogue,
      ecranModal : EcranModal
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
