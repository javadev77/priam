import Vue from 'vue'
import VueRouter from 'vue-router';
import VueResource from 'vue-resource';

import App from './App.vue'
import { routes } from './routes';
import { fetchInitData } from './store/actions';
import store from './store/store';
//import 'select2';
//import 'select2/dist/css/select2.css'

Vue.use(VueRouter);
Vue.use(VueResource);

Vue.directive('status-color', {
    bind(el, binding, vnode) {
      el.style.color= 'black';
      el.style.backgroundColor = binding.value;
   },

});

/*Vue.directive('sacem-combo', {
    bind(el, binding, vnode) {

      $(() => {
        $(el).select2('destroy') ;
        $(el).select2({dropdownAutoWidth: 'true', minimumInputLength: 0, minimumResultsForSearch: -1, data: binding.options});
        $(el).select2('container').css('display', 'block');
        $(el).addClass('select select-primary');
      });
    }
});*/

/*Vue.component('select2', {
  props: ['options', 'value'],
  template: '#select2-template',
  mounted: function () {
    var vm = this;
    $(() => {
      $(this.$el)
      // init select2
        .select2({dropdownAutoWidth: 'true', minimumInputLength: 0, minimumResultsForSearch: -1,data: this.options})
        .addClass('select select-primary')
        .val(this.value)
        .trigger('change')
        // emit event on change.
        .on('change', function () {
          vm.$emit('input', this.value);
          vm.$emit('change-list');
        });
    });
  },
  watch: {
    value: function (value) {
      // update value
      $(() => {
        $(this.$el).val(value).trigger('change');

      });
    },
    options: function (options) {
      // update options
      $(() => {
        $(this.$el).select2({
          dropdownAutoWidth: 'true',
          minimumInputLength: 0,
          minimumResultsForSearch: -1,
          data: options
        });
      });
    }
  },
  destroyed: function () {
    $(this.$el).off().select2('destroy')
  }
})*/

Vue.http.options.root="/priam"

const router = new VueRouter({
  routes
});

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

/*var vm = new Vue({
  el: '#app',
  template: '#demo-template',
  data: {
    selected: 2,
    options: [
      { id: 1, text: 'Hello' },
      { id: 2, text: 'World' }
    ]
  }
});*/

fetchInitData(store);
