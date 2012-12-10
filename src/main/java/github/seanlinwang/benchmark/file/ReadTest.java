package github.seanlinwang.benchmark.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * Project: IOTest
 * 
 * File Created at 2011-12-2
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
 * TODO Comment of Test
 * 
 * @author Leo Liang
 * 
 */
public class ReadTest {
	public static final String	INPUT_FILE	= "/Users/leungleo/readTestFile";
	public static final int		BUF_SIZE	= 65536;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		readByFileInputStream();
		readByFileInputStreamWithBuffer();
		readByBufferedInputStream();
		readByBufferedInputStreamWithBuffer();
		readByRandomAccessFile();
		readByRandomAccessFileWithBuffer();
		readByFileChannel();
		readByFileChannelWithBuffer();
		readByFileChannelWithBuffer2();
		readByFileChannelWithDirectBuffer();
		readByFileChannelWithDirectBufferAndArrayGet();
		readByMappedFile();
		readByMappedFileWithBuffer();
//		 FileOutputStream fos = new FileOutputStream(INPUT_FILE);
//		 for (int i = 0; i < 1024 * 1024 * 10; i++){
//		 fos.write((byte)99);
//		 }
//		 fos.close();
	}

	private static void readByFileInputStream() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		int b;
		long checkSum = 0L;
		while ((b = f.read()) != -1) {
			checkSum += b;
		}
		f.close();
		System.out.println("readByFileInputStream CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByFileInputStreamWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		byte[] barray = new byte[BUF_SIZE];
		long checkSum = 0L;
		int nRead;
		while ((nRead = f.read(barray, 0, BUF_SIZE)) != -1)
			for (int i = 0; i < nRead; i++)
				checkSum += barray[i];
		f.close();
		System.out.println("readByFileInputStreamWithBuffer CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByBufferedInputStream() throws Exception {
		long start = System.currentTimeMillis();
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(INPUT_FILE));
		int b;
		long checkSum = 0L;
		while ((b = f.read()) != -1)
			checkSum += b;
		f.close();
		System.out.println("readByBufferedInputStream CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByBufferedInputStreamWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(INPUT_FILE));
		byte[] barray = new byte[BUF_SIZE];
		long checkSum = 0L;
		int nRead;
		while ((nRead = f.read(barray, 0, BUF_SIZE)) != -1)
			for (int i = 0; i < nRead; i++)
				checkSum += barray[i];
		f.close();
		System.out.println("readByBufferedInputStreamWithBuffer CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByRandomAccessFile() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(INPUT_FILE, "r");
		int b;
		long checkSum = 0L;
		while ((b = f.read()) != -1)
			checkSum += b;
		f.close();
		System.out.println("readByRandomAccessFile CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByRandomAccessFileWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		RandomAccessFile f = new RandomAccessFile(INPUT_FILE, "r");
		byte[] barray = new byte[BUF_SIZE];
		long checkSum = 0L;
		int nRead;
		while ((nRead = f.read(barray, 0, BUF_SIZE)) != -1)
			for (int i = 0; i < nRead; i++)
				checkSum += barray[i];
		f.close();
		System.out.println("readByRandomAccessFileWithBuffer CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByFileChannel() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		ByteBuffer bb = ByteBuffer.allocate(BUF_SIZE);
		long checkSum = 0L;
		int nRead;
		while ((nRead = ch.read(bb)) != -1) {
			if (nRead == 0)
				continue;
			bb.position(0);
			bb.limit(nRead);
			while (bb.hasRemaining())
				checkSum += bb.get();
			bb.clear();
		}
		f.close();
		System.out
				.println("readByFileChannel CheckSum: " + checkSum + " Time: " + (System.currentTimeMillis() - start));
	}

	private static void readByFileChannelWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		ByteBuffer bb = ByteBuffer.allocate(BUF_SIZE);
		byte[] barray = new byte[BUF_SIZE / 2];
		long checkSum = 0L;
		int nRead, nGet;
		while ((nRead = ch.read(bb)) != -1) {
			if (nRead == 0)
				continue;
			bb.position(0);
			bb.limit(nRead);
			while (bb.hasRemaining()) {
				nGet = Math.min(bb.remaining(), BUF_SIZE / 2);
				bb.get(barray, 0, nGet);
				for (int i = 0; i < nGet; i++)
					checkSum += barray[i];
			}
			bb.clear();
		}
		f.close();
		System.out.println("readByFileChannelWithBuffer CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByFileChannelWithBuffer2() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		byte[] barray = new byte[BUF_SIZE];
		ByteBuffer bb = ByteBuffer.wrap(barray);
		long checkSum = 0L;
		int nRead;
		while ((nRead = ch.read(bb)) != -1) {
			for (int i = 0; i < nRead; i++)
				checkSum += barray[i];
			bb.clear();
		}
		f.close();
		System.out.println("readByFileChannelWithBuffer2 CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByFileChannelWithDirectBuffer() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		ByteBuffer bb = ByteBuffer.allocateDirect(BUF_SIZE);
		long checkSum = 0L;
		int nRead;
		while ((nRead = ch.read(bb)) != -1) {
			bb.position(0);
			bb.limit(nRead);
			while (bb.hasRemaining())
				checkSum += bb.get();
			bb.clear();
		}
		f.close();
		System.out.println("readByFileChannelWithDirectBuffer CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByFileChannelWithDirectBufferAndArrayGet() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		ByteBuffer bb = ByteBuffer.allocateDirect(BUF_SIZE);
		byte[] barray = new byte[BUF_SIZE / 2];
		long checkSum = 0L;
		int nRead, nGet;
		while ((nRead = ch.read(bb)) != -1) {
			if (nRead == 0)
				continue;
			bb.position(0);
			bb.limit(nRead);
			while (bb.hasRemaining()) {
				nGet = Math.min(bb.remaining(), BUF_SIZE / 2);
				bb.get(barray, 0, nGet);
				for (int i = 0; i < nGet; i++)
					checkSum += barray[i];
			}
			bb.clear();
		}
		f.close();
		System.out.println("readByFileChannelWithDirectBufferAndArrayGet CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}

	private static void readByMappedFile() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		MappedByteBuffer mb = ch.map(MapMode.READ_ONLY, 0L, ch.size());
		long checkSum = 0L;
		while (mb.hasRemaining())
			checkSum += mb.get();
		f.close();
		System.out.println("readByMappedFile CheckSum: " + checkSum + " Time: " + (System.currentTimeMillis() - start));
	}

	private static void readByMappedFileWithBuffer() throws Exception {
		long start = System.currentTimeMillis();
		FileInputStream f = new FileInputStream(INPUT_FILE);
		FileChannel ch = f.getChannel();
		MappedByteBuffer mb = ch.map(MapMode.READ_ONLY, 0L, ch.size());
		byte[] barray = new byte[BUF_SIZE];
		long checkSum = 0L;
		int nGet;
		while (mb.hasRemaining()) {
			nGet = Math.min(mb.remaining(), BUF_SIZE);
			mb.get(barray, 0, nGet);
			for (int i = 0; i < nGet; i++)
				checkSum += barray[i];
		}
		f.close();
		System.out.println("readByMappedFileWithBuffer CheckSum: " + checkSum + " Time: "
				+ (System.currentTimeMillis() - start));
	}
}
