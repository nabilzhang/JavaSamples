package me.nabil.samples.search.lucence.mobile;

/**
 * 号码item
 *
 * @author nabil
 * @date 2020/12/8
 */
public class MobileItem {
    private int cityId;
    private int provinceId;
    private String mobile;
    private long timestamp;

    public int getCityId() {
        return cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public String getMobile() {
        return mobile;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static final class MobileItemBuilder {
        private int cityId;
        private int provinceId;
        private String mobile;
        private long timestamp;

        private MobileItemBuilder() {
        }

        public static MobileItemBuilder aMobileItem() {
            return new MobileItemBuilder();
        }

        public MobileItemBuilder withCityId(int cityId) {
            this.cityId = cityId;
            return this;
        }

        public MobileItemBuilder withProvinceId(int provinceId) {
            this.provinceId = provinceId;
            return this;
        }

        public MobileItemBuilder withMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public MobileItemBuilder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MobileItem build() {
            MobileItem mobileItem = new MobileItem();
            mobileItem.mobile = this.mobile;
            mobileItem.timestamp = this.timestamp;
            mobileItem.cityId = this.cityId;
            mobileItem.provinceId = this.provinceId;
            return mobileItem;
        }
    }
}
