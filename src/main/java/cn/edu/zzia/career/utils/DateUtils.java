package cn.edu.zzia.career.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *@author jon
 *@date 2016年11月30日 下午4:29:19
 *@desc 时间操作工具类
 */
public class DateUtils {

	public enum DatePattern{
		
		yyyyMMddHHmmss("yyyyMMddHHmmss"),
		
		yyyy_MM_dd("yyyy-MM-dd"),
		
		yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss");
	
		private String pattern;
		public String getPattern() {
			return pattern;
		}
		private DatePattern(String pattern){
			this.pattern = pattern;
		}
		
	}
	
	public static Integer getNowDateTime(){
		return (int) (System.currentTimeMillis()/1000);
	}
	
	public static String getNowDateTimeYMDHMS(){
		return getNowDateTime(DatePattern.yyyyMMddHHmmss);
	}
	
	public static String getNowDateTime(DatePattern pattern){
		Date date = new Date();
		SimpleDateFormat fm = new SimpleDateFormat(pattern.getPattern());
		return fm.format(date);
	}
	
	public static Long parseStringToLong(String dateTime, DatePattern pattern){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern.getPattern());
		Date date = new Date();
		try {
			date = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime()/1000;
	}
	
//	public static String parseLongToString(Long dateTime, DatePattern pattern){
//		
//		if(dateTime == null) return "";
//		Date date = new Date(dateTime * 1000l);
//		
//		return DateFormatUtils.format(date, pattern.getPattern());
//	}
	
	public static void main(String[] arg0){
		/*long l = parseStringToLong("2016-12-22", DatePattern.yyyy_MM_dd);
		System.out.println(l);
		l = parseStringToLong("2016-12-22 17:03:15", DatePattern.yyyy_MM_dd_HH_mm_ss);
		System.out.println(l);
		System.out.println(System.currentTimeMillis()/1000);*/
		/*long l = 1482459883L;
		System.out.println(DateUtils.parseLongToString(l, DatePattern.yyyy_MM_dd_HH_mm_ss));*/	
		
		
	}
}
