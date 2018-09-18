module.exports = {

  methods: {

    getLibelleTypeCatalogue(id) {
      var result = this.$store.getters.libelleTypeCatalogue.find(function (element) {
        return element.id == id;
      })
      return result.value;
    },

    getLibelleTypeEvenement(id) {
      var result = this.$store.getters.libelleTypeEvenement.find(function (element) {
        return element.id == id;
      })
      return result.value;
    }

  }

}
