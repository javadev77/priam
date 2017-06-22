/**
 * Created by fandis on 21/06/2017.
 */
!function (e, n) {
  "object" == typeof exports && "undefined" != typeof module ? module.exports = n() : "function" == typeof define && define.amd ? define(n) : (e.__locale__fr = e.__locale__fr || {}, e.__locale__fr.js = n())
}(this, function () {
  "use strict";
  var e = {
    _default: function (e) {
      return e + " n'est pas valide."
    }, after: function (e, n) {
      return e + " doit être postérieur à " + n[0] + "."
    }, alpha_dash: function (e) {
      return e + " ne peut contenir que des caractères alpha-numériques, tirets ou soulignés."
    }, alpha_num: function (e) {
      return e + " ne peut contenir que des caractères alpha-numériques."
    }, alpha_spaces: function (e) {
      return e + " ne peut contenir que des lettres ou des espaces."
    }, alpha: function (e) {
      return e + " ne peut contenir que des lettres."
    }, before: function (e, n) {
      return e + " doit être antérieur à " + n[0] + "."
    }, between: function (e, n) {
      return e + " doit être compris entre " + n[0] + " et " + n[1] + "."
    }, confirmed: function (e, n) {
      return e + " ne correspond pas à " + n[0] + "."
    }, credit_card: function (e) {
      return e + " est invalide."
    }, date_between: function (e, n) {
      return e + " doit être situé entre " + n[0] + " et " + n[1] + "."
    }, date_format: function (e, n) {
      return e + " doit être au format " + n[0] + "."
    }, decimal: function (e, n) {
      void 0 === n && (n = ["*"]);
      var t = n[0];
      return e + " doit être un nombre et peut contenir " + ("*" === t ? "" : t) + " décimales."
    }, digits: function (e, n) {
      return e + " doit être un nombre entier de " + n[0] + " chiffres."
    }, dimensions: function (e, n) {
      return e + " doit avoir une taille de " + n[0] + " pixels par " + n[1] + " pixels."
    }, email: function (e) {
      return e + " doit être une adresse e-mail valide."
    }, ext: function (e) {
      return e + " doit être un fichier valide."
    }, image: function (e) {
      return e + " doit être une image."
    }, in: function (e) {
      return e + " doit être une valeur valide."
    }, ip: function (e) {
      return e + " doit être une adresse IP."
    }, max: function (e, n) {
      return e + " ne peut pas contenir plus de " + n[0] + " caractères."
    }, max_value: function (e, n) {
      return e + " doit avoir une valeur de " + n[0] + " ou moins."
    }, mimes: function (e) {
      return e + " doit avoir un type MIME valide."
    }, min: function (e, n) {
      return e + " doit contenir au minimum " + n[0] + " caractères."
    }, min_value: function (e, n) {
      return e + " doit avoir une valeur de " + n[0] + " ou plus."
    }, not_in: function (e) {
      return e + " doit être une valeur valide."
    }, numeric: function (e) {
      return e + " ne peut contenir que des chiffres."
    }, regex: function (e) {
      return e + " est invalide."
    }, required: function (e) {
      return "Le champe " +e+ "est obligatoire et non renseigné."
    }, size: function (e, n) {
      return e + " doit avoir un poids inférieur " + n[0] + " KB."
    }, url: function (e) {
      return e + " n'est pas une URL valide."
    }
  }, n = {name: "fr", messages: e, attributes: {}};
  return "undefined" != typeof VeeValidate && VeeValidate && (VeeValidate.Validator, !0) && VeeValidate.Validator.addLocale(n), n
});
