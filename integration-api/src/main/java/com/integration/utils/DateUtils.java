package com.integration.utils;

import com.integration.utils.enums.DateFmtEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author:Starry
 * @Description:
 * @Date:Created in 9:46 2018/4/13 Modified By:
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    private final static String gs = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期转换字符串
     */
    public static String parseDate(Date date, String gs) {
        SimpleDateFormat sdf = new SimpleDateFormat(gs);
        return sdf.format(date);
    }

    /**
     * 日期转换字符串
     */
    public static String parseDate(Date date) {
        return parseDate(date, gs);
    }


    /*
     * public static void main(String[] args){
     *
     * String difference = getDifference("2019-01-01 12:00:00",
     * "2019-01-05 13:00:00");  }
     */
    public static String getDifference(String startStr, String endStr) {
        if (StringUtils.isNotEmpty(startStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(gs);
            Date start = null;
            try {
                start = sdf.parse(startStr);
                Date end = null;
                if (StringUtils.isEmpty(endStr)) {
                    end = new Date();
                } else {
                    end = sdf.parse(endStr);
                }
                return getDistanceTime(start.getTime(), end.getTime());
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }
        return null;
    }

    public static String getDuration(Date start, Date end) {
        return getDistanceTime(start.getTime(), end.getTime());
    }

    public static String getDuration(Date start) {
        return getDuration(start, new Date());
    }

    public static String getDuration(String start) {
        if (StringUtils.isNotEmpty(start)) {
            return getDuration(getDate(start));
        }
        return null;
    }

    public static String getDistanceTime(String diff) {
        if (StringUtils.isNoneEmpty(diff)) {
            return getDistanceTime(Long.parseLong(diff));
        }
        return null;
    }

    public static String getDistanceTime(long diff) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0)
            return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
        if (hour != 0)
            return hour + "小时" + min + "分钟" + sec + "秒";
        if (min != 0)
            return min + "分钟" + sec + "秒";
        if (sec != 0)
            return sec + "秒";
        return "0秒";
    }

    /*
     * 计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟 根据差值返回多长之间前或多长时间后
     */
    public static String getDistanceTime(long time1, long time2) {
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        return getDistanceTime(diff);
    }

    public static Date getDate(String source, String gs) {
        SimpleDateFormat sdf = new SimpleDateFormat(gs);
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
        }
        return null;
    }

    public static Date getDate(String source) {
        return getDate(source, gs);
    }

    /**
     * 字符串转为日期
     */
    public static Date StringToDate(String time, String gs) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(gs);
        Date d = null;
        try {
            d = sDateFormat.parse(time);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return d;

    }

    public static String getSpecifiedDayBefore(String specifiedDay) {

        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        c.setTime(date);
        int day = c.get(Calendar.DATE);

        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        return dayBefore;

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDate() {
        Date date = new Date();
        String d = parseDate(date, gs);
        return d;
    }

    public static Integer getDifference(Date beginTime, Date endTime) {
        return (int) ((endTime.getTime() - beginTime.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获取输入时间与当前时间的差
     *
     * @param time  比较时间
     * @param level 深入层，x年x月x天x时x秒之前，level用来确定精确层数，比如level=2 则x年x月 若年为0 则x月x天
     * @return x年x月x天x时x秒，层数受level约束
     */
    public static String getDateDiffForNow(String time, int level) {
        String fmt = RegexUtil.validTime(time);
        if ("".equals(fmt)) {
            return "0天";
        }

        LocalDateTime before = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(fmt));
        return getDateDiffForNow(before, level);
    }

    /**
     * 获取输入时间与当前时间的差
     *
     * @param time  比较时间
     * @param level 深入层，x年x月x天x时x秒之前，level用来确定精确层数，比如level=2 则x年x月 若年为0 则x月x天
     * @return x年x月x天x时x秒之前，层数受level约束
     */
    public static String getDateDiffForNow(LocalDateTime time, int level) {
        return getDateDiffStr(time, LocalDateTime.now(), level);
    }

    /**
     * 获取输入时间之间时间差
     *
     * @param before 之前时间
     * @param after  之后时间
     * @param level  深入层，x年x月x天x时x秒之前，level用来确定精确层数，比如level=2 则x年x月 若年为0 则x月x天
     * @return x年x月x天x时x秒，层数受level约束
     */
    public static String getDateDiffStr(LocalDateTime before, LocalDateTime after, int level) {
        int currLevel = 0;
        long lastData = 0;
        List<ChronoUnit> chL = Arrays.asList(ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS, ChronoUnit.HOURS,
                ChronoUnit.MINUTES, ChronoUnit.SECONDS);
        // 记录中文的时间描述
        String[] desCn = {"年", "月", "天", "小时", "分", "秒"};
        // 记录相邻的两个时间刻度之间的转换值，为方便计算，第一位为空缺0
        int[] diff = {0, 12, 30, 24, 60, 60};

        StringBuilder timeSb = new StringBuilder();

        for (int i = 0; i < chL.size(); i++) {
            long cal = chL.get(i).between(before, after);
            if (cal != 0) {
                timeSb.append(cal - lastData * diff[i]).append(desCn[i]);
                currLevel++;
                lastData = cal;

                if (currLevel == level) {
                    break;
                }
            }
        }

        return timeSb.toString();
    }

    /**
     * 返回当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowStr() {
        return getNowStr(DateFmtEnum.FMT_SECODE_HYPHEN);
    }

    /**
     * 根据格式化格式返回当前时间
     *
     * @param fmt 格式化
     * @return
     */
    public static String getNowStr(DateFmtEnum fmt) {
        return getNowStr(fmt.getValue());
    }

    /**
     * 根据格式化格式返回当前时间
     *
     * @param fmt 格式化
     * @return
     */
    public static String getNowStr(String fmt) {
        return LocalDateTimeFmt(LocalDateTime.now(), fmt);
    }

    /**
     * 将时间格式化为字符串，默认格式化为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间
     * @return
     */
    public static String LocalDatetimeFmt(LocalDateTime time) {
        return LocalDateTimeFmt(time, DateFmtEnum.FMT_SECODE_HYPHEN);
    }

    /**
     * 将时间格式化为字符串
     *
     * @param time 时间
     * @param fmt  格式化
     * @return
     */
    public static String LocalDateTimeFmt(LocalDateTime time, DateFmtEnum fmt) {
        return LocalDateTimeFmt(time, fmt.getValue());
    }

    /**
     * 将时间格式化为字符串
     *
     * @param time 时间
     * @param fmt  格式化
     * @return
     */
    public static String LocalDateTimeFmt(LocalDateTime time, String fmt) {
        return time.format(DateTimeFormatter.ofPattern(fmt));
    }

    /**
     * 将String格式日期转换为毫秒数
     *
     * @param inVal
     * @return
     */
    public static Long fromDateStringToLong(String inVal, String gs) { // 此方法计算时间毫秒
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
            return date.getTime(); // 返回毫秒数
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null; // 返回毫秒数
    }

    /**
     * 功能描述: 根据日期获取星期几
     *
     * @param: [date]
     * @return: java.lang.String
     */
    public static String dayForWeek(String pTime) {
        int w = 0;
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tmpDate = format.parse(pTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(tmpDate);
            // 指示一个星期中的某天。
            w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekDays[w];
    }
    
	// 考验脑子的时候到了。猜猜这个方法是干啥的
	public List<String> getDateMunute() throws ParseException {
		List<String> retList = new ArrayList<String>();
		String dateTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:");
		StringBuffer sb = new StringBuffer();
		sb.append(sdf.format(new Date()));
		// 获取当前时间 分
		Calendar c = Calendar.getInstance();
		int minute = c.get(Calendar.MINUTE);
		if (minute >= 30) {
			sb.append("30:00");
		} else {
			sb.append("00:00");
		}
		dateTime = sb.toString();
		retList.add(dateTime);
		SimpleDateFormat aa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = aa.format(new Date());
		date = date.substring(0, date.indexOf(" "));
		for (int i = 0; i < 48; i++) {
			String getdaterq = dateTime.substring(0, dateTime.indexOf(" "));
			String gettime = dateTime.substring(dateTime.indexOf(" ") + 1);
			if (getdaterq.equals(date) && !gettime.equalsIgnoreCase("00:30:00")) {
				SimpleDateFormat ssdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar ca = Calendar.getInstance();
				ca.setTime(ssdf.parse(dateTime));
				ca.set(Calendar.MINUTE, ca.get(Calendar.MINUTE) - 30);
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateTime = ssdf.format(ca.getTime());
				retList.add(dateTime);
			} else {
//				//当断不断反受其乱
				break;
			}
		}
		return retList;
	}

	public List<String> getDate(List<String> listTime, int day) throws ParseException {
		List<String> retDate = new ArrayList<String>();
		for (int i = 0; i < listTime.size(); i++) {
			String datetime = listTime.get(i);
			SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = sj.parse(datetime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.add(Calendar.DATE, day);
			retDate.add(sj.format(calendar.getTime()));
		}
		return retDate;
	}

	/**
	 * 获取几天之内的
	 * 
	 * @param listTime
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public List<String> getDateByNum(List<String> listTime, int num) throws ParseException {
		List<String> retDate = new ArrayList<String>();
		List<String> date = new ArrayList<String>();
		date = listTime;
		for (int i = 1; i <= num; i++) {

			for (int j = 0; j < date.size(); j++) {
				String datetime = date.get(j);
				SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = sj.parse(datetime);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d);
				calendar.add(Calendar.DATE, -(i + 1));
				retDate.add(sj.format(calendar.getTime()));
			}
		}
		return retDate;
	}

	/**
	 * 取相同时间的日期
	 * 
	 * @param dateTime
	 * @param todayList
	 * @return
	 */
	public List<List<String>> getTimeSort(List<String> dateTime, List<String> todayList) {
		List<List<String>> retList = new ArrayList<List<String>>();
		for (int i = 0; i < todayList.size(); i++) {
			List<String> dateTimeList = new ArrayList<String>();
			for (int j = 0; j < dateTime.size(); j++) {
				String timeOne = dateTime.get(i).substring(todayList.get(i).indexOf(" ") + 1);
				String timeTwo = dateTime.get(j).substring(dateTime.get(j).indexOf(" ") + 1);
				if (timeOne.equals(timeTwo)) {
					dateTimeList.add(dateTime.get(j));
				}
			}
			retList.add(dateTimeList);
		}
		return retList;
	}

	// 两时间相减除以分钟数，获取有多少时间段
	public List<String> getTimeByMin(String startTime, String endTime, int minute) throws ParseException {
		List<String> retDate = new ArrayList<String>();
		long min = 1000 * 60;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long getmin = (sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime()) / min; // 获取两个时间的分钟数
		int num = (int) (getmin / minute); // 有多少个分钟数
		Calendar cd = Calendar.getInstance();
		Date time = sdf.parse(endTime);
		for (int i = 0; i < num; i++) {
			cd.setTime(time);
			cd.add(Calendar.MINUTE, -(minute));
			time = cd.getTime();
			retDate.add(sdf.format(cd.getTime()));
		}
		return retDate;

	}

	public List<String> getTimeByMina(String startTime, String endTime, int minute) throws ParseException {
		List<String> retDate = new ArrayList<String>();
		long min = 1000 * 60;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long getmin = (sdf.parse(endTime).getTime() - sdf.parse(startTime).getTime()) / min; // 获取两个时间的分钟数
		int num = (int) (getmin / minute); // 有多少个分钟数
		Calendar cd = Calendar.getInstance();
		Date time = sdf.parse(endTime);
		retDate.add(endTime);
		for (int i = 0; i < num; i++) {
			cd.setTime(time);
			cd.add(Calendar.MINUTE, -(minute));
			time = cd.getTime();
			retDate.add(sdf.format(cd.getTime()));
		}
		return retDate;

	}

	public String getYestodayLast() {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");

		return sdf.format(calendar.getTime()) + "24:00:00";

	}

	public String getYestodayLasta() {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");

		return sdf.format(calendar.getTime()) + "00:00:00";

	}

	public String getYestodayFirst() {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");

		return sdf.format(calendar.getTime()) + "00:00:00";

	}

	// 截取时间 时：分
	public String gettime(String datetime) {
		datetime = datetime.substring(datetime.indexOf(" ") + 1, datetime.lastIndexOf(":"));
		return datetime;
	}

	public List getDateTimesBytime() throws ParseException {
		List<String> timeByMin = getTimeByMin(getYestodayFirst(), getYestodayLast(), 30);
		List<String> sevenDateList = getDateByNum(timeByMin, 7); // 7天之内的日期
		List<String> dateList = new ArrayList<String>();
		for (int i = 0; i < sevenDateList.size(); i++) {
			String getdate = sevenDateList.get(i);
			getdate = getdate.substring(0, getdate.indexOf(" "));
			dateList.add(getdate);
		}

		String getdate = dateList.stream().distinct().collect(Collectors.joining(","));
		String[] date = getdate.split(",");
		List retMessage = new ArrayList();
		for (int i = 0; i < date.length; i++) {
			Map<String, Object> retMapDate = new HashMap<String, Object>();
			List<String> retListdate = new ArrayList<String>();
			for (int j = 0; j < sevenDateList.size(); j++) {
				if (sevenDateList.get(j).indexOf(date[i]) != -1) {
					retListdate.add(sevenDateList.get(j));
				}
			}
			retMapDate.put("name", date[i]);
			retMapDate.put("date", retListdate);
			retMessage.add(retMapDate);
		}
		return retMessage;
	}


//	public static void main(String[] args) throws ParseException {
//		DateUtils du = new DateUtils();
//		List getdate = du.getDateTimesBytime();
//		for (int i = 0; i < getdate.size(); i++) {
//			StringBuffer sbsql = new StringBuffer();
//			Map getdateMap = (Map) getdate.get(i);
//			String name = getdateMap.get("name").toString();
//			List<String> getdateList = (List<String>) getdateMap.get("date");
//			for (int j = 0; j < getdateList.size()-1; j++) {
//				String time = getdateList.get(j);
//				time = time.substring(time.indexOf(" ")+1,time.lastIndexOf(":"));
//				sbsql.append(" WHEN cjsj<'"+getdateList.get(j)+"' AND cjsj>'"+getdateList.get(j+1)+"' THEN '"+time+"'");
//			}
//		}

//		List<String> timeByMin = du.getTimeByMin(du.getYestodayFirst(),du.getYestodayLast(),30);
//		for (int i = 0; i < timeByMin.size(); i++) {
//			String time = timeByMin.get(i);
//			time = time.substring(time.indexOf(" ")+1,time.lastIndexOf(":"));
//		}

//	}

//	private final static String timeformat = "yyyy-MM-dd";
//	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	private final static String dayStart = " 00:00:00";
//	private final static String dayEnd = " 23:59:59";
//
//	public static void main(String[] args) {
//        //  Auto-generated method stub
//    }
  
    // 获得当天0点时间
    public static Date getTimesmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
 // 获得当天0点时间
    public static Date getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    // 获得昨天0点时间
    public static Date getYesterdaymorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesmorning().getTime()-3600*24*1000);
        return cal.getTime();
    }
    
 // 获得昨天240点时间
    public static Date getYesterdaynight() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getTimesnight().getTime()-3600*24*1000);
        return cal.getTime();
    }
  
    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }
  
    // 获得本周日24点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        cal.add(Calendar.SECOND,-1);
        return cal.getTime();
    }
  
    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
  
    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.add(Calendar.SECOND,-1);
        return cal.getTime();
    }
  
    public static Date getLastMonthStartMorning() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesMonthmorning());
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }
  
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
  
    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentQuarterStartTime());
        cal.add(Calendar.MONTH, 3);
        cal.add(Calendar.SECOND,-1);
        return cal.getTime();
    }
  
  
    public static Date getCurrentYearStartTime() {
    	SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy");
    	longSdf.format(new Date());
        try {
			return longSdf.parse(shortSdf.format(new Date())+"-01-01 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
    }
  
    public static Date getCurrentYearEndTime() {
    	SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy");
    	longSdf.format(new Date());
        try {
			return longSdf.parse(shortSdf.format(new Date())+"-12-31 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
    }
}