<template>
  <div class="container-fluid sacem-formula">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Informations</a>
          <span class="pull-left collapsible-icon bg-ico-editer-courrier"></span>
        </h5>
      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h5 class="panel-title">
                <a>Résultats</a>
                <span class="pull-left collapsible-icon bg-ico-tablerow"></span>
              </h5>
            </div>
            <div class="panel-collapse">
              <div class="result-panel-body panel-body" style="height:600px; overflow-y:scroll;">
                <log-grid
                  v-if="logGrid.logGridData.content"
                  :data="logGrid.logGridData"
                  :columns="logGrid.logGridColumns"
                  noResultText="Aucun résultat."
                  @load-page="loadPage"
                  @on-sort="onSort">
                </log-grid>

              </div>
              <div class="row formula-buttons">
                <button class="btn btn-default btn-primary pull-right width-140" type="button" @click="$emit('cancel')">Fermer</button>
                <div v-if="isRightRELENR && fichier.statutEnrichissementFV === 'ERROR_SRV_ENRICHISSEMENT'">
                  <button class="btn btn-default btn-primary pull-right width-140" type="button" @click.prevent="relancer()">Relancer</button>
                </div>
                </div>
            </div>
          </div>

          <modal v-if="showModalRelancer">
            <span class="homer-prompt-q control-label" slot="body">
              Etes-vous sûr de vouloir relancer l'enrichissement ?<br>
            </span>
            <template slot="footer">
              <button class="btn btn-default btn-primary pull-right no" @click="showModalRelancer = false">Non</button>
              <button class="btn btn-default btn-primary pull-right yes" @click="onYesConfirmRelanceEnrichissement">Oui</button>
            </template>
          </modal>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Grid from '../ui/Grid';
  import programmeMixins from '../../../common/mixins/programmeMixin';
  import Modal from '../../../common/components/ui/Modal.vue';

    export default {
        name: "LogEnrichissement",
        mixins: [programmeMixins],
        props: {
          fichier : Object
          // idFichier: ''
        },
      data() {
        return {
          resource : {},
          defaultPageable : {
            page : 1,
            sort : 'date',
            dir : 'ASC',
            size : this.$store.getters.userPageSize
          },
          logGrid: {
            logGridColumns: [
              {
                id: 'date',
                name: "Date",
                sortable: true,
                type: 'date',
                cell: {
                  css: function (entry) {

                    return {style: {'text-align' : 'justify'}}
                  }
                }
              },
              {
                id: 'detail',
                name: "Détail",
                sortable: true,
                type: 'long-text',
                cell: {
                  toText: function (entry) {
                    var result = entry;
                    if (result != undefined)
                      return result;
                    else
                      return "";
                  }
                }
              }
            ],
            logGridData: {

            },
            searchQuery: ''
          },
          showModalRelancer: false,
        }
      },

      created() {
        const customActions = {
          findEnrichissementLogByFichier : {method : 'GET', url : process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/enrichissement/fichier/{idFichier}/log?page={page}&size={size}&sort={sort},{dir}'},
          relancerEnrichissement : {method : 'PUT', url : process.env.CONTEXT_ROOT_PRIAM_FV + 'app/rest/enrichissement/fichier/relancer'}
        }
        this.resource = this.$resource('', {}, customActions);
        this.rechercherEvenements();
      },

      methods: {
        onSort(currentPage, pageSize, sort) {

          debugger;
          this.launchRequest(currentPage, pageSize, sort.property, sort.direction);

          this.defaultPageable.sort = sort.property;
          this.defaultPageable.dir = sort.direction;
        },
        loadPage: function(pageNum, size, sort) {
          this.defaultPageable.size = size;
          let pageSize = this.defaultPageable.size;

          this.launchRequest(pageNum, pageSize, sort.property, sort.direction);

        },
        launchRequest(pageNum, pageSize, sort, dir) {
          debugger;

          /*this.resource.findEnrichissementLogByFichier({idFichier:  this.idFichier, page : pageNum - 1, size : pageSize,*/
          this.resource.findEnrichissementLogByFichier({idFichier:  this.fichier.id, page : pageNum - 1, size : pageSize,
            sort : sort, dir: dir})
            .then(response => {
              debugger;
              return response.json();
            })
            .then(data => {
              debugger;
              console.log("data : " + data);
              this.logGrid.logGridData = data;
              this.logGrid.logGridData.number = data.number + 1;
            });

        },

        rechercherEvenements() {
          debugger;
          this.launchRequest(this.defaultPageable.page, this.defaultPageable.size,
            this.defaultPageable.sort, this.defaultPageable.dir);
        },

        relancer(){
          this.showModalRelancer = true;
        },

        onYesConfirmRelanceEnrichissement(){
          this.showModalRelancer = false;
          var idFichier = this.fichier.id;
          if (idFichier != null || idFichier !== "") {
            /*this.resource.relancerEnrichissement({id : this.idFichier})*/
            this.resource.relancerEnrichissement({id : idFichier})
              .then(response => {
                return response.json();
              })
              .then(data => {
                console.log('data = ' + data);



              });
          }

          /*const numProgramme = this.$route.params.numProg;
          if (numProgramme !== null || numProgramme !== "") {
            this.resource.findFichiersAffecte({numProg: numProgramme})
              .then(response => {
                return response.json();
              })
              .then(data => {
                for (let key in data) {
                  this.fichersAvantDesaffectation.push(data[key].id);
                }
                this.fichiersDesaffectes = {
                  numProg: numProgramme,
                  allDesaffecte: true,
                  fichersAvantDesaffectation: this.fichersAvantDesaffectation
                }
                this.resource.desaffecterProg(this.fichiersDesaffectes)
                  .then(response => {
                    return response.json();
                  })
                  .then(data => {
                    console.log("Déaffactation ok");
                    this.showModalDesactiver = false;
                    this.$router.push({name: 'programme'});

                  })
                  .catch(response => {
                    console.error(response);
                    alert("Erreur technique lors de désaffectation des fichiers du programme !! ");
                  });

              });*/
          }
      },

      computed :{
        isRightRELENR() {
          return this.hasRight('RELENR');
        }
      },

        components: {
          logGrid: Grid,
          modal: Modal
        }
    }
</script>

<style scoped>

</style>
