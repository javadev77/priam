/**
 * Created by benmerzoukah on 08/11/2017.
 */

import * as components from './components';

export default [

  { path: '/', redirect: '/programme' },

  { path: '/chargement', name :'chargement', component: components.Chargement },

  //{ path: '/chargement/fichiers', name :'fichiers', component: components.Chargement },

  { path: '/programme', name: 'programme', component : components.ListeProgramme},

  //{ path: '/programme/list', name :'listeProg', component: components.ListeProgramme },

  { path: '/parametrage', name: 'parametrage', component: components.Parametrage }

]
