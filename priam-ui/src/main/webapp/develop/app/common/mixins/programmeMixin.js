/**
 * Created by embouazzar on 05/09/2017.
 */

module.exports = {

  methods : {
    hasRight(right) {
      let rights = this.$store.getters.getCurrentUser.rights;
      var rightEle = rights.find(function (elem) {
        return elem === right;
      });
      debugger;
      return rightEle !== undefined;
    }
  }
}
