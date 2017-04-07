package me.nabil.demo.sleepycat;

import java.io.Serializable;

/**
 * ETL图片DTO
 *
 * @date 17/3/27
 * @since 1.0.0
 */
public final class ETLPictureDTO implements Serializable {

    private ETLPictureDTO(String crawlerId, int picType, String houseId, String ext, String key) {
        this.crawlerId = crawlerId;
        this.picType = picType;
        this.houseId = houseId;
        this.ext = ext;
        this.key = key;
    }

    public ETLPictureDTO() {
    }

    public static ETLPictureDTO of(String line) {
        try {
            String[] args = line.trim().split("\t");
            if (args.length != 5) {
                return null;
            }
            return new ETLPictureDTO(args[0], Integer.valueOf(args[1]), args[2], args[3], args[4]);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 上游ID
     */
    private String crawlerId;

    /**
     * 图片类型：1-房源图；2：经纪人图片
     */
    private int picType;

    /**
     * 对应房源的ID
     */
    private String houseId;

    /**
     * 图片类型
     */
    private String ext;

    /**
     * 图片key
     */
    private String key;

    public final String getCrawlerId() {
        return crawlerId;
    }

    public final int getPicType() {
        return picType;
    }

    public final String getHouseId() {
        return houseId;
    }

    public final String getExt() {
        return ext;
    }

    public final String getKey() {
        return key;
    }


    @Override
    public String toString() {
        return "ETLPictureDTO{" +
                "crawlerId='" + crawlerId + '\'' +
                ", picType=" + picType +
                ", houseId='" + houseId + '\'' +
                ", ext='" + ext + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
