package com.arm.encry.base;

import java.math.BigDecimal;
import java.net.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaolangjing
 * @since 2021-3-24 10:46
 */
public class StrUtil {


    public static boolean isNull(Object obj) {
        return obj == null || "".equals( obj ) || "null".equalsIgnoreCase( (String) obj );
    }

    public static boolean isNotNull(Object obj) {
        return obj != null && !"".equals( obj ) && !"null".equalsIgnoreCase( String.valueOf( obj ) );
    }

    public static void pushStr(String[] str) {
        if (str.length > 0) {
            for (int i = 0; i < str.length; ++i) {
                str[i] = "";
            }
        }

    }

    public static String trim(String s) {
        String result = "";
        if (null != s && !"".equals( s )) {
            result = s.replaceAll( "^[　*| *| *|//s*]*", "" ).replaceAll( "[　*| *| *|//s*]*$", "" );
        }

        return result;
    }

    public static String getNotNullStrValue(Object obj) {
        if (obj == null) {
            return "";
        } else {
            String value = "";
            if (obj instanceof Timestamp) {
                SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                value = formatter.format( new Date( ((Timestamp) obj).getTime() ) );
            } else if (isNotNull( obj )) {
                value = obj.toString();
            }

            return value;
        }
    }

    public static String getNotNullStrValue(Object obj, String defV) {
        return obj == null ? defV : getNotNullStrValue( (Object) obj.toString(), defV );
    }

    public static String getNotNullStrValue(String str) {
        return str != null && !str.equals( "null" ) ? getNotNullStrValue( (Object) str, "" ) : "";
    }

    public static String getNotNullStrValueUTF8(String str) {
        String sReturn = "";

        try {
            if (str != null && !str.equals( "null" )) {
                sReturn = URLDecoder.decode( str.trim(), "UTF-8" );
            } else {
                sReturn = "";
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return sReturn;
    }

    public static String getNotNullStrValue(HashMap<?, ?> map, String key) {
        String sRe = "";
        if (map != null) {
            sRe = getNotNullStrValue( map.get( key ) );
        }

        return sRe;
    }

    public String replaceIgnoreCase(String source, String oldStr, String newStr) {
        if (source != null) {
            source = source.replaceAll( "(?i)" + oldStr, newStr );
        }

        return source;
    }

    public static String peculiarCode(String code) {
        if (code != null) {
            code = code.replaceAll( "'", "''" );
        }

        return code;
    }

    public static String generateRecId(String prefix) {
        return prefix != null ? prefix + (new Date()).getTime() : "" + (new Date()).getTime();
    }

    public static int getNotNullIntValue(String str) {
        boolean var1 = false;

        int intNum;
        try {
            intNum = Integer.parseInt( str );
        } catch (Exception var5) {
            try {
                intNum = (int) Double.parseDouble( str );
            } catch (Exception var4) {
                intNum = 0;
            }
        }

        return intNum;
    }

    public static int getNotNullIntValue(Object str) {
        return getNotNullIntValue( getNotNullStrValue( str ) );
    }

    public static int getNotNullIntValue(String str, int defValue) {
        int intNum = getNotNullIntValue( str );
        if (intNum == 0 && !"0".equals( str )) {
            intNum = defValue;
        }

        return intNum;
    }

    public static float getNotNullFloatValue(String str) {
        float intNum = 0.0F;

        try {
            intNum = Float.parseFloat( str );
        } catch (NumberFormatException var3) {
            intNum = 0.0F;
        }

        return intNum;
    }

    public static float getNotNullFloatValue(Object str) {
        return getNotNullFloatValue( getNotNullStrValue( str ) );
    }

    public static double getNotNullDoubleValue(String str) {
        double intNum = 0.0D;

        try {
            intNum = Double.parseDouble( str );
        } catch (NumberFormatException var4) {
            intNum = 0.0D;
        }

        return intNum;
    }

    public static double getNotNullDoubleValue(Object str) {
        return (double) getNotNullFloatValue( getNotNullStrValue( str ) );
    }

    public static String getNullStrToZero(String str) {
        return getNotNullStrValue( (Object) str, "0" );
    }

    public static String filterXml(String value) {
        if (value == null) {
            return null;
        } else {
            char[] content = new char[value.length()];
            value.getChars( 0, value.length(), content, 0 );
            StringBuffer result = new StringBuffer( content.length + 50 );

            for (int i = 0; i < content.length; ++i) {
                switch (content[i]) {
                    case '"':
                        result.append( "&quot;" );
                        break;
                    case '&':
                        result.append( "&amp;" );
                        break;
                    case '\'':
                        result.append( "&#39;" );
                        break;
                    case '<':
                        result.append( "&lt;" );
                        break;
                    case '>':
                        result.append( "&gt;" );
                        break;
                    default:
                        result.append( content[i] );
                }
            }

            return result.toString();
        }
    }

    public static String getNotLineStr(String str) {
        String result = "";
        char[] s = str.toCharArray();
        if (s.length > 0) {
            for (int i = 0; i < s.length; ++i) {
                if ('_' != s[i]) {
                    result = result + s[i];
                }
            }
        }

        return result;
    }

    public static String filtrateStringToHtml(String str) {
        try {
            if (str != null && !str.trim().equals( "" )) {
                str = str.replaceAll( "&", "&amp;" );
                str = str.replaceAll( "\"", "&quot;" );
                str = str.replaceAll( "<", "&lt;" );
                str = str.replaceAll( ">", "&gt;" );
                str = str.replaceAll( "\n", "<br>" );
                str = str.replaceAll( " ", "&nbsp;" );
                str = str.replaceAll( "\t", "&nbsp;&nbsp;&nbsp;&nbsp;" );
            }

            return str;
        } catch (Exception var2) {
            return "";
        }
    }

    public static String filtrateHtmlToString(String str) {
        try {
            if (str != null && !str.trim().equals( "" )) {
                str = str.replaceAll( "&amp;", "&" );
                str = str.replaceAll( "&quot;", "\"" );
                str = str.replaceAll( "&lt;", "<" );
                str = str.replaceAll( "&gt;", ">" );
                str = str.replaceAll( "<br>", "\n" );
                str = str.replaceAll( "&nbsp;", " " );
                str = str.replaceAll( "&nbsp;&nbsp;&nbsp;&nbsp;", "\t" );
            }

            return str;
        } catch (Exception var2) {
            return "";
        }
    }

    public static String substring(String str, int toCount, String more) {
        int reInt = 0;
        String reStr = "";
        if (str == null) {
            return "";
        } else {
            char[] tempChar = str.toCharArray();
            if (more != null) {
                toCount -= more.length();
            }

            for (int kk = 0; kk < tempChar.length && toCount > reInt; ++kk) {
                String s1 = String.valueOf( tempChar[kk] );
                byte[] b = s1.getBytes();
                reInt += b.length;
                reStr = reStr + tempChar[kk];
            }

            if (toCount == reInt || toCount == reInt - 1) {
                reStr = reStr + more;
            }

            return reStr;
        }
    }

    public static String encoderStr(String str, String type) {
        String resultStr = "";

        try {
            resultStr = URLEncoder.encode( str, type );
        } catch (Exception var4) {
            System.out.println( "字符串加密失败!!" );
        }

        return resultStr;
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt( i );
            if (c >= 0 && c <= 255) {
                sb.append( c );
            } else {
                byte[] b;
                try {
                    b = Character.toString( c ).getBytes( "UTF-8" );
                } catch (Exception var7) {
                    b = new byte[0];
                }

                for (int j = 0; j < b.length; ++j) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }

                    sb.append( "%" + Integer.toHexString( k ).toUpperCase() );
                }
            }
        }

