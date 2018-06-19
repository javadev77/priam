package fr.sacem.priam.common.constants;


import static fr.sacem.fwk.config.Environment.getParameter;

/**
 * Created by embouazzar on 18/08/2017.
 */
public enum EnvConstants implements ConfigurableProperty {

    WEBAPP_MODE {
        @Override
        public String property() {
            return "webapp.mode";
        }
    },

    PENEF_ZIP_IN {
        @Override
        public String property() {
            return "priam.penef.dir.zip.in";
        }
    },
    PENEF_ZIP_ARCHIVES {
        @Override
        public String property() {
            return "priam.penef.dir.zip.archives";
        }

    },
    
    FELIX_PREPREP_DIR {
        @Override
        public String property() {
            return "priam.felix.preprep.dir";
        }
        
    },
    
    MIPSA_WEB_COMPONENT_HTML_URL {
        @Override
        public String property() {
            return "priam.mipsa.wc.html.url";
        }
        
    },
    
    MIPSA_WEB_COMPONENT_CDEDECL {
        @Override
        public String property() {
            return "priam.mipsa.wc.cdedecl";
        }
        
    },
    
    MIPSA_WEB_COMPONENT_BASEURL {
        @Override
        public String property() {
            return "priam.mipsa.wc.baseurl";
        }
        
    },
    
    MIPSA_WEB_COMPONENT_CDETYPINTERLOC {
        @Override
        public String property() {
            return "priam.mipsa.wc.cdetypinterloc";
        }
        
    },
    
    MIPSA_WEB_COMPONENT_USESSOTOKEN {
        @Override
        public String property() {
            return "priam.mipsa.wc.usessotoken";
        }
        
    },
    BATCH_CONFIG_PROPERTIES {
        @Override
        public String property() {
            return "priam.batch.configuration.properties";}

    },
    FTP_FELIX_HOME_DIR {
        @Override
        public String property() {return "priam.ftp.felix.home.directory";}
        
    },
    FTP_FELIX_SERVER {
        @Override
        public String property() {return "priam.ftp.felix.server.host";}
        
    },
    FTP_FELIX_PORT {
        
        @Override
        public String property() {return "priam.ftp.felix.server.port";}
        
        
        @Override
        public String defaultValue() {
            return "22";
        }
    },
    FTP_FELIX_USERNAME {
        @Override
        public String property() {return "priam.ftp.felix.user.name";}
        
      
    },
    FTP_FELIX_PASSWORD {
        @Override
        public String property() {return "priam.ftp.felix.user.password";}
    },
    
    FELIX_ACQT_INPUT_DIR {
        @Override
        public String property() {
            return "priam.felix.acquittement.in";
        }
    },
    
    FELIX_ACQT_ARCHIVES_DIR {
        @Override
        public String property() {
            return "priam.felix.acquittement.archives";
        }
        
    },

    PRIAM_DB_JNDI {
        @Override
        public String property() {
            return "priam.db.jndi";
        }
    },

    OCTAV_ZIP_IN {
        @Override
        public String property() {
            return "priam.octav.catalog.dir.in";
        }
    },

    OCTAV_ZIP_ARCHIVES {
        @Override
        public String property() {
            return "priam.octav.catalog.dir.archives";
        }
    },

    PRIAM_SERVICE_REST_COMMON_URL {
        @Override
        public String property() {
            return "priam.service.common.url";
        }
    },

    PRIAM_SERVICE_REST_CMS_URL {
        @Override
        public String property() {
            return "priam.service.cms.url";
        }
    },

    PRIAM_SERVICE_REST_CP_URL {
        @Override
        public String property() {
            return "priam.service.cp.url";
        }
    },

    CATCMS_RDO_INPUT {
        @Override
        public String property() {
            return "priam.catcms.rdo.input";
        }
    },

    CATCMS_RDO__ARCHIVES {
        @Override
        public String property() {
            return "priam.catcms.rdo.archives";
        }
    },

    CATCMS_ENRICHISSEMENT_INPUT {
        @Override
        public String property() {
            return "priam.catcms.enrichissement.input";
        }
    },

    CATCMS_ENRICHISSEMENT_ARCHIVES {
        @Override
        public String property() {
            return "priam.catcms.enrichissement.archives";
        }
    },

    PARTICIPANTS_REQ_OCTAV_CSV_DIR {
        @Override
        public String property() {
            return "priam.catcms.participants.req.octav.dir";
        }

    };

    @Override
    public String toString() {
        
        return getParameter(property(), defaultValue());
    }
    
    public String defaultValue(){
        return "";
    }
}
