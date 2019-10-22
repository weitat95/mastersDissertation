/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import org.joda.time.DateTimeFieldType;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimePrinter;

public class ISODateTimeFormat {
    public static DateTimeFormatter date() {
        return ISODateTimeFormat.yearMonthDay();
    }

    public static DateTimeFormatter dateTime() {
        return Constants.dt;
    }

    public static DateTimeFormatter hour() {
        return Constants.hde;
    }

    public static DateTimeFormatter weekDate() {
        return Constants.wwd;
    }

    public static DateTimeFormatter yearMonthDay() {
        return Constants.ymd;
    }

    static final class Constants {
        private static final DateTimeFormatter bd;
        private static final DateTimeFormatter bdt;
        private static final DateTimeFormatter bdtx;
        private static final DateTimeFormatter bod;
        private static final DateTimeFormatter bodt;
        private static final DateTimeFormatter bodtx;
        private static final DateTimeFormatter bt;
        private static final DateTimeFormatter btt;
        private static final DateTimeFormatter bttx;
        private static final DateTimeFormatter btx;
        private static final DateTimeFormatter bwd;
        private static final DateTimeFormatter bwdt;
        private static final DateTimeFormatter bwdtx;
        private static final DateTimeFormatter dh;
        private static final DateTimeFormatter dhm;
        private static final DateTimeFormatter dhms;
        private static final DateTimeFormatter dhmsf;
        private static final DateTimeFormatter dhmsl;
        private static final DateTimeFormatter dme;
        private static final DateTimeFormatter dotp;
        private static final DateTimeFormatter dp;
        private static final DateTimeFormatter dpe;
        private static final DateTimeFormatter dt;
        private static final DateTimeFormatter dtp;
        private static final DateTimeFormatter dtx;
        private static final DateTimeFormatter dwe;
        private static final DateTimeFormatter dye;
        private static final DateTimeFormatter fse;
        private static final DateTimeFormatter hde;
        private static final DateTimeFormatter hm;
        private static final DateTimeFormatter hms;
        private static final DateTimeFormatter hmsf;
        private static final DateTimeFormatter hmsl;
        private static final DateTimeFormatter ldotp;
        private static final DateTimeFormatter ldp;
        private static final DateTimeFormatter lte;
        private static final DateTimeFormatter ltp;
        private static final DateTimeFormatter mhe;
        private static final DateTimeFormatter mye;
        private static final DateTimeFormatter od;
        private static final DateTimeFormatter odt;
        private static final DateTimeFormatter odtx;
        private static final DateTimeFormatter sme;
        private static final DateTimeFormatter t;
        private static final DateTimeFormatter tp;
        private static final DateTimeFormatter tpe;
        private static final DateTimeFormatter tt;
        private static final DateTimeFormatter ttx;
        private static final DateTimeFormatter tx;
        private static final DateTimeFormatter wdt;
        private static final DateTimeFormatter wdtx;
        private static final DateTimeFormatter we;
        private static final DateTimeFormatter ww;
        private static final DateTimeFormatter wwd;
        private static final DateTimeFormatter wwe;
        private static final DateTimeFormatter ye;
        private static final DateTimeFormatter ym;
        private static final DateTimeFormatter ymd;
        private static final DateTimeFormatter ze;

