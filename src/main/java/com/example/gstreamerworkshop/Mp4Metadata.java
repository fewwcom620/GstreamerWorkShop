package com.example.gstreamerworkshop;

import java.util.HashMap;
import java.util.Map;

public class Mp4Metadata {
    private String fileName;

    private String duration;

    private String fileType;

    private String names;

    private String audioRate;

    private String createdBy;

    private String length;

    private String kb;

    private String mb;

    private String gb;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("fileName", getFileName());
        map.put("fileType", getFileType());
        map.put("duration", getDuration());
        map.put("names", getNames());
        map.put("rate", getAudioRate());
        map.put("createdBy", getCreatedBy());
        map.put("bytes", getLength());
        map.put("kbs", getKb());
        map.put("mbs", getMb());
        map.put("gbs", getGb());

        return map;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getAudioRate() {
        return audioRate;
    }

    public void setAudioRate(String audioRate) {
        this.audioRate = audioRate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getKb() {
        return kb;
    }

    public void setKb(String kb) {
        this.kb = kb;
    }

    public String getMb() {
        return mb;
    }

    public void setMb(String mb) {
        this.mb = mb;
    }

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }
}
