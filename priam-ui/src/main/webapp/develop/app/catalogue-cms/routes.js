/**
 * Created by benmerzoukah on 08/11/2017.
 */
import * as components from './components';

export default [

  { path: '/catalogue-cms', name: 'catalogue-cms', redirect : '/catalogue',
    children: [
      {
        path: 'catalogue',
        name: 'Catalogue',
        component: components.EcranCatalogueCMS
      },

      {
        path: 'journal',
        name: 'Journal',
        component: components.EcranCatalogueCMS
      }
    ]
  }
]
