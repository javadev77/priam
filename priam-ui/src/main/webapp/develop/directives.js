/**
 * Created by benmerzoukah on 09/01/2018.
 */

import Vue from 'vue';

//Register a global custom directive called `v-focus`
Vue.directive('focus', {
  // When the bound element is inserted into the DOM...
  inserted: function (el) {
    // Focus the element
    el.focus()
  }
});

Vue.directive('duree', {

  // When the bound element is inserted into the DOM...
  update: function (el) {
    // Focus the element
    let jours = Math.floor( el.value / 86400);
    let reste = el.value % 86400;
    let hours = Math.floor( reste / 3600);
    reste = reste % 3600;
    let minutes = Math.floor(reste / 60);
    let seconds = reste % 60;


    let result = ((jours < 10) ? '0'+jours : jours) + 'j ' +((hours < 10) ? '0'+hours : hours)+"h "+((minutes < 10) ? '0' + minutes: minutes)+"m "+ ((seconds < 10) ? '0'+seconds : seconds) + "s";
    el.value = result;
  }
});
