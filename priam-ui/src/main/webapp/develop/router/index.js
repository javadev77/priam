/**
 * Created by benmerzoukah on 08/11/2017.
 */
import Vue from 'vue';
import Router from 'vue-router';
import { routes } from '../app';
import store from './../store';

Vue.use(Router);


var _routingPromise = null;
var _showCounts = 0;

var $rootScope = {};
$rootScope.showRouting = function(delay){
  _showCounts = _showCounts < 0 ? 1 : (_showCounts + 1);
  $rootScope.cancelRoutingHide();
  if(Number.isInteger(delay) && delay > 0){
    setTimeout(function(){store.commit('ROUTING', true);}, delay, true);
  }else{
    store.commit('ROUTING', true);
  }
  //$log.log('showRouting-' + _showCounts);
};

$rootScope.cancelRoutingHide = function(){
  if(_routingPromise !== null){
    clearTimeout(_routingPromise);
    _routingPromise = null;
  }
};

$rootScope.hideRouting = function(delay){
  _showCounts--;
  //$log.log('hideRouting-' + _showCounts);
  $rootScope.cancelRoutingHide();
  if(_showCounts < 1){
    _routingPromise = setTimeout(function(){
      store.commit('ROUTING', false);
      _routingPromise = null;
    }, delay || 225, true);
  }
};


const router = new Router({
  mode: 'hash',
  routes: routes
});


router.beforeEach((to, from, next) => {
  // If this isn't an initial page load.
  //if (to.name) {
    // Start the route progress bar.
    $rootScope.showRouting(30);

  //}
  next()
});

router.afterEach((to, from) => {
  // Complete the animation of the route progress bar.
  $rootScope.hideRouting(250);
});

export default router;
