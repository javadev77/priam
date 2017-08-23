package fr.sacem.priam.common.constants;

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
    PENEL_ZIP_IN {
        @Override
        public String property() {
            return "priam.penef.dir.zip.in";
        }
    },
    PENEL_ZIP_ARCHIVES {
        @Override
        public String property() {
            return "priam.penef.dir.zip.archives";
        }
    }


}
