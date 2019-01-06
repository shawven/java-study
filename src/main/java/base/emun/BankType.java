package base.emun;

public enum BankType {

    ICBC("工商银行", "01020000"),
    CCB("建设银行", "01050000"),
    BOC("中国银行", "0104000"),
    ABC("农业银行", "01030000"),
    BOCO("交通银行", "03010000"),
    POST("邮政储蓄银行", "0100000"),
    CMBCHINA("招商银行", "03080000"),
    PINGANBANK("平安银行", "0410000"),
    ECITIC("中信银行", "03020000"),
    CMBC("民生银行", "0305000"),
    CIB("兴业银行", "0309000"),
    CEB("光大银行", "03030000"),
    CGB("广发银行", "03060000"),
    HXB("华夏银行", "0304000"),
    SHB("上海银行", "04012900"),
    BCCB("北京银行", "04031000");

    private String bankName;
    private String bankNo;

    BankType(String bankName, String bankNo) {
        this.bankName = bankName;
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }


    public String getBankNo() {
        return bankNo;
    }


    public static BankType bankNoOf(String bankNo) {
        for (BankType bankType : values()) {
            if (bankType.getBankNo().equals(bankNo)) {
                return bankType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BankType{");
        sb.append("bankName='").append(bankName).append('\'');
        sb.append(", bankNo='").append(bankNo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
