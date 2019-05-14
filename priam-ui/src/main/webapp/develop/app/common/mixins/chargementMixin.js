/**
 * Created by benmerzoukah on 26/06/2017.
 */
module.exports = {

  methods : {

    getStatutProgrammeByCode(statutCode) {
      const result  = this.$store.getters.statutProgramme.find(function (element) {
        return element.code === statutCode;
      });
      return result;
    },

    getStatutFichierByCode(statutCode) {
      const result  = this.$store.getters.statut.find(function (element) {
        return element.code === statutCode;
      });
      return result;
    },

    getFamilleByCode(familleCode) {
      var result  = this.$store.getters.famille.find(function (element) {
        return element.id === familleCode;
      });
      return result;
    },

    getTypeUtilisationByCode(typeUtilCode) {
      var result  = this.$store.getters.typeUtilisation.find(function (element) {
        return element.id === typeUtilCode;
      });
      return result;
    },

    getModeRepartitionByCode(modeRepartCode) {
      var result  = this.$store.getters.modeRepartition.find(function (element) {
        return element.id === modeRepartCode;
      });
      return result;
    },

    getTerritoireByCode(code) {
      var result  = this.$store.getters.territoire.find(function (element) {
        return element.id === code;
      });
      return result;
    },

     getLibelleRionById(id) {

       let result = this.$store.getters.rionsSaref.find(function (element) {
         return element.id === id;
       });

       return result !== undefined ? result.value : '';

     },

    getEtatOeuvre(code) {
      var result = this.$store.getters.etatOeuvre.find(function (element) {
        return element.code === code;
      })
      return result;
    }


  }
}
