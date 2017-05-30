/**
 * Created by benmerzoukah on 19/05/2017.
 */
import Vue from 'vue'

Vue.directive('status-color', {
    bind(el, binding, vnode) {
        el.style.color= 'black';
        el.style.backgroundColor = binding.value;
    },

    update(el, binding, vnode, oldVnode) {
        if(binding.oldValue != binding.value) {
            el.style.color= 'black';
            el.style.backgroundColor = binding.value;
        }
    }

});

Vue.directive('numeric-align', {
  bind(el, binding, vnode) {
    el.style.textAlign = 'right';
  },

  update(el, binding, vnode, oldVnode) {
    if(binding.oldValue != binding.value) {
      el.style.textAlign = 'right';
    }
  }

});