        static {
            ye = Constants.yearElement();
            mye = Constants.monthElement();
            dme = Constants.dayOfMonthElement();
            we = Constants.weekyearElement();
            wwe = Constants.weekElement();
            dwe = Constants.dayOfWeekElement();
            dye = Constants.dayOfYearElement();
            hde = Constants.hourElement();
            mhe = Constants.minuteElement();
            sme = Constants.secondElement();
            fse = Constants.fractionElement();
            ze = Constants.offsetElement();
            lte = Constants.literalTElement();
            ym = Constants.yearMonth();
            ymd = Constants.yearMonthDay();
            ww = Constants.weekyearWeek();
            wwd = Constants.weekyearWeekDay();
            hm = Constants.hourMinute();
            hms = Constants.hourMinuteSecond();
            hmsl = Constants.hourMinuteSecondMillis();
            hmsf = Constants.hourMinuteSecondFraction();
            dh = Constants.dateHour();
            dhm = Constants.dateHourMinute();
            dhms = Constants.dateHourMinuteSecond();
            dhmsl = Constants.dateHourMinuteSecondMillis();
            dhmsf = Constants.dateHourMinuteSecondFraction();
            t = Constants.time();
            tx = Constants.timeNoMillis();
            tt = Constants.tTime();
            ttx = Constants.tTimeNoMillis();
            dt = Constants.dateTime();
            dtx = Constants.dateTimeNoMillis();
            wdt = Constants.weekDateTime();
            wdtx = Constants.weekDateTimeNoMillis();
            od = Constants.ordinalDate();
            odt = Constants.ordinalDateTime();
            odtx = Constants.ordinalDateTimeNoMillis();
            bd = Constants.basicDate();
            bt = Constants.basicTime();
            btx = Constants.basicTimeNoMillis();
            btt = Constants.basicTTime();
            bttx = Constants.basicTTimeNoMillis();
            bdt = Constants.basicDateTime();
            bdtx = Constants.basicDateTimeNoMillis();
            bod = Constants.basicOrdinalDate();
            bodt = Constants.basicOrdinalDateTime();
            bodtx = Constants.basicOrdinalDateTimeNoMillis();
            bwd = Constants.basicWeekDate();
            bwdt = Constants.basicWeekDateTime();
            bwdtx = Constants.basicWeekDateTimeNoMillis();
            dpe = Constants.dateElementParser();
            tpe = Constants.timeElementParser();
            dp = Constants.dateParser();
            ldp = Constants.localDateParser();
            tp = Constants.timeParser();
            ltp = Constants.localTimeParser();
            dtp = Constants.dateTimeParser();
            dotp = Constants.dateOptionalTimeParser();
            ldotp = Constants.localDateOptionalTimeParser();
        }

        private static DateTimeFormatter basicDate() {
            if (bd == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.monthOfYear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfMonth(), 2).toFormatter();
            }
            return bd;
        }

        private static DateTimeFormatter basicDateTime() {
            if (bdt == null) {
                return new DateTimeFormatterBuilder().append(Constants.basicDate()).append(Constants.basicTTime()).toFormatter();
            }
            return bdt;
        }

        private static DateTimeFormatter basicDateTimeNoMillis() {
            if (bdtx == null) {
                return new DateTimeFormatterBuilder().append(Constants.basicDate()).append(Constants.basicTTimeNoMillis()).toFormatter();
            }
            return bdtx;
        }

