/**
 * Created by benmerzoukah on 09/11/2017.
 */
import Vue from 'vue'
import VeeValidate from 'vee-validate';
import moment from 'moment';
import { Validator } from 'vee-validate';

Vue.use(VeeValidate, {
  locale: 'fr',
  dictionary: {
    fr: {
      attributes: {
        'rion.theorique' : 'Rion statuaire',
        'typeUtilisation' : "Type d'utilisation",
        'dateDebutProgramme' : 'Date de début',
        'dateFinProgramme' : 'Date de fin'
      },

      messages: {
        required : (e) => "Le champ '" + e + "' est obligatoire et non renseigné.",
        max: (e, n) => e + " ne peut pas contenir plus de " + n[0] + " caractères.",
        numeric: (e) => "Le champ '" + e +  "' ne peut contenir que des chiffres.",
        before: (e,n) => "La date de début doit être antérieure à la date de fin.",
        decimal: (e) =>  "Le champ '" + e +  "' ne peut contenir que des chiffres.",
        regex: (e) =>   "Le champ '" + e +  "' doit être au format numérique."
      }
    }
  }
});
Validator.installDateTimeValidators(moment);
