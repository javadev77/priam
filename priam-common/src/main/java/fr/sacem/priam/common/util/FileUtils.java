package fr.sacem.priam.common.util;

public class FileUtils {
    public static final String PREFIX_PRIV_SON_PH = "FF_PENEF_EXTRANA_EXTCPRIVSONPH";
    public static final String PREFIX_PRIV_SON_RD = "FF_PENEF_EXTRANA_EXTCPRIVSONRD";
    // la creation des zips sous linux ajoute des \ dans les nom fichier, ce hack a pour but contrôler ce probleme
    public static final String PREFIX_PRIV_SON_PH_HACK_LINUX = "\\FF_PENEF_EXTRANA_EXTCPRIVSONPH";
    public static final String PREFIX_PRIV_SON_RD_HACK_LINIX = "\\FF_PENEF_EXTRANA_EXTCPRIVSONRD";
}