        return sb.toString();
    }

    public static boolean checkStr(String regex, String str) {
        return Pattern.matches( regex, str );
    }

    public static String getStrToUpperCase(String str) {
        return str == null ? "" : getNotNullStrValue( (Object) str, "" ).toUpperCase();
    }

    public static String getStrWithQuot(String value, String sp, String regex) {
        String[] arrValue = value.split( regex );
        String strResult = "";
        String[] var5 = arrValue;
        int var6 = arrValue.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String str = var5[var7];
            strResult = strResult + regex + sp + str + sp;
        }

        strResult = strResult.substring( 1 );
        return strResult;
    }

    public static boolean isImage(String name) {
        boolean result = false;
        String[] type = new String[]{".JPG", ".GIF", ".BMP", ".PNG"};

        for (int i = 0; i < type.length; ++i) {
            if (name.toUpperCase().indexOf( type[i] ) != -1) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static String getStrMove(String fileId, String[] delId) {
        if (fileId != null && !fileId.equals( "" )) {
            if (delId != null && delId.length != 0) {
                String[] strArr = fileId.split( "," );
                String s = "";

                for (int i = 0; i < strArr.length; ++i) {
                    for (int j = 0; j < delId.length; ++j) {
                        if (!strArr[i].equals( delId[j] )) {
                            s = s + strArr[i] + ",";
                        }
                    }
                }

                if (isNotNull( s )) {
                    s = s.substring( 0, s.length() - 1 );
                }

                return s;
            } else {
                return fileId;
            }
        } else {
            return "";
        }
    }

    public static String filtrateStringToHtmlBYLOG(String str) {
        try {
            if (str != null && !str.trim().equals( "" )) {
                str = str.replaceAll( "\n", "<br>" );
                str = str.replaceAll( "\t", "    " );
            }

            return str;
        } catch (Exception var2) {
            return "";
        }
    }

    public static String getStrByList(List<?> list) {
        String str = "";

        for (int i = 0; i < list.size(); ++i) {
            str = str + String.valueOf( list.get( i ) );
            if (i != list.size()) {
                str = str + "\n";
            }
        }

        return str;
    }

    public static String formatDouble(String str) {
        return (new DecimalFormat( "0.00" )).format( Double.parseDouble( str ) );
    }

    public static String formatDouble(String str, String pattern) {
        return (new DecimalFormat( pattern )).format( Double.parseDouble( str ) );
    }

    public static String batchNumber() {
        return formatDateByStr( "yyyyMMddHHmmssSSS" ) + getStringRandom( 3 );
    }

    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        for (int i = 0; i < length; ++i) {
            String charOrNum = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase( charOrNum )) {
                int temp = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                val = val + (char) (random.nextInt( 26 ) + temp);
            } else if ("num".equalsIgnoreCase( charOrNum )) {
                val = val + String.valueOf( random.nextInt( 10 ) );
            }
        }

        return val.toUpperCase();
    }

    public static String getRandomNo(String prefix, int bit) {
        return getRandomNo( prefix, getRandomOf999(), bit );
    }

    public static String getRandomNo(String prefix, String seq, int bit) {
        String res = "";
        Date date = new Date();
        prefix = prefix == null ? "" : prefix;
        String temp = prefix + String.valueOf( date.getTime() );
        int rl = bit - (seq + temp).length();
        String rs = "";
        if (rl > 0) {
            rs = getStringRandom( rl );
        }

        res = temp + rs + seq;
        if (bit < res.length()) {
            int tempLength = res.length() - bit;
            res = res.substring( tempLength, res.length() );
        }

        return res;
    }

    public static String getRandomNo(int bit) {
        return getRandomNo( (String) null, bit );
    }

    private static String getRandomOf999() {
        Random r = new Random( System.currentTimeMillis() );
        int radom = 0;

        try {
            radom = r.nextInt( 999 );
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return radom + "";
    }

    public static String formatDateByStr(String formatStr) {
        String res = "";
        DateFormat format = new SimpleDateFormat( formatStr );
        res = format.format( new Date() );
        return res;
    }

    public static String formatDateStrbByDate(Date date, String formatStr) {
        String res = "";
        DateFormat format = new SimpleDateFormat( formatStr );
        res = format.format( date );
        return res;
    }

    public static Date getDateByStr(String formatStr, String dateStr) {
        Date res = null;
        formatStr = formatStr != null && !formatStr.equals( "" ) ? formatStr : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat( formatStr );

        try {
            Date date = format.parse( dateStr );
            res = date;
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return res;
    }

    public static Date addDateByInfo(Date date, int dateFlag, int count) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime( date );
        int f = 2;
        if (dateFlag == 1) {
            f = 1;
        } else if (dateFlag == 2) {
            f = 2;
        } else if (dateFlag == 3) {
            f = 6;
        }

        rightNow.add( f, count );
        return rightNow.getTime();
    }

    public static Date getDateByStr(String dateStr) {
        return getDateByStr( (String) null, dateStr );
    }

    public static String dateAfterOrBefore(Date date, int ymwd, int count) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime( date );
        gc.add( ymwd, count );
        gc.set( gc.get( 1 ), gc.get( 2 ), gc.get( 5 ) );
        SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd" );
        return sf.format( gc.getTime() );
    }

    public static int getLastDay(Date date) {
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.set( date.getYear(), date.getMonth(), day );
        int last = cal.getActualMaximum( 5 );
        return last;
    }

    public static String getDayStartByMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        String dStr = format.format( date );
        return dStr.substring( 0, 8 ) + "01";
    }

    public static String getDayEndByMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        String dStr = format.format( date );
        String maxDay = getLastDay( date ) + "";
        return dStr.substring( 0, 8 ) + maxDay;
    }

    public static long getDaySub(Date beginDateStr, Date endDateStr) {
        return (beginDateStr.getTime() - endDateStr.getTime()) / 86400000L;
    }

    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0L;
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );

        try {
            Date beginDate = format.parse( beginDateStr );
            Date endDate = format.parse( endDateStr );
            day = (endDate.getTime() - beginDate.getTime()) / 86400000L;
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        return day;
    }

    public static long getTimeSub(String formatStr, String beginDateStr, String endDateStr) {
        long time = 0L;
        SimpleDateFormat format = new SimpleDateFormat( formatStr );

        try {
            Date beginDate = format.parse( beginDateStr );
            Date endDate = format.parse( endDateStr );
            time = endDate.getTime() - beginDate.getTime();
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        return time;
    }

    public static String arithmetic(String front, String sign, String behind) {
        String res = "";
        BigDecimal m = new BigDecimal( front );
        BigDecimal s = new BigDecimal( behind );
        if (sign.equals( "+" )) {
            res = m.add( s ).toString();
        } else if (sign.equals( "-" )) {
            res = m.subtract( s ).toString();
        } else if (sign.equals( "*" )) {
            res = m.multiply( s ).toString();
        } else if (sign.equals( "/" )) {
            res = m.divide( s ).toString();
        }

        return res;
    }

    public static double arithmetic(double front, String sign, double behind) {
        String res = "";
        BigDecimal m = new BigDecimal( front );
        BigDecimal s = new BigDecimal( behind );
        if (sign.equals( "+" )) {
            res = m.add( s ).toString();
        } else if (sign.equals( "-" )) {
            res = m.subtract( s ).toString();
        } else if (sign.equals( "*" )) {
            res = m.multiply( s ).toString();
        } else if (sign.equals( "/" )) {
            res = m.divide( s ).toString();
        }

        return Double.valueOf( res );
    }

    public static String numberSensitive(String number, int front, int end) {
        String res = "";
        if (isNotNull( number )) {
            char[] ns = number.toCharArray();
            if (ns.length > front + end) {
                for (int i = 0; i < ns.length; ++i) {
                    if (i >= front && i < ns.length - end) {
                        ns[i] = '*';
                    }
                }

                res = new String( ns );
            }
        }

        return res;
    }

    public static long getTimeMillis(String time) {
        DateFormat dateFormat = new SimpleDateFormat( "yy-MM-dd HH:mm:ss" );
        SimpleDateFormat dayFormat = new SimpleDateFormat( "yy-MM-dd" );

        try {
            Date currentDate = dateFormat.parse( dayFormat.format( new Date() ) + " " + time );
            return currentDate.getTime();
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    public static String getNameByFilePath(String filePath) {
        Matcher m = Pattern.compile( "([^<>/\\\\|:\"\"\\*\\?]+)\\.\\w+$+" ).matcher( filePath );
        String fileName = null;
        if (m.find()) {
            fileName = m.group( 1 );
        }

        return fileName;
    }

    public static String getLocalMacAddress() {
        InetAddress ia = null;
        StringBuffer sb = null;

        try {
            ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress( ia ).getHardwareAddress();
            sb = new StringBuffer( "" );

            for (int i = 0; i < mac.length; ++i) {
                if (i != 0) {
                    sb.append( "-" );
                }

                int temp = mac[i] & 255;
                String str = Integer.toHexString( temp );
                if (str.length() == 1) {
                    sb.append( "0" + str );
                } else {
                    sb.append( str );
                }
            }
        } catch (SocketException | UnknownHostException var6) {
            var6.printStackTrace();
        }

        return sb.toString();
    }

    public static List<String> getAllMacAddress() {
        ArrayList res = new ArrayList();

        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();

            while (true) {
                StringBuffer stringBuffer;
                byte[] bytes;
                do {
                    NetworkInterface networkInterface;
                    do {
                        if (!enumeration.hasMoreElements()) {
                            return res;
                        }

                        stringBuffer = new StringBuffer();
                        networkInterface = (NetworkInterface) enumeration.nextElement();
                    } while (networkInterface == null);

                    bytes = networkInterface.getHardwareAddress();
                } while (bytes == null);

                for (int i = 0; i < bytes.length; ++i) {
                    if (i != 0) {
                        stringBuffer.append( "-" );
                    }

                    int tmp = bytes[i] & 255;
                    String str = Integer.toHexString( tmp );
                    if (str.length() == 1) {
                        stringBuffer.append( "0" + str );
                    } else {
                        stringBuffer.append( str );
                    }
                }

                res.add( stringBuffer.toString().toUpperCase() );
            }
        } catch (Exception var8) {
            var8.printStackTrace();
            return res;
        }
    }

    public static int getStrLength(String value) {
        String chinese = "[Α-￥]";
        int valueLength = 0;

        for (int j2 = 0; j2 < value.length(); ++j2) {
            String temp = value.substring( j2, j2 + 1 );
            if (temp.matches( chinese )) {
                valueLength += 2;
            } else {
                ++valueLength;
            }
        }

        return valueLength;
    }

    public static String getDatabaseStrByJavaModelStr(String javaModelStr) {
        String result = "";
        if (javaModelStr != null && !javaModelStr.equals( "" )) {
            for (int i = 0; i < javaModelStr.length(); ++i) {
                char zm = javaModelStr.charAt( i );
                if (Character.isUpperCase( zm )) {
                    result = result + "_" + zm;
                } else if (Character.isLowerCase( zm )) {
                    result = result + Character.toUpperCase( zm );
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println( getAllMacAddress() );
    }
}
