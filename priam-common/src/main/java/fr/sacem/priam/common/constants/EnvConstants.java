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
        
    };
    
    @Override
    public String toString() {
         return getParameter(property());
    }


}
