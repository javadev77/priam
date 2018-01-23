var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  DEBUG_MODE: true,
  CONTEXT_ROOT_PRIAM : '"http://P93141.sacem.fr:8080/priambenmerzoukah/"',
  CONTEXT_ROOT_PRIAM_COMMON : '"http://P93141.sacem.fr:8080/priam-common/"',
  CONTEXT_ROOT_PRIAM_CP : '"http://P93141.sacem.fr:8080/priam-cp/"',
  CONTEXT_ROOT_PRIAM_CMS : '"http://P93141.sacem.fr:8080/priam-cms/"'
})

