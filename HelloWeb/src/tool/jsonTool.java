package tool;

import java.sql.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class jsonTool {
	public static  String creatJsonString(List list) {
		JsonConfig config = new JsonConfig();
		JSONValueProcess jsonValueProcessor = new JSONValueProcess();
		config.registerJsonValueProcessor(Date.class, jsonValueProcessor);
		JSONArray jsonArray = JSONArray.fromObject(list, config);
//		System.out.println(jsonArray.toString());
		return jsonArray.toString();
	}
	
	

}
