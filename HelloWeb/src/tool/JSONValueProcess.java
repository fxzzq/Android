package tool;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JSONValueProcess implements JsonValueProcessor {
	
	private String DateFormat="yyyy-MM-dd";
	/*yyyy-MM-dd HH:mm:ss 
	��-��-�� ʱ:��:��
	M��д��Ϊ�����֡��¡��롰�֡�
	д��Сд�޷�����װ��
*/
	public JSONValueProcess() {
		
	}

	@Override
	 public Object processArrayValue(Object value, JsonConfig config) {  
        return process(value);  
    }  
  
    public Object processObjectValue(String key, Object value, JsonConfig config) {  
        return process(value);  
    }  
    private Object process(Object value){  
        if(value instanceof Date){  
            SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.CHINA);  
            return sdf.format(value);  
        }  
        return value == null ? "" : value.toString();  
    }  

}
