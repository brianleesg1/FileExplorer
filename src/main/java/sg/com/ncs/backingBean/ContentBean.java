package sg.com.ncs.backingBean;

import org.apache.myfaces.extensions.validator.crossval.annotation.DateIs;
import org.apache.myfaces.extensions.validator.crossval.annotation.DateIsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: brian
 * Date: 13/6/13
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@ManagedBean
@Scope("request")
public class ContentBean extends BaseBackingBean {

    @DateIs(type = DateIsType.after, valueOf = "#{dateUtil.currentDate}")
    private Date valueDate;

    private Date currDate = new Date();

    private boolean selected;
    private String pay_mthd_num;
    private String bank_code;
    private String branch_code;
    private int acct_num;
    private String amount;
    private String particular;
    private String custRefNum;
    private int successInd;
    private String reasonCd;
    private String otherReason;


    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Date getCurrDate() {
        return currDate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPay_mthd_num() {
        return pay_mthd_num;
    }

    public void setPay_mthd_num(String pay_mthd_num) {
        this.pay_mthd_num = pay_mthd_num;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public int getAcct_num() {
        return acct_num;
    }

    public void setAcct_num(int acct_num) {
        this.acct_num = acct_num;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getCustRefNum() {
        return custRefNum;
    }

    public void setCustRefNum(String custRefNum) {
        this.custRefNum = custRefNum;
    }

    public int getSuccessInd() {
        return successInd;
    }

    public void setSuccessInd(int successInd) {
        this.successInd = successInd;
    }

    public String getReasonCd() {
        return reasonCd;
    }

    public void setReasonCd(String reasonCd) {
        this.reasonCd = reasonCd;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public void submit() {
         log.info("submit");
         log.info("date = " + valueDate);
    }
}
