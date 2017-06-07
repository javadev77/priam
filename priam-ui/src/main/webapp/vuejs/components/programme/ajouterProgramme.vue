<template>
  <div class="container">
    <form>
      <div class="row">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
          <h1>Création programme</h1>
          <hr>
          <div class="form-group">
            <label for="nomProgramme">Nom programme</label>
            <input
              type="text"
              id="nomProgramme"
              class="form-control"
              v-model="programmeData.nomProgramme">
          </div>
          <div class="row">
            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 from-group">
              <label for="rionTheorique">>Rion théorique</label>
              <select
                id="rionTheorique"
                class="form-control"
                v-model="programmeData.rionTheorique">
                <option>Rion1</option>
                <option>Rion2</option>
                <option>Rion3</option>
                <option>Rion4</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 from-group">
              <label for="famille">Famille</label>
              <select
                id="famille"
                class="form-control"
                v-model="programmeData.famille">
                <option>famille1</option>
                <option>famille2</option>
                <option>famille3</option>
                <option>famille4</option>
              </select>
            </div>
          </div>
          <div class="row">
            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 from-group">
              <label for="typeUtilisation">Type d'utilisation</label>
              <select
                id="typeUtilisation"
                class="form-control"
                v-model="programmeData.typeUtilisation">
                <option>TypeUtilisation1</option>
                <option>TypeUtilisation2</option>
                <option>TypeUtilisation3</option>
                <option>TypeUtilisation4</option>
              </select>
            </div>
          </div>

          <div class="row">
            <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 form-group">
              <label for="TypeRepartitionOeuvre">
                <input
                  type="radio"
                  id="TypeRepartitionOeuvre"
                  value="Oeuvre"> Oeuvre
              </label>
              <label for="TypeRepartitionOeuvreAyantDroit">
                <input
                  type="radio"
                  id="TypeRepartitionOeuvreAyantDroit"
                  value="Ayant droit"> Ayant droit
              </label>
            </div>
          </div>
        </div>
      </div>


      <hr>
      <div class="row">
        <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
          <button
            class="btn btn-primary">Submit!
          </button>
        </div>
      </div>
    </form>
    <hr>
    <div class="row">
      <div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h4>Your Data</h4>
          </div>
          <div class="panel-body">
            <p>Nom programme:{{programmeData.nomProgramme}}</p>
            <p>famille:{{programmeData.famille}}</p>
            <p>Type d'utilisation:{{programmeData.typeUtilisation}}</p>
            <p>Rion théorique:{programmeData.rionTheorique} </p>
            <p>type répartition{{programmeData.typeRepartition}}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data(){
        return{
            programmeData{
              nomProgramme: '',
              rionTheorique:'',
              famille:'',
              typeUtilisation:'',
              typeRepartition:''
            }

        }
    }
  }
</script>
<style>

</style>
