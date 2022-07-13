package dev.mja00.timecorrect;

public class TimeInfo {

    private String abbreviation;
    private String client_ip;
    private String datetime;
    private Integer day_of_week;
    private Integer day_of_year;
    private Boolean dst;
    private String dst_from;
    private Integer dst_offset;
    private String dst_until;
    private Integer raw_offset;
    private String timezone;
    private final Long unixtime;
    private String utc_datetime;
    private String utc_offset;
    private Integer week_number;

    public TimeInfo(String abbreviation, String client_ip, String datetime, Integer day_of_week, Integer day_of_year, Boolean dst, String dst_from, Integer dst_offset, String dst_until, Integer raw_offset, String timezone, Long unixtime, String utc_datetime, String utc_offset, Integer week_number) {
        this.abbreviation = abbreviation;
        this.client_ip = client_ip;
        this.datetime = datetime;
        this.day_of_week = day_of_week;
        this.day_of_year = day_of_year;
        this.dst = dst;
        this.dst_from = dst_from;
        this.dst_offset = dst_offset;
        this.dst_until = dst_until;
        this.raw_offset = raw_offset;
        this.timezone = timezone;
        this.unixtime = unixtime;
        this.utc_datetime = utc_datetime;
        this.utc_offset = utc_offset;
        this.week_number = week_number;
    }

    public String getUtc_datetime() {
        return utc_datetime;
    }

    public Long getUnixtime() {
        return unixtime;
    }
}
