package fr.sacem.priam.model.util;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * Extends default {@link StdDateFormat}
 * by adding <b>yyyy-MM-dd HH:mm:ss</b> pattern for <b>deserialization</b>
 * by adding <b>dd/MM/yyyy</b> pattern for <b>deserialization</b>
 *
 * @author benmerzoukah
 */
public class StdDateFormatExt extends StdDateFormat {
    private static final Logger LOGGER = LoggerFactory.getLogger(StdDateFormatExt.class);

    protected final static String DATE_FORMAT_STR_PRIAM = "yyyy-MM-dd HH:mm:ss";
    protected final static String DATE_FORMAT_STR_PRIAM_SHORT_DATE = "dd/MM/yyyy";

    protected final static Locale DEFAULT_LOCALE = Locale.FRANCE;

    protected final static DateFormat DATE_FORMAT_PRIAM;
    protected final static DateFormat DATE_FORMAT_PRIAM_SHORT_DATE;
    static {
        DATE_FORMAT_PRIAM = new SimpleDateFormat(DATE_FORMAT_STR_PRIAM, DEFAULT_LOCALE);
        DATE_FORMAT_PRIAM.setTimeZone(StdDateFormat.getDefaultTimeZone());

        DATE_FORMAT_PRIAM_SHORT_DATE = new SimpleDateFormat(DATE_FORMAT_STR_PRIAM_SHORT_DATE, DEFAULT_LOCALE);
        DATE_FORMAT_PRIAM_SHORT_DATE.setTimeZone(StdDateFormat.getDefaultTimeZone());
    }

    protected transient TimeZone _timezone;
    protected transient DateFormat _formatOscar;
    protected transient DateFormat _formatOscar2;

    protected final Locale _locale;

    public StdDateFormatExt() {
      this(StdDateFormat.getDefaultTimeZone());
    }

    public StdDateFormatExt(TimeZone tz) {
        this(tz, DEFAULT_LOCALE);
    }

    public StdDateFormatExt(TimeZone tz, Locale loc) {
        super(tz, loc);
        _timezone = tz;
        _locale = loc;
    }

    @Override
    public StdDateFormat withTimeZone(TimeZone tz) {
        if (tz == null) {
            tz = StdDateFormat.getDefaultTimeZone();
        }
        if (tz.equals(_timezone)) {
            return this;
        }
        return new StdDateFormatExt(tz, _locale);
    }

    @Override
    public StdDateFormat withLocale(Locale loc) {
        if (loc.equals(_locale)) {
            return this;
        }
        return new StdDateFormatExt(_timezone, loc);
    }

    @Override
    public StdDateFormatExt clone() {
        return new StdDateFormatExt(_timezone, _locale);
    }

    @Override
    public void setTimeZone(TimeZone tz){
        if (!tz.equals(_timezone)) {
            _formatOscar = null;
            _timezone = tz;
        }
        super.setTimeZone(tz);
    }

    @Override
    public Date parse(String dateStr, ParsePosition pos){
        LOGGER.info("dateStr=" + dateStr);
        String trimmed = dateStr != null ? dateStr.trim() : "";
        if(isOscarLongYMDDate(trimmed)){
            if (_formatOscar == null) {
                _formatOscar = getOscarLongYMDFormat(_timezone, _locale);
            }

            Date date = getDate(_formatOscar, pos, trimmed);
            if (date != null) return date;
        }

        if(isOscarShortDate(trimmed)){
            if (_formatOscar2 == null) {
                _formatOscar2 = getOscarShortDateFormat(_timezone, _locale);
            }

            Date date = getDate(_formatOscar2, pos, trimmed);
            if (date != null) return date;
        }

        return super.parse(dateStr, pos);
    }
  
    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
      LOGGER.info("date=" + date);
      return DATE_FORMAT_PRIAM.format(date, toAppendTo, fieldPosition);
    }
  
  private boolean isOscarLongYMDDate(String trimmed) {
        return trimmed.length() == DATE_FORMAT_STR_PRIAM.length() && trimmed.indexOf("-") == 4 && trimmed.lastIndexOf(" ") == 10;
    }

    private static boolean isOscarShortDate(String trimmed) {
        return trimmed.length() == DATE_FORMAT_STR_PRIAM_SHORT_DATE.length() && trimmed.indexOf("/") == 2 && trimmed.lastIndexOf("/") == 5;
    }

    private static DateFormat getOscarLongYMDFormat(TimeZone tz, Locale loc) {
        return _cloneFormat(DATE_FORMAT_PRIAM, DATE_FORMAT_STR_PRIAM, tz, loc);
    }

    private static DateFormat getOscarShortDateFormat(TimeZone tz, Locale loc) {
        return _cloneFormat(DATE_FORMAT_PRIAM_SHORT_DATE, DATE_FORMAT_STR_PRIAM_SHORT_DATE, tz, loc);
    }

    private Date getDate(DateFormat formatter, ParsePosition pos, String trimmed) {
        if(formatter != null){
            try{
                return formatter.parse(trimmed, pos);
            }catch (Exception ex){ /*ignore, let's normal process take in place*/ }
        }
        return null;
    }

    private static DateFormat _cloneFormat(DateFormat df, String format, TimeZone tz, Locale loc){
        if (!loc.equals(DEFAULT_LOCALE)) {
            df = new SimpleDateFormat(format, loc);
            df.setTimeZone((tz == null) ? StdDateFormat.getDefaultTimeZone() : tz);
        } else {
            df = (DateFormat) df.clone();
            if (tz != null) {
                df.setTimeZone(tz);
            }
        }
        return df;
    }
}