        private static DateTimeFormatter basicOrdinalDate() {
            if (bod == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 4).appendFixedDecimal(DateTimeFieldType.dayOfYear(), 3).toFormatter();
            }
            return bod;
        }

        private static DateTimeFormatter basicOrdinalDateTime() {
            if (bodt == null) {
                return new DateTimeFormatterBuilder().append(Constants.basicOrdinalDate()).append(Constants.basicTTime()).toFormatter();
            }
            return bodt;
        }

        private static DateTimeFormatter basicOrdinalDateTimeNoMillis() {
            if (bodtx == null) {
                return new DateTimeFormatterBuilder().append(Constants.basicOrdinalDate()).append(Constants.basicTTimeNoMillis()).toFormatter();
            }
            return bodtx;
        }

        private static DateTimeFormatter basicTTime() {
            if (btt == null) {
                return new DateTimeFormatterBuilder().append(Constants.literalTElement()).append(Constants.basicTime()).toFormatter();
            }
            return btt;
        }

        private static DateTimeFormatter basicTTimeNoMillis() {
            if (bttx == null) {
                return new DateTimeFormatterBuilder().append(Constants.literalTElement()).append(Constants.basicTimeNoMillis()).toFormatter();
            }
            return bttx;
        }

        private static DateTimeFormatter basicTime() {
            if (bt == null) {
                return new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendLiteral('.').appendFractionOfSecond(3, 9).appendTimeZoneOffset("Z", false, 2, 2).toFormatter();
            }
            return bt;
        }

        private static DateTimeFormatter basicTimeNoMillis() {
            if (btx == null) {
                return new DateTimeFormatterBuilder().appendFixedDecimal(DateTimeFieldType.hourOfDay(), 2).appendFixedDecimal(DateTimeFieldType.minuteOfHour(), 2).appendFixedDecimal(DateTimeFieldType.secondOfMinute(), 2).appendTimeZoneOffset("Z", false, 2, 2).toFormatter();
            }
            return btx;
        }

        private static DateTimeFormatter basicWeekDate() {
            if (bwd == null) {
                return new DateTimeFormatterBuilder().appendWeekyear(4, 4).appendLiteral('W').appendFixedDecimal(DateTimeFieldType.weekOfWeekyear(), 2).appendFixedDecimal(DateTimeFieldType.dayOfWeek(), 1).toFormatter();
            }
            return bwd;
        }

        private static DateTimeFormatter basicWeekDateTime() {
            if (bwdt == null) {
                return new DateTimeFormatterBuilder().append(Constants.basicWeekDate()).append(Constants.basicTTime()).toFormatter();
            }
            return bwdt;
        }

        private static DateTimeFormatter basicWeekDateTimeNoMillis() {
            if (bwdtx == null) {
                return new DateTimeFormatterBuilder().append(Constants.basicWeekDate()).append(Constants.basicTTimeNoMillis()).toFormatter();
            }
            return bwdtx;
        }

        private static DateTimeFormatter dateElementParser() {
            if (dpe == null) {
                return new DateTimeFormatterBuilder().append(null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(Constants.yearElement()).appendOptional(new DateTimeFormatterBuilder().append(Constants.monthElement()).appendOptional(Constants.dayOfMonthElement().getParser()).toParser()).toParser(), new DateTimeFormatterBuilder().append(Constants.weekyearElement()).append(Constants.weekElement()).appendOptional(Constants.dayOfWeekElement().getParser()).toParser(), new DateTimeFormatterBuilder().append(Constants.yearElement()).append(Constants.dayOfYearElement()).toParser()}).toFormatter();
            }
            return dpe;
        }

        private static DateTimeFormatter dateHour() {
            if (dh == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.literalTElement()).append(ISODateTimeFormat.hour()).toFormatter();
            }
            return dh;
        }

        private static DateTimeFormatter dateHourMinute() {
            if (dhm == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.literalTElement()).append(Constants.hourMinute()).toFormatter();
            }
            return dhm;
        }

        private static DateTimeFormatter dateHourMinuteSecond() {
            if (dhms == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.literalTElement()).append(Constants.hourMinuteSecond()).toFormatter();
            }
            return dhms;
        }

        private static DateTimeFormatter dateHourMinuteSecondFraction() {
            if (dhmsf == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.literalTElement()).append(Constants.hourMinuteSecondFraction()).toFormatter();
            }
            return dhmsf;
        }

        private static DateTimeFormatter dateHourMinuteSecondMillis() {
            if (dhmsl == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.literalTElement()).append(Constants.hourMinuteSecondMillis()).toFormatter();
            }
            return dhmsl;
        }

        private static DateTimeFormatter dateOptionalTimeParser() {
            if (dotp == null) {
                DateTimeParser dateTimeParser = new DateTimeFormatterBuilder().appendLiteral('T').appendOptional(Constants.timeElementParser().getParser()).appendOptional(Constants.offsetElement().getParser()).toParser();
                return new DateTimeFormatterBuilder().append(Constants.dateElementParser()).appendOptional(dateTimeParser).toFormatter();
            }
            return dotp;
        }

        private static DateTimeFormatter dateParser() {
            if (dp == null) {
                DateTimeParser dateTimeParser = new DateTimeFormatterBuilder().appendLiteral('T').append(Constants.offsetElement()).toParser();
                return new DateTimeFormatterBuilder().append(Constants.dateElementParser()).appendOptional(dateTimeParser).toFormatter();
            }
            return dp;
        }

        private static DateTimeFormatter dateTime() {
            if (dt == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.tTime()).toFormatter();
            }
            return dt;
        }

        private static DateTimeFormatter dateTimeNoMillis() {
            if (dtx == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.date()).append(Constants.tTimeNoMillis()).toFormatter();
            }
            return dtx;
        }

        private static DateTimeFormatter dateTimeParser() {
            if (dtp == null) {
                DateTimeParser dateTimeParser = new DateTimeFormatterBuilder().appendLiteral('T').append(Constants.timeElementParser()).appendOptional(Constants.offsetElement().getParser()).toParser();
                return new DateTimeFormatterBuilder().append(null, new DateTimeParser[]{dateTimeParser, Constants.dateOptionalTimeParser().getParser()}).toFormatter();
            }
            return dtp;
        }

        private static DateTimeFormatter dayOfMonthElement() {
            if (dme == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfMonth(2).toFormatter();
            }
            return dme;
        }

        private static DateTimeFormatter dayOfWeekElement() {
            if (dwe == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfWeek(1).toFormatter();
            }
            return dwe;
        }

        private static DateTimeFormatter dayOfYearElement() {
            if (dye == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendDayOfYear(3).toFormatter();
            }
            return dye;
        }

        private static DateTimeFormatter fractionElement() {
            if (fse == null) {
                return new DateTimeFormatterBuilder().appendLiteral('.').appendFractionOfSecond(3, 9).toFormatter();
            }
            return fse;
        }

        private static DateTimeFormatter hourElement() {
            if (hde == null) {
                return new DateTimeFormatterBuilder().appendHourOfDay(2).toFormatter();
            }
            return hde;
        }

        private static DateTimeFormatter hourMinute() {
            if (hm == null) {
                return new DateTimeFormatterBuilder().append(Constants.hourElement()).append(Constants.minuteElement()).toFormatter();
            }
            return hm;
        }

        private static DateTimeFormatter hourMinuteSecond() {
            if (hms == null) {
                return new DateTimeFormatterBuilder().append(Constants.hourElement()).append(Constants.minuteElement()).append(Constants.secondElement()).toFormatter();
            }
            return hms;
        }

        private static DateTimeFormatter hourMinuteSecondFraction() {
            if (hmsf == null) {
                return new DateTimeFormatterBuilder().append(Constants.hourElement()).append(Constants.minuteElement()).append(Constants.secondElement()).append(Constants.fractionElement()).toFormatter();
            }
            return hmsf;
        }

        private static DateTimeFormatter hourMinuteSecondMillis() {
            if (hmsl == null) {
                return new DateTimeFormatterBuilder().append(Constants.hourElement()).append(Constants.minuteElement()).append(Constants.secondElement()).appendLiteral('.').appendFractionOfSecond(3, 3).toFormatter();
            }
            return hmsl;
        }

        private static DateTimeFormatter literalTElement() {
            if (lte == null) {
                return new DateTimeFormatterBuilder().appendLiteral('T').toFormatter();
            }
            return lte;
        }

        private static DateTimeFormatter localDateOptionalTimeParser() {
            if (ldotp == null) {
                DateTimeParser dateTimeParser = new DateTimeFormatterBuilder().appendLiteral('T').append(Constants.timeElementParser()).toParser();
                return new DateTimeFormatterBuilder().append(Constants.dateElementParser()).appendOptional(dateTimeParser).toFormatter().withZoneUTC();
            }
            return ldotp;
        }

        private static DateTimeFormatter localDateParser() {
            if (ldp == null) {
                return Constants.dateElementParser().withZoneUTC();
            }
            return ldp;
        }

        private static DateTimeFormatter localTimeParser() {
            if (ltp == null) {
                return new DateTimeFormatterBuilder().appendOptional(Constants.literalTElement().getParser()).append(Constants.timeElementParser()).toFormatter().withZoneUTC();
            }
            return ltp;
        }

        private static DateTimeFormatter minuteElement() {
            if (mhe == null) {
                return new DateTimeFormatterBuilder().appendLiteral(':').appendMinuteOfHour(2).toFormatter();
            }
            return mhe;
        }

        private static DateTimeFormatter monthElement() {
            if (mye == null) {
                return new DateTimeFormatterBuilder().appendLiteral('-').appendMonthOfYear(2).toFormatter();
            }
            return mye;
        }

        private static DateTimeFormatter offsetElement() {
            if (ze == null) {
                return new DateTimeFormatterBuilder().appendTimeZoneOffset("Z", true, 2, 4).toFormatter();
            }
            return ze;
        }

        private static DateTimeFormatter ordinalDate() {
            if (od == null) {
                return new DateTimeFormatterBuilder().append(Constants.yearElement()).append(Constants.dayOfYearElement()).toFormatter();
            }
            return od;
        }

        private static DateTimeFormatter ordinalDateTime() {
            if (odt == null) {
                return new DateTimeFormatterBuilder().append(Constants.ordinalDate()).append(Constants.tTime()).toFormatter();
            }
            return odt;
        }

        private static DateTimeFormatter ordinalDateTimeNoMillis() {
            if (odtx == null) {
                return new DateTimeFormatterBuilder().append(Constants.ordinalDate()).append(Constants.tTimeNoMillis()).toFormatter();
            }
            return odtx;
        }

        private static DateTimeFormatter secondElement() {
            if (sme == null) {
                return new DateTimeFormatterBuilder().appendLiteral(':').appendSecondOfMinute(2).toFormatter();
            }
            return sme;
        }

        private static DateTimeFormatter tTime() {
            if (tt == null) {
                return new DateTimeFormatterBuilder().append(Constants.literalTElement()).append(Constants.time()).toFormatter();
            }
            return tt;
        }

        private static DateTimeFormatter tTimeNoMillis() {
            if (ttx == null) {
                return new DateTimeFormatterBuilder().append(Constants.literalTElement()).append(Constants.timeNoMillis()).toFormatter();
            }
            return ttx;
        }

        private static DateTimeFormatter time() {
            if (t == null) {
                return new DateTimeFormatterBuilder().append(Constants.hourMinuteSecondFraction()).append(Constants.offsetElement()).toFormatter();
            }
            return t;
        }

        private static DateTimeFormatter timeElementParser() {
            if (tpe == null) {
                DateTimeParser dateTimeParser = new DateTimeFormatterBuilder().append(null, new DateTimeParser[]{new DateTimeFormatterBuilder().appendLiteral('.').toParser(), new DateTimeFormatterBuilder().appendLiteral(',').toParser()}).toParser();
                return new DateTimeFormatterBuilder().append(Constants.hourElement()).append(null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(Constants.minuteElement()).append(null, new DateTimeParser[]{new DateTimeFormatterBuilder().append(Constants.secondElement()).appendOptional(new DateTimeFormatterBuilder().append(dateTimeParser).appendFractionOfSecond(1, 9).toParser()).toParser(), new DateTimeFormatterBuilder().append(dateTimeParser).appendFractionOfMinute(1, 9).toParser(), null}).toParser(), new DateTimeFormatterBuilder().append(dateTimeParser).appendFractionOfHour(1, 9).toParser(), null}).toFormatter();
            }
            return tpe;
        }

        private static DateTimeFormatter timeNoMillis() {
            if (tx == null) {
                return new DateTimeFormatterBuilder().append(Constants.hourMinuteSecond()).append(Constants.offsetElement()).toFormatter();
            }
            return tx;
        }

        private static DateTimeFormatter timeParser() {
            if (tp == null) {
                return new DateTimeFormatterBuilder().appendOptional(Constants.literalTElement().getParser()).append(Constants.timeElementParser()).appendOptional(Constants.offsetElement().getParser()).toFormatter();
            }
            return tp;
        }

        private static DateTimeFormatter weekDateTime() {
            if (wdt == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(Constants.tTime()).toFormatter();
            }
            return wdt;
        }

        private static DateTimeFormatter weekDateTimeNoMillis() {
            if (wdtx == null) {
                return new DateTimeFormatterBuilder().append(ISODateTimeFormat.weekDate()).append(Constants.tTimeNoMillis()).toFormatter();
            }
            return wdtx;
        }

        private static DateTimeFormatter weekElement() {
            if (wwe == null) {
                return new DateTimeFormatterBuilder().appendLiteral("-W").appendWeekOfWeekyear(2).toFormatter();
            }
            return wwe;
        }

        private static DateTimeFormatter weekyearElement() {
            if (we == null) {
                return new DateTimeFormatterBuilder().appendWeekyear(4, 9).toFormatter();
            }
            return we;
        }

        private static DateTimeFormatter weekyearWeek() {
            if (ww == null) {
                return new DateTimeFormatterBuilder().append(Constants.weekyearElement()).append(Constants.weekElement()).toFormatter();
            }
            return ww;
        }

        private static DateTimeFormatter weekyearWeekDay() {
            if (wwd == null) {
                return new DateTimeFormatterBuilder().append(Constants.weekyearElement()).append(Constants.weekElement()).append(Constants.dayOfWeekElement()).toFormatter();
            }
            return wwd;
        }

        private static DateTimeFormatter yearElement() {
            if (ye == null) {
                return new DateTimeFormatterBuilder().appendYear(4, 9).toFormatter();
            }
            return ye;
        }

        private static DateTimeFormatter yearMonth() {
            if (ym == null) {
                return new DateTimeFormatterBuilder().append(Constants.yearElement()).append(Constants.monthElement()).toFormatter();
            }
            return ym;
        }

        private static DateTimeFormatter yearMonthDay() {
            if (ymd == null) {
                return new DateTimeFormatterBuilder().append(Constants.yearElement()).append(Constants.monthElement()).append(Constants.dayOfMonthElement()).toFormatter();
            }
            return ymd;
        }
    }

}

