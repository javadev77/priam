/**
 * Created by benmerzoukah on 08/11/2017.
 */

import * as components from './components';

export default [

  { path: '/', redirect: '/programme/listePrg' },

  { path: '/chargement', name :'chargement', component: components.Chargement },

  { path: '/programme', name: 'programme', component : components.ListeProgramme,
    children: [
      {
        path: 'listePrg',
        name: 'ListePrg',
        component: components.ListeProgramme
      }
    ]
  },

  { path: '/parametrage', name: 'parametrage', component: components.Parametrage }

]
