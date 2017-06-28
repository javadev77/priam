<template>
  <div class="container-fluid sacem-formula">

    <!--En tete Panel-->
    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title"  @click="isCollapsed = !isCollapsed">
          <a>Programme</a>
          <span class="pull-left collapsible-icon"><img src="static/images/iconescontextes/btninformation.gif" alt=""/></span>
          <span class="pull-right fa" :class="{'fui-triangle-up' : isCollapsed,  'fui-triangle-down' : !isCollapsed}"></span>
        </h5>
      </div>

      <!--Body Panel-->
      <div class="panel-collapse" :class="{collapse : isCollapsed}">
        <div class="panel-body">
          <div class="row">

            <div class="col-sm-2">
              <span class="pull-right">N° programme</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.numProg }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Statut</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ getStatutProgrammeByCode(programmeInfo.statut) !== undefined ? getStatutProgrammeByCode(programmeInfo.statut).libelle : '' }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Famille</span>
            </div>
            <div class="col-sm-3">
              <strong>{{ getFamilleByCode(programmeInfo.famille) !== undefined ? getFamilleByCode(programmeInfo.famille).value : '' }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Nb fichiers affectés</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.fichiers }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Créé par</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.usercre }}</strong>
            </div>
          </div>

          <br/>
          <!-- 2 eme ligne -->
          <div class="row">

            <div class="col-sm-2">
              <span class="pull-right">Nom</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.nom }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Rion théorique</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.rionTheorique }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Type d'utilisation</span>
            </div>
            <div class="col-sm-3">
              <strong>{{ getTypeUtilisationByCode(programmeInfo.typeUtilisation) !== undefined ? getTypeUtilisationByCode(programmeInfo.typeUtilisation).value : '' }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Mode répartition</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ getModeRepartitionByCode(programmeInfo.typeRepart) !== undefined ?  getModeRepartitionByCode(programmeInfo.typeRepart).value : '' }}</strong>
            </div>

            <div class="col-sm-2">
              <span class="pull-right">Date création</span>
            </div>
            <div class="col-sm-2">
              <strong>{{ programmeInfo.dateCreation }}</strong>
            </div>
          </div>
        </div>
      </div>

    </div>



    <div class="panel panel-default">
      <div class="panel-heading">
        <h5 class="panel-title">
          <a>Critères de recherche</a>
          <span class="pull-left collapsible-icon formula-criteria-search"></span>
          <span class="pull-right"></span>
        </h5>


      </div>
      <div class="panel-collapse">
        <div class="panel-body">
          <form class="form-horizontal" role="form">
            <div class="row">
              <div class="col-sm-2">
                <label class="control-label pull-right">Famille</label>
              </div>
              <div class="col-sm-2">

                <v-select :searchable="false" label="value" v-model="familleSelected" :options="familleOptions" :on-change="loadTypeUtilisation">
                </v-select>
              </div>

              <div class="col-sm-2">
                <label class="control-label pull-right">Type d'utilisation</label>
              </div>
              <div class="col-sm-4">
                <v-select :searchable="false" label="value" v-model="typeUtilisationSelected" :options="typeUtilisationOptions">
                </v-select>
              </div>

              <div class="col-sm-2">
                <label class="control-label pull-right">Statut</label>
              </div>
              <div class="col-sm-2">
                <v-select :searchable="false" label="value" v-model="statutSelected" :options="statutAffectationOptions">
                </v-select>
              </div>

            </div>

          </form>
        </div>
      </div>
    </div>


    <div class="row formula-buttons">
      <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Rétablir</button>
      <button class="btn btn-default btn-primary pull-right" type="button" @click="rechercher()">Rechercher</button>
    </div>

  </div>

</template>

<script>

  import chargementMixins from '../../mixins/chargementMixin'
  import vSelect from '../common/Select.vue';

  export default {

      mixins: [chargementMixins],

      data() {
          return {
            isCollapsed : false,
            resource: {},
            programmeInfo : {},
            familleSelected : null,
            typeUtilisationSelected : null,
            statutSelected : null,

            inputChgtCriteria : {
              familleCode : '',
              typeUtilisationCode : '',
              statutCode         : []
            }

          }
      },


      mounted() {
          console.log("router params numProg = " + this.$route.params.numProg)
          const customActions = {
              findByNumProg : {method : 'GET', url : 'app/rest/programme/numProg/{numProg}'},
              findAllFichiers : {method : 'POST', url :'app/rest/chargement/allFichiers'}
          }
          this.resource= this.$resource('', {}, customActions);


          this.resource.findByNumProg({numProg:  this.$route.params.numProg})
            .then(response => {
                return response.json();
            })
            .then(data => {
              this.programmeInfo = data;
              this.initData();
          });
      },

      computed : {
        familleOptions() {
          return this.$store.getters.familleOptions;
        },

        typeUtilisationOptions() {
          return this.$store.getters.typeUtilisationOptions;
        },

        statutAffectationOptions() {

          var options =   this.statutFichier().map(status => {
                return {
                  id : status.code,
                  value : status.libelle
              }
          });
          options.unshift({id : 'ALL', value : 'Tous'})

          console.log("tab="+options);

          return options;
        }
      },

      methods : {

        statutFichier() {
          var filtredStatut = this.$store.getters.statut
            .filter(elem => {
              return elem.code === 'AFFECTE' || elem.code === 'CHARGEMENT_OK'
            });

          return filtredStatut;
        },


        loadTypeUtilisation(val) {
          //if(this.familleSelected !== val ) {
              this.familleSelected = val;
              this.$store.dispatch('loadTypeUtilisation', val);
              //this.typeUtilisationSelected = {'id' : 'ALL', 'value': 'Tous'};
          //}

          if(this.typeUtilisationSelected == null && this.programmeInfo.statut === 'CREE') {
            this.typeUtilisationSelected = this.getTypeUtilisationByCode(this.programmeInfo.typeUtilisation);

          } else {
            this.typeUtilisationSelected = {id : 'ALL', value: 'Tous'};
          }

        },

        initData() {
            console.log("this.programmeInfo="+this.programmeInfo.statut)
            if(this.programmeInfo !== null && this.programmeInfo.statut === 'CREE') {

                this.familleSelected = this.getFamilleByCode(this.programmeInfo.famille);
                this.statutSelected = { id : 'ALL', value : 'Tous'};

            } else {
                this.familleSelected = {id : 'ALL', value : 'Toutes'}
                let statutFichier = this.getStatutFichierByCode('AFFECTE');
                this.statutSelected = { id : statutFichier.code, value : statutFichier.libelle};
            }

        },

        rechercher() {
          this.inputChgtCriteria.typeUtilisationCode = this.typeUtilisationSelected.id;
          this.inputChgtCriteria.familleCode = this.familleSelected.id;
          let statusCode = this.statutSelected.id;
          if(statusCode === 'ALL') {
            this.inputChgtCriteria.statutCode = this.statutFichier().map(status => {
                return status.code;
            });
          } else {
            let statutFichier = this.getStatutFichierByCode(statusCode);
            this.inputChgtCriteria.statutCode = [statutFichier.code];
          }


          this.resource.findAllFichiers(this.inputChgtCriteria)
            .then(response => {
              return response.json();
            })
            .then(data => {
              console.log(data);
              //this.priamGrid.gridData = data;
              //this.priamGrid.gridData.number = data.number + 1;

            });

        },

        retablir() {

        }


      },

      components : {
        vSelect : vSelect
      }


  }
</script>
