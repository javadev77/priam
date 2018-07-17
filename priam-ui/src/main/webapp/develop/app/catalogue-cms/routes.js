/**
 * Created by benmerzoukah on 08/11/2017.
 */
import * as components from './components';

export default [

  { path: '/catalogue', name: 'catalogue-cms', component: components.EcranCatalogueCMS },

  { path: '/catalogue/journal', name: 'journal', component: components.EcranJournalCatalogueCMS },

  { path: '/catalogue/stat', name: 'stat', component: components.EcranStatCatalogueCMS }

]
