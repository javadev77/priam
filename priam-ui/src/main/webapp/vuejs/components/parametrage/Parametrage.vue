<template>

  <div class="container-fluid">
    <div class="navbar navbar-default navbar-sm breadcrumb">
      <div class="titre-page">
        <span>Paramétrage</span>
      </div>
    </div>
    <div class="container-fluid sacem-formula">

      <div class="panel panel-default">
        <div class="panel-heading">
          <h5 class="panel-title">
            <!--<strong>Critères de Recherche</strong>-->
            <a>Paramétrage par défaut</a>
            <span class="pull-left collapsible-icon bg-ico-btn-info"></span>
          </h5>

        </div>

        <div class="panel-collapse">

          <div class="panel-body">

            <div class="row">

              <div class="form-group col-md-1"></div>

              <div class="form-group col-md-15">
                <label class="col-md-9 control-label blueText text-right">Nombre de lignes affichées par page</label>
                <div class="col-md-15">
                  <select :value="userPageSize" @change="pageSizeChanged($event.target.value)">
                    <option v-for="s in this.$store.getters.itemsPerPage" :value="s">
                      {{ s }}
                    </option>
                  </select>
                </div>
              </div>
            </div>

            <div class="row">

              <div class="form-group col-md-1"></div>

              <div class="form-group col-md-15">
                <label class="col-md-9 control-label blueText text-right">Famille par défaut</label>
                <div class="col-md-15">
                  <select :value="familleSelected.id" @change="familleChanged($event.target.value)">
                    <option v-for="f in this.$store.getters.familleOptions" :value="f.id">
                      {{ f.value }}
                    </option>
                  </select>
                </div>
              </div>
            </div>

          </div>

          <div class="row formula-buttons">
            <button class="btn btn-default btn-primary pull-right" type="button" @click="retablir()">Annuler</button>
            <button class="btn btn-default btn-primary pull-right" type="button" @click="enregistrer()">Enregistrer</button>
          </div>

        </div>


      </div>
    </div>
  </div>

</template>

<script>

  import store from '../../store/store';

  export default {

      created() {
        const customActions = {
          enregistrerParametrage : {method: 'PUT', url: 'app/rest/general/parametres'}
        }

        this.resource = this.$resource('', {}, customActions);
      },
      data() {

          return {
            userPageSize : this.$store.getters.userPageSize,
            familleSelected : this.$store.getters.userFamille
          }

      },

      methods :  {

        pageSizeChanged(value) {
          this.userPageSize = Number.parseInt(value);
        },

        familleChanged(value) {

          var familles = this.$store.getters.familleOptions;
          for(var i = 0; i < familles.length; i++) {
            if(familles[i].id == value)
            {
              this.familleSelected = familles[i];
              break;
            }
          }

        },

        enregistrer() {

          var $this = this;

          var parametrage = {
              'USER_PAGE_SIZE' : $this.userPageSize,
              'USER_FAMILLE' : $this.familleSelected.id+","+$this.familleSelected.value
          }

          this.resource.enregistrerParametrage(parametrage)
            .then(response => {
              store.commit('SELECT_PAGE_SIZE', $this.userPageSize);
              store.commit('SELECT_FAMILLE', $this.familleSelected.id+","+$this.familleSelected.value);
          });

        },

        retablir() {
          this.userPageSize = this.$store.getters.userPageSize;
        }
      }

  }
</script>

