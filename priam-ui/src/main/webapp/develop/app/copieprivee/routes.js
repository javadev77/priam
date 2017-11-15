/**
 * Created by benmerzoukah on 08/11/2017.
 */

import * as components from './components';

export default [

  { path: '/programme/affectation/:numProg', name: 'affectation', component: components.EcranAffectationCP },
  { path: '/programme/selection/:numProg', name: 'selection', component: components.EcranSelectionCP }

]
