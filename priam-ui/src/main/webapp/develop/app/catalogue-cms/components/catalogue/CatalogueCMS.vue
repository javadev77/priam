<template>

  <div>
    <priam-navbar titre1="Catalogue Musique des Sonorisations" :backButton="false"></priam-navbar>
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
            <button class="btn btn-default btn-primary pull-right"  type="button" style="width: 120px;" @click="onAjouterOeuvre">Ajouter Oeuvre</button>
          </div>



          <priam-grid
            v-if="catalogueGrid.gridData.content"
            :isPaginable="false"
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


    <modal v-show="showPopupDeleteOeuvre">
      <label class="homer-prompt-q control-label" slot="body">
        Suppression Oeuvre
      </label>
      <template slot="footer">
        <button class="btn btn-default btn-primary pull-right no" @click="showPopupDeleteOeuvre = false">Non</button>
        <button class="btn btn-default btn-primary pull-right yes" @click="onYesDeleteOeuvre">Oui</button>
      </template>
    </modal>
  </div>
</template>

<script>

  import PriamGrid from './../../../common/components/ui/Grid.vue';
  import PriamNavbar from  './../../../common/components/ui/priam-navbar.vue';
  import Modal from './../../../common/components/ui/Modal.vue';
  import FiltreCatalogue from './FiltreCatalogueCMS.vue';


  export default {

    props : {

    },


    data() {

      return {

        defaultPageable : {
          page : 1,
          sort : 'ide12',
          dir : 'DESC',
          size : this.$store.getters.userPageSize
        },

        showPopupDeleteOeuvre : false,

        oeuvreToDelete : {},

        resource : {},

        catalogueGrid : {
          gridData : {
            //"content":[{"ide12":3516649111,"titreOeuvre":"JE TE PARDONNE FEAT SIA","roleParticipant1":"C","nomParticipant1":"REBILLAUD RENAUD","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2986577411,"titreOeuvre":"LA MORALE","roleParticipant1":"CA","nomParticipant1":"DESSART CHRISTIAN","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":2960328811,"titreOeuvre":"SAPES COMME JAMAIS","roleParticipant1":"C","nomParticipant1":"KOUELOUKOUENDA DANIEL","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2930892411,"titreOeuvre":"EST CE QUE TU M AIMES","roleParticipant1":"C","nomParticipant1":"REBILLAUD RENAUD","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2986577611,"titreOeuvre":"AMOR Y LIBERTAD","roleParticipant1":"C","nomParticipant1":"MAILLIE KENDJI","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":2977109111,"titreOeuvre":"LES YEUX DE LA MAMA","roleParticipant1":"CA","nomParticipant1":"ERRAMI JOHAN    MERCURY GR","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":3501341911,"titreOeuvre":"MUJER I NEED A GIRL","roleParticipant1":"CA","nomParticipant1":"SALDIVIA FELIPE","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":3058787911,"titreOeuvre":"INTERLUDE LE PRISONNIER","roleParticipant1":"C","nomParticipant1":"CIOSI JEROME","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2943823211,"titreOeuvre":"LAISSEZ PASSER","roleParticipant1":"C","nomParticipant1":"REBILLAUD RENAUD","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2965587411,"titreOeuvre":"MAIN DU ROI","roleParticipant1":"C","nomParticipant1":"KENDOUSSI ADIL","durDif":null,"ajout":"CORRIGE","nbrDif":871830,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":3560846811,"titreOeuvre":"JE NOUS VEUX","roleParticipant1":"CA","nomParticipant1":"DUPRE MARC","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2986395211,"titreOeuvre":"C EST TROP","roleParticipant1":"CA","nomParticipant1":"ESPOSITO DAVIDE","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":2986369711,"titreOeuvre":"NO ME MIRES MAS","roleParticipant1":"CA","nomParticipant1":"KENDJI GIRAC","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":2937044211,"titreOeuvre":"JEUNE","roleParticipant1":"CA","nomParticipant1":"BLACK DANIEL EDWARD","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":3078390510,"titreOeuvre":"SI C ETAIT A REFAIRE","roleParticipant1":"C","nomParticipant1":"VENERUSO JACQUES","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":3560923011,"titreOeuvre":"MA FAILLE","roleParticipant1":"C","nomParticipant1":"COMPAGNON FLAVIEN","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2986567011,"titreOeuvre":"TU Y YO","roleParticipant1":"CA","nomParticipant1":"KENDJI GIRAC","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":2960333011,"titreOeuvre":"ME QUEMO","roleParticipant1":"CA","nomParticipant1":"KENDJI GIRAC","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":3560919211,"titreOeuvre":"LES YEUX AU CIEL","roleParticipant1":"C","nomParticipant1":"LISBONNE SILVIO","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":3577328911,"titreOeuvre":"A LA PLUS HAUTE BRANCHE","roleParticipant1":"CA","nomParticipant1":"PICARD DANIEL A","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":3560846911,"titreOeuvre":"TOUTES CES CHOSES","roleParticipant1":"CA","nomParticipant1":"DUPRE MARC","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":3560918711,"titreOeuvre":"L ETOILE","roleParticipant1":"C","nomParticipant1":"LISBONNE SILVIO","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":2833258111,"titreOeuvre":"LE CHANT DES SIRENES","roleParticipant1":"C","nomParticipant1":"FLO DELAVEGA","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"UNIVERSAL - Universal Music France","selection":true,"cdeUtil":"UNIVERSAL"},{"ide12":2945128511,"titreOeuvre":"PLUS QU AILLEURS","roleParticipant1":"CA","nomParticipant1":"CABREL FRANCIS","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"},{"ide12":6656641211,"titreOeuvre":"ORDINAIRE","roleParticipant1":"C","nomParticipant1":"CHARLEBOIS ROBERT","durDif":null,"ajout":"CORRIGE","nbrDif":784565,"libAbrgUtil":"SONYBMG - SONY BMG","selection":true,"cdeUtil":"SONYBMG"}],"last":false,"totalPages":9319,"totalElements":232965,"size":25,"number":0,"sort":[{"direction":"DESC","property":"sum(nbrDifEdit)","ignoreCase":false,"nullHandling":"NATIVE","unsafe":true,"descending":true,"ascending":false}],"numberOfElements":25,"first":true
          },

          gridColumns : [
            {
              id : 'iconIde12',
              name : '',
              sortable :false,
              type : 'clickable-icon',
              cell : {
                css : function (entry) {

                  return {
                    style : {
                      width : '50px'
                    }
                  }
                },

                cellTemplate: function (cellValue) {
                  var tempalte = '<span class="glyphicon glyphicon-trash" aria-hidden="true" title="Abandonner"></span>';

                  return '';
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
                  if(result !=undefined)
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
              type: 'text-centre',
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
                  if(result !=undefined)
                    return result ;
                  else
                    return "";
                }
              }
            },

            {
              id: 'participant',
              name: "Participant",
              sortable: true,
              type: 'text-centre',
              cell: {
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
              id: 'role',
              name: 'Rôle',
              sortable: true,
              type: 'text-centre',
              cell : {
                css : function (entry) {
                  return {
                    style : {
                      width : '50px'
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
                  var template = [
                    {event : 'delete-oeuvre',
                      template : tempalteTrash}];
                  return template;
                }
              }
            }
          ]
        },

        filter : {
          typeCMS : 'CMS France',
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
          typeCMS : 'FR',
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
        }
      }
    },

    created() {

      const customActions = {
        searchCatalogueRdo : {
          method : 'POST',
          url : process.env.CONTEXT_ROOT_PRIAM_CAT_RDO + 'app/rest/catalogue/search?page={page}&size={size}&sort={sort},{dir}'},
      }
      this.resource= this.$resource('', {}, customActions);
      this.findCatalogueByCriteria();
    },

    methods : {



      findCatalogueByCriteria() {
        if(this.filter.typeCMS == 'CMS France'){
          this.currentFilter.typeCMS = 'FR'
        } else {
          this.currentFilter.typeCMS = 'ANF'
        }
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
          })
          .catch(error => {
            alert("Erreur lors de la recherche !!! ")
          });
      },


      onDeleteOeuvre(row, column) {
        this.showPopupDeleteOeuvre = true;
        this.oeuvreToDelete = row;
        console.log('this.oeuvreToDelete => ide12 = ', this.oeuvreToDelete.ide12)

      },

      onYesDeleteOeuvre() {
        console.log('Deleting Oeuvre ide12 = ', this.oeuvreToDelete.ide12);
        this.showPopupDeleteOeuvre = false;
      },

      onAjouterOeuvre() {

      },

      retablirFiltre() {
        this.filter = {
          typeCMS : 'CMS France',
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
        this.resource.searchCatalogueRdo({page : pageNum - 1, size : pageSize,
          sort : sort, dir: dir}, this.currentFilter)
          .then(response => {
            return response.json();
          })
          .then(data => {
            this.catalogueGrid.gridData = data;
            this.catalogueGrid.gridData.number = data.number + 1;

          });


      },
    },


    components : {
      'priam-grid' : PriamGrid,
      'priam-navbar' : PriamNavbar,
      'modal': Modal,
      appFiltreCatalogue : FiltreCatalogue,
    }
  }

</script>

<style>

</style>
