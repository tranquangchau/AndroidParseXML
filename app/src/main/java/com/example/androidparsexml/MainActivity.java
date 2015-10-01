package com.example.androidparsexml;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String LOG_TAG = "MainActivity.java";

	TextView textView1,textView2,textView3,textView4;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView1 = (TextView) findViewById(R.id.textView);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView4 = (TextView) findViewById(R.id.textView4);

		try {
			// parse our XML
			Toast.makeText(getApplicationContext(), "Dang xu ly xml b1", Toast.LENGTH_SHORT).show();
			new parseXmlAsync().execute();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), "Xu ly xong xml b2?", Toast.LENGTH_SHORT).show();
	}

	Integer i1=0;
	public void  btn_action(View v){
		if (i1==0){
			String a1 = parsedDataSet.get(0).getName();
			textView1.setText(a1);
			String a2 = parsedDataSet.get(0).getAge();
			textView2.setText(a2);
			String a3 = parsedDataSet.get(0).getBirthday();
			textView3.setText(a3);
			String a4 = parsedDataSet.get(0).getEmailAddress();
			textView4.setText(a4);
			i1++;
		}else{
			String a1 = parsedDataSet.get(i1).getName();
			textView1.setText(a1);
			String a2 = parsedDataSet.get(i1).getAge();
			textView2.setText(a2);
			String a3 = parsedDataSet.get(i1).getBirthday();
			textView3.setText(a3);
			String a4 = parsedDataSet.get(i1).getEmailAddress();
			textView4.setText(a4);
			i1++;
		}
	}

	/*
	 * @We are using an AsyncTask to avoid
	 * android.os.NetworkOnMainThreadException when parsing from a URL
	 * @If you don't know a thing about AsynTasks, there are a lot of excellent
	 * tutorial out there, see this thread
	 * https://www.codeofaninja.com/2013/02/parse-xml-in-android.html
	 */

	public List list1= new ArrayList();
	public List<ParsedDataSet> parsedDataSet;
	private class parseXmlAsync extends AsyncTask<String, String, String> {


		@Override
		protected String doInBackground(String... strings) {

			try {

				/*
				 * You may change the value of x to try different sources of XML
				 * 
				 * @1 = XML from SD Card
				 * 
				 * @2 = XML from URL
				 * 
				 * @3 = XML from assets folder
				 */
				int x = 3;

				// initialize our input source variable
				InputSource inputSource = null;

				// XML from sdcard
				if (x == 1) {
					
					// make sure sample.xml is in your root SD card directory
					File xmlFile = new File(
							Environment.getExternalStorageDirectory()
									+ "/sample.xml");
					FileInputStream xmlFileInputStream = new FileInputStream(
							xmlFile);
					inputSource = new InputSource(xmlFileInputStream);
				}

				// XML from URL
				else if (x == 2) {
					// specify a URL
					// make sure you are connected to the internet
					URL url = new URL(
							"http://demo.codeofaninja.com/AndroidXml/sample.xml");
					inputSource = new InputSource(url.openStream());
				}

				// XML from assets folder
				else if (x == 3) {
					inputSource = new InputSource(getAssets()
							.open("sample.xml"));
				}

				// instantiate SAX parser
				SAXParserFactory saxParserFactory = SAXParserFactory
						.newInstance();
				SAXParser saxParser = saxParserFactory.newSAXParser();

				// get the XML reader
				XMLReader xmlReader = saxParser.getXMLReader();

				// prepare and set the XML content or data handler before
				// parsing
				XmlContentHandler xmlContentHandler = new XmlContentHandler();
				xmlReader.setContentHandler(xmlContentHandler);

				// parse the XML input source
				xmlReader.parse(inputSource);

				// put the parsed data to a List

				parsedDataSet = xmlContentHandler.getParsedData();

				// we'll use an iterator so we can loop through the data
				Iterator<ParsedDataSet> i = parsedDataSet.iterator();
				ParsedDataSet dataItem;


				while (i.hasNext()) {

					dataItem = (ParsedDataSet) i.next();

					/*
					 * parentTag can also represent the main type of data, in
					 * our example, "Owners" and "Dogs"
					 */
					String parentTag = dataItem.getParentTag();
					Log.v(LOG_TAG, "parentTag: " + parentTag);

					if (parentTag.equals("Owners")) {
						Log.v(LOG_TAG, "Name: " + dataItem.getName());
						Log.v(LOG_TAG, "Age: " + dataItem.getAge());
						Log.v(LOG_TAG,
								"EmailAddress: " + dataItem.getEmailAddress());
					}

					else if (parentTag.equals("Dogs")) {
						Log.v(LOG_TAG, "Name: " + dataItem.getName());
						Log.v(LOG_TAG, "Birthday: " + dataItem.getBirthday());
					}
				}
				list1 = parsedDataSet;
				//textView1.setText();
				parsedDataSet.get(0).getAge();

			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String lenghtOfFile) {
			// your do stuff after parsing the XML
		}
	}
}
