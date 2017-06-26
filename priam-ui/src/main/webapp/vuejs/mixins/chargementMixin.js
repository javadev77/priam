/**
 * Created by benmerzoukah on 26/06/2017.
 */
module.exports = {

  methods : {

    getStatutByCode(statutCode) {
      const result  = this.$store.getters.statutProgramme.find(function (element) {
        return element.code === statutCode;
      });
      return result !== undefined ? result.libelle : '';
    },

    getFamilleByCode(familleCode) {
      var result  = this.$store.getters.famille.find(function (element) {
        return element.id === familleCode;
      });
      return result !== undefined && result.value;
    },

    getTypeUtilisationByCode(typeUtilCode) {
      var result  = this.$store.getters.typeUtilisation.find(function (element) {
        return element.id === typeUtilCode;
      });
      return result !== undefined && result.value;
    },

    getModeRepartitionByCode(modeRepartCode) {
      var result  = this.$store.getters.typeRepart.find(function (element) {
        return element.id === modeRepartCode;
      });
      return result !== undefined && result.value;
    }


  }
}
