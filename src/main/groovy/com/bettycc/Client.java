package com.bettycc;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {

    private static final String CHANNELS_NAME = "channels.txt";
    private static final String APK_NAME = "zssq.apk";
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        List<String> readChannels = readChannels();
        System.out.println(
                "please input apk path( or file name ,but package output path is ./out/***-channel.apk). default zssq.apk?");
        String filepath = scan.nextLine();
        if (filepath == null || filepath.trim().equals("")) {
            filepath = APK_NAME;
        }
        startPackTask(filepath, readChannels);
    }

    private static void readPack() throws Exception {
        String basePath = "/opt/";
        String filename = "/out/zssq-360.apk";
        ZipFile file = new ZipFile(basePath + filename);

        Enumeration<? extends ZipEntry> entries = file.getEntries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            System.out.println(entry.getName());
        }
    }

    private static List<String> readChannels() throws Exception {
        System.out.println("start read channels !");
        BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(CHANNELS_NAME)));
        List<String> channels = new ArrayList<String>();
        String channel = null;
        while ((channel = read.readLine()) != null) {
            channels.add(channel);
        }
        System.out.println("finish read channels ! channel count:" + channels.size());
        System.out.println("");
        return channels;
    }

    public static void startPackTask(String filepath, List<String> channels) throws Exception {
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("task pack start ");
        System.out.println("-----------------------");
        System.out.println();
        long start = System.currentTimeMillis();
        String filename = filepath;
        String outpath = "./out";
        int index = filepath.lastIndexOf('/');
        if (index != -1) {
            filename = filepath.substring(index + 1);
        }
        ZipFile file = new ZipFile(filepath);
        Map<ZipEntry, byte[]> readZipAllEntry = readZipAllEntry(file);
        System.out.println("find output dir ....");
        File outDir = new File(outpath);
        if (!outDir.exists()) {
            System.out.println("output dir not exists.");
            outDir.mkdirs();
            System.out.println("create output dir finish! ");
        } else {
            System.out.println("output dir exists .");
        }
        System.out.println();

        for (String channel : channels) {
            long packStartTime = System.currentTimeMillis();
            String apkName = "out/" + filename.substring(0, filename.length() - 4) + "-" + channel + ".apk";

            // copyFile(basePath + filename, apkName);
            writePack(readZipAllEntry, file, apkName, channel);

            long takeTime = System.currentTimeMillis() - packStartTime;
            long ms = takeTime % 1000;
            System.out.println("package :" + apkName + " success, take time " + (takeTime) / 1000 + "s " + ms + "ms");
        }
        file.close();
        long totalTakeTime = System.currentTimeMillis() - start;
        long ms = totalTakeTime % 1000;
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("task pack success !");
        System.out.println("-----------------------");
        System.out.println();
        System.out.println(
                "package count:" + channels.size() + ", total time: " + (totalTakeTime) / 1000 + "s " + ms + "ms ");
    }

    private static void writePack(Map<ZipEntry, byte[]> readZipAllEntry, ZipFile file, String path, String channel)
            throws Exception {
        ZipOutputStream zot = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(path)));

        Iterator<ZipEntry> iterator = readZipAllEntry.keySet().iterator();
        while (iterator.hasNext()) {
            ZipEntry entry = iterator.next();

            zot.putNextEntry(entry);
            byte[] data = readZipAllEntry.get(entry);
            zot.write(data, 0, data.length);
            // System.out.println(entry);
            // System.out.println(entry.getSize() + " ," +
            // entry.getCompressedSize() + "," + data.length);
            zot.closeEntry();
        }
        zot.putNextEntry(new ZipEntry("META-INF/zssq_channel_" + channel));
        zot.closeEntry();
        zot.close();
    }

    private static Map<ZipEntry, byte[]> readZipAllEntry(ZipFile file) throws Exception {
        Map<ZipEntry, byte[]> entryMaps = new HashMap<ZipEntry, byte[]>();
        Enumeration<? extends ZipEntry> entries = file.getEntries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            int len = -1;
            byte[] data = new byte[512];
            InputStream istream = file.getInputStream(entry);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = istream.read(data, 0, 512)) != -1) {
                bos.write(data, 0, len);
            }
            bos.close();
            istream.close();

            byte[] result = bos.toByteArray();
            entryMaps.put(entry, result);
        }
        return entryMaps;
    }
}

