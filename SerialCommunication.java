package dp;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import gnu.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SerialCommunication implements SerialPortEventListener {

	private CommPortIdentifier portID;
	private Enumeration<CommPortIdentifier> portList;
	private InputStream inputstream;
	private OutputStream outputstram;

	private static SerialPort serialPort;
	public static String test = "";
	private static SerialCommunication uniqueInstance;

	public void init() {
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portID = (CommPortIdentifier) portList.nextElement();
			if (portID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portID.getName().equals("COM3")) {
					System.out.println("找到com3");
				}
				try {
					serialPort = (SerialPort) portID.open("COM3", 2000);
					serialPort.addEventListener( new SerialCommunication());
					serialPort.notifyOnDataAvailable(true);
					serialPort.setSerialPortParams(38400,
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);
					test = "";
					outputstram = serialPort.getOutputStream();
				} catch (PortInUseException e) {
					e.printStackTrace();
				} catch (TooManyListenersException e) {
					e.printStackTrace();
				} catch (UnsupportedCommOperationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new SerialCommunication().init();
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:

		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			readComm();
			break;
		default:
			break;
		}
	}

	private void readComm() {
		byte[] readBuffer = new byte[1024];
		try {
			inputstream = ((CommPort) uniqueInstance.serialPort).getInputStream();
			int len = 0;
			while ((len = inputstream.read(readBuffer)) != -1) {
				byte[] jqdata = subBytes(readBuffer, 0, len);
				String data = bytes2HexString(jqdata);
				System.out.println("实时反馈：" + data);
				System.out.println("实时反馈：" + jiexi(data));
				test += new String(readBuffer, 0, len).trim();
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static String jiexi(String data) {
		String reg = ".*b12.*";
		String jx = "";
		System.out.println("1111111");
		if (data.matches(reg)) {
			System.out.println("2222222");
			String[] str = data.split("b12");
			for (int i = 1; i < str.length; i++) {
				if (str[i].substring(0, 3)!=null) {
					System.out.println("3333333");
					jx = "Gas:" + (Integer.parseInt(str[i].substring(13, 16), 16))/542;
				insertserialdata((Integer.parseInt(str[i].substring(13,16 ),16))/542);
				} else {
					jx = "传输信息";
				}
			}
		} else {
			jx = "数据无信息";
		}
		return jx;
	}

	public static void insertserialdata(int dcbianhao) {
		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://118.89.38.92:3306/forest-fire-prevention"; // JDBC的URL
			Connection conn;
			conn = DriverManager.getConnection(url, "xzq", "12345678");
			Statement stmt = conn.createStatement(); // 创建Statement对象
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			String sql = "insert into collectdata(cllectname,collecttemp,collecthumi,collectflame,collectgas,time) values('A',26,15,125.5,'"+ dcbianhao + "','"+df.format(new Date())+"')"; // 要执行的SQL
			System.out.println(df.format(new Date()));
			stmt.execute(sql);
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex;
		}
		return ret;
	}

	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = src[i];
		return bs;
	}
	
}