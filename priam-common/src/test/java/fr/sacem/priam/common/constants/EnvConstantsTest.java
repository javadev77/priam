package fr.sacem.priam.common.constants;

import fr.sacem.fwk.config.Environment;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by embouazzar on 18/08/2017.
 */
public class EnvConstantsTest {

    @Before
    public  void setup() {
        System.setProperty("SACEM_HOME", "L:/JavaDev/SacemHome");
    }

    @Test
    @Ignore
    public void getConstant (){
        assertEquals("webapp.mode", EnvConstants.WEBAPP_MODE.property());
        assertEquals("dev", Environment.getParameter(EnvConstants.WEBAPP_MODE.property()));

        assertEquals("priam.penef.dir.zip.in", EnvConstants.PENEF_ZIP_IN.property());
        assertEquals("/work/DomainApp/priam/HTDOCS/PENEF/IN", Environment.getParameter(EnvConstants.PENEF_ZIP_IN.property()));

        assertEquals("priam.penef.dir.zip.archives", EnvConstants.PENEF_ZIP_ARCHIVES.property());
        assertEquals("/work/DomainApp/priam/HTDOCS/PENEF/ARCHIVES", Environment.getParameter(EnvConstants.PENEF_ZIP_ARCHIVES.property()));

        
    }
}
