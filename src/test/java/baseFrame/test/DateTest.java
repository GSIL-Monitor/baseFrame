package baseFrame.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class DateTest {

	@Test
	public void testPattern(){
//		String datePattern = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
//		String date = "2016-12-22";
//		Pattern p = Pattern.compile(datePattern);
//		Matcher m = p.matcher(date);
//		if(m.matches())
//			System.out.println("格式正确");
//		else
//			System.out.println("格式错误");
		
		String datePattern = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
		String date = "2016-12-22 71:23:91";
		Pattern p = Pattern.compile(datePattern);
		Matcher m = p.matcher(date);
		if(m.matches())
			System.out.println("格式正确");
		else
			System.out.println("格式错误");
		
	}
	
	@Test
	public void display() throws ParseException{
		 //display("2008-08-08", "2008-08-24");
		 displayMonth("2008-08", "2009-04");
	}
	
	public  void display(String dateFirst, String dateSecond){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         
        try{
            Date dateOne = dateFormat.parse(dateFirst);
            Date dateTwo = dateFormat.parse(dateSecond);
             
            Calendar calendar = Calendar.getInstance();
             
            calendar.setTime(dateOne);
             
            while(calendar.getTime().before(dateTwo)){               
                System.out.println(dateFormat.format(calendar.getTime()));
                 
                calendar.add(Calendar.DAY_OF_MONTH, 1);               
            }
        }
        catch(Exception e){
        	System.out.println(e);
            e.printStackTrace();
        }
         
    }
	
	public  void displayMonth(String dateFirst, String dateSecond) throws ParseException{
		Date d1 = new SimpleDateFormat("yyyy-MM").parse(dateFirst);//定义起始日期

		Date d2 = new SimpleDateFormat("yyyy-MM").parse(dateSecond);//定义结束日期

		Calendar dd = Calendar.getInstance();//定义日期实例

		dd.setTime(d1);//设置日期起始时间

		while(dd.getTime().before(d2)){//判断是否到结束日期

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	
			String str = sdf.format(dd.getTime());
	
			System.out.println(str);//输出日期结果
	
			dd.add(Calendar.MONTH, 1);//进行当前日期月份加1

		}
         
    }
	
	
	@Test
	public void testWeekOfDay() throws ParseException{
		System.out.println(getWeekOfDate("2017-2-28"));
	}
	
	public static int getWeekOfDate(String date) throws ParseException {      
	    int[] weekOfDays = {7, 1, 2, 3, 4, 5, 6};        
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null && !"".equals(date)){   
	         calendar.setTime(format.parse(date));      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    

	}
}
