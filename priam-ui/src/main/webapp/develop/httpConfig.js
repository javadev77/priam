/**
 * Created by benmerzoukah on 13/11/2017.
 */
import Vue from 'vue'
import VueResource from 'vue-resource';


Vue.use(VueResource);


if(process.env.DEBUG_MODE) {
  // Only for DEV MODE
  Vue.http.headers.common['Access-Control-Allow-Origin'] = 'http://P93141.sacem.fr:8085';
  Vue.http.headers.common['Access-Control-Request-Method'] = '*';

  Vue.http.interceptors.push((request, next) => {
    request.credentials = true;
    next();
  });

} else {
  //Vue.http.options.root = process.env.CONTEXT_ROOT;

  Vue.http.interceptors.push((request, next) => {
    request.credentials = true;
    next();
  });
}
