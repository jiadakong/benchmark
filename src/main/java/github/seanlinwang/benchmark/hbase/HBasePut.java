package github.seanlinwang.benchmark.hbase;

import github.seanlinwang.benchmark.BenchmarkBase;
import github.seanlinwang.benchmark.util.Format;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

import javax.jms.JMSException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * mq sender
 */
public class HBasePut extends BenchmarkBase {

	private String[] resultSet;
	private DateFormat dateFormat = Format.getDateFormat();
	private Calendar cal = Format.getCalendar();
	private PrintWriter out;

	private HTable table;
	private String rowPrefix;

	public HBasePut(PrintWriter out, String tableName, String rowPrefix, int times) throws IOException {
		this.rowPrefix = rowPrefix;
		Configuration conf = (Configuration) HBaseConfiguration.create();
		this.table = new HTable(conf, tableName);
		this.resultSet = new String[times];
		this.out = out;
	}

	@Override
	public void run() {
		for (int i = 0; i < resultSet.length; i++) {
			final long now = System.currentTimeMillis();
			Put put = new Put(Bytes.toBytes(rowPrefix + i));
			put.add(Bytes.toBytes("cf"), Bytes.toBytes("col"), Bytes.toBytes(i));
			try {
				table.put(put);
			} catch (IOException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			resultSet[i] = ((end - now) + "," + end);
		}
	}

	@Override
	public void deleteTestData() {
		for (int i = 0; i < resultSet.length; i++) {
			Delete del = new Delete(Bytes.toBytes(rowPrefix + i));
			try {
				table.delete(del);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void print() {
		for (String line : resultSet) {
			String[] segs = StringUtils.split(line, ',');
			out.write(segs[0]);
			out.write(",");
			this.cal.setTimeInMillis(Long.valueOf(segs[1]));
			out.write(this.dateFormat.format(cal.getTime()) + "\n");
		}
	}

	public static void main(String[] args) throws JMSException, IOException, InterruptedException {
		PrintWriter out = BenchmarkBase.createOutput("hbase-put.csv");

		Configuration config = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(config);
		String table = "benchmark_put";
		HTableDescriptor htable = new HTableDescriptor(table);
		HColumnDescriptor cf1 = new HColumnDescriptor("cf");
		htable.addFamily(cf1); // adding new ColumnFamily
		admin.createTable(htable);
		while (!admin.isTableAvailable(table)) {
			Thread.sleep(500);
		}
		System.out.println("create table:" + table);

		HBasePut[] senders = new HBasePut[4];
		for (int i = 0; i < senders.length; i++) {
			senders[i] = new HBasePut(out, table, "test_row" + i + "-", 10000);
		}
		Thread[] ts = new Thread[senders.length];
		for (int i = 0; i < senders.length; i++) {
			Thread t = new Thread(senders[i]);
			ts[i] = t;
			t.start();
		}

		for (int i = 0; i < ts.length; i++) {
			ts[i].join();
		}

		for (int i = 0; i < senders.length; i++) {
			HBasePut sender = senders[i];
			sender.print();
			sender.deleteTestData();
		}

		admin.disableTable(table);
		admin.deleteTable(table);
		while (admin.isTableAvailable(table)) {
			Thread.sleep(500);
		}
		System.out.println("delete table:" + table);

		out.close();
	}

}
