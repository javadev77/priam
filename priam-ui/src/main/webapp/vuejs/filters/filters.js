/**
 * Created by benmerzoukah on 03/07/2017.
 */
import Vue from 'vue'
import moment from 'moment'

Vue.filter('dateAffectation', function (date) {
    return date !== null && date !== undefined ? "le " +  moment(date).format("DD/MM/YYYY à HH:mm") : '';
});

Vue.filter('numberFormat', function (number) {
    return new Intl.NumberFormat("fr-FR").format(number);
});
