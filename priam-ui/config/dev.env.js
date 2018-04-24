var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

/*module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  DEBUG_MODE: true,

  CONTEXT_ROOT_PRIAM_COMMON : '"http://localhost:8080/priam-common/"',
  CONTEXT_ROOT_PRIAM_CP : '"http://localhost:8080/priam-cp/"',
  CONTEXT_ROOT_PRIAM_CMS : '"http://localhost:8080/priam-cms/"'
})*/

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  DEBUG_MODE: true,

  CONTEXT_ROOT_PRIAM_COMMON : '"http://localhost:8080/priam-common-embouazzar/"',
  CONTEXT_ROOT_PRIAM_CP : '"http://localhost:8080/priam-cp-embouazzar/"',
  CONTEXT_ROOT_PRIAM_CMS : '"http://localhost:8080/priam-cms-embouazzar/"'
})


