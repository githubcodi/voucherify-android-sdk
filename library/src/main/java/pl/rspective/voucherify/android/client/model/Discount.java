package pl.rspective.voucherify.android.client.model;

import com.google.gson.annotations.SerializedName;

public class Discount {

    private DiscountType type;

    @SerializedName("amount_off")
    private Integer amountOff;

    @SerializedName("percent_off")
    private Double percentOff;

    @SerializedName("unit_off")
    private Double unitOff;

    @SerializedName("unit_type")
    private String unitType;

    public static Discount from(DiscountType type, int value) {
        Discount discount = new Discount();
        discount.type = type;
        switch(type) {
            case AMOUNT:  discount.amountOff = value; break;
            case PERCENT: discount.percentOff = value / 100.0; break;
            case UNIT:    discount.unitOff = value / 100.0; break;
            default:
                throw new IllegalArgumentException("Unsupported discount type: " + type);
        }
        return discount;
    }

    public DiscountType getType() {
        return type;
    }

    public Integer getAmountOff() {
        return amountOff;
    }

    public Double getPercentOff() {
        return percentOff;
    }

    public Double getUnitOff() {
        return unitOff;
    }

    public String getUnitType() {
        return unitType;
    }
}
