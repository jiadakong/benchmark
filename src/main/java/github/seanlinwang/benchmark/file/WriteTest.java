package github.seanlinwang.benchmark.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

/**
 * Project: IOTest
 * 
 * File Created at 2012-1-18
 * $Id$
 * 
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */

/**
 * TODO Comment of WriteTest
 * 
 * @author Leo Liang
 * 
 */
public class WriteTest {

	public static final String	OUTPUT_FILE	= "/tmp/writeTestFile";
	public static final int		BUF_SIZE	= 65536;
	public static final int		FILE_SIZE	= 1024 * 1024 * 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		writeByFileOutputStream();
		writeByFileOutputStreamWithBuffer();
		writeByBufferedOutputStream();
		writeByBufferedOutputStreamWithBuffer();
		writeByBufferedOutputStreamWithBufferArray();
		writeByBufferedOutputStreamWithArray();
		writeByRandomAccessFile();
		writeByRandomAccessFileWithArray();
		writeByFileChannel();
		writeByFileChannelForceNoMeta();
		writeByFileChannelForceWithMeta();
		writeByFileChannelWithDirect();
		writeByFileChannelWithForceNoMeta();
		writeByFileChannelWithForceWithMeta();
		writeByMMap();
		writeByMMapForce();
	}

	private static void writeByFileOutputStream() throws Exception {
		long start = System.currentTimeMillis();
		FileOutputStream f = new FileOutputStream(OUTPUT_FILE);
		for (int i = 0; i < FILE_SIZE; i++) {
			f.write(99);
		}
		f.flush();
		f.close();
		System.out.println("writeByFileOutputStream Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByFileOutputStreamWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		FileOutputStream f = new FileOutputStream(OUTPUT_FILE);
		byte[] bytes = new byte[FILE_SIZE];
		for (int i = 0; i < FILE_SIZE; i++) {
			bytes[i] = (byte) 99;
		}
		f.write(bytes);
		f.flush();
		f.close();
		System.out.println("writeByFileOutputStreamWithBuffer Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByBufferedOutputStream() throws Exception {
		long start = System.currentTimeMillis();
		BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE));
		for (int i = 0; i < FILE_SIZE; i++) {
			f.write(99);
		}
		f.flush();
		f.close();
		System.out.println("writeByBufferedOutputStream Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByBufferedOutputStreamWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE), BUF_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			f.write(99);
		}
		f.flush();
		f.close();
		System.out.println("writeByBufferedOutputStreamWithBuffer Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByBufferedOutputStreamWithBufferArray() throws Exception {
		long start = System.currentTimeMillis();
		BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE), BUF_SIZE);
		byte[] bytes = new byte[FILE_SIZE];
		for (int i = 0; i < FILE_SIZE; i++) {
			bytes[i] = (byte) 99;
		}
		f.write(bytes);
		f.flush();
		f.close();
		System.out.println("writeByBufferedOutputStreamWithBufferArray Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByBufferedOutputStreamWithArray() throws Exception {
		long start = System.currentTimeMillis();
		BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE));
		byte[] bytes = new byte[FILE_SIZE];
		for (int i = 0; i < FILE_SIZE; i++) {
			bytes[i] = (byte) 99;
		}
		f.write(bytes);
		f.flush();
		f.close();
		System.out.println("writeByBufferedOutputStreamWithArray Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByRandomAccessFile() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		for (int i = 0; i < FILE_SIZE; i++) {
			f.write(99);
		}
		f.close();
		System.out.println("writeByRandomAccessFile Time: " + (System.currentTimeMillis() - start));
	}

	private static void writeByRandomAccessFileWithArray() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		byte[] bytes = new byte[FILE_SIZE];
		for (int i = 0; i < FILE_SIZE; i++) {
			bytes[i] = (byte) 99;
		}
		f.write(bytes);
		f.close();
		System.out.println("writeByRandomAccessFileWithArray Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByFileChannel() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		ByteBuffer bb = ByteBuffer.allocate(FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			bb.put((byte)99);
		}
		f.getChannel().write(bb);
		f.close();
		System.out.println("writeByFileChannel Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByFileChannelForceNoMeta() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		ByteBuffer bb = ByteBuffer.allocate(FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			bb.put((byte)99);
		}
		f.getChannel().write(bb);
		f.getChannel().force(false);
		f.close();
		System.out.println("writeByFileChannelForceNoMeta Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByFileChannelForceWithMeta() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		ByteBuffer bb = ByteBuffer.allocate(FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			bb.put((byte)99);
		}
		f.getChannel().write(bb);
		f.getChannel().force(true);
		f.close();
		System.out.println("writeByFileChannelForceWithMeta Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByFileChannelWithDirect() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		ByteBuffer bb = ByteBuffer.allocateDirect(FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			bb.put((byte)99);
		}
		f.getChannel().write(bb);
		f.close();
		System.out.println("writeByFileChannelWithDirect Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByFileChannelWithForceNoMeta() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		ByteBuffer bb = ByteBuffer.allocateDirect(FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			bb.put((byte)99);
		}
		f.getChannel().write(bb);
		f.getChannel().force(false);
		f.close();
		System.out.println("writeByFileChannelWithForceNoMeta Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByFileChannelWithForceWithMeta() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		ByteBuffer bb = ByteBuffer.allocateDirect(FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			bb.put((byte)99);
		}
		f.getChannel().write(bb);
		f.getChannel().force(true);
		f.close();
		System.out.println("writeByFileChannelWithForceWithMeta Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByMMap() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		MappedByteBuffer mbb = f.getChannel().map(MapMode.READ_WRITE, 0, FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			mbb.put((byte)99);
		}
		f.close();
		System.out.println("writeByMMap Time: " + (System.currentTimeMillis() - start));
	}
	
	private static void writeByMMapForce() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(OUTPUT_FILE, "rw");
		MappedByteBuffer mbb = f.getChannel().map(MapMode.READ_WRITE, 0, FILE_SIZE);
		for (int i = 0; i < FILE_SIZE; i++) {
			mbb.put((byte)99);
		}
		mbb.force();
		f.close();
		System.out.println("writeByMMapForce Time: " + (System.currentTimeMillis() - start));
	}
}
