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
        'dateFinProgramme' : 'Date de fin',
        'periodeEntree' : 'période d\'entrée',
        'periodeRenouvellement' : 'période de renouvellement',
        'periodeSortie' : 'période de sortie',
        'periodeEvenement' : 'période d\'évévenement'


      },

      messages: {
        required : (e) => "Le champ '" + e + "' est obligatoire et non renseigné.",
        max: (e, n) => e + " ne peut pas contenir plus de " + n[0] + " caractères.",
        numeric: (e) => "Le champ '" + e +  "' ne peut contenir que des chiffres.",
        before: (e,n) => "La date de début doit être antérieure à la date de fin.",
        decimal: (e) =>  "Le champ '" + e +  "' ne peut contenir que des chiffres."
      }
    }
  }
});
Validator.installDateTimeValidators(moment);

Validator.extend('periodeRule', {
  getMessage: field => 'La date de début de la ' + field + ' doit être antérieure à la date de fin.',
  validate: (value, format) => {
    console.log("The periodRule value = " + value);
    if(value.dateDebut == null || value.dateFin==null) {
      return true;
    }
    const dateDebut = moment(value.dateDebut, format, true);
    const dateFin = moment(value.dateFin, format, true);
    // if either is not valid.
    if (! dateDebut.isValid() || ! dateFin.isValid()) {
      return false;
    }
    return dateDebut.isBefore(dateFin) || (dateDebut.isSame(dateFin));
  }
});

