package ctlTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import models.Record;
import models.RecordsSet;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import controller.Excel;
import controller.ImEx_port;

public class ImEx_portTest {

	RecordsSet rs;
	private String[] tRec=new String[]{"tName","tCode","15-5-10","tType","100","1","1%","2%","tState","tRemark","tHandle"};
	Record rec;
	@Before
	public void setUp() throws Exception {
		rs=new RecordsSet();
		rec=new Record(tRec);
		File file = new File(pFolder);
		// 创建目录
		if (!file.exists()) {
			file.mkdirs();// 目录不存在的情况下，创建目录。
		}
	}

	@After
	public void tearDown() throws Exception {
		//recover
		rs.removeRecord(rec);
		rs.save();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ExcelTest.deletefile("testsrc/data");
	}

	@Test
	public void testImport() throws Exception {
		String path=pFolder+"/testImport.xls";
		String[][] nullStr=new String[1][11];
		Excel.write(path, nullStr);
		ImEx_port.Import(path);
		
		RecordsSet rs=new RecordsSet();
		assertEquals(nullStr, new String[1][11]);
	}


	private String pFolder="testsrc/data/testExcel";
	@Test
	public void testExport() throws JSONException, IOException {
	String path=pFolder+"/testExport.xls";

	rs.addRecord(rec);
	rs.save();
	ImEx_port.Export(path);
	
	assertEquals(true,rs.removeRecord(rec) );


	}

}
